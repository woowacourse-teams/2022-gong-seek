package com.woowacourse.gongseek.tag.presentation;

import com.woowacourse.gongseek.tag.application.TagService;
import com.woowacourse.gongseek.tag.application.dto.TagsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/tags")
@RestController
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<TagsResponse> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }
}
