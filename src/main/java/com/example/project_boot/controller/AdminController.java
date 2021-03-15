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

        System.out.println("add User === " + user);
        System.out.println("add role === " + roleId);

        Set<Role> setRole = new HashSet<>();
        setRole.add(userService.getRoleById(roleId));

        user.setRoles(setRole);
        userService.addUser(user);
        return "redirect:/admin/";
    }
//    @GetMapping(value = "/admin/update/{id}")
//    public User updatePage(@PathVariable("id") long id, Model model) {
    @GetMapping(value = "/admin/findOne")
    @ResponseBody
    public User getUser(long id) {
        System.out.println("id === "+id);
//        model.addAttribute("user", userService.getUserById(id));
//        model.addAttribute("roleList", userService.getRoles());
        return userService.getUserById(id);
    }

    @PostMapping(value = "/admin/update")
    public String updateUser(User user) {

        System.out.println("update user ===" + user);

//        Set<Role> role = new HashSet<>();
//
//        for (int i = 0; i < arrId.length; i++){
//            role.add(userService.getRoleById((long) arrId[i]));
//        }
//
//        user.setRoles(role);
//        userService.updateUser(user);
        return "redirect:/admin/";
    }
//    @GetMapping(value = "/admin/add")
//    public String addPage(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("roleList", userService.getRoles());
//        return "addUserPage";
//    }
//    @PostMapping(value = "/admin/add")
//    public String addUser(@ModelAttribute("user") User user,
//                          @RequestParam int[] arrId) {
//
//        Set<Role> role = new HashSet<>();
//
//        for (int i = 0; i < arrId.length; i++){
//            role.add(userService.getRoleById((long) arrId[i]));
//        }
//
//        user.setRoles(role);
//        userService.addUser(user);
//        return "redirect:/admin/";
//    }


    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeById(id);
        return "redirect:/admin/";
    }
}
