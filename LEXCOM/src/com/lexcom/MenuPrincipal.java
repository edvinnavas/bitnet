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

            this.MenuEntidades.setVisible(false);
            this.MenuReportes.setVisible(false);
            this.MenuAyuda.setVisible(false);

            this.SubMenuUsuarios.setVisible(false);
            this.SubMenuPermisosUsuarios.setVisible(true);

            this.SubMenuCodigosContactabilidad.setVisible(false);
            this.MenuCargarJuicios.setVisible(false);
            this.SubMenuPermisosUsuarios.setVisible(false);
            this.SubMenuConstantes.setVisible(false);
            
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
        SubMenuUsuarios = new javax.swing.JMenuItem();
        SubMenuPermisosUsuarios = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        SubMenuConstantes = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        SubMenuSalir = new javax.swing.JMenuItem();
        MenuEntidades = new javax.swing.JMenu();
        SubMenuCooperaciones = new javax.swing.JMenuItem();
        SubMenuActores = new javax.swing.JMenuItem();
        MenuDeudores = new javax.swing.JMenu();
        SubMenuCargaIndividualDeudores = new javax.swing.JMenuItem();
        SubMenuCargaMasivaDeudores = new javax.swing.JMenuItem();
        SubMenuUpdateMasivaDeudores = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        SubMenuCargaRotacionCartera = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        SubMenuArchivoDeudoresCarga = new javax.swing.JMenuItem();
        SubMenuArchivoDeudoresUpdate = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        SubMenuArchivoRotacionCartera = new javax.swing.JMenuItem();
        SubMenuArchivoRotacionCarteraManual = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        MenuArchivoCargaPagos = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        SubMenuJuzgados = new javax.swing.JMenuItem();
        SubMenuGarantia = new javax.swing.JMenuItem();
        SubMenuBancos = new javax.swing.JMenuItem();
        SubMenuEstados = new javax.swing.JMenuItem();
        SubMenuStatus = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        SubMenuTiposDescuento = new javax.swing.JMenuItem();
        SubMenuTiposAumento = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        MenuCartera = new javax.swing.JMenu();
        SubMenuCodigosContactabilidad = new javax.swing.JMenuItem();
        SubMenuProgramcionCartera = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        SubMenuExpedientes = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        MenuCargarJuicios = new javax.swing.JMenu();
        SubMenuCargaMasivaPresentarDemanda = new javax.swing.JMenuItem();
        SubMenuCargaMasivaCaratula = new javax.swing.JMenuItem();
        SubMenuCargaMasivaMedidasPrecautorias = new javax.swing.JMenuItem();
        SubMenuCargaMasivaDeligenciarMedidas = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        SubMenuArchivoPresentarDemanda = new javax.swing.JMenuItem();
        SubMenuArchivoCaratula = new javax.swing.JMenuItem();
        SubMenuArchivoMedidasPrecautorias = new javax.swing.JMenuItem();
        SubMenuArchivoDiligenciarMedidas = new javax.swing.JMenuItem();
        MenuReportes = new javax.swing.JMenu();
        SubMenuRptPagosGestorGarantia = new javax.swing.JMenuItem();
        SubMenuRptPagosAntiguedadGarantia = new javax.swing.JMenuItem();
        SubMenuRptAsignacionCartera = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        SubMenuRptConsejo = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        SubMenuRptProcuradores = new javax.swing.JMenuItem();
        SubMenuRptPagos = new javax.swing.JMenuItem();
        SubMenuRptIrrecuperabilidad = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        MenuAyuda = new javax.swing.JMenu();
        SubMenuAcercade = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(-65536,true));
        jLabel1.setText("AMBIENTE DE PRUEBAS.");
        jLabel1.setBounds(10, 10, 600, 24);
        desktopPane.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(-65536,true));
        jLabel2.setText("Fecha despliegue: 23/12/2021.");
        jLabel2.setBounds(10, 70, 600, 24);
        desktopPane.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLabel2.getAccessibleContext().setAccessibleName("Fecha despliegue: 23/12/2021.");
        jLabel2.getAccessibleContext().setAccessibleDescription("");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(-65536,true));
        jLabel3.setText("Versión 1.0.1.");
        jLabel3.setBounds(10, 40, 600, 24);
        desktopPane.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        MenuPrincipal.setMnemonic('p');
        MenuPrincipal.setText("Principal");

        SubMenuUsuarios.setText("Usuarios");
        SubMenuUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuUsuariosActionPerformed(evt);
            }
        });
        MenuPrincipal.add(SubMenuUsuarios);

        SubMenuPermisosUsuarios.setText("Permisos Usuarios");
        SubMenuPermisosUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuPermisosUsuariosActionPerformed(evt);
            }
        });
        MenuPrincipal.add(SubMenuPermisosUsuarios);
        MenuPrincipal.add(jSeparator1);

        SubMenuConstantes.setText("Constantes");
        SubMenuConstantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuConstantesActionPerformed(evt);
            }
        });
        MenuPrincipal.add(SubMenuConstantes);
        MenuPrincipal.add(jSeparator2);

        SubMenuSalir.setText("Salir");
        SubMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuSalirActionPerformed(evt);
            }
        });
        MenuPrincipal.add(SubMenuSalir);

        menuBar.add(MenuPrincipal);

        MenuEntidades.setMnemonic('e');
        MenuEntidades.setText("Entidades");

        SubMenuCooperaciones.setText("Corporaciones");
        SubMenuCooperaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCooperacionesActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuCooperaciones);

        SubMenuActores.setText("Actores");
        SubMenuActores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuActoresActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuActores);

        MenuDeudores.setText("Deudores");

        SubMenuCargaIndividualDeudores.setText("Carga individual deudores");
        SubMenuCargaIndividualDeudores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaIndividualDeudoresActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuCargaIndividualDeudores);

        SubMenuCargaMasivaDeudores.setText("Carga masiva deudores");
        SubMenuCargaMasivaDeudores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaMasivaDeudoresActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuCargaMasivaDeudores);

        SubMenuUpdateMasivaDeudores.setText("Actualización masiva deudores");
        SubMenuUpdateMasivaDeudores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuUpdateMasivaDeudoresActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuUpdateMasivaDeudores);
        MenuDeudores.add(jSeparator13);

        SubMenuCargaRotacionCartera.setText("Carga rotación de cartera");
        SubMenuCargaRotacionCartera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaRotacionCarteraActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuCargaRotacionCartera);
        MenuDeudores.add(jSeparator5);

        jMenuItem3.setText("Carga de pagos masivos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MenuDeudores.add(jMenuItem3);
        MenuDeudores.add(jSeparator15);

        SubMenuArchivoDeudoresCarga.setText("Archivo deudores carga");
        SubMenuArchivoDeudoresCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoDeudoresCargaActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuArchivoDeudoresCarga);

        SubMenuArchivoDeudoresUpdate.setText("Archivo deudores actualización");
        SubMenuArchivoDeudoresUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoDeudoresUpdateActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuArchivoDeudoresUpdate);
        MenuDeudores.add(jSeparator12);

        SubMenuArchivoRotacionCartera.setText("Archivo rotación de cartera automático");
        SubMenuArchivoRotacionCartera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoRotacionCarteraActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuArchivoRotacionCartera);

        SubMenuArchivoRotacionCarteraManual.setText("Archivo rotación de cartera manual");
        SubMenuArchivoRotacionCarteraManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoRotacionCarteraManualActionPerformed(evt);
            }
        });
        MenuDeudores.add(SubMenuArchivoRotacionCarteraManual);
        MenuDeudores.add(jSeparator8);

        MenuArchivoCargaPagos.setText("Archivo carga de pagos masivos");
        MenuArchivoCargaPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuArchivoCargaPagosActionPerformed(evt);
            }
        });
        MenuDeudores.add(MenuArchivoCargaPagos);

        MenuEntidades.add(MenuDeudores);
        MenuEntidades.add(jSeparator3);

        SubMenuJuzgados.setText("Juzgados");
        SubMenuJuzgados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuJuzgadosActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuJuzgados);

        SubMenuGarantia.setText("Garantía");
        SubMenuGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuGarantiaActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuGarantia);

        SubMenuBancos.setText("Bancos");
        SubMenuBancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuBancosActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuBancos);

        SubMenuEstados.setText("Estados");
        SubMenuEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuEstadosActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuEstados);

        SubMenuStatus.setText("Status");
        SubMenuStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuStatusActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuStatus);
        MenuEntidades.add(jSeparator4);

        SubMenuTiposDescuento.setText("Tipos de Descuento");
        SubMenuTiposDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuTiposDescuentoActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuTiposDescuento);

        SubMenuTiposAumento.setText("Tipos de Aumento");
        SubMenuTiposAumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuTiposAumentoActionPerformed(evt);
            }
        });
        MenuEntidades.add(SubMenuTiposAumento);
        MenuEntidades.add(jSeparator16);

        jMenuItem5.setText("Frases Predeterminadas Gestión");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        MenuEntidades.add(jMenuItem5);

        menuBar.add(MenuEntidades);

        MenuCartera.setMnemonic('c');
        MenuCartera.setText("Cartera");

        SubMenuCodigosContactabilidad.setText("Códigos Contactabilidad");
        SubMenuCodigosContactabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCodigosContactabilidadActionPerformed(evt);
            }
        });
        MenuCartera.add(SubMenuCodigosContactabilidad);

        SubMenuProgramcionCartera.setText("Asignación Cartera");
        SubMenuProgramcionCartera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuProgramcionCarteraActionPerformed(evt);
            }
        });
        MenuCartera.add(SubMenuProgramcionCartera);
        MenuCartera.add(jSeparator7);

        jMenuItem2.setText("Bolson Asignación");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MenuCartera.add(jMenuItem2);

        SubMenuExpedientes.setText("Expedientes");
        SubMenuExpedientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuExpedientesActionPerformed(evt);
            }
        });
        MenuCartera.add(SubMenuExpedientes);
        MenuCartera.add(jSeparator6);

        MenuCargarJuicios.setText("Carga juicios");

        SubMenuCargaMasivaPresentarDemanda.setText("Carga masiva presentar demanda");
        SubMenuCargaMasivaPresentarDemanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaMasivaPresentarDemandaActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuCargaMasivaPresentarDemanda);

        SubMenuCargaMasivaCaratula.setText("Carga masiva caratula");
        SubMenuCargaMasivaCaratula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaMasivaCaratulaActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuCargaMasivaCaratula);

        SubMenuCargaMasivaMedidasPrecautorias.setText("Carga masiva medidas precautorias");
        SubMenuCargaMasivaMedidasPrecautorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaMasivaMedidasPrecautoriasActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuCargaMasivaMedidasPrecautorias);

        SubMenuCargaMasivaDeligenciarMedidas.setText("Carga masiva deligenciar medidas");
        SubMenuCargaMasivaDeligenciarMedidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuCargaMasivaDeligenciarMedidasActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuCargaMasivaDeligenciarMedidas);
        MenuCargarJuicios.add(jSeparator11);

        SubMenuArchivoPresentarDemanda.setText("Archivo presentar demanda");
        SubMenuArchivoPresentarDemanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoPresentarDemandaActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuArchivoPresentarDemanda);

        SubMenuArchivoCaratula.setText("Archivo caratula");
        SubMenuArchivoCaratula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoCaratulaActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuArchivoCaratula);

        SubMenuArchivoMedidasPrecautorias.setText("Archivo medidas precautorias");
        SubMenuArchivoMedidasPrecautorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoMedidasPrecautoriasActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuArchivoMedidasPrecautorias);

        SubMenuArchivoDiligenciarMedidas.setText("Archivo diligenciar medidas");
        SubMenuArchivoDiligenciarMedidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuArchivoDiligenciarMedidasActionPerformed(evt);
            }
        });
        MenuCargarJuicios.add(SubMenuArchivoDiligenciarMedidas);

        MenuCartera.add(MenuCargarJuicios);

        menuBar.add(MenuCartera);

        MenuReportes.setMnemonic('r');
        MenuReportes.setText("Reportes");

        SubMenuRptPagosGestorGarantia.setText("Pagos por gestor y garantía");
        SubMenuRptPagosGestorGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptPagosGestorGarantiaActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptPagosGestorGarantia);

        SubMenuRptPagosAntiguedadGarantia.setText("Pagos por antigüedad y garantía");
        SubMenuRptPagosAntiguedadGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptPagosAntiguedadGarantiaActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptPagosAntiguedadGarantia);

        SubMenuRptAsignacionCartera.setText("Asignación de cartera");
        SubMenuRptAsignacionCartera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptAsignacionCarteraActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptAsignacionCartera);
        MenuReportes.add(jSeparator9);

        SubMenuRptConsejo.setText("Reporte Consejo");
        SubMenuRptConsejo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptConsejoActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptConsejo);
        MenuReportes.add(jSeparator10);

        SubMenuRptProcuradores.setText("Reporte de procuradores");
        SubMenuRptProcuradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptProcuradoresActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptProcuradores);

        SubMenuRptPagos.setText("Reporte de pagos");
        SubMenuRptPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptPagosActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptPagos);

        SubMenuRptIrrecuperabilidad.setText("Reporte Irrecuperabilidad");
        SubMenuRptIrrecuperabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuRptIrrecuperabilidadActionPerformed(evt);
            }
        });
        MenuReportes.add(SubMenuRptIrrecuperabilidad);
        MenuReportes.add(jSeparator14);

        jMenuItem1.setText("Notificados por entidad y garantía");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenuReportes.add(jMenuItem1);

        jMenuItem4.setText("Liquidación de gastos de demanda");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        MenuReportes.add(jMenuItem4);

        menuBar.add(MenuReportes);

        MenuAyuda.setMnemonic('a');
        MenuAyuda.setText("Ayuda");

        SubMenuAcercade.setText("Acerca de ...");
        SubMenuAcercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubMenuAcercadeActionPerformed(evt);
            }
        });
        MenuAyuda.add(SubMenuAcercade);

        menuBar.add(MenuAyuda);

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

    private void SubMenuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuUsuariosActionPerformed
        try {
            //Llama al listado de todos los usuarios en la forma Datos.
            String[] campos_busqueda = {"a.usuario", "a.nombre_completo", "a.nombre", "a.estado", "a.gestor", "a.procurador", "a.asistente", "a.digitador", "a.investigador"};
            Datos a = new Datos(this.conn, this.usuario, "select a.usuario AS USUARIO, a.nombre_completo AS NOMBRE_COMPLETO, a.nombre AS NOMBRE, a.estado AS ESTADO, a.gestor AS GESTOR, a.procurador AS PROCURADOR, a.asistente AS ASISTENTE, a.digitador AS DIGITADOR, a.investigador AS INVESTIGADOR from usuario a where 1=1", "usuario", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuUsuariosActionPerformed

    private void SubMenuActoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuActoresActionPerformed
        try {
            //Llama al listado de todos los actores en la forma Datos.
            String[] campos_busqueda = {"a.actor", "a.nombre", "a.nit", "a.telefono", "a.estado", "c.nombre"};
            Datos a = new Datos(this.conn, this.usuario, "select a.actor AS ACTOR, a.nombre AS NOMBRE, a.nit AS NIT, a.telefono AS TELEFONO, a.estado AS ESTADO, c.nombre AS COOPERACION from actor a left join cooperacion c on (a.cooperacion=c.cooperacion) where 1=1", "actor", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuActoresActionPerformed

    private void SubMenuCargaIndividualDeudoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaIndividualDeudoresActionPerformed
        try {
            //Llama al listado de todos los deudores en la forma Datos.
            String[] campos_busqueda = {"a.deudor", "a.nombre", "a.dpi", "a.nit", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.deudor AS DEUDOR, a.nombre AS NOMBRE, a.dpi AS DPI, a.nit AS NIT, a.estado AS ESTADO from deudor a where 1=1", "deudor", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuCargaIndividualDeudoresActionPerformed

    private void SubMenuJuzgadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuJuzgadosActionPerformed
        try {
            //Llama al listado de todos los juzgados en la forma Datos.
            String[] campos_busqueda = {"a.juzgado", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.juzgado AS JUZGADO, a.nombre AS NOMBRE, a.estado AS ESTADO from juzgado a where 1=1", "juzgado", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuJuzgadosActionPerformed

    private void SubMenuGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuGarantiaActionPerformed
        try {
            //Llama al listado de todos las garantias en la forma Datos.
            String[] campos_busqueda = {"a.garantia", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.garantia AS GARANTIA, a.nombre AS NOMBRE, a.estado AS ESTADO from garantia a where 1=1", "garantia", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuGarantiaActionPerformed

    private void SubMenuBancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuBancosActionPerformed
        try {
            //Llama al listado de todos los bancos en la forma Datos.
            String[] campos_busqueda = {"a.banco", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.banco AS BANCO, a.nombre AS NOMBRE, a.estado AS ESTADO from banco a where 1=1", "banco", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuBancosActionPerformed

    private void SubMenuEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuEstadosActionPerformed
        try {
            //Llama al listado de todos los estados de cliente en la forma Datos.
            String[] campos_busqueda = {"a.sestado", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.sestado AS SESTADO, a.nombre AS NOMBRE, a.estado AS ESTADO from sestado a where 1=1", "sestado", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuEstadosActionPerformed

    private void SubMenuStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuStatusActionPerformed
        try {
            //Llama al listado de todos los status de cliente en la forma Datos.
            String[] campos_busqueda = {"a.estatus", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.estatus AS ESTATUS, a.nombre AS NOMBRE, a.estado AS ESTADO from estatus a where 1=1", "estatus", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuStatusActionPerformed

    private void SubMenuTiposDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuTiposDescuentoActionPerformed
        try {
            //Llama al listado de todos los tipos de descuento en la forma Datos.
            String[] campos_busqueda = {"a.tipo_descuento", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.tipo_descuento AS TIPO_DESCUENTO, a.nombre AS NOMBRE, a.estado AS ESTADO from tipo_descuento a where 1=1", "tipo_descuento", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuTiposDescuentoActionPerformed

    private void SubMenuTiposAumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuTiposAumentoActionPerformed
        try {
            //Llama al listado de todos los tipos de aumentos en la forma Datos.
            String[] campos_busqueda = {"a.tipo_aumento", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.tipo_aumento AS TIPO_AUMENTO, a.nombre AS NOMBRE, a.estado AS ESTADO from tipo_aumento a where 1=1", "tipo_aumento", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuTiposAumentoActionPerformed

    private void SubMenuCodigosContactabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCodigosContactabilidadActionPerformed
        try {
            //Llama al listado de todos los codigos de contactabilidad en la forma Datos.
            String[] campos_busqueda = {"a.codigo_contactabilidad", "a.codigo", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.codigo_contactabilidad AS CODIGO_CONTACTABILIDAD, a.codigo AS CODIGO, a.nombre AS NOMBRE, a.estado AS ESTADO from codigo_contactabilidad a where 1=1", "codigo_contactabilidad", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuCodigosContactabilidadActionPerformed

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

    private void SubMenuConstantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuConstantesActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Constantes a = new Constantes(new javax.swing.JFrame(), true, conn, usuario);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuConstantesActionPerformed

    private void SubMenuCooperacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCooperacionesActionPerformed
        try {
            //Llama al listado de todos los actores en la forma Datos.
            String[] campos_busqueda = {"a.cooperacion", "a.nombre", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "select a.cooperacion AS COOPERACION, a.nombre AS NOMBRE, a.estado AS ESTADO from cooperacion a where 1=1", "cooperacion", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuCooperacionesActionPerformed

    private void SubMenuRptPagosGestorGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptPagosGestorGarantiaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "PAGOS_GESTOR_GARANTIA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptPagosGestorGarantiaActionPerformed

    private void SubMenuUpdateMasivaDeudoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuUpdateMasivaDeudoresActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Deudor_Masivo a = new Deudor_Masivo(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuUpdateMasivaDeudoresActionPerformed

    private void SubMenuArchivoDeudoresUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoDeudoresUpdateActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "ACTUALIZACION_DEUDORES"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoDeudoresUpdateActionPerformed

    private void SubMenuAcercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuAcercadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SubMenuAcercadeActionPerformed

    private void SubMenuCargaMasivaMedidasPrecautoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaMasivaMedidasPrecautoriasActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Recoger_Medidas a = new Recoger_Medidas(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
                a.setTitle("RECOGER MEDIDAS");
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuCargaMasivaMedidasPrecautoriasActionPerformed

    private void SubMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SubMenuSalirActionPerformed

    private void SubMenuRptPagosAntiguedadGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptPagosAntiguedadGarantiaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "PAGOS_ANTIGUEDAD_GARANTIA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptPagosAntiguedadGarantiaActionPerformed

    private void SubMenuRptAsignacionCarteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptAsignacionCarteraActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "ASIGNACION_CARTERA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptAsignacionCarteraActionPerformed

    private void SubMenuArchivoRotacionCarteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoRotacionCarteraActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "ROTACION_AUTOMATICA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoRotacionCarteraActionPerformed

    private void SubMenuRptProcuradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptProcuradoresActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "REPORTE_PROCURADORES"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptProcuradoresActionPerformed

    private void SubMenuRptPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptPagosActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "REPORTE_PAGOS"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptPagosActionPerformed

    private void SubMenuCargaMasivaDeudoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaMasivaDeudoresActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Deudor_Masivo_Carga a = new Deudor_Masivo_Carga(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuCargaMasivaDeudoresActionPerformed

    private void SubMenuArchivoDeudoresCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoDeudoresCargaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "CARGA_DEUDORES"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoDeudoresCargaActionPerformed

    private void SubMenuRptConsejoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptConsejoActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "REPORTE_CONSEJO"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptConsejoActionPerformed

    private void SubMenuCargaRotacionCarteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaRotacionCarteraActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Rotacion_Cartera a = new Rotacion_Cartera(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuCargaRotacionCarteraActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "NOTIFICADOS_ENTIDAD_GARANTIA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void SubMenuPermisosUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuPermisosUsuariosActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Permisos_Usuario a = new Permisos_Usuario(new javax.swing.JFrame(), true, conn, usuario);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuPermisosUsuariosActionPerformed

    private void SubMenuProgramcionCarteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuProgramcionCarteraActionPerformed
        try {
            //Llama al listado de todos los actores en la forma Datos.
            String[] campos_busqueda = {"a.asignacion_cartera", "a.fecha", "a.fecha_promesa_pago", "a.fecha_casos_por_fecha"};
            Datos a = new Datos(this.conn, this.usuario, "SELECT a.asignacion_cartera AS ASIGNACION_CARTERA, a.fecha AS FECHA, a.fecha_promesa_pago AS FECHA_PP, a.fecha_casos_por_fecha AS FECHA_CASO FROM asignacion_cartera a WHERE 1=1", "asignacion_cartera", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_SubMenuProgramcionCarteraActionPerformed

    private void SubMenuCargaMasivaPresentarDemandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaMasivaPresentarDemandaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Presentar_Demanda a = new Presentar_Demanda(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
                a.setTitle("PRESENTAR DEMANDAS");
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuCargaMasivaPresentarDemandaActionPerformed

    private void SubMenuCargaMasivaCaratulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaMasivaCaratulaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Caratula a = new Caratula(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
                a.setTitle("CARATULA");
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuCargaMasivaCaratulaActionPerformed

    private void SubMenuCargaMasivaDeligenciarMedidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuCargaMasivaDeligenciarMedidasActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar deudores de forma masiva.
                Recoger_Medidas a = new Recoger_Medidas(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
                a.setTitle("DILIGENCIAR MEDIDAS");
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuCargaMasivaDeligenciarMedidasActionPerformed

    private void SubMenuArchivoPresentarDemandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoPresentarDemandaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "PRESENTAR_DEMANDA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoPresentarDemandaActionPerformed

    private void SubMenuArchivoCaratulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoCaratulaActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "CARATULA"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoCaratulaActionPerformed

    private void SubMenuArchivoMedidasPrecautoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoMedidasPrecautoriasActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "MEDIDAS_PRECAUTORIAS"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoMedidasPrecautoriasActionPerformed

    private void SubMenuArchivoDiligenciarMedidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoDiligenciarMedidasActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "DELIGENCIAR_MEDIDAS"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoDiligenciarMedidasActionPerformed

    private void SubMenuRptIrrecuperabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuRptIrrecuperabilidadActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "REPORTE_IRRECUPERABILIDAD"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuRptIrrecuperabilidadActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                Datos_Expediente a = new Datos_Expediente(this.conn, this.usuario);
                a.bolson_asignacion(true);
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
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void SubMenuArchivoRotacionCarteraManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubMenuArchivoRotacionCarteraManualActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "ROTACION_MANUAL"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_SubMenuArchivoRotacionCarteraManualActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "LIQUIDACION"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void MenuArchivoCargaPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuArchivoCargaPagosActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            Thread hilo1 = new Thread(new Reportes(conn, usuario, "ARCHIVO_CARGA_PAGOS"), "LEX-LOADING-1");
            hilo1.start();
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_MenuArchivoCargaPagosActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if (this.cargar_permisos(evt.getActionCommand().toString())) {
            try {
                //Llama al ventana para cargar los pagos masivos.
                Pagos_Masivo a = new Pagos_Masivo(this.conn, this.usuario);
                this.desktopPane.add(a);
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension ventana = a.getSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setMaximum(true);
                a.setVisible(true);
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tiene permiso de ingresar a esta opcion.");
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            //Llama al listado de todos los tipos de aumentos en la forma Datos.
            String[] campos_busqueda = {"a.frase_predeterminada", "a.nombre", "a.tipo", "a.frase", "a.estado"};
            Datos a = new Datos(this.conn, this.usuario, "SELECT a.frase_predeterminada AS FRASE_PREDETERMINADA, a.nombre AS NOMBRE, a.tipo AS TIPO, a.frase AS FRASE, a.estado AS ESTADO FROM frase_predeterminada a where 1=1", "frase_predeterminada", campos_busqueda, evt.getActionCommand().toString());
            this.desktopPane.add(a);
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ventana = a.getSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setMaximum(true);
            a.setVisible(true);
        } catch (HeadlessException | PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
    private javax.swing.JMenuItem MenuArchivoCargaPagos;
    private javax.swing.JMenu MenuAyuda;
    private javax.swing.JMenu MenuCargarJuicios;
    private javax.swing.JMenu MenuCartera;
    private javax.swing.JMenu MenuDeudores;
    private javax.swing.JMenu MenuEntidades;
    private javax.swing.JMenu MenuPrincipal;
    private javax.swing.JMenu MenuReportes;
    private javax.swing.JMenuItem SubMenuAcercade;
    private javax.swing.JMenuItem SubMenuActores;
    private javax.swing.JMenuItem SubMenuArchivoCaratula;
    private javax.swing.JMenuItem SubMenuArchivoDeudoresCarga;
    private javax.swing.JMenuItem SubMenuArchivoDeudoresUpdate;
    private javax.swing.JMenuItem SubMenuArchivoDiligenciarMedidas;
    private javax.swing.JMenuItem SubMenuArchivoMedidasPrecautorias;
    private javax.swing.JMenuItem SubMenuArchivoPresentarDemanda;
    private javax.swing.JMenuItem SubMenuArchivoRotacionCartera;
    private javax.swing.JMenuItem SubMenuArchivoRotacionCarteraManual;
    private javax.swing.JMenuItem SubMenuBancos;
    private javax.swing.JMenuItem SubMenuCargaIndividualDeudores;
    private javax.swing.JMenuItem SubMenuCargaMasivaCaratula;
    private javax.swing.JMenuItem SubMenuCargaMasivaDeligenciarMedidas;
    private javax.swing.JMenuItem SubMenuCargaMasivaDeudores;
    private javax.swing.JMenuItem SubMenuCargaMasivaMedidasPrecautorias;
    private javax.swing.JMenuItem SubMenuCargaMasivaPresentarDemanda;
    private javax.swing.JMenuItem SubMenuCargaRotacionCartera;
    private javax.swing.JMenuItem SubMenuCodigosContactabilidad;
    private javax.swing.JMenuItem SubMenuConstantes;
    private javax.swing.JMenuItem SubMenuCooperaciones;
    private javax.swing.JMenuItem SubMenuEstados;
    private javax.swing.JMenuItem SubMenuExpedientes;
    private javax.swing.JMenuItem SubMenuGarantia;
    private javax.swing.JMenuItem SubMenuJuzgados;
    private javax.swing.JMenuItem SubMenuPermisosUsuarios;
    private javax.swing.JMenuItem SubMenuProgramcionCartera;
    private javax.swing.JMenuItem SubMenuRptAsignacionCartera;
    private javax.swing.JMenuItem SubMenuRptConsejo;
    private javax.swing.JMenuItem SubMenuRptIrrecuperabilidad;
    private javax.swing.JMenuItem SubMenuRptPagos;
    private javax.swing.JMenuItem SubMenuRptPagosAntiguedadGarantia;
    private javax.swing.JMenuItem SubMenuRptPagosGestorGarantia;
    private javax.swing.JMenuItem SubMenuRptProcuradores;
    private javax.swing.JMenuItem SubMenuSalir;
    private javax.swing.JMenuItem SubMenuStatus;
    private javax.swing.JMenuItem SubMenuTiposAumento;
    private javax.swing.JMenuItem SubMenuTiposDescuento;
    private javax.swing.JMenuItem SubMenuUpdateMasivaDeudores;
    private javax.swing.JMenuItem SubMenuUsuarios;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables
}
