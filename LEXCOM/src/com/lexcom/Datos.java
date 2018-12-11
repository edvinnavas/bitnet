package com.lexcom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class Datos extends javax.swing.JInternalFrame {

    Connection conn;
    Integer usuario;
    String cadenasql;
    String tabla;
    String menu;
    String[] campos_busqueda;
    
    public Datos(Connection conn, Integer usuario, String cadenasql, String tabla, String[] campos_busqueda, String menu) {
        this.conn = conn;
        this.usuario = usuario;
        this.cadenasql = cadenasql;
        this.tabla = tabla;
        this.campos_busqueda = campos_busqueda;
        this.menu = menu;
        initComponents();

        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql));
        this.cbxBuscarPor.setModel(DDatos.obtener_lista_busqueda((DefaultTableModel) this.jTable1.getModel()));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
        this.cargar_permisos();
    }
    
    public void cargar_permisos() {
        try {
            String cadenasql_permiso = "select a.nuevo, a.modificar, a.eliminar, a.activar, a.ver from permiso_usuario a, menu b where a.menu=b.menu and a.usuario=" + this.usuario + " and b.nombre='" + this.menu + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql_permiso);
            while(rs.next()) {
                if(rs.getString(1).equals("SI")) {
                    this.btnNuevo.setEnabled(true);
                } else {
                    this.btnNuevo.setEnabled(false);
                }
                if(rs.getString(2).equals("SI")) {
                    this.btnModificar.setEnabled(true);
                } else {
                    this.btnModificar.setEnabled(false);
                }
                if(rs.getString(3).equals("SI")) {
                    this.btnEliminar.setEnabled(true);
                } else {
                    this.btnEliminar.setEnabled(false);
                }
                if(rs.getString(4).equals("SI")) {
                    this.btnActivar.setEnabled(true);
                } else {
                    this.btnActivar.setEnabled(false);
                }
                if(rs.getString(5).equals("SI")) {
                    this.btnVer.setEnabled(true);
                } else {
                    this.btnVer.setEnabled(false);
                }
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtTextoBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbxBuscarPor = new javax.swing.JComboBox();
        btnLimpiar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

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
        jTable1.setNextFocusableComponent(btnCerrar);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtTextoBuscar.setNextFocusableComponent(btnBuscar);
        txtTextoBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTextoBuscarKeyPressed(evt);
            }
        });

        jLabel1.setText("Texto de Busqueda");

        btnBuscar.setText("Buscar");
        btnBuscar.setNextFocusableComponent(btnLimpiar);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar por");

        cbxBuscarPor.setNextFocusableComponent(txtTextoBuscar);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.setNextFocusableComponent(btnNuevo);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.setNextFocusableComponent(btnModificar);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.setNextFocusableComponent(btnEliminar);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setNextFocusableComponent(btnActivar);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActivar.setText("Activar");
        btnActivar.setNextFocusableComponent(btnVer);
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
            }
        });

        btnVer.setText("Ver");
        btnVer.setNextFocusableComponent(jTable1);
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        jLabel3.setText("No. registros:");

        jLabel4.setText("0");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 680, Short.MAX_VALUE)
                        .addComponent(btnCerrar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxBuscarPor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimpiar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 332, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbxBuscarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnActivar)
                    .addComponent(btnVer))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.cbxBuscarPor.getSelectedItem().toString();
        String campo_busqueda = this.campos_busqueda[this.cbxBuscarPor.getSelectedIndex()];
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql + " and " + campo_busqueda + " like '%" + this.txtTextoBuscar.getText() + "%'"));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.cbxBuscarPor.getSelectedItem().toString();
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        if (tabla.equals("usuario")) {
            Usuario a = new Usuario(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("actor")) {
            Actor a = new Actor(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("deudor")) {
            Deudor a = new Deudor(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("juzgado")) {
            Juzgado a = new Juzgado(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("garantia")) {
            Garantia a = new Garantia(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("banco")) {
            Banco a = new Banco(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("sestado")) {
            Estado a = new Estado(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("estatus")) {
            Status a = new Status(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("tipo_descuento")) {
            Tipo_Descuento a = new Tipo_Descuento(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("tipo_aumento")) {
            Tipo_Aumento a = new Tipo_Aumento(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("codigo_contactabilidad")) {
            Codigo_Contactabilidad a = new Codigo_Contactabilidad(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("cooperacion")) {
            Cooperacion a = new Cooperacion(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("asignacion_cartera")) {
            Asignacion_Cartera a = new Asignacion_Cartera(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        if (tabla.equals("frase_predeterminada")) {
            Frase_Predeterminada a = new Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 0);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        
        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        Integer fila_posicion = jTable1.getSelectedRow();

        if (fila_posicion != -1) {
            if (tabla.equals("usuario")) {
                Usuario a = new Usuario(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("actor")) {
                Actor a = new Actor(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("deudor")) {
                Deudor a = new Deudor(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("juzgado")) {
                Juzgado a = new Juzgado(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("garantia")) {
                Garantia a = new Garantia(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("banco")) {
                Banco a = new Banco(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("sestado")) {
                Estado a = new Estado(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("estatus")) {
                Status a = new Status(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("tipo_descuento")) {
                Tipo_Descuento a = new Tipo_Descuento(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("tipo_aumento")) {
                Tipo_Aumento a = new Tipo_Aumento(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("codigo_contactabilidad")) {
                Codigo_Contactabilidad a = new Codigo_Contactabilidad(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("cooperacion")) {
                Cooperacion a = new Cooperacion(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("asignacion_cartera")) {
                Asignacion_Cartera a = new Asignacion_Cartera(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("frase_predeterminada")) {
                Frase_Predeterminada a = new Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 1);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
        
        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Integer fila_posicion = jTable1.getSelectedRow();

        if (fila_posicion != -1) {
            if (tabla.equals("usuario")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el usuario?", "ELIMINACIÓN USUARIO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Usuario drive = new com.lexcom.driver.Usuario(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("actor")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el actor?", "ELIMINACIÓN ACTOR", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Actor drive = new com.lexcom.driver.Actor(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("deudor")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el deudor?", "ELIMINACIÓN DEUDOR", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Deudor drive = new com.lexcom.driver.Deudor(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("juzgado")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el juzgado?", "ELIMINACIÓN JUZGADO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Juzgado drive = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("garantia")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el garantía?", "ELIMINACIÓN GARANTÍA", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Garantia drive = new com.lexcom.driver.Garantia(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("banco")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el banco?", "ELIMINACIÓN BANCO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Banco drive = new com.lexcom.driver.Banco(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("sestado")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el estado?", "ELIMINACIÓN ESTADO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Estado drive = new com.lexcom.driver.Estado(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("estatus")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el status?", "ELIMINACIÓN STATUS", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Status drive = new com.lexcom.driver.Status(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("tipo_descuento")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el tipo de descuento?", "ELIMINACIÓN TIPO DESCUENTO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Tipo_Descuento drive = new com.lexcom.driver.Tipo_Descuento(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("tipo_aumento")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el tipo de aumento?", "ELIMINACIÓN TIPO AUMENTO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Tipo_Aumento drive = new com.lexcom.driver.Tipo_Aumento(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("codigo_contactabilidad")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el codigo contactabilidad?", "ELIMINACIÓN CÓDIGO DE CONTACTABILIDAD", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Codigo_Contactabilidad drive = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("cooperacion")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar la cooperación?", "ELIMINACIÓN COOPERACIÓN", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Cooperacion drive = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("asignacion_cartera")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar la asignación de cartera?", "ASIGNACIÓN CARTERA", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Asignacion_Cartera drive = new com.lexcom.driver.Asignacion_Cartera(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("frase_predeterminada")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar la frase predeterminada?", "FRASE PREDETERMINADA", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Frase_Predeterminada drive = new com.lexcom.driver.Frase_Predeterminada(this.conn, this.usuario);
                    String resultado = drive.eliminar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
        
        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        Integer fila_posicion = jTable1.getSelectedRow();

        if (fila_posicion != -1) {
            if (tabla.equals("usuario")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el usuario?", "ACTIVACIÓN USUARIO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Usuario drive = new com.lexcom.driver.Usuario(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("actor")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el actor?", "ACTIVACIÓN ACTOR", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Actor drive = new com.lexcom.driver.Actor(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("deudor")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el deudor?", "ACTIVACIÓN DEUDOR", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Deudor drive = new com.lexcom.driver.Deudor(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("juzgado")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el juzgado?", "ACTIVACIÓN JUZGADO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Juzgado drive = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("garantia")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el garantía?", "ACTIVACIÓN GARANTÍA", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Garantia drive = new com.lexcom.driver.Garantia(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("banco")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el banco?", "ACTIVACIÓN BANCO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Banco drive = new com.lexcom.driver.Banco(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("sestado")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el estado?", "ACTIVACIÓN ESTADO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Estado drive = new com.lexcom.driver.Estado(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("estatus")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el status?", "ACTIVACIÓN STATUS", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Status drive = new com.lexcom.driver.Status(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("tipo_descuento")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el tipo de descuento?", "ACTIVACIÓN TIPO DESCUENTO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Tipo_Descuento drive = new com.lexcom.driver.Tipo_Descuento(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("tipo_aumento")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el tipo de aumento?", "ACTIVACIÓN TIPO AUMENTO", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Tipo_Aumento drive = new com.lexcom.driver.Tipo_Aumento(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("codigo_contactabilidad")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar el codigo contactabilidad?", "ACTIVACIÓN CÓDIGO DE CONTACTABILIDAD", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Codigo_Contactabilidad drive = new com.lexcom.driver.Codigo_Contactabilidad(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("cooperacion")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar la cooperación?", "ACTIVACIÓN COOPERACIÓN", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Cooperacion drive = new com.lexcom.driver.Cooperacion(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
            if (tabla.equals("asignacion_cartera")) {
                JOptionPane.showMessageDialog(null, "Esta opción no puede ser aplicada a la asignación de cartera.");
            }
            if (tabla.equals("frase_predeterminada")) {
                Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea activar la frase predeterminada?", "FRASE PREDETERMINADA", 0);
                if (opcion1 == 0) {
                    com.lexcom.driver.Frase_Predeterminada drive = new com.lexcom.driver.Frase_Predeterminada(this.conn, this.usuario);
                    String resultado = drive.activar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
        
        com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
        this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql));
        Integer registros = this.jTable1.getRowCount();
        this.jLabel4.setText(registros.toString());
    }//GEN-LAST:event_btnActivarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        Integer fila_posicion = jTable1.getSelectedRow();

        if (fila_posicion != -1) {
            if (tabla.equals("usuario")) {
                Usuario a = new Usuario(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("actor")) {
                Actor a = new Actor(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("deudor")) {
                Deudor a = new Deudor(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("juzgado")) {
                Juzgado a = new Juzgado(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("garantia")) {
                Garantia a = new Garantia(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("banco")) {
                Banco a = new Banco(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("sestado")) {
                Estado a = new Estado(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("estatus")) {
                Status a = new Status(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("tipo_descuento")) {
                Tipo_Descuento a = new Tipo_Descuento(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("tipo_aumento")) {
                Tipo_Aumento a = new Tipo_Aumento(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("codigo_contactabilidad")) {
                Codigo_Contactabilidad a = new Codigo_Contactabilidad(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("cooperacion")) {
                Cooperacion a = new Cooperacion(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("asignacion_cartera")) {
                Asignacion_Cartera a = new Asignacion_Cartera(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
            if (tabla.equals("frase_predeterminada")) {
                Frase_Predeterminada a = new Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 2);
                a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnVerActionPerformed

    private void txtTextoBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTextoBuscarKeyPressed
        if (evt.getKeyCode()==10){
            com.lexcom.driver.Datos DDatos = new com.lexcom.driver.Datos(this.conn);
            this.cbxBuscarPor.getSelectedItem().toString();
            String campo_busqueda = this.campos_busqueda[this.cbxBuscarPor.getSelectedIndex()];
            this.jTable1.setModel(DDatos.obtener_tabla(this.cadenasql + " and " + campo_busqueda + " like '%" + this.txtTextoBuscar.getText() + "%'"));
            Integer registros = this.jTable1.getRowCount();
            this.jLabel4.setText(registros.toString());
        }
    }//GEN-LAST:event_txtTextoBuscarKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = this.jTable1.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                if (tabla.equals("usuario")) {
                    Usuario a = new Usuario(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("actor")) {
                    Actor a = new Actor(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("deudor")) {
                    Deudor a = new Deudor(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("juzgado")) {
                    Juzgado a = new Juzgado(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("garantia")) {
                    Garantia a = new Garantia(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("banco")) {
                    Banco a = new Banco(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("sestado")) {
                    Estado a = new Estado(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("estatus")) {
                    Status a = new Status(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("tipo_descuento")) {
                    Tipo_Descuento a = new Tipo_Descuento(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("tipo_aumento")) {
                    Tipo_Aumento a = new Tipo_Aumento(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("codigo_contactabilidad")) {
                    Codigo_Contactabilidad a = new Codigo_Contactabilidad(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("cooperacion")) {
                    Cooperacion a = new Cooperacion(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("asignacion_cartera")) {
                    Asignacion_Cartera a = new Asignacion_Cartera(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
                if (tabla.equals("frase_predeterminada")) {
                    Frase_Predeterminada a = new Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 1);
                    a.cargar(Integer.parseInt(jTable1.getValueAt(fila_posicion, 0).toString()));
                    Dimension ventana = a.getSize();
                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                    a.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVer;
    private javax.swing.JComboBox cbxBuscarPor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtTextoBuscar;
    // End of variables declaration//GEN-END:variables
}
