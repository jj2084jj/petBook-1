package io.petbook.pbboard.domain.board.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryReader categoryReader;
    private final CategoryStore categoryStore;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryInfo.Main> getCategoryInfoList() {
        return categoryReader
                .getList()
                .stream()
                .map(CategoryInfo.Main::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryInfo.Main getCategoryInfo(String token) {
        Category category = categoryReader.getEntity(token);
        return CategoryInfo.Main.toInfo(category);
    }

    @Override
    @Transactional
    public CategoryInfo.Main createCategoryInfo(CategoryCommand.Main command) {
        Category category = categoryStore.store(command.toEntity());
        return CategoryInfo.Main.toInfo(category);
    }

    @Override
    @Transactional // 이 어노테이션 안에서 데이터 변경이 들어가도, ~~~.save() 를 하지 않더라도 데이터가 갱신되게 된다.
    public CategoryInfo.Main modifyCategoryInfo(CategoryCommand.Modifier command) {
        Category category = categoryReader.getEntity(command.getToken());
        category.modifyByCommand(command);
        category.modified();
        return CategoryInfo.Main.toInfo(category);
    }

    @Override
    @Transactional
    public CategoryInfo.DeleteProcStatus deleteCategoryInfo(String token) {
        Category category = categoryReader.getEntity(token);
        category.deleted();
        return CategoryInfo.DeleteProcStatus.builder().completed(true).build();
    }

    @Override
    @Transactional
    public CategoryInfo.Main restoreCategoryInfo(String token) {
        Category category = categoryReader.getDeleteEntity(token);
        category.restored();
        return CategoryInfo.Main.toInfo(category);
    }

    @Override
    @Transactional
    public CategoryInfo.Main enableCategoryInfo(String token) {
        Category category = categoryReader.getEntity(token);
        category.enable();
        return CategoryInfo.Main.toInfo(category);
    }

    @Override
    @Transactional
    public CategoryInfo.Main disableCategoryInfo(String token) {
        Category category = categoryReader.getEntity(token);
        category.disable();
        return CategoryInfo.Main.toInfo(category);
    }
}
