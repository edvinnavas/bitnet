package com.lexcom;

import java.awt.HeadlessException;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Deudor extends javax.swing.JDialog {

    Connection conn;
    Integer usuario;
    Integer accion;
    Integer seleccion;
    
    public Deudor(java.awt.Frame parent, boolean modal, Connection conn, Integer usuario, Integer accion) {
        super(parent, modal);
        this.conn = conn;
        this.usuario = usuario;
        this.accion = accion;
        initComponents();
        
        com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
        this.cbxActor.setModel(DActor.dar_lista());
        
        com.lexcom.driver.Usuario DGestor = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        this.cbxGestor.setModel(DGestor.dar_lista_gestores());
        
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        this.cbxEstado.setModel(DEstado.dar_lista());
        
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        this.cbxStatus.setModel(DStatus.dar_lista());
        
        com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
        this.cbxGarantia.setModel(DGarantia.dar_lista());
        
        if(accion == 2) {
            this.txtCorreoElectronico.setEditable(false);
            this.txtDireccion.setEditable(false);
            this.txtDireccionTrabajo.setEditable(false);
            this.txtDpi.setEditable(false);
            this.txtLugarTrabajo.setEditable(false);
            this.txtNacionalidad.setEditable(false);
            this.txtNit.setEditable(false);
            this.txtNoCuenta.setEditable(false);
            this.txtNombre.setEditable(false);
            this.txtProfesion.setEditable(false);
            this.txtTelefonoCasa.setEditable(false);
            this.txtTelefonoCelular.setEditable(false);
            this.txtTelefonoTrabajo.setEditable(false);
            this.txtOtroNoCuenta.setEditable(false);
            
            this.cbxActor.setEnabled(false);
            this.cbxCargado.setEnabled(false);
            this.cbxDepartamento.setEnabled(false);
            this.cbxEstado.setEnabled(false);
            this.cbxEstadoCivil.setEnabled(false);
            this.cbxGarantia.setEnabled(false);
            this.cbxGestor.setEnabled(false);
            this.cbxMoneda.setEnabled(false);
            this.cbxSexo.setEnabled(false);
            this.cbxStatus.setEnabled(false);
            this.cbxpais.setEnabled(false);
            this.cbxCosecha.setEnabled(false);
            this.cbxAnticipo.setEnabled(false);
            this.cbxAntiguedad.setEnabled(false);
            
            this.spnMontoInicial.setEnabled(false);
            this.spnZona.setEnabled(false);
            this.spnCaso.setEnabled(false);
            this.spnSaldo.setEnabled(false);
            
            this.chkPDF.setEnabled(false);
            this.chkINV.setEnabled(false);
            this.chkMAYCOM.setEnabled(false);
            this.chkNITS.setEnabled(false);
            
            this.areDescripcion.setEditable(false);
            this.areDescripcionAdicional.setEditable(false);
            
            this.btnAceptar.setVisible(false);
        }
        this.cbxActor.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDpi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dccFechaNac = new datechooser.beans.DateChooserCombo();
        jLabel11 = new javax.swing.JLabel();
        cbxpais = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        cbxDepartamento = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox();
        txtNacionalidad = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        spnZona = new javax.swing.JSpinner();
        cbxEstadoCivil = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        txtProfesion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTelefonoCasa = new javax.swing.JTextField();
        txtTelefonoCelular = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        chkNITS = new javax.swing.JCheckBox();
        chkMAYCOM = new javax.swing.JCheckBox();
        chkINV = new javax.swing.JCheckBox();
        chkPDF = new javax.swing.JCheckBox();
        txtCorreoElectronico = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtLugarTrabajo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtDireccionTrabajo = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtTelefonoTrabajo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxActor = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        dccFechaIngreso = new datechooser.beans.DateChooserCombo();
        jLabel28 = new javax.swing.JLabel();
        txtNoCuenta = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cbxMoneda = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        spnMontoInicial = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        cbxStatus = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        cbxGarantia = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        spnCaso = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        cbxCosecha = new javax.swing.JComboBox();
        txtOtroNoCuenta = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        dccFechaRecepcion = new datechooser.beans.DateChooserCombo();
        jLabel32 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areDescripcionAdicional = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areDescripcion = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        cbxGestor = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        cbxCargado = new javax.swing.JComboBox();
        cbxAnticipo = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        cbxAntiguedad = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        spnSaldo = new javax.swing.JSpinner();
        jLabel37 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DEUDOR");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales"));

        jLabel3.setText("Nombre");

        jLabel8.setText("DPI");

        jLabel4.setText("Nit");

        jLabel10.setText("Fecha de nacimiento");

        dccFechaNac.setNothingAllowed(false);
        dccFechaNac.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel11.setText("Pais");

        cbxpais.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guatemala", "Afganistán", "Akrotiri", "Albania", "Alemania", "Andorra", "Angola", "Anguila", "Antártida", "Antigua y Barbuda", "Antillas Neerlandesas", "Arabia Saudí", "Arctic Ocean", "Argelia", "Argentina", "Armenia", "Aruba", "Ashmore and Cartier Islands", "Atlantic Ocean", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bahráin", "Bangladesh", "Barbados", "Bélgica", "Belice", "Benín", "Bermudas", "Bielorrusia", "Birmania; Myanmar", "Bolivia", "Bosnia y Hercegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Chad", "Chile", "China", "Chipre", "Clipperton Island", "Colombia", "Comoras", "Congo", "Coral Sea Islands", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dhekelia", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "El Vaticano", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Gaza Strip", "Georgia", "Ghana", "Gibraltar", "Granada", "Grecia", "Groenlandia", "Guam", "Guernsey", "Guinea", "Guinea Ecuatorial", "Guinea-Bissau", "Guyana", "Haití", "Honduras", "Hong Kong", "Hungría", "India", "Indian Ocean", "Indonesia", "Irán", "Iraq", "Irlanda", "Isla Bouvet", "Isla Christmas", "Isla Norfolk", "Islandia", "Islas Caimán", "Islas Cocos", "Islas Cook", "Islas Feroe", "Islas Georgia del Sur y Sandwich del Sur", "Islas Heard y McDonald", "Islas Malvinas", "Islas Marianas del Norte", "Islas Marshall", "Islas Pitcairn", "Islas Salomón", "Islas Turcas y Caicos", "Islas Vírgenes Americanas", "Islas Vírgenes Británicas", "Israel", "Italia", "Jamaica", "Jan Mayen", "Japón", "Jersey", "Jordania", "Kazajistán", "Kenia", "Kirguizistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Macao", "Macedonia", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Man, Isle of", "Marruecos", "Mauricio", "Mauritania", "Mayotte", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Montserrat", "Mozambique", "Mundo", "Namibia", "Nauru", "Navassa Island", "Nepal", "Nicaragua", "Níger", "Nigeria", "Niue", "Noruega", "Nueva Caledonia", "Nueva Zelanda", "Omán", "Pacific Ocean", "Países Bajos", "Pakistán", "Palaos", "Panamá", "Papúa-Nueva Guinea", "Paracel Islands", "Paraguay", "Perú", "Polinesia Francesa", "Polonia", "Portugal", "Puerto Rico", "Qatar", "Reino Unido", "República Centroafricana", "República Checa", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Sáhara Occidental", "Samoa", "Samoa Americana", "San Cristóbal y Nieves", "San Marino", "San Pedro y Miquelón", "San Vicente y las Granadinas", "Santa Helena", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Southern Ocean", "Spratly Islands", "Sri Lanka", "Suazilandia", "Sudáfrica", "Sudán", "Suecia", "Suiza", "Surinam", "Svalbard y Jan Mayen", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Territorio Británico del Océano Indico", "Territorios Australes Franceses", "Timor Oriental", "Togo", "Tokelau", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Unión Europea", "Uruguay", "Uzbekistán", "Vanuatu", "Venezuela", "Vietnam", "Wake Island", "Wallis y Futuna", "West Bank", "Yemen", "Yibuti", "Zambia", "Zimbabue" }));

        jLabel19.setText("Departamento");

        cbxDepartamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Guatemala", "Alta Verapaz", "Baja Verapaz", "Chimaltenango", "Chiquimula", "Petén", "El Progreso", "Quiché", "Escuintla", "Huehuetenango", "Izabal", "Jalapa", "Jutiapa", "Quetzaltenango", "Retalhuleu", "Sacatepéquez", "San Marcos", "Santa Rosa", "Sololá", "Suchitepéquez", "Totonicapán", "Zacapa", "Otro" }));

        jLabel9.setText("Sexo");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMENINO", "-" }));

        jLabel16.setText("Nacionalidad");

        jLabel5.setText("Dirección");

        jLabel17.setText("Zona");

        spnZona.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        cbxEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SOLTERO", "CASADO", "-" }));

        jLabel14.setText("Estado civil");

        jLabel12.setText("Profesión");

        jLabel7.setText("Teléfono Casa");

        jLabel20.setText("Teléfono Celular");

        chkNITS.setText("NIT");

        chkMAYCOM.setText("MAY");

        chkINV.setText("INV");

        chkPDF.setText("PDF");

        jLabel15.setText("Correo electrónico");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chkPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkINV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkMAYCOM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNITS))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel4))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNit, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(dccFechaNac, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(txtDpi, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel19))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxDepartamento, 0, 184, Short.MAX_VALUE)
                            .addComponent(cbxpais, 0, 184, Short.MAX_VALUE)
                            .addComponent(cbxSexo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 184, Short.MAX_VALUE)
                            .addComponent(cbxEstadoCivil, javax.swing.GroupLayout.Alignment.TRAILING, 0, 184, Short.MAX_VALUE)
                            .addComponent(txtProfesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel5)
                            .addComponent(jLabel17)
                            .addComponent(jLabel7)
                            .addComponent(jLabel20)
                            .addComponent(jLabel15))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(txtNacionalidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(spnZona, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(dccFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProfesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbxpais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbxDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkNITS)
                    .addComponent(chkMAYCOM)
                    .addComponent(chkINV)
                    .addComponent(chkPDF))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Trabajo"));

        jLabel22.setText("Lugar de trabajo");

        jLabel21.setText("Contacto trabajo");

        jLabel24.setText("Teléfono trabajo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLugarTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(txtTelefonoTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .addComponent(txtDireccionTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtLugarTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtDireccionTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Expediente"));

        jLabel1.setText("Actor");

        jLabel13.setText("Fecha ingreso");

        dccFechaIngreso.setNothingAllowed(false);
        dccFechaIngreso.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel28.setText("No. Cuenta");

        jLabel18.setText("Moneda");

        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "QUETZAL", "DOLLAR" }));

        jLabel23.setText("Monto Inicial");

        spnMontoInicial.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        jLabel26.setText("Estado");

        jLabel27.setText("Status");

        jLabel29.setText("Garantía");

        jLabel2.setText("Caso");

        jLabel6.setText("Cosecha");

        cbxCosecha.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 antes 31 dic 2008", "2009 1er t", "2009 2do t", "2009 3er t", "2009 4to t", "2010 1er t", "2010 2do t", "2010 3er t", "2010 4to t", "2011 1er t", "2011 2do t", "2011 3er t", "2011 4to t", "2012 1er t", "2012 2do t", "2012 3er t", "2012 4to t", "2013 1er t", "2013 2do t", "2013 3er t", "2013 4to t", "2014 1er t", "2014 2do t", "2014 3er t", "2014 4to t", "2015 1er t", "2015 2do t", "2015 3er t", "2015 4to t", "2016 1er t", "2016 2do t", "2016 3er t", "2016 4to t", "2017 1er t", "2017 2do t", "2017 3er t", "2017 4to t", "2018 1er t", "2018 2do t", "2018 3er t", "2018 4to t", "2019 1er t", "2019 2do t", "2019 3er t", "2019 4to t" }));
        cbxCosecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCosechaActionPerformed(evt);
            }
        });

        jLabel30.setText("Otro No. Cuenta");

        dccFechaRecepcion.setNothingAllowed(false);
        dccFechaRecepcion.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel32.setText("Fecha Recepción");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel13)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel28)
                    .addComponent(jLabel18)
                    .addComponent(jLabel1)
                    .addComponent(jLabel32))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dccFechaRecepcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(cbxActor, 0, 233, Short.MAX_VALUE)
                    .addComponent(txtNoCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(dccFechaIngreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(spnCaso, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(cbxCosecha, 0, 233, Short.MAX_VALUE)
                    .addComponent(cbxMoneda, javax.swing.GroupLayout.Alignment.TRAILING, 0, 233, Short.MAX_VALUE)
                    .addComponent(txtOtroNoCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(cbxGarantia, javax.swing.GroupLayout.Alignment.TRAILING, 0, 233, Short.MAX_VALUE)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, 233, Short.MAX_VALUE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.Alignment.TRAILING, 0, 233, Short.MAX_VALUE)
                    .addComponent(spnMontoInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxActor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnCaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxCosecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbxMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(spnMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cbxGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOtroNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dccFechaRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        areDescripcionAdicional.setColumns(20);
        areDescripcionAdicional.setRows(5);
        jScrollPane1.setViewportView(areDescripcionAdicional);

        jLabel34.setText("Descripción");

        jLabel35.setText("Descripción Adicional");

        areDescripcion.setColumns(20);
        areDescripcion.setRows(5);
        jScrollPane2.setViewportView(areDescripcion);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addComponent(jLabel35)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Cuenta"));

        jLabel25.setText("Gestor");

        jLabel31.setText("Cargado");

        cbxCargado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CARGADO", "DESCARGADO" }));

        cbxAnticipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO", "SI", "COBRAR", "REPRE" }));

        jLabel33.setText("Anticipo");

        cbxAntiguedad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ORO", "PLATA", "BRONCE" }));

        jLabel36.setText("Antiguedad");

        spnSaldo.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        jLabel37.setText("Saldo");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel25)
                    .addComponent(jLabel33)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxAnticipo, 0, 191, Short.MAX_VALUE)
                    .addComponent(cbxGestor, 0, 191, Short.MAX_VALUE)
                    .addComponent(cbxCargado, 0, 191, Short.MAX_VALUE)
                    .addComponent(cbxAntiguedad, 0, 191, Short.MAX_VALUE)
                    .addComponent(spnSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(cbxGestor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(cbxCargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Generales", jPanel1);

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
                .addContainerGap(871, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
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

    private void cbxCosechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCosechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCosechaActionPerformed

    private void insertar() {
        try {
            com.lexcom.driver.Deudor drive = new com.lexcom.driver.Deudor(this.conn, this.usuario);

            com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
            com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(this.conn, this.usuario);
            com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
            com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
            com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
            String PDF = "";
            if(this.chkPDF.isSelected()) { PDF = "SI"; } else { PDF = "NO"; }
            String INV = "";
            if(this.chkINV.isSelected()) { INV = "SI"; } else { INV = "NO"; }
            String MAYCOM = "";
            if(this.chkMAYCOM.isSelected()) { MAYCOM = "SI"; } else { MAYCOM = "NO"; }
            String NITS = "";
            if(this.chkNITS.isSelected()) { NITS = "SI"; } else { NITS = "NO"; }

            String resultado = drive.insertar(
                    DActor.obtener_indice(this.cbxActor.getSelectedItem().toString()),
                    this.cbxMoneda.getSelectedItem().toString(),
                    this.txtDpi.getText(),
                    this.txtNit.getText(),
                    this.dccFechaNac.getSelectedDate(),
                    this.txtNombre.getText(),
                    this.txtNacionalidad.getText(),
                    this.txtTelefonoCasa.getText(),
                    this.txtTelefonoCelular.getText(),
                    this.txtDireccion.getText(),
                    Integer.parseInt(this.spnZona.getValue().toString()),
                    this.cbxpais.getSelectedItem().toString(),
                    this.cbxDepartamento.getSelectedItem().toString(),
                    this.cbxSexo.getSelectedItem().toString(),
                    this.cbxEstadoCivil.getSelectedItem().toString(),
                    this.dccFechaIngreso.getSelectedDate(),
                    this.txtProfesion.getText(),
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
                    this.areDescripcion.getText(),
                    1,
                    Integer.parseInt(this.spnCaso.getValue().toString()),
                    PDF,
                    INV,
                    MAYCOM,
                    NITS,
                    this.cbxCosecha.getSelectedItem().toString(),
                    this.txtOtroNoCuenta.getText(),
                    this.areDescripcionAdicional.getText(),
                    this.dccFechaRecepcion.getSelectedDate(),
                    this.cbxAnticipo.getSelectedItem().toString(),
                    this.cbxAntiguedad.getSelectedItem().toString(),
                    Double.parseDouble(this.spnSaldo.getValue().toString()),
                    "-",
                    0.00,
                    "-",
                    "-",
                    "NO",
                    1,
                    1,
                    4);
            String[] mensaje = resultado.split(",");
            JOptionPane.showMessageDialog(null, mensaje[1]);
            if (mensaje[0].equals("1")) {
                this.dispose();
            }
        } catch (NumberFormatException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    
    private void modificar() {
        try {
            com.lexcom.driver.Deudor drive = new com.lexcom.driver.Deudor(this.conn, this.usuario);

            com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
            com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(this.conn, this.usuario);
            com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
            com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
            com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
            String PDF = "";
            if(this.chkPDF.isSelected()) { PDF = "SI"; } else { PDF = "NO"; }
            String INV = "";
            if(this.chkINV.isSelected()) { INV = "SI"; } else { INV = "NO"; }
            String MAYCOM = "";
            if(this.chkMAYCOM.isSelected()) { MAYCOM = "SI"; } else { MAYCOM = "NO"; }
            String NITS = "";
            if(this.chkNITS.isSelected()) { NITS = "SI"; } else { NITS = "NO"; }
            
            String resultado = drive.modificar(
                    this.seleccion,
                    DActor.obtener_indice(this.cbxActor.getSelectedItem().toString()),
                    this.cbxMoneda.getSelectedItem().toString(),
                    this.txtDpi.getText(),
                    this.txtNit.getText(),
                    this.dccFechaNac.getSelectedDate(),
                    this.txtNombre.getText(),
                    this.txtNacionalidad.getText(),
                    this.txtTelefonoCasa.getText(),
                    this.txtTelefonoCelular.getText(),
                    this.txtDireccion.getText(),
                    Integer.parseInt(this.spnZona.getValue().toString()),
                    this.cbxpais.getSelectedItem().toString(),
                    this.cbxDepartamento.getSelectedItem().toString(),
                    this.cbxSexo.getSelectedItem().toString(),
                    this.cbxEstadoCivil.getSelectedItem().toString(),
                    this.dccFechaIngreso.getSelectedDate(),
                    this.txtProfesion.getText(),
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
                    this.areDescripcion.getText(),
                    Integer.parseInt(this.spnCaso.getValue().toString()),
                    PDF,
                    INV,
                    MAYCOM,
                    NITS,
                    this.cbxCosecha.getSelectedItem().toString(),
                    this.txtOtroNoCuenta.getText(),
                    this.areDescripcionAdicional.getText(),
                    this.dccFechaRecepcion.getSelectedDate(),
                    this.cbxAnticipo.getSelectedItem().toString(),
                    this.cbxAntiguedad.getSelectedItem().toString(),
                    Double.parseDouble(this.spnSaldo.getValue().toString()),
                    "-",
                    0.00,
                    "-",
                    "-",
                    "NO",
                    1,
                    1,
                    4);
            String[] mensaje = resultado.split(",");
            JOptionPane.showMessageDialog(null, mensaje[1]);
            if (mensaje[0].equals("1")) {
                this.dispose();
            }
        } catch (NumberFormatException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    
    public void cargar(Integer seleccion) {
        this.seleccion = seleccion;
        com.lexcom.driver.Deudor drive = new com.lexcom.driver.Deudor(this.conn, this.usuario);
        com.lexcom.driver.Deudor resultado = drive.obtener(seleccion);
        
        com.lexcom.driver.Actor DActor = new com.lexcom.driver.Actor(this.conn, this.usuario);
        com.lexcom.driver.Usuario DUsuario = new com.lexcom.driver.Usuario(this.conn, this.usuario);
        com.lexcom.driver.Estado DEstado = new com.lexcom.driver.Estado(this.conn, this.usuario);
        com.lexcom.driver.Status DStatus = new com.lexcom.driver.Status(this.conn, this.usuario);
        com.lexcom.driver.Garantia DGarantia = new com.lexcom.driver.Garantia(this.conn, this.usuario);
        
        this.cbxActor.setSelectedItem(DActor.obtener_nombre(resultado.actor));
        this.cbxMoneda.setSelectedItem(resultado.moneda);
        this.txtDpi.setText(resultado.dpi);
        this.txtNit.setText(resultado.nit);
        this.dccFechaNac.setSelectedDate(resultado.fecha_nacimiento);
        this.txtNombre.setText(resultado.nombre);
        this.txtNacionalidad.setText(resultado.nacionalidad);
        this.txtTelefonoCasa.setText(resultado.telefono_casa);
        this.txtTelefonoCelular.setText(resultado.telefono_celular);
        this.txtDireccion.setText(resultado.direccion);
        this.spnZona.setValue(resultado.zona);
        this.cbxpais.setSelectedItem(resultado.pais);
        this.cbxDepartamento.setSelectedItem(resultado.departamento);
        this.cbxSexo.setSelectedItem(resultado.sexo);
        this.cbxEstadoCivil.setSelectedItem(resultado.estado_civil);
        this.dccFechaIngreso.setSelectedDate(resultado.fecha_ingreso);
        this.txtProfesion.setText(resultado.profesion);
        this.txtCorreoElectronico.setText(resultado.correo_electronico);
        this.txtLugarTrabajo.setText(resultado.lugar_trabajo);
        this.txtDireccionTrabajo.setText(resultado.direccion_trabajo);
        this.txtTelefonoTrabajo.setText(resultado.telefono_trabajo);
        this.spnMontoInicial.setValue(resultado.monto_inicial);
        this.cbxGestor.setSelectedItem(DUsuario.obtener_nombre(resultado.gestor));
        this.cbxEstado.setSelectedItem(DEstado.obtener_nombre(resultado.sestado));
        this.cbxStatus.setSelectedItem(DStatus.obtener_nombre(resultado.estatus));
        this.txtNoCuenta.setText(resultado.no_cuenta);
        this.cbxGarantia.setSelectedItem(DGarantia.obtener_nombre(resultado.garantia));
        this.cbxCargado.setSelectedItem(resultado.cargado);
        this.areDescripcion.setText(resultado.descripcion);
        this.spnCaso.setValue(resultado.caso);
        if(resultado.PDF.equals("SI")) { this.chkPDF.setSelected(true); } else { this.chkPDF.setSelected(false); }
        if(resultado.INV.equals("SI")) { this.chkINV.setSelected(true); } else { this.chkINV.setSelected(false); }
        if(resultado.MAYCOM.equals("SI")) { this.chkMAYCOM.setSelected(true); } else { this.chkMAYCOM.setSelected(false); }
        if(resultado.NITS.equals("SI")) { this.chkNITS.setSelected(true); } else { this.chkNITS.setSelected(false); }
        this.cbxCosecha.setSelectedItem(resultado.cosecha);
        this.txtOtroNoCuenta.setText(resultado.no_cuenta_otro);
        this.areDescripcionAdicional.setText(resultado.descripcion_adicional);
        this.dccFechaRecepcion.setSelectedDate(resultado.fecha_recepcion);
        this.cbxAnticipo.setSelectedItem(resultado.anticipo);
        this.cbxAntiguedad.setSelectedItem(resultado.antiguedad);
        this.spnSaldo.setValue(resultado.saldo);
        
        if(accion == 2) {
            this.dccFechaIngreso.setEnabled(false);
            this.dccFechaNac.setEnabled(false);
            this.dccFechaRecepcion.setEnabled(false);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areDescripcion;
    private javax.swing.JTextArea areDescripcionAdicional;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxActor;
    private javax.swing.JComboBox cbxAnticipo;
    private javax.swing.JComboBox cbxAntiguedad;
    private javax.swing.JComboBox cbxCargado;
    private javax.swing.JComboBox cbxCosecha;
    private javax.swing.JComboBox cbxDepartamento;
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JComboBox cbxEstadoCivil;
    private javax.swing.JComboBox cbxGarantia;
    private javax.swing.JComboBox cbxGestor;
    private javax.swing.JComboBox cbxMoneda;
    private javax.swing.JComboBox cbxSexo;
    private javax.swing.JComboBox cbxStatus;
    private javax.swing.JComboBox cbxpais;
    private javax.swing.JCheckBox chkINV;
    private javax.swing.JCheckBox chkMAYCOM;
    private javax.swing.JCheckBox chkNITS;
    private javax.swing.JCheckBox chkPDF;
    private datechooser.beans.DateChooserCombo dccFechaIngreso;
    private datechooser.beans.DateChooserCombo dccFechaNac;
    private datechooser.beans.DateChooserCombo dccFechaRecepcion;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spnCaso;
    private javax.swing.JSpinner spnMontoInicial;
    private javax.swing.JSpinner spnSaldo;
    private javax.swing.JSpinner spnZona;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccionTrabajo;
    private javax.swing.JTextField txtDpi;
    private javax.swing.JTextField txtLugarTrabajo;
    private javax.swing.JTextField txtNacionalidad;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNoCuenta;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOtroNoCuenta;
    private javax.swing.JTextField txtProfesion;
    private javax.swing.JTextField txtTelefonoCasa;
    private javax.swing.JTextField txtTelefonoCelular;
    private javax.swing.JTextField txtTelefonoTrabajo;
    // End of variables declaration//GEN-END:variables

}
