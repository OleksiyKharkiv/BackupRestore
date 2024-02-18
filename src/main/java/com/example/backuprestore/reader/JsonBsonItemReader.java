package com.example.backuprestore.reader;
import lombok.Setter;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.codecs.Decoder;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.json.JsonReader;
import org.springframework.batch.item.ItemReader;

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

    @SuppressWarnings("unchecked")
    private Iterator<T> readBsonFile() throws IOException {
        List<T> documents = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(bsonFilePath);
             BsonReader bsonReader = new JsonReader(String.valueOf(inputStream))) {

            bsonReader.readStartDocument();
            Decoder<Document> decoder = new DocumentCodec();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) { // Используем BsonType.END_OF_DOCUMENT
                documents.add((T) decoder.decode(bsonReader, DecoderContext.builder().build()));
            }
        }
        return documents.iterator();
    }
}