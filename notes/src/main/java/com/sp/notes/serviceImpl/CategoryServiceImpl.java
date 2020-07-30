
package com.sp.notes.serviceImpl;

import com.sp.notes.model.Category;
import com.sp.notes.repository.CategoryRepository;
import com.sp.notes.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (category.getId() != null) {
            return this.update(category, username);
        }
        boolean alreadyExists = this.categoryRepository.existsByName(category.getName());
        if (alreadyExists) {
            return null;
        } else {
            category.setUsername(username);
            return this.categoryRepository.save(category);
        }
    }

    private Category update(Category category, String username) {
        Category valid = this.categoryRepository.findByIdAndUsername(category.getId(), username);
        if (valid != null) {
            valid.setName(category.getName());
            return this.categoryRepository.save(valid);
        }
        return valid;
    }

    @Override
    public List<Category> findByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.categoryRepository.findByUsername(username);
    }

    @Override
    public Category findById(Integer categoryId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.categoryRepository.findByIdAndUsername(categoryId, username);
    }

    @Override
    public Integer deleteById(Integer categoryId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.categoryRepository.deleteByIdAndUsername(categoryId, username);
    }

}
