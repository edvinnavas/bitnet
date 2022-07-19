package com.lexcom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Gestion_Pendiente_Cobros extends javax.swing.JDialog {
    Connection conn;
    Integer usuario;
    Integer deudor;
    
    String estado_judicial;
    String status_judicial;
    String estado_extrajudicial;
    String status_extrajudicial;
    String intencion_pago;
    String razon_deuda;
    
    public Gestion_Pendiente_Cobros(
            java.awt.Frame parent, 
            boolean modal, 
            Connection conn, 
            Integer usuario, 
            Integer deudor,
            
            String estado_judicial,
            String status_judicial,
            String estado_extrajudicial,
            String status_extrajudicial,
            String intencion_pago,
            String razon_deuda) {
        
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.deudor = deudor;
        initComponents();
        
        //this.setUndecorated(true);
        this.setDefaultCloseOperation(0);
        
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        this.cbxEstadoJudicial.setModel(DEstado.dar_lista_comb());
        this.cbxEstadoJudicial.setSelectedItem(estado_judicial);
        
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatusJudicial.setModel(DStatus.dar_lista_comb(this.cbxEstadoJudicial.getSelectedItem().toString()));
        this.cbxStatusJudicial.setSelectedItem(status_judicial);
        
        com.lexcom.driver.EstadoExtra DEstadoExtra = new com.lexcom.driver.EstadoExtra(this.conn, this.usuario);
        this.cbxEstadoExtrajudicial.setModel(DEstadoExtra.dar_lista_comb_vacio());
        this.cbxEstadoExtrajudicial.setSelectedItem("Seleccione...");
        this.lbEstadoActual.setText(estado_extrajudicial);
        
        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        this.cbxStatusExtrajudicial.setModel(DStatusExtra.dar_lista_comb_vacio(this.cbxEstadoExtrajudicial.getSelectedItem().toString()));
        this.cbxStatusExtrajudicial.setSelectedItem("Seleccione...");
        this.lbEstatusActual.setText(status_extrajudicial);
        
        com.lexcom.driver.Intencion_Pago DIntencion_Pago = new com.lexcom.driver.Intencion_Pago(this.conn, this.usuario);
        this.cbxIntencionPago.setModel(DIntencion_Pago.dar_lista());
        this.cbxIntencionPago.setSelectedItem(intencion_pago);
        
        com.lexcom.driver.Razon_Deuda DRazon_Deuda = new com.lexcom.driver.Razon_Deuda(this.conn, this.usuario);
        this.cbxRazonDeuda.setModel(DRazon_Deuda.dar_lista());
        this.cbxRazonDeuda.setSelectedItem(razon_deuda);
        
        com.lexcom.driver.Tipo_Codigo_Contactabilidad DTipoCodigoContactabilidad = new com.lexcom.driver.Tipo_Codigo_Contactabilidad(this.conn, this.usuario);
        this.cbxTipoGestionCodigo_p.setModel(DTipoCodigoContactabilidad.dar_lista());
        this.cbxTipoGestion.setModel(DTipoCodigoContactabilidad.dar_lista());
        
        com.lexcom.driver.Codigo_Contactabilidad DCodigo_Contactabilidad = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
        this.cbxGestionCodigo.setModel(DCodigo_Contactabilidad.dar_lista_comb(this.cbxTipoGestion.getSelectedItem().toString(),"%%"));
        this.cbxGestionCodigo_p.setModel(DCodigo_Contactabilidad.dar_lista_comb(this.cbxTipoGestionCodigo_p.getSelectedItem().toString(), this.cbxContacto.getSelectedItem().toString()));
        
        // BLOQUEA OPCIONES DE ESTADO-STATUS JUDICIAL Y EXTRAJUDICIAL.
        this.cbxEstadoJudicial.setEnabled(false);
        this.cbxStatusJudicial.setEnabled(false);
        this.cbxEstadoExtrajudicial.setEnabled(false);
        this.cbxStatusExtrajudicial.setEnabled(false);
        this.cbxIntencionPago.setEnabled(false);
        this.cbxRazonDeuda.setEnabled(false);
        
        com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Usuario DUsuario_T = DUsuario.obtener(this.usuario);
        if(DUsuario_T.asistente.equals("SI")) {
            this.cbxEstadoJudicial.setEnabled(true);
            this.cbxStatusJudicial.setEnabled(true);
            this.cbxEstadoExtrajudicial.setEnabled(true);
            this.cbxStatusExtrajudicial.setEnabled(true);
            this.cbxIntencionPago.setEnabled(true);
            this.cbxRazonDeuda.setEnabled(true);
        } else {
            if(DUsuario_T.gestor.equals("SI")) {
                this.cbxEstadoExtrajudicial.setEnabled(true);
                this.cbxStatusExtrajudicial.setEnabled(true);
                this.cbxIntencionPago.setEnabled(true);
                this.cbxRazonDeuda.setEnabled(true);
            }
            if(DUsuario_T.procurador.equals("SI") || DUsuario_T.digitador.equals("SI")) {
                this.cbxEstadoJudicial.setEnabled(true);
                this.cbxStatusJudicial.setEnabled(true);
            }
        }
        
        com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(this.conn, this.usuario);
        com.lexcom.driver.Deudor resultado = DExpediente.Dar_Datos_Deudor(deudor);
        if(DExpediente.estado_status_bloquea_judicial(resultado.sestado_extra, resultado.estatus_extra).equals("SI")) {
            this.cbxEstadoJudicial.setEnabled(false);
            this.cbxStatusJudicial.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbxTipoGestionCodigo_p = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cbxGestionCodigo_p = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        areGestionDescripcion_p = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbxIntencionPago = new javax.swing.JComboBox();
        cbxContacto = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cbxRazonDeuda = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbxEstadoExtrajudicial = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cbxStatusExtrajudicial = new javax.swing.JComboBox();
        lbEstadoActual1 = new javax.swing.JLabel();
        lbEstatusActual1 = new javax.swing.JLabel();
        lbEstadoActual = new javax.swing.JLabel();
        lbEstatusActual = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        cbxGestionCodigo = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        areGestionDescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dccFecha = new datechooser.beans.DateChooserCombo();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxEstadoJudicial = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbxStatusJudicial = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cbxTipoGestion = new javax.swing.JComboBox();
        btnGestionInsertar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("INSERTAR GESTIÓN.");
        setResizable(false);

        jLabel9.setText("Tipo de gestión:");

        cbxTipoGestionCodigo_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoGestionCodigo_pActionPerformed(evt);
            }
        });

        jLabel10.setText("Gestión:");

        areGestionDescripcion_p.setColumns(20);
        areGestionDescripcion_p.setRows(5);
        areGestionDescripcion_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areGestionDescripcion_pKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(areGestionDescripcion_p);

        jLabel11.setText("Descripción de la gestión:");

        jLabel8.setText("Intención de pago");

        cbxContacto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO", "SI" }));
        cbxContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxContactoActionPerformed(evt);
            }
        });

        jLabel12.setText("Contacto:");

        jLabel14.setText("Razón de deuda");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Extrajudicial"));

        jLabel6.setText("Estado");

        cbxEstadoExtrajudicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoExtrajudicialActionPerformed(evt);
            }
        });

        jLabel7.setText("Estatus");

        lbEstadoActual1.setFont(new java.awt.Font("Dialog", 0, 12));
        lbEstadoActual1.setText("Estado actual:");

        lbEstatusActual1.setFont(new java.awt.Font("Dialog", 0, 12));
        lbEstatusActual1.setText("Estatus actual:");

        lbEstadoActual.setFont(new java.awt.Font("Dialog", 1, 12));
        lbEstadoActual.setForeground(new java.awt.Color(-16776961,true));
        lbEstadoActual.setText("jLabel15");

        lbEstatusActual.setFont(new java.awt.Font("Dialog", 1, 12));
        lbEstatusActual.setForeground(new java.awt.Color(-16776961,true));
        lbEstatusActual.setText("jLabel16");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbxStatusExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbEstatusActual1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbxEstadoExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbEstadoActual1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEstadoActual)
                    .addComponent(lbEstatusActual))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxEstadoExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEstadoActual1)
                    .addComponent(lbEstadoActual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbxStatusExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEstatusActual1)
                    .addComponent(lbEstatusActual))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxContacto, 0, 477, Short.MAX_VALUE)
                            .addComponent(cbxTipoGestionCodigo_p, 0, 477, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(55, 55, 55)
                        .addComponent(cbxGestionCodigo_p, 0, 477, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxRazonDeuda, 0, 467, Short.MAX_VALUE)
                            .addComponent(cbxIntencionPago, 0, 467, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbxTipoGestionCodigo_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxGestionCodigo_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxIntencionPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxRazonDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Gestión", jPanel1);

        areGestionDescripcion.setColumns(20);
        areGestionDescripcion.setRows(5);
        areGestionDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areGestionDescripcionKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(areGestionDescripcion);

        jLabel3.setText("Codigo");

        jLabel4.setText("Gestión");

        jLabel1.setText("Fecha");

        dccFecha.setNothingAllowed(false);
        dccFecha.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Judicial"));

        jLabel2.setText("Estado");

        cbxEstadoJudicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoJudicialActionPerformed(evt);
            }
        });

        jLabel5.setText("Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxStatusJudicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEstadoJudicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(299, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxEstadoJudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxStatusJudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jLabel13.setText("Tipo gestión");

        cbxTipoGestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoGestionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxGestionCodigo, 0, 496, Short.MAX_VALUE)
                            .addComponent(cbxTipoGestion, 0, 496, Short.MAX_VALUE)
                            .addComponent(dccFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbxTipoGestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxGestionCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Proxima gestión", jPanel11);

        btnGestionInsertar.setText("Insertar");
        btnGestionInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionInsertarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGestionInsertar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(btnGestionInsertar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void areGestionDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areGestionDescripcionKeyReleased
        if (evt.getKeyCode() == 123) {
            Dar_Frase_Predeterminada a = new Dar_Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 0);
            a.set_tipo("GESTION");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.areGestionDescripcion.setText(this.areGestionDescripcion.getText() + a.dar_frase());
        }
}//GEN-LAST:event_areGestionDescripcionKeyReleased

    private void btnGestionInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionInsertarActionPerformed
        if (!this.areGestionDescripcion_p.getText().equals("")) {
            if (this.cbxGestionCodigo_p.getSelectedIndex() > 0) {
                if (!this.areGestionDescripcion.getText().equals("")) {
                    if (this.cbxGestionCodigo.getSelectedIndex() > 0) {
                        if(!this.cbxEstadoExtrajudicial.getSelectedItem().toString().equals("Seleccione...")) {
                            if(!this.cbxStatusExtrajudicial.getSelectedItem().toString().equals("Seleccione...")) {
                                com.lexcom.driver.Codigo_Contactabilidad DCodigo_Contactabilidad = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
                                com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
                                com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
                                com.lexcom.driver.EstadoExtra DEstadoExtra = new com.lexcom.driver.EstadoExtra(this.conn, this.usuario);
                                com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
                                com.lexcom.driver.Intencion_Pago DIntencion_Pago = new com.lexcom.driver.Intencion_Pago(this.conn, this.usuario);
                                com.lexcom.driver.Razon_Deuda DRazon_Deuda = new com.lexcom.driver.Razon_Deuda(this.conn, this.usuario);

                                com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(this.conn, this.usuario);
                                DExpediente.insertar_btn_gestion(
                                        this.deudor, 
                                        this.usuario, 
                                        DCodigo_Contactabilidad.obtener_indice(this.cbxGestionCodigo_p.getSelectedItem().toString()), 
                                        this.areGestionDescripcion_p.getText(),
                                        this.cbxContacto.getSelectedItem().toString());
                                this.cbxGestionCodigo_p.setSelectedIndex(0);
                                this.areGestionDescripcion_p.setText("");

                                com.lexcom.driver.Gestion_Pendiente_Cobros drive = new com.lexcom.driver.Gestion_Pendiente_Cobros(this.conn, this.usuario);
                                drive.insertar_btn_gestion(
                                        this.deudor,
                                        this.dccFecha.getSelectedDate(),
                                        this.usuario,
                                        DCodigo_Contactabilidad.obtener_indice(this.cbxGestionCodigo.getSelectedItem().toString()),
                                        this.areGestionDescripcion.getText(),
                                        DEstado.obtener_indice(this.cbxEstadoJudicial.getSelectedItem().toString()),
                                        DStatus.obtener_indice(this.cbxStatusJudicial.getSelectedItem().toString()),
                                        DEstadoExtra.obtener_indice(this.cbxEstadoExtrajudicial.getSelectedItem().toString()),
                                        DStatusExtra.obtener_indice(this.cbxStatusExtrajudicial.getSelectedItem().toString()),
                                        DIntencion_Pago.obtener_indice(this.cbxIntencionPago.getSelectedItem().toString()),
                                        DRazon_Deuda.obtener_indice(this.cbxRazonDeuda.getSelectedItem().toString()));
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Debe actualizar el ESTATUS extrajudicial del cliente.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe actualizar el ESTADO extrajudicial del cliente.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe selecionar un código de resultado para la gestión pendiente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una descripción de la gestión pendiente.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe selecionar un código de resultado para la gestión.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar una descripción de la gestión.");
        }
}//GEN-LAST:event_btnGestionInsertarActionPerformed

    private void cbxEstadoJudicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoJudicialActionPerformed
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatusJudicial.setModel(DStatus.dar_lista_comb(this.cbxEstadoJudicial.getSelectedItem().toString()));
}//GEN-LAST:event_cbxEstadoJudicialActionPerformed

    private void cbxEstadoExtrajudicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoExtrajudicialActionPerformed
        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        this.cbxStatusExtrajudicial.setModel(DStatusExtra.dar_lista_comb_vacio(this.cbxEstadoExtrajudicial.getSelectedItem().toString()));
}//GEN-LAST:event_cbxEstadoExtrajudicialActionPerformed

    private void cbxTipoGestionCodigo_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoGestionCodigo_pActionPerformed
        com.lexcom.driver.Codigo_Contactabilidad DCodigo_Contactabilidad = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
        this.cbxGestionCodigo_p.setModel(DCodigo_Contactabilidad.dar_lista_comb(this.cbxTipoGestionCodigo_p.getSelectedItem().toString(),this.cbxContacto.getSelectedItem().toString()));
    }//GEN-LAST:event_cbxTipoGestionCodigo_pActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void cbxTipoGestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoGestionActionPerformed
        com.lexcom.driver.Codigo_Contactabilidad DCodigo_Contactabilidad = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
        this.cbxGestionCodigo.setModel(DCodigo_Contactabilidad.dar_lista_comb(this.cbxTipoGestion.getSelectedItem().toString(),"%%"));
    }//GEN-LAST:event_cbxTipoGestionActionPerformed

    private void cbxContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxContactoActionPerformed
        com.lexcom.driver.Codigo_Contactabilidad DCodigo_Contactabilidad = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
        this.cbxGestionCodigo_p.setModel(DCodigo_Contactabilidad.dar_lista_comb(this.cbxTipoGestionCodigo_p.getSelectedItem().toString(),this.cbxContacto.getSelectedItem().toString()));
    }//GEN-LAST:event_cbxContactoActionPerformed

    private void areGestionDescripcion_pKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areGestionDescripcion_pKeyReleased
        if (evt.getKeyCode() == 123) {
            Dar_Frase_Predeterminada a = new Dar_Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 0);
            a.set_tipo("GESTION");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.areGestionDescripcion_p.setText(this.areGestionDescripcion_p.getText() + a.dar_frase());
        }
    }//GEN-LAST:event_areGestionDescripcion_pKeyReleased
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areGestionDescripcion;
    private javax.swing.JTextArea areGestionDescripcion_p;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGestionInsertar;
    private javax.swing.JComboBox cbxContacto;
    private javax.swing.JComboBox cbxEstadoExtrajudicial;
    private javax.swing.JComboBox cbxEstadoJudicial;
    private javax.swing.JComboBox cbxGestionCodigo;
    private javax.swing.JComboBox cbxGestionCodigo_p;
    private javax.swing.JComboBox cbxIntencionPago;
    private javax.swing.JComboBox cbxRazonDeuda;
    private javax.swing.JComboBox cbxStatusExtrajudicial;
    private javax.swing.JComboBox cbxStatusJudicial;
    private javax.swing.JComboBox cbxTipoGestion;
    private javax.swing.JComboBox cbxTipoGestionCodigo_p;
    private datechooser.beans.DateChooserCombo dccFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbEstadoActual;
    private javax.swing.JLabel lbEstadoActual1;
    private javax.swing.JLabel lbEstatusActual;
    private javax.swing.JLabel lbEstatusActual1;
    // End of variables declaration//GEN-END:variables

}
