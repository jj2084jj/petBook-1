package io.petbook.pbboard.domain.board.category;

/**
 * [Kang] Category 데이터에 대한 조회성 작업
 */
public interface CategoryReader {
    Category getEntity(String token);
}
