package com.example.backuprestore.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "userWorkout")
// Предположим, что имя коллекции MongoDB для пользовательских тренировок - "userWorkout"
public class UserWorkout {
    private ObjectId id;
    private Date startDate;
    private int metricValue;
    private String activityType;
    private String workoutType;
    private int metricTypeId;
    private List<String> hashtags;
    private int userWorkoutLibraryId;
    private int userId;
}