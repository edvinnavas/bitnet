package com.lexcom_cargas;

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

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();

            this.pagina_seleccionada = "inicio";
        } catch(Exception ex) {
            try {
                System.out.println("ERROR JAVABEAN INDEX: " + ex.toString());
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch(Exception ex1) {
                System.out.println("ERROR JAVABEAN INDEX: " + ex1.toString());
            }
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
    
    public void Salir() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch(Exception ex) {
            System.out.println("ERROR JAVABEAN INDEX: " + ex.toString());
        }
    }

}
