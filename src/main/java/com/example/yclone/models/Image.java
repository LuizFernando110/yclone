package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Image extends Media {
    @Column(nullable = false)
    private Long width;

    @Column(nullable = false)
    private Long height;

    @Column(nullable = false)
    private String format;
}
