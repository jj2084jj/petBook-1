package io.petbook.pbboard.domain.board.article;

import java.util.Map;

public interface ArticleService {
    Map<String, Object>  loadBriefList(ArticleCommand.Paginate paginate);
    ArticleInfo.Detail loadDetailView(String token);
    ArticleInfo.Brief createArticle(ArticleCommand.Main command);
}
