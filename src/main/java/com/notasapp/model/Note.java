package com.notasapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Clase que representa una nota en el sistema.
 * Se almacena en la colección "notes" de MongoDB.
 * Utiliza la anotación @Data de Lombok para generar automáticamente
 * getters, setters, equals, hashCode y toString.
 */
@Data
@Document(collection = "notes")
public class Note {
    // Identificador único de la nota
    @Id
    private String id;

    // Título de la nota
    private String title;

    // Contenido detallado de la nota
    private String content;

    // Estado actual de la nota (Hecho, No hecho, En proceso, En revisión)
    private String status;

    // ID del usuario propietario de la nota
    private String userId;

    // Fecha y hora de creación de la nota
    private Date createdAt = new Date();

    // Fecha y hora de la última actualización de la nota
    private Date updatedAt = new Date();

    /**
     * Sobrescribe el método toString para mostrar la nota en un formato legible.
     * @return Representación en texto de la nota con formato
     */
    @Override
    public String toString() {
        return "=== %s === [%s]\n%s\n\nCreado: %s | Actualizado: %s".formatted(
                title, status, content, createdAt, updatedAt
        );
    };
};