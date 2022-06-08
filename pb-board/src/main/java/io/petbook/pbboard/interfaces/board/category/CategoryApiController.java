package io.petbook.pbboard.interfaces.board.category;

import io.petbook.pbboard.common.response.CommonResponse;
import io.petbook.pbboard.domain.board.category.CategoryCommand;
import io.petbook.pbboard.domain.board.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// TODO: Command 객체 -> Request 객체 재구성
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryApiController {
    private final CategoryService categoryService;

    @GetMapping
    public CommonResponse getCategories() {
        return CommonResponse.success(categoryService.getCategoryInfoList());
    }

    @GetMapping("{token}")
    public CommonResponse getCategory(@PathVariable String token) {
        return CommonResponse.success(categoryService.getCategoryInfo(token));
    }

    @PostMapping
    public CommonResponse createCategory(@RequestBody CategoryCommand.Main request) {
        return CommonResponse.success(categoryService.createCategoryInfo(request));
    }

    @PutMapping
    public CommonResponse modifyCategory(@RequestBody CategoryCommand.Modifier request) {
        return CommonResponse.success(categoryService.modifyCategoryInfo(request));
    }

    @DeleteMapping("{token}")
    public CommonResponse deleteCategory(@PathVariable String token) {
        return CommonResponse.success(categoryService.deleteCategoryInfo(token));
    }
}
