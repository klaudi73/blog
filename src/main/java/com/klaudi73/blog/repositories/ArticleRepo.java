package com.klaudi73.blog.repositories;

import com.klaudi73.blog.models.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends CrudRepository<ArticleEntity, Long> {
    List<ArticleEntity> findAllByOrderByTitleAsc();
    List<ArticleEntity> findAllByOrderByIdAsc();
    //List<ArticleEntity> findById(Long id);
    //UserEntity getByName(String name);
    //UserEntity getByLogin(String login);
}
