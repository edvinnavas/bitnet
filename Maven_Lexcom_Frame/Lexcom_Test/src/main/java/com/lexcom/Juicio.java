package com.lexcom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Juicio extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    Integer deudor;
    
    public Juicio(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion, Integer deudor) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        this.deudor = deudor;
        initComponents();
        
        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
        this.cbxJuzgado.setModel(DJuzgado.dar_lista());
        
        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        this.cbxProcurador.setModel(DProcurador.dar_lista_procuradores());
        
        com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
        this.cbxTipoJuicio.setModel(DTipoJuicio.dar_lista());
        
        com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);
        this.cbxTituloEjecutivo.setModel(DTituloEjecutivo.dar_lista());
        
        if(accion == 2) {
            this.txtNoJuicio.setEditable(false);
            this.txtDepositario.setEditable(false);
            this.cbxTipoJuicio.setEnabled(false);
            this.cbxTituloEjecutivo.setEnabled(false);
            this.txtAbogadoDeudor.setEditable(false);
            this.txtTiempoEstimado.setEditable(false);
            
            this.cbxJuzgado.setEnabled(false);
            this.cbxProcurador.setEnabled(false);
            this.cbxNotificado.setEnabled(false);
            
            this.spnMonto.setEnabled(false);
            this.spnNotificador.setEnabled(false);
            
            this.areDescripcion.setEditable(false);
            this.areDescripcionNotificacion.setEditable(false);
            this.areDescripcionProcuracion.setEditable(false);
            
            this.TblArraigo.setEnabled(false);
            this.TblBanco.setEnabled(false);
            this.TblFinca.setEnabled(false);
            this.TblSalario.setEnabled(false);
            this.TblVehiculo.setEnabled(false);
            
            this.btnArraigoAgregar.setEnabled(false);
            this.btnArraigoEliminar.setEnabled(false);
            this.btnBancoAgregar.setEnabled(false);
            this.btnBancoEliminar.setEnabled(false);
            this.btnFincaAgregar.setEnabled(false);
            this.btnFincaEliminar.setEnabled(false);
            this.btnSalarioAgregar.setEnabled(false);
            this.btnSalarioEliminar.setEnabled(false);
            this.btnVehiculoAgregar.setEnabled(false);
            this.btnVehiculoEliminar.setEnabled(false);
            this.btnOtrosAgregar.setEnabled(false);
            this.btnOtrosEliminar.setEnabled(false);
            this.btnAceptar.setVisible(false);
        }
        this.cbxJuzgado.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxJuzgado = new javax.swing.JComboBox();
        spnMonto = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        dccFecha = new datechooser.beans.DateChooserCombo();
        txtNoJuicio = new javax.swing.JTextField();
        cbxProcurador = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtAbogadoDeudor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        spnNotificador = new javax.swing.JSpinner();
        dccMemorial = new datechooser.beans.DateChooserCombo();
        dccFechaAdminision = new datechooser.beans.DateChooserCombo();
        jLabel14 = new javax.swing.JLabel();
        cbxNotificado = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        dccFechaNotificacion = new datechooser.beans.DateChooserCombo();
        jLabel16 = new javax.swing.JLabel();
        txtDepositario = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTiempoEstimado = new javax.swing.JTextField();
        cbxTipoJuicio = new javax.swing.JComboBox<>();
        cbxTituloEjecutivo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        panelMedida = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TblArraigo = new javax.swing.JTable();
        btnArraigoAgregar = new javax.swing.JButton();
        btnArraigoEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TblBanco = new javax.swing.JTable();
        btnBancoAgregar = new javax.swing.JButton();
        btnBancoEliminar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TblFinca = new javax.swing.JTable();
        btnFincaAgregar = new javax.swing.JButton();
        btnFincaEliminar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TblSalario = new javax.swing.JTable();
        btnSalarioAgregar = new javax.swing.JButton();
        btnSalarioEliminar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TblVehiculo = new javax.swing.JTable();
        btnVehiculoAgregar = new javax.swing.JButton();
        btnVehiculoEliminar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TblOtros = new javax.swing.JTable();
        btnOtrosAgregar = new javax.swing.JButton();
        btnOtrosEliminar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areDescripcion = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        areDescripcionProcuracion = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        areDescripcionNotificacion = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ARRAIGO");
        setResizable(false);

        jLabel2.setText("Juzgado");

        jLabel4.setText("No. Juicio");

        jLabel5.setText("Monto");

        spnMonto.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        jLabel1.setText("Fecha");

        dccFecha.setNothingAllowed(false);
        dccFecha.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel6.setText("Procurador");

        jLabel8.setText("Notificador");

        jLabel9.setText("Abogado Deudor");

        jLabel10.setText("Tipo Juicio");

        jLabel11.setText("Memorial");

        spnNotificador.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        dccMemorial.setNothingAllowed(false);
        dccMemorial.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dccFechaAdminision.setNothingAllowed(false);
        dccFechaAdminision.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel14.setText("Fecha Admisión");

        cbxNotificado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO", "D INCORRECTA", "NADIE ATIENDE", "NO VIVE LUGAR", "IMPOSIBLE", "FUERA PAIS", "FALLECIDO" }));

        jLabel15.setText("Notificado");

        dccFechaNotificacion.setNothingAllowed(false);
        dccFechaNotificacion.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel16.setText("Fecha Notificación");

        jLabel17.setText("Depositario");

        jLabel18.setText("Tiempo Estimado");

        cbxTipoJuicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxTituloEjecutivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Título Ejecutivo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dccFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNoJuicio, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spnMonto, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbxProcurador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxJuzgado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel7))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dccFechaNotificacion, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                            .addComponent(dccFechaAdminision, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(dccMemorial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(spnNotificador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(txtAbogadoDeudor, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(cbxNotificado, 0, 463, Short.MAX_VALUE)
                            .addComponent(txtDepositario, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(txtTiempoEstimado, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(cbxTipoJuicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxTituloEjecutivo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxJuzgado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(dccFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNoJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spnMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(spnNotificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAbogadoDeudor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(cbxTipoJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccMemorial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(dccFechaAdminision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxNotificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(dccFechaNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDepositario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTiempoEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTituloEjecutivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Generales", jPanel1);

        TblArraigo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ARRAIGO", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblArraigo.setFillsViewportHeight(true);
        TblArraigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblArraigoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TblArraigo);
        if (TblArraigo.getColumnModel().getColumnCount() > 0) {
            TblArraigo.getColumnModel().getColumn(0).setResizable(false);
            TblArraigo.getColumnModel().getColumn(1).setResizable(false);
        }

        btnArraigoAgregar.setText("Agregar");
        btnArraigoAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArraigoAgregarActionPerformed(evt);
            }
        });

        btnArraigoEliminar.setText("Eliminar");
        btnArraigoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArraigoEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(btnArraigoAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnArraigoEliminar)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnArraigoEliminar)
                    .addComponent(btnArraigoAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMedida.addTab("Arraigo", jPanel2);

        TblBanco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BANCOS", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblBanco.setFillsViewportHeight(true);
        TblBanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblBancoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TblBanco);
        if (TblBanco.getColumnModel().getColumnCount() > 0) {
            TblBanco.getColumnModel().getColumn(0).setResizable(false);
            TblBanco.getColumnModel().getColumn(1).setResizable(false);
        }

        btnBancoAgregar.setText("Agregar");
        btnBancoAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBancoAgregarActionPerformed(evt);
            }
        });

        btnBancoEliminar.setText("Eliminar");
        btnBancoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBancoEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(btnBancoAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBancoEliminar)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBancoEliminar)
                    .addComponent(btnBancoAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMedida.addTab("Banco", jPanel3);

        TblFinca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TRAMITE", "FINCA", "LETRA", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblFinca.setFillsViewportHeight(true);
        TblFinca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblFincaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TblFinca);
        if (TblFinca.getColumnModel().getColumnCount() > 0) {
            TblFinca.getColumnModel().getColumn(0).setResizable(false);
            TblFinca.getColumnModel().getColumn(1).setResizable(false);
            TblFinca.getColumnModel().getColumn(2).setResizable(false);
            TblFinca.getColumnModel().getColumn(3).setResizable(false);
        }

        btnFincaAgregar.setText("Agregar");
        btnFincaAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFincaAgregarActionPerformed(evt);
            }
        });

        btnFincaEliminar.setText("Eliminar");
        btnFincaEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFincaEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(btnFincaAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFincaEliminar)
                .addContainerGap())
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFincaEliminar)
                    .addComponent(btnFincaAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMedida.addTab("Finca", jPanel4);

        TblSalario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SALARIO", "EMPRESA", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblSalario.setFillsViewportHeight(true);
        TblSalario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblSalarioMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TblSalario);
        if (TblSalario.getColumnModel().getColumnCount() > 0) {
            TblSalario.getColumnModel().getColumn(0).setResizable(false);
            TblSalario.getColumnModel().getColumn(1).setResizable(false);
            TblSalario.getColumnModel().getColumn(2).setResizable(false);
        }

        btnSalarioAgregar.setText("Agregar");
        btnSalarioAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalarioAgregarActionPerformed(evt);
            }
        });

        btnSalarioEliminar.setText("Eliminar");
        btnSalarioEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalarioEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(btnSalarioAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalarioEliminar)
                .addContainerGap())
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalarioEliminar)
                    .addComponent(btnSalarioAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMedida.addTab("Salario", jPanel5);

        TblVehiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "VEHICULO", "MEDIDA", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblVehiculo.setFillsViewportHeight(true);
        TblVehiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblVehiculoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TblVehiculo);
        if (TblVehiculo.getColumnModel().getColumnCount() > 0) {
            TblVehiculo.getColumnModel().getColumn(0).setResizable(false);
            TblVehiculo.getColumnModel().getColumn(1).setResizable(false);
            TblVehiculo.getColumnModel().getColumn(2).setResizable(false);
        }

        btnVehiculoAgregar.setText("Agregar");
        btnVehiculoAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiculoAgregarActionPerformed(evt);
            }
        });

        btnVehiculoEliminar.setText("Eliminar");
        btnVehiculoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiculoEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(btnVehiculoAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVehiculoEliminar)
                .addContainerGap())
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVehiculoEliminar)
                    .addComponent(btnVehiculoAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMedida.addTab("Vehículo", jPanel6);

        TblOtros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OTROS", "MEDIDA", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblOtros.setFillsViewportHeight(true);
        TblOtros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblOtrosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TblOtros);
        if (TblOtros.getColumnModel().getColumnCount() > 0) {
            TblOtros.getColumnModel().getColumn(0).setResizable(false);
            TblOtros.getColumnModel().getColumn(1).setResizable(false);
            TblOtros.getColumnModel().getColumn(2).setResizable(false);
        }

        btnOtrosAgregar.setText("Agregar");
        btnOtrosAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtrosAgregarActionPerformed(evt);
            }
        });

        btnOtrosEliminar.setText("Eliminar");
        btnOtrosEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtrosEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(413, Short.MAX_VALUE)
                .addComponent(btnOtrosAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOtrosEliminar)
                .addContainerGap())
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOtrosEliminar)
                    .addComponent(btnOtrosAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMedida.addTab("Otros", jPanel8);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMedida)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Medidas Precautorias", jPanel7);

        jLabel3.setText("Descripción");

        areDescripcion.setColumns(20);
        areDescripcion.setRows(5);
        jScrollPane1.setViewportView(areDescripcion);

        jLabel12.setText("Procuración");

        areDescripcionProcuracion.setColumns(20);
        areDescripcionProcuracion.setRows(5);
        jScrollPane8.setViewportView(areDescripcionProcuracion);

        jLabel13.setText("Razon Notificación");

        areDescripcionNotificacion.setColumns(20);
        areDescripcionNotificacion.setRows(5);
        jScrollPane9.setViewportView(areDescripcionNotificacion);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(jLabel13))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Descripción", jPanel9);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if(this.accion == 0) {
            this.insertar();
        } else {
            this.modificar();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnArraigoAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArraigoAgregarActionPerformed
        Juicio_Arraigo a = new Juicio_Arraigo(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        DefaultTableModel modelo_arraigo = (DefaultTableModel) this.TblArraigo.getModel();
        Object[] fila = new Object[2];
        fila[0] = a.arraigo;
        fila[1] = CalendarToString(a.deligenciado);
        modelo_arraigo.addRow(fila);
        this.TblArraigo.setModel(modelo_arraigo);
    }//GEN-LAST:event_btnArraigoAgregarActionPerformed

    private void btnArraigoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArraigoEliminarActionPerformed
        Integer fila_posicion = this.TblArraigo.getSelectedRow();
        
        if (fila_posicion != -1) {
            DefaultTableModel modelo_arraigo = (DefaultTableModel) this.TblArraigo.getModel();
            modelo_arraigo.removeRow(fila_posicion);
            this.TblArraigo.setModel(modelo_arraigo);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnArraigoEliminarActionPerformed

    private void btnBancoAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBancoAgregarActionPerformed
        Juicio_Banco a = new Juicio_Banco(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        DefaultTableModel modelo_banco = (DefaultTableModel) this.TblBanco.getModel();
        Object[] fila = new Object[2];
        fila[0] = a.bancos;
        fila[1] = CalendarToString(a.deligenciado);
        modelo_banco.addRow(fila);
        this.TblBanco.setModel(modelo_banco);
    }//GEN-LAST:event_btnBancoAgregarActionPerformed

    private void btnBancoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBancoEliminarActionPerformed
        Integer fila_posicion = this.TblBanco.getSelectedRow();
        
        if (fila_posicion != -1) {
            DefaultTableModel modelo_banco = (DefaultTableModel) this.TblBanco.getModel();
            modelo_banco.removeRow(fila_posicion);
            this.TblBanco.setModel(modelo_banco);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnBancoEliminarActionPerformed

    private void btnFincaAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFincaAgregarActionPerformed
        Juicio_Finca a = new Juicio_Finca(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        DefaultTableModel modelo_finca = (DefaultTableModel) this.TblFinca.getModel();
        Object[] fila = new Object[4];
        fila[0] = a.tramite;
        fila[1] = a.finca;
        fila[2] = a.letra;
        fila[3] = CalendarToString(a.deligenciado);
        modelo_finca.addRow(fila);
        this.TblFinca.setModel(modelo_finca);
    }//GEN-LAST:event_btnFincaAgregarActionPerformed

    private void btnFincaEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFincaEliminarActionPerformed
        Integer fila_posicion = this.TblFinca.getSelectedRow();
        
        if (fila_posicion != -1) {
            DefaultTableModel modelo_finca = (DefaultTableModel) this.TblFinca.getModel();
            modelo_finca.removeRow(fila_posicion);
            this.TblFinca.setModel(modelo_finca);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnFincaEliminarActionPerformed

    private void btnSalarioAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalarioAgregarActionPerformed
        Juicio_Salario a = new Juicio_Salario(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        DefaultTableModel modelo_salario = (DefaultTableModel) this.TblSalario.getModel();
        Object[] fila = new Object[3];
        fila[0] = a.salario;
        fila[1] = a.empresa;
        fila[2] = CalendarToString(a.deligenciado);
        modelo_salario.addRow(fila);
        this.TblSalario.setModel(modelo_salario);
    }//GEN-LAST:event_btnSalarioAgregarActionPerformed

    private void btnSalarioEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalarioEliminarActionPerformed
        Integer fila_posicion = this.TblSalario.getSelectedRow();
        
        if (fila_posicion != -1) {
            DefaultTableModel modelo_salario = (DefaultTableModel) this.TblSalario.getModel();
            modelo_salario.removeRow(fila_posicion);
            this.TblSalario.setModel(modelo_salario);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnSalarioEliminarActionPerformed

    private void btnVehiculoAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVehiculoAgregarActionPerformed
        Juicio_Vehiculo a = new Juicio_Vehiculo(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        DefaultTableModel modelo_vehiculo = (DefaultTableModel) this.TblVehiculo.getModel();
        Object[] fila = new Object[3];
        fila[0] = a.vehiculo;
        fila[1] = a.medida;
        fila[2] = CalendarToString(a.deligenciado);
        modelo_vehiculo.addRow(fila);
        this.TblVehiculo.setModel(modelo_vehiculo);
    }//GEN-LAST:event_btnVehiculoAgregarActionPerformed

    private void btnVehiculoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVehiculoEliminarActionPerformed
        Integer fila_posicion = this.TblVehiculo.getSelectedRow();
        
        if (fila_posicion != -1) {
            DefaultTableModel modelo_salario = (DefaultTableModel) this.TblVehiculo.getModel();
            modelo_salario.removeRow(fila_posicion);
            this.TblVehiculo.setModel(modelo_salario);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnVehiculoEliminarActionPerformed

    private void btnOtrosAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtrosAgregarActionPerformed
        Juicio_Otros a = new Juicio_Otros(new javax.swing.JFrame(), true, conn, usuario);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        DefaultTableModel modelo_otros = (DefaultTableModel) this.TblOtros.getModel();
        Object[] fila = new Object[3];
        fila[0] = a.otros;
        fila[1] = a.medida;
        fila[2] = CalendarToString(a.deligenciado);
        modelo_otros.addRow(fila);
        this.TblOtros.setModel(modelo_otros);
    }//GEN-LAST:event_btnOtrosAgregarActionPerformed

    private void btnOtrosEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtrosEliminarActionPerformed
        Integer fila_posicion = this.TblOtros.getSelectedRow();
        
        if (fila_posicion != -1) {
            DefaultTableModel modelo_otros = (DefaultTableModel) this.TblOtros.getModel();
            modelo_otros.removeRow(fila_posicion);
            this.TblOtros.setModel(modelo_otros);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnOtrosEliminarActionPerformed

    private void TblArraigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblArraigoMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Integer fila = TblArraigo.rowAtPoint(evt.getPoint());
                Juicio_Arraigo a = new Juicio_Arraigo(new javax.swing.JFrame(), true, conn, usuario);
                SimpleDateFormat Formateador = new SimpleDateFormat("yyyy/MM/dd"); 
                Date dateObj = Formateador.parse(this.TblArraigo.getValueAt(fila,1).toString());
                Calendar fecha_deligenciado = Calendar.getInstance();
                fecha_deligenciado.setTime(dateObj);
                a.cargar_datos(this.TblArraigo.getValueAt(fila,0).toString(), fecha_deligenciado);
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_TblArraigoMouseClicked

    private void TblBancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblBancoMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Integer fila = TblBanco.rowAtPoint(evt.getPoint());
                Juicio_Banco a = new Juicio_Banco(new javax.swing.JFrame(), true, conn, usuario);
                SimpleDateFormat Formateador = new SimpleDateFormat("yyyy/MM/dd"); 
                Date dateObj = Formateador.parse(this.TblBanco.getValueAt(fila,1).toString());
                Calendar fecha_deligenciado = Calendar.getInstance();
                fecha_deligenciado.setTime(dateObj);
                a.cargar_datos(this.TblBanco.getValueAt(fila,0).toString(), fecha_deligenciado);
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_TblBancoMouseClicked

    private void TblFincaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblFincaMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Integer fila = TblFinca.rowAtPoint(evt.getPoint());
                Juicio_Finca a = new Juicio_Finca(new javax.swing.JFrame(), true, conn, usuario);
                SimpleDateFormat Formateador = new SimpleDateFormat("yyyy/MM/dd"); 
                Date dateObj = Formateador.parse(this.TblFinca.getValueAt(fila,3).toString());
                Calendar fecha_deligenciado = Calendar.getInstance();
                fecha_deligenciado.setTime(dateObj);
                
                a.cargar_datos(this.TblFinca.getValueAt(fila,1).toString(), this.TblFinca.getValueAt(fila,2).toString(), fecha_deligenciado, this.TblFinca.getValueAt(fila,0).toString());
                
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_TblFincaMouseClicked

    private void TblSalarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblSalarioMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Integer fila = TblSalario.rowAtPoint(evt.getPoint());
                Juicio_Salario a = new Juicio_Salario(new javax.swing.JFrame(), true, conn, usuario);
                SimpleDateFormat Formateador = new SimpleDateFormat("yyyy/MM/dd"); 
                Date dateObj = Formateador.parse(this.TblSalario.getValueAt(fila,2).toString());
                Calendar fecha_deligenciado = Calendar.getInstance();
                fecha_deligenciado.setTime(dateObj);
                a.cargar_datos(this.TblSalario.getValueAt(fila,0).toString(), this.TblSalario.getValueAt(fila,1).toString(), fecha_deligenciado);
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_TblSalarioMouseClicked

    private void TblVehiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblVehiculoMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Integer fila = TblVehiculo.rowAtPoint(evt.getPoint());
                Juicio_Vehiculo a = new Juicio_Vehiculo(new javax.swing.JFrame(), true, conn, usuario);
                SimpleDateFormat Formateador = new SimpleDateFormat("yyyy/MM/dd"); 
                Date dateObj = Formateador.parse(this.TblVehiculo.getValueAt(fila,2).toString());
                Calendar fecha_deligenciado = Calendar.getInstance();
                fecha_deligenciado.setTime(dateObj);
                a.cargar_datos(this.TblVehiculo.getValueAt(fila,1).toString(), this.TblVehiculo.getValueAt(fila,0).toString(), fecha_deligenciado);
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_TblVehiculoMouseClicked

    private void TblOtrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblOtrosMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                Integer fila = TblOtros.rowAtPoint(evt.getPoint());
                Juicio_Otros a = new Juicio_Otros(new javax.swing.JFrame(), true, conn, usuario);
                SimpleDateFormat Formateador = new SimpleDateFormat("yyyy/MM/dd"); 
                Date dateObj = Formateador.parse(this.TblOtros.getValueAt(fila,2).toString());
                Calendar fecha_deligenciado = Calendar.getInstance();
                fecha_deligenciado.setTime(dateObj);
                a.cargar_datos(this.TblOtros.getValueAt(fila,1).toString(), this.TblOtros.getValueAt(fila,0).toString(), fecha_deligenciado);
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
        }
    }//GEN-LAST:event_TblOtrosMouseClicked

    private void insertar() {
        com.lexcom.driver.Juicio drive = new com.lexcom.driver.Juicio(this.conn, this.usuario);
        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
        com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);
        
        String resultado = drive.insertar(
                this.deudor, 
                DJuzgado.obtener_indice(this.cbxJuzgado.getSelectedItem().toString()), 
                this.dccFecha.getSelectedDate(), 
                this.txtNoJuicio.getText(), 
                Double.parseDouble(this.spnMonto.getValue().toString()), 
                this.areDescripcion.getText(), 
                (DefaultTableModel) this.TblArraigo.getModel(),
                (DefaultTableModel) this.TblBanco.getModel(),
                (DefaultTableModel) this.TblFinca.getModel(),
                (DefaultTableModel) this.TblSalario.getModel(),
                (DefaultTableModel) this.TblVehiculo.getModel(),
                (DefaultTableModel) this.TblOtros.getModel(),
                DProcurador.obtener_indice(this.cbxProcurador.getSelectedItem().toString()),
                this.areDescripcionNotificacion.getText(),
                Integer.parseInt(this.spnNotificador.getValue().toString()),
                this.txtAbogadoDeudor.getText(),
                DTipoJuicio.obtener_indice(this.cbxTipoJuicio.getSelectedItem().toString()), 
                this.dccMemorial.getSelectedDate(),
                this.areDescripcionProcuracion.getText(),
                this.dccFechaAdminision.getSelectedDate(),
                this.cbxNotificado.getSelectedItem().toString(),
                this.dccFechaNotificacion.getSelectedDate(),
                this.txtDepositario.getText(),
                this.txtTiempoEstimado.getText(),
                DTituloEjecutivo.obtener_indice(this.cbxTituloEjecutivo.getSelectedItem().toString()));
        
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        com.lexcom.driver.Juicio drive = new com.lexcom.driver.Juicio(this.conn, this.usuario);
        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
        com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);
        
        String resultado = drive.modificar(
                this.seleccion,
                this.deudor, 
                DJuzgado.obtener_indice(this.cbxJuzgado.getSelectedItem().toString()), 
                this.dccFecha.getSelectedDate(), 
                this.txtNoJuicio.getText(), 
                Double.parseDouble(this.spnMonto.getValue().toString()), 
                this.areDescripcion.getText(), 
                (DefaultTableModel) this.TblArraigo.getModel(),
                (DefaultTableModel) this.TblBanco.getModel(),
                (DefaultTableModel) this.TblFinca.getModel(),
                (DefaultTableModel) this.TblSalario.getModel(),
                (DefaultTableModel) this.TblVehiculo.getModel(),
                (DefaultTableModel) this.TblOtros.getModel(),
                DProcurador.obtener_indice(this.cbxProcurador.getSelectedItem().toString()),
                this.areDescripcionNotificacion.getText(),
                Integer.parseInt(this.spnNotificador.getValue().toString()),
                this.txtAbogadoDeudor.getText(),
                DTipoJuicio.obtener_indice(this.cbxTipoJuicio.getSelectedItem().toString()),
                this.dccMemorial.getSelectedDate(),
                this.areDescripcionProcuracion.getText(),
                this.dccFechaAdminision.getSelectedDate(),
                this.cbxNotificado.getSelectedItem().toString(),
                this.dccFechaNotificacion.getSelectedDate(),
                this.txtDepositario.getText(),
                this.txtTiempoEstimado.getText(),
                DTituloEjecutivo.obtener_indice(this.cbxTituloEjecutivo.getSelectedItem().toString()));
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Juicio drive = new com.lexcom.driver.Juicio(this.conn, this.usuario);
        com.lexcom.driver.Juicio resultado = drive.obtener(seleccion, (DefaultTableModel) this.TblArraigo.getModel(), (DefaultTableModel) this.TblBanco.getModel(), (DefaultTableModel) this.TblFinca.getModel(), (DefaultTableModel) this.TblSalario.getModel(), (DefaultTableModel) this.TblVehiculo.getModel(), (DefaultTableModel) this.TblOtros.getModel());
        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
        com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);
        
        this.cbxJuzgado.setSelectedItem(DJuzgado.obtener_nombre(resultado.juzgado));
        this.dccFecha.setSelectedDate(resultado.fecha);
        this.txtNoJuicio.setText(resultado.no_juicio);
        this.spnMonto.setValue(resultado.monto);
        this.areDescripcion.setText(resultado.descripcion);
        this.TblArraigo.setModel(resultado.modelo_arraigo);
        this.TblBanco.setModel(resultado.modelo_banco);
        this.TblFinca.setModel(resultado.modelo_finca);
        this.TblSalario.setModel(resultado.modelo_salario);
        this.TblVehiculo.setModel(resultado.modelo_vehiculo);
        this.TblOtros.setModel(resultado.modelo_otros);
        this.cbxProcurador.setSelectedItem(DProcurador.obtener_nombre(resultado.procurador));
        this.areDescripcionNotificacion.setText(resultado.razon_notificacion);
        this.spnNotificador.setValue(resultado.notificador);
        this.txtAbogadoDeudor.setText(resultado.abogado_deudor);
        this.cbxTipoJuicio.setSelectedItem(DTipoJuicio.obtener_nombre(resultado.tipo_juicio));
        this.cbxTituloEjecutivo.setSelectedItem(DTituloEjecutivo.obtener_nombre(resultado.titulo_ejecutivo));
        this.dccMemorial.setSelectedDate(resultado.memorial);
        this.areDescripcionProcuracion.setText(resultado.procuracion);
        this.dccFechaAdminision.setSelectedDate(resultado.fecha_admision_demanda);
        this.cbxNotificado.setSelectedItem(resultado.deudor_notificado);
        this.dccFechaNotificacion.setSelectedDate(resultado.fecha_notificacion);
        this.txtDepositario.setText(resultado.depositario);
        this.txtTiempoEstimado.setText(resultado.tiempo_estimado);
    }
    
    private String CalendarToString(Calendar fecha) {
        
        Integer dia = fecha.get(Calendar.DATE);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer ano = fecha.get(Calendar.YEAR);
        String resultado = ano.toString() + "/" + mes.toString() + "/" + dia.toString();
        
        return resultado;
    }
    
    private Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblArraigo;
    private javax.swing.JTable TblBanco;
    private javax.swing.JTable TblFinca;
    private javax.swing.JTable TblOtros;
    private javax.swing.JTable TblSalario;
    private javax.swing.JTable TblVehiculo;
    private javax.swing.JTextArea areDescripcion;
    private javax.swing.JTextArea areDescripcionNotificacion;
    private javax.swing.JTextArea areDescripcionProcuracion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnArraigoAgregar;
    private javax.swing.JButton btnArraigoEliminar;
    private javax.swing.JButton btnBancoAgregar;
    private javax.swing.JButton btnBancoEliminar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFincaAgregar;
    private javax.swing.JButton btnFincaEliminar;
    private javax.swing.JButton btnOtrosAgregar;
    private javax.swing.JButton btnOtrosEliminar;
    private javax.swing.JButton btnSalarioAgregar;
    private javax.swing.JButton btnSalarioEliminar;
    private javax.swing.JButton btnVehiculoAgregar;
    private javax.swing.JButton btnVehiculoEliminar;
    private javax.swing.JComboBox cbxJuzgado;
    private javax.swing.JComboBox cbxNotificado;
    private javax.swing.JComboBox cbxProcurador;
    private javax.swing.JComboBox<String> cbxTipoJuicio;
    private javax.swing.JComboBox<String> cbxTituloEjecutivo;
    private datechooser.beans.DateChooserCombo dccFecha;
    private datechooser.beans.DateChooserCombo dccFechaAdminision;
    private datechooser.beans.DateChooserCombo dccFechaNotificacion;
    private datechooser.beans.DateChooserCombo dccMemorial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane panelMedida;
    private javax.swing.JSpinner spnMonto;
    private javax.swing.JSpinner spnNotificador;
    private javax.swing.JTextField txtAbogadoDeudor;
    private javax.swing.JTextField txtDepositario;
    private javax.swing.JTextField txtNoJuicio;
    private javax.swing.JTextField txtTiempoEstimado;
    // End of variables declaration//GEN-END:variables
}
