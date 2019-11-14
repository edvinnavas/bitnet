package com.lexcom_cargas;

import java.io.Serializable;
import java.lang.reflect.Field;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "accese_get")
@ViewScoped
public class acceso_get implements Serializable {

    private static final long serialVersionUID = 1L;
    acceso_menu s = new acceso_menu();

    @PostConstruct
    public void init() {

    }

    public acceso_get() {
        s.init();
    }

    public String getpermiso(String menu) {
        String retorno = "false";
        
        menu = menu.replaceAll(" ", "_");
        menu = menu.replaceAll("á", "a");
        menu = menu.replaceAll("é", "e");
        menu = menu.replaceAll("í", "i");
        menu = menu.replaceAll("ó", "o");
        menu = menu.replaceAll("ú", "u");
        try {
            Field[] f = acceso_menu.class.getDeclaredFields();
            int strIndex = 0;

            while (!f[strIndex].getName().equals(menu) || f.length < strIndex) {
                strIndex++;
            }

            retorno = f[strIndex].get(s).toString();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return retorno;
    }
    
}
