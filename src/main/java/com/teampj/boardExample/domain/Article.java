package com.teampj.boardExample.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
    @Index(columnList =  "title"),
    @Index(columnList =  "hashtag"),
    @Index(columnList =  "createdAt"),
    @Index(columnList =  "createdBy")
})


@Entity
public class Article extends AuditingFields{ // 상속으로 필드 연결

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000)private String content; // 본문

    @Setter private String hashtag; // 해시태그

    // 연관관계: 양방향 바인딩 (one-to-many)
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();



//    @Embedded // 메타 데이터를 묶은 클래스를 만들어서 이 안에 필드를 추가


    // 기본 생성자
    protected Article() {

    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag){
        return new Article(title, content, hashtag);
    }

    @Override // 동등성 검사
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id); // 영속화 되지 않은 엔티티는 모두 동등성 검사를 탈락한다.
        // 데이터베이스에서 사용하는 환경에서 서로 다른 2 low가 같은 조건이 무엇인가에 답을 줌
        // id가 부여되지않으면 동등성검사가 의미가 없는걸로 보고 다른것으로 간주하거나
        // 처리하지않는걸로 간주함
        // id가 있다면 id가 같은지만 보고 같으면 객체가 동일함 그걸로 동일성 검사를 하는데 id만 가지고 함
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
