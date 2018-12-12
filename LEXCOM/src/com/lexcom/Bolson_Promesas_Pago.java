package com.lexcom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Bolson_Promesas_Pago extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer seleccion;
    
    public Bolson_Promesas_Pago(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
        cargar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        TblPromesaPago = new javax.swing.JTable();
        btnPagosModificar1 = new javax.swing.JButton();
        btnPagosEliminar1 = new javax.swing.JButton();
        btnPagosVer1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PROMESAS DE PAGO");
        setResizable(false);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        TblPromesaPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RECORDATORIO", "FINANCIERA", "NOMBRE", "CASO", "FECHA INGRESO", "ALERTA", "ESTADO", "MONTO", "COD_USUARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblPromesaPago.setFillsViewportHeight(true);
        jScrollPane18.setViewportView(TblPromesaPago);

        btnPagosModificar1.setText("Modificar");
        btnPagosModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosModificar1ActionPerformed(evt);
            }
        });

        btnPagosEliminar1.setText("Eliminar");
        btnPagosEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosEliminar1ActionPerformed(evt);
            }
        });

        btnPagosVer1.setText("Ver");
        btnPagosVer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosVer1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnPagosModificar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosVer1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagosModificar1)
                    .addComponent(btnPagosEliminar1)
                    .addComponent(btnPagosVer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cargar() {
        com.lexcom.driver.Bolson_Promesa_Pago DBolson_Promesa_Pago = new com.lexcom.driver.Bolson_Promesa_Pago(this.conn,usuario);
        this.TblPromesaPago.setModel(DBolson_Promesa_Pago.obtener_tabla_promesa_pago((DefaultTableModel) this.TblPromesaPago.getModel(), this.usuario));
    }
    
    private void btnPagosModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosModificar1ActionPerformed
        Integer fila_posicion = this.TblPromesaPago.getSelectedRow();
        
        if (fila_posicion != -1) {
            Promesa_Pago a = new Promesa_Pago(new javax.swing.JFrame(), true, conn, usuario, 1, Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 8).toString()));
            a.cargar(Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.cargar();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
}//GEN-LAST:event_btnPagosModificar1ActionPerformed

    private void btnPagosEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosEliminar1ActionPerformed
        Integer fila_posicion = this.TblPromesaPago.getSelectedRow();
        
        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar la promesa de pago?", "ELIMINACIÓN PROMESA DE PAGO", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Promesa_Pago drive = new com.lexcom.driver.Promesa_Pago(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                this.cargar();
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblPromesaPago.setModel(drive.obtener_tabla_promesa_pago((DefaultTableModel) this.TblPromesaPago.getModel(), Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 8).toString())));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
}//GEN-LAST:event_btnPagosEliminar1ActionPerformed

    private void btnPagosVer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosVer1ActionPerformed
        Integer fila_posicion = this.TblPromesaPago.getSelectedRow();
        
        if (fila_posicion != -1) {
            Promesa_Pago a = new Promesa_Pago(new javax.swing.JFrame(), true, conn, usuario, 2, Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 8).toString()));
            a.cargar(Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
}//GEN-LAST:event_btnPagosVer1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblPromesaPago;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnPagosEliminar1;
    private javax.swing.JButton btnPagosModificar1;
    private javax.swing.JButton btnPagosVer1;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane18;
    // End of variables declaration//GEN-END:variables
}
