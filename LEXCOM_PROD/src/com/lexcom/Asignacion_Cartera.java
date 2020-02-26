package com.lexcom;

import java.sql.Connection;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Asignacion_Cartera extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    
    public Asignacion_Cartera(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        initComponents();
        
        com.lexcom.driver.Asignacion_Cartera drive = new com.lexcom.driver.Asignacion_Cartera(this.conn, this.usuario);
        this.lstCodigoContactabilidad.setModel(drive.cargar_codigo_contactabilidad());
        
        if(accion == 2) {
            this.lstCodigoContactabilidadAsignadas.setEnabled(false);
        }
        this.dccFecha.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JTabbaedPanel1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnAgregarCodigoContactabilidad = new javax.swing.JButton();
        btnQuitarCodigoContactabilidad = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dccFecha = new datechooser.beans.DateChooserCombo();
        dccPromesaPago = new datechooser.beans.DateChooserCombo();
        dccCasoFecha = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstCodigoContactabilidad = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstCodigoContactabilidadAsignadas = new javax.swing.JList();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ASIGANCIÓN CARTERA");
        setResizable(false);

        btnAgregarCodigoContactabilidad.setText("Agregar");
        btnAgregarCodigoContactabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCodigoContactabilidadActionPerformed(evt);
            }
        });

        btnQuitarCodigoContactabilidad.setText("Quitar");
        btnQuitarCodigoContactabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarCodigoContactabilidadActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha");

        dccFecha.setNothingAllowed(false);
        dccFecha.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dccPromesaPago.setNothingAllowed(false);
        dccPromesaPago.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dccCasoFecha.setNothingAllowed(false);
        dccCasoFecha.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel2.setText("Fecha PP");

        jLabel3.setText("Fecha G. Antigua");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(72, 72, 72)
                        .addComponent(dccFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dccPromesaPago, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                            .addComponent(dccCasoFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(dccFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(dccPromesaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(dccCasoFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lstCodigoContactabilidad.setBorder(javax.swing.BorderFactory.createTitledBorder("Cod. Result."));
        lstCodigoContactabilidad.setModel(new DefaultListModel<>());
        jScrollPane1.setViewportView(lstCodigoContactabilidad);

        lstCodigoContactabilidadAsignadas.setBorder(javax.swing.BorderFactory.createTitledBorder("Cod. Result Asig."));
        lstCodigoContactabilidadAsignadas.setModel(new DefaultListModel<>());
        jScrollPane2.setViewportView(lstCodigoContactabilidadAsignadas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(btnQuitarCodigoContactabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarCodigoContactabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitarCodigoContactabilidad)
                    .addComponent(btnAgregarCodigoContactabilidad))
                .addContainerGap())
        );

        JTabbaedPanel1.addTab("Datos Generales", jPanel1);

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
                .addContainerGap(337, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JTabbaedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JTabbaedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
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

    private void btnAgregarCodigoContactabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCodigoContactabilidadActionPerformed
        try {
            DefaultListModel templstCodigoContactabilidadAsignadas = (DefaultListModel) this.lstCodigoContactabilidadAsignadas.getModel();
            templstCodigoContactabilidadAsignadas.addElement(this.lstCodigoContactabilidad.getSelectedValue().toString());
            this.lstCodigoContactabilidadAsignadas.setModel(templstCodigoContactabilidadAsignadas);

            DefaultListModel templstCodigoContactabilidad = (DefaultListModel) this.lstCodigoContactabilidad.getModel();
            templstCodigoContactabilidad.removeElement(this.lstCodigoContactabilidad.getSelectedValue().toString());
            this.lstCodigoContactabilidad.setModel(templstCodigoContactabilidad);
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_btnAgregarCodigoContactabilidadActionPerformed

    private void btnQuitarCodigoContactabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarCodigoContactabilidadActionPerformed
        try {
            DefaultListModel templstCodigoContactabilidad = (DefaultListModel) this.lstCodigoContactabilidad.getModel();
            templstCodigoContactabilidad.addElement(this.lstCodigoContactabilidadAsignadas.getSelectedValue().toString());
            this.lstCodigoContactabilidad.setModel(templstCodigoContactabilidad);

            DefaultListModel templstCodigoContactabilidadAsignada = (DefaultListModel) this.lstCodigoContactabilidadAsignadas.getModel();
            templstCodigoContactabilidadAsignada.removeElement(this.lstCodigoContactabilidadAsignadas.getSelectedValue().toString());
            this.lstCodigoContactabilidadAsignadas.setModel(templstCodigoContactabilidadAsignada);
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_btnQuitarCodigoContactabilidadActionPerformed

    private void insertar() {
        com.lexcom.driver.Asignacion_Cartera drive = new com.lexcom.driver.Asignacion_Cartera(this.conn, this.usuario);
        
        String resultado = drive.insertar(
                this.dccFecha.getSelectedDate(),
                this.dccPromesaPago.getSelectedDate(),
                this.dccCasoFecha.getSelectedDate(),
                (DefaultListModel) this.lstCodigoContactabilidadAsignadas.getModel());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        com.lexcom.driver.Asignacion_Cartera drive = new com.lexcom.driver.Asignacion_Cartera(this.conn, this.usuario);
        
        String resultado = drive.modificar(
                this.seleccion,
                this.dccFecha.getSelectedDate(),
                this.dccPromesaPago.getSelectedDate(),
                this.dccCasoFecha.getSelectedDate(),
                (DefaultListModel) this.lstCodigoContactabilidadAsignadas.getModel());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Asignacion_Cartera drive = new com.lexcom.driver.Asignacion_Cartera(this.conn, this.usuario);
        com.lexcom.driver.Asignacion_Cartera resultado = drive.obtener(seleccion,(DefaultListModel) this.lstCodigoContactabilidadAsignadas.getModel());
        
        this.dccFecha.setSelectedDate(resultado.fecha);
        this.dccPromesaPago.setSelectedDate(resultado.fecha_promesa_pago);
        this.dccCasoFecha.setSelectedDate(resultado.fecha_casos_por_fecha);
        this.lstCodigoContactabilidadAsignadas.setModel(resultado.lstCodigoContactabilidadAsignadas);
        for(Integer i=0;i < resultado.lstCodigoContactabilidadAsignadas.getSize();i++) {
            DefaultListModel templstCodigoContactabilidad = (DefaultListModel) this.lstCodigoContactabilidad.getModel();
            templstCodigoContactabilidad.removeElement(resultado.lstCodigoContactabilidadAsignadas.getElementAt(i).toString());
            this.lstCodigoContactabilidad.setModel(templstCodigoContactabilidad);
        }
        if(this.accion == 2) {
            this.dccFecha.setEnabled(false);
            this.dccCasoFecha.setEnabled(false);
            this.dccPromesaPago.setEnabled(false);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane JTabbaedPanel1;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarCodigoContactabilidad;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnQuitarCodigoContactabilidad;
    private datechooser.beans.DateChooserCombo dccCasoFecha;
    private datechooser.beans.DateChooserCombo dccFecha;
    private datechooser.beans.DateChooserCombo dccPromesaPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstCodigoContactabilidad;
    private javax.swing.JList lstCodigoContactabilidadAsignadas;
    // End of variables declaration//GEN-END:variables
}
