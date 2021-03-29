package com.example.project_boot.controller;

import com.example.project_boot.model.Role;
import com.example.project_boot.model.User;
import com.example.project_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/authUser")
    public User showUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @GetMapping(value = "/admin/allusers")
    public List<User> allUsers() {
        return userService.getUsers();
    }
    @PostMapping(value = "/admin/addUser", consumes = "application/json")
    public String addUser(@RequestBody Map<String, String> objectJson) {
        char[] charArr = objectJson.get("roles").toCharArray();

        List<Role> lRole = userService.getRoles();
        Set<Role> setRole = new HashSet<>();
        for (Role role : lRole) {
            for (int i = 0; i < charArr.length; i++) {
                Long idRole = Long.valueOf(Character.getNumericValue(charArr[i]));
                if (idRole.equals(role.getId())) {
                    setRole.add(role);
                }
            }
        }

        User user = new User(objectJson.get("username"), Integer.parseInt(objectJson.get("age")), objectJson.get("email"), objectJson.get("password"));
        user.setRoles(setRole);

        userService.editUser(user);
        return "ok";
    }

    @GetMapping(value = "/admin/findOne/{id}")
    public User findOneUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/admin/update", consumes = "application/json")
    public String updateUser(@RequestBody Map<String, String> objectJson) {
        char[] charArr = objectJson.get("roles").toCharArray();

        List<Role> lRole = userService.getRoles();
        Set<Role> setRole = new HashSet<>();
        for (Role role : lRole) {
            for (int i = 0; i < charArr.length; i++) {
                Long idRole = Long.valueOf(Character.getNumericValue(charArr[i]));
                if (idRole.equals(role.getId())) {
                    setRole.add(role);
                }
            }
        }

        User user = new User(objectJson.get("username"), Integer.parseInt(objectJson.get("age")), objectJson.get("email"), objectJson.get("password"));
        user.setId(Long.valueOf(objectJson.get("id")));
        user.setRoles(setRole);

        userService.editUser(user);
        return "ok";
    }

    @PostMapping(value = "/admin/delete", consumes = "application/json")
    public String deleteUser(@RequestBody Map<String, String> objectJson) {
        userService.removeById(Long.valueOf(objectJson.get("id")));
        return "ok";
    }

    @GetMapping(value = "/admin/allRoles")
    public List<Role> allRoles() {
        return userService.getRoles();
    }
}
