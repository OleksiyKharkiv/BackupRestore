package org.example.backuprestore.config;
import org.bson.Document;
import org.bson.BsonDocument;
import org.bson.codecs.Decoder;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonBsonItemReader<T> implements ItemReader<T> {
    private String bsonFilePath; // Путь к BSON файлу
    private Iterator<T> bsonIterator;

    public void setBsonFilePath(String bsonFilePath) {
        this.bsonFilePath = bsonFilePath;
    }

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
        DocumentCodec decoder = new DocumentCodec();
        while (true) {
            try {
                BsonDocument document = decoder.decode(inputStream, DecoderContext.builder().build()).toBsonDocument().toBsonDocument();
                if (document != null) {
                    documents.add((T) document);
                } else {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        return documents.iterator();
    }
}