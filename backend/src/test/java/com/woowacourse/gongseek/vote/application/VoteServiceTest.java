package com.woowacourse.gongseek.vote.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.support.DatabaseCleaner;
import com.woowacourse.gongseek.support.IntegrationTest;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import com.woowacourse.gongseek.vote.exception.UnavailableArticleException;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteItemResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class VoteServiceTest extends IntegrationTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteItemRepository voteItemRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VoteHistoryRepository voteHistoryRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;
    private Article discussionArticle;
    private List<VoteItem> voteItems;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("기론", "gyuchool", "avatar.url"));
        discussionArticle = articleRepository.save(
                new Article("게시글 제목입니다.", "내용이 들어갑니다.", Category.DISCUSSION, member, false)
        );
        Vote vote = voteRepository.save(new Vote(discussionArticle, LocalDateTime.now().plusDays(7)));
        voteItems = voteItemRepository.saveAll(List.of(new VoteItem("content1", vote), new VoteItem("content2", vote)));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

    @Test
    void 투표를_생성한다() {
        VoteCreateResponse voteCreateResponse = voteService.create(
                new LoginMember(member.getId()),
                discussionArticle.getId(),
                new VoteCreateRequest(List.of("Dto 최고다.", "VO 최고다"), LocalDateTime.now().plusDays(5))
        );

        assertThat(voteCreateResponse.getArticleId()).isEqualTo(discussionArticle.getId());
    }

    @Test
    void 게시글의_작성자가_아닌_사용자가_투표를_생성하면_예외가_발생한다() {
        LoginMember member = new LoginMember(
                memberRepository.save(new Member("jurl", "jurlring", "avatarUrl")).getId());
        VoteCreateRequest voteCreateRequest = new VoteCreateRequest(List.of("Dto 최고다.", "VO 최고다"),
                LocalDateTime.now().plusDays(5));

        assertThatThrownBy(() -> voteService.create(member, discussionArticle.getId(), voteCreateRequest))
                .isExactlyInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 질문게시글에서_투표를_생성하면_예외가_발생한다() {
        Article article = articleRepository.save(new Article("토론입니당", "내용입니다..", Category.QUESTION, member, false));

        assertThatThrownBy(() -> voteService.create(new LoginMember(member.getId()), article.getId(),
                new VoteCreateRequest(List.of("Dto 최고다.", "VO 최고다"), LocalDateTime.now().plusDays(5)))
        ).isExactlyInstanceOf(UnavailableArticleException.class)
                .hasMessageContaining("토론 게시글만 투표를 생성할 수 있습니다.");
    }

    @Test
    void 투표를_안한_사용자가_투표를_조회한다() {
        VoteResponse voteResponse = voteService.getOne(discussionArticle.getId(), new LoginMember(member.getId()));

        assertAll(
                () -> assertThat(voteResponse.getArticleId()).isEqualTo(discussionArticle.getId()),
                () -> assertThat(voteResponse.getVotedItemId()).isNull(),
                () -> assertThat(voteResponse.getVoteItems()).hasSize(2),
                () -> assertThat(voteResponse.isExpired()).isFalse()
        );
    }

    @Test
    void 투표를_한_사용자가_투표를_조회한다() {
        Long selectVoteItemId = voteItems.get(0).getId();
        voteService.doVote(discussionArticle.getId(), new LoginMember(member.getId()),
                new SelectVoteItemIdRequest(selectVoteItemId));

        VoteResponse voteResponse = voteService.getOne(discussionArticle.getId(), new LoginMember(member.getId()));

        assertAll(
                () -> assertThat(voteResponse.getArticleId()).isEqualTo(discussionArticle.getId()),
                () -> assertThat(voteResponse.getVotedItemId()).isEqualTo(selectVoteItemId),
                () -> assertThat(voteResponse.getVoteItems()).hasSize(2),
                () -> assertThat(voteResponse.isExpired()).isFalse()
        );
    }

    @Test
    void 처음_투표를_하면_선택한_투표수가_증가한다() {
        LoginMember loginMember = new LoginMember(member.getId());
        int selectIndex = 0;

        voteService.doVote(discussionArticle.getId(), loginMember,
                new SelectVoteItemIdRequest(voteItems.get(selectIndex).getId()));

        List<VoteItemResponse> foundVoteItems = voteService.getOne(discussionArticle.getId(), loginMember)
                .getVoteItems();
        assertAll(
                () -> assertThat(foundVoteItems.get(selectIndex).getAmount()).isEqualTo(1),
                () -> assertThat(foundVoteItems.get(1).getAmount()).isEqualTo(0)
        );
    }

    @Test
    void 다른_항목을_투표하면_기존의_투표수는_감소하고_선택한_투표수가_증가한다() {
        LoginMember loginMember = new LoginMember(member.getId());
        voteService.doVote(discussionArticle.getId(), loginMember,
                new SelectVoteItemIdRequest(voteItems.get(0).getId()));
        voteService.doVote(discussionArticle.getId(), loginMember,
                new SelectVoteItemIdRequest(voteItems.get(1).getId()));

        List<VoteItemResponse> foundVoteItems = voteService.getOne(discussionArticle.getId(), loginMember)
                .getVoteItems();
        assertAll(
                () -> assertThat(foundVoteItems.get(0).getAmount()).isEqualTo(0),
                () -> assertThat(foundVoteItems.get(1).getAmount()).isEqualTo(1)
        );
    }

    @Test
    void 서로_다른_두_사람이_같은_항목을_투표하면_투표수는_2가_된다() {
        Member other = memberRepository.save(new Member("다른이", "gittt", "avater.url"));
        LoginMember loginMember1 = new LoginMember(member.getId());
        LoginMember loginMember2 = new LoginMember(other.getId());

        voteService.doVote(discussionArticle.getId(), loginMember1,
                new SelectVoteItemIdRequest(voteItems.get(0).getId()));
        voteService.doVote(discussionArticle.getId(), loginMember2,
                new SelectVoteItemIdRequest(voteItems.get(0).getId()));

        List<VoteItemResponse> foundVoteItems = voteService.getOne(discussionArticle.getId(), loginMember1)
                .getVoteItems();

        assertThat(foundVoteItems.get(0).getAmount()).isEqualTo(2);
    }

    @Test
    void 비회원이_투표를_하면_예외를_발생한다() {
        assertThatThrownBy(
                () -> voteService.doVote(discussionArticle.getId(), new GuestMember(),
                        new SelectVoteItemIdRequest(voteItems.get(0).getId())))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 투표중인_토론게시글을_삭제하면_투표도_삭제된다() {
        Article article = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        Vote vote = new Vote(article, LocalDateTime.now().plusDays(3));
        voteRepository.save(vote);
        VoteItem firstVoteItem = new VoteItem("A번", vote);
        VoteItem secondVoteItem = new VoteItem("B번", vote);
        VoteItem thirdVoteItem = new VoteItem("C번", vote);
        voteItemRepository.saveAll(List.of(firstVoteItem, secondVoteItem, thirdVoteItem));

        voteHistoryRepository.save(new VoteHistory(member, firstVoteItem));
        articleService.delete(new LoginMember(member.getId()), article.getId());

        assertAll(
                () -> assertThat(voteRepository.findByArticleId(article.getId())).isEmpty(),
                () -> assertThat(voteHistoryRepository.findAll()).isEmpty()
        );
    }
}
