package com.woowacourse.gongseek.question.application;

import com.woowacourse.gongseek.question.domain.Question;
import com.woowacourse.gongseek.question.domain.repository.QuestionRepository;
import com.woowacourse.gongseek.question.presentation.dto.QuestionRequest;
import com.woowacourse.gongseek.question.presentation.dto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionResponse save(QuestionRequest questionRequest) {
        Question question = questionRepository.save(questionRequest.toEntity());
        return new QuestionResponse(question);
    }
}
