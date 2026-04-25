package com.example.demo.model;

/**
 * FacilityStatus defines the operational states of a facility within the system.
 * This is used to track whether a facility is available for use or restricted 
 * due to technical issues or scheduled upkeep.
 */
public enum FacilityStatus {

    /**
     * The facility is fully operational and available for standard use.
     */
    ACTIVE,

    /**
     * The facility is currently unavailable due to an unexpected issue or failure.
     */
    OUT_OF_SERVICE,

    /**
     * The facility is temporarily closed for routine checkups or scheduled repairs.
     */
    MAINTENANCE
}
