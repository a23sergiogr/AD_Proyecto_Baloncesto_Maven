package baloncesto.patrondao;

import baloncesto.Clasificacion;
import baloncesto.Equipo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ClasificacionFileDao implements Dao<Clasificacion, String>{
    private final String RUTA;
    private final Path datos;

    public ClasificacionFileDao(String ruta){
        this.RUTA = ruta;
        datos = Paths.get(RUTA);
    }

    @Override
    public Clasificacion get(String id) {
        HashSet<Clasificacion> clasificaciones = new HashSet<>(getAll());
        return clasificaciones.stream()
                .filter(c -> c.getCompeticion().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Clasificacion> getAll() {
        TreeSet<Clasificacion> clasificaciones = new TreeSet<>();
        if (Files.exists(datos) && Files.isDirectory(datos)) {
            try {
                Files.walk(datos)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".dat"))
                        .forEach(path -> {
                            Clasificacion clasificacion = new Clasificacion(path.getFileName().toString().replace(".dat", ""));
                            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                                while (true) {
                                    try {
                                        Equipo equipo = (Equipo) ois.readObject();
                                            clasificacion.addEquipo(equipo);
                                    } catch (EOFException e) {
                                        break;
                                    }
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                System.err.println("Error al leer los equipos del archivo: " + path + " - " + e.getMessage());
                            }
                            clasificaciones.add(clasificacion);
                        });
            } catch (IOException e) {
                System.err.println("Error al leer archivos de clasificaciones: " + e.getMessage());
            }
        }

        return clasificaciones;
    }

    @Override
    public boolean save(Clasificacion clasificacion) {
        String archivoClasificacion = RUTA + clasificacion.getCompeticion() + ".dat";
        EquipoDaoFactory equipoDAOFactory = EquipoDaoFactory.getInstance();
        Dao<Equipo, String> equipoDao = equipoDAOFactory.getEquipoDAO("OS", archivoClasificacion);
        clasificacion.getEquipos().forEach(equipoDao::save);
        return true;
    }

    @Override
    public boolean delete(Clasificacion clasificacion) {
        try {
            return Files.deleteIfExists(Paths.get(RUTA + clasificacion.getCompeticion() + ".dat"));
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
            return Files.deleteIfExists(Paths.get(RUTA + id + ".dat"));
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        Path path1 = Paths.get(RUTA);
        System.out.println(RUTA);
        if (Files.exists(path1) && Files.isDirectory(path1)) {
            try {
                // Obtener todos los archivos en la ruta que terminen en ".dat"
                Files.walk(path1)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".dat"))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                System.err.println("Error al borrar el fichero");
                            }
                        });
            } catch (IOException e) {
                System.err.println("Error al leer archivos de clasificaciones: " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    public void update(Clasificacion clasificacion) {
        delete(clasificacion);
        save(clasificacion);
    }
}
