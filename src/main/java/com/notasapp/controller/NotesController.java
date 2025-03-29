package com.notasapp.controller;

import com.notasapp.model.Note;
import com.notasapp.model.User;
import com.notasapp.service.NotesService;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Scanner;

/**
 * Controlador que maneja la interfaz de usuario por consola.
 * Gestiona la interacción del usuario con la aplicación.
 */
@Controller
public class NotesController {

    // Servicio para gestionar la lógica de las notas
    private final NotesService notesService;
    
    // Scanner para leer la entrada del usuario
    private final Scanner scanner;
    
    // Usuario actualmente logueado en la aplicación
    private User currentUser;

    /**
     * Constructor que inyecta el servicio de notas y crea un scanner.
     * @param notesService El servicio de notas a utilizar
     */
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia la aplicación solicitando el login y mostrando el menú principal.
     */
    public void start() {
        loginUser();
        showMainMenu();
    }

    /**
     * Solicita al usuario que ingrese su nombre de usuario para iniciar sesión.
     */
    private void loginUser() {
        System.out.println("=== INICIO DE SESIÓN ===");
        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();
        this.currentUser = new User(username);
        System.out.println("\n¡Bienvenido, " + username + "!");
    }

    /**
     * Muestra el menú principal y procesa las opciones seleccionadas por el usuario.
     */
    private void showMainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver todas mis notas");
            System.out.println("2. Ver notas por estado");
            System.out.println("3. Salir");
            System.out.print("\nSeleccione una opción: ");

            int option = getIntInput();

            switch (option) {
                case 1:
                    viewAllNotes();
                    break;
                case 2:
                    viewNotesByStatus();
                    break;
                case 3:
                    exit = true;
                    System.out.println("\n¡Hasta pronto, " + currentUser.getUsername() + "!");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    /**
     * Muestra todas las notas del usuario actual.
     */
    private void viewAllNotes() {
        List<Note> notes = notesService.getAllNotesByUser(currentUser.getUsername());

        System.out.println("\n=== TODAS MIS NOTAS ===");
        if (notes.isEmpty()) {
            System.out.println("No tienes ninguna nota creada.");
            return;
        }

        displayNotesList(notes);
    }

    /**
     * Solicita un estado y muestra las notas filtradas por ese estado.
     */
    private void viewNotesByStatus() {
        System.out.println("\n=== VER NOTAS POR ESTADO ===");
        System.out.println("Estados disponibles: Hecho, No hecho, En proceso, En revisión");
        System.out.print("Ingrese el estado a filtrar: ");
        String status = scanner.nextLine();

        List<Note> notes = notesService.getNotesByStatus(currentUser.getUsername(), status);

        System.out.println("\n=== NOTAS - " + status.toUpperCase() + " ===");
        if (notes.isEmpty()) {
            System.out.println("No tienes notas con el estado '" + status + "'.");
            return;
        }

        displayNotesList(notes);
    }

    /**
     * Muestra una lista de notas numeradas y permite seleccionar una para ver sus detalles.
     * @param notes Lista de notas a mostrar
     */
    private void displayNotesList(List<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() + 
                             " [" + notes.get(i).getStatus() + "]");
        }

        System.out.print("\nIngrese el número de la nota para ver detalles (0 para volver): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= notes.size()) {
            System.out.println("\n" + notes.get(choice - 1).toString());
        }
    }

    /**
     * Obtiene un número entero de la entrada del usuario, validando que sea correcto.
     * @return El número entero ingresado
     */
    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return input;
    }
}