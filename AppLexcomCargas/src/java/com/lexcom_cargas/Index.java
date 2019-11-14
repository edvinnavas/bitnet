package com.lexcom_cargas;

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
    
    private String nombre;
    private String contrasena;
    private String Nes_contrasena;
    private String newRender="false";
    private String CommonRender="true";
    
    private List<SelectItem> Lista_usuarios = new ArrayList<>();

    @PostConstruct
    public void init() {
        Driver drive = new Driver();
        String lista_usuarios_sql = "select u.nombre, u.nombre from usuario u where u.estado = 'VIGENTE'";
        this.Lista_usuarios = drive.lista_SelectItem_simple(lista_usuarios_sql);
    }
    
    public String login() {
        String resultado = "index";
        
        try {
            Servicio servicio = new Servicio();
            resultado = servicio.logueo(this.nombre, this.contrasena, "LEXCOMJNDI");
            if(resultado.equals("menu")) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("id_usuario", this.nombre);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Bienvenido " + this.nombre + " al sistema Lexcom."));
            } else if(resultado.equals("reinicio")) {
            this.CommonRender="false";
            this.newRender="true";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Debe cambiar su contraseña ingrese una nueva!"));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form1");
            
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Usuario o contraseña incorrectos!"));
            }
        } catch(Exception ex) {
            System.out.println("ERROR JAVABEAN INDEX: " + ex.toString());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", ex.toString()));
        }
        
        return resultado;
    }
    
     public String loginCambio() {
        String resultado = "index";
        
        try {
            if(this.Nes_contrasena.equals(this.contrasena)){
                resultado="";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "La contraseña nueva y la antigua deben ser diferentes."));
            }else{
            
            
            Servicio servicio = new Servicio();
            resultado = servicio.logueoCambio(this.nombre, this.contrasena,this.Nes_contrasena, "LEXCOMJNDI");
            
            if(resultado.equals("menu")) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("id_usuario", this.nombre);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Bienvenido " + this.nombre + " al sistema Lexcom."));
            } else if(resultado.equals("Ingrese contraeña antigua")) {
            this.CommonRender="false";
            this.newRender="true";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "La contraseña antigua no es correcta"));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form1");
            
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", "Usuario o contraseña incorrectos!"));
            }
            
            }
        } catch(Exception ex) {
            System.out.println("ERROR JAVABEAN INDEX: " + ex.toString());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje del sistema...", ex.toString()));
        }
        
        return resultado;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    
}
