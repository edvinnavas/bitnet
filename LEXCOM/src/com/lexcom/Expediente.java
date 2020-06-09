package com.lexcom;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

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

        hilo = new Thread(this);
        hilo.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCerrar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel25 = new javax.swing.JPanel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jLabel18 = new javax.swing.JLabel();
        dccFechaRecepcion = new datechooser.beans.DateChooserCombo();
        jLabel28 = new javax.swing.JLabel();
        txtNoCuenta = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtOtroNoCuenta = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        cbxGarantia = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbxAntiguedad = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        cbxCargado = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        cbxGestor = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cbxIntencionPago = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        cbxRazonDeuda = new javax.swing.JComboBox();
        jScrollPane21 = new javax.swing.JScrollPane();
        areNotas = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        spnAumentos = new javax.swing.JSpinner();
        jLabel33 = new javax.swing.JLabel();
        spnDescuentos = new javax.swing.JSpinner();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        spnPagos = new javax.swing.JSpinner();
        jLabel35 = new javax.swing.JLabel();
        dccFechaUltimoPago = new datechooser.beans.DateChooserCombo();
        jLabel36 = new javax.swing.JLabel();
        spnMontoUltimoPago = new javax.swing.JSpinner();
        chkProximoPago = new javax.swing.JCheckBox();
        jLabel37 = new javax.swing.JLabel();
        dccFechaProximoPago = new datechooser.beans.DateChooserCombo();
        spnCuotaConvenio = new javax.swing.JSpinner();
        btnConvenio = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        AreConvenioPactado = new javax.swing.JTextArea();
        jLabel46 = new javax.swing.JLabel();
        txtTipoConvenio = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        jLabel15 = new javax.swing.JLabel();
        txtTelefonoCasa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefonoCelular = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCorreoElectronico = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtLugarTrabajo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtDireccionTrabajo = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtTelefonoTrabajo = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        areDireccion = new javax.swing.JTextArea();
        jPanel27 = new javax.swing.JPanel();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        areDescripcionProcuracion = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        areSituacionCaso = new javax.swing.JTextArea();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        areRazonNotificacion = new javax.swing.JTextArea();
        jLayeredPane8 = new javax.swing.JLayeredPane();
        jLabel13 = new javax.swing.JLabel();
        cbxProcurador = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        dccFechaJuicio = new datechooser.beans.DateChooserCombo();
        jLabel17 = new javax.swing.JLabel();
        cbxJuzgado = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        txtNoJuicio = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        spnNotificador = new javax.swing.JSpinner();
        jLabel40 = new javax.swing.JLabel();
        txtAbogadoDeudor = new javax.swing.JTextField();
        labSumario = new javax.swing.JLabel();
        txtSumario = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        dccMemorial = new datechooser.beans.DateChooserCombo();
        jLabel47 = new javax.swing.JLabel();
        cbxNotificado = new javax.swing.JComboBox();
        jLabel48 = new javax.swing.JLabel();
        dccFechaNotificacion = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        spnMontoDemanda = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        cbxAnticipo = new javax.swing.JComboBox();
        dccFechaAdmision = new datechooser.beans.DateChooserCombo();
        jLabel54 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        chkPDF = new javax.swing.JCheckBox();
        chkINV = new javax.swing.JCheckBox();
        chkMAYCOM = new javax.swing.JCheckBox();
        chkNITS = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        dccFechaNac = new datechooser.beans.DateChooserCombo();
        jLabel8 = new javax.swing.JLabel();
        txtDpi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        panelGestion = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panelBitacora = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblGestion = new javax.swing.JTable();
        btnGestionInsertar = new javax.swing.JButton();
        lbNoGestionesCobros = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TblAdministracion = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        areAdministracionDescripcion = new javax.swing.JTextArea();
        btnAdministracionInsertar = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TblJuridico = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        areJuridicoDescripcion = new javax.swing.JTextArea();
        btnJuridicoInsertar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        TblPagos = new javax.swing.JTable();
        btnPagosAgregar = new javax.swing.JButton();
        btnPagosModificar = new javax.swing.JButton();
        btnPagosEliminar = new javax.swing.JButton();
        btnPagosVer = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        TblPromesaPago = new javax.swing.JTable();
        btnPagosAgregar1 = new javax.swing.JButton();
        btnPagosModificar1 = new javax.swing.JButton();
        btnPagosEliminar1 = new javax.swing.JButton();
        btnPagosVer1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TblJuicios = new javax.swing.JTable();
        btnJuiciosAgregar = new javax.swing.JButton();
        btnJuiciosModificar = new javax.swing.JButton();
        btnJuiciosEliminar = new javax.swing.JButton();
        btnJuiciosVer = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TblDescuentos = new javax.swing.JTable();
        btnDescuentosAgregar = new javax.swing.JButton();
        btnDescuentosModificar = new javax.swing.JButton();
        btnDescuentosEliminar = new javax.swing.JButton();
        btnDescuentosVer = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        TblAumentos = new javax.swing.JTable();
        btnAumentosAgregar = new javax.swing.JButton();
        btnAumentosModificar = new javax.swing.JButton();
        btnAumentosEliminar = new javax.swing.JButton();
        btnAumentosVer = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        TblFiadores = new javax.swing.JTable();
        btnFiadoresAgregar = new javax.swing.JButton();
        btnFiadoresModificar = new javax.swing.JButton();
        btnFiadoresEliminar = new javax.swing.JButton();
        btnFiadoresVer = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        TblReferencias = new javax.swing.JTable();
        btnReferenciasAgregar = new javax.swing.JButton();
        btnReferenciasModificar = new javax.swing.JButton();
        btnReferenciasEliminar = new javax.swing.JButton();
        btnReferenciasVer = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        TblDigitalizacion = new javax.swing.JTable();
        btnDigitalizacionVer = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        TblConvenio = new javax.swing.JTable();
        btnConvenioAgregar = new javax.swing.JButton();
        btnConvenioModificar = new javax.swing.JButton();
        btnConvenioEliminar = new javax.swing.JButton();
        btnConvenioVer = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        TblCuentaRelacionada = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbxActor = new javax.swing.JComboBox();
        jLabel41 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbxFiador = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        spnMontoInicial = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        cbxMoneda = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        spnSaldo = new javax.swing.JSpinner();
        txtCaso = new javax.swing.JTextField();
        chkCuentaPrincipalRelacion = new javax.swing.JCheckBox();
        lbGestiones = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        cbxEstadoExtra = new javax.swing.JComboBox();
        cbxStatusExtra = new javax.swing.JComboBox();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabel26 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EXPEDIENTE");

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Deudor"));

        jLayeredPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("-"));

        jLabel18.setText("Fecha Recepción");
        jLabel18.setBounds(20, 23, 81, 14);
        jLayeredPane4.add(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccFechaRecepcion.setNothingAllowed(false);
        dccFechaRecepcion.setLocale(new java.util.Locale("es", "GT", ""));
        dccFechaRecepcion.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaRecepcion.setBounds(120, 20, 200, 20);
        jLayeredPane4.add(dccFechaRecepcion, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel28.setText("No. Cuenta");
        jLabel28.setBounds(20, 48, 55, 14);
        jLayeredPane4.add(jLabel28, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtNoCuenta.setBounds(120, 45, 200, 20);
        jLayeredPane4.add(txtNoCuenta, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel38.setText("Otro No. Cuenta");
        jLabel38.setBounds(20, 73, 80, 14);
        jLayeredPane4.add(jLabel38, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtOtroNoCuenta.setBounds(120, 70, 200, 20);
        jLayeredPane4.add(txtOtroNoCuenta, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel29.setText("Garantía");
        jLabel29.setBounds(20, 98, 41, 14);
        jLayeredPane4.add(jLabel29, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxGarantia.setBounds(120, 95, 200, 20);
        jLayeredPane4.add(cbxGarantia, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setText("Antigüedad");
        jLabel3.setBounds(20, 123, 55, 14);
        jLayeredPane4.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxAntiguedad.setBounds(120, 120, 200, 20);
        jLayeredPane4.add(cbxAntiguedad, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel31.setText("Notas");
        jLabel31.setBounds(20, 173, 28, 14);
        jLayeredPane4.add(jLabel31, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxCargado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CARGADO", "DESCARGADO" }));
        cbxCargado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCargadoActionPerformed(evt);
            }
        });
        cbxCargado.setBounds(120, 145, 200, 20);
        jLayeredPane4.add(cbxCargado, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel30.setText("Gestor");
        jLabel30.setBounds(360, 23, 32, 14);
        jLayeredPane4.add(jLabel30, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxGestor.setBounds(450, 20, 200, 20);
        jLayeredPane4.add(cbxGestor, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel12.setText("Intención pago");
        jLabel12.setBounds(360, 48, 72, 14);
        jLayeredPane4.add(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxIntencionPago.setBounds(450, 45, 200, 20);
        jLayeredPane4.add(cbxIntencionPago, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel44.setText("Razón deuda");
        jLabel44.setBounds(360, 73, 63, 14);
        jLayeredPane4.add(jLabel44, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxRazonDeuda.setBounds(450, 70, 200, 20);
        jLayeredPane4.add(cbxRazonDeuda, javax.swing.JLayeredPane.DEFAULT_LAYER);

        areNotas.setColumns(20);
        areNotas.setLineWrap(true);
        areNotas.setRows(5);
        jScrollPane21.setViewportView(areNotas);

        jScrollPane21.setBounds(20, 200, 630, 200);
        jLayeredPane4.add(jScrollPane21, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel45.setText("Cargado");
        jLabel45.setBounds(20, 148, 41, 14);
        jLayeredPane4.add(jLabel45, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel32.setText("Aumentos");
        jLabel32.setBounds(360, 98, 48, 14);
        jLayeredPane4.add(jLabel32, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnAumentos.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnAumentos.setBounds(450, 95, 200, 20);
        jLayeredPane4.add(spnAumentos, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel33.setText("Descuentos");
        jLabel33.setBounds(360, 123, 56, 14);
        jLayeredPane4.add(jLabel33, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnDescuentos.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnDescuentos.setBounds(450, 120, 200, 20);
        jLayeredPane4.add(spnDescuentos, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagos"));

        jLabel2.setText("Pagos");
        jLabel2.setBounds(20, 23, 29, 14);
        jLayeredPane5.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnPagos.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnPagos.setBounds(140, 20, 150, 20);
        jLayeredPane5.add(spnPagos, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel35.setText("Fecha Último Pago");
        jLabel35.setBounds(20, 48, 88, 14);
        jLayeredPane5.add(jLabel35, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccFechaUltimoPago.setNothingAllowed(false);
        dccFechaUltimoPago.setLocale(new java.util.Locale("es", "GT", ""));
        dccFechaUltimoPago.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaUltimoPago.setBounds(140, 45, 150, 20);
        jLayeredPane5.add(dccFechaUltimoPago, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel36.setText("Monto Último Pago");
        jLabel36.setBounds(20, 73, 89, 14);
        jLayeredPane5.add(jLabel36, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnMontoUltimoPago.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnMontoUltimoPago.setBounds(140, 70, 150, 20);
        jLayeredPane5.add(spnMontoUltimoPago, javax.swing.JLayeredPane.DEFAULT_LAYER);

        chkProximoPago.setText("Próximo Pago");
        chkProximoPago.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkProximoPagoItemStateChanged(evt);
            }
        });
        chkProximoPago.setBounds(20, 98, 91, 23);
        jLayeredPane5.add(chkProximoPago, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel37.setText("Fecha Próximo Pago");
        jLabel37.setBounds(20, 123, 97, 14);
        jLayeredPane5.add(jLabel37, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccFechaProximoPago.setNothingAllowed(false);
        dccFechaProximoPago.setLocale(new java.util.Locale("es", "GT", ""));
        dccFechaProximoPago.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaProximoPago.setBounds(140, 120, 150, 20);
        jLayeredPane5.add(dccFechaProximoPago, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnCuotaConvenio.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnCuotaConvenio.setBounds(140, 145, 150, 20);
        jLayeredPane5.add(spnCuotaConvenio, javax.swing.JLayeredPane.DEFAULT_LAYER);

        btnConvenio.setText("Convenio");
        btnConvenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvenioActionPerformed(evt);
            }
        });
        btnConvenio.setBounds(20, 180, 100, 23);
        jLayeredPane5.add(btnConvenio, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton2.setText("Recordatorio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.setBounds(140, 180, 100, 23);
        jLayeredPane5.add(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        AreConvenioPactado.setColumns(20);
        AreConvenioPactado.setLineWrap(true);
        AreConvenioPactado.setRows(5);
        jScrollPane2.setViewportView(AreConvenioPactado);

        jScrollPane2.setBounds(20, 250, 270, 150);
        jLayeredPane5.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel46.setText("Cuota Pactada");
        jLabel46.setBounds(20, 148, 71, 14);
        jLayeredPane5.add(jLabel46, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtTipoConvenio.setBackground(new java.awt.Color(-256,true));
        txtTipoConvenio.setEditable(false);
        txtTipoConvenio.setBounds(20, 225, 270, 20);
        jLayeredPane5.add(txtTipoConvenio, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel43.setText("Convenio pactado");
        jLabel43.setBounds(20, 210, 87, 14);
        jLayeredPane5.add(jLabel43, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("General", jPanel25);

        jLayeredPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("-"));

        jLabel15.setText("Teléfono casa");
        jLabel15.setBounds(20, 23, 67, 14);
        jLayeredPane6.add(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtTelefonoCasa.setBounds(115, 20, 200, 20);
        jLayeredPane6.add(txtTelefonoCasa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setText("Teléfono celular");
        jLabel9.setBounds(335, 23, 76, 14);
        jLayeredPane6.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtTelefonoCelular.setBounds(435, 20, 200, 20);
        jLayeredPane6.add(txtTelefonoCelular, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel20.setText("Correo electrónico");
        jLabel20.setBounds(655, 23, 88, 14);
        jLayeredPane6.add(jLabel20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtCorreoElectronico.setBounds(760, 20, 200, 20);
        jLayeredPane6.add(txtCorreoElectronico, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel22.setText("Lugar de trabajo");
        jLabel22.setBounds(20, 48, 80, 14);
        jLayeredPane6.add(jLabel22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtLugarTrabajo.setBounds(115, 45, 200, 20);
        jLayeredPane6.add(txtLugarTrabajo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel21.setText("Contacto trabajo");
        jLabel21.setBounds(335, 48, 82, 14);
        jLayeredPane6.add(jLabel21, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtDireccionTrabajo.setBounds(435, 45, 200, 20);
        jLayeredPane6.add(txtDireccionTrabajo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel24.setText("Teléfono trabajo");
        jLabel24.setBounds(655, 48, 80, 14);
        jLayeredPane6.add(jLabel24, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtTelefonoTrabajo.setBounds(760, 45, 200, 20);
        jLayeredPane6.add(txtTelefonoTrabajo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel52.setText("Dirección");
        jLabel52.setBounds(20, 90, 43, 14);
        jLayeredPane6.add(jLabel52, javax.swing.JLayeredPane.DEFAULT_LAYER);

        areDireccion.setColumns(20);
        areDireccion.setLineWrap(true);
        areDireccion.setRows(5);
        jScrollPane22.setViewportView(areDireccion);

        jScrollPane22.setBounds(20, 110, 940, 100);
        jLayeredPane6.add(jScrollPane22, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Contacto", jPanel26);

        jLayeredPane7.setBorder(javax.swing.BorderFactory.createTitledBorder("-"));

        jLabel53.setText("Procuración");
        jLabel53.setBounds(20, 150, 56, 14);
        jLayeredPane7.add(jLabel53, javax.swing.JLayeredPane.DEFAULT_LAYER);

        areDescripcionProcuracion.setColumns(20);
        areDescripcionProcuracion.setLineWrap(true);
        areDescripcionProcuracion.setRows(5);
        areDescripcionProcuracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areDescripcionProcuracionKeyReleased(evt);
            }
        });
        jScrollPane16.setViewportView(areDescripcionProcuracion);

        jScrollPane16.setBounds(20, 170, 600, 100);
        jLayeredPane7.add(jScrollPane16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel14.setText("Situación caso");
        jLabel14.setBounds(20, 20, 68, 14);
        jLayeredPane7.add(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        areSituacionCaso.setColumns(20);
        areSituacionCaso.setLineWrap(true);
        areSituacionCaso.setRows(5);
        areSituacionCaso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                areSituacionCasoKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(areSituacionCaso);

        jScrollPane10.setBounds(20, 40, 600, 100);
        jLayeredPane7.add(jScrollPane10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel49.setText("Razón Notificación");
        jLabel49.setBounds(20, 280, 88, 14);
        jLayeredPane7.add(jLabel49, javax.swing.JLayeredPane.DEFAULT_LAYER);

        areRazonNotificacion.setColumns(20);
        areRazonNotificacion.setLineWrap(true);
        areRazonNotificacion.setRows(5);
        jScrollPane17.setViewportView(areRazonNotificacion);

        jScrollPane17.setBounds(20, 300, 600, 100);
        jLayeredPane7.add(jScrollPane17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane8.setBorder(javax.swing.BorderFactory.createTitledBorder("Juicio"));

        jLabel13.setText("Procurador");
        jLabel13.setBounds(20, 23, 53, 14);
        jLayeredPane8.add(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxProcurador.setBounds(130, 20, 190, 20);
        jLayeredPane8.add(cbxProcurador, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel16.setText("Fecha presentación");
        jLabel16.setBounds(20, 48, 94, 14);
        jLayeredPane8.add(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccFechaJuicio.setNothingAllowed(false);
        dccFechaJuicio.setLocale(new java.util.Locale("es", "GT", ""));
        dccFechaJuicio.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaJuicio.setBounds(130, 45, 190, 20);
        jLayeredPane8.add(dccFechaJuicio, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel17.setText("Juzgado");
        jLabel17.setBounds(20, 73, 40, 14);
        jLayeredPane8.add(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxJuzgado.setBounds(130, 70, 190, 20);
        jLayeredPane8.add(cbxJuzgado, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel19.setText("No. Juicio");
        jLabel19.setBounds(20, 98, 46, 14);
        jLayeredPane8.add(jLabel19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtNoJuicio.setBounds(130, 95, 190, 20);
        jLayeredPane8.add(txtNoJuicio, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel39.setText("Notificador");
        jLabel39.setBounds(20, 223, 52, 14);
        jLayeredPane8.add(jLabel39, javax.swing.JLayeredPane.DEFAULT_LAYER);
        spnNotificador.setBounds(130, 220, 190, 20);
        jLayeredPane8.add(spnNotificador, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel40.setText("Abogado Deudor");
        jLabel40.setBounds(20, 198, 81, 14);
        jLayeredPane8.add(jLabel40, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtAbogadoDeudor.setBounds(130, 195, 190, 20);
        jLayeredPane8.add(txtAbogadoDeudor, javax.swing.JLayeredPane.DEFAULT_LAYER);

        labSumario.setText("Sumario");
        labSumario.setBounds(20, 173, 38, 14);
        jLayeredPane8.add(labSumario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        txtSumario.setBounds(130, 170, 190, 20);
        jLayeredPane8.add(txtSumario, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel42.setText("Memorial");
        jLabel42.setBounds(20, 248, 42, 14);
        jLayeredPane8.add(jLabel42, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccMemorial.setNothingAllowed(false);
        dccMemorial.setLocale(new java.util.Locale("es", "GT", ""));
        dccMemorial.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccMemorial.setBounds(130, 245, 190, 20);
        jLayeredPane8.add(dccMemorial, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel47.setText("Notificado");
        jLabel47.setBounds(20, 273, 48, 14);
        jLayeredPane8.add(jLabel47, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxNotificado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO", "D INCORRECTA", "NADIE ATIENDE", "NO VIVE LUGAR", "IMPOSIBLE", "FUERA PAIS", "FALLECIDO" }));
        cbxNotificado.setBounds(130, 270, 190, 20);
        jLayeredPane8.add(cbxNotificado, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel48.setText("Fecha Notif.");
        jLabel48.setBounds(20, 298, 59, 14);
        jLayeredPane8.add(jLabel48, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccFechaNotificacion.setNothingAllowed(false);
        dccFechaNotificacion.setLocale(new java.util.Locale("es", "GT", ""));
        dccFechaNotificacion.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaNotificacion.setBounds(130, 295, 190, 20);
        jLayeredPane8.add(dccFechaNotificacion, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setText("Monto Demanda");
        jLabel5.setBounds(20, 148, 78, 14);
        jLayeredPane8.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnMontoDemanda.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnMontoDemanda.setBounds(130, 145, 190, 20);
        jLayeredPane8.add(spnMontoDemanda, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setText("Anticipo");
        jLabel4.setBounds(20, 323, 38, 14);
        jLayeredPane8.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxAnticipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO", "SI", "COBRAR", "REPRE" }));
        cbxAnticipo.setBounds(130, 320, 190, 20);
        jLayeredPane8.add(cbxAnticipo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dccFechaAdmision.setNothingAllowed(false);
        dccFechaAdmision.setLocale(new java.util.Locale("es", "GT", ""));
        dccFechaAdmision.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        dccFechaAdmision.setBounds(130, 120, 190, 20);
        jLayeredPane8.add(dccFechaAdmision, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel54.setText("Fecha admisión");
        jLabel54.setBounds(20, 123, 73, 14);
        jLayeredPane8.add(jLabel54, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Judicial", jPanel27);

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Otros"));

        chkPDF.setText("PDF");

        chkINV.setText("INV");

        chkMAYCOM.setText("MAY");

        chkNITS.setText("NIT");

        jLabel10.setText("Fecha nacimiento");

        dccFechaNac.setNothingAllowed(false);
        dccFechaNac.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel8.setText("DPI");

        jLabel7.setText("Nit");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10)
                .addGap(17, 17, 17)
                .addComponent(dccFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(chkPDF)
                .addGap(5, 5, 5)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkINV)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(chkMAYCOM)))
                .addGap(3, 3, 3)
                .addComponent(chkNITS))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addGap(83, 83, 83)
                .addComponent(txtDpi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addGap(87, 87, 87)
                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel10))
                    .addComponent(dccFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPDF)
                    .addComponent(chkINV)
                    .addComponent(chkMAYCOM)
                    .addComponent(chkNITS))
                .addGap(2, 2, 2)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(txtDpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Perfil", jPanel30);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel4);

        panelGestion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                panelGestionStateChanged(evt);
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

        btnGestionInsertar.setText("Insertar");
        btnGestionInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionInsertarActionPerformed(evt);
            }
        });

        lbNoGestionesCobros.setText("No. de Gestiones: ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(lbNoGestionesCobros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 880, Short.MAX_VALUE)
                        .addComponent(btnGestionInsertar)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNoGestionesCobros)
                    .addComponent(btnGestionInsertar))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBitacora.addTab("Cobros", jPanel11);

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

        areAdministracionDescripcion.setColumns(20);
        areAdministracionDescripcion.setRows(5);
        jScrollPane7.setViewportView(areAdministracionDescripcion);

        btnAdministracionInsertar.setText("Insertar");
        btnAdministracionInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdministracionInsertarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 963, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdministracionInsertar)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdministracionInsertar)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBitacora.addTab("Administracion", jPanel12);

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

        areJuridicoDescripcion.setColumns(20);
        areJuridicoDescripcion.setRows(5);
        jScrollPane9.setViewportView(areJuridicoDescripcion);

        btnJuridicoInsertar.setText("Insertar");
        btnJuridicoInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuridicoInsertarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnJuridicoInsertar)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnJuridicoInsertar)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBitacora.addTab("Juridico", jPanel13);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelBitacora)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBitacora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelGestion.addTab("Gestion", jPanel1);

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
        TblPagos.getColumnModel().getColumn(5).setHeaderValue("FECHA_REGISTRO");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnPagosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnPagosModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnPagosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnPagosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPagosAgregar)
                    .addComponent(btnPagosModificar)
                    .addComponent(btnPagosEliminar)
                    .addComponent(btnPagosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Pagos", jPanel3);

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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnPagosAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnPagosModificar1)
                        .addGap(6, 6, 6)
                        .addComponent(btnPagosEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnPagosVer1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPagosAgregar1)
                    .addComponent(btnPagosModificar1)
                    .addComponent(btnPagosEliminar1)
                    .addComponent(btnPagosVer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Recordatorio", jPanel9);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnJuiciosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnJuiciosModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnJuiciosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnJuiciosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnJuiciosAgregar)
                    .addComponent(btnJuiciosModificar)
                    .addComponent(btnJuiciosEliminar)
                    .addComponent(btnJuiciosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Juicios", jPanel2);

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

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(btnDescuentosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnDescuentosModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnDescuentosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnDescuentosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDescuentosAgregar)
                    .addComponent(btnDescuentosModificar)
                    .addComponent(btnDescuentosEliminar)
                    .addComponent(btnDescuentosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Descuentos", jPanel14);

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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnAumentosAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnAumentosModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnAumentosEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnAumentosVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAumentosAgregar)
                    .addComponent(btnAumentosModificar)
                    .addComponent(btnAumentosEliminar)
                    .addComponent(btnAumentosVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Aumentos", jPanel15);

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

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnFiadoresAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnFiadoresModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnFiadoresEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnFiadoresVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFiadoresAgregar)
                    .addComponent(btnFiadoresModificar)
                    .addComponent(btnFiadoresEliminar)
                    .addComponent(btnFiadoresVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Fiadores", jPanel16);

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
        TblReferencias.getColumnModel().getColumn(3).setHeaderValue("NOMBRE");
        TblReferencias.getColumnModel().getColumn(4).setHeaderValue("TELEFONO");
        TblReferencias.getColumnModel().getColumn(5).setHeaderValue("DIRECCION");
        TblReferencias.getColumnModel().getColumn(6).setHeaderValue("ZONA");
        TblReferencias.getColumnModel().getColumn(7).setHeaderValue("CORREO_ELECTRONICO");

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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(btnReferenciasAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnReferenciasModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnReferenciasEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnReferenciasVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReferenciasAgregar)
                    .addComponent(btnReferenciasModificar)
                    .addComponent(btnReferenciasEliminar)
                    .addComponent(btnReferenciasVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Referencias", jPanel17);

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

        btnDigitalizacionVer.setText("Ver");
        btnDigitalizacionVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigitalizacionVerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addComponent(btnDigitalizacionVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnDigitalizacionVer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Digitalización", jPanel18);

        TblConvenio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CONVENIO", "FECHA_CREACION", "ESTADO", "FECHA_PAGO", "TOTAL_DEUDA", "NO_CUOTAS", "FRECUENCIA", "CUOTA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btnConvenioAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnConvenioModificar)
                        .addGap(6, 6, 6)
                        .addComponent(btnConvenioEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnConvenioVer, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConvenioAgregar)
                    .addComponent(btnConvenioModificar)
                    .addComponent(btnConvenioEliminar)
                    .addComponent(btnConvenioVer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Convenio", jPanel19);

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

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelGestion.addTab("Cuenta relacionada", jPanel20);

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Caso"));

        jLabel6.setText("Nombre deudor");
        jLabel6.setBounds(20, 48, 74, 14);
        jLayeredPane1.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtNombre.setBounds(110, 45, 400, 20);
        jLayeredPane1.add(txtNombre, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setText("Actor");
        jLabel1.setBounds(20, 73, 26, 14);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxActor.setBounds(110, 70, 400, 20);
        jLayeredPane1.add(cbxActor, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel41.setText("Caso");
        jLabel41.setBounds(20, 23, 24, 14);
        jLayeredPane1.add(jLabel41, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel11.setText("Fiador");
        jLabel11.setBounds(20, 98, 30, 14);
        jLayeredPane1.add(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxFiador.setBounds(110, 95, 400, 20);
        jLayeredPane1.add(cbxFiador, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel25.setText("Monto Inicial");
        jLabel25.setBounds(550, 48, 60, 14);
        jLayeredPane1.add(jLabel25, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnMontoInicial.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnMontoInicial.setBounds(630, 45, 100, 20);
        jLayeredPane1.add(spnMontoInicial, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel23.setText("Moneda");
        jLabel23.setBounds(550, 73, 38, 14);
        jLayeredPane1.add(jLabel23, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "QUETZAL", "DOLLAR" }));
        cbxMoneda.setBounds(630, 70, 100, 20);
        jLayeredPane1.add(cbxMoneda, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel34.setText("Saldo");
        jLabel34.setBounds(550, 98, 26, 14);
        jLayeredPane1.add(jLabel34, javax.swing.JLayeredPane.DEFAULT_LAYER);

        spnSaldo.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spnSaldo.setBounds(630, 95, 100, 20);
        jLayeredPane1.add(spnSaldo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtCaso.setBackground(new java.awt.Color(-256,true));
        txtCaso.setEditable(false);
        txtCaso.setText("0");
        txtCaso.setBounds(110, 20, 90, 20);
        jLayeredPane1.add(txtCaso, javax.swing.JLayeredPane.DEFAULT_LAYER);

        chkCuentaPrincipalRelacion.setText("Cuenta principal relación");
        chkCuentaPrincipalRelacion.setEnabled(false);
        chkCuentaPrincipalRelacion.setBounds(20, 130, 143, 23);
        jLayeredPane1.add(chkCuentaPrincipalRelacion, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lbGestiones.setFont(new java.awt.Font("Tahoma", 1, 14));
        lbGestiones.setForeground(new java.awt.Color(255, 0, 0));
        lbGestiones.setText("GESTIONES");

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Situación extrajudicial"));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel50.setForeground(new java.awt.Color(51, 51, 255));
        jLabel50.setText("Estado");
        jLabel50.setBounds(20, 23, 38, 14);
        jLayeredPane2.add(jLabel50, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel51.setForeground(new java.awt.Color(51, 51, 255));
        jLabel51.setText("Status");
        jLabel51.setBounds(20, 48, 37, 14);
        jLayeredPane2.add(jLabel51, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxEstadoExtra.setForeground(new java.awt.Color(51, 51, 255));
        cbxEstadoExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoExtraActionPerformed(evt);
            }
        });
        cbxEstadoExtra.setBounds(70, 20, 200, 20);
        jLayeredPane2.add(cbxEstadoExtra, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxStatusExtra.setForeground(new java.awt.Color(51, 51, 255));
        cbxStatusExtra.setBounds(70, 45, 200, 20);
        jLayeredPane2.add(cbxStatusExtra, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Situación judicial"));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setForeground(new java.awt.Color(0, 153, 0));
        jLabel26.setText("Estado");
        jLabel26.setBounds(20, 23, 38, 14);
        jLayeredPane3.add(jLabel26, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxEstado.setForeground(new java.awt.Color(0, 153, 0));
        cbxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoActionPerformed(evt);
            }
        });
        cbxEstado.setBounds(70, 20, 200, 20);
        jLayeredPane3.add(cbxEstado, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel27.setForeground(new java.awt.Color(0, 153, 0));
        jLabel27.setText("Status");
        jLabel27.setBounds(20, 48, 37, 14);
        jLayeredPane3.add(jLabel27, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cbxStatus.setForeground(new java.awt.Color(0, 153, 0));
        cbxStatus.setBounds(70, 45, 200, 20);
        jLayeredPane3.add(cbxStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelGestion, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbGestiones, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCerrar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbGestiones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
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
            a.setTitle("GESTION OBLIGATORIA");
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

                String resultado = drive.modificar_deudor(
                        this.deudor,
                        DActor.obtener_indice(this.cbxActor.getSelectedItem().toString()),
                        this.cbxMoneda.getSelectedItem().toString(),
                        this.txtDpi.getText(),
                        this.txtNit.getText(),
                        this.dccFechaNac.getSelectedDate(),
                        this.txtNombre.getText(),
                        this.txtTelefonoCasa.getText(),
                        this.txtTelefonoCelular.getText(),
                        this.areDireccion.getText(),
                        this.dccFechaRecepcion.getSelectedDate(),
                        this.txtCorreoElectronico.getText(),
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
                        this.dccFechaProximoPago.getSelectedDate(),
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
                            this.dccFechaJuicio.getSelectedDate(),
                            this.areRazonNotificacion.getText(),
                            this.txtNoJuicio.getText(),
                            Integer.parseInt(this.spnNotificador.getValue().toString()),
                            this.txtAbogadoDeudor.getText(),
                            this.txtSumario.getText(),
                            this.dccMemorial.getSelectedDate(),
                            this.areDescripcionProcuracion.getText(),
                            this.cbxNotificado.getSelectedItem().toString(),
                            this.dccFechaNotificacion.getSelectedDate(),
                            Double.parseDouble(spnMontoDemanda.getValue().toString()));
                }

                String[] mensaje = resultado.split(",");
                JOptionPane.showMessageDialog(null, mensaje[1]);
            }
        } catch (NumberFormatException | HeadlessException ex) {
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

    private void panelGestionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panelGestionStateChanged
        if (this.panelGestion.getSelectedIndex() == 8) {
            com.lexcom.driver.Expediente DExpediente = new com.lexcom.driver.Expediente(this.conn, usuario);
            this.TblDigitalizacion.setModel(DExpediente.obtener_tabla_digitalizacion((DefaultTableModel) this.TblDigitalizacion.getModel(), this.deudor));
        }
    }//GEN-LAST:event_panelGestionStateChanged

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
        this.dccFechaUltimoPago.setEnabled(true);
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

        this.dccFechaNac.setSelectedDate(resultado.fecha_nacimiento);
        this.dccFechaRecepcion.setSelectedDate(resultado.fecha_recepcion);
        this.dccFechaUltimoPago.setSelectedDate(DExpediente.fecha_ultimo_pago(this.deudor));
        this.dccFechaProximoPago.setEnabled(true);
        this.dccFechaProximoPago.setSelectedDate(resultado.fecha_proximo_pago);
        
        this.spnMontoInicial.setValue(resultado.monto_inicial);
        this.spnAumentos.setValue(DExpediente.calcular_aumentos(this.deudor));
        this.spnDescuentos.setValue(DExpediente.calcular_descuentos(this.deudor));
        this.spnPagos.setValue(DExpediente.calcular_pagos(this.deudor));
        this.spnSaldo.setValue(resultado.saldo);
        this.spnMontoUltimoPago.setValue(DExpediente.monto_ultimo_pago(this.deudor));
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
        this.cbxFiador.setModel(DFiador.dar_lista_fiador(this.deudor));
        com.lexcom.driver.Juicio DJuicio = new com.lexcom.driver.Juicio(this.conn, this.usuario);
        Integer juicio = DJuicio.dar_juicio(this.deudor);

        if (juicio != 0) {
            com.lexcom.driver.Juicio Dresultado = DJuicio.obtener_eliminar(juicio);
            com.lexcom.driver.Juzgado DJuzgado = new com.lexcom.driver.Juzgado(this.conn, this.usuario);
            com.lexcom.driver.Usuario DProcurador = new com.lexcom.driver.Usuario(this.conn, this.usuario);
            this.cbxProcurador.setEnabled(true);
            this.cbxJuzgado.setEnabled(true);
            this.cbxNotificado.setEnabled(true);
            this.dccFechaJuicio.setEnabled(true);
            this.dccMemorial.setEnabled(true);
            this.dccFechaNotificacion.setEnabled(true);
            this.areRazonNotificacion.setEnabled(true);
            this.txtNoJuicio.setEnabled(true);
            this.txtAbogadoDeudor.setEnabled(true);
            this.txtSumario.setEnabled(true);
            this.spnNotificador.setEnabled(true);
            this.spnMontoDemanda.setEnabled(true);
            this.areDescripcionProcuracion.setEnabled(true);
            this.dccFechaAdmision.setEnabled(true);
            
            this.cbxProcurador.setSelectedItem(DProcurador.obtener_nombre(Dresultado.procurador));
            this.cbxJuzgado.setSelectedItem(DJuzgado.obtener_nombre(Dresultado.juzgado));
            this.cbxNotificado.setSelectedItem(Dresultado.deudor_notificado);
            this.dccFechaJuicio.setSelectedDate(Dresultado.fecha);
            this.dccMemorial.setSelectedDate(Dresultado.memorial);
            this.dccFechaNotificacion.setSelectedDate(Dresultado.fecha_notificacion);
            this.areRazonNotificacion.setText(Dresultado.razon_notificacion);
            this.txtNoJuicio.setText(Dresultado.no_juicio);
            this.txtAbogadoDeudor.setText(Dresultado.abogado_deudor);
            this.txtSumario.setText(Dresultado.sumario);
            this.spnNotificador.setValue(Dresultado.notificador);
            this.spnMontoDemanda.setValue(Dresultado.monto);
            this.areDescripcionProcuracion.setText(Dresultado.procuracion);
            this.dccFechaAdmision.setSelectedDate(Dresultado.fecha_admision_demanda);
        } else {
            this.cbxProcurador.setSelectedIndex(0);
            this.cbxJuzgado.setSelectedIndex(0);
            this.cbxNotificado.setSelectedIndex(0);
            this.dccFechaJuicio.setSelectedDate(Calendar.getInstance());
            this.dccMemorial.setSelectedDate(Calendar.getInstance());
            this.dccFechaNotificacion.setSelectedDate(Calendar.getInstance());
            this.areRazonNotificacion.setText("");
            this.txtNoJuicio.setText("");
            this.txtAbogadoDeudor.setText("");
            this.txtSumario.setText("");
            this.spnNotificador.setValue(0);
            this.spnMontoDemanda.setValue(0.00);
            this.areDescripcionProcuracion.setText("");
            this.cbxProcurador.setEnabled(false);
            this.cbxJuzgado.setEnabled(false);
            this.cbxNotificado.setEnabled(false);
            this.dccFechaJuicio.setEnabled(false);
            this.dccMemorial.setEnabled(false);
            this.dccFechaNotificacion.setEnabled(false);
            this.areRazonNotificacion.setEnabled(false);
            this.txtNoJuicio.setEnabled(false);
            this.txtAbogadoDeudor.setEnabled(false);
            this.txtSumario.setEnabled(false);
            this.spnNotificador.setEnabled(false);
            this.spnMontoDemanda.setEnabled(false);
            this.dccFechaAdmision.setEnabled(false);
        }

        this.dccFechaUltimoPago.setEnabled(false);
        this.spnAumentos.setEnabled(false);
        this.spnDescuentos.setEnabled(false);
        this.spnPagos.setEnabled(false);
        this.spnMontoUltimoPago.setEnabled(false);

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
            this.txtSumario.setEditable(false);
            this.dccMemorial.setEnabled(false);
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
        this.dccFechaAdmision.setEnabled(false);
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
    private javax.swing.JComboBox cbxFiador;
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
    private javax.swing.JCheckBox chkCuentaPrincipalRelacion;
    private javax.swing.JCheckBox chkINV;
    private javax.swing.JCheckBox chkMAYCOM;
    private javax.swing.JCheckBox chkNITS;
    private javax.swing.JCheckBox chkPDF;
    private javax.swing.JCheckBox chkProximoPago;
    private datechooser.beans.DateChooserCombo dccFechaAdmision;
    private datechooser.beans.DateChooserCombo dccFechaJuicio;
    private datechooser.beans.DateChooserCombo dccFechaNac;
    private datechooser.beans.DateChooserCombo dccFechaNotificacion;
    private datechooser.beans.DateChooserCombo dccFechaProximoPago;
    private datechooser.beans.DateChooserCombo dccFechaRecepcion;
    private datechooser.beans.DateChooserCombo dccFechaUltimoPago;
    private datechooser.beans.DateChooserCombo dccMemorial;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
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
    private javax.swing.JLabel labSumario;
    private javax.swing.JLabel lbGestiones;
    private javax.swing.JLabel lbNoGestionesCobros;
    private javax.swing.JTabbedPane panelBitacora;
    private javax.swing.JTabbedPane panelGestion;
    private javax.swing.JSpinner spnAumentos;
    private javax.swing.JSpinner spnCuotaConvenio;
    private javax.swing.JSpinner spnDescuentos;
    private javax.swing.JSpinner spnMontoDemanda;
    private javax.swing.JSpinner spnMontoInicial;
    private javax.swing.JSpinner spnMontoUltimoPago;
    private javax.swing.JSpinner spnNotificador;
    private javax.swing.JSpinner spnPagos;
    private javax.swing.JSpinner spnSaldo;
    private javax.swing.JTextField txtAbogadoDeudor;
    private javax.swing.JTextField txtCaso;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDireccionTrabajo;
    private javax.swing.JTextField txtDpi;
    private javax.swing.JTextField txtLugarTrabajo;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNoCuenta;
    private javax.swing.JTextField txtNoJuicio;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOtroNoCuenta;
    private javax.swing.JTextField txtSumario;
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
