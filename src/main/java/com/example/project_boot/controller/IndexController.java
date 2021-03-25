package com.example.project_boot.controller;

import com.example.project_boot.model.Role;
import com.example.project_boot.model.User;
import com.example.project_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class IndexController {

    private UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String adminPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute("authUser", user);
        return "adminPage";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", userService.getRoles());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registrationUser(@Validated User user,
                                   @RequestParam int[] arrId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<Role> role = new HashSet<>();

        for (int i = 0; i < arrId.length; i++) {
            role.add(userService.getRoleById((long) arrId[i]));
        }
        user.setRoles(role);
        userService.editUser(user);

        return "redirect:/";
    }
}
