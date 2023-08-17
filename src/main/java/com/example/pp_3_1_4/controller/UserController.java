package com.example.pp_3_1_4.controller;

import com.example.pp_3_1_4.model.Role;
import com.example.pp_3_1_4.model.User;
import com.example.pp_3_1_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String index(Principal principal){
        if(principal == null) return "redirect:/login";
        return "redirect:/logout";
    }

    @GetMapping("/user/{id}")
    public String UserForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal(expression = "username") String username) {
        boolean isAdmin = true;
        if(userService.findUserByEmail(username).getRoles().stream().allMatch(r -> r.getName().equals("USER"))) {
            id = userService.findUserByEmail(username).getId();
            isAdmin = false;
        }
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", isAdmin);
        return "user";
    }

    @GetMapping("/admin")
    public String findAll(Model model, Principal principal, User user, @AuthenticationPrincipal(expression = "username") String username) {
        Long id = userService.findUserByEmail(username).getId();
        User currUser = userService.findById(id).get();
        model.addAttribute("currUser", currUser);
        model.addAttribute("ID", id);
        model.addAttribute("principal", principal);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        List<Role> roles = userService.getRoleRepository().findAll();
        model.addAttribute("allRoles", roles);
        return "admin";
    }

    @PostMapping("/admin/user-create")
    public String createUser(User user){
        if(user.getRoles().size() == 0) {
            user.setRoles(Set.of(userService.getRoleRepository().findRoleByName("USER")));
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PutMapping("admin/user-update")
    public String updateUser(User user){
        if(user.getRoles().size() == 0) {
            user.setRoles(Set.of(userService.getRoleRepository().findRoleByName("USER")));
        }
        userService.save(user);
        return "redirect:/admin";
    }
}