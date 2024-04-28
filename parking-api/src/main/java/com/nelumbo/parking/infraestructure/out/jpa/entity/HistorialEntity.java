package com.nelumbo.parking.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="historial")
public class HistorialEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "entry_date")
    private LocalDateTime entryDate;
    @Column(name = "departure_date")
    private LocalDateTime departureDate;
    private BigDecimal payment;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private ParkingEntity parking;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;
}
