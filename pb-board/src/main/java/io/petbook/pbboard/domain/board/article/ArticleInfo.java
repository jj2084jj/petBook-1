package io.petbook.pbboard.domain.board.article;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * [Kang] Article Reading Model
 */
public class ArticleInfo {
    @Getter
    @SuperBuilder
    public static class Main {
        // [Kang] React, Vue 등에서 여러 데이터들에 대해 렌더링할 때, Key 값을 설정하기 위한 값이다.
        private final String token;
        private final String title;
        private String context;

        // [Kang] TODO: UserToken 으로 사용자 이름, 별명 정보 불러와 주입 시키기
        private final String author;
    }

    @Getter
    @SuperBuilder
    public static class Brief extends Main {
        // [Kang] 리스트 등에서 불러오기 위한 단순 데이터
        private final String createdAt;

        private final Long viewCount; // [Kang] 아직 조회수 설계에 대해 고려를 안 했음.
        private final Long likeCount; // [Kang] 아직 좋아요 설계에 대해 고려를 안 했음.
    }

    @Getter
    @SuperBuilder
    public static class Detail extends Main {
        // [Kang] 포스트 조회 등에서 불러오기 위한 복합 데이터
        private final String categoryTitle;
        private final String createdAt;
        private final String updatedAt;

        private final Long viewCount; // [Kang] 아직 조회수 설계에 대해 고려를 안 했음.
        private final Long likeCount; // [Kang] 아직 좋아요 설계에 대해 고려를 안 했음.

        // [Kang] TODO: 댓글 수, 댓글 목록 등등 반영 필요
    }
}
