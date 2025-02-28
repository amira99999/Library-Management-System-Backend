/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.controller;

import com.library.library_management_system.model.Patron;
import com.library.library_management_system.service.PatronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 *
 * @author 123
 */
@RestController
@RequestMapping("/api/patrons")
@Tag(name = "Patron Management", description = "APIs for managing patrons in the library")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @Operation(summary = "Get all patrons", description = "Retrieve a list of all patrons in the library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of patrons"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @Operation(summary = "Get a patron by ID", description = "Retrieve details of a specific patron by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the patron"),
        @ApiResponse(responseCode = "404", description = "Patron not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        return ResponseEntity.ok(patronService.getPatronById(id));
    }

    @Operation(summary = "Add a new patron", description = "Add a new patron to the library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully added the patron"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Patron> addPatron(@Valid @RequestBody Patron patron) {
        return ResponseEntity.ok(patronService.addPatron(patron));
    }

    @Operation(summary = "Update a patron", description = "Update an existing patron's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the patron"),
        @ApiResponse(responseCode = "404", description = "Patron not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patron) {
        return ResponseEntity.ok(patronService.updatePatron(id, patron));
    }

    @Operation(summary = "Delete a patron", description = "Remove a patron from the library")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the patron"),
        @ApiResponse(responseCode = "404", description = "Patron not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
