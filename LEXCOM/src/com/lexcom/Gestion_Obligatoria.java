package com.lexcom;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class Gestion_Obligatoria extends javax.swing.JDialog {
    Connection conn;
    Integer usuario;
    
    public String estado_judicial;
    public String status_judicial;
    public String estado_extrajudicial;
    public String status_extrajudicial;
    public String intencion_pago;
    public String razon_deuda;
    
    public Gestion_Obligatoria(
            java.awt.Frame parent, 
            boolean modal, 
            Connection conn,
            Integer usuario,
            String estado_judicial,
            String status_judicial,
            String estado_extrajudicial,
            String status_extrajudicial,
            String intencion_pago,
            String razon_deuda,
            Integer deudor) {
        
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
        
//        this.estado_judicial=estado_judicial;
//        this.status_judicial=status_judicial;
//        this.estado_extrajudicial=estado_extrajudicial;
//        this.status_extrajudicial=status_extrajudicial;
//        this.intencion_pago=intencion_pago;
//        this.razon_deuda=razon_deuda;
        
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        this.cbxEstadoJudicial.setModel(DEstado.dar_lista_comb());
        this.cbxEstadoJudicial.setSelectedItem(estado_judicial);
        
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatusJudicial.setModel(DStatus.dar_lista_comb(this.cbxEstadoJudicial.getSelectedItem().toString()));
        this.cbxStatusJudicial.setSelectedItem(status_judicial);
        
        com.lexcom.driver.EstadoExtra DEstadoExtra = new com.lexcom.driver.EstadoExtra(this.conn, this.usuario);
        this.cbxEstadoExtrajudicial.setModel(DEstadoExtra.dar_lista_comb_vacio());
        this.cbxEstadoExtrajudicial.setSelectedItem("Seleccione...");
        this.lbEstadoActual.setText(this.lbEstadoActual.getText() + " " + estado_extrajudicial);
        
        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        this.cbxStatusExtrajudicial.setModel(DStatusExtra.dar_lista_comb_vacio(this.cbxEstadoExtrajudicial.getSelectedItem().toString()));
        this.cbxStatusExtrajudicial.setSelectedItem("Seleccione...");
        this.lbEstatusActual.setText(this.lbEstatusActual.getText() + " " + status_extrajudicial);
        
        com.lexcom.driver.Intencion_Pago DIntencion_Pago = new com.lexcom.driver.Intencion_Pago(this.conn, this.usuario);
        this.cbxIntencionPago.setModel(DIntencion_Pago.dar_lista());
        this.cbxIntencionPago.setSelectedItem(intencion_pago);
        
        com.lexcom.driver.Razon_Deuda DRazon_Deuda = new com.lexcom.driver.Razon_Deuda(this.conn, this.usuario);
        this.cbxRazonDeuda.setModel(DRazon_Deuda.dar_lista());
        this.cbxRazonDeuda.setSelectedItem(razon_deuda);
        
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxEstadoJudicial = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cbxStatusJudicial = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbxIntencionPago = new javax.swing.JComboBox();
        cbxRazonDeuda = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbxEstadoExtrajudicial = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cbxStatusExtrajudicial = new javax.swing.JComboBox();
        lbEstadoActual = new javax.swing.JLabel();
        lbEstatusActual = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ACTUALIZACIÓN DE CASO.");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Judicial"));

        jLabel2.setText("Estado");

        cbxEstadoJudicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoJudicialActionPerformed(evt);
            }
        });

        jLabel1.setText("Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
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
                    .addComponent(jLabel1)
                    .addComponent(cbxStatusJudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Intención de pago"));

        jLabel3.setText("Intención de pago");

        jLabel4.setText("Razón de deuda");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxRazonDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxIntencionPago, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxIntencionPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbxRazonDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Extrajudicial"));

        jLabel5.setText("Estado");

        cbxEstadoExtrajudicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoExtrajudicialActionPerformed(evt);
            }
        });

        jLabel6.setText("Status");

        lbEstadoActual.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbEstadoActual.setForeground(new java.awt.Color(-16776961,true));
        lbEstadoActual.setText("Estado actual:");

        lbEstatusActual.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbEstatusActual.setForeground(new java.awt.Color(-16776961,true));
        lbEstatusActual.setText("Estatus actual:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbxStatusExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbEstatusActual))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbxEstadoExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbEstadoActual)))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxEstadoExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEstadoActual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxStatusExtrajudicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos", jPanel1);

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
                    .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAceptar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (!this.cbxEstadoExtrajudicial.getSelectedItem().toString().equals("Seleccione...")) {
            if (!this.cbxStatusExtrajudicial.getSelectedItem().toString().equals("Seleccione...")) {
                this.estado_judicial = this.cbxEstadoJudicial.getSelectedItem().toString();
                this.status_judicial = this.cbxStatusJudicial.getSelectedItem().toString();
                this.estado_extrajudicial = this.cbxEstadoExtrajudicial.getSelectedItem().toString();
                this.status_extrajudicial = this.cbxStatusExtrajudicial.getSelectedItem().toString();
                this.intencion_pago = this.cbxIntencionPago.getSelectedItem().toString();
                this.razon_deuda = this.cbxRazonDeuda.getSelectedItem().toString();

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Debe actualizar el ESTATUS extrajudicial del cliente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe actualizar el ESTADO extrajudicial del cliente.");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void cbxEstadoExtrajudicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoExtrajudicialActionPerformed
        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        this.cbxStatusExtrajudicial.setModel(DStatusExtra.dar_lista_comb_vacio(this.cbxEstadoExtrajudicial.getSelectedItem().toString()));
    }//GEN-LAST:event_cbxEstadoExtrajudicialActionPerformed

    private void cbxEstadoJudicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoJudicialActionPerformed
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatusJudicial.setModel(DStatus.dar_lista_comb(this.cbxEstadoJudicial.getSelectedItem().toString()));
    }//GEN-LAST:event_cbxEstadoJudicialActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JComboBox cbxEstadoExtrajudicial;
    private javax.swing.JComboBox cbxEstadoJudicial;
    private javax.swing.JComboBox cbxIntencionPago;
    private javax.swing.JComboBox cbxRazonDeuda;
    private javax.swing.JComboBox cbxStatusExtrajudicial;
    private javax.swing.JComboBox cbxStatusJudicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbEstadoActual;
    private javax.swing.JLabel lbEstatusActual;
    // End of variables declaration//GEN-END:variables

}
