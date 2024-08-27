package com.company.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "authors")
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String birthDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
    public Author() {
    }

    public Author(Long id, String name, String birthDate, List<Book> books) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.books = books;
    }
}
