package lexcom.app;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "Usuario")
@ViewScoped
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    private String opcion;

    private Usuario_List selectedUsuario;
    private List<Usuario_List> lst_usuario;
    private List<SelectItem> lst_rol = new ArrayList<>();
    private List<SelectItem> lst_cartera = new ArrayList<>();

    //Variables para registrar la información del usuario.
    private String nombre_completo_d;
    private String nombre_d;
    private String contrasena_d;
    private String recontrasena_d;
    private String descripcion_d;
    private String gestor_d;
    private String procurador_d;
    private String asistente_d;
    private String digitador_d;
    private String investigador_d;
    private Integer tipo_usuario_d;
    private Integer reinicio;
    private Integer rol;
    private String rol_label;

    //Habilitar los campos del formulario Usuario. 
    private Boolean txtNombreCompleto;
    private Boolean txtUsuario;
    private Boolean txtContrasena;
    private Boolean txtReContrasena;
    private Boolean somTipoUsuario;
    private Boolean sorGestor;
    private Boolean sorProcurador;
    private Boolean sorAsistente;
    private Boolean sorDigitador;
    private Boolean sorInvestigador;
    private Boolean itaDescripcion;
    private Boolean btnAceptar;
    private Boolean btnCancelar;
    private Boolean somRol;
    private Boolean pkCorporaciones;
    
    private List<String> lst_corporaciones_disponibles;
    private List<String> lst_corporaciones_asignadas;
    private DualListModel<String> lst_corporaciones;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            String cadenasql = "select "
                    + "u.usuario, "
                    + "u.nombre_completo, "
                    + "u.nombre, "
                    + "u.contrasena, "
                    + "u.estado, "
                    + "u.gestor, "
                    + "u.procurador, "
                    + "u.asistente, "
                    + "u.digitador, "
                    + "u.investigador, "
                    + "c.nombre, "
                    + "u.reinicio, "
                    + "u.rol "
                    + "from "
                    + "usuario u  "
                    + "left join cartera c on (u.tipo_usuario=c.cartera)";

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

            lst_usuario = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Usuario_List usu = new Usuario_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        vector_result[i][8],
                        vector_result[i][9],
                        vector_result[i][10],
                        Integer.parseInt(vector_result[i][11]),
                        Integer.parseInt(vector_result[i][12])
                );
                lst_usuario.add(usu);
            }

            Driver drive = new Driver();
            String lista_rol_sql = "select r.rol, r.nombre from rol r";
            this.lst_rol = drive.lista_SelectItem_simple(lista_rol_sql, this.ambiente);

            String lista_cartera_sql = "select c.cartera, c.nombre from cartera c where c.estado='VIGENTE'";
            this.lst_cartera = drive.lista_SelectItem_simple(lista_cartera_sql, this.ambiente);
            
            this.lst_corporaciones_disponibles = new ArrayList<>();
            this.lst_corporaciones_asignadas = new ArrayList<>();
            this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(init): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void constructor() {
        try {
            String cadenasql = "select "
                    + "u.usuario, "
                    + "u.nombre_completo, "
                    + "u.nombre, "
                    + "u.contrasena, "
                    + "u.estado, "
                    + "u.gestor, "
                    + "u.procurador, "
                    + "u.asistente, "
                    + "u.digitador, "
                    + "u.investigador, "
                    + "c.nombre, "
                    + "u.reinicio, "
                    + "u.rol "
                    + "from "
                    + "usuario u  "
                    + "left join cartera c on (u.tipo_usuario=c.cartera)";

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

            lst_usuario = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                Usuario_List usu = new Usuario_List(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2],
                        vector_result[i][3],
                        vector_result[i][4],
                        vector_result[i][5],
                        vector_result[i][6],
                        vector_result[i][7],
                        vector_result[i][8],
                        vector_result[i][9],
                        vector_result[i][10],
                        Integer.parseInt(vector_result[i][11]),
                        Integer.parseInt(vector_result[i][12])
                );
                lst_usuario.add(usu);
            }

            Driver drive = new Driver();
            String lista_rol_sql = "select rol, nombre from rol";
            this.lst_rol = drive.lista_SelectItem_simple(lista_rol_sql, this.ambiente);

            String lista_cartera_sql = "select c.cartera, c.nombre from cartera c where c.estado='VIGENTE'";
            this.lst_cartera = drive.lista_SelectItem_simple(lista_cartera_sql, this.ambiente);
            
            this.lst_corporaciones_disponibles = new ArrayList<>();
            this.lst_corporaciones_asignadas = new ArrayList<>();
            this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(constructor): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_insertar() {
        try {
            this.nombre_completo_d = "";
            this.nombre_d = "";
            this.contrasena_d = "";
            this.recontrasena_d = "";
            this.descripcion_d = "";
            this.gestor_d = "NO";
            this.procurador_d = "NO";
            this.asistente_d = "NO";
            this.digitador_d = "NO";
            this.investigador_d = "NO";
            this.tipo_usuario_d = 0;
            this.rol = 0;
            this.txtNombreCompleto = false;
            this.txtUsuario = false;
            this.txtContrasena = false;
            this.txtReContrasena = false;
            this.somTipoUsuario = false;
            this.sorGestor = false;
            this.sorProcurador = false;
            this.sorAsistente = false;
            this.sorDigitador = false;
            this.sorInvestigador = false;
            this.itaDescripcion = false;
            this.btnAceptar = false;
            this.btnCancelar = false;
            this.somRol = false;
            this.pkCorporaciones = false;

            String cadenasql = "select c.nombre from cooperacion c where c.estado = 'VIGENTE'";
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

            this.lst_corporaciones_disponibles = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                this.lst_corporaciones_disponibles.add(vector_result[i][0]);
            }
            
            this.lst_corporaciones_asignadas = new ArrayList<>();
            this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);

            this.opcion = "INSERTAR";

            RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(carga_info_insertar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_modificar() {
        try {
            if (this.selectedUsuario != null) {
                String cadenasql = "select "
                        + "u.nombre_completo, "
                        + "u.nombre, "
                        + "u.contrasena, "
                        + "u.descripcion, "
                        + "u.gestor, "
                        + "u.procurador, "
                        + "u.asistente, "
                        + "u.digitador, "
                        + "u.investigador, "
                        + "u.tipo_usuario, "
                        + "u.reinicio, "
                        + "u.rol "
                        + "from "
                        + "usuario u "
                        + "where "
                        + "u.usuario = " + this.selectedUsuario.getUsuario();
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

                for (Integer i = 1; i < filas; i++) {
                    this.nombre_completo_d = vector_result[i][0];
                    this.nombre_d = vector_result[i][1];
                    this.contrasena_d = vector_result[i][2];
                    this.recontrasena_d = vector_result[i][2];
                    this.descripcion_d = vector_result[i][3];
                    this.gestor_d = vector_result[i][4];
                    this.procurador_d = vector_result[i][5];
                    this.asistente_d = vector_result[i][6];
                    this.digitador_d = vector_result[i][7];
                    this.investigador_d = vector_result[i][8];
                    this.tipo_usuario_d = Integer.parseInt(vector_result[i][9]);
                    this.reinicio = Integer.parseInt(vector_result[i][10]);
                    this.rol = Integer.parseInt(vector_result[i][11]);
                }

                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion not in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_disponibles = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_disponibles.add(vector_result[i][0]);
                }

                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_asignadas = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_asignadas.add(vector_result[i][0]);
                }

                this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);

                this.txtNombreCompleto = false;
                this.txtUsuario = false;
                this.txtContrasena = true;
                this.txtReContrasena = true;
                this.somTipoUsuario = false;
                this.sorGestor = false;
                this.sorProcurador = false;
                this.sorAsistente = false;
                this.sorDigitador = false;
                this.sorInvestigador = false;
                this.itaDescripcion = false;
                this.somRol = false;
                this.btnAceptar = false;
                this.btnCancelar = false;
                this.pkCorporaciones = false;
                
                this.opcion = "MODIFICAR";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Dede seleccionar un usuario del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(carga_info_modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_eliminar() {
        try {
            if (this.selectedUsuario != null) {
                String cadenasql = "select "
                        + "u.nombre_completo, "
                        + "u.nombre, "
                        + "u.contrasena, "
                        + "u.descripcion, "
                        + "u.gestor, "
                        + "u.procurador, "
                        + "u.asistente, "
                        + "u.digitador, "
                        + "u.investigador, "
                        + "u.tipo_usuario, "
                        + "u.reinicio, "
                        + "u.rol "
                        + "from "
                        + "usuario u "
                        + "where "
                        + "u.usuario = " + this.selectedUsuario.getUsuario();
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

                for (Integer i = 1; i < filas; i++) {
                    this.nombre_completo_d = vector_result[i][0];
                    this.nombre_d = vector_result[i][1];
                    this.contrasena_d = vector_result[i][2];
                    this.recontrasena_d = vector_result[i][2];
                    this.descripcion_d = vector_result[i][3];
                    this.gestor_d = vector_result[i][4];
                    this.procurador_d = vector_result[i][5];
                    this.asistente_d = vector_result[i][6];
                    this.digitador_d = vector_result[i][7];
                    this.investigador_d = vector_result[i][8];
                    this.tipo_usuario_d = Integer.parseInt(vector_result[i][9]);
                    this.reinicio = Integer.parseInt(vector_result[i][10]);
                    this.rol = Integer.parseInt(vector_result[i][11]);
                }
                
                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion not in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_disponibles = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_disponibles.add(vector_result[i][0]);
                }

                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_asignadas = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_asignadas.add(vector_result[i][0]);
                }

                this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);

                this.txtNombreCompleto = true;
                this.txtUsuario = true;
                this.txtContrasena = true;
                this.txtReContrasena = true;
                this.somTipoUsuario = true;
                this.sorGestor = true;
                this.sorProcurador = true;
                this.sorAsistente = true;
                this.sorDigitador = true;
                this.sorInvestigador = true;
                this.itaDescripcion = true;
                this.somRol = true;
                this.btnAceptar = false;
                this.btnCancelar = false;
                this.pkCorporaciones = true;

                this.opcion = "ELIMINAR";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Dede seleccionar un usuario del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(carga_info_eliminar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_activar() {
        try {
            if (this.selectedUsuario != null) {
                String cadenasql = "select "
                        + "u.nombre_completo, "
                        + "u.nombre, "
                        + "u.contrasena, "
                        + "u.descripcion, "
                        + "u.gestor, "
                        + "u.procurador, "
                        + "u.asistente, "
                        + "u.digitador, "
                        + "u.investigador, "
                        + "u.tipo_usuario, "
                        + "u.reinicio, "
                        + "u.rol "
                        + "from "
                        + "usuario u "
                        + "where "
                        + "u.usuario = " + this.selectedUsuario.getUsuario();
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

                for (Integer i = 1; i < filas; i++) {
                    this.nombre_completo_d = vector_result[i][0];
                    this.nombre_d = vector_result[i][1];
                    this.contrasena_d = vector_result[i][2];
                    this.recontrasena_d = vector_result[i][2];
                    this.descripcion_d = vector_result[i][3];
                    this.gestor_d = vector_result[i][4];
                    this.procurador_d = vector_result[i][5];
                    this.asistente_d = vector_result[i][6];
                    this.digitador_d = vector_result[i][7];
                    this.investigador_d = vector_result[i][8];
                    this.tipo_usuario_d = Integer.parseInt(vector_result[i][9]);
                    this.reinicio = Integer.parseInt(vector_result[i][10]);
                    this.rol = Integer.parseInt(vector_result[i][11]);
                }
                
                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion not in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_disponibles = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_disponibles.add(vector_result[i][0]);
                }

                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_asignadas = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_asignadas.add(vector_result[i][0]);
                }

                this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);

                this.txtNombreCompleto = true;
                this.txtUsuario = true;
                this.txtContrasena = true;
                this.txtReContrasena = true;
                this.somTipoUsuario = true;
                this.sorGestor = true;
                this.sorProcurador = true;
                this.sorAsistente = true;
                this.sorDigitador = true;
                this.sorInvestigador = true;
                this.itaDescripcion = true;
                this.somRol = true;
                this.btnAceptar = false;
                this.btnCancelar = false;
                this.pkCorporaciones = true;

                this.opcion = "ACTIVAR";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Dede seleccionar un usuario del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(carga_info_activar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void carga_info_ver() {
        try {
            if (this.selectedUsuario != null) {
                String cadenasql = "select "
                        + "u.nombre_completo, "
                        + "u.nombre, "
                        + "u.contrasena, "
                        + "u.descripcion, "
                        + "u.gestor, "
                        + "u.procurador, "
                        + "u.asistente, "
                        + "u.digitador, "
                        + "u.investigador, "
                        + "u.tipo_usuario, "
                        + "u.reinicio, "
                        + "u.rol "
                        + "from "
                        + "usuario u "
                        + "where "
                        + "u.usuario = " + this.selectedUsuario.getUsuario();
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

                for (Integer i = 1; i < filas; i++) {
                    this.nombre_completo_d = vector_result[i][0];
                    this.nombre_d = vector_result[i][1];
                    this.contrasena_d = vector_result[i][2];
                    this.recontrasena_d = vector_result[i][2];
                    this.descripcion_d = vector_result[i][3];
                    this.gestor_d = vector_result[i][4];
                    this.procurador_d = vector_result[i][5];
                    this.asistente_d = vector_result[i][6];
                    this.digitador_d = vector_result[i][7];
                    this.investigador_d = vector_result[i][8];
                    this.tipo_usuario_d = Integer.parseInt(vector_result[i][9]);
                    this.reinicio = Integer.parseInt(vector_result[i][10]);
                    this.rol = Integer.parseInt(vector_result[i][11]);
                }
                
                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion not in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_disponibles = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_disponibles.add(vector_result[i][0]);
                }

                cadenasql = "select "
                        + "c.nombre "
                        + "from "
                        + "cooperacion c "
                        + "where "
                        + "c.cooperacion in (select uc.corporacion from usuario_corporacion uc where uc.usuario=" + this.selectedUsuario.getUsuario() + ")";
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

                this.lst_corporaciones_asignadas = new ArrayList<>();
                for (Integer i = 1; i < filas; i++) {
                    this.lst_corporaciones_asignadas.add(vector_result[i][0]);
                }

                this.lst_corporaciones = new DualListModel<>(this.lst_corporaciones_disponibles, this.lst_corporaciones_asignadas);

                this.txtNombreCompleto = true;
                this.txtUsuario = true;
                this.txtContrasena = true;
                this.txtReContrasena = true;
                this.somTipoUsuario = true;
                this.sorGestor = true;
                this.sorProcurador = true;
                this.sorAsistente = true;
                this.sorDigitador = true;
                this.sorInvestigador = true;
                this.itaDescripcion = true;
                this.somRol = true;
                this.btnAceptar = true;
                this.btnCancelar = false;
                this.pkCorporaciones = true;

                this.opcion = "VER";

                RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", "Dede seleccionar un usuario del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(carga_info_ver): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public void reiniciar_contrasena() {
        try {
            if (this.selectedUsuario != null) {
                Driver driver = new Driver();
                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                String resultado = servicio.reiniciarContrasena(id_usuario, this.selectedUsuario.getUsuario(), this.selectedUsuario.getContrasena(), this.selectedUsuario.getNombre(), this.ambiente);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Dede seleccionar un usuario del listado."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(reiniciar_contrasena): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }
    
    public void aceptar() {
        if (this.opcion.equals("INSERTAR")) {
            this.insertar();
        }
        if (this.opcion.equals("MODIFICAR")) {
            this.modificar();
        }
        if (this.opcion.equals("ELIMINAR")) {
            this.eliminar();
        }
        if (this.opcion.equals("ACTIVAR")) {
            this.activar();
        }
    }

    private void insertar() {
        try {
            if (!this.nombre_completo_d.equals("")) {
                if (!this.nombre_d.equals("")) {
                    if (this.contrasena_d.equals(this.recontrasena_d)) {
                        Driver driver = new Driver();
                        Servicio servicio = new Servicio();
                        Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                        String resultado = servicio.usuarioInsertar(
                                id_usuario,
                                this.nombre_completo_d,
                                this.nombre_d,
                                this.contrasena_d,
                                this.recontrasena_d,
                                this.descripcion_d,
                                this.gestor_d,
                                this.procurador_d,
                                this.asistente_d,
                                this.digitador_d,
                                this.investigador_d,
                                this.tipo_usuario_d,
                                1,
                                this.rol,
                                this.lst_corporaciones.getTarget(),
                                this.ambiente);
                        this.constructor();
                        RequestContext.getCurrentInstance().execute("PF('dtblWidgetUsu').clearFilters();");

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Las contraseñas no coinciden verifique."));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe ingresar el usuario que utilizar para acceder al sistema."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe ingresar el nombre del usuario."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(insertar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void modificar() {
        try {
            if (!this.nombre_completo_d.equals("")) {
                if (!this.nombre_d.equals("")) {
                    if (this.contrasena_d.equals(this.recontrasena_d)) {
                        Driver driver = new Driver();
                        Servicio servicio = new Servicio();
                        Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                        String resultado = servicio.usuarioModificar(
                                id_usuario,
                                this.selectedUsuario.getUsuario(),
                                this.nombre_completo_d,
                                this.nombre_d,
                                this.contrasena_d,
                                this.descripcion_d,
                                this.gestor_d,
                                this.procurador_d,
                                this.asistente_d,
                                this.digitador_d,
                                this.investigador_d,
                                this.tipo_usuario_d,
                                this.reinicio,
                                this.rol,
                                this.lst_corporaciones.getTarget(),
                                this.ambiente);
                        this.constructor();
                        RequestContext.getCurrentInstance().execute("PF('dtblWidgetUsu').clearFilters();");

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Las contraseñas no coinciden verifique."));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe ingresar el usuario que va utilizar para acceder al sistema."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe ingresar el nombre del usuario."));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(modificar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void eliminar() {
        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.usuarioEliminar(
                    id_usuario,
                    this.selectedUsuario.getUsuario(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetUsu').clearFilters();");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(eliminar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    private void activar() {
        try {
            Driver driver = new Driver();
            Servicio servicio = new Servicio();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            String resultado = servicio.usuarioActivar(
                    id_usuario,
                    this.selectedUsuario.getUsuario(),
                    this.ambiente);
            this.constructor();
            RequestContext.getCurrentInstance().execute("PF('dtblWidgetUsu').clearFilters();");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema...", resultado));
        } catch (Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Usuario(activar): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema...", ex.toString()));
        }
    }

    public String reinicio_convert(int value) {
        String retorno;
        
        if (value == 0) {
            retorno = "";
        } else {
            retorno = "Reiniciada";
        }
        
        return retorno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Usuario_List getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario_List selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public List<Usuario_List> getLst_usuario() {
        return lst_usuario;
    }

    public void setLst_usuario(List<Usuario_List> lst_usuario) {
        this.lst_usuario = lst_usuario;
    }

    public List<SelectItem> getLst_rol() {
        return lst_rol;
    }

    public void setLst_rol(List<SelectItem> lst_rol) {
        this.lst_rol = lst_rol;
    }

    public List<SelectItem> getLst_cartera() {
        return lst_cartera;
    }

    public void setLst_cartera(List<SelectItem> lst_cartera) {
        this.lst_cartera = lst_cartera;
    }

    public String getNombre_completo_d() {
        return nombre_completo_d;
    }

    public void setNombre_completo_d(String nombre_completo_d) {
        this.nombre_completo_d = nombre_completo_d;
    }

    public String getNombre_d() {
        return nombre_d;
    }

    public void setNombre_d(String nombre_d) {
        this.nombre_d = nombre_d;
    }

    public String getContrasena_d() {
        return contrasena_d;
    }

    public void setContrasena_d(String contrasena_d) {
        this.contrasena_d = contrasena_d;
    }

    public String getRecontrasena_d() {
        return recontrasena_d;
    }

    public void setRecontrasena_d(String recontrasena_d) {
        this.recontrasena_d = recontrasena_d;
    }

    public String getDescripcion_d() {
        return descripcion_d;
    }

    public void setDescripcion_d(String descripcion_d) {
        this.descripcion_d = descripcion_d;
    }

    public String getGestor_d() {
        return gestor_d;
    }

    public void setGestor_d(String gestor_d) {
        this.gestor_d = gestor_d;
    }

    public String getProcurador_d() {
        return procurador_d;
    }

    public void setProcurador_d(String procurador_d) {
        this.procurador_d = procurador_d;
    }

    public String getAsistente_d() {
        return asistente_d;
    }

    public void setAsistente_d(String asistente_d) {
        this.asistente_d = asistente_d;
    }

    public String getDigitador_d() {
        return digitador_d;
    }

    public void setDigitador_d(String digitador_d) {
        this.digitador_d = digitador_d;
    }

    public String getInvestigador_d() {
        return investigador_d;
    }

    public void setInvestigador_d(String investigador_d) {
        this.investigador_d = investigador_d;
    }

    public Integer getTipo_usuario_d() {
        return tipo_usuario_d;
    }

    public void setTipo_usuario_d(Integer tipo_usuario_d) {
        this.tipo_usuario_d = tipo_usuario_d;
    }

    public Integer getReinicio() {
        return reinicio;
    }

    public void setReinicio(Integer reinicio) {
        this.reinicio = reinicio;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getRol_label() {
        return rol_label;
    }

    public void setRol_label(String rol_label) {
        this.rol_label = rol_label;
    }

    public Boolean getTxtNombreCompleto() {
        return txtNombreCompleto;
    }

    public void setTxtNombreCompleto(Boolean txtNombreCompleto) {
        this.txtNombreCompleto = txtNombreCompleto;
    }

    public Boolean getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(Boolean txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public Boolean getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(Boolean txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public Boolean getTxtReContrasena() {
        return txtReContrasena;
    }

    public void setTxtReContrasena(Boolean txtReContrasena) {
        this.txtReContrasena = txtReContrasena;
    }

    public Boolean getSomTipoUsuario() {
        return somTipoUsuario;
    }

    public void setSomTipoUsuario(Boolean somTipoUsuario) {
        this.somTipoUsuario = somTipoUsuario;
    }

    public Boolean getSorGestor() {
        return sorGestor;
    }

    public void setSorGestor(Boolean sorGestor) {
        this.sorGestor = sorGestor;
    }

    public Boolean getSorProcurador() {
        return sorProcurador;
    }

    public void setSorProcurador(Boolean sorProcurador) {
        this.sorProcurador = sorProcurador;
    }

    public Boolean getSorAsistente() {
        return sorAsistente;
    }

    public void setSorAsistente(Boolean sorAsistente) {
        this.sorAsistente = sorAsistente;
    }

    public Boolean getSorDigitador() {
        return sorDigitador;
    }

    public void setSorDigitador(Boolean sorDigitador) {
        this.sorDigitador = sorDigitador;
    }

    public Boolean getSorInvestigador() {
        return sorInvestigador;
    }

    public void setSorInvestigador(Boolean sorInvestigador) {
        this.sorInvestigador = sorInvestigador;
    }

    public Boolean getItaDescripcion() {
        return itaDescripcion;
    }

    public void setItaDescripcion(Boolean itaDescripcion) {
        this.itaDescripcion = itaDescripcion;
    }

    public Boolean getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(Boolean btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public Boolean getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(Boolean btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public Boolean getSomRol() {
        return somRol;
    }

    public void setSomRol(Boolean somRol) {
        this.somRol = somRol;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public DualListModel<String> getLst_corporaciones() {
        return lst_corporaciones;
    }

    public void setLst_corporaciones(DualListModel<String> lst_corporaciones) {
        this.lst_corporaciones = lst_corporaciones;
    }

    public List<String> getLst_corporaciones_disponibles() {
        return lst_corporaciones_disponibles;
    }

    public void setLst_corporaciones_disponibles(List<String> lst_corporaciones_disponibles) {
        this.lst_corporaciones_disponibles = lst_corporaciones_disponibles;
    }

    public List<String> getLst_corporaciones_asignadas() {
        return lst_corporaciones_asignadas;
    }

    public void setLst_corporaciones_asignadas(List<String> lst_corporaciones_asignadas) {
        this.lst_corporaciones_asignadas = lst_corporaciones_asignadas;
    }

    public Boolean getPkCorporaciones() {
        return pkCorporaciones;
    }

    public void setPkCorporaciones(Boolean pkCorporaciones) {
        this.pkCorporaciones = pkCorporaciones;
    }
    
}
