import java.util.Scanner;

public class NotesApp {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n[Ver Notas seleccionado]");
                    // Aquí irá la lógica para ver notas
                    break;
                case 2:
                    System.out.println("\n[Crear Nueva Nota seleccionado]");
                    // Aquí irá la lógica para crear notas
                    break;
                case 3:
                    System.out.println("\n[Editar Nota seleccionado]");
                    // Aquí irá la lógica para editar notas
                    break;
                case 4:
                    System.out.println("\n[Eliminar Nota seleccionado]");
                    // Aquí irá la lógica para eliminar notas
                    break;
                case 5:
                    System.out.println("\n[Buscar Notas seleccionado]");
                    // Aquí irá la lógica para buscar notas
                    break;
                case 6:
                    System.out.println("\nGracias por usar la aplicación. ¡Hasta pronto!");
                    salir = true;
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== BIENVENIDO A TU BLOC DE NOTAS ===");
        System.out.println("1. Ver mis notas");
        System.out.println("2. Crear nueva nota");
        System.out.println("3. Editar nota");
        System.out.println("4. Eliminar nota");
        System.out.println("5. Buscar notas");
        System.out.println("6. Salir");
        System.out.print("\nSeleccione una opción: ");
    }
} 