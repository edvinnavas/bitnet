package com.lexcom;

import java.io.File;
import java.sql.Connection;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Deudor_Masivo extends javax.swing.JInternalFrame {

    Connection conn;
    Integer usuario;
    
    public Deudor_Masivo(Connection conn, Integer usuario) {
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaDeudores = new javax.swing.JTable();
        btnCargar = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setTitle("CARGA DE DEUDORES");

        tblListaDeudores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Deudor", "Actor", "Moneda", "DPI", "NIT", "Fecha Nacimiento", "Nombre", "Nacionalidad", "Teléfono Casa", "Teléfono Celular", "Dirección", "Zona", "Pais", "Departamento", "Sexo", "Estado Civil", "Fecha Ingreso", "Profesión", "Correo Electronico", "Lugar de Trabajo", "Dirección de Trabajo", "Teléfono de Trabajo", "Monto Inicial", "Usuario", "Estado", "Status", "No. Cuenta", "Garantía", "Cargado", "Estado Sistema", "Descripción", "Codigo Contactabilidad", "Caso", "PDF", "INV", "MAYCOM", "NITS", "Cosecha", "Otro No. Cuenta", "Descripción Adicional", "Fecha Recepción", "Anticipo", "Antiguedad", "Fecha Proximo Pago", "Monto Proximo Pago", "Saldo", "Convenio Pactado", "Cuota Convenio", "Costas Pagadas", "Situacion Caso", "Opcion Proximo Pago", "Procuración"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListaDeudores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblListaDeudores.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tblListaDeudores);

        btnCargar.setText("Cargar");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        btnInsertar.setText("Insertar");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCargar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInsertar)
                .addContainerGap(765, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCargar)
                    .addComponent(btnInsertar))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lista de Deudores", jPanel1);

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
                .addContainerGap(841, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE)
                    .addGap(2, 2, 2)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(393, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                    .addGap(43, 43, 43)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        JFileChooser fileChooser = new JFileChooser(".");
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String direccion = selectedFile.getParent() + "/" + selectedFile.getName();
            com.lexcom.driver.Deudor_Masivo DM = new com.lexcom.driver.Deudor_Masivo(this.conn,this.usuario);
            DefaultTableModel modelo = DM.cargar_archivo(direccion, (DefaultTableModel) this.tblListaDeudores.getModel());
            this.tblListaDeudores.setModel(modelo);
        } 
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        com.lexcom.driver.Deudor_Masivo DM = new com.lexcom.driver.Deudor_Masivo(this.conn, this.usuario);
        String mensaje = DM.cargar_deudores((DefaultTableModel) this.tblListaDeudores.getModel());
        JOptionPane.showMessageDialog(this, mensaje);
    }//GEN-LAST:event_btnInsertarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblListaDeudores;
    // End of variables declaration//GEN-END:variables
}
