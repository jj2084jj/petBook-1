package io.petbook.pbboard.domain.board.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private ArticleReader articleReader;
    private ArticleStore articleStore;

    @Override
    public Iterable<ArticleInfo.Brief> loadBriefList(ArticleCommand.Paginate paginate) {
        return Collections.EMPTY_LIST;
    }
}
