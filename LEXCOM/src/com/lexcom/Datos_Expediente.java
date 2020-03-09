package com.lexcom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Datos_Expediente extends javax.swing.JInternalFrame {

    Connection conn;
    Integer usuario;
    Boolean es_asignacion;
    
    public Datos_Expediente(Connection conn, Integer usuario) {
        this.conn = conn;
        this.usuario = usuario;
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTextoBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("DATOS");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setFillsViewportHeight(true);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtTextoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTextoBuscarKeyReleased(evt);
            }
        });

        jLabel1.setText("Texto de Busqueda");

        btnBuscar.setText("Gestionar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("No. registros:");

        jLabel4.setText("0");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Solo CARGADOS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 690, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void bolson_asignacion(Boolean es_asignacion) {
        this.es_asignacion = es_asignacion;
        this.buscar_asignacion();
        this.jButton1.setEnabled(false);
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Integer fila_posicion = jTable1.getSelectedRow();

        if (fila_posicion != -1) {
            com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(conn, usuario);
            if (DUsuario.validar_corporacion(usuario, Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()))) {
                Expediente a = new Expediente(conn, usuario);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (es_asignacion) {
                            buscar_asignacion();
                        }
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
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "La corporación del actor asignado el expediente no puede ser consultado por el usuario.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!this.txtTextoBuscar.getText().equals("")) {
            buscar();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTextoBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTextoBuscarKeyReleased
        if (evt.getKeyCode()==40) {
            if(!this.txtTextoBuscar.getText().equals("")) {
                if(this.jTable1.getRowCount()>0) {
                    this.jTable1.requestFocus();
                    this.jTable1.setRowSelectionInterval(0, 0);
                }
            }
        }
        if (evt.getKeyCode()==10) {
            if(!this.txtTextoBuscar.getText().equals("")) {
                if(this.es_asignacion == null) {
                    this.es_asignacion = false;
                }
                if(!this.es_asignacion) {
                    buscar();
                }
            }
        }
    }//GEN-LAST:event_txtTextoBuscarKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = this.jTable1.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(conn, usuario);
                if (DUsuario.validar_corporacion(usuario, Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()))) {
                    Expediente a = new Expediente(conn, usuario);
                    a.addWindowListener(new WindowListener() {

                        @Override
                        public void windowClosed(WindowEvent e) {
                            if (es_asignacion) {
                                buscar_asignacion();
                            }
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
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "La corporación del actor asignado el expediente no puede ser consultado por el usuario.");
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == 10) {
            Integer fila_posicion = this.jTable1.getSelectedRow();
            if (fila_posicion != -1) {
                com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(conn, usuario);
                if (DUsuario.validar_corporacion(usuario, Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()))) {
                    Expediente a = new Expediente(conn, usuario);
                    a.addWindowListener(new WindowListener() {

                        @Override
                        public void windowClosed(WindowEvent e) {
                            if (es_asignacion) {
                                buscar_asignacion();
                            }
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
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "La corporación del actor asignado el expediente no puede ser consultado por el usuario.");
                }
            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void buscar() {
        Boolean solo_cargados = true;
        if(this.jCheckBox1.isSelected()) {
            solo_cargados = true;
        } else  {
            solo_cargados = false;
        }
        
        com.lexcom.driver.Datos_Expediente DDatos_Expediente = new com.lexcom.driver.Datos_Expediente(this.conn);
        this.jTable1.setModel(DDatos_Expediente.obtener_tabla(this.txtTextoBuscar.getText(), solo_cargados));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }
    
    private void buscar_asignacion() {
        com.lexcom.driver.Datos_Expediente DDatos_Expediente = new com.lexcom.driver.Datos_Expediente(this.conn);
        this.jTable1.setModel(DDatos_Expediente.obtener_tabla_asignacion(this.usuario));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtTextoBuscar;
    // End of variables declaration//GEN-END:variables
}
