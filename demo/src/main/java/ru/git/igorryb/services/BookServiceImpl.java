package ru.git.igorryb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.git.igorryb.models.Book;
import ru.git.igorryb.models.User;
import ru.git.igorryb.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserService userService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(long id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void takeBook(long id, User user) {
        Optional<Book> takingBook = bookRepository.findById(id);
        takingBook.ifPresent(book -> book.setBookUser(user));
        takingBook.ifPresent(bookRepository::save);
    }

    @Override
    public List<Book> searchBooks(String contain) {
        return bookRepository.findBookByName(contain);
    }

}
