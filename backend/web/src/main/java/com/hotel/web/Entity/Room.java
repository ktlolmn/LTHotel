package com.hotel.web.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String description;
    
    @Column(nullable = false)
    private boolean status;

    public Room() {
    }

    public Room(String name, double price, String type, String description, boolean status) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.status = status;
    }
    
}
