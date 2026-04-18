package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingDTO {
    private Long facilityId;  // ✅ Instead of resourceType and resourceName
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private Integer expectedAttendees;
}