package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Pago")
@ViewScoped
public class Expediente_Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Integer deudor;

    private List<Pago_List> lst_pago;
    private Pago_List pago_sel;
    private String lb_numero_pagos;

    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    //Mantenimiento
    private Integer banco;
    private List<SelectItem> lst_banco;
    private Date fecha;
    private String noboleta;
    private Double monto;
    private String descripcion;

    private Integer opcion_gestion; // 1: INSERTAR  2: MODIFICAR

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            this.lst_pago = new ArrayList<>();
            this.pago_sel = null;
            this.lb_numero_pagos = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void limpiar_expediente_pago() {
        try {
            this.lst_pago = new ArrayList<>();
            this.pago_sel = null;
            this.lb_numero_pagos = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(limpiar_expediente_pago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Pago(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                    String cadenasql = "select "
                            + "p.pago, "// rs.getObject(0)
                            + "b.nombre, "// rs.getObject(1)
                            + "p.fecha, "// rs.getObject(2)
                            + "p.monto, "// rs.getObject(3)
                            + "p.no_boleta, "// rs.getObject(4)
                            + "p.fecha_registro "// rs.getObject(5)
                            + "from "
                            + "pago p "
                            + "left join banco b on (p.banco=b.banco) "
                            + "where "
                            + "p.deudor=" + this.deudor + " "
                            + "order by "
                            + "p.fecha desc";

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
                    this.lst_pago = new ArrayList<>();
                    for (Integer i = 1; i < filas; i++) {
                        Pago_List nod = new Pago_List(
                                Integer.parseInt(vector_result[i][0]),
                                vector_result[i][1],
                                formatDate.parse(vector_result[i][2]),
                                Double.parseDouble(vector_result[i][3]),
                                vector_result[i][4],
                                formatDate.parse(vector_result[i][5]));
                        this.lst_pago.add(nod);
                    }

                    filas = filas - 1;
                    this.lb_numero_pagos = "No. de pagos: " + filas;

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

                    RequestContext.getCurrentInstance().execute("PF('var_exp_pago').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(Cargar_Expediente_Pago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Actualizar_Expediente_pago() {
        try {
            String cadenasql = "select "
                    + "p.pago, "// rs.getObject(0)
                    + "b.nombre, "// rs.getObject(1)
                    + "p.fecha, "// rs.getObject(2)
                    + "p.monto, "// rs.getObject(3)
                    + "p.no_boleta, "// rs.getObject(4)
                    + "p.fecha_registro "// rs.getObject(5)
                    + "from "
                    + "pago p "
                    + "left join banco b on (p.banco=b.banco) "
                    + "where "
                    + "p.deudor=" + this.deudor + " "
                    + "order by "
                    + "p.fecha desc";

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
            this.lst_pago = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Pago_List nod = new Pago_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        formatDate.parse(vector_result[i][2]),
                        Double.parseDouble(vector_result[i][3]),
                        vector_result[i][4],
                        formatDate.parse(vector_result[i][5]));
                this.lst_pago.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_pagos = "No. de pagos: " + filas;

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
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(Actualizar_Expediente_pago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_pago_agregar() {
        try {
            opcion_gestion = 1;
            Driver drive = new Driver();
            String lista_banco_sql = "select b.banco, b.nombre from banco b where b.estado='VIGENTE' order by b.nombre";
            this.lst_banco = drive.lista_SelectItem_simple(lista_banco_sql, this.ambiente);
            if (!this.lst_banco.isEmpty()) {
                this.banco = Integer.parseInt(this.lst_banco.get(0).getValue().toString());
            } else {
                this.banco = 0;
            }
            this.fecha = new Date();
            this.noboleta = "";
            this.monto = 0.00;
            this.descripcion = "";

            RequestContext.getCurrentInstance().execute("PF('var_pago').show();");

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(cargar_pago_agregar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_pago_modificar() {
        try {
            opcion_gestion = 2;
            Driver drive = new Driver();
            String lista_banco_sql = "select b.banco, b.nombre from banco b where b.estado='VIGENTE' order by b.nombre";
            this.lst_banco = drive.lista_SelectItem_simple(lista_banco_sql, this.ambiente);

            String cadenasql = "select "
                    + "p.banco, "//rs.getObject(0)
                    + "p.fecha, "//rs.getObject(1)
                    + "p.no_boleta, "//rs.getObject(2)
                    + "p.monto, "//rs.getObject(3)
                    + "p.descripcion "//rs.getObject(4)
                    + "from "
                    + "pago p "
                    + "where "
                    + "p.pago = " + this.pago_sel.getIndice();

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
            for (Integer i = 1; i < filas; i++) {
                this.banco = Integer.parseInt(vector_result[i][0]);
                this.fecha = formatDate.parse(vector_result[i][1]);
                this.noboleta = vector_result[i][2];
                this.monto = Double.parseDouble(vector_result[i][3]);
                this.descripcion = vector_result[i][4];
            }

            RequestContext.getCurrentInstance().execute("PF('var_pago').show();");

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(cargar_pago_modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void gestion_pago() {
        try {
            if (opcion_gestion == 1) {
                this.insertar_gestion_pago();
            } else {
                this.modificar_gestion_pago();
            }

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(gestion_pago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void insertar_gestion_pago() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha.getYear() + 1900, this.fecha.getMonth(), this.fecha.getDate());
            XMLGregorianCalendar gre_fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.pagoInsertar(id_usuario, this.deudor, this.banco, gre_fecha, this.noboleta, this.monto, this.descripcion, this.ambiente);

            this.limpiar_expediente_pago();
            this.Actualizar_Expediente_pago();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(insertar_gestion_pago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar_gestion_pago() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha.getYear() + 1900, this.fecha.getMonth(), this.fecha.getDate());
            XMLGregorianCalendar gre_fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.pagoModificar(id_usuario, this.pago_sel.getIndice(), this.deudor, this.banco, gre_fecha, this.noboleta, this.monto, this.descripcion, this.ambiente);

            this.limpiar_expediente_pago();
            this.Actualizar_Expediente_pago();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(modificar_gestion_pago): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_gestion_pago() {
        try {
            if (this.pago_sel != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                Servicio servicio = new Servicio();
                String resultado = servicio.pagoEliminar(id_usuario, this.pago_sel.getIndice(), this.ambiente);

                this.limpiar_expediente_pago();
                this.Actualizar_Expediente_pago();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un pago del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Pago(eliminar_gestion_pago): " + ex.toString());
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

    public List<Pago_List> getLst_pago() {
        return lst_pago;
    }

    public void setLst_pago(List<Pago_List> lst_pago) {
        this.lst_pago = lst_pago;
    }

    public Pago_List getPago_sel() {
        return pago_sel;
    }

    public void setPago_sel(Pago_List pago_sel) {
        this.pago_sel = pago_sel;
    }

    public String getLb_numero_pagos() {
        return lb_numero_pagos;
    }

    public void setLb_numero_pagos(String lb_numero_pagos) {
        this.lb_numero_pagos = lb_numero_pagos;
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

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }

    public List<SelectItem> getLst_banco() {
        return lst_banco;
    }

    public void setLst_banco(List<SelectItem> lst_banco) {
        this.lst_banco = lst_banco;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNoboleta() {
        return noboleta;
    }

    public void setNoboleta(String noboleta) {
        this.noboleta = noboleta;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOpcion_gestion() {
        return opcion_gestion;
    }

    public void setOpcion_gestion(Integer opcion_gestion) {
        this.opcion_gestion = opcion_gestion;
    }

}
