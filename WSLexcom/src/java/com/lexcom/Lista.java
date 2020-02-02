package com.lexcom;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;

    Nodo inicio;
    Nodo fin;

    private Lista3 asignacion_actual;

    private Integer longitud_lista;
    private Connection conn;

    public Lista() {
        inicio = null;
        fin = null;
        longitud_lista = 0;
    }

    public void insertar(String deudor, String caso, String antiguedad, String fecha_recepcion, String no_cuenta, String nombre_deudor, Double monto_inicial, String garantia, String codigo_resultado, String fecha_proximo_pago, String cuota_proximo_pago, String gestor, Integer tipo_gestor, String estado_judicial, String status_judicial, String estado_extrajudicial, String status_extrajudicial, String actor) {
        Nodo nuevo = new Nodo(deudor, caso, antiguedad, fecha_recepcion, no_cuenta, nombre_deudor, monto_inicial, garantia, codigo_resultado, fecha_proximo_pago, cuota_proximo_pago, gestor, tipo_gestor, estado_judicial, status_judicial, estado_extrajudicial, status_extrajudicial, actor);

        try {
            longitud_lista++;
            if (inicio == null) {
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
            System.out.println("ERROR => WS-Lista(insertar): " + ex.toString());
        }
    }

    public Integer no_casos_sin_gestor() {
        Integer resultado = 0;
        Nodo recorre = null;

        try {
            recorre = inicio;
            while (recorre != null) {
                if (recorre.getGestor().equals("-1")) {
                    resultado = resultado + 1;
                }
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista(no_casos_sin_gestor): " + ex.toString());
        }

        return resultado;
    }

    public Lista1 cartera_resumen() {
        Lista1 cartera_resumen = new Lista1();
        Nodo recorre = null;

        try {
            recorre = inicio;
            while (recorre != null) {
                cartera_resumen.insertar(recorre.getGestor(), 1, recorre.getMonto_inicial());
                recorre = recorre.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista(cartera_resumen): " + ex.toString());
        }

        return cartera_resumen;
    }

    public void actualizar_cartera_deudores(Integer dif_max_min, String gestor, Integer tipo_cartera) {
        try {
            if (no_casos_sin_gestor() < dif_max_min) {
                dif_max_min = no_casos_sin_gestor();
            }

            for (Integer i = 0; i < dif_max_min; i++) {
                Double monto_minimo = 0.00;
                Nodo aux = null;
                Nodo recorre = inicio;
                while (recorre != null) {
                    if (monto_minimo == 0.00 && recorre.getGestor().equals("-1") && asignacion_actual.permite_asignacion(Integer.parseInt(recorre.getDeudor()), Integer.parseInt(gestor))) {
                        monto_minimo = recorre.getMonto_inicial();
                        aux = recorre;
                    }
                    if (recorre.getMonto_inicial() < monto_minimo && recorre.getGestor().equals("-1") && asignacion_actual.permite_asignacion(Integer.parseInt(recorre.getDeudor()), Integer.parseInt(gestor))) {
                        monto_minimo = recorre.getMonto_inicial();
                        aux = recorre;
                    }
                    recorre = recorre.siguiente;
                }

                aux.setGestor(gestor);
                aux.setTipo_gestor(tipo_cartera);
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista(actualizar_cartera_deudores): " + ex.toString());
        }
    }

    public void actualizar_cartera_monto(String gestor, Integer tipo_cartera) {
        Double monto_maximo = 0.00;
        Nodo aux = null;
        Nodo recorre = inicio;

        try {
            while (recorre != null) {
                if (monto_maximo == 0.00 && recorre.getGestor().equals("-1") && asignacion_actual.permite_asignacion(Integer.parseInt(recorre.getDeudor()), Integer.parseInt(gestor))) {
                    monto_maximo = recorre.getMonto_inicial();
                    aux = recorre;
                }
                if (recorre.getMonto_inicial() > monto_maximo && recorre.getGestor().equals("-1") && asignacion_actual.permite_asignacion(Integer.parseInt(recorre.getDeudor()), Integer.parseInt(gestor))) {
                    monto_maximo = recorre.getMonto_inicial();
                    aux = recorre;
                }
                recorre = recorre.siguiente;
            }

            aux.setGestor(gestor);
            aux.setTipo_gestor(tipo_cartera);
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista(actualizar_cartera_monto): " + ex.toString());
        }
    }

    public Nodo getNode(Integer pos) {
        Nodo resultado = inicio;

        try {
            for (Integer i = 0; i < pos; i++) {
                resultado = resultado.siguiente;
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista(getNode): " + ex.toString());
        }

        return resultado;
    }

    public void rotacion(Integer tipo_cartera, Lista3 asignacion_actual) {
        try {
            this.asignacion_actual = asignacion_actual;

            Boolean dale = true;
            while (dale) {
                Lista1 cartera_resumen = this.cartera_resumen();
                String cadenasql = cartera_resumen.query_gestores_falantes(tipo_cartera);
                Statement stmt = this.conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    cartera_resumen.insertar_faltante(rs.getString(1));
                }
                rs.close();
                stmt.close();

                Integer numero_cartera_sin = cartera_resumen.no_casos_sin_gestor();
                if (numero_cartera_sin != 0) {
                    Integer minimo_cartera = cartera_resumen.cartera_minimo();
                    Integer maximo_cartera = cartera_resumen.cartera_maximo();
                    Integer dif_max_min = maximo_cartera - minimo_cartera;
                    if (dif_max_min != 0) {
                        String minimo_gestor = cartera_resumen.gestor_cartera_minimo();
                        //error = "M_" + minimo_gestor + "," + dif_max_min + "," + minimo_gestor;
                        this.actualizar_cartera_deudores(dif_max_min, minimo_gestor, tipo_cartera);
                    } else {
                        String minimo_monto_gestor = cartera_resumen.gestor_monto_minimo();
                        this.actualizar_cartera_monto(minimo_monto_gestor, tipo_cartera);
                    }
                } else {
                    dale = false;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR => WS-Lista(rotacion): " + ex.toString());
        }
    }

    public Integer getLongitud_lista() {
        return longitud_lista;
    }

    public void setLongitud_lista(Integer longitud_lista) {
        this.longitud_lista = longitud_lista;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
