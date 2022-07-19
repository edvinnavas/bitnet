package com.lexcom;

import java.sql.Connection;

public class FiltroProcurador extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    String procurador;
    String garantia;
    String status;
    String estado;
    String juzgado;
    
    public FiltroProcurador(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
        
        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        this.cbxProcurador.setModel(DProcurador.dar_lista_procuradores());
        this.cbxProcurador.addItem("TODOS");
        this.cbxProcurador.setSelectedItem("TODOS");
        
        com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
        this.cbxGarantia.setModel(DGarantia.dar_lista());
        this.cbxGarantia.addItem("TODOS");
        this.cbxGarantia.setSelectedItem("TODOS");
        
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatus.setModel(DStatus.dar_lista());
        this.cbxStatus.addItem("TODOS");
        this.cbxStatus.setSelectedItem("TODOS");
        
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        this.cbxEstado.setModel(DEstado.dar_lista());
        this.cbxEstado.addItem("TODOS");
        this.cbxEstado.setSelectedItem("TODOS");
        
        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
        this.cbxJuzgado.setModel(DJuzgado.dar_lista());
        this.cbxJuzgado.addItem("TODOS");
        this.cbxJuzgado.setSelectedItem("TODOS");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxProcurador = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cbxGarantia = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox();
        cbxEstado = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cbxJuzgado = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REPORTE PROCURADORES");
        setResizable(false);

        jLabel1.setText("Procurador");

        jLabel2.setText("Garantía");

        jLabel3.setText("Status");

        jLabel4.setText("Estado");

        jLabel5.setText("Juzgado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProcurador, 0, 298, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxJuzgado, javax.swing.GroupLayout.Alignment.TRAILING, 0, 298, Short.MAX_VALUE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.Alignment.TRAILING, 0, 298, Short.MAX_VALUE)
                            .addComponent(cbxStatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, 298, Short.MAX_VALUE)
                            .addComponent(cbxGarantia, 0, 298, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxJuzgado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Fechas", jPanel1);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.procurador = "";
        this.garantia = "";
        this.status = "";
        this.estado = "";
        this.juzgado = "";
        
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed
    
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        this.procurador = this.cbxProcurador.getSelectedItem().toString();
        this.garantia = this.cbxGarantia.getSelectedItem().toString();
        this.status = this.cbxStatus.getSelectedItem().toString();
        this.estado = this.cbxEstado.getSelectedItem().toString();
        this.juzgado = this.cbxJuzgado.getSelectedItem().toString();
        
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed
    
    public String dar_procurador() {
        return procurador;
    }
    
    public String dar_garantia() {
        return garantia;
    }
    
    public String dar_status() {
        return status;
    }
    
    public String dar_estado() {
        return estado;
    }
    
    public String dar_juzgado() {
        return juzgado;
    }
    
    public void Esconder_filtros() {
        this.jLabel2.setVisible(false);
        this.jLabel3.setVisible(false);
        this.jLabel4.setVisible(false);
        this.jLabel5.setVisible(false);
        
        this.cbxGarantia.setVisible(false);
        this.cbxStatus.setVisible(false);
        this.cbxEstado.setVisible(false);
        this.cbxJuzgado.setVisible(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JComboBox cbxGarantia;
    private javax.swing.JComboBox cbxJuzgado;
    private javax.swing.JComboBox cbxProcurador;
    private javax.swing.JComboBox cbxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
