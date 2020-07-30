package com.sp.notes.service;

import com.sp.notes.model.User;

public interface UserService {
    String save(User user);

    User findByUsername(String username);
}
