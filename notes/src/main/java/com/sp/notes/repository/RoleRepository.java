package com.sp.notes.repository;

import com.sp.notes.model.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Set<Role> findByName(String name);
}
