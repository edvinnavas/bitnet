package com.lexcom;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MenuPrincipal extends javax.swing.JFrame implements Runnable {

    String user = "usuario_gcj";
    String pass = "gcj123";
    String url = "jdbc:mysql://192.168.2.3:3306/gcj_test";
    
    Connection conn;
    Integer usuario;
    Thread hilo;
    Boolean hilo_corriendo;
    String version_app = "Versión 1.0.1.";

    public MenuPrincipal() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, pass);
//            Properties properties = new Properties();
//            properties.setProperty("user", "usuario_gcj");
//            properties.setProperty("password", "gcj123");
//            properties.setProperty("useSSL", "false");
//            properties.setProperty("autoReconnect", "true");
//            conn = DriverManager.getConnection(url, properties);
                    
//            com.lexcom.driver.Mantenimiento DMantenimiento = new com.lexcom.driver.Mantenimiento(this.conn);
//            DMantenimiento.migrar_fecha_telefono_celular();
//            DMantenimiento.agregar_pagos_deudor();
//            DMantenimiento.agregar_historio_cobros();
//            DMantenimiento.agregar_historio_administrativo();
//            DMantenimiento.agregar_historio_juridico();
//            DMantenimiento.agregar_descuento_deudor();
//            DMantenimiento.agregar_aumento_deudor();
//            DMantenimiento.permisos_usuario();
//            DMantenimiento.insertar_juicios_AA_No_Demandado();
//            DMantenimiento.actualizar_deudor_contactabilidad_ultimo();
//            DMantenimiento.agregar_convenio_promesa_pago();
            
            // VALIDAR VERSION.
            Boolean version = false;
            String cadenasql = "select c.valor from constantes c where c.constantes=3";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                if(rs.getString(1).trim().equals(this.version_app)) {
                    version = true;
                }
            }
            rs.close();
            stmt.close();
            
            if(!version) {
                JOptionPane.showMessageDialog(null, "VERSIÓN DE LA APLICACIÓN INCORRECTA.");
                System.exit(0);
            }

            //Ventana de login.
            Login a = new Login(new javax.swing.JFrame(), true, conn);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.usuario = a.dar_usuario();
            
            initComponents();
            
            this.setExtendedState(this.MAXIMIZED_BOTH);
            com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(conn, this.usuario);
            this.setTitle("APP-LEXCOM-DESKTOP-PRUEBAS - " + DUsuario.obtener_nombre(this.usuario));
            this.jLabel1.setText("AMBIENTE DE PRUEBAS.");
            this.jLabel3.setText(this.version_app);

            this.MenuReportes.setVisible(false);
            
            hilo = new Thread(this);
            hilo.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        MenuPrincipal = new javax.swing.JMenu();
        SubMenuSalir = new javax.swing.JMenuItem();
        MenuCartera = new javax.swing.JMenu();
        SubMenuExpedientes = new javax.swing.JMenuItem();
        MenuReportes = new javax.swing.JMenu();
        SubMenuRptIrrecuperabilidad = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel1.setForeground(new java.awt.Color(-65536,true));
        jLabel1.setText("AMBIENTE DE PRUEBAS.");
        desktopPane.add(jLabel1);
        jLabel1.setBounds(10, 10, 600, 24);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel2.setForeground(new java.awt.Color(-65536,true));
        jLabel2.setText("Fecha despliegue: 23/12/2021.");
        desktopPane.add(jLabel2);
        jLabel2.setBounds(10, 70, 600, 24);
        jLabel2.getAccessibleContext().setAccessibleDescription("");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel3.setForeground(new java.awt.Color(-65536,true));
        jLabel3.setText("Versión 1.0.1.");
        desktopPane.add(jLabel3);
        jLabel3.setBounds(10, 40, 600, 24);

        MenuPrincipal.setMnemonic('p');
        MenuPrincipal.setText("Principal");

        SubMenuSalir.setText("Salir");
        SubMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuSalirActionPerformed(evt);
            }
        });
        MenuPrincipal.add(SubMenuSalir);

        menuBar.add(MenuPrincipal);

        MenuCartera.setMnemonic('c');
        MenuCartera.setText("Cartera");

        SubMenuExpedientes.setText("Expedientes");
        SubMenuExpedientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuExpedientesActionPerformed(evt);
            }
        });
        MenuCartera.add(SubMenuExpedientes);

        menuBar.add(MenuCartera);

        MenuReportes.setMnemonic('r');
        MenuReportes.setText("Reportes");

        SubMenuRptIrrecuperabilidad.setText("Reporte Irrecuperabilidad");
        SubMenuRptIrrecuperabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptIrrecuperabilidadActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptIrrecuperabilidad);

        menuBar.add(MenuReportes);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void run() {
        hilo_corriendo = true;
        while (true) {
            try {
                String cadenasql = "SELECT "
                        + "d.usuario "
                        + "FROM "
                        + "promesa_pago pp "
                        + "left join deudor d on (pp.deudor=d.deudor) "
                        + "where "
                        + "d.usuario='" + this.usuario + "' and "
                        + "pp.estado_promesa='NO LEIDO' and "
                        + "pp.fecha_pago<=CURRENT_DATE() and "
                        + "pp.hora_pago<=CURRENT_TIME()";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                Integer contador = 0;
                while (rs.next()) {
                    contador++;
                }
                rs.close();

                if (contador > 0) {
                    if (this.hilo_corriendo) {
                        Bolson_Promesas_Pago a = new Bolson_Promesas_Pago(new javax.swing.JFrame(), false, conn, usuario);
                        a.addWindowListener(new WindowListener() {

                            @Override
                            public void windowClosed(WindowEvent e) {
                                hilo_corriendo = true;
                            }

                            @Override
                            public void windowIconified(WindowEvent e) {
                            }

                            @Override
                            public void windowDeiconified(WindowEvent e) {
                            }

                            @Override
                            public void windowActivated(WindowEvent e) {
                            }

                            @Override
                            public void windowDeactivated(WindowEvent e) {
                            }

                            @Override
                            public void windowOpened(WindowEvent e) {
                            }

                            @Override
                            public void windowClosing(WindowEvent e) {
                            }
                        });
                        Dimension ventana = a.getSize();
                        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                        a.setVisible(true);
                        hilo_corriendo = false;
                    }
                }
                Thread.sleep(20000);
            } catch (SQLException | HeadlessException | InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }

    private void SubMenuExpedientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuExpedientesActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al listado de todos los tipos de aumentos en la forma Datos.
                Datos_Expediente a = new Datos_Expediente(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
            } catch (HeadlessException | PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuExpedientesActionPerformed

    private void SubMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SubMenuSalirActionPerformed

    private void SubMenuRptIrrecuperabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptIrrecuperabilidadActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "REPORTE_IRRECUPERABILIDAD"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptIrrecuperabilidadActionPerformed

    public boolean cargar_permisos(String menu) {
        boolean dato = false;

        try {
            String cadenasql_permiso = "select a.ver from permiso_usuario_uno a, menu b where a.menu=b.menu and a.usuario=" + usuario + " and b.nombre='" + menu + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql_permiso);
            while (rs.next()) {
                if (rs.getString(1).equals("SI")) {
                    dato = true;
                } else {
                    dato = false;
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return dato;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuCartera;
    private javax.swing.JMenu MenuPrincipal;
    private javax.swing.JMenu MenuReportes;
    private javax.swing.JMenuItem SubMenuExpedientes;
    private javax.swing.JMenuItem SubMenuRptIrrecuperabilidad;
    private javax.swing.JMenuItem SubMenuSalir;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables
}
