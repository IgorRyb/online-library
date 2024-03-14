package ru.git.igorryb.services;

import ru.git.igorryb.models.Book;
import ru.git.igorryb.models.User;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(long id);

    void save(Book book);

    void update(long id, Book book);

    void delete(long id);

    void takeBook(long id, User user);

    List<Book> searchBooks(String contain);
}
