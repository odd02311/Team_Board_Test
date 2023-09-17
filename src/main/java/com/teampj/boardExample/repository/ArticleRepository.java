package com.teampj.boardExample.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.teampj.boardExample.domain.Article;
import com.teampj.boardExample.domain.QArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ArticleRepository extends
    JpaRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>,
    QuerydslBinderCustomizer<QArticle>
{
  @Override
  default void customize(QuerydslBindings bindings, QArticle root){
      bindings.excludeUnlistedProperties(true);
      bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색 필터
//      bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // 쿼리 생성 문자 == like '%{v}'
      bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
      bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 대소문자 구문 x | 쿼리 생성 문장 like '%${v}%'
      bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
      bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 문자열 x datetimeexpression | 적절한 검사가 없어서 eq로 놔둠
      bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
  }

}