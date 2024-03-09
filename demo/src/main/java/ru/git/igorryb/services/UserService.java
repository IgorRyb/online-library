package ru.git.igorryb.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.git.igorryb.models.Book;
import ru.git.igorryb.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    Optional<User> findByUsername(String name);

    void save(User user);

    void update(long id, User user);

    void delete(long id);

    List<User> findByName(String contain);

    List<Book> getBooks(long id);

    UserDetailsImpl getUserDetails();
}
