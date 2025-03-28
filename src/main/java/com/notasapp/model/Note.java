package com.notasapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String title;
    private String content;
    private String status;
    private String userId;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    @Override
    public String toString() {
        return String.format(
            "=== %s === [%s]\n%s\n\nCreado: %s | Actualizado: %s",
            title, status, content, createdAt, updatedAt
        );
    }
}