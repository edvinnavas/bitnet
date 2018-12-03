package lexcom.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "Index")
@SessionScoped
public class Index implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String contrasena;
    private String Nes_contrasena;
    private String newRender;
    private String CommonRender;
    private String ambiente;
    private String mensaje_reinicio;
    private Integer reinicio;

    private List<SelectItem> Lista_usuarios = new ArrayList<>();
    private List<SelectItem> Lista_ambientes = new ArrayList<>();

    @PostConstruct
    public void init() {
        Driver drive = new Driver();
        
        this.Lista_ambientes = drive.lista_ambientes();
        this.ambiente = "LEXCOMJNDI";
        String lista_usuarios_sql = "select u.nombre, u.nombre from usuario u where u.estado = 'VIGENTE'";
        this.Lista_usuarios = drive.lista_SelectItem_simple(lista_usuarios_sql, this.ambiente);
        this.usuario = this.Lista_usuarios.get(0).getValue().toString();
        this.newRender = "false";
        this.CommonRender = "true";
        this.validar_reinicio_usuario();
    }

    public void validar_reinicio_usuario() {
        try {
            Driver drive = new Driver();
            String cadenasql = "select u.reinicio from usuario u where u.nombre='" + this.usuario + "'";
            this.reinicio = drive.getInt(cadenasql, ambiente);
            if(this.reinicio == 1) {
                this.mensaje_reinicio = "Reinicio de contrase requerido (PASS=" + this.usuario + ").";
            } else {
                this.mensaje_reinicio = "-";
            }
        } catch (Exception ex) {
            System.out.println("ERROR => APP-Reportes-Index(validar_reinicio_usuario): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", ex.toString()));
        }
    }
    
    public String login() {
        String resultado = "index";

        try {
            Servicio servicio = new Servicio();
            resultado = servicio.logueo(this.usuario, this.contrasena, this.ambiente);
            
            if (resultado.equals("menu")) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("id_usuario", this.usuario);
                session.setAttribute("ambiente", this.ambiente);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Bienvenido " + this.usuario + " al sistema Lexcom."));
            } else if (resultado.equals("reinicio")) {
                this.newRender = "true";
                this.CommonRender = "false";
                resultado = "index";
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Debe cambiar su contraseña ingrese una nueva!"));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form1");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Usuario o contraseña incorrectos!"));
            }
        } catch (Exception ex) {
            System.out.println("ERROR => APP-Reportes-Index(login): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", ex.toString()));
        }

        return resultado;
    }

    public String loginCambio() {
        String resultado = "index";

        try {
            if (this.Nes_contrasena.equals(this.contrasena)) {
                resultado = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "La contraseña nueva y la antigua deben ser diferentes."));
            } else {
                Servicio servicio = new Servicio();
                resultado = servicio.logueoCambio(this.usuario, this.contrasena, this.Nes_contrasena, this.ambiente);

                if (resultado.equals("menu")) {
                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    session.setAttribute("id_usuario", this.usuario);
                    session.setAttribute("ambiente", this.ambiente);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Bienvenido " + this.usuario + " al sistema Lexcom."));
                } else if (resultado.equals("Ingrese contraeña antigua")) {
                    this.CommonRender = "false";
                    this.newRender = "true";
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "La contraseña antigua no es correcta."));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form1");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Usuario o contraseña incorrectos!"));
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => APP-Reportes-Index(loginCambio): " + ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", ex.toString()));
        }

        return resultado;
    }
    
    public void cambiar_ambiente() {
        try {
            Driver drive = new Driver();
            String lista_usuarios_sql = "select u.nombre, u.nombre from usuario u where u.estado = 'VIGENTE'";
            this.Lista_usuarios = drive.lista_SelectItem_simple(lista_usuarios_sql, this.ambiente);
        } catch(Exception ex) {
            System.out.println("ERROR => APP-Reportes-Index(cambiar_ambiente): " + ex.toString());
            System.out.println(ex.toString());
        }
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<SelectItem> getLista_usuarios() {
        return Lista_usuarios;
    }

    public void setLista_usuarios(List<SelectItem> Lista_usuarios) {
        this.Lista_usuarios = Lista_usuarios;
    }

    public String getNewRender() {
        return newRender;
    }

    public void setNewRender(String newRender) {
        this.newRender = newRender;
    }

    public String getCommonRender() {
        return CommonRender;
    }

    public void setCommonRender(String CommonRender) {
        this.CommonRender = CommonRender;
    }

    public String getNes_contrasena() {
        return Nes_contrasena;
    }

    public void setNes_contrasena(String Nes_contrasena) {
        this.Nes_contrasena = Nes_contrasena;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public List<SelectItem> getLista_ambientes() {
        return Lista_ambientes;
    }

    public void setLista_ambientes(List<SelectItem> Lista_ambientes) {
        this.Lista_ambientes = Lista_ambientes;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensaje_reinicio() {
        return mensaje_reinicio;
    }

    public void setMensaje_reinicio(String mensaje_reinicio) {
        this.mensaje_reinicio = mensaje_reinicio;
    }

    public Integer getReinicio() {
        return reinicio;
    }

    public void setReinicio(Integer reinicio) {
        this.reinicio = reinicio;
    }
    
}
