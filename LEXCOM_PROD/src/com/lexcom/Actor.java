package com.lexcom;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class Actor extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    
    public Actor(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        initComponents();
        
        com.lexcom.driver.Cooperacion DCooperacion = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
        this.cbxCooperacion.setModel(DCooperacion.dar_lista());
        
        if(accion == 2) {
            this.txtNombre.setEditable(false);
            this.txtNit.setEditable(false);
            this.txtTelefono.setEditable(false);
            this.areDescripcion.setEnabled(false);
            this.cbxCooperacion.setEnabled(false);
            this.btnAceptar.setVisible(false);
            this.txtDigitalizados.setEditable(false);
        }
        this.txtNombre.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areDescripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbxCooperacion = new javax.swing.JComboBox();
        txtDigitalizados = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ACTOR");
        setResizable(false);

        jLabel2.setText("Nombre");

        jLabel3.setText("Descripción");

        areDescripcion.setColumns(20);
        areDescripcion.setRows(5);
        jScrollPane1.setViewportView(areDescripcion);

        jLabel4.setText("Nit");

        jLabel5.setText("Teléfono");

        jLabel1.setText("Cooperación");

        jLabel6.setText("Digitalizados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txtTelefono)
                            .addComponent(txtNombre)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDigitalizados, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(cbxCooperacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(328, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cbxCooperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDigitalizados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
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
        com.lexcom.driver.Actor drive = new com.lexcom.driver.Actor(this.conn, this.usuario);
        com.lexcom.driver.Cooperacion DCooperacion = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
        
        String resultado = drive.insertar(
                this.txtNombre.getText(), 
                this.txtNit.getText(), 
                this.txtTelefono.getText(), 
                "VIGENTE", 
                this.areDescripcion.getText(),
                DCooperacion.obtener_indice(this.cbxCooperacion.getSelectedItem().toString()),
                this.txtDigitalizados.getText());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        com.lexcom.driver.Actor drive = new com.lexcom.driver.Actor(this.conn, this.usuario);
        com.lexcom.driver.Cooperacion DCooperacion = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
        
        String resultado = drive.modificar(
                this.txtNombre.getText(), 
                this.txtNit.getText(), 
                this.txtTelefono.getText(), 
                this.seleccion, 
                this.areDescripcion.getText(),
                DCooperacion.obtener_indice(this.cbxCooperacion.getSelectedItem().toString()),
                this.txtDigitalizados.getText());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Actor drive = new com.lexcom.driver.Actor(this.conn, this.usuario);
        com.lexcom.driver.Actor resultado = drive.obtener(seleccion);
        
        com.lexcom.driver.Cooperacion DCooperacion = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
        
        this.txtNombre.setText(resultado.nombre);
        this.txtNit.setText(resultado.nit);
        this.txtTelefono.setText(resultado.telefono);
        this.areDescripcion.setText(resultado.descripcion);
        this.cbxCooperacion.setSelectedItem(DCooperacion.obtener_nombre(resultado.cooperacion));
        this.txtDigitalizados.setText(resultado.digitalizados);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areDescripcion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxCooperacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtDigitalizados;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}
