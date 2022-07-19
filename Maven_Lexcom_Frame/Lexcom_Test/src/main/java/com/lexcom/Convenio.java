package com.lexcom;

import com.lexcom.driver.Nodo_Convenio_Detalle;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Convenio extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    Integer deudor;
    Integer conta_promesas;
    
    List<Nodo_Convenio_Detalle> promesas = new ArrayList<>();
 
    public Convenio(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion, Integer deudor) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        this.deudor = deudor;
        initComponents();

        this.txtSubTotal.setEditable(false);
        this.txtMontoCostas.setEditable(false);
        this.txtTotal.setEditable(false);
        this.txtTotalPagar.setEditable(false);
        this.txtCuota.setEditable(false);

        if (accion == 2) {
            this.cbxTipoConvenio.setEnabled(false);
            this.cbxEstado.setEnabled(false);
            this.spnSaldo.setEnabled(false);
            this.spnIntereses.setEnabled(false);
            this.spnMora.setEnabled(false);
            this.spnGastosOtros.setEnabled(false);
            this.spnRebajaInteres.setEnabled(false);
            this.spnCostas.setEnabled(false);
            this.spnCuotaInicial.setEnabled(false);
            this.spnNoCuotas.setEnabled(false);
            this.cbxFrecuencia.setEnabled(false);
            this.dccFechaPago.setEnabled(false);
            this.btnAgregar.setEnabled(false);
            this.btnQuitar.setEnabled(false);
            this.btnCancelar.setEnabled(false);
        }
        this.cbxTipoConvenio.requestFocus();
        this.conta_promesas = 0;

        this.btnQuitar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spnSaldo = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        dccFechaPago = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox();
        spnIntereses = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        spnCostas = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        spnCuotaInicial = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        spnNoCuotas = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        cbxFrecuencia = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMontoCostas = new javax.swing.JTextField();
        txtSubTotal = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotalPagar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCuota = new javax.swing.JTextField();
        cbxTipoConvenio = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        spnMora = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        spnGastosOtros = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        spnRebajaInteres = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TblPromesasPago = new javax.swing.JTable();
        btnQuitar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        labResPro = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areObservacion = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CONVENIO DE PAGO");
        setResizable(false);

        jLabel5.setText("Saldo");

        spnSaldo.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnSaldo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnSaldoStateChanged(evt);
            }
        });

        jLabel1.setText("Fecha pago inicial");

        dccFechaPago.setNothingAllowed(false);
        dccFechaPago.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaPago.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                dccFechaPagoOnSelectionChange(evt);
            }
        });

        jLabel2.setText("Estado");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGOCIACION", "ACTIVO", "ANULADO", "TERMINADO" }));
        cbxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoActionPerformed(evt);
            }
        });

        spnIntereses.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnIntereses.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnInteresesStateChanged(evt);
            }
        });

        jLabel6.setText("Interes");

        spnCostas.setModel(new javax.swing.SpinnerNumberModel(15.0d, 0.0d, 100.0d, 1.0d));
        spnCostas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnCostasStateChanged(evt);
            }
        });

        jLabel7.setText("Costas %");

        spnCuotaInicial.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnCuotaInicial.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnCuotaInicialStateChanged(evt);
            }
        });

        jLabel8.setText("Cuota inicial");

        spnNoCuotas.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spnNoCuotas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNoCuotasStateChanged(evt);
            }
        });

        jLabel9.setText("No. cuotas");

        cbxFrecuencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENSUAL", "QUINCENAL", "SEMANAL", "DIARIO", "MISMO DIA" }));
        cbxFrecuencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFrecuenciaActionPerformed(evt);
            }
        });

        jLabel10.setText("Frecuencia");

        jLabel11.setText("Monto costas");

        jLabel12.setText("Sub-Total");

        txtMontoCostas.setBackground(new java.awt.Color(255, 255, 51));
        txtMontoCostas.setEditable(false);
        txtMontoCostas.setForeground(new java.awt.Color(204, 0, 0));
        txtMontoCostas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMontoCostas.setText("0.00");

        txtSubTotal.setBackground(new java.awt.Color(255, 255, 51));
        txtSubTotal.setEditable(false);
        txtSubTotal.setForeground(new java.awt.Color(204, 0, 0));
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubTotal.setText("0.00");

        txtTotal.setBackground(new java.awt.Color(255, 255, 51));
        txtTotal.setEditable(false);
        txtTotal.setForeground(new java.awt.Color(204, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0.00");

        jLabel13.setText("Total");

        txtTotalPagar.setBackground(new java.awt.Color(255, 255, 51));
        txtTotalPagar.setEditable(false);
        txtTotalPagar.setForeground(new java.awt.Color(204, 0, 0));
        txtTotalPagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalPagar.setText("0.00");

        jLabel14.setText("Saldo cuotas");

        jLabel15.setText("Cuota");

        txtCuota.setBackground(new java.awt.Color(255, 255, 51));
        txtCuota.setEditable(false);
        txtCuota.setForeground(new java.awt.Color(204, 0, 0));
        txtCuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCuota.setText("0.00");

        cbxTipoConvenio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NORMAL", "CANCELACION TOTAL", "TEMPORAL", "TRANSACCION", "PAGOS SIN CONVENIO" }));
        cbxTipoConvenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoConvenioActionPerformed(evt);
            }
        });

        jLabel4.setText("Tipo convenio");

        spnMora.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnMora.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnMoraStateChanged(evt);
            }
        });

        jLabel16.setText("Mora");

        spnGastosOtros.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnGastosOtros.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnGastosOtrosStateChanged(evt);
            }
        });

        jLabel17.setText("Gastos y Otros");

        spnRebajaInteres.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spnRebajaInteres.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnRebajaInteresStateChanged(evt);
            }
        });

        jLabel18.setText("Rebaja Intereses");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15)
                    .addComponent(jLabel10)
                    .addComponent(jLabel1))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxFrecuencia, javax.swing.GroupLayout.Alignment.TRAILING, 0, 519, Short.MAX_VALUE)
                    .addComponent(txtCuota, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnNoCuotas, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnCuotaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(txtMontoCostas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnCostas, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnIntereses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(cbxEstado, 0, 519, Short.MAX_VALUE)
                    .addComponent(cbxTipoConvenio, 0, 519, Short.MAX_VALUE)
                    .addComponent(spnGastosOtros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnRebajaInteres, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(spnMora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(dccFechaPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTipoConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spnIntereses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnMora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnGastosOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnRebajaInteres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnCostas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMontoCostas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnCuotaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnNoCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxFrecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(dccFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(258, 258, 258))
        );

        jTabbedPane1.addTab("Convenio", jPanel1);

        TblPromesasPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Promesa", "Fecha", "Hora", "Estado", "Monto", "F. Creación", "F. Anulación", "F. Cumplimiento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblPromesasPago.setFillsViewportHeight(true);
        TblPromesasPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblPromesasPagoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TblPromesasPago);

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        labResPro.setText("Pendiente:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labResPro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                .addComponent(btnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuitar)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitar)
                    .addComponent(btnAgregar)
                    .addComponent(labResPro))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Promesas de pago", jPanel2);

        jLabel3.setText("Descripción");

        areObservacion.setColumns(20);
        areObservacion.setRows(5);
        areObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areObservacionKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(areObservacion);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Observación", jPanel3);

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
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void spnSaldoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnSaldoStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnSaldoStateChanged

    private void spnInteresesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnInteresesStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnInteresesStateChanged

    private void spnCostasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnCostasStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnCostasStateChanged

    private void spnCuotaInicialStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnCuotaInicialStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnCuotaInicialStateChanged

    private void spnNoCuotasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnNoCuotasStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnNoCuotasStateChanged

    private void TblPromesasPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblPromesasPagoMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = this.TblPromesasPago.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Integer no_promesa = Integer.parseInt(this.TblPromesasPago.getValueAt(fila_posicion, 0).toString());
                int index_lst = 0;
                for (Integer i = 0; i < this.promesas.size(); i++) {
                    if (this.promesas.get(i).getPromesa_pago() == no_promesa) {
                        index_lst = i;
                    }
                }
                Convenio_Detalle a = new Convenio_Detalle(new javax.swing.JFrame(), true, conn, usuario);
                Integer modificar = 0;

                if (this.cbxEstado.getSelectedItem().toString().equals("TERMINADO") || this.cbxEstado.getSelectedItem().toString().equals("ANULADO")) {
                    modificar = 2;
                }

                a.cargar_datos(
                        this.promesas.get(index_lst).getFecha_pago(),
                        this.promesas.get(index_lst).getHora_pago(),
                        this.promesas.get(index_lst).getEstado_promesa(),
                        this.promesas.get(index_lst).getMonto(),
                        this.promesas.get(index_lst).getObservacion(),
                        modificar);
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);

                if (a.fecha != null && a.hora != null && a.estado != null && a.monto != null && a.observacion != null) {
                    this.promesas.get(index_lst).setFecha_pago(a.fecha);
                    this.promesas.get(index_lst).setHora_pago(a.hora);
                    this.promesas.get(index_lst).setEstado_promesa(a.estado);
                    this.promesas.get(index_lst).setMonto(a.monto);
                    this.promesas.get(index_lst).setObservacion(a.observacion);

                    this.cargar_promesas_tabla();
                }
            }
        }
    }//GEN-LAST:event_TblPromesasPagoMouseClicked

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        Integer fila_posicion = this.TblPromesasPago.getSelectedRow();

        if (fila_posicion != -1) {
            if (this.TblPromesasPago.getValueAt(fila_posicion, 3).toString().equals("PENDIENTE")) {
                Integer no_promesa = Integer.parseInt(this.TblPromesasPago.getValueAt(fila_posicion, 0).toString());
                int index_lst = 0;
                for (Integer i = 0; i < this.promesas.size(); i++) {
                    if (this.promesas.get(i).getPromesa_pago() == no_promesa) {
                        index_lst = i;
                    }
                }
                this.promesas.remove(index_lst);

                this.cargar_promesas_tabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se puede eliminar promesas de pago cumplidas o incumplidas.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        Boolean promesa_pendiente = false;
        for (Integer i = 0; i < this.promesas.size(); i++) {
            if (this.promesas.get(i).getEstado_promesa().equals("PENDIENTE")) {
                promesa_pendiente = true;
            }
        }

        if (!promesa_pendiente) {
            Convenio_Detalle a = new Convenio_Detalle(new javax.swing.JFrame(), true, conn, usuario);
            a.cargar(false);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            if (a.fecha != null && a.hora != null && a.estado != null && a.monto != null && a.observacion != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Nodo_Convenio_Detalle nodo = new Nodo_Convenio_Detalle(
                        this.conta_promesas,
                        a.fecha,
                        a.hora,
                        a.estado,
                        a.monto,
                        a.observacion,
                        dateFormat.format(new Date()),
                        "-",
                        "-");
                this.conta_promesas++;
                this.promesas.add(nodo);

                this.cargar_promesas_tabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "El deudor tiene promesas de pago PENDIENTES.");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void areObservacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areObservacionKeyReleased
        if (evt.getKeyCode() == 123) {
            Dar_Frase_Predeterminada a = new Dar_Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 0);
            a.set_tipo("CONVENIO");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.areObservacion.setText(this.areObservacion.getText() + a.dar_frase());
        }
    }//GEN-LAST:event_areObservacionKeyReleased

    private void spnMoraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnMoraStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnMoraStateChanged

    private void spnGastosOtrosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnGastosOtrosStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnGastosOtrosStateChanged

    private void spnRebajaInteresStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnRebajaInteresStateChanged
        this.calcular_saldos();
    }//GEN-LAST:event_spnRebajaInteresStateChanged

    private void cbxTipoConvenioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoConvenioActionPerformed
        this.calcular_saldos();
    }//GEN-LAST:event_cbxTipoConvenioActionPerformed

    private void cbxFrecuenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFrecuenciaActionPerformed
        this.calcular_saldos();
    }//GEN-LAST:event_cbxFrecuenciaActionPerformed

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed
        if (this.cbxEstado.getSelectedItem().toString().equals("ANULADO") || this.cbxEstado.getSelectedItem().toString().equals("TERMINADO")) {
            this.btnAgregar.setEnabled(false);
        } else {
            this.btnAgregar.setEnabled(true);
        }
        this.calcular_saldos();
    }//GEN-LAST:event_cbxEstadoActionPerformed

    private void dccFechaPagoOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dccFechaPagoOnSelectionChange
        this.calcular_saldos();
    }//GEN-LAST:event_dccFechaPagoOnSelectionChange

    private void cargar_promesas_tabla() {
        DefaultTableModel modelo = (DefaultTableModel) this.TblPromesasPago.getModel();
        
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        for (Integer i = 0; i < this.promesas.size(); i++) {
            Integer dia = this.promesas.get(i).getFecha_pago().get(Calendar.DATE);
            Integer mes = this.promesas.get(i).getFecha_pago().get(Calendar.MONTH) + 1;
            Integer ano = this.promesas.get(i).getFecha_pago().get(Calendar.YEAR);
            String fecha_promesa = ano.toString() + "/" + mes.toString() + "/" + dia.toString();

            Object[] fila = new Object[8];
            fila[0] = this.promesas.get(i).getPromesa_pago();
            fila[1] = fecha_promesa;
            fila[2] = this.promesas.get(i).getHora_pago();
            fila[3] = this.promesas.get(i).getEstado_promesa();
            fila[4] = this.promesas.get(i).getMonto();
            fila[5] = this.promesas.get(i).getFecha_creacion();
            fila[6] = this.promesas.get(i).getFecha_anulacion();
            fila[7] = this.promesas.get(i).getFecha_cumplimiento();
            modelo.addRow(fila);
        }
        this.TblPromesasPago.setModel(modelo);

        Integer promesas_pendientes = 0;
        Integer promesas_cumplidas = 0;
        Integer promesas_incumplidas = 0;
        for (Integer i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 3).toString().equals("PENDIENTE")) {
                promesas_pendientes++;
            }
            if (modelo.getValueAt(i, 3).toString().equals("CUMPLIDO")) {
                promesas_cumplidas++;
            }
            if (modelo.getValueAt(i, 3).toString().equals("INCUMPLIDO")) {
                promesas_incumplidas++;
            }
        }
        this.labResPro.setText("Pendientes: " + promesas_pendientes + " Cumplidas: " + promesas_cumplidas + " Incumplidas: " + promesas_incumplidas);
    }
    
    private void calcular_saldos() {
        Double saldo = Double.parseDouble(spnSaldo.getValue().toString());
        Double interes = Double.parseDouble(spnIntereses.getValue().toString());
        Double mora = Double.parseDouble(spnMora.getValue().toString());
        Double gastos_otros = Double.parseDouble(spnGastosOtros.getValue().toString());
        Double rebaja_interes = Double.parseDouble(spnRebajaInteres.getValue().toString());
        Double costas = Double.parseDouble(spnCostas.getValue().toString());
        Double cuota_inicial = Double.parseDouble(spnCuotaInicial.getValue().toString());
        Integer numero_cuotas = Integer.parseInt(spnNoCuotas.getValue().toString());

        Double sub_total = saldo + interes + mora + gastos_otros - rebaja_interes;
        Double monto_costas = sub_total * (costas / 100);
        Double total = sub_total + monto_costas;
        Double total_pagar = total - cuota_inicial;
        Double cuota = total_pagar / numero_cuotas;
        if (cuota.floatValue() > cuota.intValue()) {
            Integer temp = cuota.intValue();
            cuota = temp + 1.00;
        }

        DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
        this.txtSubTotal.setText("Q. " + formatter.format(sub_total));
        this.txtMontoCostas.setText("Q. " + formatter.format(monto_costas));
        this.txtTotal.setText("Q. " + formatter.format(total));
        this.txtTotalPagar.setText("Q. " + formatter.format(total_pagar));
        this.txtCuota.setText("Q. " + formatter.format(cuota));

        Integer dia = this.dccFechaPago.getSelectedDate().get(Calendar.DATE);
        Integer mes = this.dccFechaPago.getSelectedDate().get(Calendar.MONTH) + 1;
        Integer anio = this.dccFechaPago.getSelectedDate().get(Calendar.YEAR);
        String fecha_pago = dia.toString() + "/" + mes.toString() + "/" + anio.toString();

        Date actual = new Date();
        String fecha_actual = "";
        if (this.accion == 0) {
            dia = actual.getDate();
            mes = actual.getMonth() + 1;
            anio = actual.getYear() + 1900;
            fecha_actual = dia.toString() + "/" + mes.toString() + "/" + anio.toString();
        } else {
            if (this.cbxEstado.getSelectedItem().equals("ACTIVO")) {
                dia = actual.getDate();
                mes = actual.getMonth() + 1;
                anio = actual.getYear() + 1900;
                fecha_actual = dia.toString() + "/" + mes.toString() + "/" + anio.toString();
            } else {
                try {
                    com.lexcom.driver.Convenio drive = new com.lexcom.driver.Convenio(this.conn, this.usuario);
                    drive.obtener(this.seleccion);
                    Calendar fecha_creacion_t = drive.fecha_activacion;
                    actual = fecha_creacion_t.getTime();
                    dia = actual.getDate();
                    mes = actual.getMonth() + 1;
                    anio = actual.getYear() + 1900;
                    fecha_actual = dia.toString() + "/" + mes.toString() + "/" + anio.toString();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }

        String observacion = "";
        if (this.cbxTipoConvenio.getSelectedItem().equals("NORMAL")) {
            observacion = fecha_actual + " CONVENIO: \n"
                    + "Total Convenio Q. " + formatter.format(total) + ". \n"
                    + "Se pactó pago inicial de Q. " + formatter.format(cuota_inicial) + " y \n"
                    + numero_cuotas + " cuotas " + this.cbxFrecuencia.getSelectedItem().toString() + " de Q. " + formatter.format(cuota) + ". \n"
                    + "Se inician pagos el " + fecha_pago + ".";
        }
        if (this.cbxTipoConvenio.getSelectedItem().equals("CANCELACION TOTAL")) {
            observacion = fecha_actual + " CONVENIO: \n"
                    + "Total Convenio Q. " + formatter.format(total) + ". \n"
                    + "Saldo Q. " + formatter.format(saldo) + " \n"
                    + "Rebaja Autorizada de Q. " + formatter.format(rebaja_interes) + " \n"
                    + "Costas Q. " + formatter.format(monto_costas) + " \n"
                    + "Cancelación Total Autorizada por Q. " + formatter.format(total) + " \n"
                    + "Pago el " + fecha_pago + ".";
        }
        if (this.cbxTipoConvenio.getSelectedItem().equals("TRANSACCION")) {
            observacion = fecha_actual + " CONVENIO: \n"
                    + "Total Convenio Q. " + formatter.format(total) + ". \n"
                    + "Cancelación Total Transacción Judicial por Q. " + formatter.format(total) + " por medio \n"
                    + "Deudor " + formatter.format(costas) + "% Costas  Q. " + formatter.format(cuota_inicial) + " \n"
                    + "Restante Transacción Q. " + formatter.format(cuota) + " \n"
                    + "Pago el " + fecha_pago + ".";
        }
        if (this.cbxTipoConvenio.getSelectedItem().equals("TEMPORAL")) {
            observacion = fecha_actual + " CONVENIO: \n"
                    + "Total Convenio Q. " + formatter.format(total) + ". \n"
                    + "Se pactaron Cuota Inicial de Q. " + formatter.format(cuota_inicial) + " y \n"
                    + numero_cuotas + " cuotas temporales de Q. " + formatter.format(cuota) + " y pagos " + this.cbxFrecuencia.getSelectedItem().toString() + " \n"
                    + "Se inician pagos el " + fecha_pago + ". \n"
                    + "Al finalizar estos pagos se realizará convenio normal.";
        }
        if (this.cbxTipoConvenio.getSelectedItem().equals("PAGOS SIN CONVENIO")) {
            observacion = fecha_actual + " Pagos Sin Convenio. \n"
                    + "Total Convenio Q. " + formatter.format(total) + ". \n"
                    + "Caso con gestiones Anteriores, \n"
                    + "con Demanda o Sin Demanda (modificar manualmente). \n"
                    + "Cliente ilocalizado o Cliente localizado \n"
                    + "pero no pacto forma de pago.";
        }
        this.areObservacion.setText(observacion);

        if (this.cbxEstado.getSelectedItem().toString().equals("ACTIVO")) {
            this.promesas = new ArrayList<>();
            this.conta_promesas = 0;
            
            if (this.promesas.isEmpty()) {
                if (Double.parseDouble(this.spnCuotaInicial.getValue().toString()) > 0.00) {
                    dia = this.dccFechaPago.getSelectedDate().get(Calendar.DATE);
                    mes = this.dccFechaPago.getSelectedDate().get(Calendar.MONTH);
                    anio = this.dccFechaPago.getSelectedDate().get(Calendar.YEAR);

                    Calendar fecha1 = Calendar.getInstance();
                    fecha1.set(anio, mes, dia);

                    Calendar fecha2 = Calendar.getInstance();
                    fecha2.set(anio, mes, dia);
                    fecha2 = sumar_frecuencia(fecha2);
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Nodo_Convenio_Detalle nodo = new Nodo_Convenio_Detalle(this.conta_promesas, fecha1, "8:00", "PENDIENTE", Double.parseDouble(this.spnCuotaInicial.getValue().toString()), "CUOTA INICIAL.", dateFormat.format(new Date()), "-", "-");
                    this.conta_promesas++;
                    this.promesas.add(nodo);

                    Nodo_Convenio_Detalle nodo1 = new Nodo_Convenio_Detalle(this.conta_promesas, fecha2, "8:00", "PENDIENTE", Double.parseDouble(this.quitar_currency(this.txtCuota.getText())), "PRIMERA CUOTA.", dateFormat.format(new Date()), "-", "-");
                    this.conta_promesas++;
                    this.promesas.add(nodo1);
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Nodo_Convenio_Detalle nodo = new Nodo_Convenio_Detalle(this.conta_promesas, this.dccFechaPago.getSelectedDate(), "8:00", "PENDIENTE", Double.parseDouble(this.quitar_currency(this.txtCuota.getText())), "PRIMERA CUOTA.", dateFormat.format(new Date()), "-", "-");
                    this.conta_promesas++;
                    this.promesas.add(nodo);
                }

                this.cargar_promesas_tabla();
            }
        }
    }
    
    private Calendar sumar_frecuencia(Calendar fecha_param) {
        Calendar resultado = Calendar.getInstance();
        Integer dias = 0;

        if (this.cbxFrecuencia.getSelectedItem().toString().equals("MENSUAL")) {
            dias = 30;
        }
        if (this.cbxFrecuencia.getSelectedItem().toString().equals("QUINCENAL")) {
            dias = 15;
        }
        if (this.cbxFrecuencia.getSelectedItem().toString().equals("SEMANAL")) {
            dias = 7;
        }
        if (this.cbxFrecuencia.getSelectedItem().toString().equals("DIARIO")) {
            dias = 1;
        }
        if (this.cbxFrecuencia.getSelectedItem().toString().equals("MISMO DIA")) {
            dias = 0;
        }

        fecha_param.add(Calendar.DAY_OF_YEAR, dias);
        resultado = fecha_param;

        return resultado;
    }
    
    private void insertar() {
        com.lexcom.driver.Convenio drive = new com.lexcom.driver.Convenio(this.conn, this.usuario);
        
        String resultado = drive.insertar(
                this.deudor,                                                                            // deudor              Integer deudor,
                this.cbxTipoConvenio.getSelectedItem().toString(),                                      // tipo_convenio       String tipo_convenio,
                this.cbxEstado.getSelectedItem().toString(),                                            // estado              String estado,
                Double.parseDouble(this.spnSaldo.getValue().toString()),                                // saldo               Double saldo,
                Double.parseDouble(this.spnIntereses.getValue().toString()),                            // interes             Double interes,
                Double.parseDouble(this.spnMora.getValue().toString()),                                 // mora                Double mora,
                Double.parseDouble(this.spnGastosOtros.getValue().toString()),                          // gasto_otros         Double gasto_otros,
                Double.parseDouble(this.spnRebajaInteres.getValue().toString()),                        // rebaja_interes      Double rebaja_interes,
                Double.parseDouble(this.quitar_currency(this.txtSubTotal.getText())),                   // subtotal_pagar      Double subtotal_pagar,
                Double.parseDouble(this.spnCostas.getValue().toString()),                               // costas              Double costas,
                Double.parseDouble(this.quitar_currency(this.txtMontoCostas.getText())),                // monto_costas        Double monto_costas,
                Double.parseDouble(this.quitar_currency(this.txtTotal.getText())),                      // total               Double total,
                Double.parseDouble(this.spnCuotaInicial.getValue().toString()),                         // cuota_inicial       Double cuota_inicial,
                Double.parseDouble(this.quitar_currency(this.txtTotalPagar.getText())),                 // total_pagar         Double total_pagar,
                Integer.parseInt(this.spnNoCuotas.getValue().toString()),                               // numero_cuotas       Integer numero_cuotas,
                Double.parseDouble(this.quitar_currency(this.txtCuota.getText())),                      // monto_cuota         Double monto_cuota,
                this.cbxFrecuencia.getSelectedItem().toString(),                                        // frecuencia          String frecuencia,
                this.dccFechaPago.getSelectedDate(),                                                    // fecha_pago_inicial  Calendar fecha_pago_inicial,
                this.areObservacion.getText(),                                                          // observacion         String observacion,
                this.promesas);                                                                         // promesas            List<Nodo_Convenio_Detalle> promesas
        String[] mensaje = resultado.split(",");
        JOptionPane.showMessageDialog(null, mensaje[1]);
        if(mensaje[0].equals("1")) {
            this.dispose();
        }
    }
    
    private void modificar() {
        Integer opcion1 = -1;
        switch (this.cbxEstado.getSelectedItem().toString()) {
            case "ACTIVO": {
                opcion1 = JOptionPane.showConfirmDialog(null, "¿Seguro desea guardar el Convenio de Pago? Revise los datos del convenio y las anotaciones adicionales manuales en el campo de 'Observaciones'.", "Convenio de pago.", 0);
                break;
            }
            case "ANULADO": {
                opcion1 = JOptionPane.showConfirmDialog(null, "¿Está seguro de Anular el Convenio de pago?", "Convenio de pago.", 0);
                break;
            }
            case "TERMINADO": {
                opcion1 = JOptionPane.showConfirmDialog(null, "¿Está seguro de Terminar el Convenio de pago?", "Convenio de pago.", 0);
                break;
            }
            default: {
                opcion1 = 0;
                break;
            }
        }

        if (opcion1 == 0) {
            com.lexcom.driver.Convenio drive = new com.lexcom.driver.Convenio(this.conn, this.usuario);
            String resultado = drive.modificar(
                    this.seleccion,                                                          // convenio            Integer convenio,
                    this.deudor,                                                             // deudor              Integer deudor,
                    this.cbxTipoConvenio.getSelectedItem().toString(),                       // tipo_convenio       String tipo_convenio,
                    this.cbxEstado.getSelectedItem().toString(),                             // estado              String estado,
                    Double.parseDouble(this.spnSaldo.getValue().toString()),                 // saldo               Double saldo,
                    Double.parseDouble(this.spnIntereses.getValue().toString()),             // interes             Double interes,
                    Double.parseDouble(this.spnMora.getValue().toString()),                  // mora                Double mora,
                    Double.parseDouble(this.spnGastosOtros.getValue().toString()),           // gasto_otros         Double gasto_otros,
                    Double.parseDouble(this.spnRebajaInteres.getValue().toString()),         // rebaja_interes      Double rebaja_interes,
                    Double.parseDouble(this.quitar_currency(this.txtSubTotal.getText())),    // subtotal_pagar      Double subtotal_pagar,
                    Double.parseDouble(this.spnCostas.getValue().toString()),                // costas              Double costas,
                    Double.parseDouble(this.quitar_currency(this.txtMontoCostas.getText())), // monto_costas        Double monto_costas,
                    Double.parseDouble(this.quitar_currency(this.txtTotal.getText())),       // total               Double total,
                    Double.parseDouble(this.spnCuotaInicial.getValue().toString()),          // cuota_inicial       Double cuota_inicial,
                    Double.parseDouble(this.quitar_currency(this.txtTotalPagar.getText())),  // total_pagar         Double total_pagar,
                    Integer.parseInt(this.spnNoCuotas.getValue().toString()),                // numero_cuotas       Integer numero_cuotas,
                    Double.parseDouble(this.quitar_currency(this.txtCuota.getText())),       // monto_cuota         Double monto_cuota,
                    this.cbxFrecuencia.getSelectedItem().toString(),                         // frecuencia          String frecuencia,
                    this.dccFechaPago.getSelectedDate(),                                     // fecha_pago_inicial  Calendar fecha_pago_inicial,
                    this.areObservacion.getText(),                                           // observacion         String observacion,
                    this.promesas);                                                          // promesas            List<Nodo_Convenio_Detalle> promesas
            String[] mensaje = resultado.split(",");
            JOptionPane.showMessageDialog(null, mensaje[1]);
            if (mensaje[0].equals("1")) {
                this.dispose();
            }
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Convenio drive = new com.lexcom.driver.Convenio(this.conn, this.usuario);
        com.lexcom.driver.Convenio resultado = drive.obtener(seleccion);
        DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
        
        this.cbxTipoConvenio.setSelectedItem(resultado.tipo_convenio);
        this.cbxEstado.setSelectedItem(resultado.estado);
        this.spnSaldo.setValue(resultado.saldo);
        this.spnIntereses.setValue(resultado.interes);
        this.spnMora.setValue(resultado.mora);
        this.spnGastosOtros.setValue(resultado.gasto_otros);
        this.spnRebajaInteres.setValue(resultado.rebaja_interes);
        this.txtSubTotal.setText("Q. " + formatter.format(resultado.subtotal_pagar));
        this.spnCostas.setValue(resultado.costas);
        this.txtMontoCostas.setText("Q. " + formatter.format(resultado.monto_costas));
        this.txtTotal.setText("Q. " + formatter.format(resultado.total));
        this.spnCuotaInicial.setValue(resultado.cuota_inicial);
        this.txtTotalPagar.setText("Q. " + formatter.format(resultado.total_pagar));
        this.spnNoCuotas.setValue(resultado.numero_cuotas);
        this.txtCuota.setText("Q. " + formatter.format(resultado.monto_cuota));
        this.cbxFrecuencia.setSelectedItem(resultado.frecuencia);
        
        this.dccFechaPago.setEnabled(true);
        this.dccFechaPago.setSelectedDate(resultado.fecha_pago_inicial);
        this.areObservacion.setText(resultado.observacion);
        
        this.promesas = resultado.promesas;
        this.cargar_promesas_tabla();
        this.conta_promesas = this.promesas.size();

        if(this.cbxEstado.getSelectedItem().toString().equals("NEGOCIACION")) {
            this.cbxTipoConvenio.setEnabled(true);
            this.spnSaldo.setEnabled(true);
            this.spnIntereses.setEnabled(true);
            this.spnMora.setEnabled(true);
            this.spnGastosOtros.setEnabled(true);
            this.spnRebajaInteres.setEnabled(true);
            this.spnCostas.setEnabled(true);
            this.spnCuotaInicial.setEnabled(true);
            this.spnNoCuotas.setEnabled(true);
            this.cbxFrecuencia.setEnabled(true);
            this.dccFechaPago.setEnabled(true);
            this.areObservacion.setEditable(true);
            this.btnAgregar.setEnabled(false);
            this.btnQuitar.setEnabled(false);
        } else {
            if(this.cbxEstado.getSelectedItem().toString().equals("ACTIVO")) {
                this.cbxTipoConvenio.setEnabled(false);
                this.spnSaldo.setEnabled(false);
                this.spnIntereses.setEnabled(false);
                this.spnMora.setEnabled(false);
                this.spnGastosOtros.setEnabled(false);
                this.spnRebajaInteres.setEnabled(false);
                this.spnCostas.setEnabled(false);
                this.spnCuotaInicial.setEnabled(false);
                this.spnNoCuotas.setEnabled(false);
                this.cbxFrecuencia.setEnabled(false);
                this.dccFechaPago.setEnabled(false);
                this.areObservacion.setEditable(false);

                this.btnAgregar.setEnabled(true);
                this.btnQuitar.setEnabled(false);

                this.cbxEstado.removeItemAt(0);
            } else {
                this.cbxTipoConvenio.setEnabled(false);
                this.cbxEstado.setEnabled(false);
                this.spnSaldo.setEnabled(false);
                this.spnIntereses.setEnabled(false);
                this.spnMora.setEnabled(false);
                this.spnGastosOtros.setEnabled(false);
                this.spnRebajaInteres.setEnabled(false);
                this.spnCostas.setEnabled(false);
                this.spnCuotaInicial.setEnabled(false);
                this.spnNoCuotas.setEnabled(false);
                this.cbxFrecuencia.setEnabled(false);
                this.dccFechaPago.setEnabled(false);
                this.areObservacion.setEditable(false);

                this.btnAgregar.setEnabled(false);
                this.btnQuitar.setEnabled(false);

                this.cbxEstado.removeItemAt(0);
            }
        }
    }
    
    public void cargar_estado(boolean b_estado) {
        this.cbxEstado.setEnabled(b_estado);
        
        this.btnAgregar.setEnabled(false);
        this.btnQuitar.setEnabled(false);
    }
    
    public void habilitar_boton_guardar(Boolean opcion) {
        this.btnAceptar.setVisible(opcion);
    }
    
    private String quitar_currency(String dato) {
        String resultado = "";
        
        resultado = dato.replaceAll(",", "");
        resultado = resultado.replaceAll("Q. ", "");
        
        return resultado;
    }
    
    public void set_saldo(Double saldo) {
        this.spnSaldo.setValue(saldo);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblPromesasPago;
    private javax.swing.JTextArea areObservacion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JComboBox cbxFrecuencia;
    private javax.swing.JComboBox cbxTipoConvenio;
    private datechooser.beans.DateChooserCombo dccFechaPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labResPro;
    private javax.swing.JSpinner spnCostas;
    private javax.swing.JSpinner spnCuotaInicial;
    private javax.swing.JSpinner spnGastosOtros;
    private javax.swing.JSpinner spnIntereses;
    private javax.swing.JSpinner spnMora;
    private javax.swing.JSpinner spnNoCuotas;
    private javax.swing.JSpinner spnRebajaInteres;
    private javax.swing.JSpinner spnSaldo;
    private javax.swing.JTextField txtCuota;
    private javax.swing.JTextField txtMontoCostas;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalPagar;
    // End of variables declaration//GEN-END:variables

}
