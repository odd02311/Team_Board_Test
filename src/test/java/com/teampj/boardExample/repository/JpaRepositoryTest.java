package com.teampj.boardExample.repository;

import com.teampj.boardExample.config.JpaConfig;
import com.teampj.boardExample.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//@ActiveProfiles("testdb")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

  private final ArticleRepository articleRepository;
  private final ArticleCommentRepository articleCommentRepository;

  public JpaRepositoryTest(
      @Autowired  ArticleRepository articleRepository,
      @Autowired  ArticleCommentRepository articleCommentRepository)
  {
    this.articleRepository = articleRepository;
    this.articleCommentRepository = articleCommentRepository;
  }

  @DisplayName("select test")
  @Test
  void givenTestData_whenSelecting_thenWorkingFine() {
    // given

    // when
    List<Article> articles = articleRepository.findAll();

    // then
    assertThat(articles)
        .isNotNull()
        .hasSize(123);
  }

  @DisplayName("insert test")
  @Test
  void givenTestData_whenInserting_thenWorkingFine() {
    // given
    long previousCount = articleRepository.count();

    // when
    Article savedArticle = articleRepository.save(Article.of("new article", "new content", "#Spring"));

    // then
    assertThat(articleRepository.count()).isEqualTo(previousCount + 1);

  }

  @DisplayName("update test") // 롤백 되기때문에 반영되진 않음
  @Test
  void givenTestData_whenUpdating_thenWorkingFine() { // 기존에 있던 데이를 수정했을 때 쿼리가 발생하는것 테스트
    // given
    Article article = articleRepository.findById(1L).orElseThrow();
    String updatedHashtag = "#springboot";
    article.setHashtag(updatedHashtag);

    // when
    Article savedArticle = articleRepository.saveAndFlush(article);


    // then
    assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);

  }

  @DisplayName("delete test")
  @Test
  void givenTestData_whenDeleting_thenWorkingFine() {
    // given
    Article article = articleRepository.findById(1L).orElseThrow();
    long previousArticleCount = articleRepository.count();
    long previousArticleCommentCount = articleCommentRepository.count();
    int deletedCommentsSize = article.getArticleComments().size();


    // when
    articleRepository.delete(article); // void


    // then
    assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
    assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);



  }
}