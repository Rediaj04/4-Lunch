package com.notasapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario en el sistema.
 * Utiliza la anotación @Data de Lombok para generar automáticamente
 * getters, setters, equals, hashCode y toString.
 */
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    // El nombre de usuario único para cada usuario
    private String username;

    // Lista de estados disponibles para las notas del usuario
    private List<String> availableStatuses;

    /**
     * Constructor que inicializa un usuario con su nombre de usuario.
     * @param username El nombre de usuario
     */
    public User(String username) {
        this.username = username;
        this.availableStatuses = new ArrayList<>();
        // Añadir estados predeterminados
        this.availableStatuses.add("Hecho");
        this.availableStatuses.add("No hecho");
        this.availableStatuses.add("En proceso");
        this.availableStatuses.add("En revisión");
    };

    /**
     * Añade un nuevo estado a la lista de estados disponibles.
     * @param status El nuevo estado a añadir
     * @return true si se añadió el estado, false si ya existía
     */
    public boolean addStatus(String status) {
        if (!availableStatuses.contains(status)) {
            availableStatuses.add(status);
            return true;
        };
        return false;
    };

    /**
     * Elimina un estado de la lista de estados disponibles.
     * @param status El estado a eliminar
     * @return true si se eliminó el estado, false si no existía o no se puede eliminar
     */
    public boolean removeStatus(String status) {
        // No permitir eliminar si quedarían menos de 1 estado
        if (availableStatuses.size() <= 1) {
            return false;
        };
        return availableStatuses.remove(status);
    };

    /**
     * Verifica si un estado está disponible para este usuario.
     * @param status El estado a verificar
     * @return true si el estado está disponible, false en caso contrario
     */
    public boolean isStatusAvailable(String status) {
        return availableStatuses.contains(status);
    };
};