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

@ManagedBean(name = "Expediente_Admon")
@ViewScoped
public class Expediente_Admon implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    
    private Integer deudor;
    
    private String lb_numero_gestiones_admon;
    private String descripcion_gestion;
    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;
    
    private List<Administrativo_List> lst_admon;
    private Administrativo_List admon_sel;
    
    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            System.out.println("USUARIO : => LexcomExpediente-Expediente_Admon(init): " + this.usuario);
            System.out.println("AMBIENTE: => LexcomExpediente-Expediente_Admon(init): " + this.ambiente);
            
            this.descripcion_gestion = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Admon(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void limpiar_expediente_admon() {
        try {
            this.descripcion_gestion = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Admon(limpiar_expediente_admon): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Admon(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                String cadenasql = "select "
                        + "d.deudor_historial_administrativo indice, " // rs.getObject(0);
                        + "d.fecha fecha, " // rs.getObject(1);
                        + "d.hora hora, " // rs.getObject(2);
                        + "u.nombre usuario, " // rs.getObject(3);
                        + "d.descripcion observacion " // rs.getObject(4);
                        + "from "
                        + "deudor_historial_administrativo d "
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
                java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql,this.ambiente);

                Integer filas = resultado.size();
                Integer columnas = resultado.get(0).getItem().size();
                String[][] vector_result = new String[resultado.size()][columnas];
                for (Integer i = 0; i < resultado.size(); i++) {
                    for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                        vector_result[i][j] = resultado.get(i).getItem().get(j);
                    }
                }

                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                this.lst_admon = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    Administrativo_List nod = new Administrativo_List(
                            Integer.parseInt(vector_result[i][0]),
                            formatDate.parse(vector_result[i][1]),
                            vector_result[i][2],
                            vector_result[i][3],
                            vector_result[i][4]);
                    this.lst_admon.add(nod);
                }

                filas = filas - 1;
                this.lb_numero_gestiones_admon = "No. de gestiones: " + filas;

                cadenasql = "select "
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(0);
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(1)
                        + "d.caso " // rs.getObject(2)
                        + "from "
                        + "deudor d "
                        + "where "
                        + "d.deudor=" + this.deudor;

                servicio = new Servicio();
                resultado = servicio.reporte(cadenasql,this.ambiente);

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
                
                RequestContext.getCurrentInstance().execute("PF('var_exp_admon').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Admon(Cargar_Expediente_Admon): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void Actualizar_Expediente_Admon() {
        try {
            String cadenasql = "select "
                    + "d.deudor_historial_administrativo indice, "
                    + "d.fecha fecha, "
                    + "d.hora hora, "
                    + "u.nombre usuario, "
                    + "d.descripcion observacion "
                    + "from "
                    + "deudor_historial_administrativo d "
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
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql,this.ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            this.lst_admon = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Administrativo_List nod = new Administrativo_List(
                        Integer.parseInt(vector_result[i][0]),
                        formatDate.parse(vector_result[i][1]),
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4]);
                this.lst_admon.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_gestiones_admon = "No. de gestiones: " + filas;

            cadenasql = "select "
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(0);
                    + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(1)
                    + "d.caso "
                    + "from "
                    + "deudor d "
                    + "where "
                    + "d.deudor=" + this.deudor;

            servicio = new Servicio();
            resultado = servicio.reporte(cadenasql,this.ambiente);

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
            System.out.println("ERROR => LexcomExpediente-Expediente_Admon(Actualizar_Expediente_Admon): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void insertar_gestion_admon() {
        try {
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'",this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.gestionAdministracionInsertar(id_usuario, this.deudor, id_usuario, 1, this.descripcion_gestion,this.ambiente);
            
            this.limpiar_expediente_admon();
            this.Actualizar_Expediente_Admon();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Admon(insertar_gestion_admon): " + ex.toString());
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

    public String getLb_numero_gestiones_admon() {
        return lb_numero_gestiones_admon;
    }

    public void setLb_numero_gestiones_admon(String lb_numero_gestiones_admon) {
        this.lb_numero_gestiones_admon = lb_numero_gestiones_admon;
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

    public List<Administrativo_List> getLst_admon() {
        return lst_admon;
    }

    public void setLst_admon(List<Administrativo_List> lst_admon) {
        this.lst_admon = lst_admon;
    }

    public Administrativo_List getAdmon_sel() {
        return admon_sel;
    }

    public void setAdmon_sel(Administrativo_List admon_sel) {
        this.admon_sel = admon_sel;
    }
    
}
