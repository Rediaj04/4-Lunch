package com.notasapp.controller;

import com.notasapp.model.Note;
import com.notasapp.model.User;
import com.notasapp.service.NotesService;
import com.notasapp.service.UserService;
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

    // Servicio para gestionar la lógica de usuarios
    private final UserService userService;

    // Scanner para leer la entrada del usuario
    private final Scanner scanner;

    // Usuario actualmente logueado en la aplicación
    private User currentUser;

    /**
     * Constructor que inyecta los servicios necesarios y crea un scanner.
     *
     * @param notesService El servicio de notas a utilizar
     * @param userService  El servicio de usuarios a utilizar
     */
    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia la aplicación solicitando el login y mostrando el menú principal.
     */
    public void start() {
        loginUser();
        showMainMenu();
    };

    /**
     * Solicita al usuario que ingrese su nombre de usuario para iniciar sesión.
     */
    private void loginUser() {
        System.out.println("=== INICIO DE SESIÓN ===");
        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();

        // Obtener o crear usuario
        this.currentUser = userService.getOrCreateUser(username);

        System.out.println("\n¡Bienvenido, " + username + "!");
    };

    /**
     * Muestra el menú principal y procesa las opciones seleccionadas por el
     * usuario.
     */
    private void showMainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver todas mis notas");
            System.out.println("2. Ver notas por estado");
            System.out.println("3. Crear nueva nota");
            System.out.println("4. Administrar estados");
            System.out.println("5. Salir");
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
                    createNewNote();
                    break;
                case 4:
                    manageStatuses();
                    break;
                case 5:
                    exit = true;
                    System.out.println("\n¡Hasta pronto, " + currentUser.getUsername() + "!");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            };
        };
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
        };

        displayNotesList(notes);
    };

    /**
     * Solicita un estado y muestra las notas filtradas por ese estado.
     */
    private void viewNotesByStatus() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== VER NOTAS POR ESTADO ===");

            // Mostrar estados disponibles
            List<String> availableStatuses = currentUser.getAvailableStatuses();
            System.out.println("Estados disponibles:");
            for (int i = 0; i < availableStatuses.size(); i++) {
                System.out.println((i + 1) + ". " + availableStatuses.get(i));
            };

            System.out.println("5. Cancelar");

            System.out.print("\nSeleccione una opción: ");
            int option = getIntInput();

            switch (option) {
                case 5:
                    // Opción para volver al menú principal
                    back = true;
                    break;
                default:
                    // Verificar que la opción corresponda a un estado válido
                    if (option >= 1 && option <= availableStatuses.size()) {
                        String status = availableStatuses.get(option - 1);
                        List<Note> notes = notesService.getNotesByStatus(currentUser.getUsername(), status);

                        System.out.println("\n=== NOTAS - " + status.toUpperCase() + " ===");
                        if (notes.isEmpty()) {
                            System.out.println("No tienes notas con el estado '" + status + "'.");
                            System.out.println("\nPresiona Enter para continuar...");
                            scanner.nextLine();
                        } else {
                            displayNotesList(notes);
                        }
                    } else {
                        System.out.println("\nOpción no válida.");
                        System.out.println("\nPresiona Enter para continuar...");
                        scanner.nextLine();
                    }
                    break;
            };
        };
    };

    /**
     * Permite al usuario crear una nueva nota.
     */
    private void createNewNote() {
        System.out.println("\n=== CREAR NUEVA NOTA ===");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Contenido: ");
        String content = scanner.nextLine();

        // Seleccionar estado usando switch case
        String status = selectNoteStatus();
        if (status == null) {
            return; // Se canceló la creación de la nota
        }

        Note newNote = notesService.createNote(currentUser.getUsername(), title, content, status);

        if (newNote != null) {
            System.out.println("\nNota creada con éxito:");
            System.out.println(newNote);
        } else {
            System.out.println("\nError al crear la nota.");
        }
    }

    /**
     * Permite al usuario seleccionar un estado para una nota de su lista de estados
     * disponibles.
     *
     * @return El estado seleccionado o null si se cancela
     */
    private String selectNoteStatus() {
        System.out.println("\nSeleccione un estado para la nota:");

        // Mostrar estados disponibles numerados
        List<String> availableStatuses = currentUser.getAvailableStatuses();
        for (int i = 0; i < availableStatuses.size(); i++) {
            System.out.println((i + 1) + ". " + availableStatuses.get(i));
        };

        System.out.println("0. Cancelar");

        System.out.print("\nOpción: ");
        int option = getIntInput();

        switch (option) {
            case 0:
                System.out.println("\nCreación de nota cancelada.");
                return null;
            default:
                if (option >= 1 && option <= availableStatuses.size()) {
                    return availableStatuses.get(option - 1);
                } else {
                    System.out.println("\nOpción no válida. Intente nuevamente.");
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    return selectNoteStatus(); // Recursión para volver a intentar
                }
        }
    };

    /**
     * Muestra el menú para administrar los estados de las notas.
     */
    private void manageStatuses() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== ADMINISTRAR ESTADOS ===");

            // Mostrar estados actuales
            List<String> statuses = currentUser.getAvailableStatuses();
            System.out.println("Estados actuales:");
            for (int i = 0; i < statuses.size(); i++) {
                System.out.println((i + 1) + ". " + statuses.get(i));
            }

            System.out.println("\n1. Añadir nuevo estado");
            System.out.println("2. Eliminar estado existente");
            System.out.println("3. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");

            int option = getIntInput();

            switch (option) {
                case 1:
                    addNewStatus();
                    break;
                case 2:
                    removeStatus();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            };
        };
    };

    /**
     * Permite al usuario añadir un nuevo estado a su lista.
     */
    private void addNewStatus() {
        System.out.print("\nIngrese el nombre del nuevo estado: ");
        String newStatus = scanner.nextLine();

        boolean added = userService.addStatusToUser(currentUser.getUsername(), newStatus);

        if (added) {
            System.out.println("\nEstado '" + newStatus + "' añadido con éxito.");
        } else {
            System.out.println("\nEl estado '" + newStatus + "' ya existe en tu lista.");
        };
    };

    /**
     * Permite al usuario eliminar un estado de su lista.
     */
    private void removeStatus() {
        // Mostrar estados actuales para seleccionar
        List<String> statuses = currentUser.getAvailableStatuses();

        if (statuses.size() <= 1) {
            System.out.println("\nNo puedes eliminar más estados. Debe existir al menos uno.");
            return;
        };

        System.out.println("\nSeleccione el estado a eliminar:");
        for (int i = 0; i < statuses.size(); i++) {
            System.out.println((i + 1) + ". " + statuses.get(i));
        };

        System.out.print("Número de estado: ");
        int choice = getIntInput();

        if (choice < 1 || choice > statuses.size()) {
            System.out.println("\nOpción no válida.");
            return;
        };

        String statusToRemove = statuses.get(choice - 1);
        boolean removed = userService.removeStatusFromUser(currentUser.getUsername(), statusToRemove);

        if (removed) {
            System.out.println("\nEstado '" + statusToRemove + "' eliminado con éxito.");
        } else {
            System.out.println("\nNo se pudo eliminar el estado '" + statusToRemove + "'.");
        };
    };

    /**
     * Muestra una lista de notas numeradas.
     *
     * @param notes Lista de notas a mostrar
     */
    private void displayNotesList(List<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() +
                    " [" + notes.get(i).getStatus() + "]");
        };

        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    };

    /**
     * Obtiene un número entero de la entrada del usuario, validando que sea
     * correcto.
     *
     * @return El número entero ingresado
     */
    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        };
        int input = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return input;
    };
};