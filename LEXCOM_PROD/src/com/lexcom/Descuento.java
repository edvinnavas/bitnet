package com.lexcom;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class Descuento extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    Integer deudor;
    
    public Descuento(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion, Integer deudor) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        this.deudor = deudor;
        initComponents();
        
        com.lexcom.driver.Tipo_Descuento DTipo_Descuento = new com.lexcom.driver.Tipo_Descuento(this.conn, this.usuario);
        this.cbxTipoDescuento.setModel(DTipo_Descuento.dar_lista());
        
        if(accion == 2) {
            this.cbxTipoDescuento.setEnabled(false);
            this.dccFecha.setEnabled(false);
            this.spnMonto.setEnabled(false);
            this.areDescripcion.setEnabled(false);
            this.btnAceptar.setVisible(false);
        }
        this.cbxTipoDescuento.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areDescripcion = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        spnMonto = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        dccFecha = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        cbxTipoDescuento = new javax.swing.JComboBox();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DESCUENTO");
        setResizable(false);

        jLabel3.setText("Descripción");

        areDescripcion.setColumns(20);
        areDescripcion.setRows(5);
        jScrollPane1.setViewportView(areDescripcion);

        jLabel5.setText("Monto");

        spnMonto.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));

        jLabel1.setText("Fecha");

        dccFecha.setNothingAllowed(false);
        dccFecha.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel2.setText("Tipo Descuento");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cbxTipoDescuento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(63, 63, 63)
                        .addComponent(dccFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(62, 62, 62)
                        .addComponent(spnMonto)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxTipoDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spnMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Generales", jPanel1);

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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

    private void insertar() {
        com.lexcom.driver.Descuento drive = new com.lexcom.driver.Descuento(this.conn, this.usuario);
        com.lexcom.driver.Tipo_Descuento DTipo_Descuento = new com.lexcom.driver.Tipo_Descuento(this.conn, this.usuario);
        
        String resultado = drive.insertar(
                this.deudor, 
                DTipo_Descuento.obtener_indice(this.cbxTipoDescuento.getSelectedItem().toString()), 
                this.dccFecha.getSelectedDate(), 
                Double.parseDouble(this.spnMonto.getValue().toString()), 
                this.areDescripcion.getText());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        com.lexcom.driver.Descuento drive = new com.lexcom.driver.Descuento(this.conn, this.usuario);
        com.lexcom.driver.Tipo_Descuento DTipo_Descuento = new com.lexcom.driver.Tipo_Descuento(this.conn, this.usuario);
        
        String resultado = drive.modificar(
                this.seleccion, 
                this.deudor, 
                DTipo_Descuento.obtener_indice(this.cbxTipoDescuento.getSelectedItem().toString()), 
                this.dccFecha.getSelectedDate(), 
                Double.parseDouble(this.spnMonto.getValue().toString()), 
                this.areDescripcion.getText());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Descuento drive = new com.lexcom.driver.Descuento(this.conn, this.usuario);
        com.lexcom.driver.Descuento resultado = drive.obtener(seleccion);
        com.lexcom.driver.Tipo_Descuento DTipo_Descuento = new com.lexcom.driver.Tipo_Descuento(this.conn, this.usuario);
        
        this.cbxTipoDescuento.setSelectedItem(DTipo_Descuento.obtener_nombre(resultado.tipo_descuento));
        this.dccFecha.setSelectedDate(resultado.fecha);
        this.spnMonto.setValue(resultado.monto);
        this.areDescripcion.setText(resultado.descripcion);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areDescripcion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxTipoDescuento;
    private datechooser.beans.DateChooserCombo dccFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spnMonto;
    // End of variables declaration//GEN-END:variables
}
