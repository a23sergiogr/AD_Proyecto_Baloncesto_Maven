package baloncesto;

import baloncesto.patrondao.ClasificacionFileDao;

import java.util.Scanner;

public class Proyecto_Xestion_de_Equipo_de_Baloncesto {
    private static final Scanner sc = new Scanner(System.in);
    private static ClasificacionFileDao clasificacionFileDao;
    private static Clasificacion clasificacionActual;

    public static void main(String[] args) {
        clasificacionFileDao = new ClasificacionFileDao("src/main/resources/clasificaciones/");
        boolean salir = false;

        System.out.print("Nombre de la Clasificacion: ");
        clasificacionActual = new Clasificacion(sc.nextLine());

        while (!salir) {
            mostrarMenu();
            String opcion = sc.nextLine().toLowerCase();

            switch (opcion) {
                case "a":
                    agregarEquipo();
                    break;
                case "b":
                    mostrarClasificacion();
                    break;
                case "c":
                    guardarClasificacion();
                    break;
                case "d":
                    cargarClasificacion();
                    break;
                case "e":
                    salir = salirDelPrograma();
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú de Gestión de Clasificación ---");
        System.out.println("a. Añadir equipo");
        System.out.println("b. Mostrar clasificación");
        System.out.println("c. Guardar clasificación");
        System.out.println("d. Cargar clasificación");
        System.out.println("e. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarEquipo() {
        System.out.print("Ingrese el nombre del equipo: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese el número de victorias: ");
        int victorias = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese el número de derrotas: ");
        int derrotas = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese los puntos a favor: ");
        int puntosAFavor = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese los puntos en contra: ");
        int puntosEnContra = Integer.parseInt(sc.nextLine());

        Equipo nuevoEquipo = new Equipo(nombre, victorias, derrotas, puntosAFavor, puntosEnContra);

        if (clasificacionActual.addEquipo(nuevoEquipo)) {
            System.out.println("Equipo agregado con éxito.");
        } else {
            System.out.println("El equipo ya existe en la clasificación.");
        }
    }

    private static void mostrarClasificacion() {
        System.out.println(clasificacionActual);
    }

    private static void guardarClasificacion() {
        if (clasificacionFileDao.save(clasificacionActual)) {
            System.out.println("Clasificación guardada con éxito.");
        } else {
            System.out.println("Error al guardar la clasificación.");
        }
    }

    private static void cargarClasificacion() {
        System.out.print("Ingrese el nombre de la clasificación a cargar: ");
        String nombreCompeticion = sc.nextLine();
        Clasificacion clasificacion = clasificacionFileDao.get(nombreCompeticion);

        if (clasificacion != null) {
            clasificacionActual = clasificacion;
            System.out.println("Clasificación cargada con éxito.");
        } else {
            System.out.println("No se pudo encontrar la clasificación especificada.");
        }
    }

    private static boolean salirDelPrograma() {
        System.out.print("¿Está seguro de que desea salir? (s/n): ");
        String confirmacion = sc.nextLine().toLowerCase();
        return confirmacion.equals("s");
    }
}

