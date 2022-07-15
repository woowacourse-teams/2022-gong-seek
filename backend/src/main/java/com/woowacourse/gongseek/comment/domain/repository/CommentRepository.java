package com.woowacourse.gongseek.comment.domain.repository;

import com.woowacourse.gongseek.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
