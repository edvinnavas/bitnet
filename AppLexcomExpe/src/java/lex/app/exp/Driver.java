package lex.app.exp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String path;
    
    public Driver() {
        //this.path = "C:\\lex\\";
        //this.path = "C:\\Users\\Edvin\\Desktop\\";
        //this.path = "C:\\Users\\enavas\\Desktop\\";
        //this.path = "/home/lexcomadmin/ReportesLexcom/";
        //this.path = "/home/edvin/ReportesLexcom/";
        this.path = "/home/administrador/ReportesLexcom/";
    }
    
    public String getPath() {
        return path;
    }
    
    public List<SelectItem> lista_SelectItem(String cadenasql, String ambiente) {
        List<SelectItem> lista = new ArrayList<>();
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                lista.add(new SelectItem(vector_result[i][0], vector_result[i][1]));
            }
            lista.add(new SelectItem("TODOS", "TODOS"));
        } catch (Exception ex) {
            lista.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lista;
    }
    
    public List<String> convertir_array_to_list(String[] array) {
        List<String> lista = new ArrayList<>();
        
        try {
            for(Integer i = 0; i < array.length; i++) {
                lista.add(array[i]);
            }
        } catch (Exception ex) {
            lista.add("**ERROR**");
        }

        return lista;
    }
    
    public List<String> lista_StringItem(String cadenasql, String ambiente) {
        List<String> lista = new ArrayList<>();
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                lista.add(vector_result[i][0]);
            }
        } catch (Exception ex) {
            lista.add("**ERROR**");
        }

        return lista;
    }
    
    public List<SelectItem> lista_SelectItem_simple(String cadenasql, String ambiente) {
        List<SelectItem> lista = new ArrayList<>();
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                lista.add(new SelectItem(vector_result[i][0], vector_result[i][1]));
            }
        } catch (Exception ex) {
            lista.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lista;
    }
    
    public List<SelectItem> lista_paises() {
        List<SelectItem> lst_pais = new ArrayList<>();
        
        try {
            lst_pais.add(new SelectItem("Guatemala", "Guatemala"));
            lst_pais.add(new SelectItem("Afganistán", "Afganistán"));
            lst_pais.add(new SelectItem("Akrotiri", "Akrotiri"));
            lst_pais.add(new SelectItem("Albania", "Albania"));
            lst_pais.add(new SelectItem("Alemania", "Alemania"));
            lst_pais.add(new SelectItem("Andorra", "Andorra"));
            lst_pais.add(new SelectItem("Angola", "Angola"));
            lst_pais.add(new SelectItem("Anguila", "Anguila"));
            lst_pais.add(new SelectItem("Antártida", "Antártida"));
            lst_pais.add(new SelectItem("Antigua y Barbuda", "Antigua y Barbuda"));
            lst_pais.add(new SelectItem("Antillas Neerlandesas", "Antillas Neerlandesas"));
            lst_pais.add(new SelectItem("Arabia Saudí", "Arabia Saudí"));
            lst_pais.add(new SelectItem("Arctic Ocean", "Arctic Ocean"));
            lst_pais.add(new SelectItem("Argelia", "Argelia"));
            lst_pais.add(new SelectItem("Argentina", "Argentina"));
            lst_pais.add(new SelectItem("Armenia", "Armenia"));
            lst_pais.add(new SelectItem("Aruba", "Aruba"));
            lst_pais.add(new SelectItem("Ashmore and Cartier Islands", "Ashmore and Cartier Islands"));
            lst_pais.add(new SelectItem("Atlantic Ocean", "Atlantic Ocean"));
            lst_pais.add(new SelectItem("Australia", "Australia"));
            lst_pais.add(new SelectItem("Austria", "Austria"));
            lst_pais.add(new SelectItem("Azerbaiyán", "Azerbaiyán"));
            lst_pais.add(new SelectItem("Bahamas", "Bahamas"));
            lst_pais.add(new SelectItem("Bahráin", "Bahráin"));
            lst_pais.add(new SelectItem("Bangladesh", "Bangladesh"));
            lst_pais.add(new SelectItem("Barbados", "Barbados"));
            lst_pais.add(new SelectItem("Bélgica", "Bélgica"));
            lst_pais.add(new SelectItem("Belice", "Belice"));
            lst_pais.add(new SelectItem("Benín", "Benín"));
            lst_pais.add(new SelectItem("Bermudas", "Bermudas"));
            lst_pais.add(new SelectItem("Bielorrusia", "Bielorrusia"));
            lst_pais.add(new SelectItem("Birmania; Myanmar", "Birmania; Myanmar"));
            lst_pais.add(new SelectItem("Bolivia", "Bolivia"));
            lst_pais.add(new SelectItem("Bosnia y Hercegovina", "Bosnia y Hercegovina"));
            lst_pais.add(new SelectItem("Botsuana", "Botsuana"));
            lst_pais.add(new SelectItem("Brasil", "Brasil"));
            lst_pais.add(new SelectItem("Brunéi", "Brunéi"));
            lst_pais.add(new SelectItem("Bulgaria", "Bulgaria"));
            lst_pais.add(new SelectItem("Burkina Faso", "Burkina Faso"));
            lst_pais.add(new SelectItem("Burundi", "Burundi"));
            lst_pais.add(new SelectItem("Bután", "Bután"));
            lst_pais.add(new SelectItem("Cabo Verde", "Cabo Verde"));
            lst_pais.add(new SelectItem("Camboya", "Camboya"));
            lst_pais.add(new SelectItem("Camerún", "Camerún"));
            lst_pais.add(new SelectItem("Canadá", "Canadá"));
            lst_pais.add(new SelectItem("Chad", "Chad"));
            lst_pais.add(new SelectItem("Chile", "Chile"));
            lst_pais.add(new SelectItem("China", "China"));
            lst_pais.add(new SelectItem("Chipre", "Chipre"));
            lst_pais.add(new SelectItem("Clipperton Island", "Clipperton Island"));
            lst_pais.add(new SelectItem("Colombia", "Colombia"));
            lst_pais.add(new SelectItem("Comoras", "Comoras"));
            lst_pais.add(new SelectItem("Congo", "Congo"));
            lst_pais.add(new SelectItem("Coral Sea Islands", "Coral Sea Islands"));
            lst_pais.add(new SelectItem("Corea del Norte", "Corea del Norte"));
            lst_pais.add(new SelectItem("Corea del Sur", "Corea del Sur"));
            lst_pais.add(new SelectItem("Costa de Marfil", "Costa de Marfil"));
            lst_pais.add(new SelectItem("Costa Rica", "Costa Rica"));
            lst_pais.add(new SelectItem("Croacia", "Croacia"));
            lst_pais.add(new SelectItem("Cuba", "Cuba"));
            lst_pais.add(new SelectItem("Dhekelia", "Dhekelia"));
            lst_pais.add(new SelectItem("Dinamarca", "Dinamarca"));
            lst_pais.add(new SelectItem("Dominica", "Dominica"));
            lst_pais.add(new SelectItem("Ecuador", "Ecuador"));
            lst_pais.add(new SelectItem("Egipto", "Egipto"));
            lst_pais.add(new SelectItem("El Salvador", "El Salvador"));
            lst_pais.add(new SelectItem("El Vaticano", "El Vaticano"));
            lst_pais.add(new SelectItem("Emiratos Árabes Unidos", "Emiratos Árabes Unidos"));
            lst_pais.add(new SelectItem("Eritrea", "Eritrea"));
            lst_pais.add(new SelectItem("Eslovaquia", "Eslovaquia"));
            lst_pais.add(new SelectItem("Eslovenia", "Eslovenia"));
            lst_pais.add(new SelectItem("España", "España"));
            lst_pais.add(new SelectItem("Estados Unidos", "Estados Unidos"));
            lst_pais.add(new SelectItem("Estonia", "Estonia"));
            lst_pais.add(new SelectItem("Etiopía", "Etiopía"));
            lst_pais.add(new SelectItem("Filipinas", "Filipinas"));
            lst_pais.add(new SelectItem("Finlandia", "Finlandia"));
            lst_pais.add(new SelectItem("Fiyi", "Fiyi"));
            lst_pais.add(new SelectItem("Francia", "Francia"));
            lst_pais.add(new SelectItem("Gabón", "Gabón"));
            lst_pais.add(new SelectItem("Gambia", "Gambia"));
            lst_pais.add(new SelectItem("Gaza Strip", "Gaza Strip"));
            lst_pais.add(new SelectItem("Georgia", "Georgia"));
            lst_pais.add(new SelectItem("Ghana", "Ghana"));
            lst_pais.add(new SelectItem("Gibraltar", "Gibraltar"));
            lst_pais.add(new SelectItem("Granada", "Granada"));
            lst_pais.add(new SelectItem("Grecia", "Grecia"));
            lst_pais.add(new SelectItem("Groenlandia", "Groenlandia"));
            lst_pais.add(new SelectItem("Guam", "Guam"));
            lst_pais.add(new SelectItem("Guernsey", "Guernsey"));
            lst_pais.add(new SelectItem("Guinea", "Guinea"));
            lst_pais.add(new SelectItem("Guinea Ecuatorial", "Guinea Ecuatorial"));
            lst_pais.add(new SelectItem("Guinea-Bissau", "Guinea-Bissau"));
            lst_pais.add(new SelectItem("Guyana", "Guyana"));
            lst_pais.add(new SelectItem("Haití", "Haití"));
            lst_pais.add(new SelectItem("Honduras", "Honduras"));
            lst_pais.add(new SelectItem("Hong Kong", "Hong Kong"));
            lst_pais.add(new SelectItem("Hungría", "Hungría"));
            lst_pais.add(new SelectItem("India", "India"));
            lst_pais.add(new SelectItem("Indian Ocean", "Indian Ocean"));
            lst_pais.add(new SelectItem("Indonesia", "Indonesia"));
            lst_pais.add(new SelectItem("Irán", "Irán"));
            lst_pais.add(new SelectItem("Iraq", "Iraq"));
            lst_pais.add(new SelectItem("Irlanda", "Irlanda"));
            lst_pais.add(new SelectItem("Isla Bouvet", "Isla Bouvet"));
            lst_pais.add(new SelectItem("Isla Christmas", "Isla Christmas"));
            lst_pais.add(new SelectItem("Isla Norfolk", "Isla Norfolk"));
            lst_pais.add(new SelectItem("Islandia", "Islandia"));
            lst_pais.add(new SelectItem("Islas Caimán", "Islas Caimán"));
            lst_pais.add(new SelectItem("Islas Cocos", "Islas Cocos"));
            lst_pais.add(new SelectItem("Islas Cook", "Islas Cook"));
            lst_pais.add(new SelectItem("Islas Feroe", "Islas Feroe"));
            lst_pais.add(new SelectItem("Islas Georgia del Sur y Sandwich del Sur", "Islas Georgia del Sur y Sandwich del Sur"));
            lst_pais.add(new SelectItem("Islas Heard y McDonald", "Islas Heard y McDonald"));
            lst_pais.add(new SelectItem("Islas Malvinas", "Islas Malvinas"));
            lst_pais.add(new SelectItem("Islas Marianas del Norte", "Islas Marianas del Norte"));
            lst_pais.add(new SelectItem("Islas Marshall", "Islas Marshall"));
            lst_pais.add(new SelectItem("Islas Pitcairn", "Islas Pitcairn"));
            lst_pais.add(new SelectItem("Islas Salomón", "Islas Salomón"));
            lst_pais.add(new SelectItem("Islas Turcas y Caicos", "Islas Turcas y Caicos"));
            lst_pais.add(new SelectItem("Islas Vírgenes Americanas", "Islas Vírgenes Americanas"));
            lst_pais.add(new SelectItem("Islas Vírgenes Británicas", "Islas Vírgenes Británicas"));
            lst_pais.add(new SelectItem("Israel", "Israel"));
            lst_pais.add(new SelectItem("Italia", "Italia"));
            lst_pais.add(new SelectItem("Jamaica", "Jamaica"));
            lst_pais.add(new SelectItem("Jan Mayen", "Jan Mayen"));
            lst_pais.add(new SelectItem("Japón", "Japón"));
            lst_pais.add(new SelectItem("Jersey", "Jersey"));
            lst_pais.add(new SelectItem("Jordania", "Jordania"));
            lst_pais.add(new SelectItem("Kazajistán", "Kazajistán"));
            lst_pais.add(new SelectItem("Kenia", "Kenia"));
            lst_pais.add(new SelectItem("Kirguizistán", "Kirguizistán"));
            lst_pais.add(new SelectItem("Kiribati", "Kiribati"));
            lst_pais.add(new SelectItem("Kuwait", "Kuwait"));
            lst_pais.add(new SelectItem("Laos", "Laos"));
            lst_pais.add(new SelectItem("Lesoto", "Lesoto"));
            lst_pais.add(new SelectItem("Letonia", "Letonia"));
            lst_pais.add(new SelectItem("Líbano", "Líbano"));
            lst_pais.add(new SelectItem("Liberia", "Liberia"));
            lst_pais.add(new SelectItem("Libia", "Libia"));
            lst_pais.add(new SelectItem("Liechtenstein", "Liechtenstein"));
            lst_pais.add(new SelectItem("Lituania", "Lituania"));
            lst_pais.add(new SelectItem("Luxemburgo", "Luxemburgo"));
            lst_pais.add(new SelectItem("Macao", "Macao"));
            lst_pais.add(new SelectItem("Macedonia", "Macedonia"));
            lst_pais.add(new SelectItem("Madagascar", "Madagascar"));
            lst_pais.add(new SelectItem("Malasia", "Malasia"));
            lst_pais.add(new SelectItem("Malaui", "Malaui"));
            lst_pais.add(new SelectItem("Maldivas", "Maldivas"));
            lst_pais.add(new SelectItem("Malí", "Malí"));
            lst_pais.add(new SelectItem("Malta", "Malta"));
            lst_pais.add(new SelectItem("Man, Isle of", "Man, Isle of"));
            lst_pais.add(new SelectItem("Marruecos", "Marruecos"));
            lst_pais.add(new SelectItem("Mauricio", "Mauricio"));
            lst_pais.add(new SelectItem("Mauritania", "Mauritania"));
            lst_pais.add(new SelectItem("Mayotte", "Mayotte"));
            lst_pais.add(new SelectItem("México", "México"));
            lst_pais.add(new SelectItem("Micronesia", "Micronesia"));
            lst_pais.add(new SelectItem("Moldavia", "Moldavia"));
            lst_pais.add(new SelectItem("Mónaco", "Mónaco"));
            lst_pais.add(new SelectItem("Mongolia", "Mongolia"));
            lst_pais.add(new SelectItem("Montenegro", "Montenegro"));
            lst_pais.add(new SelectItem("Montserrat", "Montserrat"));
            lst_pais.add(new SelectItem("Mozambique", "Mozambique"));
            lst_pais.add(new SelectItem("Mundo", "Mundo"));
            lst_pais.add(new SelectItem("Namibia", "Namibia"));
            lst_pais.add(new SelectItem("Nauru", "Nauru"));
            lst_pais.add(new SelectItem("Navassa Island", "Navassa Island"));
            lst_pais.add(new SelectItem("Nepal", "Nepal"));
            lst_pais.add(new SelectItem("Nicaragua", "Nicaragua"));
            lst_pais.add(new SelectItem("Níger", "Níger"));
            lst_pais.add(new SelectItem("Nigeria", "Nigeria"));
            lst_pais.add(new SelectItem("Niue", "Niue"));
            lst_pais.add(new SelectItem("Noruega", "Noruega"));
            lst_pais.add(new SelectItem("Nueva Caledonia", "Nueva Caledonia"));
            lst_pais.add(new SelectItem("Nueva Zelanda", "Nueva Zelanda"));
            lst_pais.add(new SelectItem("Omán", "Omán"));
            lst_pais.add(new SelectItem("Pacific Ocean", "Pacific Ocean"));
            lst_pais.add(new SelectItem("Países Bajos", "Países Bajos"));
            lst_pais.add(new SelectItem("Pakistán", "Pakistán"));
            lst_pais.add(new SelectItem("Palaos", "Palaos"));
            lst_pais.add(new SelectItem("Panamá", "Panamá"));
            lst_pais.add(new SelectItem("Papúa-Nueva Guinea", "Papúa-Nueva Guinea"));
            lst_pais.add(new SelectItem("Paracel Islands", "Paracel Islands"));
            lst_pais.add(new SelectItem("Paraguay", "Paraguay"));
            lst_pais.add(new SelectItem("Perú", "Perú"));
            lst_pais.add(new SelectItem("Polinesia Francesa", "Polinesia Francesa"));
            lst_pais.add(new SelectItem("Polonia", "Polonia"));
            lst_pais.add(new SelectItem("Portugal", "Portugal"));
            lst_pais.add(new SelectItem("Puerto Rico", "Puerto Rico"));
            lst_pais.add(new SelectItem("Qatar", "Qatar"));
            lst_pais.add(new SelectItem("Reino Unido", "Reino Unido"));
            lst_pais.add(new SelectItem("República Centroafricana", "República Centroafricana"));
            lst_pais.add(new SelectItem("República Checa", "República Checa"));
            lst_pais.add(new SelectItem("República Democrática del Congo", "República Democrática del Congo"));
            lst_pais.add(new SelectItem("República Dominicana", "República Dominicana"));
            lst_pais.add(new SelectItem("Ruanda", "Ruanda"));
            lst_pais.add(new SelectItem("Rumania", "Rumania"));
            lst_pais.add(new SelectItem("Rusia", "Rusia"));
            lst_pais.add(new SelectItem("Sáhara Occidental", "Sáhara Occidental"));
            lst_pais.add(new SelectItem("Samoa", "Samoa"));
            lst_pais.add(new SelectItem("Samoa Americana", "Samoa Americana"));
            lst_pais.add(new SelectItem("San Cristóbal y Nieves", "San Cristóbal y Nieves"));
            lst_pais.add(new SelectItem("San Marino", "San Marino"));
            lst_pais.add(new SelectItem("San Pedro y Miquelón", "San Pedro y Miquelón"));
            lst_pais.add(new SelectItem("San Vicente y las Granadinas", "San Vicente y las Granadinas"));
            lst_pais.add(new SelectItem("Santa Helena", "Santa Helena"));
            lst_pais.add(new SelectItem("Santa Lucía", "Santa Lucía"));
            lst_pais.add(new SelectItem("Santo Tomé y Príncipe", "Santo Tomé y Príncipe"));
            lst_pais.add(new SelectItem("Senegal", "Senegal"));
            lst_pais.add(new SelectItem("Serbia", "Serbia"));
            lst_pais.add(new SelectItem("Seychelles", "Seychelles"));
            lst_pais.add(new SelectItem("Sierra Leona", "Sierra Leona"));
            lst_pais.add(new SelectItem("Singapur", "Singapur"));
            lst_pais.add(new SelectItem("Siria", "Siria"));
            lst_pais.add(new SelectItem("Somalia", "Somalia"));
            lst_pais.add(new SelectItem("Southern Ocean", "Southern Ocean"));
            lst_pais.add(new SelectItem("Spratly Islands", "Spratly Islands"));
            lst_pais.add(new SelectItem("Sri Lanka", "Sri Lanka"));
            lst_pais.add(new SelectItem("Suazilandia", "Suazilandia"));
            lst_pais.add(new SelectItem("Sudáfrica", "Sudáfrica"));
            lst_pais.add(new SelectItem("Sudán", "Sudán"));
            lst_pais.add(new SelectItem("Suecia", "Suecia"));
            lst_pais.add(new SelectItem("Suiza", "Suiza"));
            lst_pais.add(new SelectItem("Surinam", "Surinam"));
            lst_pais.add(new SelectItem("Svalbard y Jan Mayen", "Svalbard y Jan Mayen"));
            lst_pais.add(new SelectItem("Tailandia", "Tailandia"));
            lst_pais.add(new SelectItem("Taiwán", "Taiwán"));
            lst_pais.add(new SelectItem("Tanzania", "Tanzania"));
            lst_pais.add(new SelectItem("Tayikistán", "Tayikistán"));
            lst_pais.add(new SelectItem("Territorio Británico del Océano Indico", "Territorio Británico del Océano Indico"));
            lst_pais.add(new SelectItem("Territorios Australes Franceses", "Territorios Australes Franceses"));
            lst_pais.add(new SelectItem("Timor Oriental", "Timor Oriental"));
            lst_pais.add(new SelectItem("Togo", "Togo"));
            lst_pais.add(new SelectItem("Tokelau", "Tokelau"));
            lst_pais.add(new SelectItem("Tonga", "Tonga"));
            lst_pais.add(new SelectItem("Trinidad y Tobago", "Trinidad y Tobago"));
            lst_pais.add(new SelectItem("Túnez", "Túnez"));
            lst_pais.add(new SelectItem("Turkmenistán", "Turkmenistán"));
            lst_pais.add(new SelectItem("Turquía", "Turquía"));
            lst_pais.add(new SelectItem("Tuvalu", "Tuvalu"));
            lst_pais.add(new SelectItem("Ucrania", "Ucrania"));
            lst_pais.add(new SelectItem("Uganda", "Uganda"));
            lst_pais.add(new SelectItem("Unión Europea", "Unión Europea"));
            lst_pais.add(new SelectItem("Uruguay", "Uruguay"));
            lst_pais.add(new SelectItem("Uzbekistán", "Uzbekistán"));
            lst_pais.add(new SelectItem("Vanuatu", "Vanuatu"));
            lst_pais.add(new SelectItem("Venezuela", "Venezuela"));
            lst_pais.add(new SelectItem("Vietnam", "Vietnam"));
            lst_pais.add(new SelectItem("Wake Island", "Wake Island"));
            lst_pais.add(new SelectItem("Wallis y Futuna", "Wallis y Futuna"));
            lst_pais.add(new SelectItem("West Bank", "West Bank"));
            lst_pais.add(new SelectItem("Yemen", "Yemen"));
            lst_pais.add(new SelectItem("Yibuti", "Yibuti"));
            lst_pais.add(new SelectItem("Zambia", "Zambia"));
            lst_pais.add(new SelectItem("Zimbabue", "Zimbabue"));
        } catch (Exception ex) {
            lst_pais.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_pais;
    }
    
    public List<SelectItem> lista_departamentos() {
        List<SelectItem> lst_departamento = new ArrayList<>();
        
        try {
            lst_departamento.add(new SelectItem("Guatemala", "Guatemala"));
            lst_departamento.add(new SelectItem("Alta Verapaz", "Alta Verapaz"));
            lst_departamento.add(new SelectItem("Baja Verapaz", "Baja Verapaz"));
            lst_departamento.add(new SelectItem("Chimaltenango", "Chimaltenango"));
            lst_departamento.add(new SelectItem("Chiquimula", "Chiquimula"));
            lst_departamento.add(new SelectItem("Petén", "Petén"));
            lst_departamento.add(new SelectItem("El Progreso", "El Progreso"));
            lst_departamento.add(new SelectItem("Quiché", "Quiché"));
            lst_departamento.add(new SelectItem("Escuintla", "Escuintla"));
            lst_departamento.add(new SelectItem("Huehuetenango", "Huehuetenango"));
            lst_departamento.add(new SelectItem("Izabal", "Izabal"));
            lst_departamento.add(new SelectItem("Jalapa", "Jalapa"));
            lst_departamento.add(new SelectItem("Jutiapa", "Jutiapa"));
            lst_departamento.add(new SelectItem("Quetzaltenango", "Quetzaltenango"));
            lst_departamento.add(new SelectItem("Retalhuleu", "Retalhuleu"));
            lst_departamento.add(new SelectItem("Sacatepéquez", "Sacatepéquez"));
            lst_departamento.add(new SelectItem("San Marcos", "San Marcos"));
            lst_departamento.add(new SelectItem("Santa Rosa", "Santa Rosa"));
            lst_departamento.add(new SelectItem("Sololá", "Sololá"));
            lst_departamento.add(new SelectItem("Suchitepéquez", "Suchitepéquez"));
            lst_departamento.add(new SelectItem("Totonicapán", "Totonicapán"));
            lst_departamento.add(new SelectItem("Zacapa", "Zacapa"));
            lst_departamento.add(new SelectItem("Otro", "Otro"));
        } catch (Exception ex) {
            lst_departamento.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_departamento;
    }
    
    public List<SelectItem> lista_cosechas() {
        List<SelectItem> lst_cosecha = new ArrayList<>();
        
        try {
            lst_cosecha.add(new SelectItem("0 antes 31 dic 2008", "0 antes 31 dic 2008"));
            lst_cosecha.add(new SelectItem("2009 1er t", "2009 1er t"));
            lst_cosecha.add(new SelectItem("2009 2do t", "2009 2do t"));
            lst_cosecha.add(new SelectItem("2009 3er t", "2009 3er t"));
            lst_cosecha.add(new SelectItem("2009 4to t", "2009 4to t"));
            lst_cosecha.add(new SelectItem("2010 1er t", "2010 1er t"));
            lst_cosecha.add(new SelectItem("2010 2do t", "2010 2do t"));
            lst_cosecha.add(new SelectItem("2010 3er t", "2010 3er t"));
            lst_cosecha.add(new SelectItem("2010 4to t", "2010 4to t"));
            lst_cosecha.add(new SelectItem("2011 1er t", "2011 1er t"));
            lst_cosecha.add(new SelectItem("2011 2do t", "2011 2do t"));
            lst_cosecha.add(new SelectItem("2011 3er t", "2011 3er t"));
            lst_cosecha.add(new SelectItem("2011 4to t", "2011 4to t"));
            lst_cosecha.add(new SelectItem("2012 1er t", "2012 1er t"));
            lst_cosecha.add(new SelectItem("2012 2do t", "2012 2do t"));
            lst_cosecha.add(new SelectItem("2012 3er t", "2012 3er t"));
            lst_cosecha.add(new SelectItem("2012 4to t", "2012 4to t"));
            lst_cosecha.add(new SelectItem("2013 1er t", "2013 1er t"));
            lst_cosecha.add(new SelectItem("2013 2do t", "2013 2do t"));
            lst_cosecha.add(new SelectItem("2013 3er t", "2013 3er t"));
            lst_cosecha.add(new SelectItem("2013 4to t", "2013 4to t"));
            lst_cosecha.add(new SelectItem("2014 1er t", "2014 1er t"));
            lst_cosecha.add(new SelectItem("2014 2do t", "2014 2do t"));
            lst_cosecha.add(new SelectItem("2014 3er t", "2014 3er t"));
            lst_cosecha.add(new SelectItem("2014 4to t", "2014 4to t"));
            lst_cosecha.add(new SelectItem("2015 1er t", "2015 1er t"));
            lst_cosecha.add(new SelectItem("2015 2do t", "2015 2do t"));
            lst_cosecha.add(new SelectItem("2015 3er t", "2015 3er t"));
            lst_cosecha.add(new SelectItem("2015 4to t", "2015 4to t"));
            lst_cosecha.add(new SelectItem("2016 1er t", "2016 1er t"));
            lst_cosecha.add(new SelectItem("2016 2do t", "2016 2do t"));
            lst_cosecha.add(new SelectItem("2016 3er t", "2016 3er t"));
            lst_cosecha.add(new SelectItem("2016 4to t", "2016 4to t"));
            lst_cosecha.add(new SelectItem("2017 1er t", "2017 1er t"));
            lst_cosecha.add(new SelectItem("2017 2do t", "2017 2do t"));
            lst_cosecha.add(new SelectItem("2017 3er t", "2017 3er t"));
            lst_cosecha.add(new SelectItem("2017 4to t", "2017 4to t"));
            lst_cosecha.add(new SelectItem("2018 1er t", "2018 1er t"));
            lst_cosecha.add(new SelectItem("2018 2do t", "2018 2do t"));
            lst_cosecha.add(new SelectItem("2018 3er t", "2018 3er t"));
            lst_cosecha.add(new SelectItem("2018 4to t", "2018 4to t"));
            lst_cosecha.add(new SelectItem("2019 1er t", "2019 1er t"));
            lst_cosecha.add(new SelectItem("2019 2do t", "2019 2do t"));
            lst_cosecha.add(new SelectItem("2019 3er t", "2019 3er t"));
            lst_cosecha.add(new SelectItem("2019 4to t", "2019 4to t"));
        } catch (Exception ex) {
            lst_cosecha.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_cosecha;
    }
    
    public List<SelectItem> lista_sexo() {
        List<SelectItem> lst_sexo = new ArrayList<>();
        
        try {
            lst_sexo.add(new SelectItem("MASCULINO", "MASCULINO"));
            lst_sexo.add(new SelectItem("FEMENINO", "FEMENINO"));
            lst_sexo.add(new SelectItem("-", "-"));
        } catch (Exception ex) {
            lst_sexo.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_sexo;
    }
    
    public List<SelectItem> lista_estado_civil() {
        List<SelectItem> lst_estado_civil = new ArrayList<>();
        
        try {
            lst_estado_civil.add(new SelectItem("SOLTERO", "SOLTERO"));
            lst_estado_civil.add(new SelectItem("CASADO", "CASADO"));
            lst_estado_civil.add(new SelectItem("-", "-"));
        } catch (Exception ex) {
            lst_estado_civil.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_estado_civil;
    }
    
    public List<SelectItem> lista_moneda() {
        List<SelectItem> lst_moneda = new ArrayList<>();
        
        try {
            lst_moneda.add(new SelectItem("QUETZAL", "QUETZAL"));
            lst_moneda.add(new SelectItem("DOLLAR", "DOLLAR"));
        } catch (Exception ex) {
            lst_moneda.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_moneda;
    }
    
    public List<SelectItem> lista_cargado() {
        List<SelectItem> lst_cargado = new ArrayList<>();
        
        try {
            lst_cargado.add(new SelectItem("CARGADO", "CARGADO"));
            lst_cargado.add(new SelectItem("DESCARGADO", "DESCARGADO"));
        } catch (Exception ex) {
            lst_cargado.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_cargado;
    }
    
    public List<SelectItem> lista_anticipo() {
        List<SelectItem> lst_anticipo = new ArrayList<>();
        
        try {
            lst_anticipo.add(new SelectItem("NO", "NO"));
            lst_anticipo.add(new SelectItem("SI", "SI"));
            lst_anticipo.add(new SelectItem("COBRAR", "COBRAR"));
            lst_anticipo.add(new SelectItem("REPRE", "REPRE"));
        } catch (Exception ex) {
            lst_anticipo.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_anticipo;
    }
    
    public List<SelectItem> lista_antiguedad() {
        List<SelectItem> lst_antiguedad = new ArrayList<>();
        
        try {
            lst_antiguedad.add(new SelectItem("ORO", "ORO"));
            lst_antiguedad.add(new SelectItem("PLATA", "PLATA"));
            lst_antiguedad.add(new SelectItem("BRONCE", "BRONCE"));
        } catch (Exception ex) {
            lst_antiguedad.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_antiguedad;
    }
    
    public List<SelectItem> lista_notificado() {
        List<SelectItem> lst_notificado = new ArrayList<>();
        
        try {
            lst_notificado.add(new SelectItem("SI", "SI"));
            lst_notificado.add(new SelectItem("NO", "NO"));
            lst_notificado.add(new SelectItem("D INCORRECTA", "D INCORRECTA"));
            lst_notificado.add(new SelectItem("NADIE ATIENDE", "NADIE ATIENDE"));
            lst_notificado.add(new SelectItem("NO VIVE LUGAR", "NO VIVE LUGAR"));
            lst_notificado.add(new SelectItem("IMPOSIBLE", "IMPOSIBLE"));
            lst_notificado.add(new SelectItem("FUERA PAIS", "FUERA PAIS"));
            lst_notificado.add(new SelectItem("FALLECIDO", "FALLECIDO"));
        } catch (Exception ex) {
            lst_notificado.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_notificado;
    }
        
    public List<SelectItem> lista_estado_convenio_detalle() {
        List<SelectItem> lista_estado_convenio_detalle = new ArrayList<>();
        
        try {
            lista_estado_convenio_detalle.add(new SelectItem("PENDIENTE", "PENDIENTE"));
            lista_estado_convenio_detalle.add(new SelectItem("CUMPLIDO", "CUMPLIDO"));
            lista_estado_convenio_detalle.add(new SelectItem("INCUMPLIDO", "INCUMPLIDO"));
        } catch (Exception ex) {
            lista_estado_convenio_detalle.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lista_estado_convenio_detalle;
    }
    
    public List<SelectItem> lista_estado_promesa() {
        List<SelectItem> lst_estado_promesa = new ArrayList<>();
        
        try {
            lst_estado_promesa.add(new SelectItem("NO LEIDO", "NO LEIDO"));
            lst_estado_promesa.add(new SelectItem("LEIDO", "LEIDO"));
        } catch (Exception ex) {
            lst_estado_promesa.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_estado_promesa;
    }
    
    public List<SelectItem> lista_medida_arraigo() {
        List<SelectItem> lst_medida_arraigo = new ArrayList<>();
        
        try {
            lst_medida_arraigo.add(new SelectItem("PEDIDO", "PEDIDO"));
            lst_medida_arraigo.add(new SelectItem("NO PEDIDO", "NO PEDIDO"));
            lst_medida_arraigo.add(new SelectItem("DECRETADO", "DECRETADO"));
            lst_medida_arraigo.add(new SelectItem("NO DECRETADO", "NO DECRETADO"));
            lst_medida_arraigo.add(new SelectItem("DILIGENCIADO", "DILIGENCIADO"));
        } catch (Exception ex) {
            lst_medida_arraigo.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_medida_arraigo;
    }
    
    public List<SelectItem> lista_medida_banco() {
        List<SelectItem> lst_medida_banco = new ArrayList<>();
        
        try {
            lst_medida_banco.add(new SelectItem("PEDIDO", "PEDIDO"));
            lst_medida_banco.add(new SelectItem("NO PEDIDO", "NO PEDIDO"));
            lst_medida_banco.add(new SelectItem("DECRETADO", "DECRETADO"));
            lst_medida_banco.add(new SelectItem("NO DECRETADO", "NO DECRETADO"));
            lst_medida_banco.add(new SelectItem("DILIGENCIADO", "DILIGENCIADO"));
        } catch (Exception ex) {
            lst_medida_banco.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_medida_banco;
    }
    
    public List<SelectItem> lista_medida_finca() {
        List<SelectItem> lst_medida_finca = new ArrayList<>();
        
        try {
            lst_medida_finca.add(new SelectItem("PENDIENTE", "PENDIENTE"));
            lst_medida_finca.add(new SelectItem("EN TRAMITE", "EN TRAMITE"));
            lst_medida_finca.add(new SelectItem("EN REGISTRO", "EN REGISTRO"));
            lst_medida_finca.add(new SelectItem("PEDIDO", "PEDIDO"));
            lst_medida_finca.add(new SelectItem("NO PEDIDO", "NO PEDIDO"));
            lst_medida_finca.add(new SelectItem("DECRETADO", "DECRETADO"));
            lst_medida_finca.add(new SelectItem("NO DECRETADO", "NO DECRETADO"));
            lst_medida_finca.add(new SelectItem("ANOTADA", "ANOTADA"));
        } catch (Exception ex) {
            lst_medida_finca.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_medida_finca;
    }
    
    public List<SelectItem> lista_medida_salario() {
        List<SelectItem> lst_medida_salario = new ArrayList<>();
        
        try {
            lst_medida_salario.add(new SelectItem("PEDIDO", "PEDIDO"));
            lst_medida_salario.add(new SelectItem("NO PEDIDO", "NO PEDIDO"));
            lst_medida_salario.add(new SelectItem("DECRETADO", "DECRETADO"));
            lst_medida_salario.add(new SelectItem("NO DECRETADO", "NO DECRETADO"));
            lst_medida_salario.add(new SelectItem("DILIGENCIADO", "DILIGENCIADO"));
            lst_medida_salario.add(new SelectItem("NO LABORA", "NO LABORA"));
        } catch (Exception ex) {
            lst_medida_salario.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_medida_salario;
    }
    
    public List<SelectItem> lista_medida_vehiculo() {
        List<SelectItem> lst_medida_vehiculo = new ArrayList<>();
        
        try {
            lst_medida_vehiculo.add(new SelectItem("PEDIDO", "PEDIDO"));
            lst_medida_vehiculo.add(new SelectItem("NO PEDIDO", "NO PEDIDO"));
            lst_medida_vehiculo.add(new SelectItem("DECRETADO", "DECRETADO"));
            lst_medida_vehiculo.add(new SelectItem("NO DECRETADO", "NO DECRETADO"));
            lst_medida_vehiculo.add(new SelectItem("DILIGENCIADO", "DILIGENCIADO"));
        } catch (Exception ex) {
            lst_medida_vehiculo.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_medida_vehiculo;
    }
    
    public List<SelectItem> lista_medida_otro() {
        List<SelectItem> lst_medida_otro = new ArrayList<>();
        
        try {
            lst_medida_otro.add(new SelectItem("PEDIDO", "PEDIDO"));
            lst_medida_otro.add(new SelectItem("NO PEDIDO", "NO PEDIDO"));
            lst_medida_otro.add(new SelectItem("DECRETADO", "DECRETADO"));
            lst_medida_otro.add(new SelectItem("NO DECRETADO", "NO DECRETADO"));
            lst_medida_otro.add(new SelectItem("DILIGENCIADO", "DILIGENCIADO"));
        } catch (Exception ex) {
            lst_medida_otro.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_medida_otro;
    }
    
    public List<SelectItem> lista_periodo_rotacion() {
        List<SelectItem> lst_periodo_rotacion = new ArrayList<>();
        
        try {
            lst_periodo_rotacion.add(new SelectItem(30, "30"));
            lst_periodo_rotacion.add(new SelectItem(45, "45"));
            lst_periodo_rotacion.add(new SelectItem(60, "60"));
        } catch (Exception ex) {
            lst_periodo_rotacion.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_periodo_rotacion;
    }
    
    public List<SelectItem> lista_tipo_convenio() {
        List<SelectItem> lst_tipo_convenio = new ArrayList<>();
        
        try {
            lst_tipo_convenio.add(new SelectItem("NORMAL", "NORMAL"));
            lst_tipo_convenio.add(new SelectItem("CANCELACION TOTAL", "CANCELACION TOTAL"));
            lst_tipo_convenio.add(new SelectItem("TEMPORAL", "TEMPORAL"));
            lst_tipo_convenio.add(new SelectItem("TRANSACCION", "TRANSACCION"));
            lst_tipo_convenio.add(new SelectItem("PAGOS SIN CONVENIO", "PAGOS SIN CONVENIO"));
        } catch (Exception ex) {
            lst_tipo_convenio.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_tipo_convenio;
    }
    
    public List<SelectItem> lista_estado_convenio() {
        List<SelectItem> lst_estado_convenio = new ArrayList<>();
        
        try {
            lst_estado_convenio.add(new SelectItem("NEGOCIACION", "NEGOCIACION"));
            lst_estado_convenio.add(new SelectItem("ACTIVO", "ACTIVO"));
            lst_estado_convenio.add(new SelectItem("ANULADO", "ANULADO"));
            lst_estado_convenio.add(new SelectItem("TERMINADO", "TERMINADO"));
        } catch (Exception ex) {
            lst_estado_convenio.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_estado_convenio;
    }
    
    public List<SelectItem> lista_frecuencia_convenio() {
        List<SelectItem> lst_frecuencia_convenio = new ArrayList<>();
        
        try {
            lst_frecuencia_convenio.add(new SelectItem("MENSUAL", "MENSUAL"));
            lst_frecuencia_convenio.add(new SelectItem("QUINCENAL", "QUINCENAL"));
            lst_frecuencia_convenio.add(new SelectItem("SEMANAL", "SEMANAL"));
            lst_frecuencia_convenio.add(new SelectItem("DIARIO", "DIARIO"));
        } catch (Exception ex) {
            lst_frecuencia_convenio.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_frecuencia_convenio;
    }
    
    public List<SelectItem> lista_ambientes() {
        List<SelectItem> lst_ambientes = new ArrayList<>();
        
        try {
            lst_ambientes.add(new SelectItem("LEXCOMJNDI", "Producción"));
            lst_ambientes.add(new SelectItem("LexcomJndiTEST", "Pruebas"));
        } catch (Exception ex) {
            lst_ambientes.add(new SelectItem("**ERROR**", "**ERROR**"));
        }

        return lst_ambientes;
    }
    
    public Integer getInt(String cadenasql, String ambiente) {
        Integer numero = 0;
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                numero = Integer.parseInt(vector_result[i][0]);
            }
        } catch (Exception ex) {
            numero = 0;
        }

        return numero;
    }
    
    public Double getDouble(String cadenasql, String ambiente) {
        Double numero = 0.00;
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                numero = Double.parseDouble(vector_result[i][0]);
            }
        } catch (Exception ex) {
            numero = 0.00;
        }

        return numero;
    }
    
    public Date getDate(String cadenasql, String ambiente) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = new Date();
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            
            for(Integer i = 1; i < filas; i++) {
                fecha = formatter.parse(vector_result[i][0]);
            }
        } catch (Exception ex) {
            fecha = new Date();
        }

        return fecha;
    }
    
    public String getString(String cadenasql, String ambiente) {
        String resultado_string = "";
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                resultado_string = vector_result[i][0];
            }
        } catch (Exception ex) {
            resultado_string = "Error";
        }

        return resultado_string;
    }
    
    public String ValidarEstadoStatus(String cadenasql, String ambiente) {
        String resultado_string = "INCORRECTO";
        
        try {
            Servicio servicio = new Servicio();
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            for(Integer i = 1; i < filas; i++) {
                resultado_string = "CORRECTO";
            }
        } catch (Exception ex) {
            resultado_string = "INCORRECTO";
        }

        return resultado_string;
    }
    
    public String dar_path_digitalizados(Integer constante, String ambiente) {
        String resultado = "";
        
        try {
            resultado = this.getString("select c.valor from constantes c where c.constantes=" + constante, ambiente);
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    public String dar_path_actor(Integer deudor, String ambiente) {
        String resultado = "";
        
        try {
            resultado = this.getString("select a.digitalizados from actor a left join deudor d on (a.actor=d.actor) where d.deudor=" + deudor, ambiente);
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
    public String dar_caso_deudor(Integer deudor, String ambiente) {
        String resultado = "";
        
        try {
            resultado = this.getString("select d.caso from deudor d where d.deudor=" + deudor, ambiente);
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return resultado;
    }
    
}
