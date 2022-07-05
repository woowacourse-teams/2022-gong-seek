package com.woowacourse.gongseek.question.presentation;

import com.woowacourse.gongseek.question.application.QuestionService;
import com.woowacourse.gongseek.question.presentation.dto.QuestionRequest;
import com.woowacourse.gongseek.question.presentation.dto.QuestionResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/articles/questions")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> create(@RequestBody QuestionRequest questionRequest) {
        QuestionResponse questionResponse = questionService.save(questionRequest);
        return ResponseEntity.created(URI.create("/api/articles/questions/" + questionResponse.getId())).build();
    }
}
