package com.lexcom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Permisos_Usuario extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    
    public Permisos_Usuario(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
        
        com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
        lstUsuario.setModel(drive.cargar_usuarios());
        lstUsuarioUno.setModel(drive.cargar_usuarios());
        lstMenu.setModel(drive.cargar_menu());
        lstMenuUno.setModel(drive.cargar_menu_uno());
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
        jScrollPane1 = new javax.swing.JScrollPane();
        lstUsuario = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMenu = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstUsuarioUno = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstMenuUno = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jRadioButton19 = new javax.swing.JRadioButton();
        jRadioButton20 = new javax.swing.JRadioButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PERMISOS DE USUARIO");
        setResizable(false);

        lstUsuario.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstUsuarioValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstUsuario);

        lstMenu.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstMenuValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstMenu);

        jLabel1.setText("Usuario");

        jLabel2.setText("Menu");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("NUEVO"));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Si");
        jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton1ItemStateChanged(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("No");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("MODIFICAR"));

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setText("Si");
        jRadioButton3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton3ItemStateChanged(evt);
            }
        });

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setText("No");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("ELIMINAR"));

        buttonGroup3.add(jRadioButton5);
        jRadioButton5.setText("Si");
        jRadioButton5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton5ItemStateChanged(evt);
            }
        });

        buttonGroup3.add(jRadioButton6);
        jRadioButton6.setText("No");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton6))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("ACTIVAR"));

        buttonGroup4.add(jRadioButton7);
        jRadioButton7.setText("Si");
        jRadioButton7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton7ItemStateChanged(evt);
            }
        });

        buttonGroup4.add(jRadioButton8);
        jRadioButton8.setText("No");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton8))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton7)
                    .addComponent(jRadioButton8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("VER"));

        buttonGroup5.add(jRadioButton9);
        jRadioButton9.setText("Si");
        jRadioButton9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton9ItemStateChanged(evt);
            }
        });

        buttonGroup5.add(jRadioButton10);
        jRadioButton10.setText("No");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton9)
                    .addComponent(jRadioButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Permisos Entidades", jPanel1);

        lstUsuarioUno.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstUsuarioUnoValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(lstUsuarioUno);

        lstMenuUno.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstMenuUnoValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(lstMenuUno);

        jLabel3.setText("Usuario");

        jLabel4.setText("Menu");

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("VER"));

        buttonGroup5.add(jRadioButton19);
        jRadioButton19.setText("Si");
        jRadioButton19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton19ItemStateChanged(evt);
            }
        });

        buttonGroup5.add(jRadioButton20);
        jRadioButton20.setText("No");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton20))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton19)
                    .addComponent(jRadioButton20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Permisos Listas", jPanel7);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jRadioButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton1ItemStateChanged
        String cadenasql = "";
        Statement stmt = null;
        
        if(lstUsuario.getSelectedValue() != null && lstMenu.getSelectedValue() != null) {
            com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
            if(jRadioButton1.isSelected()) {
                cadenasql = "UPDATE permiso_usuario SET nuevo='SI' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            } else {
                cadenasql = "UPDATE permiso_usuario SET nuevo='NO' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            }
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                com.lexcom.driver.Evento DEvento = new com.lexcom.driver.Evento (this.conn);
                if(jRadioButton1.isSelected()) {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Nuevo=SI", 1);
                } else {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Nuevo=NO", 1);
                }
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
}//GEN-LAST:event_jRadioButton1ItemStateChanged

    private void jRadioButton3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton3ItemStateChanged
        String cadenasql = "";
        Statement stmt = null;
        
        if( lstUsuario.getSelectedValue() != null && lstMenu.getSelectedValue() != null ) {
            com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
            if(jRadioButton3.isSelected()) {
                cadenasql = "UPDATE permiso_usuario SET modificar='SI' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            } else {
                cadenasql = "UPDATE permiso_usuario SET modificar='NO' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            }
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                com.lexcom.driver.Evento DEvento = new com.lexcom.driver.Evento (this.conn);
                if(jRadioButton1.isSelected()) {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Modificado=SI", 1);
                } else {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Modificado=NO", 1);
                }
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
}//GEN-LAST:event_jRadioButton3ItemStateChanged

    private void jRadioButton5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton5ItemStateChanged
        String cadenasql = "";
        Statement stmt = null;
        
        if( lstUsuario.getSelectedValue() != null && lstMenu.getSelectedValue() != null ) {
            com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
            if(jRadioButton5.isSelected()) {
                cadenasql = "UPDATE permiso_usuario SET eliminar='SI' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            } else {
                cadenasql = "UPDATE permiso_usuario SET eliminar='NO' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            }
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                com.lexcom.driver.Evento DEvento = new com.lexcom.driver.Evento (this.conn);
                if(jRadioButton1.isSelected()) {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Eliminar=SI", 1);
                } else {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Eliminar=NO", 1);
                }
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
}//GEN-LAST:event_jRadioButton5ItemStateChanged

    private void jRadioButton7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton7ItemStateChanged
        String cadenasql = "";
        Statement stmt = null;
        
        if( lstUsuario.getSelectedValue() != null && lstMenu.getSelectedValue() != null ) {
            com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
            if(jRadioButton7.isSelected()) {
                cadenasql = "UPDATE permiso_usuario SET activar='SI' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            } else {
                cadenasql = "UPDATE permiso_usuario SET activar='NO' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            }
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                com.lexcom.driver.Evento DEvento = new com.lexcom.driver.Evento (this.conn);
                if(jRadioButton1.isSelected()) {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Activar=SI",1 );
                } else {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Activar=NO", 1);
                }
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
}//GEN-LAST:event_jRadioButton7ItemStateChanged

    private void jRadioButton9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton9ItemStateChanged
        String cadenasql = "";
        Statement stmt = null;
        
        if( lstUsuario.getSelectedValue() != null && lstMenu.getSelectedValue() != null ) {
            com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
            if(jRadioButton9.isSelected()) {
                cadenasql = "UPDATE permiso_usuario SET ver='SI' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            } else {
                cadenasql = "UPDATE permiso_usuario SET ver='NO' WHERE usuario=" + drive.dar_usuario_index(lstUsuario.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenu.getSelectedValue().toString());
            }
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                com.lexcom.driver.Evento DEvento = new com.lexcom.driver.Evento (this.conn);
                if(jRadioButton1.isSelected()) {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Ver=SI", 1);
                } else {
                    DEvento.insertar(this.usuario, "Permiso de usuario modificado=> Usuario: " + lstUsuario.getSelectedValue().toString() + " Menu: " + lstMenu.getSelectedValue().toString() + " Ver=NO", 1);
                }
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
}//GEN-LAST:event_jRadioButton9ItemStateChanged

    private void lstUsuarioValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstUsuarioValueChanged
        if(evt.getValueIsAdjusting()) {
            buscar_permiso();
        }
    }//GEN-LAST:event_lstUsuarioValueChanged

    private void lstMenuValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstMenuValueChanged
        if(evt.getValueIsAdjusting()) {
            buscar_permiso();
        }
    }//GEN-LAST:event_lstMenuValueChanged

    private void lstUsuarioUnoValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstUsuarioUnoValueChanged
        if(evt.getValueIsAdjusting()) {
            buscar_permiso_uno();
        }
    }//GEN-LAST:event_lstUsuarioUnoValueChanged

    private void lstMenuUnoValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstMenuUnoValueChanged
        if(evt.getValueIsAdjusting()) {
            buscar_permiso_uno();
        }
    }//GEN-LAST:event_lstMenuUnoValueChanged

    private void jRadioButton19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton19ItemStateChanged
        String cadenasql = "";
        Statement stmt = null;
        
        if(lstUsuarioUno.getSelectedValue() != null && lstMenuUno.getSelectedValue() != null ) {
            com.lexcom.driver.Permisos_Usuario drive = new com.lexcom.driver.Permisos_Usuario(this.conn, this.usuario);
            if(jRadioButton19.isSelected()) {
                cadenasql = "UPDATE permiso_usuario_uno SET ver='SI' WHERE usuario=" + drive.dar_usuario_index(lstUsuarioUno.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenuUno.getSelectedValue().toString());
            } else {
                cadenasql = "UPDATE permiso_usuario_uno SET ver='NO' WHERE usuario=" + drive.dar_usuario_index(lstUsuarioUno.getSelectedValue().toString()) + " AND menu=" + drive.dar_menu_index(lstMenuUno.getSelectedValue().toString());
            }
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                
                com.lexcom.driver.Evento DEvento = new com.lexcom.driver.Evento (this.conn);
                if(jRadioButton19.isSelected()) {
                    DEvento.insertar(this.usuario, "Permiso de usuario Uno modificado=> Usuario: " + lstUsuarioUno.getSelectedValue().toString() + " Menu: " + lstMenuUno.getSelectedValue().toString() + " Ver=SI", 1);
                } else {
                    DEvento.insertar(this.usuario, "Permiso de usuario Uno modificado=> Usuario: " + lstUsuarioUno.getSelectedValue().toString() + " Menu: " + lstMenuUno.getSelectedValue().toString() + " Ver=NO", 1);
                }
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_jRadioButton19ItemStateChanged
    
    private void buscar_permiso() {
        if(lstUsuario.getSelectedValue() != null && lstMenu.getSelectedValue() != null ) {
            String cadenasql = "select a.nombre, b.nombre, c.nuevo, c.modificar, c.eliminar, c.activar, c.ver from usuario a, menu b, permiso_usuario c where a.usuario=c.usuario and b.menu=c.menu and a.nombre='" + lstUsuario.getSelectedValue().toString() + "'";
            try {
                Statement stmt = conn.createStatement();        
                ResultSet rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    if( rs.getString(2).equals(lstMenu.getSelectedValue().toString()) ) {
                        if(rs.getString(3).equals("SI")) {
                            jRadioButton1.setSelected(true);
                            jRadioButton2.setSelected(false);
                        }
                        else {
                            jRadioButton1.setSelected(false);
                            jRadioButton2.setSelected(true);
                        }
                        if(rs.getString(4).equals("SI")) {
                            jRadioButton3.setSelected(true);
                            jRadioButton4.setSelected(false);
                        }
                        else {
                            jRadioButton3.setSelected(false);
                            jRadioButton4.setSelected(true);
                        }
                        if(rs.getString(5).equals("SI")) {
                            jRadioButton5.setSelected(true);
                            jRadioButton6.setSelected(false);
                        }
                        else {
                            jRadioButton5.setSelected(false);
                            jRadioButton6.setSelected(true);
                        }
                        if(rs.getString(6).equals("SI")) {
                            jRadioButton7.setSelected(true);
                            jRadioButton8.setSelected(false);
                        }
                        else {
                            jRadioButton7.setSelected(false);
                            jRadioButton8.setSelected(true);
                        }
                        if(rs.getString(7).equals("SI")) {
                            jRadioButton9.setSelected(true);
                            jRadioButton10.setSelected(false);
                        }
                        else {
                            jRadioButton9.setSelected(false);
                            jRadioButton10.setSelected(true);
                        }
                    }
                }
                rs.close();
            }
            catch(Exception ex) {
                System.out.println(ex);
                jRadioButton1.setSelected(false);
                jRadioButton2.setSelected(false);
            }
        }
    }
    
    private void buscar_permiso_uno() {
        if(lstUsuarioUno.getSelectedValue() != null && lstMenuUno.getSelectedValue() != null ) {
            String cadenasql = "select a.nombre, b.nombre, c.ver from usuario a, menu b, permiso_usuario_uno c where a.usuario=c.usuario and b.menu=c.menu and a.nombre='" + lstUsuarioUno.getSelectedValue().toString() + "'";
            try {
                Statement stmt = conn.createStatement();        
                ResultSet rs = stmt.executeQuery(cadenasql);
                while (rs.next()) {
                    if( rs.getString(2).equals(lstMenuUno.getSelectedValue().toString()) ) {
                        if(rs.getString(3).equals("SI")) {
                            jRadioButton19.setSelected(true);
                            jRadioButton20.setSelected(false);
                        }
                        else {
                            jRadioButton19.setSelected(false);
                            jRadioButton20.setSelected(true);
                        }
                    }
                }
                rs.close();
            }
            catch(Exception ex) {
                System.out.println(ex);
                jRadioButton19.setSelected(false);
                jRadioButton20.setSelected(false);
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton20;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList lstMenu;
    private javax.swing.JList lstMenuUno;
    private javax.swing.JList lstUsuario;
    private javax.swing.JList lstUsuarioUno;
    // End of variables declaration//GEN-END:variables
}
