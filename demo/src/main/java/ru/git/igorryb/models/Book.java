package ru.git.igorryb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(max = 50)
    private String name;

    @Column(name = "author", nullable = false)
    @NotEmpty
    @Size(max = 50)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY) //
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User bookUser;

}
