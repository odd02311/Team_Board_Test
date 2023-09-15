package com.teampj.boardExample.repository;

import com.teampj.boardExample.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}