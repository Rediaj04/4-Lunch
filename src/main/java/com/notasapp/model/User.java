package com.notasapp.model;

import lombok.Data;

/**
 * Clase que representa un usuario en el sistema.
 * Utiliza la anotación @Data de Lombok para generar automáticamente
 * getters, setters, equals, hashCode y toString.
 */
@Data
public class User {
    // El nombre de usuario único para cada usuario
    private String username;

    /**
     * Constructor que inicializa un usuario con su nombre de usuario.
     * @param username El nombre de usuario
     */
    public User(String username) {
        this.username = username;
    }
}