package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a physical facility within the system.
 * Uses Lombok for boilerplate reduction and JPA for ORM mapping.
 */
@Entity
@Table(name = "facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {

    /**
     * Unique identifier for the facility, auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the facility (e.g., "Conference Room A"). Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The category or type of facility (e.g., "Gym", "Lab"). Cannot be null.
     */
    @Column(nullable = false)
    private String type;

    /**
     * The maximum number of people the facility can accommodate.
     */
    private Integer capacity;

    /**
     * Physical address or room number of the facility.
     */
    private String location;

    /**
     * Detailed information about the facility stored as a large text block.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Current operational status, persisted as a String in the database.
     */
    @Enumerated(EnumType.STRING)
    private FacilityStatus status;

    /**
     * String representation of when the facility is open or available.
     */
    private String availabilitySchedule;

    /**
     * URL path to a hosted image representing the facility.
     */
    private String imageUrl;

    /**
     * Relationship to the Booking entity.
     * @JsonIgnore prevents infinite recursion during JSON serialization.
     * Cascade settings ensure facility-related booking persistence is handled.
     */
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "facility", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Timestamp indicating when the record was first created.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last time the record was modified.
     */
    private LocalDateTime updatedAt;

    /**
     * Lifecycle hook executed before the entity is saved to the database.
     * Sets timestamps and provides a default status of ACTIVE if none is provided.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = FacilityStatus.ACTIVE;
        }
    }

    /**
     * Lifecycle hook executed before the entity is updated.
     * Refreshes the updatedAt timestamp.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}