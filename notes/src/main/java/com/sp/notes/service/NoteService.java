package com.sp.notes.service;

import com.sp.notes.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    Note save(Note note);

    Note findById(Integer noteId);

    Integer deleteById(Integer noteId);

    Page<Note> findByUsername(Pageable pageable);

    Page<Note> findByCategory(Integer categoryId, Pageable pageable);

}
