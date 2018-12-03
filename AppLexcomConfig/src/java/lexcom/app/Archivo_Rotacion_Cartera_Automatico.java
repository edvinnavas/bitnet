package lexcom.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "Archivo_Rotacion_Cartera_Automatico")
@ViewScoped
public class Archivo_Rotacion_Cartera_Automatico implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String archivo;
    private String usuario;
    private String ambiente;

    private Integer cartera;
    private List<SelectItem> lst_cartera;
    
    private StreamedContent file;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.usuario = session.getAttribute("id_usuario").toString();
        this.ambiente = session.getAttribute("ambiente").toString();

        Driver drive = new Driver();
        String cadenasql_lst_cartera = "select c.cartera, c.nombre from cartera c where c.estado='VIGENTE' order by c.nombre";
        this.lst_cartera = drive.lista_SelectItem_simple(cadenasql_lst_cartera, this.ambiente);
        
        this.archivo = drive.getPath() + this.usuario + "_archivo_rotacion_cartera_automatico.xls";
        this.file = null;
    }
    
    public void constructor () {
        Driver drive = new Driver();
        String cadenasql_lst_cartera = "select c.cartera, c.nombre from cartera c where c.estado='VIGENTE' order by c.nombre";
        this.lst_cartera = drive.lista_SelectItem_simple(cadenasql_lst_cartera, this.ambiente);
        
        this.archivo = drive.getPath() + this.usuario + "_archivo_rotacion_cartera_automatico.xls";
        this.file = null;
    }

    public void generar_reporte() {
        try {
            Driver driver = new Driver();
            Integer id_usuario = driver.getInt("select u.usuario from usuario u where u.nombre = '" + this.usuario + "'", this.ambiente);
            
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.rotacionAutomatica(id_usuario, this.cartera, this.ambiente);
            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] reporte_excel = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    reporte_excel[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            driver.generar_reporte_excel(this.archivo, reporte_excel, filas, columnas);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            InputStream stream = new FileInputStream(this.archivo);
            file = new DefaultStreamedContent(stream, "resources/img/Encabezado.png", "ArchivoRotacionAutomatica_" + sdf.format(cal.getTime()) + ".xls");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            FacesMessage msg = new FacesMessage("Mensaje del sistema...", ex.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getCartera() {
        return cartera;
    }

    public void setCartera(Integer cartera) {
        this.cartera = cartera;
    }

    public List<SelectItem> getLst_cartera() {
        return lst_cartera;
    }

    public void setLst_cartera(List<SelectItem> lst_cartera) {
        this.lst_cartera = lst_cartera;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
}
