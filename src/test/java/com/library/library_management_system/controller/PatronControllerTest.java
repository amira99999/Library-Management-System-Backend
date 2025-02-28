/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.controller;

import com.library.library_management_system.model.Patron;
import com.library.library_management_system.service.PatronService;
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
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author 123
 */
@SpringBootTest(properties = "spring.security.enabled=false")
@AutoConfigureMockMvc
public class PatronControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void getAllPatrons_ShouldReturnAllPatrons() throws Exception {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("Patron 1");

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Patron 2");

        List<Patron> patrons = Arrays.asList(patron1, patron2);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        // Act & Assert
        mockMvc.perform(get("/api/patrons"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Patron 1"))
            .andExpect(jsonPath("$[1].name").value("Patron 2"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void getPatronById_ShouldReturnPatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Sample Patron");
        when(patronService.getPatronById(1L)).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(get("/api/patrons/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Sample Patron"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void addPatron_ShouldReturnCreatedPatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("New Patron");
        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"New Patron\", \"phoneNumber\": \"+1234567890\", \"address\": \"123 Main St\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("New Patron"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void updatePatron_ShouldReturnUpdatedPatron() throws Exception {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Updated Patron");
        when(patronService.updatePatron(any(Long.class), any(Patron.class))).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(put("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Patron\", \"phoneNumber\": \"+1234567890\", \"address\": \"123 Main St\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Patron"));
    }

    @Test
    @WithMockUser(username = "librarian", roles = {"LIBRARIAN"})
    void deletePatron_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(patronService).deletePatron(1L); // Mock the void method

        // Act & Assert
        mockMvc.perform(delete("/api/patrons/1"))
            .andExpect(status().isNoContent());

        // Verify that the method was called
        verify(patronService, times(1)).deletePatron(1L);
    }
}
