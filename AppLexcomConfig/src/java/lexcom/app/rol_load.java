/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexcom.app;

/**
 *
 * @author Administrador
 */
public class rol_load {
    
    private int rol ;
    private String  menu ;
    private String  ver ;

    public rol_load(int rol, String menu, String ver) {
        this.rol = rol;
        this.menu = menu;
        this.ver = ver;
    }
    
    

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
    
    
    
    
}
