package com.klaudi73.blog.repositories;


import com.klaudi73.blog.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity getByName(String name);
    UserEntity getByLogin(String login);
    UserEntity getByEmail(String email);
    UserEntity findByLogin(String login);

    Iterable<UserEntity> findAllByOrderByUserIdDesc();

    //Iterable<UserEntity> findAllByOrderByUserIdDesc(Long userId);

    Iterable<UserEntity> findAllByUserIdOrderByUserIdDesc(Long userId);

    Iterable<UserEntity> findAllByOrderByUserIdAsc();
}
