package lexcom.app;

import java.io.Serializable;

public class Permiso_Expediente_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id_usuario;
    private String usuario;
    private String menu;
    private String permiso;

    public Permiso_Expediente_List(Integer id_usuario, String usuario, String menu, String permiso) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.menu = menu;
        this.permiso = permiso;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
    
}
