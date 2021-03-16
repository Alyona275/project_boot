package com.example.project_boot.controller;

import com.example.project_boot.model.Role;
import com.example.project_boot.model.User;
import com.example.project_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String allUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute("authUser", user);
        model.addAttribute("userList", userService.getUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roleList", userService.getRoles());
        return "adminPage";
    }

    @PostMapping(value = "/admin")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam long roleId) {

        Set<Role> setRole = new HashSet<>();
        setRole.add(userService.getRoleById(roleId));

        user.setRoles(setRole);
        userService.editUser(user);
        return "redirect:/admin/";
    }

    @PostMapping(value = "/admin/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam long updRoleId) {

        Set<Role> setRole = new HashSet<>();
        setRole.add(userService.getRoleById(updRoleId));

        user.setRoles(setRole);
        userService.editUser(user);
        return "redirect:/admin/";
    }

    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam long delUserId) {
        userService.removeById(delUserId);
        return "redirect:/admin/";
    }
}
