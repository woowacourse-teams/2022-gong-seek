package com.woowacourse.gongseek.support;

import com.woowacourse.gongseek.article.infra.repository.ArticleTagRepositoryImpl;
import com.woowacourse.gongseek.article.infra.repository.PagingArticleRepositoryImpl;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.config.QuerydslConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({JpaAuditingConfig.class, QuerydslConfig.class, PagingArticleRepositoryImpl.class,
        ArticleTagRepositoryImpl.class})
@DataJpaTest
public @interface RepositoryTest {
}
