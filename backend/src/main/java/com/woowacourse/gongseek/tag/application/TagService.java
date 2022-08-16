package com.woowacourse.gongseek.tag.application;

import com.woowacourse.gongseek.tag.domain.Name;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.tag.presentation.dto.TagsResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;

    public Tags getOrCreateTags(Tags tags) {
        List<Tag> foundTags = tags.getTagNames().stream()
                .map(this::getOrCreateTagIfNotExist)
                .collect(Collectors.toList());
        return new Tags(foundTags);
    }

    private Tag getOrCreateTagIfNotExist(String name) {
        return tagRepository.findByName(new Name(name))
                .orElseGet(() -> tagRepository.save(new Tag(name)));
    }

    @Transactional(readOnly = true)
    public TagsResponse getAll() {
        List<Tag> tags = tagRepository.findAll();
        return TagsResponse.of(tags);
    }

    public void delete(String name) {
        tagRepository.deleteByName(new Name(name));
    }
}
