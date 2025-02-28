/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 *
 * @author 123
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique ID of the book", example = "1")
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must be less than 100 characters")
    @Schema(description = "The title of the book", example = "The Great Gatsby")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 100, message = "Author must be less than 100 characters")
    @Schema(description = "The author of the book", example = "F. Scott Fitzgerald")
    private String author;

    @NotNull(message = "Publication year is mandatory")
    @Positive(message = "Publication year must be a positive number")
    @Schema(description = "The publication year of the book", example = "1925")
    private Integer publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    @Schema(description = "The ISBN of the book", example = "9780743273565")
    private String isbn;
}
