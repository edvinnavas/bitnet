package com.lexcom.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Lista {
    
    Nodo inicio;
    Nodo fin;
    
    public Lista() {
        inicio = null;
        fin = null;
    }
    
    public void insertar(String deudor, String caso, String antiguedad, String fecha_recepcion, String no_cuenta, String nombre_deudor, Double monto_inicial, String garantia, String estado, String fecha_proximo_pago, String cuota_proximo_pago, String gestor, String codigo_contactabilidad, String actor) {
        Nodo nuevo = new Nodo(deudor, caso, antiguedad, fecha_recepcion, no_cuenta, nombre_deudor, monto_inicial, garantia, estado, fecha_proximo_pago, cuota_proximo_pago, gestor, codigo_contactabilidad, actor);
        
        if(inicio == null) {
            inicio = nuevo; 
            fin = nuevo;
            inicio.siguiente = null;
            inicio.anterior = null;
        } else {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            fin = nuevo;
        }
    }
    
    public Integer no_casos_sin_gestor() {
        Integer resultado = 0;
        Nodo recorre = null;
        
        recorre = inicio;
        while(recorre != null) {
            if(recorre.getGestor().equals("-1")) {
                resultado = resultado + 1;
            }
            recorre = recorre.siguiente;
        }
        
        return resultado;
    }
    
    public Lista1 cartera_resumen() {
        Lista1 cartera_resumen = new Lista1();
        Nodo recorre = null;
        
        recorre = inicio;
        while(recorre != null) {
            cartera_resumen.insertar(recorre.getGestor(), 1, recorre.getMonto_inicial());
            recorre = recorre.siguiente;
        }
        
        return cartera_resumen;
    }
    
    public void actualizar_cartera_deudores(Integer dif_max_min, String gestor) {
        if(no_casos_sin_gestor() < dif_max_min) {
            dif_max_min = no_casos_sin_gestor();
        }
        for(Integer i=0; i<dif_max_min; i++) {
            Double monto_minimo = 0.00;
            Nodo aux = null;
            Nodo recorre = inicio;
            while(recorre != null) {
                if(monto_minimo == 0.00 && recorre.getGestor().equals("-1")) {
                    monto_minimo = recorre.getMonto_inicial();
                    aux = recorre;
                }
                if(recorre.getMonto_inicial() < monto_minimo && recorre.getGestor().equals("-1")) {
                    monto_minimo = recorre.getMonto_inicial();
                    aux = recorre;
                }
                recorre = recorre.siguiente;
            }
            aux.setGestor(gestor);
        }
    }
    
    public void actualizar_cartera_monto(String gestor) {
        Double monto_maximo = 0.00;
        Nodo aux = null;
        Nodo recorre = inicio;
        
        while(recorre != null) {
            if(monto_maximo == 0.00 && recorre.getGestor().equals("-1")) {
                monto_maximo = recorre.getMonto_inicial();
                aux = recorre;
            }
            if(recorre.getMonto_inicial() > monto_maximo && recorre.getGestor().equals("-1")) {
                monto_maximo = recorre.getMonto_inicial();
                aux = recorre;
            }
            recorre = recorre.siguiente;
        }
        
        aux.setGestor(gestor);
    }
    
    public void tabla(String tabla, Connection conn) {
        try {
            conn.setAutoCommit(false);
            
            String cadenasql = "delete from " + tabla + " where temp_reporte_rotacion<>-1";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            
            Nodo recorre = inicio;
            Integer contador = 1;
            while(recorre != null) {
                cadenasql = "insert into " + tabla + "("
                        + "temp_reporte_rotacion,"
                        + "deudor,"
                        + "caso,"
                        + "antiguedad,"
                        + "fecha_recepcion,"
                        + "no_cuenta,"
                        + "nombre_deudor,"
                        + "monto_inicial,"
                        + "garantia,"
                        + "estado,"
                        + "fecha_proximo_pago,"
                        + "cuota_proximo_pago,"
                        + "gestor,"
                        + "codigo_contactabilidad,"
                        + "actor) values ('"
                        + contador + "','"
                        + recorre.getDeudor() + "','"
                        + recorre.getCaso() + "','"
                        + recorre.getAntiguedad() + "','"
                        + recorre.getFecha_recepcion() + "','"
                        + recorre.getNo_cuenta() + "','"
                        + recorre.getNombre_deudor() + "','"
                        + recorre.getMonto_inicial() + "','"
                        + recorre.getGarantia() + "','"
                        + recorre.getEstado() + "','"
                        + recorre.getFecha_proximo_pago() + "','"
                        + recorre.getCuota_proximo_pago() + "','"
                        + recorre.getGestor() + "','"
                        + recorre.getCodigo_contactabilidad() + "','"
                        + recorre.getActor() + "')";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                contador++;
                recorre = recorre.siguiente;
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch(Exception ex) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                System.out.println(ex.toString());
            } catch (SQLException ex1) {
                System.out.println(ex1.toString());
            }
        }
    }
    
}
