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
        private final String userToken;
        private final Category category;

        public Article toEntity(
            String title,
            String context,
            boolean visible,
            String userToken,
            Category category
        ) {
            return Article.builder()
                    .title(title)
                    .context(context)
                    .visible(visible)
                    .userToken(userToken)
                    .category(category)
                    .build();
        }

        // [Kang] TODO : Data Validation Check
    }

    @Getter
    @Builder
    public static class Paginate {
        private final int pg = 1;
        private final int sz = 10;
        private final OrderBy ob;
        private final SearchBy sb;
        private final String st;
        private final String ctgTk; // [Kang] 카테고리 토큰

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

        @RequiredArgsConstructor
        public enum SearchBy {
            ALL_CONTAINS(0), // [Kang] 제목, 내용 모두 해당.
            TITLE_CONTAINS(1),
            CONTEXT_CONTAINS(2);

            // [Kang] 사용자 이름 정보는 User Domain 에서 얻어와 해결해야 하기 때문에 향후 생각해볼 것.

            private final int code;
        }
    }
}
