package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.repository.TempArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TempArticleEventService {
    private final TempArticleRepository tempArticleRepository;

    public void delete(Long id) {
        tempArticleRepository.deleteById(id);
    }
}
