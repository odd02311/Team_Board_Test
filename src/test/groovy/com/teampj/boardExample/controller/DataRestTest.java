package com.teampj.boardExample.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Data REST - API Test")
// integration test(API를 실행한 결과가 Repository까지 실행)이기 때문에 db에 영향을 줘서 롤백을 해줘야 함
@Transactional // test 롤백
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

  private final MockMvc mvc;

  public DataRestTest(@Autowired MockMvc mvc) {
    this.mvc = mvc;
  }

  @DisplayName("[API] Board list - test") // 게시글 리스트 조회
  @Test
  void whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
    // given

    // when & then
    mvc.perform(get("/api/articles")) // 리스트 조회 / *get() = MockMvcRequestBuilders.get() static import
        .andExpect(status().isOk()) // 존재 검사식
        // content type이 application/hal+json으로 뜨기 때문에, APPLICATION_JSON은 쓰지 못하고 직접 입력해줘야 한다.
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
        // 결과 확인
//        .andDo(print());
  }

  @DisplayName("[API] check single context  - test") // 게시글 단건 조회 조회
  @Test
  void whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {
    // given

    // when & then
    mvc.perform(get("/api/articles/1")) // 특정해서 조회함
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
  }

  @DisplayName("[API] board -> check comment - test") // 게시글 댓글 조회
  @Test
  void whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {
    // given

    // when & then
    mvc.perform(get("/api/articles/1/articleComments")) // 특정게시글에서 조회함
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
  }

  @DisplayName("[API] check comments - test") // 게시글 댓글 리스트 조회
  @Test
  void whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {
    // given

    // when & then
    mvc.perform(get("/api/articleComments"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
  }

  @DisplayName("[API] check single comment - test") // 게시글 댓글 단건 리스트 조회
  @Test
  void whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {
    // given

    // when & then
    mvc.perform(get("/api/articleComments/1")) // 특정해서 조회함
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
  }


}
