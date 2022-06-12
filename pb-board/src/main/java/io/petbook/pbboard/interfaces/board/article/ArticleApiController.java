package io.petbook.pbboard.interfaces.board.article;

import io.petbook.pbboard.common.response.CommonResponse;
import io.petbook.pbboard.domain.board.article.ArticleCommand;
import io.petbook.pbboard.domain.board.article.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// TODO: Command 객체 -> Request 객체 재구성 (다음 주 부터 진행 예정)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleApiController {
    private final ArticleService articleService;

    @GetMapping
    public CommonResponse getArticleBriefList(ArticleCommand.Paginate paginate) {
        return CommonResponse.success(articleService.loadBriefList(paginate));
    }

    @GetMapping("{token}")
    public CommonResponse getArticleDetailList(@PathVariable String token) {
        return CommonResponse.success(articleService.loadDetailView(token));
    }

    @PostMapping
    public CommonResponse createArticle(@RequestBody ArticleCommand.Main request) {
        return CommonResponse.success(articleService.createArticle(request));
    }
}
