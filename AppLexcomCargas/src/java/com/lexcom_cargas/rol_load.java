package com.lexcom_cargas;

import java.io.Serializable;

public class rol_load implements Serializable {

    private static final long serialVersionUID = 1L;

    private int rol;
    private String menu;
    private String ver;

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
