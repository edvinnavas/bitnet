package com.lexcom;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

public class Expediente extends javax.swing.JFrame implements Runnable {

    Connection conn;
    Integer usuario;
    Integer deudor;
    String gestion_actual;
    Thread hilo;

    public Expediente(Connection conn, Integer usuario) {
        initComponents();
        this.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        //////////////////////////////////////////////////////////////////////////////////////////////////
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        this.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        //////////////////////////////////////////////////////////////////////////////////////////////////
        this.conn = conn;
        this.usuario = usuario;
        this.gestion_actual = "";
        // this.setExtendedState(Expediente.MAXIMIZED_BOTH);

        com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
        this.cbxActor.setModel(DActor.dar_lista());

        com.lexcom.driver.Usuario DGestor = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        this.cbxGestor.setModel(DGestor.dar_lista_gestores());

        com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
        this.cbxGarantia.setModel(DGarantia.dar_lista());

        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
        this.cbxJuzgado.setModel(DJuzgado.dar_lista());

        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        this.cbxProcurador.setModel(DProcurador.dar_lista_procuradores());
        
        com.lexcom.driver.Intencion_Pago DIntencion_Pago = new com.lexcom.driver.Intencion_Pago(this.conn, this.usuario);
        this.cbxIntencionPago.setModel(DIntencion_Pago.dar_lista());
        
        com.lexcom.driver.Razon_Deuda DRazon_Deuda = new com.lexcom.driver.Razon_Deuda(this.conn, this.usuario);
        this.cbxRazonDeuda.setModel(DRazon_Deuda.dar_lista());
        
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        this.cbxEstado.setModel(DEstado.dar_lista_comb());

        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatus.setModel(DStatus.dar_lista_comb(this.cbxEstado.getSelectedItem().toString()));
        
        com.lexcom.driver.EstadoExtra DEstadoExtra = new com.lexcom.driver.EstadoExtra(this.conn, this.usuario);
        this.cbxEstadoExtra.setModel(DEstadoExtra.dar_lista_comb());

        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        this.cbxStatusExtra.setModel(DStatusExtra.dar_lista_comb(this.cbxEstadoExtra.getSelectedItem().toString()));
        
        com.lexcom.driver.Antiguedad DAntiguedad = new com.lexcom.driver.Antiguedad(this.conn, this.usuario);
        this.cbxAntiguedad.setModel(DAntiguedad.dar_lista());
        
        com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
        this.cbxTipoJuicio.setModel(DTipoJuicio.dar_lista());
        
        com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);
        this.cbxTituloEjecutivo.setModel(DTituloEjecutivo.dar_lista());
        
        // EDITORES DE FECHA PARA LOS JSPINNER.
        
        JSpinner.DateEditor spinnerDateEditor1 = (JSpinner.DateEditor) this.dccFechaRecepcion.getEditor();
        spinnerDateEditor1.getFormat().applyPattern("dd/MM/yyyy");
        this.dccFechaRecepcion.setEditor(spinnerDateEditor1);
        
        JSpinner.DateEditor spinnerDateEditor2 = (JSpinner.DateEditor) this.dccFechaProximoPago.getEditor();
        spinnerDateEditor2.getFormat().applyPattern("dd/MM/yyyy");
        this.dccFechaProximoPago.setEditor(spinnerDateEditor2);
        
        JSpinner.DateEditor spinnerDateEditor3 = (JSpinner.DateEditor) this.dccFechaJuicio.getEditor();
        spinnerDateEditor3.getFormat().applyPattern("dd/MM/yyyy");
        this.dccFechaJuicio.setEditor(spinnerDateEditor3);
        
        JSpinner.DateEditor spinnerDateEditor4 = (JSpinner.DateEditor) this.dccFechaAdmision.getEditor();
        spinnerDateEditor4.getFormat().applyPattern("dd/MM/yyyy");
        this.dccFechaAdmision.setEditor(spinnerDateEditor4);
        
        JSpinner.DateEditor spinnerDateEditor5 = (JSpinner.DateEditor) this.dccMemorial.getEditor();
        spinnerDateEditor5.getFormat().applyPattern("dd/MM/yyyy");
        this.dccMemorial.setEditor(spinnerDateEditor5);
        
        JSpinner.DateEditor spinnerDateEditor6 = (JSpinner.DateEditor) this.dccFechaNotificacion.getEditor();
        spinnerDateEditor6.getFormat().applyPattern("dd/MM/yyyy");
        this.dccFechaNotificacion.setEditor(spinnerDateEditor6);
        
        JSpinner.DateEditor spinnerDateEditor7 = (JSpinner.DateEditor) this.dccFechaNac.getEditor();
        spinnerDateEditor7.getFormat().applyPattern("dd/MM/yyyy");
        this.dccFechaNac.setEditor(spinnerDateEditor7);
        
        
        hilo = new Thread(this);
        hilo.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbGestiones = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        txtCaso = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbxActor = new javax.swing.JComboBox();
        chkCuentaPrincipalRelacion = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        spnMontoInicial = new javax.swing.JSpinner();
        spnSaldo = new javax.swing.JSpinner();
        jLabel45 = new javax.swing.JLabel();
        cbxCargado = new javax.swing.JComboBox();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        spnMontoInicial1 = new javax.swing.JSpinner();
        cbxMoneda = new javax.swing.JComboBox();
        spnSaldo1 = new javax.swing.JSpinner();
        jLabel60 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        dccFechaRecepcion = new javax.swing.JSpinner();
        jLabel30 = new javax.swing.JLabel();
        cbxGestor = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        cbxGarantia = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbxAntiguedad = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        txtNoCuenta = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtOtroNoCuenta = new javax.swing.JTextField();
        jScrollPane21 = new javax.swing.JScrollPane();
        areNotas = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        cbxEstadoExtra = new javax.swing.JComboBox();
        cbxStatusExtra = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox();
        cbxStatus = new javax.swing.JComboBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel24 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtTelefonoCelular = new javax.swing.JTextField();
        txtTelefonoCasa = new javax.swing.JTextField();
        txtCorreoElectronico = new javax.swing.JTextField();
        txtLugarTrabajo = new javax.swing.JTextField();
        txtDireccionTrabajo = new javax.swing.JTextField();
        txtTelefonoTrabajo = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        areDireccion = new javax.swing.JTextArea();
        jPanel28 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dccFechaJuicio = new javax.swing.JSpinner();
        cbxProcurador = new javax.swing.JComboBox();
        cbxJuzgado = new javax.swing.JComboBox();
        txtNoJuicio = new javax.swing.JTextField();
        spnNotificador = new javax.swing.JSpinner();
        dccFechaAdmision = new javax.swing.JSpinner();
        spnMontoDemanda = new javax.swing.JSpinner();
        labSumario = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAbogadoDeudor = new javax.swing.JTextField();
        cbxTituloEjecutivo = new javax.swing.JComboBox<>();
        cbxTipoJuicio = new javax.swing.JComboBox<>();
        dccMemorial = new javax.swing.JSpinner();
        cbxNotificado = new javax.swing.JComboBox();
        dccFechaNotificacion = new javax.swing.JSpinner();
        cbxAnticipo = new javax.swing.JComboBox();
        chkPDF = new javax.swing.JCheckBox();
        chkINV = new javax.swing.JCheckBox();
        chkMAYCOM = new javax.swing.JCheckBox();
        chkNITS = new javax.swing.JCheckBox();
        jScrollPane16 = new javax.swing.JScrollPane();
        areDescripcionProcuracion = new javax.swing.JTextArea();
        jLabel53 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        areSituacionCaso = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        areRazonNotificacion = new javax.swing.JTextArea();
        jLabel49 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dccFechaNac = new javax.swing.JSpinner();
        txtDpi = new javax.swing.JTextField();
        txtNit = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        btnPagosAgregar = new javax.swing.JButton();
        btnPagosModificar = new javax.swing.JButton();
        btnPagosEliminar = new javax.swing.JButton();
        btnPagosVer = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        TblPagos = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        btnPagosAgregar1 = new javax.swing.JButton();
        btnPagosModificar1 = new javax.swing.JButton();
        btnPagosEliminar1 = new javax.swing.JButton();
        btnPagosVer1 = new javax.swing.JButton();
        jScrollPane18 = new javax.swing.JScrollPane();
        TblPromesaPago = new javax.swing.JTable();
        jPanel32 = new javax.swing.JPanel();
        btnJuiciosAgregar = new javax.swing.JButton();
        btnJuiciosModificar = new javax.swing.JButton();
        btnJuiciosEliminar = new javax.swing.JButton();
        btnJuiciosVer = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        TblJuicios = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        btnDescuentosAgregar = new javax.swing.JButton();
        btnDescuentosModificar = new javax.swing.JButton();
        btnDescuentosEliminar = new javax.swing.JButton();
        btnDescuentosVer = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        TblDescuentos = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        btnAumentosAgregar = new javax.swing.JButton();
        btnAumentosModificar = new javax.swing.JButton();
        btnAumentosEliminar = new javax.swing.JButton();
        btnAumentosVer = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        TblAumentos = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        btnFiadoresAgregar = new javax.swing.JButton();
        btnFiadoresModificar = new javax.swing.JButton();
        btnFiadoresEliminar = new javax.swing.JButton();
        btnFiadoresVer = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        TblFiadores = new javax.swing.JTable();
        jPanel36 = new javax.swing.JPanel();
        btnReferenciasAgregar = new javax.swing.JButton();
        btnReferenciasModificar = new javax.swing.JButton();
        btnReferenciasEliminar = new javax.swing.JButton();
        btnReferenciasVer = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        TblReferencias = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        btnDigitalizacionVer = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        TblDigitalizacion = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        btnConvenioAgregar = new javax.swing.JButton();
        btnConvenioModificar = new javax.swing.JButton();
        btnConvenioEliminar = new javax.swing.JButton();
        btnConvenioVer = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        TblConvenio = new javax.swing.JTable();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        TblCuentaRelacionada = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel22 = new javax.swing.JPanel();
        lbNoGestionesCobros = new javax.swing.JLabel();
        btnGestionInsertar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblGestion = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        areAdministracionDescripcion = new javax.swing.JTextArea();
        btnAdministracionInsertar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        TblAdministracion = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        areJuridicoDescripcion = new javax.swing.JTextArea();
        btnJuridicoInsertar = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        TblJuridico = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPagos = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtFechaUltimoPago = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtMontoUltimoPago = new javax.swing.JTextField();
        chkProximoPago = new javax.swing.JCheckBox();
        jLabel37 = new javax.swing.JLabel();
        dccFechaProximoPago = new javax.swing.JSpinner();
        jLabel46 = new javax.swing.JLabel();
        spnCuotaConvenio = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        cbxIntencionPago = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        cbxRazonDeuda = new javax.swing.JComboBox();
        jPanel23 = new javax.swing.JPanel();
        btnConvenio = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txtTipoConvenio = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        AreConvenioPactado = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EXPEDIENTE");

