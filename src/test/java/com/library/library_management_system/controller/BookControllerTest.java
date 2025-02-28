/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.controller;

import com.library.library_management_system.model.Book;
import com.library.library_management_system.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;

/**
 *
 * @author 123
 */
@SpringBootTest(properties = "spring.security.enabled=false")
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"}) // Mock authentication
    void getAllBooks_ShouldReturnAllBooks() throws Exception {
        // Arrange
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");

        List<Book> books = Arrays.asList(book1, book2);
        when(bookService.getAllBooks()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Book 1"))
            .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void getBookById_ShouldReturnBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        when(bookService.getBookById(1L)).thenReturn(book);

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Sample Book"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void addBook_ShouldReturnCreatedBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("New Book");
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"New Book\", \"author\": \"Author\", \"publicationYear\": 2023, \"isbn\": \"1234567890\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void updateBook_ShouldReturnUpdatedBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");
        when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Book\", \"author\": \"Author\", \"publicationYear\": 2023, \"isbn\": \"1234567890\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void deleteBook_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook(1L); // Mock the void method

        // Act & Assert
        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isNoContent());

        // Verify that the method was called
        verify(bookService, times(1)).deleteBook(1L);
    }
}
