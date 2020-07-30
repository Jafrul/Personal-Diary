package com.sp.notes.controller;

import com.sp.notes.model.Category;
import com.sp.notes.service.CategoryService;
import com.sp.utils.ModelManager;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("save")
    public String save(@Valid Category category, BindingResult bindingResult, Model model) {
        String message = null, error = null;
        List<Category> categories = null;
        if (bindingResult.hasErrors()) {
            categories = this.categoryService.findByUsername();
            ModelManager.formAndListModel(model, "category", category, categories, message, error);
        } else {
            category = categoryService.save(category);
            if (category != null) {
                message = "Successfully saved";
            } else {
                error = "Category already exists";
            }
            categories = this.categoryService.findByUsername();
            ModelManager.formAndListModel(model, "category", new Category(), categories, message, error);
        }
        return "category/manage";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer categoryId, Model model) {
        List<Category> categories = this.categoryService.findByUsername();
        Category category = this.categoryService.findById(categoryId);
        ModelManager.formAndListModel(model, "category", category, categories, null, null);
        return "category/manage";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer categoryId, Model model) {
        String message = null, error = null;
        Integer count = this.categoryService.deleteById(categoryId);
        if (count < 1) {
            error = "Something went wrong. Could not delete Category";
        } else {
            message = "Successfully deleted";
        }
        List<Category> categories = this.categoryService.findByUsername();
        ModelManager.formAndListModel(model, "category", new Category(), categories, message, error);
        return "category/manage";
    }

    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = this.categoryService.findByUsername();
        ModelManager.formAndListModel(model, "category", new Category(), categories, null, null);
        return "category/manage";
    }

}
