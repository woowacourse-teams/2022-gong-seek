package com.woowacourse.gongseek.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.application.TempArticleService;
import com.woowacourse.gongseek.article.presentation.ArticleController;
import com.woowacourse.gongseek.article.presentation.TempArticleController;
import com.woowacourse.gongseek.auth.application.AuthService;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.presentation.AuthController;
import com.woowacourse.gongseek.comment.application.CommentService;
import com.woowacourse.gongseek.comment.presentation.CommentController;
import com.woowacourse.gongseek.like.application.LikeService;
import com.woowacourse.gongseek.like.presentation.LikeController;
import com.woowacourse.gongseek.member.application.MemberService;
import com.woowacourse.gongseek.member.presentation.MemberController;
import com.woowacourse.gongseek.tag.application.TagService;
import com.woowacourse.gongseek.tag.presentation.TagController;
import com.woowacourse.gongseek.vote.application.VoteService;
import com.woowacourse.gongseek.vote.presentation.VoteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest({
        MemberController.class,
        ArticleController.class,
        TempArticleController.class,
        AuthController.class,
        TagController.class,
        CommentController.class,
        LikeController.class,
        VoteController.class
})
public abstract class ControllerTest {

    @MockBean
    protected ArticleService articleService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected TempArticleService tempArticleService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected LikeService likeService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected TagService tagService;

    @MockBean
    protected VoteService voteService;

    @MockBean
    protected AuthService authService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
