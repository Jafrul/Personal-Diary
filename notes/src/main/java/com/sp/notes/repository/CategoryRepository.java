package com.sp.notes.repository;

import com.sp.notes.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByNameAndUsername(String name, String username);

    List<Category> findByUsername(String username);

    Category findByIdAndUsername(Integer id, String username);

    @Transactional
    @Modifying
    Integer deleteByIdAndUsername(Integer id, String username);
}
