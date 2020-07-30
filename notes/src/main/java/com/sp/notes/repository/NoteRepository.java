package com.sp.notes.repository;

import com.sp.notes.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    boolean existsByTitle(String title);

    @Query("select n from Note n where n.username = :username")
    Page<Note> findByUsername(@Param("username") String username, Pageable pageable);

    @Query("select n from Note n where n.category.id = :category and n.username = :username")
    Page<Note> findByCategoryAndUsername(@Param("category") Integer categoryId, @Param("username") String username, Pageable pageable);

    @Query("select n from Note n where n.id = :id and n.username = :username")
    Note findByIdAndUsername(@Param("id") Integer id, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("delete from Note n where n.id = :id and n.username = :username")
    Integer deleteByIdAndUsername(@Param("id") Integer id, @Param("username") String username);
}
