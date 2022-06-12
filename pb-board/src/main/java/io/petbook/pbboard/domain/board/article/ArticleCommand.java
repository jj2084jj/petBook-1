package io.petbook.pbboard.domain.board.article;

/**
 * [Kang] Article Writing Model
 */

import io.petbook.pbboard.domain.board.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ArticleCommand {
    @Getter
    @Builder
    public static class Main {
        private final String title;
        private final String context;
        private final Boolean visible;
        private final String userToken;
        private final String categoryToken;

        public Article toEntity(Category category) {
            Article article = Article.builder()
                                .title(title)
                                .context(context)
                                .userToken(userToken)
                                .category(category)
                                .build();

            if (visible) {
                article.enable();
            } else {
                article.disable();
            }

            return article;
        }

        // [Kang] TODO : Data Validation Check
    }

    @Getter
    @Builder
    public static class Paginate {
        private int pg = 1;
        private int sz = 10;
        private OrderBy ob = OrderBy.CREATED_AT_DESC;
        private SearchBy sb;
        private final String st;
        private final String ctgTk; // [Kang] 카테고리 토큰

        @Getter
        @RequiredArgsConstructor
        public enum OrderBy {
            // [Kang] 코드 순서는 향후에 바뀔 수도 있다.
            CREATED_AT_ASC(0),
            CREATED_AT_DESC(1),
            VIEW_COUNTS_DESC(2),
            LIKE_COUNTS_DESC(3),
            ACCURATE_DESC(4); // [Kang] TODO: 검색 결과에 대한 정확도 계산 향상

            private final int code;
        }

        @Getter
        @RequiredArgsConstructor
        public enum SearchBy {
            ALL_CONTAINS(0), // [Kang] 제목, 내용 모두 해당.
            TITLE_CONTAINS(1),
            CONTEXT_CONTAINS(2);

            // [Kang] 사용자 이름 정보는 User Domain 에서 얻어와 해결해야 하기 때문에 향후 생각해볼 것.

            private final int code;
        }
    }

    // [Kang] TODO: sb, ob 값으로, 검색 조건에 대한 enumeration 을 반환하는 기능을 하나 만들어야 할 거 같다.
}
