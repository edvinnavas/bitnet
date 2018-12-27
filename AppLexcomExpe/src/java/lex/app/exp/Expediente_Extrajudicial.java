package lex.app.exp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Extrajudicial")
@ViewScoped
public class Expediente_Extrajudicial implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    
    private Integer deudor;

    private String telefono_casa;
    private String telefono_celular;
    private String correo_electronico;
    private String lugar_trabajo;
    private String contacto_trabajo;
    private String telefono_trabajo;
    private String dpi;
    private String nit;
    private Integer intencion_pago;

    private Integer estado_extrajudicial;
    private Integer status_extrajudicial;

    private String convenio_pactado;
    private String notas;
    private String direccion;
    
    private List<SelectItem> lst_estado_extrajudicial;
    private List<SelectItem> lst_estatus_extrajudicial;
    private List<SelectItem> lst_intension_pago;
    
    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;
    
    private boolean somestadoextrajudicial;
    private boolean somstatusextrajudicial;
    private boolean somintensionpago;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            System.out.println("USUARIO : => LexcomExpediente-Expediente_Caso(init): " + this.usuario);
            System.out.println("AMBIENTE: => LexcomExpediente-Expediente_Caso(init): " + this.ambiente);
            
            Driver drive = new Driver();
            this.telefono_casa = "0000-0000";
            this.telefono_celular = "0000-0000";
            this.correo_electronico = "Correo electronico";
            this.lugar_trabajo = "Lugar de trabajo";
            this.contacto_trabajo = "Contacto trabajo";
            this.telefono_trabajo = "0000-0000";
            this.dpi = "DPI";
            this.nit = "NIT";
            this.intencion_pago = 0;

            this.estado_extrajudicial = 0;
            this.status_extrajudicial = 0;

            this.convenio_pactado = "Convenio pactado";
            this.notas = "Notas del gestor";
            this.direccion = "Direccion deudor";
            
            this.com_extrajudicial = "";
            
            String lista_estado_extra_sql = "select s.sestado_extra, s.nombre from sestado_extra s where s.estado='VIGENTE'";
            this.lst_estado_extrajudicial = drive.lista_SelectItem_simple(lista_estado_extra_sql, this.ambiente);
            this.lst_estatus_extrajudicial = new ArrayList<>();
            String lista_intencion_pago_sql = "select i.intencion_pago, i.nombre from intencion_pago i where i.estado='VIGENTE'";
            this.lst_intension_pago = drive.lista_SelectItem_simple(lista_intencion_pago_sql, this.ambiente);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Extrajudicial(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Extrajudicial(Integer deudor) {
        try {
            this.deudor = deudor;
            
            if (this.deudor != null) {
                String cadenasql = "select "
                        + "d.telefono_casa, " // rs.getObject(0);
                        + "d.telefono_celular, " // rs.getObject(1);
                        + "d.correo_electronico, " // rs.getObject(2);
                        + "d.lugar_trabajo, " // rs.getObject(3);
                        + "d.direccion_trabajo, " // rs.getObject(4);
                        + "d.telefono_trabajo, " // rs.getObject(5);
                        + "d.dpi, " // rs.getObject(6);
                        + "d.nit, " // rs.getObject(7);
                        + "d.intencion_pago, " // rs.getObject(8);
                        + "d.sestado_extra, " // rs.getObject(9);
                        + "d.estatus_extra, " // rs.getObject(10);
                        + "d.convenio_pactado, " // rs.getObject(11);
                        + "d.descripcion, " // rs.getObject(12);
                        + "d.direccion, " // rs.getObject(13);
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial, " // rs.getObject(14);
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(15)
                        + "d.caso " // rs.getObject(16)
                        + "from "
                        + "deudor d "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join juicio j on (d.deudor=j.deudor) "
                        + "where "
                        + "d.deudor=" + this.deudor;

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

                Integer caso = 0;
                for (Integer i = 1; i < filas; i++) {
                    this.telefono_casa = vector_result[i][0];
                    this.telefono_celular = vector_result[i][1];
                    this.correo_electronico = vector_result[i][2];
                    this.lugar_trabajo = vector_result[i][3];
                    this.contacto_trabajo = vector_result[i][4];
                    this.telefono_trabajo = vector_result[i][5];
                    this.dpi = vector_result[i][6];
                    this.nit = vector_result[i][7];
                    this.intencion_pago = Integer.parseInt(vector_result[i][8]);
                    this.estado_extrajudicial = Integer.parseInt(vector_result[i][9]);
                    this.cambio_estado_extrajudicial();
                    this.status_extrajudicial = Integer.parseInt(vector_result[i][10]);
                    this.convenio_pactado = vector_result[i][11];
                    this.notas = vector_result[i][12];
                    this.direccion = vector_result[i][13];
                    this.com_extrajudicial = vector_result[i][14];
                    this.com_judicial = vector_result[i][15];
                    caso = Integer.parseInt(vector_result[i][16]);
                }
                
                this.somestadoextrajudicial = true;
                this.somstatusextrajudicial = true;
                this.somintensionpago = true;
                
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                
                String esAsistente = driver.getString("select u.asistente from usuario u where u.usuario=" + id_usuario, this.ambiente);
                String esGestor = driver.getString("select u.gestor from usuario u where u.usuario=" + id_usuario, this.ambiente);
                
                if(esAsistente.equals("SI")) {
                    this.somestadoextrajudicial = false;
                    this.somstatusextrajudicial = false;
                    this.somintensionpago = false;
                } 
                if(esGestor.equals("SI")) {
                    this.somestadoextrajudicial = false;
                    this.somstatusextrajudicial = false;
                    this.somintensionpago = false;
                }
                
                this.titulo_deudor = "CASO: " + caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";
                
                RequestContext.getCurrentInstance().execute("PF('var_exp_extra').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Extrajudicial(Cargar_Expediente_Extrajudicial): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void cambio_estado_extrajudicial() {
        try {
            String cadenasql = "select "
                    + "distinct ee.estatus_extra, ee.nombre "
                    + "from "
                    + "estado_status_extrajudicial ese "
                    + "left join estatus_extra ee on (ese.estatus_extra=ee.estatus_extra) "
                    + "left join sestado_extra se on (ese.sestado_extra=se.sestado_extra) "
                    + "where "
                    + "se.sestado_extra = " + this.estado_extrajudicial + " "
                    + "order by "
                    + "ee.nombre";
            Driver driver = new Driver();
            this.lst_estatus_extrajudicial = driver.lista_SelectItem_simple(cadenasql, this.ambiente);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Extrajudicial(cambio_estado_extrajudicial): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void guardar_expediente_extrajudicial() {
        try {
            Servicio servicio = new Servicio();
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.guardarExpedienteExtrajudicial(id_usuario, this.deudor, this.estado_extrajudicial, this.status_extrajudicial, this.telefono_casa, this.telefono_celular, this.correo_electronico, this.lugar_trabajo, this.contacto_trabajo, this.telefono_trabajo,this.dpi, this.nit, this.intencion_pago,this.direccion, this.notas, this.ambiente);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Extrajudicial(guardar_expediente_extrajudicial): " + ex.toString());
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

    public String getTelefono_casa() {
        return telefono_casa;
    }

    public void setTelefono_casa(String telefono_casa) {
        this.telefono_casa = telefono_casa;
    }

    public String getTelefono_celular() {
        return telefono_celular;
    }

    public void setTelefono_celular(String telefono_celular) {
        this.telefono_celular = telefono_celular;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getLugar_trabajo() {
        return lugar_trabajo;
    }

    public void setLugar_trabajo(String lugar_trabajo) {
        this.lugar_trabajo = lugar_trabajo;
    }

    public String getContacto_trabajo() {
        return contacto_trabajo;
    }

    public void setContacto_trabajo(String contacto_trabajo) {
        this.contacto_trabajo = contacto_trabajo;
    }

    public String getTelefono_trabajo() {
        return telefono_trabajo;
    }

    public void setTelefono_trabajo(String telefono_trabajo) {
        this.telefono_trabajo = telefono_trabajo;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Integer getIntencion_pago() {
        return intencion_pago;
    }

    public void setIntencion_pago(Integer intencion_pago) {
        this.intencion_pago = intencion_pago;
    }

    public Integer getEstado_extrajudicial() {
        return estado_extrajudicial;
    }

    public void setEstado_extrajudicial(Integer estado_extrajudicial) {
        this.estado_extrajudicial = estado_extrajudicial;
    }

    public Integer getStatus_extrajudicial() {
        return status_extrajudicial;
    }

    public void setStatus_extrajudicial(Integer status_extrajudicial) {
        this.status_extrajudicial = status_extrajudicial;
    }

    public String getConvenio_pactado() {
        return convenio_pactado;
    }

    public void setConvenio_pactado(String convenio_pactado) {
        this.convenio_pactado = convenio_pactado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<SelectItem> getLst_estado_extrajudicial() {
        return lst_estado_extrajudicial;
    }

    public void setLst_estado_extrajudicial(List<SelectItem> lst_estado_extrajudicial) {
        this.lst_estado_extrajudicial = lst_estado_extrajudicial;
    }

    public List<SelectItem> getLst_estatus_extrajudicial() {
        return lst_estatus_extrajudicial;
    }

    public void setLst_estatus_extrajudicial(List<SelectItem> lst_estatus_extrajudicial) {
        this.lst_estatus_extrajudicial = lst_estatus_extrajudicial;
    }

    public List<SelectItem> getLst_intension_pago() {
        return lst_intension_pago;
    }

    public void setLst_intension_pago(List<SelectItem> lst_intension_pago) {
        this.lst_intension_pago = lst_intension_pago;
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

    public boolean isSomestadoextrajudicial() {
        return somestadoextrajudicial;
    }

    public void setSomestadoextrajudicial(boolean somestadoextrajudicial) {
        this.somestadoextrajudicial = somestadoextrajudicial;
    }

    public boolean isSomstatusextrajudicial() {
        return somstatusextrajudicial;
    }

    public void setSomstatusextrajudicial(boolean somstatusextrajudicial) {
        this.somstatusextrajudicial = somstatusextrajudicial;
    }

    public boolean isSomintensionpago() {
        return somintensionpago;
    }

    public void setSomintensionpago(boolean somintensionpago) {
        this.somintensionpago = somintensionpago;
    }
    
}
