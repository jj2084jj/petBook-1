package io.petbook.pbboard.infrastructure.board.article;

import io.petbook.pbboard.common.exception.EntityNotFoundException;
import io.petbook.pbboard.domain.board.article.Article;
import io.petbook.pbboard.domain.board.article.ArticleCommand;
import io.petbook.pbboard.domain.board.article.ArticleReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleReaderImpl implements ArticleReader {
    private final ArticleRepository articleRepository;

    @Override
    public Article getEntity(String token) {
        return articleRepository
                .findByToken(token)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Map<String, Object> getList(ArticleCommand.Paginate paginate) {
        return articleRepository.findByPaginate(paginate);
    }

    // [Kang] TODO : 페이징네이션 모델 반영
}
