package lexcom.app;

import java.io.Serializable;
import java.sql.Connection;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    Connection conn;

    public Evento(Connection conn) {
        this.conn = conn;
    }

    public String insertar(String usuario, String descripcion) {
        String resultado;
        
        try {
            String cadenasql = "insert into evento ("
                    + "usuario,"
                    + "fecha,"
                    + "hora,"
                    + "descripcion) values ("
                    + usuario + ","
                    + "CURRENT_DATE()" + ","
                    + "CURRENT_TIME()" + ",'"
                    + descripcion + "')";
            
            Servicio servicio = new Servicio();
            //resultado = servicio.sqlTransaccion(cadenasql);
            resultado = servicio.sqlTransaccion(cadenasql, descripcion);
        } catch (Exception ex) {
            resultado = "2," + ex.toString();
        }

        return resultado;
    }

}
