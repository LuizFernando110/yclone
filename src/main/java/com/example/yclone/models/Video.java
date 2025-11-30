package com.example.yclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Video extends Media{
    @Column(nullable = false)
    private Double duration;

    @Column(nullable = false)
    private String resolution;

    @Column(nullable = false)
    private String codec;
}
