package lexcom.app;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "Menu")
@ViewScoped
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pagina_seleccionada;
    private String usuario;
    private String ambiente;
    private String nombre_ambiente;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.pagina_seleccionada = "inicio";
            this.ambiente = session.getAttribute("ambiente").toString();
            if(this.ambiente.equals("LEXCOMJNDI")) {
                this.nombre_ambiente = "Asesoría Jurídica LEXCOM - Ambiente de producción.";
            } else {
                this.nombre_ambiente = "Asesoría Jurídica LEXCOM - Ambiente de pruebas.";
            }
        } catch(Exception ex) {
            try {
                System.out.println("ERROR => AppLexcomConfig-Menu(init): " + ex.toString());
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch(Exception ex1) {
                System.out.println("ERROR => AppLexcomConfig-Menu(init - redirect): " + ex1.toString());
            }
        }
    }
    
    public void Salir() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch(Exception ex) {
            System.out.println("ERROR => AppLexcomConfig-Menu(Salir): " + ex.toString());
        }
    }

    public String getPagina_seleccionada() {
        return pagina_seleccionada + ".xhtml";
    }

    public void setPagina_seleccionada(String pagina_seleccionada) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("id_usuario", this.usuario);

        this.pagina_seleccionada = pagina_seleccionada;
    }

    public String getUsuario() {
        return "Usuario: " + usuario;
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

    public String getNombre_ambiente() {
        return nombre_ambiente;
    }

    public void setNombre_ambiente(String nombre_ambiente) {
        this.nombre_ambiente = nombre_ambiente;
    }
    
}
