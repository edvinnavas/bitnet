package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Juridico")
@ViewScoped
public class Expediente_Juridico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Integer deudor;

    private List<Juridico_List> lst_juridico;
    private Juridico_List juridico_sel;
    private String lb_numero_gestiones_juridico;

    private String descripcion_gestion;

    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            this.descripcion_gestion = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juridico(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void limpiar_expediente_juridico() {
        try {
            this.descripcion_gestion = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juridico(limpiar_expediente_juridico): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Juridico(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                    String cadenasql = "select "
                            + "d.deudor_historial_juridico indice, " // rs.getObject(0);
                            + "d.fecha fecha, " // rs.getObject(1);
                            + "d.hora hora, " // rs.getObject(2);
                            + "u.nombre usuario, " // rs.getObject(3);
                            + "d.descripcion observacion " // rs.getObject(4);
                            + "from "
                            + "deudor_historial_juridico d "
                            + "left join usuario u on (d.usuario=u.usuario) "
                            + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                            + "left join deudor de on (d.deudor=de.deudor) "
                            + "left join actor a on (de.actor=a.actor) "
                            + "where "
                            + "d.deudor=" + this.deudor + " "
                            + "order "
                            + "by d.fecha desc, "
                            + "d.hora desc";

                    Servicio servicio = new Servicio();
                    java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

                    Integer filas = resultado.size();
                    Integer columnas = resultado.get(0).getItem().size();
                    String[][] vector_result = new String[resultado.size()][columnas];
                    for (Integer i = 0; i < resultado.size(); i++) {
                        for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                            vector_result[i][j] = resultado.get(i).getItem().get(j);
                        }
                    }

                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    this.lst_juridico = new ArrayList<>();
                    for (Integer i = 1; i < filas; i++) {
                        Juridico_List nod = new Juridico_List(
                                Integer.parseInt(vector_result[i][0]),
                                formatDate.parse(vector_result[i][1]),
                                vector_result[i][2],
                                vector_result[i][3],
                                vector_result[i][4]);
                        this.lst_juridico.add(nod);
                    }

                    filas = filas - 1;
                    this.lb_numero_gestiones_juridico = "No. de gestiones: " + filas;

                    cadenasql = "select "
                            + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(0);
                            + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(1)
                            + "d.caso " // rs.getObject(2)
                            + "from "
                            + "deudor d "
                            + "where "
                            + "d.deudor=" + this.deudor;

                    servicio = new Servicio();
                    resultado = servicio.reporte(cadenasql, this.ambiente);

                    filas = resultado.size();
                    columnas = resultado.get(0).getItem().size();
                    vector_result = new String[resultado.size()][columnas];
                    for (Integer i = 0; i < resultado.size(); i++) {
                        for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                            vector_result[i][j] = resultado.get(i).getItem().get(j);
                        }
                    }

                    Integer caso = 0;
                    for (Integer i = 1; i < filas; i++) {
                        this.com_extrajudicial = vector_result[i][0];
                        this.com_judicial = vector_result[i][1];
                        caso = Integer.parseInt(vector_result[i][2]);
                    }

                    this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";

                    RequestContext.getCurrentInstance().execute("PF('var_exp_juridico').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juridico(Cargar_Expediente_Juridico): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Actualizar_Expediente_Juridico() {
        try {
            String cadenasql = "select "
                    + "d.deudor_historial_juridico indice, " // rs.getObject(0);
                    + "d.fecha fecha, " // rs.getObject(1);
                    + "d.hora hora, " // rs.getObject(2);
                    + "u.nombre usuario, " // rs.getObject(3);
                    + "d.descripcion observacion " // rs.getObject(4);
                    + "from "
                    + "deudor_historial_juridico d "
                    + "left join usuario u on (d.usuario=u.usuario) "
                    + "left join codigo_contactabilidad c on (d.codigo_contactabilidad=c.codigo_contactabilidad) "
                    + "left join deudor de on (d.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "d.deudor=" + this.deudor + " "
                    + "order "
                    + "by d.fecha desc, "
                    + "d.hora desc";

            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            this.lst_juridico = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juridico_List nod = new Juridico_List(
                        Integer.parseInt(vector_result[i][0]),
                        formatDate.parse(vector_result[i][1]),
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4]);
                this.lst_juridico.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_gestiones_juridico = "No. de gestiones: " + filas;

            cadenasql = "select "
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(0);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(1)
                    + "d.caso " // rs.getObject(2)
                    + "from "
                    + "deudor d "
                    + "where "
                    + "d.deudor=" + this.deudor;

            servicio = new Servicio();
            resultado = servicio.reporte(cadenasql, this.ambiente);

            filas = resultado.size();
            columnas = resultado.get(0).getItem().size();
            vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            Integer caso = 0;
            for (Integer i = 1; i < filas; i++) {
                this.com_extrajudicial = vector_result[i][0];
                this.com_judicial = vector_result[i][1];
                caso = Integer.parseInt(vector_result[i][2]);
            }

            this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juridico(Actualizar_Expediente_Juridico): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void insertar_gestion_juridico() {
        try {

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.gestionJuridicoInsertar(id_usuario, this.deudor, id_usuario, 1, this.descripcion_gestion, this.ambiente);

            this.limpiar_expediente_juridico();
            this.Actualizar_Expediente_Juridico();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juridico(insertar_gestion_juridico): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public Integer getDeudor() {
        return deudor;
    }

    public void setDeudor(Integer deudor) {
        this.deudor = deudor;
    }

    public List<Juridico_List> getLst_juridico() {
        return lst_juridico;
    }

    public void setLst_juridico(List<Juridico_List> lst_juridico) {
        this.lst_juridico = lst_juridico;
    }

    public Juridico_List getJuridico_sel() {
        return juridico_sel;
    }

    public void setJuridico_sel(Juridico_List juridico_sel) {
        this.juridico_sel = juridico_sel;
    }

    public String getLb_numero_gestiones_juridico() {
        return lb_numero_gestiones_juridico;
    }

    public void setLb_numero_gestiones_juridico(String lb_numero_gestiones_juridico) {
        this.lb_numero_gestiones_juridico = lb_numero_gestiones_juridico;
    }

    public String getDescripcion_gestion() {
        return descripcion_gestion;
    }

    public void setDescripcion_gestion(String descripcion_gestion) {
        this.descripcion_gestion = descripcion_gestion;
    }

    public String getCom_extrajudicial() {
        return com_extrajudicial;
    }

    public void setCom_extrajudicial(String com_extrajudicial) {
        this.com_extrajudicial = com_extrajudicial;
    }

    public String getCom_judicial() {
        return com_judicial;
    }

    public void setCom_judicial(String com_judicial) {
        this.com_judicial = com_judicial;
    }

    public String getTitulo_deudor() {
        return titulo_deudor;
    }

    public void setTitulo_deudor(String titulo_deudor) {
        this.titulo_deudor = titulo_deudor;
    }

}
