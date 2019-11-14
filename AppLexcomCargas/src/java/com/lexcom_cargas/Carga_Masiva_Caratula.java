package com.lexcom_cargas;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "Carga_Masiva_Caratula")
@ViewScoped
public class Carga_Masiva_Caratula implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
    }
    
    public void constructor () {
        this.file = null;
    }

    public void cargar() {
        Driver driver = new Driver();
        
        try {
            if (this.file != null) {
                File destFile = new File(driver.getPath() + this.usuario + "_carga_masiva_caratula.xlsx");
                FileUtils.copyInputStreamToFile(this.file.getInputstream(), destFile);
                
                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'");
                String resultado = servicio.cargaMasivaCaratula(id_usuario, driver.getPath() + this.usuario + "_carga_masiva_caratula.xlsx","LEXCOMJNDI");
                
                FacesMessage msg = new FacesMessage("Mensaje del sistema...", resultado);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (IOException | IndexOutOfBoundsException ex) {
            System.out.println(ex.toString());
        }
        driver = null;
        
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
