package com.sp.notes.serviceImpl;

import com.sp.notes.model.Note;
import com.sp.notes.repository.NoteRepository;
import com.sp.notes.repository.UserRepository;
import com.sp.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImp implements NoteService {

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Note save(Note note) {
        note.setUsername(this.getUsername());
        if (note.getId() != null) {
            return this.update(note);
        }
        boolean alreadyExists = this.noteRepository.existsByTitle(note.getTitle());
        if (alreadyExists) {
            return null;
        } else {
            return this.noteRepository.save(note);
        }
    }

    private Note update(Note note) {
        Note valid = this.noteRepository.findByIdAndUsername(note.getId(), note.getUsername());
        if (valid != null) {
            return this.noteRepository.save(note);
        }
        return valid;
    }

    @Override
    public Page<Note> findByUsername(Pageable pageable) {
        return this.noteRepository.findByUsername(this.getUsername(), pageable);
    }

    @Override
    public Page<Note> findByCategory(Integer categoryId, Pageable pageable) {
        return this.noteRepository.findByCategoryAndUsername(categoryId, this.getUsername(), pageable);
    }

    @Override
    public Note findById(Integer noteId) {
        return this.noteRepository.findByIdAndUsername(noteId, this.getUsername());
    }

    @Override
    public Integer deleteById(Integer noteId) {
        return this.noteRepository.deleteByIdAndUsername(noteId, this.getUsername());
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
