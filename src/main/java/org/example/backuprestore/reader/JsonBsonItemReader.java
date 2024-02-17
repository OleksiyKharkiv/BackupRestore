package org.example.backuprestore.reader;
import lombok.Setter;
import org.bson.Document;
import org.bson.json.JsonReader;
import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Setter
public class JsonBsonItemReader<T> implements ItemReader<T> {
    private String bsonFilePath; // Путь к BSON файлу
    private Iterator<T> bsonIterator;

    @Override
    public T read() throws Exception {
        if (bsonIterator != null && bsonIterator.hasNext()) {
            return bsonIterator.next();
        }
        return null;
    }

    public void afterPropertiesSet() throws IOException {
        bsonIterator = readBsonFile();
    }

    private Iterator<T> readBsonFile() throws IOException {
        List<T> documents = new ArrayList<>();
        InputStream inputStream = new FileInputStream(new File(bsonFilePath));
        JsonReader jsonReader = new JsonReader(inputStream);
        jsonReader.setTreatEmptyJsonAsError(true); // обработка пустых строк в файле BSON
        while (jsonReader.readBsonDocument() != null) {
            documents.add((T) Document.parse(jsonReader.readBsonDocument().toJson()));
        }
        return documents.iterator();
    }
}