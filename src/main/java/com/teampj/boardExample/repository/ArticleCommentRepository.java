package com.teampj.boardExample.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.teampj.boardExample.domain.Article;
import com.teampj.boardExample.domain.ArticleComment;
import com.teampj.boardExample.domain.QArticle;
import com.teampj.boardExample.domain.QArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
    JpaRepository<ArticleComment, Long>,
    QuerydslPredicateExecutor<ArticleComment>, // Article 엔티티 안에있는 모든 필드에 대한 검색 기능 추가
    QuerydslBinderCustomizer<QArticleComment> //
{
  @Override
  default void customize(QuerydslBindings bindings, QArticleComment root){
    bindings.excludeUnlistedProperties(true);
    bindings.including(root.content, root.createdAt, root.createdBy); // 검색 필터
    bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
    bindings.bind(root.createdAt).first(DateTimeExpression::eq);
    bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
  }

}
