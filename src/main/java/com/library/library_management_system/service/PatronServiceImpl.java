/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.library.library_management_system.service;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.model.Patron;
import com.library.library_management_system.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author 123
 */
@Service
public class PatronServiceImpl implements PatronService {
    @Autowired
    private PatronRepository patronRepository;

    @Override
    @Cacheable("patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    @Cacheable(value = "patrons", key = "#id")
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
    }

    @Override
    @CachePut(value = "patrons", key = "#patron.id")
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    @CachePut(value = "patrons", key = "#id")
    public Patron updatePatron(Long id, Patron patron) {
        Patron existingPatron = patronRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        existingPatron.setName(patron.getName());
        existingPatron.setPhoneNumber(patron.getPhoneNumber());
        existingPatron.setAddress(patron.getAddress());
        return patronRepository.save(existingPatron);
    }

    @Override
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patron not found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}
