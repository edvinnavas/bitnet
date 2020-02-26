package com.lexcom;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Constantes extends javax.swing.JDialog {

    DefaultTableModel modelo;
    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    
    public Constantes(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
        cargar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblConstantes = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CONSTANTES");
        setResizable(false);

        tblConstantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "INDICE", "NOMBRE", "VALOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblConstantes);
        tblConstantes.getColumnModel().getColumn(0).setResizable(false);
        tblConstantes.getColumnModel().getColumn(1).setResizable(false);
        tblConstantes.getColumnModel().getColumn(2).setResizable(false);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargar() {
        try {
            modelo = (DefaultTableModel) this.tblConstantes.getModel();
        
            while (this.modelo.getRowCount() > 0) {
                this.modelo.removeRow(0);
            }
            
            String cadenasql = "SELECT constantes AS CONSTANTE, nombre AS NOMBRE, valor AS VALOR FROM constantes c;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Object[] fila = new Object[3];
                for (int i = 0; i < 3; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                this.modelo.addRow(fila);
            }
            rs.close();
            stmt.close();
            
            this.tblConstantes.setModel(modelo);
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {
            for(Integer i=0; i < this.tblConstantes.getRowCount(); i++) {
                String cadenasql = "update constantes set valor='" + 
                        this.tblConstantes.getValueAt(i, 2) + "' " +
                        "where constantes='" + this.tblConstantes.getValueAt(i, 0).toString() + "'";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                JOptionPane.showMessageDialog(this, "Constantes actualizadas exitosamente.");
                this.dispose();
            }
        } catch(SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.toString());
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblConstantes;
    // End of variables declaration//GEN-END:variables

}
