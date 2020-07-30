package com.sp.notes.service;

import com.sp.notes.model.Category;
import java.util.List;

public interface CategoryService {

    Category save(Category note);

    Category findById(Integer categoryId);
    
    Integer deleteById(Integer categoryId);

    List<Category> findByUsername();

}
