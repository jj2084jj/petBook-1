package io.petbook.pbboard.domain.board.category;

import lombok.Builder;
import lombok.Getter;

/**
 * [Kang] Category Writing Model
 */

public class CategoryCommand {
    @Getter
    @Builder
    public static class Main {
        private final String title;
        private final String userToken;
        private final Boolean visible;

        public Category toEntity() {
            return Category.builder()
                    .title(title)
                    .userToken(userToken)
                    .visible(visible)
                    .build();
        }

        // [Kang] TODO : Data Validation Check
    }

    @Getter
    @Builder
    public static class Modifier {
        private final String token;
        private final String title;
        private final String userToken;
        private final Boolean visible;

        // [Kang] TODO : Data Validation Check
    }
}
