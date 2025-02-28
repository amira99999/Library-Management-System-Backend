/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.controller;

import com.library.library_management_system.model.BorrowingRecord;
import com.library.library_management_system.service.BorrowingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author 123
 */
@SpringBootTest(properties = "spring.security.enabled=false")
@AutoConfigureMockMvc
public class BorrowingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingService borrowingService;

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void borrowBook_ShouldReturnBorrowingRecord() throws Exception {
        // Arrange
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);
        when(borrowingService.borrowBook(anyLong(), anyLong())).thenReturn(record);

        // Act & Assert
        mockMvc.perform(post("/api/borrow/1/patron/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void returnBook_ShouldReturnBorrowingRecord() throws Exception {
        // Arrange
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);
        when(borrowingService.returnBook(anyLong(), anyLong())).thenReturn(record);

        // Act & Assert
        mockMvc.perform(put("/api/return/1/patron/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }
}
