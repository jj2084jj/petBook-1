package io.petbook.pbboard.domain.post;

import io.petbook.pbboard.common.exception.InvalidParamException;
import io.petbook.pbboard.domain.AbstractEntity;
import io.petbook.pbboard.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "_post")
public class Post extends AbstractEntity {
    private static final String ENTITY_PREFIX = "post_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "title", length = 75, nullable = false)
    private String title;

    @Column(name = "context", length = 2048, nullable = false)
    private String context;

    @Column(name = "user_token", unique = true, nullable = false)
    private String userToken;

    @Column(name = "visible_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisibleStatus visibleStatus;

    @Getter
    @RequiredArgsConstructor
    public enum VisibleStatus {
        ENABLED("공개"), DISABLED("비공개");
        private final String description;
    }

    // 자동으로 두면 fetch Type 은 Eager 로 된다.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // TODO: 권한 별 공개 여부, 게시글 조회수 등등

    @Builder
    public Post (
        String title,
        String context,
        String userToken,
        Category category
    ) {
        if (StringUtils.isEmpty(title)) {
            throw new InvalidParamException("파라미터 오류 - Post : title");
        }
        if (StringUtils.isEmpty(context)) {
            throw new InvalidParamException("파라미터 오류 - Post : context");
        }
        if (StringUtils.isEmpty(title)) {
            throw new InvalidParamException("파라미터 오류 - Category : title");
        }
    }

    public void enable() {
        this.visibleStatus = VisibleStatus.ENABLED;
    }

    public void disable() {
        this.visibleStatus = VisibleStatus.DISABLED;
    }
}
