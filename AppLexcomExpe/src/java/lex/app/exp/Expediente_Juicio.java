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

@ManagedBean(name = "Expediente_Juicio")
@ViewScoped
public class Expediente_Juicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;

    private Integer deudor;

    private List<Juicio_List> lst_juicio;
    private Juicio_List juicio_sel;
    private String lb_numero_juicios;

    private String com_extrajudicial;
    private String com_judicial;
    private String titulo_deudor;

    //Campos formulario Juicio
    private Integer juzgado;
    private List<SelectItem> lst_juzgado;
    private Date fecha;
    private String no_juicio;
    private Double monto;
    private String descripcion;
    private Integer procurador;
    private List<SelectItem> lst_procurador;
    private String razon_notificacion;
    private Integer notificador;
    private String abogado_deudor;
    private String sumario;
    private Date memorial;
    private String procuracion;
    private Date fecha_admision_demanda;
    private String deudor_notificado;
    private Date fecha_notificacion;
    private String depositario;
    private String tiempo_estimado;

    private List<Juicio_Arraigo_List> lst_juicio_arraigo;
    Juicio_Arraigo_List sel_arraigo;
    private List<SelectItem> lst_arraigo;

    private List<Juicio_Banco_List> lst_juicio_banco;
    Juicio_Banco_List sel_banco;
    private List<SelectItem> lst_banco;

    private List<Juicio_Finca_List> lst_juicio_finca;
    Juicio_Finca_List sel_finca;
    private List<SelectItem> lst_finca;

    private List<Juicio_Salario_List> lst_juicio_salario;
    Juicio_Salario_List sel_salario;
    private List<SelectItem> lst_salario;

    private List<Juicio_Vehiculo_List> lst_juicio_vehiculo;
    Juicio_Vehiculo_List sel_vehiculo;
    private List<SelectItem> lst_vehiculo;

    private List<Juicio_Otros_List> lst_juicio_otros;
    Juicio_Otros_List sel_otros;
    private List<SelectItem> lst_otros;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            this.lst_juicio = new ArrayList<>();
            this.juicio_sel = null;
            this.lb_numero_juicios = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";

            this.lst_juicio_arraigo = new ArrayList<>();
            this.lst_juicio_banco = new ArrayList<>();
            this.lst_juicio_finca = new ArrayList<>();
            this.lst_juicio_salario = new ArrayList<>();
            this.lst_juicio_vehiculo = new ArrayList<>();
            this.lst_juicio_otros = new ArrayList<>();

            this.lst_arraigo = new ArrayList<>();
            this.lst_banco = new ArrayList<>();
            this.lst_finca = new ArrayList<>();
            this.lst_salario = new ArrayList<>();
            this.lst_vehiculo = new ArrayList<>();
            this.lst_otros = new ArrayList<>();
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void limpiar_expediente_juicio() {
        try {
            this.lst_juicio = new ArrayList<>();
            this.juicio_sel = null;
            this.lb_numero_juicios = "";
            this.com_extrajudicial = "";
            this.com_judicial = "";
            this.titulo_deudor = "";

            this.lst_juicio_arraigo = new ArrayList<>();
            this.lst_juicio_banco = new ArrayList<>();
            this.lst_juicio_finca = new ArrayList<>();
            this.lst_juicio_salario = new ArrayList<>();
            this.lst_juicio_vehiculo = new ArrayList<>();
            this.lst_juicio_otros = new ArrayList<>();

            this.lst_arraigo = new ArrayList<>();
            this.lst_banco = new ArrayList<>();
            this.lst_finca = new ArrayList<>();
            this.lst_salario = new ArrayList<>();
            this.lst_vehiculo = new ArrayList<>();
            this.lst_otros = new ArrayList<>();
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(limpiar_expediente_juicio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Cargar_Expediente_Juicio(Integer deudor) {
        try {
            this.deudor = deudor;

            if (this.deudor != null) {
                Driver driver = new Driver();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                if (driver.validar_corporacion(id_usuario, this.deudor, ambiente)) {
                    String cadenasql = "select "
                            + "j.juicio, "// rs.getObject(0)
                            + "ju.nombre, "// rs.getObject(1)
                            + "j.fecha, "// rs.getObject(2)
                            + "j.no_juicio, "// rs.getObject(3)
                            + "j.monto "// rs.getObject(4)
                            + "from "
                            + "juicio j "
                            + "left join juzgado ju on (j.juzgado=ju.juzgado) "
                            + "left join deudor de on (j.deudor=de.deudor) "
                            + "left join actor a on (de.actor=a.actor) "
                            + "where "
                            + "j.deudor=" + this.deudor + " "
                            + "order by "
                            + "j.fecha";

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
                    this.lst_juicio = new ArrayList<>();
                    for (Integer i = 1; i < filas; i++) {
                        Juicio_List nod = new Juicio_List(
                                Integer.parseInt(vector_result[i][0]),
                                vector_result[i][1],
                                formatDate.parse(vector_result[i][2]),
                                vector_result[i][3],
                                Double.parseDouble(vector_result[i][4]));
                        this.lst_juicio.add(nod);
                    }

                    filas = filas - 1;
                    this.lb_numero_juicios = "No. de juicios: " + filas;

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

                    RequestContext.getCurrentInstance().execute("PF('var_exp_juicio').show();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "La corporación del actor asignado el expediente no puede ser consultado por el usuario."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un expediente del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(Cargar_Expediente_Juicio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void Actualizar_Expediente_juicio() {
        try {
            String cadenasql = "select "
                    + "j.juicio, "// rs.getObject(0)
                    + "ju.nombre, "// rs.getObject(1)
                    + "j.fecha, "// rs.getObject(2)
                    + "j.no_juicio, "// rs.getObject(3)
                    + "j.monto "// rs.getObject(4)
                    + "from "
                    + "juicio j "
                    + "left join juzgado ju on (j.juzgado=ju.juzgado) "
                    + "left join deudor de on (j.deudor=de.deudor) "
                    + "left join actor a on (de.actor=a.actor) "
                    + "where "
                    + "j.deudor=" + this.deudor + " "
                    + "order by "
                    + "j.fecha";

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
            this.lst_juicio = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_List nod = new Juicio_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        formatDate.parse(vector_result[i][2]),
                        vector_result[i][3],
                        Double.parseDouble(vector_result[i][4]));
                this.lst_juicio.add(nod);
            }

            filas = filas - 1;
            this.lb_numero_juicios = "No. de jucios: " + filas;

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
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(Actualizar_Expediente_juicio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void cargar_juicio_modificar() {
        try {
            Driver drive = new Driver();

            String lista_juzgado_sql = "select j.juzgado, j.nombre from juzgado j where j.estado='VIGENTE' order by j.nombre";
            this.lst_juzgado = drive.lista_SelectItem_simple(lista_juzgado_sql, this.ambiente);
            if (!this.lst_juzgado.isEmpty()) {
                this.juzgado = Integer.parseInt(this.lst_juzgado.get(0).getValue().toString());
            } else {
                this.juzgado = 0;
            }

            String lista_procurador_sql = "select u.usuario, u.nombre from usuario u where u.estado='VIGENTE' and u.procurador='SI' order by u.nombre";
            this.lst_procurador = drive.lista_SelectItem_simple(lista_procurador_sql, this.ambiente);
            if (!this.lst_procurador.isEmpty()) {
                this.procurador = Integer.parseInt(this.lst_procurador.get(0).getValue().toString());
            } else {
                this.procurador = 0;
            }

            this.lst_arraigo = drive.lista_medida_arraigo();
            this.lst_banco = drive.lista_medida_banco();
            this.lst_finca = drive.lista_medida_finca();
            this.lst_salario = drive.lista_medida_salario();
            this.lst_vehiculo = drive.lista_medida_vehiculo();
            this.lst_otros = drive.lista_medida_otro();

            String cadenasql = "select "
                    + "j.juzgado, "//rs.getObject(0)
                    + "j.fecha, "//rs.getObject(1)
                    + "j.no_juicio, "//rs.getObject(2)
                    + "j.monto, "//rs.getObject(3)
                    + "j.descripcion, "//rs.getObject(4)
                    + "j.procurador, "//rs.getObject(5)
                    + "j.razon_notificacion, "//rs.getObject(6)
                    + "j.notificador, "//rs.getObject(7)
                    + "j.abogado_deudor, "//rs.getObject(8)
                    + "j.sumario, "//rs.getObject(9)
                    + "j.memorial, "//rs.getObject(10)
                    + "j.procuracion, "//rs.getObject(11)
                    + "j.fecha_admision_demanda, "//rs.getObject(12)
                    + "j.deudor_notificado, "//rs.getObject(13)
                    + "j.fecha_notificacion, "//rs.getObject(14)
                    + "j.depositario, "//rs.getObject(15)
                    + "j.tiempo_estimado "//rs.getObject(16)
                    + "from "
                    + "juicio j "
                    + "where "
                    + "j.juicio=" + this.juicio_sel.getIndice();

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
                this.juzgado = Integer.parseInt(vector_result[i][0]);
                this.fecha = formatDate.parse(vector_result[i][1]);
                this.no_juicio = vector_result[i][2];
                this.monto = Double.parseDouble(vector_result[i][3]);
                this.descripcion = vector_result[i][4];
                this.procurador = Integer.parseInt(vector_result[i][5]);
                this.razon_notificacion = vector_result[i][6];
                this.notificador = Integer.parseInt(vector_result[i][7]);
                this.abogado_deudor = vector_result[i][8];
                this.sumario = vector_result[i][9];
                this.memorial = formatDate.parse(vector_result[i][10]);
                this.procuracion = vector_result[i][11];
                this.fecha_admision_demanda = formatDate.parse(vector_result[i][12]);
                this.deudor_notificado = vector_result[i][13];
                this.fecha_notificacion = formatDate.parse(vector_result[i][14]);
                this.depositario = vector_result[i][15];
                this.tiempo_estimado = vector_result[i][16];
            }

            // CARGA MEDIDA PRECAUTORIA ARRAIGO.
            cadenasql = "select ja.arraigo, ja.deligenciado from juicio_arraigo ja where ja.juicio=" + this.juicio_sel.getIndice();

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

            this.lst_juicio_arraigo = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_Arraigo_List nod = new Juicio_Arraigo_List(
                        i,
                        vector_result[i][0],
                        formatDate.parse(vector_result[i][1]));
                this.lst_juicio_arraigo.add(nod);
            }

            // CARGA MEDIDA PRECAUTORIA BANCO.
            cadenasql = "select ja.bancos, ja.deligenciado from juicio_banco ja where ja.juicio=" + this.juicio_sel.getIndice();

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

            this.lst_juicio_banco = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_Banco_List nod = new Juicio_Banco_List(
                        i,
                        vector_result[i][0],
                        formatDate.parse(vector_result[i][1]));
                this.lst_juicio_banco.add(nod);
            }

            // CARGA MEDIDA PRECAUTORIA FINCA.
            cadenasql = "select ja.finca, ja.letra, ja.deligenciado, ja.tramite from juicio_finca ja where ja.juicio=" + this.juicio_sel.getIndice();

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

            this.lst_juicio_finca = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_Finca_List nod = new Juicio_Finca_List(
                        i,
                        vector_result[i][0],
                        vector_result[i][1],
                        formatDate.parse(vector_result[i][2]),
                        vector_result[i][3]);
                this.lst_juicio_finca.add(nod);
            }

            // CARGA MEDIDA PRECAUTORIA SALARIO.
            cadenasql = "select ja.salario, ja.empresa, ja.deligenciado from juicio_salario ja where ja.juicio=" + this.juicio_sel.getIndice();

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

            this.lst_juicio_salario = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_Salario_List nod = new Juicio_Salario_List(
                        i,
                        vector_result[i][0],
                        vector_result[i][1],
                        formatDate.parse(vector_result[i][2]));
                this.lst_juicio_salario.add(nod);
            }

            // CARGA MEDIDA PRECAUTORIA VEHICULO.
            cadenasql = "select ja.vehiculo, ja.medida, ja.deligenciado from juicio_vehiculo ja where ja.juicio=" + this.juicio_sel.getIndice();

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

            this.lst_juicio_vehiculo = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_Vehiculo_List nod = new Juicio_Vehiculo_List(
                        i,
                        vector_result[i][0],
                        vector_result[i][1],
                        formatDate.parse(vector_result[i][2]));
                this.lst_juicio_vehiculo.add(nod);
            }

            // CARGA MEDIDA PRECAUTORIA OTROS.
            cadenasql = "select ja.otros, ja.medida, ja.deligenciado from juicio_otros ja where ja.juicio=" + this.juicio_sel.getIndice();

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

            this.lst_juicio_otros = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Juicio_Otros_List nod = new Juicio_Otros_List(
                        i,
                        vector_result[i][0],
                        vector_result[i][1],
                        formatDate.parse(vector_result[i][2]));
                this.lst_juicio_otros.add(nod);
            }

            RequestContext.getCurrentInstance().execute("PF('var_juicio').show();");

        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(cargar_juicio_modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void agregar_arraigo() {
        try {
            Juicio_Arraigo_List nodo = new Juicio_Arraigo_List(lst_juicio_arraigo.size() + 1, "PEDIDO", new Date());
            this.lst_juicio_arraigo.add(nodo);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(agregar_arraigo): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_arraigo() {
        try {
            if (this.sel_arraigo != null) {
                this.lst_juicio_arraigo.remove(this.sel_arraigo);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una medida precautoria."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(eliminar_arraigo): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void agregar_banco() {
        try {
            Juicio_Banco_List nodo = new Juicio_Banco_List(lst_juicio_banco.size() + 1, "PEDIDO", new Date());
            this.lst_juicio_banco.add(nodo);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(agregar_banco): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_banco() {
        try {
            if (this.sel_banco != null) {
                this.lst_juicio_banco.remove(this.sel_banco);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una medida precautoria."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(eliminar_banco): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void agregar_finca() {
        try {
            Juicio_Finca_List nodo = new Juicio_Finca_List(lst_juicio_finca.size() + 1, "-", "-", new Date(), "PEDIDO");
            this.lst_juicio_finca.add(nodo);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(agregar_finca): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_finca() {
        try {
            if (this.sel_finca != null) {
                this.lst_juicio_finca.remove(this.sel_finca);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una medida precautoria."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(eliminar_finca): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void agregar_salario() {
        try {
            Juicio_Salario_List nodo = new Juicio_Salario_List(lst_juicio_salario.size() + 1, "PEDIDO", "-", new Date());
            this.lst_juicio_salario.add(nodo);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(agregar_salario): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_salario() {
        try {
            if (this.sel_salario != null) {
                this.lst_juicio_salario.remove(this.sel_salario);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una medida precautoria."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(eliminar_salario): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void agregar_vehiculo() {
        try {
            Juicio_Vehiculo_List nodo = new Juicio_Vehiculo_List(lst_juicio_vehiculo.size() + 1, "-", "PEDIDO", new Date());
            this.lst_juicio_vehiculo.add(nodo);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(agregar_vehiculo): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_vehiculo() {
        try {
            if (this.sel_vehiculo != null) {
                this.lst_juicio_vehiculo.remove(this.sel_vehiculo);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una medida precautoria."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(eliminar_vehiculo): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void agregar_otros() {
        try {
            Juicio_Otros_List nodo = new Juicio_Otros_List(lst_juicio_otros.size() + 1, "-", "PEDIDO", new Date());
            this.lst_juicio_otros.add(nodo);
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(agregar_otros): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void eliminar_otros() {
        try {
            if (this.sel_otros != null) {
                this.lst_juicio_otros.remove(this.sel_otros);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar una medida precautoria."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(eliminar_otros): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void modificar_gestion_juicio() {
        try {
            GregorianCalendar gregory1 = new GregorianCalendar();
            gregory1.set(this.fecha.getYear() + 1900, this.fecha.getMonth(), this.fecha.getDate());
            XMLGregorianCalendar gre_fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory1);

            GregorianCalendar gregory2 = new GregorianCalendar();
            gregory2.set(this.memorial.getYear() + 1900, this.memorial.getMonth(), this.memorial.getDate());
            XMLGregorianCalendar gre_memorial = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory2);

            GregorianCalendar gregory3 = new GregorianCalendar();
            gregory3.set(this.fecha_admision_demanda.getYear() + 1900, this.fecha_admision_demanda.getMonth(), this.fecha_admision_demanda.getDate());
            XMLGregorianCalendar gre_fecha_admision_demanda = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory3);

            GregorianCalendar gregory4 = new GregorianCalendar();
            gregory4.set(this.fecha_notificacion.getYear() + 1900, this.fecha_notificacion.getMonth(), this.fecha_notificacion.getDate());
            XMLGregorianCalendar gre_fecha_notificacion = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory4);

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

            List<lexcom.ws.StringArray> modeloArraigo = new ArrayList<>();
            for (Integer i = 0; i < this.lst_juicio_arraigo.size(); i++) {
                lexcom.ws.StringArray regArraigo = new lexcom.ws.StringArray();
                regArraigo.getItem().add(this.lst_juicio_arraigo.get(i).getArraigo());
                regArraigo.getItem().add(formatDate.format(this.lst_juicio_arraigo.get(i).getFecha()));
                modeloArraigo.add(regArraigo);
            }

            List<lexcom.ws.StringArray> modeloBanco = new ArrayList<>();
            for (Integer i = 0; i < this.lst_juicio_banco.size(); i++) {
                lexcom.ws.StringArray regBanco = new lexcom.ws.StringArray();
                regBanco.getItem().add(this.lst_juicio_banco.get(i).getBanco());
                regBanco.getItem().add(formatDate.format(this.lst_juicio_banco.get(i).getFecha()));
                modeloBanco.add(regBanco);
            }

            List<lexcom.ws.StringArray> modeloFinca = new ArrayList<>();
            for (Integer i = 0; i < this.lst_juicio_finca.size(); i++) {
                lexcom.ws.StringArray regFinca = new lexcom.ws.StringArray();
                regFinca.getItem().add(this.lst_juicio_finca.get(i).getFinca());
                regFinca.getItem().add(this.lst_juicio_finca.get(i).getLetra());
                regFinca.getItem().add(formatDate.format(this.lst_juicio_finca.get(i).getFecha()));
                regFinca.getItem().add(this.lst_juicio_finca.get(i).getTramite());
                modeloFinca.add(regFinca);
            }

            List<lexcom.ws.StringArray> modeloSalario = new ArrayList<>();
            for (Integer i = 0; i < this.lst_juicio_salario.size(); i++) {
                lexcom.ws.StringArray regSalario = new lexcom.ws.StringArray();
                regSalario.getItem().add(this.lst_juicio_salario.get(i).getSalario());
                regSalario.getItem().add(this.lst_juicio_salario.get(i).getEmpresa());
                regSalario.getItem().add(formatDate.format(this.lst_juicio_salario.get(i).getFecha()));
                modeloSalario.add(regSalario);
            }

            List<lexcom.ws.StringArray> modeloVehiculo = new ArrayList<>();
            for (Integer i = 0; i < this.lst_juicio_vehiculo.size(); i++) {
                lexcom.ws.StringArray regVehiculo = new lexcom.ws.StringArray();
                regVehiculo.getItem().add(this.lst_juicio_vehiculo.get(i).getVehiculo());
                regVehiculo.getItem().add(this.lst_juicio_vehiculo.get(i).getMedida());
                regVehiculo.getItem().add(formatDate.format(this.lst_juicio_vehiculo.get(i).getFecha()));
                modeloVehiculo.add(regVehiculo);
            }

            List<lexcom.ws.StringArray> modeloOtros = new ArrayList<>();
            for (Integer i = 0; i < this.lst_juicio_otros.size(); i++) {
                lexcom.ws.StringArray regOtros = new lexcom.ws.StringArray();
                regOtros.getItem().add(this.lst_juicio_otros.get(i).getOtros());
                regOtros.getItem().add(this.lst_juicio_otros.get(i).getMedida());
                regOtros.getItem().add(formatDate.format(this.lst_juicio_otros.get(i).getFecha()));
                modeloOtros.add(regOtros);
            }

            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            Servicio servicio = new Servicio();
            String resultado = servicio.juicioModificar(id_usuario, this.juicio_sel.getIndice(), this.deudor, this.juzgado, gre_fecha, this.no_juicio, this.monto, this.descripcion, modeloArraigo, modeloBanco, modeloFinca, modeloSalario, modeloVehiculo, modeloOtros, this.procurador, this.razon_notificacion, this.notificador, this.abogado_deudor, this.sumario, gre_memorial, this.procuracion, gre_fecha_admision_demanda, this.deudor_notificado, gre_fecha_notificacion, this.depositario, this.tiempo_estimado, this.ambiente);

            this.limpiar_expediente_juicio();
            this.Actualizar_Expediente_juicio();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => LexcomExpediente-Expediente_Juicio(modificar_gestion_juicio): " + ex.toString());
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

    public List<Juicio_List> getLst_juicio() {
        return lst_juicio;
    }

    public void setLst_juicio(List<Juicio_List> lst_juicio) {
        this.lst_juicio = lst_juicio;
    }

    public Juicio_List getJuicio_sel() {
        return juicio_sel;
    }

    public void setJuicio_sel(Juicio_List juicio_sel) {
        this.juicio_sel = juicio_sel;
    }

    public String getLb_numero_juicios() {
        return lb_numero_juicios;
    }

    public void setLb_numero_juicios(String lb_numero_juicios) {
        this.lb_numero_juicios = lb_numero_juicios;
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

    public Integer getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(Integer juzgado) {
        this.juzgado = juzgado;
    }

    public List<SelectItem> getLst_juzgado() {
        return lst_juzgado;
    }

    public void setLst_juzgado(List<SelectItem> lst_juzgado) {
        this.lst_juzgado = lst_juzgado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNo_juicio() {
        return no_juicio;
    }

    public void setNo_juicio(String no_juicio) {
        this.no_juicio = no_juicio;
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

    public Integer getProcurador() {
        return procurador;
    }

    public void setProcurador(Integer procurador) {
        this.procurador = procurador;
    }

    public List<SelectItem> getLst_procurador() {
        return lst_procurador;
    }

    public void setLst_procurador(List<SelectItem> lst_procurador) {
        this.lst_procurador = lst_procurador;
    }

    public String getRazon_notificacion() {
        return razon_notificacion;
    }

    public void setRazon_notificacion(String razon_notificacion) {
        this.razon_notificacion = razon_notificacion;
    }

    public Integer getNotificador() {
        return notificador;
    }

    public void setNotificador(Integer notificador) {
        this.notificador = notificador;
    }

    public String getAbogado_deudor() {
        return abogado_deudor;
    }

    public void setAbogado_deudor(String abogado_deudor) {
        this.abogado_deudor = abogado_deudor;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Date getMemorial() {
        return memorial;
    }

    public void setMemorial(Date memorial) {
        this.memorial = memorial;
    }

    public String getProcuracion() {
        return procuracion;
    }

    public void setProcuracion(String procuracion) {
        this.procuracion = procuracion;
    }

    public Date getFecha_admision_demanda() {
        return fecha_admision_demanda;
    }

    public void setFecha_admision_demanda(Date fecha_admision_demanda) {
        this.fecha_admision_demanda = fecha_admision_demanda;
    }

    public String getDeudor_notificado() {
        return deudor_notificado;
    }

    public void setDeudor_notificado(String deudor_notificado) {
        this.deudor_notificado = deudor_notificado;
    }

    public Date getFecha_notificacion() {
        return fecha_notificacion;
    }

    public void setFecha_notificacion(Date fecha_notificacion) {
        this.fecha_notificacion = fecha_notificacion;
    }

    public String getDepositario() {
        return depositario;
    }

    public void setDepositario(String depositario) {
        this.depositario = depositario;
    }

    public String getTiempo_estimado() {
        return tiempo_estimado;
    }

    public void setTiempo_estimado(String tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public List<Juicio_Arraigo_List> getLst_juicio_arraigo() {
        return lst_juicio_arraigo;
    }

    public void setLst_juicio_arraigo(List<Juicio_Arraigo_List> lst_juicio_arraigo) {
        this.lst_juicio_arraigo = lst_juicio_arraigo;
    }

    public Juicio_Arraigo_List getSel_arraigo() {
        return sel_arraigo;
    }

    public void setSel_arraigo(Juicio_Arraigo_List sel_arraigo) {
        this.sel_arraigo = sel_arraigo;
    }

    public List<SelectItem> getLst_arraigo() {
        return lst_arraigo;
    }

    public void setLst_arraigo(List<SelectItem> lst_arraigo) {
        this.lst_arraigo = lst_arraigo;
    }

    public List<Juicio_Banco_List> getLst_juicio_banco() {
        return lst_juicio_banco;
    }

    public void setLst_juicio_banco(List<Juicio_Banco_List> lst_juicio_banco) {
        this.lst_juicio_banco = lst_juicio_banco;
    }

    public Juicio_Banco_List getSel_banco() {
        return sel_banco;
    }

    public void setSel_banco(Juicio_Banco_List sel_banco) {
        this.sel_banco = sel_banco;
    }

    public List<SelectItem> getLst_banco() {
        return lst_banco;
    }

    public void setLst_banco(List<SelectItem> lst_banco) {
        this.lst_banco = lst_banco;
    }

    public List<Juicio_Finca_List> getLst_juicio_finca() {
        return lst_juicio_finca;
    }

    public void setLst_juicio_finca(List<Juicio_Finca_List> lst_juicio_finca) {
        this.lst_juicio_finca = lst_juicio_finca;
    }

    public Juicio_Finca_List getSel_finca() {
        return sel_finca;
    }

    public void setSel_finca(Juicio_Finca_List sel_finca) {
        this.sel_finca = sel_finca;
    }

    public List<SelectItem> getLst_finca() {
        return lst_finca;
    }

    public void setLst_finca(List<SelectItem> lst_finca) {
        this.lst_finca = lst_finca;
    }

    public List<Juicio_Salario_List> getLst_juicio_salario() {
        return lst_juicio_salario;
    }

    public void setLst_juicio_salario(List<Juicio_Salario_List> lst_juicio_salario) {
        this.lst_juicio_salario = lst_juicio_salario;
    }

    public Juicio_Salario_List getSel_salario() {
        return sel_salario;
    }

    public void setSel_salario(Juicio_Salario_List sel_salario) {
        this.sel_salario = sel_salario;
    }

    public List<SelectItem> getLst_salario() {
        return lst_salario;
    }

    public void setLst_salario(List<SelectItem> lst_salario) {
        this.lst_salario = lst_salario;
    }

    public List<Juicio_Vehiculo_List> getLst_juicio_vehiculo() {
        return lst_juicio_vehiculo;
    }

    public void setLst_juicio_vehiculo(List<Juicio_Vehiculo_List> lst_juicio_vehiculo) {
        this.lst_juicio_vehiculo = lst_juicio_vehiculo;
    }

    public Juicio_Vehiculo_List getSel_vehiculo() {
        return sel_vehiculo;
    }

    public void setSel_vehiculo(Juicio_Vehiculo_List sel_vehiculo) {
        this.sel_vehiculo = sel_vehiculo;
    }

    public List<SelectItem> getLst_vehiculo() {
        return lst_vehiculo;
    }

    public void setLst_vehiculo(List<SelectItem> lst_vehiculo) {
        this.lst_vehiculo = lst_vehiculo;
    }

    public List<Juicio_Otros_List> getLst_juicio_otros() {
        return lst_juicio_otros;
    }

    public void setLst_juicio_otros(List<Juicio_Otros_List> lst_juicio_otros) {
        this.lst_juicio_otros = lst_juicio_otros;
    }

    public Juicio_Otros_List getSel_otros() {
        return sel_otros;
    }

    public void setSel_otros(Juicio_Otros_List sel_otros) {
        this.sel_otros = sel_otros;
    }

    public List<SelectItem> getLst_otros() {
        return lst_otros;
    }

    public void setLst_otros(List<SelectItem> lst_otros) {
        this.lst_otros = lst_otros;
    }

}
