package com.lexcom;

import java.sql.Connection;
import javax.swing.JOptionPane;

public class Usuario extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    
    public Usuario(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        initComponents();
        if(accion == 2) {
            this.txtNombreCompleto.setEditable(false);
            this.txtUsuario.setEditable(false);
            this.txtpContrasena.setEditable(false);
            this.areDescripcion.setEnabled(false);
            this.RioAsistenteNO.setEnabled(false);
            this.RioAsistenteSI.setEnabled(false);
            this.RioDigitadorNO.setEnabled(false);
            this.RioDigitadorSI.setEnabled(false);
            this.RioGestorNO.setEnabled(false);
            this.RioGestorSI.setEnabled(false);
            this.RioInvestigadorNO.setEnabled(false);
            this.RioInvestigadorSI.setEnabled(false);
            this.RioProcuradorNO.setEnabled(false);
            this.RioProcuradorSI.setEnabled(false);
            this.btnAceptar.setVisible(false);
            this.cbxTipoGestor.setEnabled(false);
        }
        this.txtNombreCompleto.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreCompleto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtpContrasena = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areDescripcion = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        RioGestorSI = new javax.swing.JRadioButton();
        RioGestorNO = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        RioProcuradorSI = new javax.swing.JRadioButton();
        RioProcuradorNO = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        RioAsistenteSI = new javax.swing.JRadioButton();
        RioAsistenteNO = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        RioDigitadorSI = new javax.swing.JRadioButton();
        RioDigitadorNO = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        RioInvestigadorSI = new javax.swing.JRadioButton();
        RioInvestigadorNO = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        cbxTipoGestor = new javax.swing.JComboBox();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("USUARIO");
        setResizable(false);

        jLabel1.setText("Nombre completo");

        jLabel2.setText("Usuario");

        jLabel3.setText("Contraseña");

        jLabel4.setText("Descripción");

        areDescripcion.setColumns(20);
        areDescripcion.setRows(5);
        jScrollPane1.setViewportView(areDescripcion);

        jLabel5.setText("Gestor");

        buttonGroup1.add(RioGestorSI);
        RioGestorSI.setSelected(true);
        RioGestorSI.setText("SI");

        buttonGroup1.add(RioGestorNO);
        RioGestorNO.setText("NO");

        jLabel6.setText("Procurador");

        buttonGroup2.add(RioProcuradorSI);
        RioProcuradorSI.setSelected(true);
        RioProcuradorSI.setText("SI");

        buttonGroup2.add(RioProcuradorNO);
        RioProcuradorNO.setText("NO");

        jLabel7.setText("Asistente");

        buttonGroup3.add(RioAsistenteSI);
        RioAsistenteSI.setSelected(true);
        RioAsistenteSI.setText("SI");

        buttonGroup3.add(RioAsistenteNO);
        RioAsistenteNO.setText("NO");

        jLabel8.setText("Digitador");

        buttonGroup4.add(RioDigitadorSI);
        RioDigitadorSI.setSelected(true);
        RioDigitadorSI.setText("SI");

        buttonGroup4.add(RioDigitadorNO);
        RioDigitadorNO.setText("NO");

        jLabel9.setText("Investigador");

        buttonGroup5.add(RioInvestigadorSI);
        RioInvestigadorSI.setSelected(true);
        RioInvestigadorSI.setText("SI");

        buttonGroup5.add(RioInvestigadorNO);
        RioInvestigadorNO.setText("NO");

        jLabel10.setText("Tipo de gestor");

        cbxTipoGestor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO APLICA", "CREDIFACIL", "ORO", "BRONCE", "PLATA" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addComponent(txtpContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(RioGestorSI)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RioGestorNO)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(8, 8, 8)
                                        .addComponent(RioDigitadorSI)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RioDigitadorNO)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(RioInvestigadorSI))
                                    .addComponent(RioProcuradorSI))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RioInvestigadorNO)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(RioProcuradorNO)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(RioAsistenteSI)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RioAsistenteNO)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel10)
                    .addComponent(cbxTipoGestor, 0, 433, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(RioGestorSI)
                    .addComponent(RioGestorNO)
                    .addComponent(jLabel6)
                    .addComponent(RioProcuradorSI)
                    .addComponent(RioProcuradorNO)
                    .addComponent(jLabel7)
                    .addComponent(RioAsistenteSI)
                    .addComponent(RioAsistenteNO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(RioDigitadorSI)
                    .addComponent(RioDigitadorNO)
                    .addComponent(jLabel9)
                    .addComponent(RioInvestigadorSI)
                    .addComponent(RioInvestigadorNO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTipoGestor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
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
        String gestor = "";
        if(this.RioGestorSI.isSelected()) { gestor = "SI"; } 
        else { gestor = "NO"; }
        String procurador = "";
        if(this.RioProcuradorSI.isSelected()) { procurador = "SI"; }
        else { procurador = "NO"; }
        String asistente = "";
        if(this.RioAsistenteSI.isSelected()) { asistente = "SI"; } 
        else { asistente = "NO"; }
        String digitador = "";
        if(this.RioDigitadorSI.isSelected()) { digitador = "SI"; } 
        else { digitador = "NO"; }
        String investigador = "";
        if(this.RioInvestigadorSI.isSelected()) { investigador = "SI"; } 
        else { investigador = "NO"; }
        
        com.lexcom.driver.Usuario drive = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        String resultado = drive.insertar(this.txtNombreCompleto.getText(), this.txtUsuario.getText(), this.txtpContrasena.getText(), "VIGENTE", this.areDescripcion.getText(), gestor, procurador, asistente, digitador, investigador, this.cbxTipoGestor.getSelectedItem().toString());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        String gestor = "";
        if(this.RioGestorSI.isSelected()) { gestor = "SI"; } 
        else { gestor = "NO"; }
        String procurador = "";
        if(this.RioProcuradorSI.isSelected()) { procurador = "SI"; }
        else { procurador = "NO"; }
        String asistente = "";
        if(this.RioAsistenteSI.isSelected()) { asistente = "SI"; } 
        else { asistente = "NO"; }
        String digitador = "";
        if(this.RioDigitadorSI.isSelected()) { digitador = "SI"; } 
        else { digitador = "NO"; }
        String investigador = "";
        if(this.RioInvestigadorSI.isSelected()) { investigador = "SI"; } 
        else { investigador = "NO"; }
        
        com.lexcom.driver.Usuario drive = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        String resultado = drive.modificar(this.txtNombreCompleto.getText(), this.txtUsuario.getText(), this.txtpContrasena.getText(), this.seleccion, this.areDescripcion.getText(), gestor, procurador, asistente, digitador, investigador, this.cbxTipoGestor.getSelectedItem().toString());
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Usuario drive = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Usuario resultado = drive.obtener(seleccion);
        
        this.txtNombreCompleto.setText(resultado.nombre_completo);
        this.txtUsuario.setText(resultado.nombre);
        this.txtpContrasena.setText(resultado.contrasena);
        this.areDescripcion.setText(resultado.descripcion);
        
        if(resultado.gestor.equals("SI")) { this.RioGestorSI.setSelected(true); this.RioGestorNO.setSelected(false); }
        else { this.RioGestorSI.setSelected(false); this.RioGestorNO.setSelected(true); }
        
        if(resultado.procurador.equals("SI")) { this.RioProcuradorSI.setSelected(true); this.RioProcuradorNO.setSelected(false); } 
        else { this.RioProcuradorSI.setSelected(false); this.RioProcuradorNO.setSelected(true); }
        
        if(resultado.asistente.equals("SI")) { this.RioAsistenteSI.setSelected(true); this.RioAsistenteNO.setSelected(false); }
        else { this.RioAsistenteSI.setSelected(false); this.RioAsistenteNO.setSelected(true); }
        
        if(resultado.digitador.equals("SI")) { this.RioDigitadorSI.setSelected(true); this.RioDigitadorNO.setSelected(false); } 
        else { this.RioDigitadorSI.setSelected(false); this.RioDigitadorNO.setSelected(true); }
        
        if(resultado.investigador.equals("SI")) { this.RioInvestigadorSI.setSelected(true); this.RioInvestigadorNO.setSelected(false); } 
        else { this.RioInvestigadorSI.setSelected(false); this.RioInvestigadorNO.setSelected(true); }
        
        this.cbxTipoGestor.setSelectedItem(resultado.tipo_usuario);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RioAsistenteNO;
    private javax.swing.JRadioButton RioAsistenteSI;
    private javax.swing.JRadioButton RioDigitadorNO;
    private javax.swing.JRadioButton RioDigitadorSI;
    private javax.swing.JRadioButton RioGestorNO;
    private javax.swing.JRadioButton RioGestorSI;
    private javax.swing.JRadioButton RioInvestigadorNO;
    private javax.swing.JRadioButton RioInvestigadorSI;
    private javax.swing.JRadioButton RioProcuradorNO;
    private javax.swing.JRadioButton RioProcuradorSI;
    private javax.swing.JTextArea areDescripcion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JComboBox cbxTipoGestor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JPasswordField txtpContrasena;
    // End of variables declaration//GEN-END:variables
}
