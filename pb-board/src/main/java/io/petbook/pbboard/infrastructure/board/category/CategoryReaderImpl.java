package io.petbook.pbboard.infrastructure.board.category;


import io.petbook.pbboard.common.exception.EntityNotFoundException;
import io.petbook.pbboard.domain.board.category.Category;
import io.petbook.pbboard.domain.board.category.CategoryReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryReaderImpl implements CategoryReader {
    private CategoryRepository categoryRepository;


    @Override
    public Category getEntity(String token) {
        return categoryRepository
                .findByToken(token)
                .orElseThrow(EntityNotFoundException::new);
    }
}
