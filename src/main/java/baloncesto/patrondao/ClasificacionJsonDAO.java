package baloncesto.patrondao;

import baloncesto.Clasificacion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClasificacionJsonDAO implements Dao<Clasificacion, String> {
    public static final Path DEFAULT_PATH = Paths.get("src\\main\\resources");
    public static ClasificacionJsonDAO instance;
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ClasificacionJsonDAO() {
    }

    public static ClasificacionJsonDAO getInstance() {
        if (instance == null)
            instance = new ClasificacionJsonDAO();
        return instance;
    }

    @Override
    public Clasificacion get(String id) {
        Path completePath = Paths.get(String.valueOf(DEFAULT_PATH) + "/" + id.toLowerCase().replace(' ','_') + ".json");

        try(var fr = new FileReader(String.valueOf(completePath))){

            return gson.fromJson(fr,Clasificacion.class);
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo");
        }

        return null;
    }

    @Override
    public Set<Clasificacion> getAll() {
        HashSet<Clasificacion> arrayList = new HashSet<>();
        try {
            Files.walk(DEFAULT_PATH)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try(var fr = new FileReader(String.valueOf(path))){
                            arrayList.add(gson.fromJson(fr,Clasificacion.class));
                        } catch (IOException e) {
                            System.err.println("No se pudo leer el archivo");
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    @Override
    public boolean save(Clasificacion obxecto) {
        Path completePath = Paths.get(String.valueOf(DEFAULT_PATH) + "\\" + obxecto.getCompeticion().toLowerCase().replace(' ','_') + ".json");

        if (Files.exists(completePath)) {
            try {
                Files.createFile(completePath);
            } catch (IOException e) {
                System.err.println("No se pudo crear archivo");
            }
        }

        try (var fw = new FileWriter(String.valueOf(completePath))) {
            gson.toJson(obxecto,fw);
        } catch (IOException e) {
            System.err.println("ERROR: save");
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Clasificacion obx) {
        Path completePath = Paths.get(String.valueOf(DEFAULT_PATH) + "\\" + obx.getCompeticion().toLowerCase().replace(' ','_') + ".json");

        try {
            Files.delete(completePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            Files.list(DEFAULT_PATH).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        Path completePath = Paths.get(String.valueOf(DEFAULT_PATH) + "\\" + id.toLowerCase().replace(' ','_') + ".json");

        try {
            Files.delete(completePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void update(Clasificacion obx) {
        if (delete(obx))
            save(obx);
    }
}
