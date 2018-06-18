package com.klaudi73.blog.repositories;

import com.klaudi73.blog.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);
}
