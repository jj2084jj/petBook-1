package io.petbook.pbboard.domain.board.article;

public interface ArticleService {
    Iterable<ArticleInfo.Brief> loadBriefList(ArticleCommand.Paginate paginate);
}
