package com.lexcom;

import java.sql.Connection;
import java.util.Calendar;

public class FiltroPeriodoIncialFinal extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    String periodo1;
    String periodo2;
    String corporacion;
    
    public FiltroPeriodoIncialFinal(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
        
        com.lexcom.driver.Cooperacion DCorporacion = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
        this.cbxCorporacion .setModel(DCorporacion.dar_lista());
        this.cbxCorporacion.addItem("TODOS");
        this.cbxCorporacion.setSelectedItem("TODOS");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dccPeriodoIncial1 = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        dccPeriodoFinal1 = new datechooser.beans.DateChooserCombo();
        dccPeriodoIncial2 = new datechooser.beans.DateChooserCombo();
        dccPeriodoFinal2 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        cbxCorporacion = new javax.swing.JComboBox();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REPORTE CONSEJO");
        setResizable(false);

        jLabel1.setText("Periodo Anterior");

        dccPeriodoIncial1.setNothingAllowed(false);
        dccPeriodoIncial1.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel2.setText("Periodo Actual");

        dccPeriodoFinal1.setNothingAllowed(false);
        dccPeriodoFinal1.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dccPeriodoIncial2.setNothingAllowed(false);
        dccPeriodoIncial2.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dccPeriodoFinal2.setNothingAllowed(false);
        dccPeriodoFinal2.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel3.setText("Corporación");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxCorporacion, 0, 418, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dccPeriodoFinal1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dccPeriodoFinal2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dccPeriodoIncial1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dccPeriodoIncial2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dccPeriodoIncial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dccPeriodoIncial2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccPeriodoFinal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dccPeriodoFinal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCorporacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.periodo1 = "";
        this.periodo2 = "";
        this.corporacion = "";
        
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        Calendar cfecha1 = this.dccPeriodoIncial1.getSelectedDate();
        Calendar cfecha2 = this.dccPeriodoIncial2.getSelectedDate();
        
        Calendar cfecha3 = this.dccPeriodoFinal1.getSelectedDate();
        Calendar cfecha4 = this.dccPeriodoFinal2.getSelectedDate();
        
        Integer dia = cfecha1.get(Calendar.DATE);
        Integer mes = cfecha1.get(Calendar.MONTH) + 1;
        Integer ano = cfecha1.get(Calendar.YEAR);
        this.periodo1 = "'" + ano.toString() + "/" + mes.toString() + "/" + dia.toString() + "'" + " AND ";
        dia = cfecha2.get(Calendar.DATE);
        mes = cfecha2.get(Calendar.MONTH) + 1;
        ano = cfecha2.get(Calendar.YEAR);
        this.periodo1 = periodo1 + "'" + ano.toString() + "/" + mes.toString() + "/" + dia.toString() + "'";
        
        dia = cfecha3.get(Calendar.DATE);
        mes = cfecha3.get(Calendar.MONTH) + 1;
        ano = cfecha3.get(Calendar.YEAR);
        this.periodo2 = "'" + ano.toString() + "/" + mes.toString() + "/" + dia.toString() + "'" + " AND ";
        dia = cfecha4.get(Calendar.DATE);
        mes = cfecha4.get(Calendar.MONTH) + 1;
        ano = cfecha4.get(Calendar.YEAR);
        this.periodo2 = periodo2 + "'" + ano.toString() + "/" + mes.toString() + "/" + dia.toString() + "'";
        
        this.corporacion = this.cbxCorporacion.getSelectedItem().toString();
        
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed
    
    public String dar_periodo1() {
        return periodo1;
    }
    
    public String dar_periodo2() {
        return periodo2;
    }
    
    public String dar_corporacion() {
        return corporacion;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxCorporacion;
    private datechooser.beans.DateChooserCombo dccPeriodoFinal1;
    private datechooser.beans.DateChooserCombo dccPeriodoFinal2;
    private datechooser.beans.DateChooserCombo dccPeriodoIncial1;
    private datechooser.beans.DateChooserCombo dccPeriodoIncial2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
