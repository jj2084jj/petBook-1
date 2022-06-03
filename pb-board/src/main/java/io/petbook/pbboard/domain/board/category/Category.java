package io.petbook.pbboard.domain.board.category;

import com.google.common.collect.Lists;
import io.petbook.pbboard.common.exception.InvalidParamException;
import io.petbook.pbboard.common.util.TokenGenerator;
import io.petbook.pbboard.domain.AbstractEntity;
import io.petbook.pbboard.domain.board.article.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

/**
 * [Kang] Category JPA Domain Entity
 */

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "_category")
public class Category extends AbstractEntity {
    private static final String ENTITY_PREFIX = "ctgy_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "title", length = 75, nullable = false)
    private String title;

    @Column(name = "user_token", nullable = false)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Article> posts = Lists.newArrayList();

    @Builder
    public Category (
        String title,
        boolean visible,
        String userToken
    ) {
        if (StringUtils.isEmpty(title)) {
            throw new InvalidParamException("파라미터 오류 - Category : title");
        }

        if (StringUtils.isEmpty(title)) {
            throw new InvalidParamException("파라미터 오류 - Category : userToken");
        }

        // [Kang] Category 객체 대체키 (Auto Increment ID 대체용)
        this.token = TokenGenerator.randomCharacterWithPrefix(ENTITY_PREFIX);

        // [Kang] Main Fields
        this.title = title;

        if (visible) {
            this.enable();
        } else {
            this.disable();
        }

        // [Kang] FK Setting (User)
        this.userToken = userToken;

        // [Kang] Abstract Entity Created 설정
        super.created();
    }

    public void enable() {
        this.visibleStatus = VisibleStatus.ENABLED;
    }

    public void disable() {
        this.visibleStatus = VisibleStatus.DISABLED;
    }
}
