package com.example.backuprestore.mapper;

import com.example.backuprestore.model.UserWorkout;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class UserWorkoutMapper {

    public Document toDocument(UserWorkout userWorkout) {
        Document document = new Document();
        // Здесь осуществляем преобразование полей объекта UserWorkout в документ MongoDB
        document.append("_id", userWorkout.getId());
        document.append("startDate", userWorkout.getStartDate());
        document.append("metricValue", userWorkout.getMetricValue());
        document.append("activityType", userWorkout.getActivityType());
        document.append("workoutType", userWorkout.getWorkoutType());
        document.append("metricTypeId", userWorkout.getMetricTypeId());
        document.append("hashtags", userWorkout.getHashtags());
        document.append("userWorkoutLibraryId", userWorkout.getUserWorkoutLibraryId());
        document.append("userId", userWorkout.getUserId());
        return document;
    }

    public UserWorkout toUserWorkout(Document document) {
        UserWorkout userWorkout = new UserWorkout();
        // Здесь осуществляем преобразование полей документа MongoDB в объект UserWorkout
        userWorkout.setId(document.getObjectId("_id"));
        userWorkout.setStartDate(document.getDate("startDate"));
        userWorkout.setMetricValue(document.getInteger("metricValue"));
        userWorkout.setActivityType(document.getString("activityType"));
        userWorkout.setWorkoutType(document.getString("workoutType"));
        userWorkout.setMetricTypeId(document.getInteger("metricTypeId"));
        userWorkout.setHashtags(document.getList("hashtags", String.class));
        userWorkout.setUserWorkoutLibraryId(document.getInteger("userWorkoutLibraryId"));
        userWorkout.setUserId(document.getInteger("userId"));
        return userWorkout;
    }
}