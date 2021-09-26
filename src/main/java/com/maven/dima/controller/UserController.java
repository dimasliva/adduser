package com.maven.dima.controller;

import com.maven.dima.model.User;
import com.maven.dima.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class UserController {
    private UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String findAll(Model model) {
        Iterable<User> title = userRepo.findAll();
        model.addAttribute("title", title);
        return "index";
    }
    @GetMapping("/blog")
    public String findBlog(Model model) {
        model.addAttribute("title", userRepo.findAll());
        return "pages/blog";
    }
    @GetMapping("/blog/{id}")
    public String blogId(@PathVariable Long id, Model model) {
        if(!userRepo.existsById(id)){
            return "redirect:/";
        }

        Optional<User> user = userRepo.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "pages/blog-details";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "pages/blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAdd(Model model, @RequestParam String firstName, @RequestParam String lastName) {
        User user = new User(firstName, lastName);
        userRepo.save(user);
        return "redirect:/";
    }
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable Long id, Model model) {
        if(!userRepo.existsById(id)){
            return "redirect:/";
        }

        Optional<User> user = userRepo.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "/pages/blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") Long id, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepo.save(user);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") Long id, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        userRepo.delete(user);
        return "redirect:/blog";
    }
}
