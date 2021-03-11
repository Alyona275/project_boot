package com.example.project_boot.controller;

import com.example.project_boot.model.Role;
import com.example.project_boot.model.User;
import com.example.project_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String allUsers(Model model) {
        model.addAttribute("userList", userService.getUsers());
        return "users";
    }

    @GetMapping(value = "/admin/update/{id}")
    public String updatePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roleList", userService.getRoles());
        return "updatePage";
    }
    @PostMapping(value = "/admin/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam int[] arrId) {

        Set<Role> role = new HashSet<>();

        for (int i = 0; i < arrId.length; i++){
            role.add(userService.getRoleById((long) arrId[i]));
        }

        user.setRoles(role);
        userService.updateUser(user);
        return "redirect:/admin/";
    }
    @GetMapping(value = "/admin/add")
    public String addPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", userService.getRoles());
        return "addUserPage";
    }
    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam int[] arrId) {

        Set<Role> role = new HashSet<>();

        for (int i = 0; i < arrId.length; i++){
            role.add(userService.getRoleById((long) arrId[i]));
        }

        user.setRoles(role);
        userService.addUser(user);
        return "redirect:/admin/";
    }


    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeById(id);
        return "redirect:/admin/";
    }
}
