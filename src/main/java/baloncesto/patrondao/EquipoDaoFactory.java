package baloncesto.patrondao;


import baloncesto.Equipo;


public class EquipoDaoFactory {
    private static EquipoDaoFactory instancia;
    private Dao<Equipo, String> dao;

    private EquipoDaoFactory() {
    }

    //Sigelton
    public static synchronized EquipoDaoFactory getInstance() {
        if (instancia == null) {
            instancia = new EquipoDaoFactory();
        }
        return instancia;
    }

    public Dao<Equipo, String> getEquipoDAO(String tipo) {
        switch (tipo) {
            case "OS":
                dao = new EquipoObjectStreamDao();
                break;
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado");

        }
        return dao;
    }

    public Dao<Equipo, String> getEquipoDAO(String tipo, String ruta) {

        switch (tipo) {
            case "OS":
                dao = new EquipoObjectStreamDao(ruta);
                break;
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado");

        }
        return dao;
    }
}