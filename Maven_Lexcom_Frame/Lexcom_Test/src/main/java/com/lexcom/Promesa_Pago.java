package com.lexcom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Promesa_Pago extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    Integer deudor;
    
    public Promesa_Pago(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion, Integer deudor) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        this.deudor = deudor;
        initComponents();
        this.dccFechaIngreso.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areObservacion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        cbxEstadoPromesa = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        dccFechaPago = new datechooser.beans.DateChooserCombo();
        dccFechaIngreso = new datechooser.beans.DateChooserCombo();
        spnHora = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        spnMinuto = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        btnExpediente = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RECORDATORIO");
        setResizable(false);

        jLabel2.setText("Fecha Ingreso");

        jLabel3.setText("Observación");

        areObservacion.setColumns(20);
        areObservacion.setRows(5);
        jScrollPane1.setViewportView(areObservacion);

        jLabel4.setText("Estado Promesa");

        cbxEstadoPromesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO LEIDO", "LEIDO" }));

        jLabel1.setText("Alerta");

        dccFechaPago.setNothingAllowed(false);
        dccFechaPago.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dccFechaIngreso.setNothingAllowed(false);
        dccFechaIngreso.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        spnHora.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));

        jLabel5.setText("Hora");

        spnMinuto.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        jLabel6.setText("Minuto");

        btnExpediente.setText("Expediente");
        btnExpediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpedienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spnMinuto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dccFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dccFechaPago, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addComponent(spnHora))
                            .addComponent(cbxEstadoPromesa, javax.swing.GroupLayout.Alignment.TRAILING, 0, 350, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(btnExpediente, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(dccFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstadoPromesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExpediente)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
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

    private void btnExpedienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpedienteActionPerformed
        Expediente a = new Expediente(conn, usuario);
        a.addWindowListener(new WindowListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                setAlwaysOnTop(true);
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
        a.cargar(this.deudor);
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
        a.setAlwaysOnTop(true);
        this.dispose();
    }//GEN-LAST:event_btnExpedienteActionPerformed

    private void insertar() {
        com.lexcom.driver.Promesa_Pago drive = new com.lexcom.driver.Promesa_Pago(this.conn, this.usuario);
        
        String resultado = drive.insertar(
                this.deudor,
                this.dccFechaIngreso.getSelectedDate(),
                this.dccFechaPago.getSelectedDate(),
                Integer.parseInt(this.spnHora.getValue().toString()),
                Integer.parseInt(this.spnMinuto.getValue().toString()),
                this.cbxEstadoPromesa.getSelectedItem().toString(),
                this.areObservacion.getText(),
                0.00);
        
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        com.lexcom.driver.Promesa_Pago drive = new com.lexcom.driver.Promesa_Pago(this.conn, this.usuario);
        
        String resultado = drive.modificar(
                this.seleccion,
                this.deudor, 
                this.dccFechaIngreso.getSelectedDate(),
                this.dccFechaPago.getSelectedDate(),
                Integer.parseInt(this.spnHora.getValue().toString()),
                Integer.parseInt(this.spnMinuto.getValue().toString()),
                this.cbxEstadoPromesa.getSelectedItem().toString(),
                this.areObservacion.getText(),
                0.00);
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Promesa_Pago drive = new com.lexcom.driver.Promesa_Pago(this.conn, this.usuario);
        com.lexcom.driver.Promesa_Pago resultado = drive.obtener(seleccion);
        
        this.dccFechaIngreso.setSelectedDate(resultado.fecha_ingreso);
        this.dccFechaPago.setSelectedDate(resultado.fecha_pago);
        this.spnHora.setValue(resultado.hora_pago);
        this.spnMinuto.setValue(resultado.minuto_pago);
        this.cbxEstadoPromesa.setSelectedItem(resultado.estado_promesa);
        this.areObservacion.setText(resultado.observacion);
        
        if(accion == 2) {
            this.dccFechaIngreso.setEnabled(false);
            this.dccFechaPago.setEnabled(false);
            this.spnHora.setEnabled(false);
            this.spnMinuto.setEnabled(false);
            this.cbxEstadoPromesa.setEnabled(false);
            this.areObservacion.setEditable(false);
            this.btnAceptar.setVisible(false);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areObservacion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExpediente;
    private javax.swing.JComboBox cbxEstadoPromesa;
    private datechooser.beans.DateChooserCombo dccFechaIngreso;
    private datechooser.beans.DateChooserCombo dccFechaPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spnHora;
    private javax.swing.JSpinner spnMinuto;
    // End of variables declaration//GEN-END:variables
}
