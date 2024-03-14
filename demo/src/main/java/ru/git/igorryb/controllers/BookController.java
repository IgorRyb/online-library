package ru.git.igorryb.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.git.igorryb.models.Book;
import ru.git.igorryb.models.User;
import ru.git.igorryb.services.BookService;
import ru.git.igorryb.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        checkRole(model);
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") long id, @ModelAttribute("user") User user) {
        checkRole(model);

        model.addAttribute("book", bookService.findById(id));
        if (model.containsAttribute("admin")) {
            model.addAttribute("users", userService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") long id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book, BindingResult bindingResult
            , @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String findBook(@ModelAttribute("pointer") String pointer, Model model) {
        model.addAttribute("pointer", pointer);
        return "books/search";
    }

    @PostMapping("/search")
    public String searchBook(@ModelAttribute("pointer") String pointer, Model model) {
        model.addAttribute("founded", bookService.searchBooks(pointer));
        return "books/search";
    }

    @PatchMapping("/{id}/take")
    public String takeBook(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        bookService.takeBook(id, user);
        return "redirect:/books/" + id;
    }


    public void checkRole(Model model) {
        if (userService.getUserDetails().getUser().getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("admin", true);
        } else {
            model.addAttribute("user", true);
        }
    }
}
