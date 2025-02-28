/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.library.library_management_system.repository;

import com.library.library_management_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 123
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
