/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.library.library_management_system.service;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.model.*;
import com.library.library_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 *
 * @author 123
 */
@Service
public class BorrowingServiceImpl implements BorrowingService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Override
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
            .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId));

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now());
        return borrowingRecordRepository.save(record);
    }

    @Override
    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository
            .findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
            .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found"));

        record.setReturnDate(LocalDate.now());
        return borrowingRecordRepository.save(record);
    }
}
