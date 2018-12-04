package com.lexcom;

import java.io.Serializable;

public class Lista3 implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    Nodo3 inicio;
    Nodo3 fin;
    
    public Lista3() {
        inicio = null;
        fin = null;
    }
    
    public void insertar(Integer deudor, Integer gestor, Integer iteracion) {
        Nodo3 nuevo = new Nodo3(deudor, gestor, iteracion);
        
        try {
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
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista3(insertar): " + ex.toString());
        }
    }
    
    public Boolean permite_asignacion(Integer deudor, Integer gestor) {
        Boolean resultado = true;
        Nodo3 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(recorre.getGestor().equals(gestor) && recorre.getDeudor().equals(deudor) && recorre.getIteracion() < 3) {
                    resultado = false;
                    recorre.setIteracion(recorre.getIteracion() + 1);
                    recorre = fin;
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista3(permite_asignacion): " + ex.toString());
        }
        
        return resultado;
    }
    
}
