package com.notasapp;

import com.notasapp.controller.NotesController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Clase principal que inicia la aplicación Spring Boot.
 * Punto de entrada para la ejecución de la aplicación.
 */
@SpringBootApplication
public class Main {
    /**
     * Método principal que arranca la aplicación y obtiene el controlador
     * de notas para iniciar la interfaz de usuario.
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Iniciar el contexto de Spring
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        
        // Obtener el controlador de notas del contexto de Spring
        NotesController notesController = context.getBean(NotesController.class);
        
        // Iniciar la interfaz de usuario
        notesController.start();
    }
}