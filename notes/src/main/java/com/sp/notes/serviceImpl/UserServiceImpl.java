package com.sp.notes.serviceImpl;

import com.sp.notes.model.Role;
import com.sp.notes.model.User;
import com.sp.notes.repository.RoleRepository;
import com.sp.notes.repository.UserRepository;
import com.sp.notes.service.UserService;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String save(User user) {
        boolean alreadyExists = this.userRepository.existsByUsername(user.getUsername());
        if (!alreadyExists) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Set<Role> roles = this.getRoles("ROLE_USER");
            if (roles == null) return "00";
            user.setRoles(roles);
            userRepository.save(user);
            return "11";
        }
        return "01";
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private Set<Role> getRoles(String roleName) {
        Set<Role> roles = this.roleRepository.findByName(roleName);
        if (roles.isEmpty()) {
            Role role = this.roleRepository.save(new Role(roleName));
            return (role != null) ? this.getRoles(roleName) : null;
        }
        return roles;
    }
}
