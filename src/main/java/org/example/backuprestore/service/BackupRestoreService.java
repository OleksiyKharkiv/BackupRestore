package org.example.backuprestore.service;

import com.example.backuprestore.model.UserWorkout;
import com.example.backuprestore.reader.JsonBsonItemReader;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BackupRestoreService {

    private final JsonBsonItemReader<Document> itemReader;
    private final MongoCollection<UserWorkout> userWorkoutCollection;

    public BackupRestoreService(JsonBsonItemReader<Document> itemReader, MongoCollection<UserWorkout> userWorkoutCollection) {
        this.itemReader = itemReader;
        this.userWorkoutCollection = userWorkoutCollection;
    }

    public void restoreBackupData() {
        itemReader.setBsonFilePath("путь_к_файлу_bson");
        itemReader.setJsonFilePath("путь_к_файлу_json");

        for (Document document : itemReader) {
            UserWorkout userWorkout = convertToUserWorkout(document);
            userWorkoutCollection.insertOne(userWorkout);
        }
    }

    private UserWorkout convertToUserWorkout(Document document) {
        UserWorkout userWorkout = new UserWorkout();
        document.get("indexes", List.class).forEach(index -> {
            Document indexDoc = (Document) index;
            String name = indexDoc.getString("name");
            switch (name) {
                case "_id_":
                    userWorkout.setId(document.getObjectId("_id"));
                    break;
                case "startDate":
                    userWorkout.setStartDate(document.getDate("startDate"));
                    break;
                case "metricValue":
                    userWorkout.setMetricValue(document.getInteger("metricValue"));
                    break;
                case "activityType":
                    userWorkout.setActivityType(document.getString("activityType"));
                    break;
                case "workoutType":
                    userWorkout.setWorkoutType(document.getString("workoutType"));
                    break;
                case "metricTypeId":
                    userWorkout.setMetricTypeId(document.getInteger("metricTypeId"));
                    break;
                case "hashtags":
                    userWorkout.setHashtags(document.getList("hashtags", String.class));
                    break;
                case "userWorkoutLibraryId":
                    userWorkout.setUserWorkoutLibraryId(document.getInteger("userWorkoutLibraryId"));
                    break;
                case "userId":
                    userWorkout.setUserId(document.getInteger("userId"));
                    break;
                default:
                    // Обработка других индексов, если необходимо
                    break;
            }
        });
        // Дополнительное преобразование данных из документа, если необходимо
        return userWorkout;
    }
}