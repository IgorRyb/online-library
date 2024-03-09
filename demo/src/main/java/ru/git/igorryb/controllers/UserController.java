package ru.git.igorryb.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.git.igorryb.models.User;
import ru.git.igorryb.services.BookService;
import ru.git.igorryb.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final BookService bookService;

    @GetMapping
    public String index(Model model) {
        checkRole(model);
        model.addAttribute("user", userService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") long id) {
        checkRole(model);
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("books", bookService.findById(id));
        return "people/id";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("person") User user) {
        return "people/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        } else {
            userService.save(user);
            return "redirect:/people";
        }
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        } else {
            userService.update(id, user);
            return "redirect:/people/{id}";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/people";
    }

    public void checkRole(Model model) {
        if (userService.getUserDetails().getUser().getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("admin", true);
        } else {
            model.addAttribute("user", true);
        }
    }
}
