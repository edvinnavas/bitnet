package com.lexcom.rotacion;

import java.io.Serializable;

public class Lista1 implements Serializable {
    private static final long serialVersionUID = 1L;
    
    Nodo1 inicio;
    Nodo1 fin;
    
    public Lista1() {
        inicio = null;
        fin = null;
    }
    
    public void insertar(String gestor, Integer no_deudores, Double monto) {
        Nodo1 nuevo = new Nodo1(gestor, no_deudores, monto);
        Boolean existe = false;
        
        try {
            if(inicio == null) {
                inicio = nuevo; 
                fin = nuevo;
                inicio.siguiente = null;
                inicio.anterior = null;
            } else {
                Nodo1 recorre = null;
                recorre = inicio;
                while(recorre != null) {
                    if(recorre.getGestor().equals(gestor)) {
                        recorre.setNo_deudores(recorre.getNo_deudores() + 1);
                        recorre.setMonto(recorre.getMonto() + monto);
                        existe = true;
                    }
                    recorre = recorre.siguiente;
                }
                if(!existe) {
                    fin.siguiente = nuevo;
                    nuevo.anterior = fin;
                    fin = nuevo;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(insertar): " + ex.toString());
        }
    }
    
    public Integer total_no_deudores() {
        Integer resultado = 0;
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                resultado = resultado + recorre.getNo_deudores();
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(total_no_deudores): " + ex.toString());
        }
        
        return resultado;
    }
    
    public Double total_monto() {
        Double resultado = 0.00;
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                resultado = resultado + recorre.getMonto();
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(total_monto): " + ex.toString());
        }
        
        return resultado;
    }
    
    public Integer no_casos_sin_gestor() {
        Integer resultado = 0;
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(no_casos_sin_gestor): " + ex.toString());
        }
        
        return resultado;
    }
    
    public Integer cartera_minimo() {
        Integer resultado = 0;
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(resultado == 0 && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                }
                if(recorre.getNo_deudores() < resultado && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(cartera_minimo): " + ex.toString());
        }
        
        return resultado;
    }
    
    public String gestor_cartera_minimo() {
        Integer resultado = 0;
        String gestor = "";
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(resultado == 0 && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                    gestor = recorre.getGestor();
                }
                if(recorre.getNo_deudores() < resultado && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                    gestor = recorre.getGestor();
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(gestor_cartera_minimo): " + ex.toString());
        }
        
        return gestor;
    }
    
    public Integer cartera_maximo() {
        Integer resultado = 0;
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(resultado == 0 && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                }
                if(recorre.getNo_deudores() > resultado && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getNo_deudores();
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(cartera_maximo): " + ex.toString());
        }
        
        return resultado;
    }
    
    public Double monto_minimo() {
        Double resultado = 0.00;
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(resultado == 0 && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getMonto();
                }
                if(recorre.getMonto() < resultado && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getMonto();
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(monto_minimo): " + ex.toString());
        }
        
        return resultado;
    }
    
    public String gestor_monto_minimo() {
        Double resultado = 0.00;
        String gestor = "";
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                if(resultado == 0 && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getMonto();
                    gestor = recorre.getGestor();
                }
                if(recorre.getMonto() < resultado && !recorre.getGestor().equals("-1")) {
                    resultado = recorre.getMonto();
                    gestor = recorre.getGestor();
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(gestor_monto_minimo): " + ex.toString());
        }
        
        return gestor;
    }
    
    public void insertar_faltante(String gestor) {
        Nodo1 nuevo = new Nodo1(gestor, 0, 0.00);
        
        try {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            fin = nuevo;
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(insertar_faltante): " + ex.toString());
        }
    }
    
    public String query_gestores_falantes(Integer tipo_cartera) {
        String cadenasql = "select u.usuario from usuario u where (u.tipo_usuario = '" + tipo_cartera + "') and (u.usuario not in (";
        Nodo1 recorre = null;

        try {
            recorre = inicio;
            while (recorre != null) {
                cadenasql = cadenasql + recorre.getGestor() + ", ";
                recorre = recorre.siguiente;
            }
            cadenasql = cadenasql.substring(0, cadenasql.length() - 2);
            cadenasql = cadenasql + "))";
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(query_gestores_falantes): " + ex.toString());
        }

        return cadenasql;
    }
    
    public void imprime() {
        Nodo1 recorre = null;
        
        try {
            recorre = inicio;
            while(recorre != null) {
                System.out.println(recorre.getGestor() + ", " + recorre.getNo_deudores() + ", " + recorre.getMonto());
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista1(imprime): " + ex.toString());
        }
    }
    
}
