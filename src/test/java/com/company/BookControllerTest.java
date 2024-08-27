package com.company;

import com.company.controller.BookController;
import com.company.entity.Author;
import com.company.entity.Book;
import com.company.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private Author author;

    @BeforeEach
    public void setup() {
        author = new Author(1L, "Author Name", "1997", null);
        book = new Book(1L, "Book Title", 2022, author);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Page<Book> books = new PageImpl<>(Arrays.asList(book));
        Pageable pageable = PageRequest.of(0, 10);

        when(bookService.findAll(any(Pageable.class))).thenReturn(books);

        mockMvc.perform(get("/api/v1/books")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value(book.getTitle()));
    }

    @Test
    public void testGetBookById() throws Exception {
        when(bookService.findById(book.getId())).thenReturn(book);

        mockMvc.perform(get("/api/v1/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void testUpdateBook() throws Exception {
        when(bookService.update(Mockito.anyLong(), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/v1/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}