package com.nhi.blog.respositories;

import com.nhi.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRespository extends JpaRepository<Role,Integer> {
    Role findByNameContaining(String name);
}
