/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.library.library_management_system.service;

import com.library.library_management_system.model.BorrowingRecord;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 123
 */
public interface BorrowingService {
    @Transactional
    BorrowingRecord borrowBook(Long bookId, Long patronId);
    
    @Transactional
    BorrowingRecord returnBook(Long bookId, Long patronId);
}
