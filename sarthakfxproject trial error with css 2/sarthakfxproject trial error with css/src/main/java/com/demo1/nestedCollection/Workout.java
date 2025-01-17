package com.demo1.nestedCollection;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Workout {
    private String timestamp;
    private Map<String, String> workoutData;

    // Default constructor (required for Firestore deserialization)
    public Workout() {
    }

    // Parameterized constructor
    public Workout(Map<String, String> workoutData) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT); // Correct format

        this.workoutData = workoutData;
    }

    // Getters and Setters
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getWorkoutData() {
        return workoutData;
    }

    public void setWorkoutData(Map<String, String> workoutData) {
        this.workoutData = workoutData;
    }

}