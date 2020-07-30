package com.sp.notes.controller;

import com.sp.notes.model.Category;
import com.sp.notes.model.Note;
import com.sp.notes.service.CategoryService;
import com.sp.notes.service.NoteService;
import com.sp.utils.ModelManager;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note/")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("save")
    public String save(@Valid Note note, BindingResult bindingResult, Model model) {
        String message = null, error = null;
        List<Category> categories = this.categoryService.findByUsername();
        if (bindingResult.hasErrors()) {
            ModelManager.multiChoiceFormModel(model, "note", note, categories, message, error);
        } else {
            note = noteService.save(note);
            if (note != null) {
                message = "Successfully saved";
            } else {
                error = "Note title already exists";
            }
            ModelManager.multiChoiceFormModel(model, "note", new Note(), categories, message, error);
        }
        return "note/add";
    }

    @GetMapping("save")
    public String update(Model model) {
        List<Category> categories = this.categoryService.findByUsername();
        ModelManager.multiChoiceFormModel(model, "note", new Note(), categories, null, null);
        return "note/add";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer noteId, Model model) {
        List<Category> categories = this.categoryService.findByUsername();
        Note note = this.noteService.findById(noteId);
        ModelManager.multiChoiceFormModel(model, "note", note, categories, null, null);
        return "note/add";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer noteId, HttpServletRequest request, Model model) {
        String message = null, error = null;
        Integer count = this.noteService.deleteById(noteId);
        if (count < 1) {
            error = "Something went wrong. Could not delete UserNotes";
        } else {
            message = "Successfully deleted";
        }
        return this.getNotes(request, model, message, error);
    }

    @GetMapping("{id}")
    public String view(@PathVariable("id") Integer noteId, Model model) {
        Note note = this.noteService.findById(noteId);
        model.addAttribute("note", note);
        return "note/view";
    }

    @GetMapping
    public String getNotes(HttpServletRequest request, Model model, String message, String error) {
        List<Category> categories = this.categoryService.findByUsername();
        Pageable pageable = this.getPageableFromRequest(request);
        Page<Note> notes = this.noteService.findByUsername(pageable);
        ModelManager.pageModel(model, "home", notes, categories, message, error);
        return "welcome";
    }

    @GetMapping("category/{id}")
    public String getNotes(@PathVariable("id") Integer categoryId, HttpServletRequest request, Model model, String message, String error) {
        List<Category> categories = this.categoryService.findByUsername();
        Pageable pageable = this.getPageableFromRequest(request);
        Page<Note> notes = this.noteService.findByCategory(categoryId, pageable);
        ModelManager.pageModel(model, "home", notes, categories, message, error);
        model.addAttribute("ctgNo", categoryId);
        return "welcome";
    }

    private Pageable getPageableFromRequest(HttpServletRequest request) {
        int page = 0;
        String rpage = request.getParameter("page");
        if (rpage != null && !rpage.isEmpty()) {
            page = Integer.parseInt(rpage);
        }
        return PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
    }

}
