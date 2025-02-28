/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.controller;

import com.library.library_management_system.model.BorrowingRecord;
import com.library.library_management_system.service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author 123
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Borrowing Management", description = "APIs for managing book borrowing and returning")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @Operation(summary = "Borrow a book", description = "Allow a patron to borrow a book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully borrowed the book"),
        @ApiResponse(responseCode = "404", description = "Book or patron not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return ResponseEntity.ok(borrowingService.borrowBook(bookId, patronId));
    }

    @Operation(summary = "Return a book", description = "Record the return of a borrowed book by a patron")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully returned the book"),
        @ApiResponse(responseCode = "404", description = "Borrowing record not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return ResponseEntity.ok(borrowingService.returnBook(bookId, patronId));
    }
}
