package baloncesto.patrondao;

import baloncesto.Clasificacion;

public class ClasificacionDAOFactoy {
    public static Dao<Clasificacion, String> getClasificacionDAO(String tipo) {
        if (tipo.equalsIgnoreCase("json")) {
            return ClasificacionJsonDAO.getInstance();
        } else {
            return null;
        }
    }
}
