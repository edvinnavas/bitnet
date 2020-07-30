package lexcom.app;

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

@ManagedBean(name = "Carga_Masiva_Deudores")
@ViewScoped
public class Carga_Masiva_Deudores implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();
        } catch (Exception ex) {
            try {
                System.out.println("ERROR => AppLexcomCargas-Carga_Masiva_Deudores(init): " + ex.toString());
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (Exception ex1) {
                System.out.println("ERROR => AppLexcomCargas-Carga_Masiva_Deudores(init - redirect): " + ex1.toString());
            }
        }
    }

    public void constructor() {
        this.file = null;
    }

    public void cargar() {
        Driver driver = new Driver();

        try {
            if (this.file != null) {
                File destFile = new File(driver.getPath() + this.usuario + "_carga_masiva_deudores.xlsx");
                FileUtils.copyInputStreamToFile(this.file.getInputstream(), destFile);

                Servicio servicio = new Servicio();
                Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
                String resultado = servicio.cargaMasivaDeudoroes(id_usuario, driver.getPath() + this.usuario + "_carga_masiva_deudores.xlsx", this.ambiente);

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

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

}
