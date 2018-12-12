package com.lexcom;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Modulo_Calculadora extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    String tipo;
    
    String frase;
    String fecha;
    
    Double saldo_capital;
    Double interes;
    Double mora;
    Double gastos;
    Double rebaja_por;
    Double rebaja;
    Double subtotal;
    Double costas_por;
    Double costas;
    Double total;
    Double monto_cuotas;
    Double cuotas;
    
    Boolean editando;
    
    public Modulo_Calculadora(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        this.editando = false;
        initComponents();

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "RUBRO", "PORCENTAJE", "MONTO"
                }) {

            Class[] types = new Class[] {
                //java.lang.String.class, java.lang.Double.class, java.lang.Double.class
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                boolean resultado = true;
                        
                if(columnIndex == 0) {
                    resultado = false;
                }
                if(columnIndex == 1) {
                    if(rowIndex == 4 || rowIndex == 6 || rowIndex == 8) {
                        resultado = true;
                    } else {
                        resultado = false;
                    }
                }
                if(columnIndex == 2) {
                    if(rowIndex == 0 || rowIndex == 1 || rowIndex == 2 || rowIndex == 3) {
                        resultado = true;
                    } else {
                        resultado = false;
                    }
                }
                
                return resultado;
            }
            
        };
        this.tblCalculadora.setModel(tableModel);
        
        DefaultTableCellRenderer MyRender = new DefaultTableCellRenderer() {
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.YELLOW);
                
                if(column == 0) {
                    c.setBackground(Color.GREEN);
                }
                if(column == 1) {
                    if(row == 4 || row == 6 || row == 8) {
                        c.setBackground(Color.GREEN);
                    }
                }
                if(column == 2) {
                    if(row == 0 || row == 1 || row == 2 || row == 3) {
                        c.setBackground(Color.GREEN);
                    }
                }
                
                return c;
            }
            
        };
        this.tblCalculadora.setDefaultRenderer(Object.class, MyRender);
        
        // CAPTURA LOS CAMBIOS EN LA TABLA PARA RE-CALCULAR LOS RUBROS.
        this.tblCalculadora.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                
                if(e.getColumn() == 1) {
                    if(e.getFirstRow() == 4) {
                        try {
                            String rebaja_por_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            rebaja_por = Double.parseDouble(rebaja_por_letras);
                            if(rebaja_por < 0.00 || rebaja_por > 1) {
                                rebaja_por = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            rebaja_por = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                    if(e.getFirstRow() == 6) {
                        try {
                            String costas_por_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            costas_por = Double.parseDouble(costas_por_letras);
                            if(costas_por < 0.00 || costas_por > 1) {
                                costas_por = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            costas_por = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                    if(e.getFirstRow() == 8) {
                        try {
                            String cuotas_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            cuotas = Double.parseDouble(cuotas_letras);
                            if(cuotas < 0.00) {
                                cuotas = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            cuotas = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                }
                
                if(e.getColumn() == 2) {
                    if(e.getFirstRow() == 0) {
                        try {
                            String saldo_capital_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            saldo_capital = Double.parseDouble(saldo_capital_letras);
                            if(saldo_capital < 0.00) {
                                saldo_capital = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            saldo_capital = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                    if(e.getFirstRow() == 1) {
                        try {
                            String interes_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            interes = Double.parseDouble(interes_letras);
                            if(interes < 0.00) {
                                interes = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            interes = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                    if(e.getFirstRow() == 2) {
                        try {
                            String mora_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            mora = Double.parseDouble(mora_letras);
                            if(mora < 0.00) {
                                mora = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            mora = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                    if(e.getFirstRow() == 3) {
                        try {
                            String gastos_letras = tblCalculadora.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                            gastos = Double.parseDouble(gastos_letras);
                            if(gastos < 0.00) {
                                gastos = 0.00;
                                tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                            }
                        } catch(Exception ex) {
                            gastos = 0.00;
                            tblCalculadora.setValueAt("0.00", e.getFirstRow(), e.getColumn());
                        }
                    }
                }
                
                //CALCULA TODOS LOS RUBROS
                rebaja = rebaja_por * interes;
                subtotal = saldo_capital + mora + gastos + rebaja;
                costas = costas_por * subtotal;
                total = subtotal + costas;
                if(cuotas == 0.00) {
                    monto_cuotas = 0.00;
                } else {
                    monto_cuotas = total / cuotas;
                }
                
                if(!editando) {
                    editando = true;
                    tblCalculadora.setValueAt(String.format("%.2f", saldo_capital), 0, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", interes), 1, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", mora), 2, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", gastos), 3, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", rebaja_por), 4, 1);
                    tblCalculadora.setValueAt(String.format("%.2f", rebaja), 4, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", subtotal), 5, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", costas_por), 6, 1);
                    tblCalculadora.setValueAt(String.format("%.2f", costas), 6, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", total), 7, 2);
                    tblCalculadora.setValueAt(String.format("%.2f", cuotas), 8, 1);
                    tblCalculadora.setValueAt(String.format("%.2f", monto_cuotas), 8, 2);
                    editando = false;
                }
                
                frase = generar_frase();
                areTextoSugerido.setText(frase);
            }
        });
        
        Date fecha_actual = new Date();
        Integer anio = fecha_actual.getYear() + 1900;
        Integer mes = fecha_actual.getMonth() + 1;
        Integer dia = fecha_actual.getDate();
        this.fecha = anio + "/" + mes + "/" + dia;
        
        this.saldo_capital = 0.00;
        this.interes = 0.00;
        this.mora = 0.00;
        this.gastos = 0.00;
        this.rebaja_por = 0.00;
        this.rebaja = 0.00;
        this.subtotal = 0.00;
        this.costas_por = 0.00;
        this.costas = 0.00;
        this.total = 0.00;
        this.monto_cuotas = 0.00;
        this.cuotas = 0.00;
        
        this.frase = this.generar_frase();
        this.areTextoSugerido.setText(this.frase);
        
        this.cargar();
    }
    
    private void cargar() {
        this.tblCalculadora.setValueAt("CAPITAL", 0, 0);
        this.tblCalculadora.setValueAt("INTERESES", 1, 0);
        this.tblCalculadora.setValueAt("MORA", 2, 0);
        this.tblCalculadora.setValueAt("GASTOS", 3, 0);
        this.tblCalculadora.setValueAt("REBAJA INTERESES", 4, 0);
        this.tblCalculadora.setValueAt("SUBTOTAL", 5, 0);
        this.tblCalculadora.setValueAt("COSTAS 15%", 6, 0);
        this.tblCalculadora.setValueAt("TOTAL", 7, 0);
        this.tblCalculadora.setValueAt("CUOTAS", 8, 0);
        
        this.tblCalculadora.setValueAt(" ", 0, 1);
        this.tblCalculadora.setValueAt(" ", 1, 1);
        this.tblCalculadora.setValueAt(" ", 2, 1);
        this.tblCalculadora.setValueAt(" ", 3, 1);
        this.tblCalculadora.setValueAt(0.00, 4, 1);
        this.tblCalculadora.setValueAt(" ", 5, 1);
        this.tblCalculadora.setValueAt(0.00, 6, 1);
        this.tblCalculadora.setValueAt(" ", 7, 1);
        this.tblCalculadora.setValueAt(" ", 8, 1);
        
        this.tblCalculadora.setValueAt("0.00", 0, 2);
        this.tblCalculadora.setValueAt("0.00", 1, 2);
        this.tblCalculadora.setValueAt("0.00", 2, 2);
        this.tblCalculadora.setValueAt("0.00", 3, 2);
        this.tblCalculadora.setValueAt("0.00", 4, 2);
        this.tblCalculadora.setValueAt("0.00", 5, 2);
        this.tblCalculadora.setValueAt("0.00", 6, 2);
        this.tblCalculadora.setValueAt("0.00", 7, 2);
        this.tblCalculadora.setValueAt("0.00", 8, 2);
        
        this.frase = this.generar_frase();
        this.areTextoSugerido.setText(this.frase);
    }
    
    private String generar_frase() {
        String resultado = "";
        
        resultado = "-- FECHA (" + this.fecha + ") " + "\n"
                + "     SALDO CAPITAL Q." + String.format("%.2f", this.saldo_capital) + ", " + "\n"
                + "     INTERÉS CON " + String.format("%.2f", this.interes) + "% DE REBAJA Q." + String.format("%.2f", this.rebaja) + ", " + "\n"
                + "     MORA Q." + String.format("%.2f", this.mora) + ", " + "\n"
                + "     GASTOS Q." + String.format("%.2f", this.gastos) + ", " + "\n"
                + "     COSTAS Q." + String.format("%.2f", this.costas) + ", " + "\n"
                + "     TOTAL Q." + String.format("%.2f", this.total) + " " + "\n"
                + "     PAGADERO EN " + String.format("%.2f", this.cuotas) + " CUOTAS DE Q." + String.format("%.2f", this.monto_cuotas);
        
        return resultado;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCalculadora = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areTextoSugerido = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SELECCIONAR FRASE PREDETERMINADA");
        setResizable(false);

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

        tblCalculadora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblCalculadora.setColumnSelectionAllowed(true);
        tblCalculadora.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tblCalculadora);
        tblCalculadora.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel1.setText("Texto sugerido");

        areTextoSugerido.setColumns(20);
        areTextoSugerido.setEditable(false);
        areTextoSugerido.setRows(5);
        jScrollPane2.setViewportView(areTextoSugerido);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos Generales", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        Integer fila_posicion = tblCalculadora.getSelectedRow();
        
        if (fila_posicion != -1) {
            this.frase = this.areTextoSugerido.getText();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una frase predeterminada.");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.frase = "";
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed
    
    public String dar_frase() {
        return frase;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areTextoSugerido;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCalculadora;
    // End of variables declaration//GEN-END:variables

}
