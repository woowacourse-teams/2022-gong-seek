package com.woowacourse.gongseek;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataFactory implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Override
    public void run(String... args) {
        if (memberRepository.findAll().isEmpty()) {
            Member member = new Member("새로운", "qwazsd", "http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg");
            memberRepository.save(member);
            List<Article> articles = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                articles.add(new Article("제목_질문" + i, "내용" + i, Category.QUESTION, member));
            }
            for (int i = 1; i < 10; i++) {
                articles.add(new Article("제목_토론" + i, "내용" + i, Category.DISCUSSION, member));
            }
            articleRepository.saveAll(articles);
        }
    }
}
