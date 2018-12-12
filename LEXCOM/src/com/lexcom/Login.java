package com.lexcom;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JDialog {
    Connection conn;
    Integer usuario;
    Integer repeticion;
    boolean logueado;
    
    public Login(java.awt.Frame parent, boolean modal, Connection conn) {
        super(parent, modal);
        this.conn = conn;
        this.repeticion = 0;
        logueado = false;
        initComponents();
        this.setAlwaysOnTop(true);
        this.btnNContrasenia.setVisible(false);
        this.txtNueva_Contrasenia.setVisible(false);
        this.lblNc.setVisible(false);
        
        com.lexcom.driver.Login DLogin = new com.lexcom.driver.Login(conn);
        this.cbxUsuario.setModel(DLogin.dar_usuarios());
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(logueado == false) {
                    System.exit(0);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
                if(logueado == false) {
                    System.exit(0);
                }
            }
            @Override
            public void windowOpened(WindowEvent e) {
                
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
        });
        
        this.cbxUsuario.requestFocus();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtpContrasena = new javax.swing.JPasswordField();
        cbxUsuario = new javax.swing.JComboBox();
        lblNc = new javax.swing.JLabel();
        txtNueva_Contrasenia = new javax.swing.JPasswordField();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnNContrasenia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("LOGIN");
        setResizable(false);

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseña");

        txtpContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpContrasenaKeyPressed(evt);
            }
        });

        lblNc.setText("Nueva Contraseña");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 299, Short.MAX_VALUE))
                    .addComponent(cbxUsuario, 0, 355, Short.MAX_VALUE)
                    .addComponent(lblNc)
                    .addComponent(txtNueva_Contrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNueva_Contrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATOS GENERALES", jPanel1);

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

        btnNContrasenia.setText("Nueva Contraseña");
        btnNContrasenia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNContraseniaActionPerformed(evt);
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
                        .addComponent(btnNContrasenia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar)
                    .addComponent(btnNContrasenia))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        loguear();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtpContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpContrasenaKeyPressed
        if (evt.getKeyCode()==10){
            loguear();
        }
    }//GEN-LAST:event_txtpContrasenaKeyPressed

    private void btnNContraseniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNContraseniaActionPerformed
        if(!this.txtNueva_Contrasenia.getText().equals("")){
        Nueva_Contrasena();
        }else{
            this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Ingrese Nueva Contraseña.");
            this.txtpContrasena.requestFocus();
            this.txtpContrasena.selectAll();
            this.setAlwaysOnTop(true);
        }
    }//GEN-LAST:event_btnNContraseniaActionPerformed

    public Integer dar_usuario() {
        return usuario;
    }
    
     private void Nueva_Contrasena() {
        int resultado;
        com.lexcom.driver.Login DLogin = new com.lexcom.driver.Login(conn);
        
        resultado = DLogin.NewLogin(this.cbxUsuario.getSelectedItem().toString(), this.txtpContrasena.getText(),this.txtNueva_Contrasenia.getText());
        if(resultado==2) {
            this.usuario = DLogin.dar_usuario_login();
            logueado = true;
            this.dispose();
        }  else if(resultado==0) {
            this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Usuario o contraseña no valida.");
            this.txtpContrasena.requestFocus();
            this.txtpContrasena.selectAll();
            this.setAlwaysOnTop(true);
            this.repeticion++;
            if(this.repeticion == 3) {
                System.exit(0);
            }
            }else if(resultado==3){
            this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "La Nueva Contraseña y la anterior tienen que ser diferentes.");
            this.txtpContrasena.requestFocus();
            this.txtpContrasena.selectAll();
            this.setAlwaysOnTop(true);
            
            }
            
        
    }
    
    
    private void loguear() {
        int resultado;
        com.lexcom.driver.Login DLogin = new com.lexcom.driver.Login(conn);
        
        resultado = DLogin.Login(this.cbxUsuario.getSelectedItem().toString(), this.txtpContrasena.getText());
        if(resultado==1) {
            this.usuario = DLogin.dar_usuario_login();
            logueado = true;
            this.dispose();
        }else if(resultado==2){
            this.btnAceptar.setVisible(false);
            this.btnNContrasenia.setVisible(true);
            this.lblNc.setVisible(true);
            this.txtNueva_Contrasenia.setVisible(true);
            
        } else {
            this.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Usuario o contraseña no valida.");
            this.txtpContrasena.requestFocus();
            this.txtpContrasena.selectAll();
            this.setAlwaysOnTop(true);
            this.repeticion++;
            if(this.repeticion == 3) {
                System.exit(0);
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNContrasenia;
    private javax.swing.JComboBox cbxUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblNc;
    private javax.swing.JPasswordField txtNueva_Contrasenia;
    private javax.swing.JPasswordField txtpContrasena;
    // End of variables declaration//GEN-END:variables
}
