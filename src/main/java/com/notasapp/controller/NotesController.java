package com.notasapp.controller;

import com.notasapp.model.Note;
import com.notasapp.model.User;
import com.notasapp.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Scanner;

@Controller
public class NotesController {

    private final NotesService notesService;
    private final Scanner scanner;
    private User currentUser;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        loginUser();
        showMainMenu();
    }

    private void loginUser() {
        System.out.println("=== INICIO DE SESIÓN ===");
        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();
        this.currentUser = new User(username);
        System.out.println("\n¡Bienvenido, " + username + "!");
    }

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

    private void viewAllNotes() {
        List<Note> notes = notesService.getAllNotesByUser(currentUser.getUsername());
        
        System.out.println("\n=== TODAS MIS NOTAS ===");
        if (notes.isEmpty()) {
            System.out.println("No tienes ninguna nota creada.");
            return;
        }
        
        displayNotesList(notes);
    }

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