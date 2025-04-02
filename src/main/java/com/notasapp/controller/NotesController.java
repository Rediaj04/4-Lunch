package com.notasapp.controller;

import com.notasapp.model.Note;
import com.notasapp.model.User;
import com.notasapp.service.NotesService;
import com.notasapp.service.UserService;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

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
            System.out.println("\n===========================================");
            System.out.println("             MENÚ PRINCIPAL");
            System.out.println("===========================================");
            System.out.println("0. Salir");
            System.out.println("1. Ver todas mis notas");
            System.out.println("2. Ver notas por estado");
            System.out.println("3. Crear nueva nota");
            System.out.println("4. Editar mis notas");
            System.out.println("5. Eliminar mis notas");
            System.out.println("6. Administrar estados");
            System.out.print("\nSeleccione una opción: ");

            int option = getIntInput();

            switch (option) {
                case 0:
                    exit = true;
                    System.out.println("\n¡Hasta pronto, " + currentUser.getUsername() + "!");
                    break;
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
                    editNote();
                    break;
                case 5:
                    deleteNote();
                    break;
                case 6:
                    manageStatuses();
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
            }
        }
        scanner.close();
    }

    /**
     * Muestra todas las notas del usuario actual.
     */
    private void viewAllNotes() {
        System.out.println("\n===========================================");
        System.out.println("             TODAS MIS NOTAS");
        System.out.println("===========================================");
        
        List<Note> notes = notesService.getAllNotesByUser(currentUser.getUsername());

        if (notes.isEmpty()) {
            System.out.println("No tienes ninguna nota creada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        displayNotesList(notes);
        
        System.out.println("\n0. Volver al menú principal");
        System.out.println("Seleccione el número de nota para ver su detalle: ");
        int option = getIntInput();

        if (option == 0) {
            System.out.println("\nVolviendo al menú principal...");
            return;
        }

        if (option >= 1 && option <= notes.size()) {
            Note selectedNote = notes.get(option - 1);
            displayNoteDetail(selectedNote);
        } else {
            System.out.println("\nOpción no válida.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Muestra el detalle completo de una nota.
     *
     * @param note La nota a mostrar en detalle
     */
    private void displayNoteDetail(Note note) {
        System.out.println("\n===========================================");
        System.out.println("           DETALLE DE LA NOTA");
        System.out.println("===========================================");
        System.out.println("Título: " + note.getTitle());
        System.out.println("Estado: " + note.getStatus());
        System.out.println("Fecha de creación: " + note.getCreatedAt());
        System.out.println("Última actualización: " + note.getUpdatedAt());
        System.out.println("\nContenido:");
        System.out.println(note.getContent());
    }

    /**
     * Solicita un estado y muestra las notas filtradas por ese estado.
     */
    private void viewNotesByStatus() {
        boolean back = false;

        while (!back) {
            System.out.println("\n===========================================");
            System.out.println("           VER NOTAS POR ESTADO");
            System.out.println("===========================================");
            
            List<String> availableStatuses = currentUser.getAvailableStatuses();
            System.out.println("0. Volver al menú principal");
            System.out.println("\nEstados disponibles:");
            for (int i = 0; i < availableStatuses.size(); i++) {
                System.out.println((i + 1) + ". " + availableStatuses.get(i));
            }

            System.out.print("\nSeleccione una opción: ");
            int option = getIntInput();

            if (option == 0) {
                back = true;
                System.out.println("\nVolviendo al menú principal...");
                break;
            }

            if (option >= 1 && option <= availableStatuses.size()) {
                String status = availableStatuses.get(option - 1);
                List<Note> notes = notesService.getNotesByStatus(currentUser.getUsername(), status);

                System.out.println("\n===========================================");
                System.out.println("           NOTAS - " + status.toUpperCase());
                System.out.println("===========================================");
                
                if (notes.isEmpty()) {
                    System.out.println("No tienes notas con el estado '" + status + "'.");
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                    continue;
                }

                displayNotesList(notes);
                
                System.out.println("\n0. Volver a la lista de estados");
                System.out.println("Seleccione el número de nota para ver su detalle: ");
                int noteOption = getIntInput();

                if (noteOption == 0) {
                    System.out.println("\nVolviendo a la lista de estados...");
                    continue;
                }

                if (noteOption >= 1 && noteOption <= notes.size()) {
                    Note selectedNote = notes.get(noteOption - 1);
                    displayNoteDetail(selectedNote);
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                } else {
                    System.out.println("\nOpción no válida.");
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
                }
            } else {
                System.out.println("\nOpción no válida. Por favor, intente nuevamente.");
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
    }

    /**
     * Permite al usuario crear una nueva nota.
     */
    private void createNewNote() {
        System.out.println("\n===========================================");
        System.out.println("           CREAR NUEVA NOTA");
        System.out.println("===========================================");

        System.out.println("0. Cancelar creación");
        System.out.print("\nTítulo: ");
        String title = scanner.nextLine().trim();
        if (title.equals("0")) {
            System.out.println("\nCreación de nota cancelada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }
        if (title.isEmpty()) {
            System.out.println("\nEl título no puede estar vacío.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.print("Contenido: ");
        String content = scanner.nextLine().trim();
        if (content.equals("0")) {
            System.out.println("\nCreación de nota cancelada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }
        if (content.isEmpty()) {
            System.out.println("\nEl contenido no puede estar vacío.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\nSeleccione un estado para la nota:");
        List<String> availableStatuses = currentUser.getAvailableStatuses();
        System.out.println("0. Cancelar creación");
        for (int i = 0; i < availableStatuses.size(); i++) {
            System.out.println((i + 1) + ". " + availableStatuses.get(i));
        }

        System.out.print("\nOpción: ");
        int option = getIntInput();

        if (option == 0) {
            System.out.println("\nCreación de nota cancelada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (option >= 1 && option <= availableStatuses.size()) {
            String status = availableStatuses.get(option - 1);
            Note newNote = notesService.createNote(currentUser.getUsername(), title, content, status);

            if (newNote != null) {
                System.out.println("\nNota creada con éxito:");
                System.out.println(newNote);
            } else {
                System.out.println("\nError al crear la nota.");
            }
        } else {
            System.out.println("\nOpción no válida.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Muestra el menú para administrar los estados de las notas.
     */
    private void manageStatuses() {
        boolean back = false;

        while (!back) {
            System.out.println("\n===========================================");
            System.out.println("           ADMINISTRAR ESTADOS");
            System.out.println("===========================================");

            List<String> statuses = currentUser.getAvailableStatuses();
            System.out.println("0. Volver al menú principal");
            System.out.println("\nEstados actuales:");
            for (int i = 0; i < statuses.size(); i++) {
                System.out.println((i + 1) + ". " + statuses.get(i));
            }

            System.out.println("\n1. Añadir nuevo estado");
            System.out.println("2. Eliminar estado existente");
            System.out.print("\nSeleccione una opción: ");

            int option = getIntInput();

            switch (option) {
                case 0:
                    back = true;
                    System.out.println("\nVolviendo al menú principal...");
                    break;
                case 1:
                    addNewStatus();
                    break;
                case 2:
                    removeStatus();
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, intente nuevamente.");
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
            }
        }
    }

    /**
     * Permite al usuario añadir un nuevo estado a su lista.
     */
    private void addNewStatus() {
        System.out.println("\n===========================================");
        System.out.println("           AÑADIR NUEVO ESTADO");
        System.out.println("===========================================");
        
        System.out.print("Ingrese el nombre del nuevo estado: ");
        String newStatus = scanner.nextLine().trim();
        
        if (newStatus.isEmpty()) {
            System.out.println("\nEl estado no puede estar vacío.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (userService.addStatusToUser(currentUser.getUsername(), newStatus)) {
            System.out.println("\nEstado '" + newStatus + "' añadido con éxito.");
            // Actualizar el usuario actual con los nuevos estados
            currentUser = userService.getOrCreateUser(currentUser.getUsername());
        } else {
            System.out.println("\nEl estado '" + newStatus + "' ya existe.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Permite al usuario eliminar un estado de su lista.
     */
    private void removeStatus() {
        System.out.println("\n===========================================");
        System.out.println("           ELIMINAR ESTADO");
        System.out.println("===========================================");
        
        List<String> statuses = currentUser.getAvailableStatuses();

        if (statuses.size() <= 1) {
            System.out.println("\nNo puedes eliminar más estados. Debe existir al menos uno.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("\nSeleccione el estado a eliminar:");
        for (int i = 0; i < statuses.size(); i++) {
            System.out.println((i + 1) + ". " + statuses.get(i));
        }

        System.out.print("\nNúmero de estado: ");
        int choice = getIntInput();

        if (choice < 1 || choice > statuses.size()) {
            System.out.println("\nOpción no válida.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        String statusToRemove = statuses.get(choice - 1);
        
        // Verificar si hay notas usando este estado
        List<Note> notesWithStatus = notesService.getNotesByStatus(currentUser.getUsername(), statusToRemove);
        if (!notesWithStatus.isEmpty()) {
            System.out.println("\nNo se puede eliminar el estado '" + statusToRemove + "' porque hay " + 
                             notesWithStatus.size() + " nota(s) que lo están usando.");
            System.out.println("Por favor, cambie el estado de estas notas antes de eliminarlo.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (userService.removeStatusFromUser(currentUser.getUsername(), statusToRemove)) {
            System.out.println("\nEstado '" + statusToRemove + "' eliminado con éxito.");
            // Actualizar el usuario actual con los nuevos estados
            currentUser = userService.getOrCreateUser(currentUser.getUsername());
        } else {
            System.out.println("\nNo se pudo eliminar el estado. Asegúrese de tener al menos un estado disponible.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Muestra una lista de notas numeradas.
     *
     * @param notes Lista de notas a mostrar
     */
    private void displayNotesList(List<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i).getTitle() +
                    " [" + notes.get(i).getStatus() + "]");
        }
    }

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

    /**
     * Permite al usuario editar una nota existente.
     */
    private void editNote() {
        System.out.println("\n===========================================");
        System.out.println("             EDITAR NOTA");
        System.out.println("===========================================");
        
        List<Note> notes = notesService.getAllNotesByUser(currentUser.getUsername());

        if (notes.isEmpty()) {
            System.out.println("No tienes ninguna nota para editar.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("0. Volver al menú principal");
        System.out.println("\nSeleccione la nota a editar:");
        displayNotesList(notes);
        
        System.out.print("\nOpción: ");
        int option = getIntInput();

        if (option == 0) {
            System.out.println("\nVolviendo al menú principal...");
            return;
        }

        if (option >= 1 && option <= notes.size()) {
            Note selectedNote = notes.get(option - 1);
            displayNoteDetail(selectedNote);

            System.out.println("\n¿Qué desea editar?");
            System.out.println("1. Título");
            System.out.println("2. Contenido");
            System.out.println("3. Estado");
            System.out.println("0. Volver al menú principal");
            System.out.print("\nOpción: ");

            int editOption = getIntInput();

            if (editOption == 0) {
                System.out.println("\nVolviendo al menú principal...");
                return;
            }

            switch (editOption) {
                case 1:
                    editNoteTitle(selectedNote);
                    break;
                case 2:
                    editNoteContent(selectedNote);
                    break;
                case 3:
                    editNoteStatus(selectedNote);
                    break;
                default:
                    System.out.println("\nOpción no válida.");
                    System.out.println("\nPresiona Enter para continuar...");
                    scanner.nextLine();
            }
        } else {
            System.out.println("\nOpción no válida.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
        }
    }

    /**
     * Permite al usuario editar el título de una nota.
     */
    private void editNoteTitle(Note note) {
        System.out.println("\n===========================================");
        System.out.println("           EDITAR TÍTULO");
        System.out.println("===========================================");

        System.out.println("0. Cancelar edición");
        System.out.print("\nNuevo título: ");
        String title = scanner.nextLine().trim();
        if (title.equals("0")) {
            System.out.println("\nEdición cancelada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }
        if (title.isEmpty()) {
            System.out.println("\nEl título no puede estar vacío.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        note.setTitle(title);
        note.setUpdatedAt(new Date());

        Note updatedNote = notesService.updateNote(note);
        if (updatedNote != null) {
            System.out.println("\nTítulo actualizado con éxito:");
            System.out.println(updatedNote);
        } else {
            System.out.println("\nError al actualizar el título.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Permite al usuario editar el contenido de una nota.
     */
    private void editNoteContent(Note note) {
        System.out.println("\n===========================================");
        System.out.println("           EDITAR CONTENIDO");
        System.out.println("===========================================");

        System.out.println("0. Cancelar edición");
        System.out.print("\nNuevo contenido: ");
        String content = scanner.nextLine().trim();
        if (content.equals("0")) {
            System.out.println("\nEdición cancelada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }
        if (content.isEmpty()) {
            System.out.println("\nEl contenido no puede estar vacío.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        note.setContent(content);
        note.setUpdatedAt(new Date());

        Note updatedNote = notesService.updateNote(note);
        if (updatedNote != null) {
            System.out.println("\nContenido actualizado con éxito:");
            System.out.println(updatedNote);
        } else {
            System.out.println("\nError al actualizar el contenido.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Permite al usuario editar el estado de una nota.
     */
    private void editNoteStatus(Note note) {
        System.out.println("\n===========================================");
        System.out.println("           EDITAR ESTADO");
        System.out.println("===========================================");

        System.out.println("0. Cancelar edición");
        System.out.println("\nSeleccione el nuevo estado:");
        List<String> availableStatuses = currentUser.getAvailableStatuses();
        for (int i = 0; i < availableStatuses.size(); i++) {
            System.out.println((i + 1) + ". " + availableStatuses.get(i));
        }

        System.out.print("\nOpción: ");
        int option = getIntInput();

        if (option == 0) {
            System.out.println("\nEdición cancelada.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        if (option >= 1 && option <= availableStatuses.size()) {
            String newStatus = availableStatuses.get(option - 1);
            Note updatedNote = notesService.updateNoteStatus(note.getId(), currentUser.getUsername(), newStatus);

            if (updatedNote != null) {
                System.out.println("\nEstado actualizado con éxito:");
                System.out.println(updatedNote);
            } else {
                System.out.println("\nError al actualizar el estado.");
            }
        } else {
            System.out.println("\nOpción no válida.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Permite al usuario eliminar una nota.
     */
    private void deleteNote() {
        System.out.println("\n===========================================");
        System.out.println("             ELIMINAR NOTA");
        System.out.println("===========================================");
        
        List<Note> notes = notesService.getAllNotesByUser(currentUser.getUsername());

        if (notes.isEmpty()) {
            System.out.println("No tienes ninguna nota para eliminar.");
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            return;
        }

        System.out.println("0. Volver al menú principal");
        System.out.println("\nSeleccione la nota a eliminar:");
        displayNotesList(notes);
        
        System.out.print("\nOpción: ");
        int option = getIntInput();

        if (option == 0) {
            System.out.println("\nVolviendo al menú principal...");
            return;
        }

        if (option >= 1 && option <= notes.size()) {
            Note selectedNote = notes.get(option - 1);
            displayNoteDetail(selectedNote);

            System.out.println("\n¿Está seguro de que desea eliminar esta nota?");
            System.out.println("1. Sí, eliminar");
            System.out.println("0. No, cancelar");
            System.out.print("\nOpción: ");

            int confirmOption = getIntInput();

            if (confirmOption == 0) {
                System.out.println("\nEliminación cancelada.");
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
                return;
            }

            if (confirmOption == 1) {
                if (notesService.deleteNote(selectedNote.getId(), currentUser.getUsername())) {
                    System.out.println("\nNota eliminada con éxito.");
                } else {
                    System.out.println("\nError al eliminar la nota.");
                }
            } else {
                System.out.println("\nOpción no válida.");
            }
        } else {
            System.out.println("\nOpción no válida.");
        }
        System.out.println("\nPresiona Enter para continuar...");
        scanner.nextLine();
    }
};