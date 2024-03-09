package ru.git.igorryb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    @Size(max = 50)
    private String name;

    @Column(name = "username")
    @Size(max = 30)
    private String username;

    @Column(name = "password")
    @Size(min = 5, max = 30)
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> reservedBooks;
}
