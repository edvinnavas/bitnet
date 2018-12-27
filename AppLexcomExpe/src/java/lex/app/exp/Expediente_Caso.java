package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "Expediente_Caso")
@ViewScoped
public class Expediente_Caso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    
    private Integer deudor;
    
    private String nombre_deudor;
    private String actor;

    private Integer caso;
    private String no_cuenta;
    private String otro_no_cuenta;
    private Integer garantia;
    private String cargado;
    private Integer fiador;
    private String anticipo;

    private Date fecha_recepcion;
    private Double monto_inicial;
    private String Moneda;
    private String gestor;

    private boolean inv_pdf;
    private boolean inv_inv;
    private boolean inv_may;
    private boolean inv_nit;

    private Double pagos;
    private Double aumentos;
    private Double descuentos;
    private Double saldo;
    private Date fecha_u_pago;
    private Double monto_u_pago;
    private Boolean opcion_proximo_pago;
    private Date fecha_p_pago;
    private Double monto_p_pago;

    private List<SelectItem> lst_garantia;
    private List<SelectItem> lst_fiador;
    
    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
            System.out.println("USUARIO : => LexcomExpediente-Expediente_Caso(init): " + this.usuario);
            System.out.println("AMBIENTE: => LexcomExpediente-Expediente_Caso(init): " + this.ambiente);
            
            Driver drive = new Driver();
            this.nombre_deudor = "Nombre del deudor";
            this.actor = "Nombre del actor";

            this.caso = 0;
            this.no_cuenta = "00-00000-0";
            this.otro_no_cuenta = "00-00000-0";
            this.garantia = 0;
            this.cargado = "CARGADO";
            this.fiador = 0;
            this.anticipo = "NO";

            this.fecha_recepcion = new Date();
            this.monto_inicial = 0.00;
            this.Moneda = "QUETZAL";
            this.gestor = "Nombre del gestor";

            this.inv_pdf = false;
            this.inv_inv = false;
            this.inv_may = false;
            this.inv_nit = false;

            this.pagos = 0.00;
            this.aumentos = 0.00;
            this.descuentos = 0.00;
            this.saldo = 0.00;
            this.fecha_u_pago = new Date();
            this.monto_u_pago = 0.00;
            this.opcion_proximo_pago = false;
            this.fecha_p_pago = new Date();
            this.monto_p_pago = 0.00;

            String lista_garantia_sql = "select g.garantia, g.nombre from garantia g where g.estado='VIGENTE'";
            this.lst_garantia = drive.lista_SelectItem_simple(lista_garantia_sql,this.ambiente);
            String lista_fiador_sql = "select f.nombre, f.nombre from fiador f where f.deudor=" + this.deudor;
            this.lst_fiador = drive.lista_SelectItem_simple(lista_fiador_sql,this.ambiente);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Caso(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Caso(Integer deudor) {
        try {
            this.deudor = deudor;
            
            if (this.deudor != null) {
                String cadenasql = "select "
                        + "d.nombre, " // rs.getObject(0);
                        + "a.nombre, " // rs.getObject(1);
                        + "d.caso, " // rs.getObject(2);
                        + "d.no_cuenta, " // rs.getObject(3);
                        + "d.no_cuenta_otro, " // rs.getObject(4);
                        + "d.garantia, " // rs.getObject(5);
                        + "d.cargado, " // rs.getObject(6);
                        + "d.anticipo, " // rs.getObject(7);
                        + "d.fecha_recepcion, " // rs.getObject(8);
                        + "d.monto_inicial, " // rs.getObject(9);
                        + "d.moneda, " // rs.getObject(10);
                        + "u.nombre, " // rs.getObject(11);
                        + "d.pdf, " // rs.getObject(12);
                        + "d.inv, " // rs.getObject(13);
                        + "d.maycom, " // rs.getObject(14);
                        + "d.nits, " // rs.getObject(15);
                        + "d.saldo, " // rs.getObject(16);
                        + "d.fecha_proximo_pago, " // rs.getObject(17);
                        + "d.cuota_convenio, " // rs.getObject(18);
                        + "d.opcion_proximo_pago, " // rs.getObject(19);
                        + "(select ifnull(sum(p.monto),0.00) from pago p where p.deudor=" + this.deudor + ") pagos, " // rs.getObject(20);
                        + "(select ifnull(sum(a.monto),0.00) from aumento a where a.deudor=" + this.deudor + ") aumento, " // rs.getObject(21);
                        + "(select ifnull(sum(d.monto),0.00) from descuento d where d.deudor=" + this.deudor + ") descuentos, " // rs.getObject(22);
                        + "(select ifnull(max(p.fecha),date('1900-01-01')) from pago p where p.deudor=" + this.deudor + ") fecha_u_pago, " // rs.getObject(23);
                        + "ifnull((select ifnull(p.monto,0.00) from pago p where p.deudor=" + this.deudor + " and p.fecha=(select ifnull(max(p.fecha),date('1900-01-01')) from pago p where p.deudor=" + this.deudor + ")),0.00) monto_u_pago, " // rs.getObject(24);
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado, d.estatus) in (select e.sestado, e.estatus from estado_status_judicial e) and d.deudor=" + this.deudor + ") validar_judicial, " // rs.getObject(25)
                        + "(select if(count(*)=0,'INCORRECTO','CORRECTO') from deudor d where (d.sestado_extra, d.estatus_extra) in (select e.sestado_extra, e.estatus_extra from estado_status_extrajudicial e) and d.deudor=" + this.deudor + ") validar_extrajudicial " // rs.getObject(26);
                        + "from "
                        + "deudor d "
                        + "left join actor a on (d.actor=a.actor) "
                        + "left join usuario u on (d.usuario=u.usuario) "
                        + "left join juicio j on (d.deudor=j.deudor) "
                        + "where "
                        + "d.deudor=" + this.deudor;

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
                for (Integer i = 1; i < filas; i++) {
                    this.nombre_deudor = vector_result[i][0];
                    this.actor = vector_result[i][1];
                    this.caso = Integer.parseInt(vector_result[i][2]);
                    this.no_cuenta = vector_result[i][3];
                    this.otro_no_cuenta = vector_result[i][4];
                    this.garantia = Integer.parseInt(vector_result[i][5]);
                    this.cargado = vector_result[i][6];
                    this.anticipo = vector_result[i][7];
                    this.fecha_recepcion = formatDate.parse(vector_result[i][8]);
                    this.monto_inicial = Double.parseDouble(vector_result[i][9]);
                    this.Moneda = vector_result[i][10];
                    this.gestor = vector_result[i][11];
                    this.inv_pdf = vector_result[i][12].equals("SI");
                    this.inv_inv = vector_result[i][13].equals("SI");
                    this.inv_may = vector_result[i][14].equals("SI");
                    this.inv_nit = vector_result[i][15].equals("SI");
                    this.saldo = Double.parseDouble(vector_result[i][16]);
                    this.fecha_p_pago = formatDate.parse(vector_result[i][17]);
                    this.monto_p_pago = Double.parseDouble(vector_result[i][18]);
                    this.opcion_proximo_pago = vector_result[i][19].equals("SI");
                    this.pagos = Double.parseDouble(vector_result[i][20]);
                    this.aumentos = Double.parseDouble(vector_result[i][21]);
                    this.descuentos = Double.parseDouble(vector_result[i][22]);
                    this.fecha_u_pago = formatDate.parse(vector_result[i][23]);
                    this.monto_u_pago = Double.parseDouble(vector_result[i][24]);
                    this.com_judicial = vector_result[i][25];
                    this.com_extrajudicial = vector_result[i][26];
                }

                Driver drive = new Driver();
                this.lst_fiador = drive.lista_SelectItem_simple("select f.fiador, f.nombre from fiador f where f.deudor=" + this.deudor,this.ambiente);
                if (this.lst_fiador.isEmpty()) {
                    this.lst_fiador.add(new SelectItem(0, "SIN FIADOR"));
                    this.fiador = 0;
                } else {
                    this.fiador = Integer.parseInt(this.lst_fiador.get(0).getValue().toString());
                }
                
                this.titulo_deudor = "CASO: " + this.caso + " JUDICIAL: " + this.com_judicial + " EXTRAJUDICIAL: " + this.com_extrajudicial + " TIEMPO: 00:00:00";

                RequestContext.getCurrentInstance().execute("PF('var_exp_caso').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Caso(Cargar_Expediente_Caso): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void guardar_expediente_caso() {
        try {
            String inv_pdf_s;
            if (this.inv_pdf) {
                inv_pdf_s = "SI";
            } else {
                inv_pdf_s = "NO";
            }
            String inv_inv_s;
            if (this.inv_inv) {
                inv_inv_s = "SI";
            } else {
                inv_inv_s = "NO";
            }
            String inv_may_s;
            if (this.inv_may) {
                inv_may_s = "SI";
            } else {
                inv_may_s = "NO";
            }
            String inv_nit_s;
            if (this.inv_nit) {
                inv_nit_s = "SI";
            } else {
                inv_nit_s = "NO";
            }
            
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.guardarExpedienteCaso(id_usuario, this.deudor, this.garantia, this.cargado, this.anticipo, this.saldo, inv_pdf_s, inv_inv_s, inv_may_s, inv_nit_s, this.ambiente);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Caso(guardar_expediente_caso): " + ex.toString());
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

    public String getNombre_deudor() {
        return nombre_deudor;
    }

    public void setNombre_deudor(String nombre_deudor) {
        this.nombre_deudor = nombre_deudor;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Integer getCaso() {
        return caso;
    }

    public void setCaso(Integer caso) {
        this.caso = caso;
    }

    public String getNo_cuenta() {
        return no_cuenta;
    }

    public void setNo_cuenta(String no_cuenta) {
        this.no_cuenta = no_cuenta;
    }

    public String getOtro_no_cuenta() {
        return otro_no_cuenta;
    }

    public void setOtro_no_cuenta(String otro_no_cuenta) {
        this.otro_no_cuenta = otro_no_cuenta;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public String getCargado() {
        return cargado;
    }

    public void setCargado(String cargado) {
        this.cargado = cargado;
    }

    public Integer getFiador() {
        return fiador;
    }

    public void setFiador(Integer fiador) {
        this.fiador = fiador;
    }

    public String getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(Date fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public boolean isInv_pdf() {
        return inv_pdf;
    }

    public void setInv_pdf(boolean inv_pdf) {
        this.inv_pdf = inv_pdf;
    }

    public boolean isInv_inv() {
        return inv_inv;
    }

    public void setInv_inv(boolean inv_inv) {
        this.inv_inv = inv_inv;
    }

    public boolean isInv_may() {
        return inv_may;
    }

    public void setInv_may(boolean inv_may) {
        this.inv_may = inv_may;
    }

    public boolean isInv_nit() {
        return inv_nit;
    }

    public void setInv_nit(boolean inv_nit) {
        this.inv_nit = inv_nit;
    }

    public Double getPagos() {
        return pagos;
    }

    public void setPagos(Double pagos) {
        this.pagos = pagos;
    }

    public Double getAumentos() {
        return aumentos;
    }

    public void setAumentos(Double aumentos) {
        this.aumentos = aumentos;
    }

    public Double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Double descuentos) {
        this.descuentos = descuentos;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Date getFecha_u_pago() {
        return fecha_u_pago;
    }

    public void setFecha_u_pago(Date fecha_u_pago) {
        this.fecha_u_pago = fecha_u_pago;
    }

    public Double getMonto_u_pago() {
        return monto_u_pago;
    }

    public void setMonto_u_pago(Double monto_u_pago) {
        this.monto_u_pago = monto_u_pago;
    }

    public Boolean getOpcion_proximo_pago() {
        return opcion_proximo_pago;
    }

    public void setOpcion_proximo_pago(Boolean opcion_proximo_pago) {
        this.opcion_proximo_pago = opcion_proximo_pago;
    }

    public Date getFecha_p_pago() {
        return fecha_p_pago;
    }

    public void setFecha_p_pago(Date fecha_p_pago) {
        this.fecha_p_pago = fecha_p_pago;
    }

    public Double getMonto_p_pago() {
        return monto_p_pago;
    }

    public void setMonto_p_pago(Double monto_p_pago) {
        this.monto_p_pago = monto_p_pago;
    }

    public List<SelectItem> getLst_garantia() {
        return lst_garantia;
    }

    public void setLst_garantia(List<SelectItem> lst_garantia) {
        this.lst_garantia = lst_garantia;
    }

    public List<SelectItem> getLst_fiador() {
        return lst_fiador;
    }

    public void setLst_fiador(List<SelectItem> lst_fiador) {
        this.lst_fiador = lst_fiador;
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
