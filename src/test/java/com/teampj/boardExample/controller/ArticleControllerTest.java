package com.teampj.boardExample.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("content view controller")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private  final MockMvc mvc;


  public ArticleControllerTest(@Autowired MockMvc mvc) { // 테스트 패키지에 있는 생성자들은 @Autowired를 직접 주입 해줘야함
    this.mvc = mvc;
  }

  @Disabled("구현 중")
  @DisplayName("[view] [GET] contents list (board) page") // 게시글 리스트 (게시판) 페이지 - 정상 호출
  @Test
  public void whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
    // given

    // when & then
    mvc.perform(get("/articles"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andExpect(view().name("articles/index"))
        .andExpect(model().attributeExists("articles"));
  }

  @Disabled("구현 중")
  @DisplayName("[view] [GET] contents page") // 게시글 상세 페이지 - 정상 호출
  @Test
  public void whenRequestingArticleView_thenReturnsArticleView() throws Exception {
    // given

    // when & then
    mvc.perform(get("/articles/1")) // /articles/{article-id}
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andExpect(view().name("articles/detail"))
        .andExpect(model().attributeExists("article"))
        .andExpect(model().attributeExists("articleComments"));
  }

  @Disabled("구현 중")
  @DisplayName("[view] [GET] content search page") // 게시글 검색 전용 페이지 - 정상 호출
  @Test
  public void whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
    // given

    // when & then
    mvc.perform(get("/articles/search")) // /articles/{article-id}
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andExpect(model().attributeExists("articles/search"));
  }

  @Disabled("구현 중")
  @DisplayName("[view] [GET] content hashtag search page") // 게시글 해시태그 검색 페이지 - 정상 호출
  @Test
  public void whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
    // given

    // when & then
    mvc.perform(get("/articles/search-hashtag")) // /articles/{article-id}
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.TEXT_HTML))
        .andExpect(model().attributeExists("articles/hash-tag"));
  }
}