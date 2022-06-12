package io.petbook.pbboard.domain.board.article;

import io.petbook.pbboard.domain.board.category.Category;
import io.petbook.pbboard.domain.board.category.CategoryReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleReader articleReader;
    private final ArticleStore articleStore;

    private final CategoryReader categoryReader;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> loadBriefList(ArticleCommand.Paginate paginate) {
        Map<String, Object> pgnMap = articleReader.getList(paginate);
        List<Article> articles = (List<Article>) pgnMap.get("data");
        List<ArticleInfo.Brief> articleBriefs = articles.stream().map(ArticleInfo.Brief::toInfo).collect(Collectors.toList());
        pgnMap.put("data", articleBriefs);
        return pgnMap;
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleInfo.Detail loadDetailView(String token) {
        Article article = articleReader.getEntity(token);
        return ArticleInfo.Detail.toInfo(article);
    }

    @Override
    @Transactional
    public ArticleInfo.Brief createArticle(ArticleCommand.Main command) {
        Category category = categoryReader.getEntity(command.getCategoryToken());
        Article newArticle = command.toEntity(category);
        Article article = articleStore.store(newArticle);
        return ArticleInfo.Brief.toInfo(article);
    }
}
