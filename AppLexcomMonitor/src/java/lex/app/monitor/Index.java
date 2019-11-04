package lex.app.monitor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "Index")
@ViewScoped
public class Index implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nombre_db_produccion;
    private String url_db_produccion;
    private String usuario_db_produccion;
    private String fecha_ultima_gestion_produccion;
    private String hora_ultima_gestion_produccion;
    private Integer numero_gestion_produccion;
    
    private String nombre_db_test;
    private String url_db_test;
    private String usuario_db_test;
    private String fecha_ultima_gestion_test;
    private String hora_ultima_gestion_test;
    private Integer numero_gestion_test;
    
    private String nombre_db_replica;
    private String url_db_replica;
    private String usuario_db_replica;
    private String fecha_ultima_gestion_replica;
    private String hora_ultima_gestion_replica;
    private Integer numero_gestion_replica;
    
    @PostConstruct
    public void init() {
        try {
            this.GetMonitor();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Bienvenido al monitor bases de datos."));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Busqueda(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void GetMonitor() {
        try {
            this.nombre_db_produccion = "gcj";
            this.url_db_produccion = "jdbc:mysql://192.168.2.3:3306/gcj";
            this.usuario_db_produccion = "usuario_gcj";
            
            this.nombre_db_test = "gcj_test";
            this.url_db_test = "jdbc:mysql://192.168.2.3:3306/gcj_test";
            this.usuario_db_test = "usuario_gcj";
            
            this.nombre_db_replica = "gcj";
            this.url_db_replica = "jdbc:mysql://192.168.2.1:3306/gcj";
            this.usuario_db_replica = "usuario_gcj";
            
            SimpleDateFormat formatoFecha_db = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoFecha_form = new SimpleDateFormat("dd/MM/yyyy");
            
            // GET MONITOR PRODUCCION.
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.getMonitor("LEXCOMJNDI");

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for (Integer i = 1; i < filas; i++) {
                Date fecha1 = formatoFecha_db.parse(vector_result[i][0]);
                this.fecha_ultima_gestion_produccion = formatoFecha_form.format(fecha1);
                this.hora_ultima_gestion_produccion = vector_result[i][1];
                this.numero_gestion_produccion = Integer.parseInt(vector_result[i][2]);
            } 
            
            // GET MONITOR PRUEBAS.
            servicio = new Servicio();
            resultado = servicio.getMonitor("LexcomJndiTEST");

            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for (Integer i = 1; i < filas; i++) {
                Date fecha1 = formatoFecha_db.parse(vector_result[i][0]);
                this.fecha_ultima_gestion_test = formatoFecha_form.format(fecha1);
                this.hora_ultima_gestion_test = vector_result[i][1];
                this.numero_gestion_test = Integer.parseInt(vector_result[i][2]);
            }
            
            // GET MONITOR REPLICA.
            servicio = new Servicio();
            resultado = servicio.getMonitor("LexcomJndiReplica");

            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for (Integer i = 1; i < filas; i++) {
                Date fecha1 = formatoFecha_db.parse(vector_result[i][0]);
                this.fecha_ultima_gestion_replica = formatoFecha_form.format(fecha1);
                this.hora_ultima_gestion_replica = vector_result[i][1];
                this.numero_gestion_replica = Integer.parseInt(vector_result[i][2]);
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomMonitor-Index(GetMonitor): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public String getNombre_db_produccion() {
        return nombre_db_produccion;
    }

    public void setNombre_db_produccion(String nombre_db_produccion) {
        this.nombre_db_produccion = nombre_db_produccion;
    }

    public String getUrl_db_produccion() {
        return url_db_produccion;
    }

    public void setUrl_db_produccion(String url_db_produccion) {
        this.url_db_produccion = url_db_produccion;
    }

    public String getUsuario_db_produccion() {
        return usuario_db_produccion;
    }

    public void setUsuario_db_produccion(String usuario_db_produccion) {
        this.usuario_db_produccion = usuario_db_produccion;
    }

    public String getFecha_ultima_gestion_produccion() {
        return fecha_ultima_gestion_produccion;
    }

    public void setFecha_ultima_gestion_produccion(String fecha_ultima_gestion_produccion) {
        this.fecha_ultima_gestion_produccion = fecha_ultima_gestion_produccion;
    }

    public String getHora_ultima_gestion_produccion() {
        return hora_ultima_gestion_produccion;
    }

    public void setHora_ultima_gestion_produccion(String hora_ultima_gestion_produccion) {
        this.hora_ultima_gestion_produccion = hora_ultima_gestion_produccion;
    }

    public Integer getNumero_gestion_produccion() {
        return numero_gestion_produccion;
    }

    public void setNumero_gestion_produccion(Integer numero_gestion_produccion) {
        this.numero_gestion_produccion = numero_gestion_produccion;
    }

    public String getNombre_db_test() {
        return nombre_db_test;
    }

    public void setNombre_db_test(String nombre_db_test) {
        this.nombre_db_test = nombre_db_test;
    }

    public String getUrl_db_test() {
        return url_db_test;
    }

    public void setUrl_db_test(String url_db_test) {
        this.url_db_test = url_db_test;
    }

    public String getUsuario_db_test() {
        return usuario_db_test;
    }

    public void setUsuario_db_test(String usuario_db_test) {
        this.usuario_db_test = usuario_db_test;
    }

    public String getFecha_ultima_gestion_test() {
        return fecha_ultima_gestion_test;
    }

    public void setFecha_ultima_gestion_test(String fecha_ultima_gestion_test) {
        this.fecha_ultima_gestion_test = fecha_ultima_gestion_test;
    }

    public String getHora_ultima_gestion_test() {
        return hora_ultima_gestion_test;
    }

    public void setHora_ultima_gestion_test(String hora_ultima_gestion_test) {
        this.hora_ultima_gestion_test = hora_ultima_gestion_test;
    }

    public Integer getNumero_gestion_test() {
        return numero_gestion_test;
    }

    public void setNumero_gestion_test(Integer numero_gestion_test) {
        this.numero_gestion_test = numero_gestion_test;
    }

    public String getNombre_db_replica() {
        return nombre_db_replica;
    }

    public void setNombre_db_replica(String nombre_db_replica) {
        this.nombre_db_replica = nombre_db_replica;
    }

    public String getUrl_db_replica() {
        return url_db_replica;
    }

    public void setUrl_db_replica(String url_db_replica) {
        this.url_db_replica = url_db_replica;
    }

    public String getUsuario_db_replica() {
        return usuario_db_replica;
    }

    public void setUsuario_db_replica(String usuario_db_replica) {
        this.usuario_db_replica = usuario_db_replica;
    }

    public String getFecha_ultima_gestion_replica() {
        return fecha_ultima_gestion_replica;
    }

    public void setFecha_ultima_gestion_replica(String fecha_ultima_gestion_replica) {
        this.fecha_ultima_gestion_replica = fecha_ultima_gestion_replica;
    }

    public String getHora_ultima_gestion_replica() {
        return hora_ultima_gestion_replica;
    }

    public void setHora_ultima_gestion_replica(String hora_ultima_gestion_replica) {
        this.hora_ultima_gestion_replica = hora_ultima_gestion_replica;
    }

    public Integer getNumero_gestion_replica() {
        return numero_gestion_replica;
    }

    public void setNumero_gestion_replica(Integer numero_gestion_replica) {
        this.numero_gestion_replica = numero_gestion_replica;
    }
    
}
