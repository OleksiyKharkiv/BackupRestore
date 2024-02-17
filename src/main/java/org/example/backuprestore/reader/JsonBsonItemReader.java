package org.example.backuprestore.reader;

import lombok.Setter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
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
    public T read() {
        if (bsonIterator != null && bsonIterator.hasNext()) {
            return bsonIterator.next();
        }
        return null;
    }

    private Iterator<T> readBsonFile() throws IOException {
        List<T> documents = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(new File(bsonFilePath));
             JsonReader jsonReader = new JsonReader(String.valueOf(inputStream))) {

            while (jsonReader.readStartDocument()) {
                DocumentCodec codec = new DocumentCodec();
                Document document = codec.decode(jsonReader, DecoderContext.builder().build());
                documents.add((T) document);
            }
        }
        return documents.iterator();
    }
}