        lbGestiones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbGestiones.setForeground(new java.awt.Color(255, 0, 0));
        lbGestiones.setText("GESTIONES");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel5.setPreferredSize(new java.awt.Dimension(1220, 1205));

        jLabel41.setText("Caso");

        txtCaso.setEditable(false);
        txtCaso.setBackground(new java.awt.Color(-256,true));
        txtCaso.setText("0");

        jLabel6.setText("Nombre deudor");

        txtNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel1.setText("Actor");

        chkCuentaPrincipalRelacion.setText("Cuenta principal relación");
        chkCuentaPrincipalRelacion.setEnabled(false);

        jLabel25.setText("QUETZALES");

        jLabel23.setText("Moneda");

        jLabel34.setText("Saldo");

        spnMontoInicial.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        spnSaldo.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        jLabel45.setText("Cargado");

        cbxCargado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CARGADO", "DESCARGADO" }));
        cbxCargado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCargadoActionPerformed(evt);
            }
        });

        jLabel56.setText("Monto Inicial");

        jLabel57.setText("Monto Inicial");

        jLabel59.setText("Saldo");

        spnMontoInicial1.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "QUETZAL", "DOLLAR" }));

        spnSaldo1.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        jLabel60.setText("DOLARES");

        jLabel18.setText("Fecha Recepción");

        dccFechaRecepcion.setModel(new javax.swing.SpinnerDateModel());

        jLabel30.setText("Gestor");

        jLabel29.setText("Garantía");

        jLabel3.setText("Antigüedad");

        jLabel28.setText("No. Cuenta");

        jLabel38.setText("Otro No. Cuenta");

        areNotas.setColumns(20);
        areNotas.setLineWrap(true);
        areNotas.setRows(5);
        jScrollPane21.setViewportView(areNotas);

        jLabel31.setText("Notas");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Situación extrajudicial"));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(51, 51, 255));
        jLabel50.setText("Estado");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(51, 51, 255));
        jLabel51.setText("Status");

        cbxEstadoExtra.setForeground(new java.awt.Color(51, 51, 255));
        cbxEstadoExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoExtraActionPerformed(evt);
            }
        });

        cbxStatusExtra.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(cbxEstadoExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxStatusExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstadoExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxStatusExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Situación judicial"));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 153, 0));
        jLabel26.setText("Estado");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 153, 0));
        jLabel27.setText("Status");

        cbxEstado.setForeground(new java.awt.Color(0, 153, 0));
        cbxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoActionPerformed(evt);
            }
        });

        cbxStatus.setForeground(new java.awt.Color(0, 153, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jLabel15.setText("Teléfono casa");

        jLabel9.setText("Contacto principal");

        jLabel20.setText("Correo electrónico");

        jLabel22.setText("Lugar de trabajo");

        jLabel21.setText("Contacto trabajo");

        jLabel24.setText("Teléfono trabajo");

        jLabel52.setText("Dirección");

        areDireccion.setColumns(20);
        areDireccion.setLineWrap(true);
        areDireccion.setRows(5);
        jScrollPane22.setViewportView(areDireccion);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel20)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel52))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLugarTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefonoTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccionTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(553, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtLugarTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtDireccionTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtTelefonoTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane22))
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Contacto", jPanel24);

        jLabel13.setText("Procurador");

        jLabel16.setText("Fecha presentación");

        jLabel17.setText("Juzgado");

        jLabel19.setText("No. Juicio");

        jLabel39.setText("Notificador");

        jLabel54.setText("Fecha admisión");

        jLabel5.setText("Monto Demanda");

        dccFechaJuicio.setModel(new javax.swing.SpinnerDateModel());

        dccFechaAdmision.setModel(new javax.swing.SpinnerDateModel());

        spnMontoDemanda.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        labSumario.setText("Tipo Juicio");

        jLabel55.setText("Título ejecutivo");

        jLabel40.setText("Abogado Deudor");

        jLabel42.setText("Memorial");

        jLabel47.setText("Notificado");

        jLabel48.setText("Fecha Notif.");

        jLabel4.setText("Anticipo");

        cbxTituloEjecutivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxTipoJuicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        dccMemorial.setModel(new javax.swing.SpinnerDateModel());

        cbxNotificado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO", "D INCORRECTA", "NADIE ATIENDE", "NO VIVE LUGAR", "IMPOSIBLE", "FUERA PAIS", "FALLECIDO" }));

        dccFechaNotificacion.setModel(new javax.swing.SpinnerDateModel());

        cbxAnticipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO", "SI", "COBRAR", "REPRE" }));

        chkPDF.setText("PDF");

        chkINV.setText("INV");

        chkMAYCOM.setText("MAY");

        chkNITS.setText("NIT");

        areDescripcionProcuracion.setColumns(20);
        areDescripcionProcuracion.setLineWrap(true);
        areDescripcionProcuracion.setRows(5);
        areDescripcionProcuracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areDescripcionProcuracionKeyReleased(evt);
            }
        });
        jScrollPane16.setViewportView(areDescripcionProcuracion);

        jLabel53.setText("Procuración");

        jLabel14.setText("Situación caso");

        areSituacionCaso.setColumns(20);
        areSituacionCaso.setLineWrap(true);
        areSituacionCaso.setRows(5);
        areSituacionCaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areSituacionCasoKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(areSituacionCaso);

        areRazonNotificacion.setColumns(20);
        areRazonNotificacion.setLineWrap(true);
        areRazonNotificacion.setRows(5);
        jScrollPane17.setViewportView(areRazonNotificacion);

        jLabel49.setText("Razón Notificación");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel39)
                    .addComponent(jLabel54)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spnNotificador, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNoJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxJuzgado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dccFechaJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dccFechaAdmision, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnMontoDemanda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel4))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAbogadoDeudor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dccMemorial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxTituloEjecutivo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxNotificado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxTipoJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dccFechaNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(labSumario)
                            .addComponent(jLabel55)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(cbxAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(chkPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkINV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkMAYCOM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNITS)))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel53)
                            .addComponent(jLabel14))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbxProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labSumario)
                    .addComponent(cbxTipoJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(dccFechaJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)
                            .addComponent(cbxTituloEjecutivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(cbxJuzgado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(txtAbogadoDeudor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtNoJuicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(dccMemorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(spnNotificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47)
                            .addComponent(cbxNotificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(dccFechaAdmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48)
                            .addComponent(dccFechaNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(spnMontoDemanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cbxAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkPDF)
                            .addComponent(chkINV)
                            .addComponent(chkMAYCOM)
                            .addComponent(chkNITS)))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Judicial", jPanel28);

        jLabel10.setText("Fecha Nac.");

        jLabel8.setText("DPI");

        jLabel7.setText("Nit");

        dccFechaNac.setModel(new javax.swing.SpinnerDateModel());

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDpi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dccFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(950, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(dccFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(279, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Perfíl", jPanel31);

        btnPagosAgregar.setText("Agregar");
        btnPagosAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosAgregarActionPerformed(evt);
            }
        });

        btnPagosModificar.setText("Modificar");
        btnPagosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosModificarActionPerformed(evt);
            }
        });

        btnPagosEliminar.setText("Eliminar");
        btnPagosEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosEliminarActionPerformed(evt);
            }
        });

        btnPagosVer.setText("Ver");
        btnPagosVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosVerActionPerformed(evt);
            }
        });

        TblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PAGO", "BANCO", "FECHA", "MONTO", "NO_BOLETA", "FECHA_REGISTRO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblPagos.setFillsViewportHeight(true);
        TblPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblPagosMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(TblPagos);
        if (TblPagos.getColumnModel().getColumnCount() > 0) {
            TblPagos.getColumnModel().getColumn(5).setHeaderValue("FECHA_REGISTRO");
        }

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(btnPagosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagosAgregar)
                    .addComponent(btnPagosModificar)
                    .addComponent(btnPagosEliminar)
                    .addComponent(btnPagosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Pagos", jPanel27);

        btnPagosAgregar1.setText("Agregar");
        btnPagosAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagosAgregar1ActionPerformed(evt);
            }
        });

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

        TblPromesaPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RECORDATORIO", "FECHA INGRESO", "ALERTA", "ESTADO PROMESA", "MONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblPromesaPago.setFillsViewportHeight(true);
        TblPromesaPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblPromesaPagoMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(TblPromesaPago);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(btnPagosAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosModificar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagosVer1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagosAgregar1)
                    .addComponent(btnPagosModificar1)
                    .addComponent(btnPagosEliminar1)
                    .addComponent(btnPagosVer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Recordatorios", jPanel30);

        btnJuiciosAgregar.setText("Agregar");
        btnJuiciosAgregar.setEnabled(false);
        btnJuiciosAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuiciosAgregarActionPerformed(evt);
            }
        });

        btnJuiciosModificar.setText("Modificar");
        btnJuiciosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuiciosModificarActionPerformed(evt);
            }
        });

        btnJuiciosEliminar.setText("Eliminar");
        btnJuiciosEliminar.setEnabled(false);
        btnJuiciosEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuiciosEliminarActionPerformed(evt);
            }
        });

        btnJuiciosVer.setText("Ver");
        btnJuiciosVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuiciosVerActionPerformed(evt);
            }
        });

        TblJuicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "JUICIO", "GUZGADO", "FECHA", "NO_JUICIO", "MONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblJuicios.setFillsViewportHeight(true);
        TblJuicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblJuiciosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TblJuicios);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(btnJuiciosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnJuiciosModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnJuiciosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnJuiciosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnJuiciosAgregar)
                    .addComponent(btnJuiciosModificar)
                    .addComponent(btnJuiciosEliminar)
                    .addComponent(btnJuiciosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Juicio", jPanel32);

        btnDescuentosAgregar.setText("Agregar");
        btnDescuentosAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentosAgregarActionPerformed(evt);
            }
        });

        btnDescuentosModificar.setText("Modificar");
        btnDescuentosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentosModificarActionPerformed(evt);
            }
        });

        btnDescuentosEliminar.setText("Eliminar");
        btnDescuentosEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentosEliminarActionPerformed(evt);
            }
        });

        btnDescuentosVer.setText("Ver");
        btnDescuentosVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentosVerActionPerformed(evt);
            }
        });

        TblDescuentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DESCUENTO", "TIPO_DESCUENTO", "FECHA", "MONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblDescuentos.setFillsViewportHeight(true);
        TblDescuentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblDescuentosMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(TblDescuentos);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(btnDescuentosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDescuentosModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDescuentosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDescuentosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDescuentosAgregar)
                    .addComponent(btnDescuentosModificar)
                    .addComponent(btnDescuentosEliminar)
                    .addComponent(btnDescuentosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Descuentos", jPanel33);

        btnAumentosAgregar.setText("Agregar");
        btnAumentosAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumentosAgregarActionPerformed(evt);
            }
        });

        btnAumentosModificar.setText("Modificar");
        btnAumentosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumentosModificarActionPerformed(evt);
            }
        });

        btnAumentosEliminar.setText("Eliminar");
        btnAumentosEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumentosEliminarActionPerformed(evt);
            }
        });

        btnAumentosVer.setText("Ver");
        btnAumentosVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumentosVerActionPerformed(evt);
            }
        });

        TblAumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AUMENTO", "TIPO_AUMENTO", "FECHA", "MONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblAumentos.setFillsViewportHeight(true);
        TblAumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblAumentosMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(TblAumentos);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(btnAumentosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAumentosModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAumentosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAumentosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAumentosAgregar)
                    .addComponent(btnAumentosModificar)
                    .addComponent(btnAumentosEliminar)
                    .addComponent(btnAumentosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Aumentos", jPanel34);

        btnFiadoresAgregar.setText("Agregar");
        btnFiadoresAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiadoresAgregarActionPerformed(evt);
            }
        });

        btnFiadoresModificar.setText("Modificar");
        btnFiadoresModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiadoresModificarActionPerformed(evt);
            }
        });

        btnFiadoresEliminar.setText("Eliminar");
        btnFiadoresEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiadoresEliminarActionPerformed(evt);
            }
        });

        btnFiadoresVer.setText("Ver");
        btnFiadoresVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiadoresVerActionPerformed(evt);
            }
        });

        TblFiadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FIADOR", "DPI", "NIT", "NOMBRE", "TELEFONO", "DIRECCION", "ZONA", "CORREO_ELECTRONICO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblFiadores.setFillsViewportHeight(true);
        TblFiadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblFiadoresMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(TblFiadores);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(btnFiadoresAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiadoresModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiadoresEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiadoresVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFiadoresAgregar)
                    .addComponent(btnFiadoresModificar)
                    .addComponent(btnFiadoresEliminar)
                    .addComponent(btnFiadoresVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Fiadores", jPanel35);

        btnReferenciasAgregar.setText("Agregar");
        btnReferenciasAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReferenciasAgregarActionPerformed(evt);
            }
        });

        btnReferenciasModificar.setText("Modificar");
        btnReferenciasModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReferenciasModificarActionPerformed(evt);
            }
        });

        btnReferenciasEliminar.setText("Eliminar");
        btnReferenciasEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReferenciasEliminarActionPerformed(evt);
            }
        });

        btnReferenciasVer.setText("Ver");
        btnReferenciasVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReferenciasVerActionPerformed(evt);
            }
        });

        TblReferencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "REFERENCIA", "DPI", "NIT", "NOMBRE", "TELEFONO", "DIRECCION", "ZONA", "CORREO_ELECTRONICO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblReferencias.setFillsViewportHeight(true);
        TblReferencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblReferenciasMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(TblReferencias);
        if (TblReferencias.getColumnModel().getColumnCount() > 0) {
            TblReferencias.getColumnModel().getColumn(3).setHeaderValue("NOMBRE");
            TblReferencias.getColumnModel().getColumn(4).setHeaderValue("TELEFONO");
            TblReferencias.getColumnModel().getColumn(5).setHeaderValue("DIRECCION");
            TblReferencias.getColumnModel().getColumn(6).setHeaderValue("ZONA");
            TblReferencias.getColumnModel().getColumn(7).setHeaderValue("CORREO_ELECTRONICO");
        }

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(btnReferenciasAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReferenciasModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReferenciasEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReferenciasVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReferenciasAgregar)
                    .addComponent(btnReferenciasModificar)
                    .addComponent(btnReferenciasEliminar)
                    .addComponent(btnReferenciasVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Referencias", jPanel36);

        btnDigitalizacionVer.setText("Ver");
        btnDigitalizacionVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigitalizacionVerActionPerformed(evt);
            }
        });

        TblDigitalizacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "INDICE", "NOMBRE", "PATH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblDigitalizacion.setFillsViewportHeight(true);
        TblDigitalizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblDigitalizacionMouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(TblDigitalizacion);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(btnDigitalizacionVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDigitalizacionVer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Digitalización", jPanel37);

        btnConvenioAgregar.setText("Agregar");
        btnConvenioAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvenioAgregarActionPerformed(evt);
            }
        });

        btnConvenioModificar.setText("Modificar");
        btnConvenioModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvenioModificarActionPerformed(evt);
            }
        });

        btnConvenioEliminar.setText("Eliminar");
        btnConvenioEliminar.setEnabled(false);
        btnConvenioEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvenioEliminarActionPerformed(evt);
            }
        });

        btnConvenioVer.setText("Ver");
        btnConvenioVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvenioVerActionPerformed(evt);
            }
        });

        TblConvenio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id convenio", "Tipo", "Estado", "F. negociación", "F. activación", "F. terminación", "Total deuda", "No. cuotas", "Frecuencia", "Cuota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblConvenio.setFillsViewportHeight(true);
        TblConvenio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblConvenioMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(TblConvenio);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(btnConvenioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConvenioModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConvenioEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConvenioVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConvenioAgregar)
                    .addComponent(btnConvenioModificar)
                    .addComponent(btnConvenioEliminar)
                    .addComponent(btnConvenioVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Convenios", jPanel38);

        TblCuentaRelacionada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID DEUDOR", "ACTOR", "CASO", "NOMBRE", "GESTOR", "GARANTIA", "NO CUENTA", "CARGADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblCuentaRelacionada.setFillsViewportHeight(true);
        jScrollPane23.setViewportView(TblCuentaRelacionada);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Cuenta-Relacionada", jPanel39);

        lbNoGestionesCobros.setText("No. de Gestiones: ");

        btnGestionInsertar.setText("Insertar");
        btnGestionInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionInsertarActionPerformed(evt);
            }
        });

        TblGestion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA", "HORA", "USUARIO", "CODIGO", "CONTACTO", "OBSERVACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblGestion.setFillsViewportHeight(true);
        TblGestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblGestionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TblGestion);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(lbNoGestionesCobros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionInsertar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNoGestionesCobros)
                    .addComponent(btnGestionInsertar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cobros", jPanel22);

        areAdministracionDescripcion.setColumns(20);
        areAdministracionDescripcion.setRows(5);
        jScrollPane7.setViewportView(areAdministracionDescripcion);

        btnAdministracionInsertar.setText("Insertar");
        btnAdministracionInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdministracionInsertarActionPerformed(evt);
            }
        });

        TblAdministracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA", "HORA", "USUARIO", "CODIGO", "OBSERVACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblAdministracion.setFillsViewportHeight(true);
        TblAdministracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblAdministracionMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TblAdministracion);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdministracionInsertar))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdministracionInsertar)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Administración", jPanel25);

        areJuridicoDescripcion.setColumns(20);
        areJuridicoDescripcion.setRows(5);
        jScrollPane9.setViewportView(areJuridicoDescripcion);

        btnJuridicoInsertar.setText("Insertar");
        btnJuridicoInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuridicoInsertarActionPerformed(evt);
            }
        });

        TblJuridico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FECHA", "HORA", "USUARIO", "CODIGO", "OBSERVACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblJuridico.setFillsViewportHeight(true);
        TblJuridico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblJuridicoMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TblJuridico);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jScrollPane9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnJuridicoInsertar))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnJuridicoInsertar)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Jurídico", jPanel26);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagos"));

        jLabel2.setText("Pagos");

        txtPagos.setText("Q. 0.00");

        jLabel35.setText("Fecha Último Pago");

        txtFechaUltimoPago.setText("-");

        jLabel36.setText("Monto Último Pago");

        txtMontoUltimoPago.setText("Q. 0.00");

        chkProximoPago.setText("Próximo Pago");
        chkProximoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkProximoPagoItemStateChanged(evt);
            }
        });

        jLabel37.setText("Fecha Próximo Pago");

        dccFechaProximoPago.setModel(new javax.swing.SpinnerDateModel());

        jLabel46.setText("Cuota Pactada");

        spnCuotaConvenio.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        jLabel12.setText("Intención pago");

        jLabel44.setText("Razón deuda");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(chkProximoPago)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel46)
                            .addComponent(jLabel12)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnCuotaConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dccFechaProximoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMontoUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxIntencionPago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxRazonDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtFechaUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtMontoUltimoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkProximoPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(dccFechaProximoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(spnCuotaConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxIntencionPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxRazonDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Convenio"));

        btnConvenio.setText("Convenio");
        btnConvenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvenioActionPerformed(evt);
            }
        });

        jButton2.setText("Recordatorio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel43.setText("Convenio pactado");

        txtTipoConvenio.setEditable(false);
        txtTipoConvenio.setBackground(new java.awt.Color(-256,true));

        AreConvenioPactado.setColumns(20);
        AreConvenioPactado.setLineWrap(true);
        AreConvenioPactado.setRows(5);
        jScrollPane2.setViewportView(AreConvenioPactado);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(btnConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConvenio)
                    .addComponent(jButton2)
                    .addComponent(jLabel43)
                    .addComponent(txtTipoConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chkCuentaPrincipalRelacion)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel6)
                            .addComponent(jLabel41)
                            .addComponent(jLabel1)
                            .addComponent(jLabel29)
                            .addComponent(jLabel3)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(cbxAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel38)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtOtroNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(cbxGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel28)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(txtCaso, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel45)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxCargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxActor, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(dccFechaRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxGestor, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel59)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(spnMontoInicial1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel60)
                                                    .addComponent(jLabel57)))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(spnSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel34)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(spnSaldo1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane2)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(chkCuentaPrincipalRelacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtCaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel60)
                            .addComponent(cbxCargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57)
                            .addComponent(spnMontoInicial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbxActor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59)
                            .addComponent(jLabel34)
                            .addComponent(spnSaldo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(dccFechaRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(cbxGestor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(cbxMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(cbxGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbxAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(txtOtroNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbGestiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1204, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar)
                    .addComponent(jButton1)
                    .addComponent(lbGestiones))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGestionInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionInsertarActionPerformed
        Gestion_Pendiente_Cobros a = new Gestion_Pendiente_Cobros(
                new javax.swing.JFrame(),
                true,
                conn,
                usuario,
                this.deudor,
                this.cbxEstado.getSelectedItem().toString(),
                this.cbxStatus.getSelectedItem().toString(),
                this.cbxEstadoExtra.getSelectedItem().toString(),
                this.cbxStatusExtra.getSelectedItem().toString(),
                this.cbxIntencionPago.getSelectedItem().toString(),
                this.cbxRazonDeuda.getSelectedItem().toString());
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);

        cargar(deudor);
    }//GEN-LAST:event_btnGestionInsertarActionPerformed

    private void btnAdministracionInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdministracionInsertarActionPerformed
        if (!this.areAdministracionDescripcion.getText().equals("")) {
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);

            drive.insertar_btn_administracion(this.deudor, this.usuario, 1, this.areAdministracionDescripcion.getText());
            this.TblAdministracion.setModel(drive.obtener_tabla_administracion((DefaultTableModel) this.TblAdministracion.getModel(), this.deudor));
            this.TblAdministracion.setEnabled(false);
            this.areAdministracionDescripcion.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar una observación.");
        }
    }//GEN-LAST:event_btnAdministracionInsertarActionPerformed

    private void btnJuridicoInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuridicoInsertarActionPerformed
        if (!this.areJuridicoDescripcion.getText().equals("")) {
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);

            drive.insertar_btn_juridico(this.deudor, this.usuario, 1, this.areJuridicoDescripcion.getText());
            this.TblJuridico.setModel(drive.obtener_tabla_juridico((DefaultTableModel) this.TblJuridico.getModel(), this.deudor));
            this.TblJuridico.setEnabled(false);
            this.areJuridicoDescripcion.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar una observación.");
        }
    }//GEN-LAST:event_btnJuridicoInsertarActionPerformed

    private void btnPagosAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosAgregarActionPerformed
        Pago a = new Pago(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblPagos.setModel(drive.obtener_tabla_pago((DefaultTableModel) TblPagos.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_btnPagosAgregarActionPerformed

    private void btnPagosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosModificarActionPerformed
        Integer fila_posicion = this.TblPagos.getSelectedRow();

        if (fila_posicion != -1) {
            Pago a = new Pago(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblPagos.setModel(drive.obtener_tabla_pago((DefaultTableModel) TblPagos.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblPagos.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnPagosModificarActionPerformed

    private void btnPagosEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosEliminarActionPerformed
        Integer fila_posicion = this.TblPagos.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el pago?", "ELIMINACIÓN PAGO", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Pago drive = new com.lexcom.driver.Pago(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblPagos.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblPagos.setModel(drive.obtener_tabla_pago((DefaultTableModel) this.TblPagos.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnPagosEliminarActionPerformed

    private void btnPagosVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosVerActionPerformed
        Integer fila_posicion = this.TblPagos.getSelectedRow();

        if (fila_posicion != -1) {
            Pago a = new Pago(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblPagos.setModel(drive.obtener_tabla_pago((DefaultTableModel) TblPagos.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblPagos.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnPagosVerActionPerformed

    private void btnJuiciosAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuiciosAgregarActionPerformed
        if (TblJuicios.getRowCount() == 1) {
            JOptionPane.showMessageDialog(null, "No se puede ingresar mas de un juicio a un expediente.");
        } else {
            Juicio a = new Juicio(this, false, conn, usuario, 0, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblJuicios.setModel(drive.obtener_tabla_juicio((DefaultTableModel) TblJuicios.getModel(), deudor));
                    cargar(deudor);
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
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
    }//GEN-LAST:event_btnJuiciosAgregarActionPerformed

    private void btnJuiciosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuiciosModificarActionPerformed
        Integer fila_posicion = this.TblJuicios.getSelectedRow();

        if (fila_posicion != -1) {
            Juicio a = new Juicio(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblJuicios.setModel(drive.obtener_tabla_juicio((DefaultTableModel) TblJuicios.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblJuicios.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnJuiciosModificarActionPerformed

    private void btnJuiciosEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuiciosEliminarActionPerformed
        Integer fila_posicion = this.TblJuicios.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el juicio?", "ELIMINACIÓN JUICIO", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Juicio drive = new com.lexcom.driver.Juicio(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblJuicios.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblJuicios.setModel(drive.obtener_tabla_juicio((DefaultTableModel) this.TblJuicios.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnJuiciosEliminarActionPerformed

    private void btnJuiciosVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuiciosVerActionPerformed
        Integer fila_posicion = this.TblJuicios.getSelectedRow();

        if (fila_posicion != -1) {
            Juicio a = new Juicio(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblJuicios.setModel(drive.obtener_tabla_juicio((DefaultTableModel) TblJuicios.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblJuicios.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnJuiciosVerActionPerformed

    private void btnDescuentosAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentosAgregarActionPerformed
        Descuento a = new Descuento(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblDescuentos.setModel(drive.obtener_tabla_descuento((DefaultTableModel) TblDescuentos.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_btnDescuentosAgregarActionPerformed

    private void btnDescuentosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentosModificarActionPerformed
        Integer fila_posicion = this.TblDescuentos.getSelectedRow();

        if (fila_posicion != -1) {
            Descuento a = new Descuento(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblDescuentos.setModel(drive.obtener_tabla_descuento((DefaultTableModel) TblDescuentos.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblDescuentos.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnDescuentosModificarActionPerformed

    private void btnDescuentosEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentosEliminarActionPerformed
        Integer fila_posicion = this.TblDescuentos.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el descuento?", "ELIMINACIÓN DESCUENTO", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Descuento drive = new com.lexcom.driver.Descuento(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblDescuentos.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblDescuentos.setModel(drive.obtener_tabla_descuento((DefaultTableModel) this.TblDescuentos.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnDescuentosEliminarActionPerformed

    private void btnDescuentosVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentosVerActionPerformed
        Integer fila_posicion = this.TblDescuentos.getSelectedRow();

        if (fila_posicion != -1) {
            Descuento a = new Descuento(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblDescuentos.setModel(drive.obtener_tabla_descuento((DefaultTableModel) TblDescuentos.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblDescuentos.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnDescuentosVerActionPerformed

    private void btnAumentosAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumentosAgregarActionPerformed
        Aumento a = new Aumento(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblAumentos.setModel(drive.obtener_tabla_aumento((DefaultTableModel) TblAumentos.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_btnAumentosAgregarActionPerformed

    private void btnAumentosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumentosModificarActionPerformed
        Integer fila_posicion = this.TblAumentos.getSelectedRow();

        if (fila_posicion != -1) {
            Aumento a = new Aumento(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblAumentos.setModel(drive.obtener_tabla_aumento((DefaultTableModel) TblAumentos.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblAumentos.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnAumentosModificarActionPerformed

    private void btnAumentosEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumentosEliminarActionPerformed
        Integer fila_posicion = this.TblAumentos.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el descuento?", "ELIMINACIÓN DESCUENTO", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Aumento drive = new com.lexcom.driver.Aumento(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblAumentos.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblAumentos.setModel(drive.obtener_tabla_aumento((DefaultTableModel) this.TblAumentos.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnAumentosEliminarActionPerformed

    private void btnAumentosVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumentosVerActionPerformed
        Integer fila_posicion = this.TblAumentos.getSelectedRow();

        if (fila_posicion != -1) {
            Aumento a = new Aumento(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblAumentos.setModel(drive.obtener_tabla_aumento((DefaultTableModel) TblAumentos.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblAumentos.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnAumentosVerActionPerformed

    private void btnFiadoresAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiadoresAgregarActionPerformed
        Fiador a = new Fiador(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblFiadores.setModel(drive.obtener_tabla_fiador((DefaultTableModel) TblFiadores.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_btnFiadoresAgregarActionPerformed

    private void btnFiadoresModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiadoresModificarActionPerformed
        Integer fila_posicion = this.TblFiadores.getSelectedRow();

        if (fila_posicion != -1) {
            Fiador a = new Fiador(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblFiadores.setModel(drive.obtener_tabla_fiador((DefaultTableModel) TblFiadores.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblFiadores.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnFiadoresModificarActionPerformed

    private void btnFiadoresEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiadoresEliminarActionPerformed
        Integer fila_posicion = this.TblFiadores.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el fiador?", "ELIMINACIÓN FIADOR", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Fiador drive = new com.lexcom.driver.Fiador(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblFiadores.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblFiadores.setModel(drive.obtener_tabla_fiador((DefaultTableModel) this.TblFiadores.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnFiadoresEliminarActionPerformed

    private void btnFiadoresVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiadoresVerActionPerformed
        Integer fila_posicion = this.TblFiadores.getSelectedRow();

        if (fila_posicion != -1) {
            Fiador a = new Fiador(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblFiadores.setModel(drive.obtener_tabla_fiador((DefaultTableModel) TblFiadores.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblFiadores.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnFiadoresVerActionPerformed

    private void btnReferenciasAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReferenciasAgregarActionPerformed
        Referencia a = new Referencia(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblReferencias.setModel(drive.obtener_tabla_referencia((DefaultTableModel) TblReferencias.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_btnReferenciasAgregarActionPerformed

    private void btnReferenciasModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReferenciasModificarActionPerformed
        Integer fila_posicion = this.TblReferencias.getSelectedRow();

        if (fila_posicion != -1) {
            Referencia a = new Referencia(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblReferencias.setModel(drive.obtener_tabla_referencia((DefaultTableModel) TblReferencias.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblReferencias.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnReferenciasModificarActionPerformed

    private void btnReferenciasEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReferenciasEliminarActionPerformed
        Integer fila_posicion = this.TblReferencias.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el fiador?", "ELIMINACIÓN FIADOR", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Referencia drive = new com.lexcom.driver.Referencia(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblReferencias.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblReferencias.setModel(drive.obtener_tabla_referencia((DefaultTableModel) this.TblReferencias.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnReferenciasEliminarActionPerformed

    private void btnReferenciasVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReferenciasVerActionPerformed
        Integer fila_posicion = this.TblReferencias.getSelectedRow();

        if (fila_posicion != -1) {
            Referencia a = new Referencia(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblReferencias.setModel(drive.obtener_tabla_referencia((DefaultTableModel) TblReferencias.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblReferencias.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnReferenciasVerActionPerformed

    private void TblGestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblGestionMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila = TblGestion.rowAtPoint(evt.getPoint());
            Bitacora_Mensaje a = new Bitacora_Mensaje(this, false);
            a.cargar(TblGestion.getValueAt(fila, 0).toString(), TblGestion.getValueAt(fila, 1).toString(), TblGestion.getValueAt(fila, 2).toString(), TblGestion.getValueAt(fila, 3).toString(), TblGestion.getValueAt(fila, 5).toString(), TblGestion.getValueAt(fila, 4).toString());
            a.setTitle("Bitacora Gestion");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
    }//GEN-LAST:event_TblGestionMouseClicked

    private void TblAdministracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblAdministracionMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila = TblAdministracion.rowAtPoint(evt.getPoint());
            Bitacora_Mensaje a = new Bitacora_Mensaje(this, false);
            a.cargar(TblAdministracion.getValueAt(fila, 0).toString(), TblAdministracion.getValueAt(fila, 1).toString(), TblAdministracion.getValueAt(fila, 2).toString(), TblAdministracion.getValueAt(fila, 3).toString(), TblAdministracion.getValueAt(fila, 4).toString(), "NO");
            a.setTitle("Bitacora Administrativo");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
    }//GEN-LAST:event_TblAdministracionMouseClicked

    private void TblJuridicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblJuridicoMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila = TblJuridico.rowAtPoint(evt.getPoint());
            Bitacora_Mensaje a = new Bitacora_Mensaje(this, false);
            a.cargar(TblJuridico.getValueAt(fila, 0).toString(), TblJuridico.getValueAt(fila, 1).toString(), TblJuridico.getValueAt(fila, 2).toString(), TblJuridico.getValueAt(fila, 3).toString(), TblJuridico.getValueAt(fila, 4).toString(), "NO");
            a.setTitle("Bitacora Juridico");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
    }//GEN-LAST:event_TblJuridicoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);

            com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
            com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(this.conn, this.usuario);
            com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
            com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
            com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
            com.lexcom.driver.Intencion_Pago DIntencion_Pago = new com.lexcom.driver.Intencion_Pago(this.conn, this.usuario);
            com.lexcom.driver.Razon_Deuda DRazon_Deuda = new com.lexcom.driver.Razon_Deuda(this.conn, this.usuario);
            com.lexcom.driver.EstadoExtra DEstadoExtra = new com.lexcom.driver.EstadoExtra(this.conn, this.usuario);
            com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
            com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
            com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);

            // Valida telefono_celular.
            Boolean telefono_celular_valido = false;
            Long numero_celular = new Long(0);
            try {
                numero_celular = Long.parseLong(this.txtTelefonoCelular.getText().trim());
                if (numero_celular == 0 || (numero_celular >= 10000000 && numero_celular <= 99999999)) {
                    telefono_celular_valido = true;
                }
            } catch (Exception ex) {
                telefono_celular_valido = false;
            }

            // Valida telefono_celular y correo_electronico.
            if (telefono_celular_valido == true) {
                if (this.txtCorreoElectronico.getText().trim().equals("-") || drive.validar_correo_electronico(this.txtCorreoElectronico.getText().trim()) == 1) {
                    String PDF = "";
                    if (this.chkPDF.isSelected()) {
                        PDF = "SI";
                    } else {
                        PDF = "NO";
                    }
                    String INV = "";
                    if (this.chkINV.isSelected()) {
                        INV = "SI";
                    } else {
                        INV = "NO";
                    }
                    String MAYCOM = "";
                    if (this.chkMAYCOM.isSelected()) {
                        MAYCOM = "SI";
                    } else {
                        MAYCOM = "NO";
                    }
                    String NITS = "";
                    if (this.chkNITS.isSelected()) {
                        NITS = "SI";
                    } else {
                        NITS = "NO";
                    }
                    String opcion_proximo_pago = "";
                    if (this.chkProximoPago.isSelected()) {
                        opcion_proximo_pago = "SI";
                    } else {
                        opcion_proximo_pago = "NO";
                    }

                    // OMITE LA ACTUALIZACION OBLIGATORIA DE LOS ESTADOS-ESTATUS EXTRAJUDICIAL Y JUDICIAL; ADEMAS, LA INTENCION DE PAGO.
                    // PARA LOS USUARIOS PRODUCRADOR = SI.
                    if (DUsuario.obtener(this.usuario).procurador.equals("NO")) {
                        Gestion_Obligatoria a = new Gestion_Obligatoria(
                                this,
                                true,
                                this.conn,
                                this.usuario,
                                this.cbxEstado.getSelectedItem().toString(),
                                this.cbxStatus.getSelectedItem().toString(),
                                this.cbxEstadoExtra.getSelectedItem().toString(),
                                this.cbxStatusExtra.getSelectedItem().toString(),
                                this.cbxIntencionPago.getSelectedItem().toString(),
                                this.cbxRazonDeuda.getSelectedItem().toString(),
                                this.deudor);
                        a.setTitle("GESTIÓN OBLIGATORIA");
                        Dimension ventana = a.getSize();
                        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                        a.setVisible(true);

                        if (a.estado_judicial != null && a.status_judicial != null && a.estado_extrajudicial != null && a.status_extrajudicial != null && a.intencion_pago != null && a.razon_deuda != null) {
                            this.cbxEstado.setSelectedItem(a.estado_judicial);
                            this.cbxStatus.setSelectedItem(a.status_judicial);
                            this.cbxEstadoExtra.setSelectedItem(a.estado_extrajudicial);
                            this.cbxStatusExtra.setSelectedItem(a.status_extrajudicial);
                            this.cbxIntencionPago.setSelectedItem(a.intencion_pago);
                            this.cbxRazonDeuda.setSelectedItem(a.razon_deuda);
                        }
                    }

                    String resultado = drive.modificar_deudor(
                            this.deudor,
                            DActor.obtener_indice(this.cbxActor.getSelectedItem().toString()),
                            this.cbxMoneda.getSelectedItem().toString(),
                            this.txtDpi.getText(),
                            this.txtNit.getText(),
                            drive.DateToCalendar((Date) this.dccFechaNac.getValue()),
                            this.txtNombre.getText(),
                            this.txtTelefonoCasa.getText(),
                            this.txtTelefonoCelular.getText().trim(),
                            this.areDireccion.getText(),
                            drive.DateToCalendar((Date) this.dccFechaRecepcion.getValue()),
                            this.txtCorreoElectronico.getText().trim(),
                            this.txtLugarTrabajo.getText(),
                            this.txtDireccionTrabajo.getText(),
                            this.txtTelefonoTrabajo.getText(),
                            Double.parseDouble(this.spnMontoInicial.getValue().toString()),
                            DUsuario.obtener_indice(this.cbxGestor.getSelectedItem().toString()),
                            DEstado.obtener_indice(this.cbxEstado.getSelectedItem().toString()),
                            DStatus.obtener_indice(this.cbxStatus.getSelectedItem().toString()),
                            this.txtNoCuenta.getText(),
                            DGarantia.obtener_indice(this.cbxGarantia.getSelectedItem().toString()),
                            this.cbxCargado.getSelectedItem().toString(),
                            "VIGENTE",
                            PDF,
                            INV,
                            MAYCOM,
                            NITS,
                            Double.parseDouble(this.spnSaldo.getValue().toString()),
                            drive.DateToCalendar((Date) this.dccFechaProximoPago.getValue()),
                            Integer.parseInt(txtCaso.getText()),
                            this.AreConvenioPactado.getText(),
                            Double.parseDouble(this.spnCuotaConvenio.getValue().toString()),
                            this.txtOtroNoCuenta.getText(),
                            this.areSituacionCaso.getText(),
                            opcion_proximo_pago,
                            this.cbxAnticipo.getSelectedItem().toString(),
                            DEstadoExtra.obtener_indice(this.cbxEstadoExtra.getSelectedItem().toString()),
                            DStatusExtra.obtener_indice(this.cbxStatusExtra.getSelectedItem().toString()),
                            this.areNotas.getText(),
                            DIntencion_Pago.obtener_indice(this.cbxIntencionPago.getSelectedItem().toString()),
                            DRazon_Deuda.obtener_indice(this.cbxRazonDeuda.getSelectedItem().toString()),
                            this.cbxAntiguedad.getSelectedItem().toString());

                    com.lexcom.driver.Juicio DJuicio = new com.lexcom.driver.Juicio(this.conn, this.usuario);
                    Integer juicio = DJuicio.dar_juicio(this.deudor);

                    if (juicio != 0) {
                        com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
                        com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);

                        resultado = drive.modificar_juicio(
                                this.deudor,
                                juicio,
                                DProcurador.obtener_indice(this.cbxProcurador.getSelectedItem().toString()),
                                DJuzgado.obtener_indice(this.cbxJuzgado.getSelectedItem().toString()),
                                drive.DateToCalendar((Date) this.dccFechaJuicio.getValue()),
                                this.areRazonNotificacion.getText(),
                                this.txtNoJuicio.getText(),
                                Integer.parseInt(this.spnNotificador.getValue().toString()),
                                this.txtAbogadoDeudor.getText(),
                                DTipoJuicio.obtener_indice(this.cbxTipoJuicio.getSelectedItem().toString()),
                                drive.DateToCalendar((Date) this.dccMemorial.getValue()),
                                this.areDescripcionProcuracion.getText(),
                                this.cbxNotificado.getSelectedItem().toString(),
                                drive.DateToCalendar((Date) this.dccFechaNotificacion.getValue()),
                                Double.parseDouble(spnMontoDemanda.getValue().toString()),
                                DTituloEjecutivo.obtener_indice(this.cbxTituloEjecutivo.getSelectedItem().toString()),
                                drive.DateToCalendar((Date) this.dccFechaAdmision.getValue()));
                    }

                    String[] mensaje = resultado.split(",");
                    JOptionPane.showMessageDialog(null, mensaje[1]);
                } else {
                    JOptionPane.showMessageDialog(null, "Formato del campo correo electrónico no es valido.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El teléfono celular deber ser 0 o un valor entero de 8 digitos.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TblPagosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblPagosMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblPagos.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Pago a = new Pago(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblPagos.setModel(drive.obtener_tabla_pago((DefaultTableModel) TblPagos.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblPagos.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblPagosMouseClicked

    private void TblJuiciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblJuiciosMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblJuicios.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Juicio a = new Juicio(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblJuicios.setModel(drive.obtener_tabla_juicio((DefaultTableModel) TblJuicios.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblJuicios.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblJuiciosMouseClicked

    private void TblDescuentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblDescuentosMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblDescuentos.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Descuento a = new Descuento(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblDescuentos.setModel(drive.obtener_tabla_descuento((DefaultTableModel) TblDescuentos.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblDescuentos.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblDescuentosMouseClicked

    private void TblAumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblAumentosMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblAumentos.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Aumento a = new Aumento(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblAumentos.setModel(drive.obtener_tabla_aumento((DefaultTableModel) TblAumentos.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblAumentos.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblAumentosMouseClicked

    private void TblFiadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblFiadoresMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblFiadores.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Fiador a = new Fiador(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblFiadores.setModel(drive.obtener_tabla_fiador((DefaultTableModel) TblFiadores.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblFiadores.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblFiadoresMouseClicked

    private void TblReferenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblReferenciasMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblFiadores.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Referencia a = new Referencia(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblReferencias.setModel(drive.obtener_tabla_referencia((DefaultTableModel) TblReferencias.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblReferencias.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblReferenciasMouseClicked

    private void chkProximoPagoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkProximoPagoItemStateChanged
        if (this.chkProximoPago.isSelected()) {
            this.jLabel37.setVisible(true);
            this.jLabel46.setVisible(true);
            this.dccFechaProximoPago.setVisible(true);
            this.spnCuotaConvenio.setVisible(true);
        } else {
            this.jLabel37.setVisible(false);
            this.jLabel46.setVisible(false);
            this.dccFechaProximoPago.setVisible(false);
            this.spnCuotaConvenio.setVisible(false);
        }
    }//GEN-LAST:event_chkProximoPagoItemStateChanged

    private void TblPromesaPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblPromesaPagoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TblPromesaPagoMouseClicked

    private void btnPagosAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosAgregar1ActionPerformed
        Promesa_Pago a = new Promesa_Pago(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblPromesaPago.setModel(drive.obtener_tabla_promesa_pago((DefaultTableModel) TblPromesaPago.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_btnPagosAgregar1ActionPerformed

    private void btnPagosModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosModificar1ActionPerformed
        Integer fila_posicion = this.TblPromesaPago.getSelectedRow();

        if (fila_posicion != -1) {
            Promesa_Pago a = new Promesa_Pago(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblPromesaPago.setModel(drive.obtener_tabla_promesa_pago((DefaultTableModel) TblPromesaPago.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
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
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblPromesaPago.setModel(drive.obtener_tabla_promesa_pago((DefaultTableModel) this.TblPromesaPago.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnPagosEliminar1ActionPerformed

    private void btnPagosVer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosVer1ActionPerformed
        Integer fila_posicion = this.TblPromesaPago.getSelectedRow();

        if (fila_posicion != -1) {
            Promesa_Pago a = new Promesa_Pago(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblPromesaPago.setModel(drive.obtener_tabla_promesa_pago((DefaultTableModel) TblPromesaPago.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblPromesaPago.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnPagosVer1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Promesa_Pago a = new Promesa_Pago(this, false, conn, usuario, 0, this.deudor);
        a.addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                TblPromesaPago.setModel(drive.obtener_tabla_promesa_pago((DefaultTableModel) TblPromesaPago.getModel(), deudor));
                cargar(deudor);
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
        Dimension ventana = a.getSize();
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        a.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void areDescripcionProcuracionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areDescripcionProcuracionKeyReleased
        if (evt.getKeyCode() == 123) {
            Dar_Frase_Predeterminada a = new Dar_Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 0);
            a.set_tipo("PROCURACION");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.areDescripcionProcuracion.setText(this.areDescripcionProcuracion.getText() + a.dar_frase());
        }
    }//GEN-LAST:event_areDescripcionProcuracionKeyReleased

    private void areSituacionCasoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areSituacionCasoKeyReleased
        if (evt.getKeyCode() == 123) {
            Dar_Frase_Predeterminada a = new Dar_Frase_Predeterminada(new javax.swing.JFrame(), true, conn, usuario, 0);
            a.set_tipo("SITUACION CASO");
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
            this.areSituacionCaso.setText(this.areSituacionCaso.getText() + a.dar_frase());
        }
    }//GEN-LAST:event_areSituacionCasoKeyReleased

    private void TblDigitalizacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblDigitalizacionMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblDigitalizacion.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                try {
                    System.out.println("start \"\" \"" + this.TblDigitalizacion.getValueAt(fila_posicion, 2).toString() + "\"");
                    Runtime.getRuntime().exec("cmd /c start \"\" \"" + this.TblDigitalizacion.getValueAt(fila_posicion, 2).toString() + "\"");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo mostrar la imagen.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
            }
        }
    }//GEN-LAST:event_TblDigitalizacionMouseClicked

    private void btnDigitalizacionVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigitalizacionVerActionPerformed
        Integer fila_posicion = this.TblDigitalizacion.getSelectedRow();

        if (fila_posicion != -1) {
            try {
                //System.out.println("start \"\" \"" + this.TblDigitalizacion.getValueAt(fila_posicion, 2).toString() + "\"" );
                Runtime.getRuntime().exec("cmd /c start \"\" \"" + this.TblDigitalizacion.getValueAt(fila_posicion, 2).toString() + "\"");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo mostrar la imagen.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnDigitalizacionVerActionPerformed

    private void TblConvenioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblConvenioMouseClicked
        if (evt.getClickCount() == 2) {
            Integer fila_posicion = TblConvenio.rowAtPoint(evt.getPoint());
            if (fila_posicion != -1) {
                Convenio a = new Convenio(this, false, conn, usuario, 1, this.deudor);
                a.addWindowListener(new WindowListener() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                        TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) TblConvenio.getModel(), deudor));
                        cargar(deudor);
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
                a.cargar(Integer.parseInt(this.TblConvenio.getValueAt(fila_posicion, 0).toString()));
                Dimension ventana = a.getSize();
                Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_TblConvenioMouseClicked

    private void btnConvenioAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvenioAgregarActionPerformed
        Boolean convenio_pendiente = false;
        for(Integer i=0; i<this.TblConvenio.getRowCount(); i++) {
            if(this.TblConvenio.getValueAt(i, 2).equals("NEGOCIACION") || this.TblConvenio.getValueAt(i, 2).equals("ACTIVO")) {
                convenio_pendiente = true;
            }
        }
        if(!convenio_pendiente) {
            Convenio a = new Convenio(this, false, conn, usuario, 0, this.deudor);
            a.cargar_estado(false);
            a.set_saldo(Double.parseDouble(this.spnSaldo.getValue().toString()));
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) TblConvenio.getModel(), deudor));
                    cargar(deudor);
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
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "El deudor tiene un convenio de pago en NEGOCIACION o ACTIVO.");
        }
    }//GEN-LAST:event_btnConvenioAgregarActionPerformed

    private void btnConvenioModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvenioModificarActionPerformed
        Integer fila_posicion = this.TblConvenio.getSelectedRow();

        if (fila_posicion != -1) {
            Convenio a = new Convenio(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) TblConvenio.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(Integer.parseInt(this.TblConvenio.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnConvenioModificarActionPerformed

    private void btnConvenioEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvenioEliminarActionPerformed
        Integer fila_posicion = this.TblConvenio.getSelectedRow();

        if (fila_posicion != -1) {
            Integer opcion1 = JOptionPane.showConfirmDialog(null, "Seguro desea eliminar el convenio de pago?", "ELIMINACIÓN CONVENIO DE PAGO", 0);
            if (opcion1 == 0) {
                com.lexcom.driver.Convenio drive = new com.lexcom.driver.Convenio(this.conn, this.usuario);
                String resultado = drive.eliminar(Integer.parseInt(this.TblConvenio.getValueAt(fila_posicion, 0).toString()));
                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
                cargar(deudor);
            }
            com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
            this.TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) this.TblConvenio.getModel(), this.deudor));
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnConvenioEliminarActionPerformed

    private void btnConvenioVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvenioVerActionPerformed
        Integer fila_posicion = this.TblConvenio.getSelectedRow();

        if (fila_posicion != -1) {
            Convenio a = new Convenio(this, false, conn, usuario, 2, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) TblConvenio.getModel(), deudor));
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
            a.cargar(Integer.parseInt(this.TblConvenio.getValueAt(fila_posicion, 0).toString()));
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un elemento de la tabla.");
        }
    }//GEN-LAST:event_btnConvenioVerActionPerformed

    private void btnConvenioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvenioActionPerformed
        com.lexcom.driver.Convenio drive = new com.lexcom.driver.Convenio(this.conn, this.usuario);
        Integer convenio = drive.dar_convenio_activo(this.deudor);
        if(convenio == 0) {
            Convenio a = new Convenio(this, false, conn, usuario, 0, this.deudor);
            a.cargar_estado(false);
            a.habilitar_boton_guardar(false);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) TblConvenio.getModel(), deudor));
                    cargar(deudor);
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
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
        else {
            Convenio a = new Convenio(this, false, conn, usuario, 1, this.deudor);
            a.addWindowListener(new WindowListener() {

                @Override
                public void windowClosed(WindowEvent e) {
                    com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(conn, usuario);
                    TblConvenio.setModel(drive.obtener_tabla_convenio((DefaultTableModel) TblConvenio.getModel(), deudor));
                    cargar(deudor);
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
            a.cargar(convenio);
            Dimension ventana = a.getSize();
            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
            a.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
            a.setVisible(true);
        }
    }//GEN-LAST:event_btnConvenioActionPerformed

    private void cbxEstadoExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoExtraActionPerformed
        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        this.cbxStatusExtra.setModel(DStatusExtra.dar_lista_comb(this.cbxEstadoExtra.getSelectedItem().toString()));
    }//GEN-LAST:event_cbxEstadoExtraActionPerformed

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatus.setModel(DStatus.dar_lista_comb(this.cbxEstado.getSelectedItem().toString()));
    }//GEN-LAST:event_cbxEstadoActionPerformed

    private void cbxCargadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCargadoActionPerformed
        if(this.cbxCargado.getSelectedItem().toString().equals("CARGADO")) {
            this.cbxCargado.setForeground(Color.BLUE);
            this.jLabel45.setForeground(Color.BLUE);
        } else {
            this.cbxCargado.setForeground(Color.RED);
            this.jLabel45.setForeground(Color.RED);
        }
    }//GEN-LAST:event_cbxCargadoActionPerformed

    public void cargar(Integer deudor) {
        this.deudor = deudor;

        com.lexcom.driver.Deudor DDeudor = new com.lexcom.driver.Deudor(this.conn, this.usuario);
        DDeudor.obtener(this.deudor);
        this.setTitle(DDeudor.nombre + " DPI:" + DDeudor.dpi + " NIT:" + DDeudor.nit);

        com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(this.conn, usuario);
        com.lexcom.driver.Deudor resultado = DExpediente.Dar_Datos_Deudor(this.deudor);

        com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
        com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
        com.lexcom.driver.Intencion_Pago DIntencion_Pago = new com.lexcom.driver.Intencion_Pago(this.conn, this.usuario);
        com.lexcom.driver.Razon_Deuda DRazon_Deuda = new com.lexcom.driver.Razon_Deuda(this.conn, this.usuario);
        
        com.lexcom.driver.EstadoExtra DEstadoExtra = new com.lexcom.driver.EstadoExtra(this.conn, this.usuario);
        com.lexcom.driver.StatusExtra DStatusExtra = new com.lexcom.driver.StatusExtra(this.conn, this.usuario);
        
        com.lexcom.driver.Convenio DConvenio = new com.lexcom.driver.Convenio(this.conn, this.usuario);
        Integer id_convenio = DConvenio.dar_convenio_activo(this.deudor);
        com.lexcom.driver.Convenio convenio_resultado = DConvenio.obtener(id_convenio);
        
        //Habilitar componentes de fechas para poder cargar la fecha.
        this.txtFechaUltimoPago.setEditable(false);
        this.dccMemorial.setEnabled(true);

        //Cargar información a los componentes.
        String com_judicial = DExpediente.validar_estado_status_judicial(this.deudor);
        String com_extrajudicial = DExpediente.validar_estado_status_extrajudicial(this.deudor);
        
        //this.gestion_actual = DDeudor.texto_gestion(this.usuario);
        gestion_actual = "CASO: " + DDeudor.caso + " | " + "NOMBRE: " + DDeudor.nombre + " | "  + "JUDICIAL: " + com_judicial + " | "  + "EXTRAJUDICIAL: " + com_extrajudicial + " | ";

        this.cbxActor.setSelectedItem(DActor.obtener_nombre(resultado.actor));
        this.cbxMoneda.setSelectedItem(resultado.moneda);
        this.cbxGestor.setSelectedItem(DUsuario.obtener_nombre(resultado.gestor));
        this.cbxEstado.setSelectedItem(DEstado.obtener_nombre(resultado.sestado));
        this.cbxStatus.setSelectedItem(DStatus.obtener_nombre(resultado.estatus));
        this.cbxGarantia.setSelectedItem(DGarantia.obtener_nombre(resultado.garantia));
        this.cbxCargado.setSelectedItem(resultado.cargado);
        this.cbxAnticipo.setSelectedItem(resultado.anticipo);
        this.cbxEstadoExtra.setSelectedItem(DEstadoExtra.obtener_nombre(resultado.sestado_extra));
        this.cbxStatusExtra.setSelectedItem(DStatusExtra.obtener_nombre(resultado.estatus_extra));
        this.cbxIntencionPago.setSelectedItem(DIntencion_Pago.obtener_nombre(resultado.intencion_pago));
        this.cbxRazonDeuda.setSelectedItem(DRazon_Deuda.obtener_nombre(resultado.razon_deuda));
        this.cbxAntiguedad.setSelectedItem(resultado.antiguedad);
        
        this.txtDpi.setText(resultado.dpi);
        this.txtNit.setText(resultado.nit);
        this.txtNombre.setText(resultado.nombre);
        this.txtTelefonoCasa.setText(resultado.telefono_casa);
        this.txtTelefonoCelular.setText(resultado.telefono_celular);
        this.areDireccion.setText(resultado.direccion);
        this.txtCorreoElectronico.setText(resultado.correo_electronico);
        this.txtLugarTrabajo.setText(resultado.lugar_trabajo);
        this.txtDireccionTrabajo.setText(resultado.direccion_trabajo);
        this.txtTelefonoTrabajo.setText(resultado.telefono_trabajo);
        this.txtNoCuenta.setText(resultado.no_cuenta);
        this.txtOtroNoCuenta.setText(resultado.no_cuenta_otro);

        this.dccFechaNac.setValue(resultado.fecha_nacimiento.getTime());
        this.dccFechaRecepcion.setValue(resultado.fecha_recepcion.getTime());
        SimpleDateFormat dateFortam = new SimpleDateFormat("dd/MM/yyyy");
        if(DExpediente.monto_ultimo_pago(this.deudor) == 0.00) {
            this.txtFechaUltimoPago.setText("-");
        } else {
            this.txtFechaUltimoPago.setText(dateFortam.format(DExpediente.fecha_ultimo_pago(this.deudor).getTime()));
        }
        this.dccFechaProximoPago.setEnabled(true);
        this.dccFechaProximoPago.setValue(resultado.fecha_proximo_pago.getTime());
        
        this.spnMontoInicial.setValue(resultado.monto_inicial);
        // this.spnAumentos.setValue(DExpediente.calcular_aumentos(this.deudor));
        // this.spnDescuentos.setValue(DExpediente.calcular_descuentos(this.deudor));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.00");
        this.txtPagos.setText("Q." + decimalFormat.format(DExpediente.calcular_pagos(this.deudor)));
        this.spnSaldo.setValue(resultado.saldo);
        this.txtMontoUltimoPago.setText("Q." + decimalFormat.format(DExpediente.monto_ultimo_pago(this.deudor)));
        this.txtCaso.setText(resultado.caso.toString());
        this.spnCuotaConvenio.setValue(resultado.cuota_convenio);

        this.AreConvenioPactado.setText(resultado.convenio_pactado);
        this.areSituacionCaso.setText(resultado.situacion_caso);
        this.areNotas.setText(resultado.descripcion);
        
        if(convenio_resultado.tipo_convenio == null) {
            this.txtTipoConvenio.setText("SIN CONVENIO");
            this.txtTipoConvenio.setBackground(Color.lightGray);
            this.txtTipoConvenio.setForeground(Color.BLACK);
        } else {
            this.txtTipoConvenio.setText(convenio_resultado.tipo_convenio);
            this.txtTipoConvenio.setBackground(Color.YELLOW);
            this.txtTipoConvenio.setForeground(Color.BLACK);
        }

        if (resultado.PDF.equals("SI")) {
            this.chkPDF.setSelected(true);
        } else {
            this.chkPDF.setSelected(false);
        }
        if (resultado.INV.equals("SI")) {
            this.chkINV.setSelected(true);
        } else {
            this.chkINV.setSelected(false);
        }
        if (resultado.MAYCOM.equals("SI")) {
            this.chkMAYCOM.setSelected(true);
        } else {
            this.chkMAYCOM.setSelected(false);
        }
        if (resultado.NITS.equals("SI")) {
            this.chkNITS.setSelected(true);
        } else {
            this.chkNITS.setSelected(false);
        }
        if (resultado.opcion_proximo_pago.equals("SI")) {
            this.chkProximoPago.setSelected(true);
            this.jLabel37.setVisible(true);
            this.jLabel46.setVisible(true);
            this.dccFechaProximoPago.setVisible(true);
            this.spnCuotaConvenio.setVisible(true);
        } else {
            this.chkProximoPago.setSelected(false);
            this.jLabel37.setVisible(false);
            this.jLabel46.setVisible(false);
            this.dccFechaProximoPago.setVisible(false);
            this.spnCuotaConvenio.setVisible(false);
        }
        
        if(resultado.cuenta_principal_relacion.equals("SI")) {
            this.chkCuentaPrincipalRelacion.setSelected(true);
        } else {
            this.chkCuentaPrincipalRelacion.setSelected(false);
        }

        this.TblGestion.setModel(DExpediente.obtener_tabla_gestion((DefaultTableModel) this.TblGestion.getModel(), this.deudor));
        this.TblAdministracion.setModel(DExpediente.obtener_tabla_administracion((DefaultTableModel) this.TblAdministracion.getModel(), this.deudor));
        this.TblJuridico.setModel(DExpediente.obtener_tabla_juridico((DefaultTableModel) this.TblJuridico.getModel(), this.deudor));
        this.TblPagos.setModel(DExpediente.obtener_tabla_pago((DefaultTableModel) this.TblPagos.getModel(), this.deudor));
        this.TblPromesaPago.setModel(DExpediente.obtener_tabla_promesa_pago((DefaultTableModel) this.TblPromesaPago.getModel(), this.deudor));
        this.TblJuicios.setModel(DExpediente.obtener_tabla_juicio((DefaultTableModel) this.TblJuicios.getModel(), this.deudor));
        this.TblDescuentos.setModel(DExpediente.obtener_tabla_descuento((DefaultTableModel) this.TblDescuentos.getModel(), this.deudor));
        this.TblAumentos.setModel(DExpediente.obtener_tabla_aumento((DefaultTableModel) this.TblAumentos.getModel(), this.deudor));
        this.TblFiadores.setModel(DExpediente.obtener_tabla_fiador((DefaultTableModel) this.TblFiadores.getModel(), this.deudor));
        this.TblReferencias.setModel(DExpediente.obtener_tabla_referencia((DefaultTableModel) this.TblReferencias.getModel(), this.deudor));
        this.TblConvenio.setModel(DExpediente.obtener_tabla_convenio((DefaultTableModel) this.TblConvenio.getModel(), this.deudor));
        this.TblCuentaRelacionada.setModel(DExpediente.obtener_tabla_cuenta_relacionada((DefaultTableModel) this.TblCuentaRelacionada.getModel(), this.deudor));

        com.lexcom.driver.Fiador DFiador = new com.lexcom.driver.Fiador(this.conn, this.usuario);
        // this.cbxFiador.setModel(DFiador.dar_lista_fiador(this.deudor));
        com.lexcom.driver.Juicio DJuicio = new com.lexcom.driver.Juicio(this.conn, this.usuario);
        Integer juicio = DJuicio.dar_juicio(this.deudor);

        if (juicio != 0) {
            com.lexcom.driver.Juicio Dresultado = DJuicio.obtener_eliminar(juicio);
            com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
            com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
            com.lexcom.driver.Tipo_Juicio DTipoJuicio = new com.lexcom.driver.Tipo_Juicio(this.conn, this.usuario);
            com.lexcom.driver.Titulo_Ejecutivo DTituloEjecutivo = new com.lexcom.driver.Titulo_Ejecutivo(this.conn, this.usuario);
            this.cbxProcurador.setEnabled(true);
            this.cbxJuzgado.setEnabled(true);
            this.cbxNotificado.setEnabled(true);
            this.dccFechaJuicio.setEnabled(true);
            this.dccMemorial.setEnabled(true);
            this.dccFechaNotificacion.setEnabled(true);
            this.areRazonNotificacion.setEnabled(true);
            this.txtNoJuicio.setEnabled(true);
            this.txtAbogadoDeudor.setEnabled(true);
            this.cbxTipoJuicio.setEnabled(true);
            this.cbxTituloEjecutivo.setEnabled(true);
            this.spnNotificador.setEnabled(true);
            this.spnMontoDemanda.setEnabled(true);
            this.areDescripcionProcuracion.setEnabled(true);
            this.dccFechaAdmision.setEnabled(true);
            
            this.cbxProcurador.setSelectedItem(DProcurador.obtener_nombre(Dresultado.procurador));
            this.cbxJuzgado.setSelectedItem(DJuzgado.obtener_nombre(Dresultado.juzgado));
            this.cbxNotificado.setSelectedItem(Dresultado.deudor_notificado);
            this.dccFechaJuicio.setValue(Dresultado.fecha.getTime());
            this.dccMemorial.setValue(Dresultado.memorial.getTime());
            this.dccFechaNotificacion.setValue(Dresultado.fecha_notificacion.getTime());
            this.areRazonNotificacion.setText(Dresultado.razon_notificacion);
            this.txtNoJuicio.setText(Dresultado.no_juicio);
            this.txtAbogadoDeudor.setText(Dresultado.abogado_deudor);
            this.cbxTipoJuicio.setSelectedItem(DTipoJuicio.obtener_nombre(Dresultado.tipo_juicio));
            this.cbxTituloEjecutivo.setSelectedItem(DTituloEjecutivo.obtener_nombre(Dresultado.titulo_ejecutivo));
            this.spnNotificador.setValue(Dresultado.notificador);
            this.spnMontoDemanda.setValue(Dresultado.monto);
            this.areDescripcionProcuracion.setText(Dresultado.procuracion);
            this.dccFechaAdmision.setValue(Dresultado.fecha_admision_demanda.getTime());
        } else {
            this.cbxProcurador.setSelectedIndex(0);
            this.cbxJuzgado.setSelectedIndex(0);
            this.cbxNotificado.setSelectedIndex(0);
            this.dccFechaJuicio.setValue(Calendar.getInstance().getTime());
            this.dccMemorial.setValue(Calendar.getInstance().getTime());
            this.dccFechaNotificacion.setValue(Calendar.getInstance().getTime());
            this.areRazonNotificacion.setText("");
            this.txtNoJuicio.setText("");
            this.txtAbogadoDeudor.setText("");
            this.cbxTipoJuicio.setSelectedIndex(0);
            this.cbxTituloEjecutivo.setSelectedIndex(0);
            this.spnNotificador.setValue(0);
            this.spnMontoDemanda.setValue(0.00);
            this.areDescripcionProcuracion.setText("");
            this.dccFechaAdmision.setValue(Calendar.getInstance().getTime());
            
            this.cbxProcurador.setEnabled(false);
            this.cbxJuzgado.setEnabled(false);
            this.cbxNotificado.setEnabled(false);
            this.dccFechaJuicio.setEnabled(false);
            this.dccMemorial.setEnabled(false);
            this.dccFechaNotificacion.setEnabled(false);
            this.areRazonNotificacion.setEnabled(false);
            this.txtNoJuicio.setEnabled(false);
            this.txtAbogadoDeudor.setEnabled(false);
            this.cbxTituloEjecutivo.setEnabled(false);
            this.spnNotificador.setEnabled(false);
            this.spnMontoDemanda.setEnabled(false);
            this.dccFechaAdmision.setEnabled(false);
        }

        this.txtFechaUltimoPago.setEditable(false);
        // this.spnAumentos.setEnabled(false);
        // this.spnDescuentos.setEnabled(false);
        this.txtPagos.setEditable(false);
        this.txtMontoUltimoPago.setEditable(false);

        com.lexcom.driver.Expediente drive = new com.lexcom.driver.Expediente(this.conn, this.usuario);
        if (drive.es_gestor(usuario) && !(drive.es_procurador(usuario) || drive.es_asistente(usuario) || drive.es_digitador(usuario) || drive.es_investigador(usuario))) {
            this.txtCaso.setEditable(false);
            this.txtNombre.setEditable(false);
            this.txtNoCuenta.setEditable(false);
            this.txtOtroNoCuenta.setEditable(false);
            this.spnMontoInicial.setEnabled(false);
            this.cbxStatus.setEnabled(false);
            this.cbxCargado.setEnabled(false);
            this.cbxGestor.setEnabled(false);
            this.cbxGestor.setEnabled(false);
            this.areDescripcionProcuracion.setEditable(false);
            this.areRazonNotificacion.setEditable(false);
            this.dccFechaJuicio.setEnabled(false);
            this.cbxJuzgado.setEnabled(false);
            this.txtNoJuicio.setEditable(false);
            this.spnNotificador.setEnabled(false);
            this.spnMontoDemanda.setEnabled(false);
            this.txtAbogadoDeudor.setEditable(false);
            this.cbxTipoJuicio.setEnabled(false);
            this.cbxTituloEjecutivo.setEnabled(false);
            this.dccMemorial.setEnabled(false);
            this.dccFechaAdmision.setEnabled(false);
        }
        
        this.AreConvenioPactado.setEditable(false);
        this.chkProximoPago.setEnabled(false);
        this.dccFechaProximoPago.setEnabled(false);
        this.spnCuotaConvenio.setEnabled(false);
        
        this.lbNoGestionesCobros.setText("No. de Gestiones: " + this.TblGestion.getRowCount());
        
        this.cbxEstado.setEnabled(false);
        this.cbxStatus.setEnabled(false);
        this.cbxEstadoExtra.setEnabled(false);
        this.cbxStatusExtra.setEnabled(false);
        this.cbxIntencionPago.setEnabled(false);
        this.cbxRazonDeuda.setEnabled(false);
        
        com.lexcom.driver.Usuario DUsuario_T = DUsuario.obtener(this.usuario);
        if(DUsuario_T.asistente.equals("SI")) {
            this.cbxEstado.setEnabled(true);
            this.cbxStatus.setEnabled(true);
            this.cbxEstadoExtra.setEnabled(true);
            this.cbxStatusExtra.setEnabled(true);
            this.cbxIntencionPago.setEnabled(true);
            this.cbxRazonDeuda.setEnabled(true);
        } else {
            if(DUsuario_T.gestor.equals("SI")) {
                this.cbxEstadoExtra.setEnabled(true);
                this.cbxStatusExtra.setEnabled(true);
                this.cbxIntencionPago.setEnabled(true);
                this.cbxRazonDeuda.setEnabled(true);
            }
            if(DUsuario_T.procurador.equals("SI") || DUsuario_T.digitador.equals("SI")) {
                this.cbxEstado.setEnabled(true);
                this.cbxStatus.setEnabled(true);
            }
        }
        
        if(drive.estado_status_bloquea_judicial(resultado.sestado_extra, resultado.estatus_extra).equals("SI")) {
            this.cbxEstado.setEnabled(false);
            this.cbxStatus.setEnabled(false);
        }
        
        if(resultado.cargado.equals("CARGADO")) {
            this.cbxCargado.setForeground(Color.BLUE);
            this.jLabel45.setForeground(Color.BLUE);
        } else {
            this.cbxCargado.setForeground(Color.RED);
            this.jLabel45.setForeground(Color.RED);
        }
        
        this.btnConvenio.setVisible(false);
        // this.dccFechaAdmision.setEnabled(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea AreConvenioPactado;
    private javax.swing.JTable TblAdministracion;
    private javax.swing.JTable TblAumentos;
    private javax.swing.JTable TblConvenio;
    private javax.swing.JTable TblCuentaRelacionada;
    private javax.swing.JTable TblDescuentos;
    private javax.swing.JTable TblDigitalizacion;
    private javax.swing.JTable TblFiadores;
    private javax.swing.JTable TblGestion;
    private javax.swing.JTable TblJuicios;
    private javax.swing.JTable TblJuridico;
    private javax.swing.JTable TblPagos;
    private javax.swing.JTable TblPromesaPago;
    private javax.swing.JTable TblReferencias;
    private javax.swing.JTextArea areAdministracionDescripcion;
    private javax.swing.JTextArea areDescripcionProcuracion;
    private javax.swing.JTextArea areDireccion;
    private javax.swing.JTextArea areJuridicoDescripcion;
    private javax.swing.JTextArea areNotas;
    private javax.swing.JTextArea areRazonNotificacion;
    private javax.swing.JTextArea areSituacionCaso;
    private javax.swing.JButton btnAdministracionInsertar;
    private javax.swing.JButton btnAumentosAgregar;
    private javax.swing.JButton btnAumentosEliminar;
    private javax.swing.JButton btnAumentosModificar;
    private javax.swing.JButton btnAumentosVer;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnConvenio;
    private javax.swing.JButton btnConvenioAgregar;
    private javax.swing.JButton btnConvenioEliminar;
    private javax.swing.JButton btnConvenioModificar;
    private javax.swing.JButton btnConvenioVer;
    private javax.swing.JButton btnDescuentosAgregar;
    private javax.swing.JButton btnDescuentosEliminar;
    private javax.swing.JButton btnDescuentosModificar;
    private javax.swing.JButton btnDescuentosVer;
    private javax.swing.JButton btnDigitalizacionVer;
    private javax.swing.JButton btnFiadoresAgregar;
    private javax.swing.JButton btnFiadoresEliminar;
    private javax.swing.JButton btnFiadoresModificar;
    private javax.swing.JButton btnFiadoresVer;
    private javax.swing.JButton btnGestionInsertar;
    private javax.swing.JButton btnJuiciosAgregar;
    private javax.swing.JButton btnJuiciosEliminar;
    private javax.swing.JButton btnJuiciosModificar;
    private javax.swing.JButton btnJuiciosVer;
    private javax.swing.JButton btnJuridicoInsertar;
    private javax.swing.JButton btnPagosAgregar;
    private javax.swing.JButton btnPagosAgregar1;
    private javax.swing.JButton btnPagosEliminar;
    private javax.swing.JButton btnPagosEliminar1;
    private javax.swing.JButton btnPagosModificar;
    private javax.swing.JButton btnPagosModificar1;
    private javax.swing.JButton btnPagosVer;
    private javax.swing.JButton btnPagosVer1;
    private javax.swing.JButton btnReferenciasAgregar;
    private javax.swing.JButton btnReferenciasEliminar;
    private javax.swing.JButton btnReferenciasModificar;
    private javax.swing.JButton btnReferenciasVer;
    private javax.swing.JComboBox cbxActor;
    private javax.swing.JComboBox cbxAnticipo;
    private javax.swing.JComboBox cbxAntiguedad;
    private javax.swing.JComboBox cbxCargado;
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JComboBox cbxEstadoExtra;
    private javax.swing.JComboBox cbxGarantia;
    private javax.swing.JComboBox cbxGestor;
    private javax.swing.JComboBox cbxIntencionPago;
    private javax.swing.JComboBox cbxJuzgado;
    private javax.swing.JComboBox cbxMoneda;
    private javax.swing.JComboBox cbxNotificado;
    private javax.swing.JComboBox cbxProcurador;
    private javax.swing.JComboBox cbxRazonDeuda;
    private javax.swing.JComboBox cbxStatus;
    private javax.swing.JComboBox cbxStatusExtra;
    private javax.swing.JComboBox<String> cbxTipoJuicio;
    private javax.swing.JComboBox<String> cbxTituloEjecutivo;
    private javax.swing.JCheckBox chkCuentaPrincipalRelacion;
    private javax.swing.JCheckBox chkINV;
    private javax.swing.JCheckBox chkMAYCOM;
    private javax.swing.JCheckBox chkNITS;
    private javax.swing.JCheckBox chkPDF;
    private javax.swing.JCheckBox chkProximoPago;
    private javax.swing.JSpinner dccFechaAdmision;
    private javax.swing.JSpinner dccFechaJuicio;
    private javax.swing.JSpinner dccFechaNac;
    private javax.swing.JSpinner dccFechaNotificacion;
    private javax.swing.JSpinner dccFechaProximoPago;
    private javax.swing.JSpinner dccFechaRecepcion;
    private javax.swing.JSpinner dccMemorial;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labSumario;
    private javax.swing.JLabel lbGestiones;
    private javax.swing.JLabel lbNoGestionesCobros;
    private javax.swing.JSpinner spnCuotaConvenio;
    private javax.swing.JSpinner spnMontoDemanda;
    private javax.swing.JSpinner spnMontoInicial;
    private javax.swing.JSpinner spnMontoInicial1;
    private javax.swing.JSpinner spnNotificador;
    private javax.swing.JSpinner spnSaldo;
    private javax.swing.JSpinner spnSaldo1;
    private javax.swing.JTextField txtAbogadoDeudor;
    private javax.swing.JTextField txtCaso;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDireccionTrabajo;
    private javax.swing.JTextField txtDpi;
    private javax.swing.JTextField txtFechaUltimoPago;
    private javax.swing.JTextField txtLugarTrabajo;
    private javax.swing.JTextField txtMontoUltimoPago;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNoCuenta;
    private javax.swing.JTextField txtNoJuicio;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOtroNoCuenta;
    private javax.swing.JTextField txtPagos;
    private javax.swing.JTextField txtTelefonoCasa;
    private javax.swing.JTextField txtTelefonoCelular;
    private javax.swing.JTextField txtTelefonoTrabajo;
    private javax.swing.JTextField txtTipoConvenio;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        Integer horas = 0;
        Integer minutos = 0;
        Integer segundos = 0;

        while (true) {
            try {
                segundos++;
                if (segundos == 60) {
                    minutos++;
                    segundos = 0;
                    if (minutos == 60) {
                        horas++;
                    }
                }
                if (minutos >= 0 && minutos < 3) {
                    this.lbGestiones.setForeground(new Color(7,90,216));
                }
                if (minutos >= 3 && minutos < 4) {
                    this.lbGestiones.setForeground(new Color(255,128,0));
                }
                if (minutos >= 4 && minutos < 5) {
                    this.lbGestiones.setForeground(new Color(255,0,0));
                }
                String reloj_hora = horas + "h " + minutos + "m " + segundos + "s";

                // this.lbGestiones.setText(gestion_actual + "DURACIÓN: " + reloj_hora);
                this.lbGestiones.setText("DURACIÓN: " + reloj_hora);
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
