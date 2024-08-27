package com.company.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int publishedYear;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    public Book() {
    }

    public Book(Long id, String title, int publishedYear, Author author) {
        this.id = id;
        this.title = title;
        this.publishedYear = publishedYear;
        this.author = author;
    }
}
