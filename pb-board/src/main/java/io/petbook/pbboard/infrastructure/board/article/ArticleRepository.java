package io.petbook.pbboard.infrastructure.board.article;

import com.google.common.collect.Maps;
import io.petbook.pbboard.common.exception.InvalidParamException;
import io.petbook.pbboard.domain.board.article.Article;
import io.petbook.pbboard.domain.board.article.ArticleCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    // [Kang] JPA 에서 메소드 네이밍이 길어지면, JPQL 를 사용하는 걸 권장한다.
    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and a.token = :token")
    Optional<Article> findByToken(@Param("token") String token);

    @Query("select count(a) from Article a where a.crudStatus <> \"DELETED\"")
    Long countAll();

    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and (a.title like %:text% or a.context like %:text%) ")
    Page<Article> findAllTextContains(@Param("text") String text, Pageable pageable);

    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and a.title like %:text%")
    Page<Article> findTitleTextContains(@Param("text") String text, Pageable pageable);

    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and a.context like %:text%")
    Page<Article> findContextTextContains(@Param("text") String text, Pageable pageable);

    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and a.category.token = :categoryToken and (a.title like %:text% or a.context like %:text%) ")
    Page<Article> findAllTextContainsWithCategory(@Param("text") String text, @Param("categoryToken") String categoryToken, Pageable pageable);

    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and a.category.token = :categoryToken and a.title like %:text%")
    Page<Article> findTitleTextContainsWithCategory(@Param("text") String text, @Param("categoryToken") String categoryToken, Pageable pageable);

    @Query("select a from Article a where a.crudStatus <> \"DELETED\" and a.category.token = :categoryToken and a.context like %:text%")
    Page<Article> findContextTextContainsWithCategory(@Param("text") String text, @Param("categoryToken") String categoryToken, Pageable pageable);

    // [Kang] 페이징네이션 객체 기반 쿼리 처리 문단
    default Map<String, Object> findByPaginate(ArticleCommand.Paginate paginate) {
        if (paginate.getSz() == 0L) {
            throw new InvalidParamException("파라미터 오류 : 페이징네이션 페이지 수는 0 보다 큰 양수여야 합니다.");
        }

        Page<Article> articles = null;
        PageRequest pageRequest = PageRequest.of(paginate.getPg() - 1, paginate.getSz());

        // [Kang] Order By 처리
        switch (paginate.getOb()) {
            case CREATED_AT_ASC:
                pageRequest.withSort(Sort.by("createdAt"));
                break;
            case CREATED_AT_DESC:
                pageRequest.withSort(Sort.by("createdAt").descending());
                break;

            // [Kang] 좋아요, 조회수, 정확도는 TODO.
        }

        boolean hasCtgTk = !StringUtils.isEmpty(paginate.getCtgTk());

        // [Kang] Search By 처리
        switch(paginate.getSb()) {
            case ALL_CONTAINS:
                articles =
                    hasCtgTk ?
                        findAllTextContainsWithCategory(paginate.getSt(), paginate.getCtgTk(), pageRequest) :
                        findAllTextContains(paginate.getSt(), pageRequest);
            case TITLE_CONTAINS:
                articles =
                    hasCtgTk ?
                        findTitleTextContainsWithCategory(paginate.getSt(), paginate.getCtgTk(), pageRequest) :
                        findTitleTextContains(paginate.getSt(), pageRequest);
                break;
            case CONTEXT_CONTAINS:
                articles =
                    hasCtgTk ?
                        findContextTextContainsWithCategory(paginate.getSt(), paginate.getCtgTk(), pageRequest) :
                        findContextTextContains(paginate.getSt(), pageRequest);
                break;
        }

        // [Kang] 결과물 반환
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", articles.getContent());
        result.put("total", articles.getTotalElements());
        result.put("pages", articles.getTotalPages());

        return result;
    }
}
