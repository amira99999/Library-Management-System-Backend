/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.service;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.model.Patron;
import com.library.library_management_system.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author 123
 */
public class PatronServiceImplTest {
    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatrons_ShouldReturnAllPatrons() {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("Patron 1");

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Patron 2");

        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron1, patron2));

        // Act
        List<Patron> patrons = patronService.getAllPatrons();

        // Assert
        assertEquals(2, patrons.size());
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    void getPatronById_ShouldReturnPatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Sample Patron");
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        // Act
        Patron result = patronService.getPatronById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Sample Patron", result.getName());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void getPatronById_ShouldThrowException() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> patronService.getPatronById(1L));
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void addPatron_ShouldReturnSavedPatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setName("New Patron");
        when(patronRepository.save(patron)).thenReturn(patron);

        // Act
        Patron result = patronService.addPatron(patron);

        // Assert
        assertNotNull(result);
        assertEquals("New Patron", result.getName());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void updatePatron_ShouldReturnUpdatedPatron() {
        // Arrange
        Patron existingPatron = new Patron();
        existingPatron.setId(1L);
        existingPatron.setName("Old Patron");

        Patron updatedPatron = new Patron();
        updatedPatron.setName("Updated Patron");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(existingPatron)).thenReturn(existingPatron);

        // Act
        Patron result = patronService.updatePatron(1L, updatedPatron);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Patron", result.getName());
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).save(existingPatron);
    }

    @Test
    void deletePatron_ShouldDeletePatron() {
        // Arrange
        when(patronRepository.existsById(1L)).thenReturn(true);

        // Act
        patronService.deletePatron(1L);

        // Assert
        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePatron_ShouldThrowException() {
        // Arrange
        when(patronRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(1L));
        verify(patronRepository, times(1)).existsById(1L);
    }
}
