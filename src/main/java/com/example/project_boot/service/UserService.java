package com.example.project_boot.service;


import com.example.project_boot.model.Role;
import com.example.project_boot.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void updateUser(User user);
    void removeById(Long id);
    User getUserById(Long id);
    Role getRoleById(Long id);
    User findByUsername(String username);
    List<User> getUsers();
    List<Role> getRoles();
}
