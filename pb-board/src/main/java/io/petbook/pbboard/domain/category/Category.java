package io.petbook.pbboard.domain.category;

import com.google.common.collect.Lists;
import io.petbook.pbboard.common.exception.InvalidParamException;
import io.petbook.pbboard.common.util.TokenGenerator;
import io.petbook.pbboard.domain.AbstractEntity;
import io.petbook.pbboard.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "title", length = 75)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Post> posts = Lists.newArrayList();

    @Builder
    public Category (
        String title
    ) {
        if (StringUtils.isEmpty(title)) {
            throw new InvalidParamException("파라미터 오류 - Category : title");
        }

        super.created();
        this.token = TokenGenerator.randomCharacterWithPrefix(ENTITY_PREFIX);
        this.title = title;
    }
}
