package com.nelumbo.parking.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="parkings")
public class ParkingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private Long capacity;
    @Column(name = "cost_x_hour")
    private BigDecimal costPerHour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
