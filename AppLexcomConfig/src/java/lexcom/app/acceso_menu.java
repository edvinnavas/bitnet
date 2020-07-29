package lexcom.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "acceso_menu")
@ViewScoped
public class acceso_menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usuario;
    private String ambiente;
    private List<rol_load> listucha;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario = session.getAttribute("id_usuario").toString();
            this.ambiente = session.getAttribute("ambiente").toString();

            listucha = new ArrayList<>();

            Servicio servicio = new Servicio();
            String cadenasql = "SELECT "
                    + "menu.menu, "
                    + "menu.nombre, "
                    + "case rol_menu.ver when 'SI' then 'true' else 'false' end "
                    + "from "
                    + "rol_menu "
                    + "LEFT OUTER JOIN usuario ON (rol_menu.rol = usuario.rol) "
                    + "LEFT OUTER JOIN menu ON (rol_menu.menu = menu.menu) "
                    + "where usuario.nombre='" + this.usuario + "'";
            java.util.List<lexcom.ws.StringArray> resultado = servicio.reporte(cadenasql, this.ambiente);

            Integer filas = resultado.size();
            Integer columnas = resultado.get(0).getItem().size();
            String[][] vector_result = new String[resultado.size()][columnas];
            for (Integer i = 0; i < resultado.size(); i++) {
                for (Integer j = 0; j < resultado.get(i).getItem().size(); j++) {
                    vector_result[i][j] = resultado.get(i).getItem().get(j);
                }
            }

            listucha = new ArrayList<>();
            for (Integer i = 1; i < filas; i++) {
                rol_load temp = new rol_load(
                        Integer.parseInt(vector_result[i][0]),
                        vector_result[i][1],
                        vector_result[i][2]);
                listucha.add(temp);
            }

            for (int i = 0; i < listucha.size(); i++) {
                switch (listucha.get(i).getMenu()) {
                    case "Inicio":
                        Inicio = listucha.get(i).getVer();
                        break;
                    case "Usuarios":
                        Usuarios = listucha.get(i).getVer();
                        break;
                    case "Permisos Usuarios":
                        Permisos_Usuarios = listucha.get(i).getVer();
                        break;
                    case "Rol":
                        Rol = listucha.get(i).getVer();
                        break;
                    case "Permisos Rol":
                        Permisos_Rol = listucha.get(i).getVer();
                        break;
                    case "Constantes":
                        Constantes = listucha.get(i).getVer();
                        break;
                    case "Corporaciones":
                        Corporaciones = listucha.get(i).getVer();
                        break;
                    case "Actores":
                        Actores = listucha.get(i).getVer();
                        break;
                    case "Deudores":
                        Deudores = listucha.get(i).getVer();
                        break;
                    case "Juzgados":
                        Juzgados = listucha.get(i).getVer();
                        break;
                    case "Garantías":
                        Garantias = listucha.get(i).getVer();
                        break;
                    case "Bancos":
                        Bancos = listucha.get(i).getVer();
                        break;
                    case "Estados judicial":
                        Estados_judicial = listucha.get(i).getVer();
                        break;
                    case "Status judicial":
                        Status_judicial = listucha.get(i).getVer();
                        break;
                    case "Estados extrajudicial":
                        Estados_extrajudicial = listucha.get(i).getVer();
                        break;
                    case "Status extrajudicial":
                        Status_extrajudicial = listucha.get(i).getVer();
                        break;
                    case "Tipos de Descuento":
                        Tipos_de_Descuento = listucha.get(i).getVer();
                        break;
                    case "Tipos de Aumento":
                        Tipos_de_Aumento = listucha.get(i).getVer();
                        break;
                    case "Frases Predeterminadas":
                        Frases_Predeterminadas = listucha.get(i).getVer();
                        break;
                    case "Tipos Código Resultado":
                        Tipos_Codigo_Resultado = listucha.get(i).getVer();
                        break;
                    case "Códigos de Resultado":
                        Codigos_de_Resultado = listucha.get(i).getVer();
                        break;
                    case "Estado Status Judicial":
                        Estado_Status_Judicial = listucha.get(i).getVer();
                        break;
                    case "Estado Status Extrajudicial":
                        Estado_Status_Extrajudicial = listucha.get(i).getVer();
                        break;
                    case "Estado Status Extrajudicial Bloqueo":
                        Estado_Status_Extrajudicial_Bloqueo = listucha.get(i).getVer();
                        break;
                    case "Tipo Resultado Resultado":
                        Tipo_Resultado_Resultado = listucha.get(i).getVer();
                        break;
                    case "Asignación Cartera":
                        Asignacion_Cartera = listucha.get(i).getVer();
                        break;
                    case "Expedientes Asignados":
                        Expedientes_Asignados = listucha.get(i).getVer();
                        break;
                    case "Expedientes":
                        Expedientes = listucha.get(i).getVer();
                        break;
                    case "Juicios":
                        Juicios = listucha.get(i).getVer();
                        break;
                    case "Archivo deudores carga":
                        Archivo_deudores_carga = listucha.get(i).getVer();
                        break;
                    case "Archivo deudores actualización":
                        Archivo_deudores_actualizacion = listucha.get(i).getVer();
                        break;
                    case "Archivo rotación de cartera automático":
                        Archivo_rotacion_de_cartera_automatico = listucha.get(i).getVer();
                        break;
                    case "Archivo rotación de cartera manual":
                        Archivo_rotacion_de_cartera_manual = listucha.get(i).getVer();
                        break;
                    case "Archivo carga de pagos masivos":
                        Archivo_carga_de_pagos_masivos = listucha.get(i).getVer();
                        break;
                    case "Archivo presentar demanda":
                        Archivo_presentar_demanda = listucha.get(i).getVer();
                        break;
                    case "Archivo caratula":
                        Archivo_caratula = listucha.get(i).getVer();
                        break;
                    case "Archivo medidas precautorias":
                        Archivo_medidas_precautorias = listucha.get(i).getVer();
                        break;
                    case "Archivo diligenciar medidas":
                        Archivo_diligenciar_medidas = listucha.get(i).getVer();
                        break;
                    case "Carga masiva deudores":
                        Carga_masiva_deudores = listucha.get(i).getVer();
                        break;
                    case "Carga actualización masiva deudores":
                        Carga_actualizacion_masiva_deudores = listucha.get(i).getVer();
                        break;
                    case "Carga rotación de cartera":
                        Carga_rotacion_de_cartera = listucha.get(i).getVer();
                        break;
                    case "Carga de pagos masivos":
                        Carga_de_pagos_masivos = listucha.get(i).getVer();
                        break;
                    case "Carga masiva presentar demanda":
                        Carga_masiva_presentar_demanda = listucha.get(i).getVer();
                        break;
                    case "Carga masiva caratula":
                        Carga_masiva_caratula = listucha.get(i).getVer();
                        break;
                    case "Carga masiva medidas precautorias":
                        Carga_masiva_medidas_precautorias = listucha.get(i).getVer();
                        break;
                    case "Carga masiva diligenciar medidas":
                        Carga_masiva_diligenciar_medidas = listucha.get(i).getVer();
                        break;
                    case "Pagos por gestor y garantía":
                        Pagos_por_gestor_y_garantia = listucha.get(i).getVer();
                        break;
                    case "Pagos por antigüedad y garantía":
                        Pagos_por_antigüedad_y_garantia = listucha.get(i).getVer();
                        break;
                    case "Asignación de cartera":
                        Asignacion_de_cartera = listucha.get(i).getVer();
                        break;
                    case "Reporte consejo":
                        Reporte_consejo = listucha.get(i).getVer();
                        break;
                    case "Reporte de procuradores":
                        Reporte_de_procuradores = listucha.get(i).getVer();
                        break;
                    case "Reporte de pagos":
                        Reporte_de_pagos = listucha.get(i).getVer();
                        break;
                    case "Reporte irrecuperabilidad":
                        Reporte_irrecuperabilidad = listucha.get(i).getVer();
                        break;
                    case "Notificados por actor y garantía":
                        Notificados_por_actor_y_garantia = listucha.get(i).getVer();
                        break;
                    case "Liquidación de gastos de demanda":
                        Liquidacion_de_gastos_de_demanda = listucha.get(i).getVer();
                        break;
                    case "Reporte deudores contactados":
                        Reporte_deudores_contactados = listucha.get(i).getVer();
                        break;
                    case "Reporte historial de gestiones":
                        Reporte_historial_de_gestiones = listucha.get(i).getVer();
                        break;
                    case "Reporte calificación casos":
                        Reporte_calificacion_casos = listucha.get(i).getVer();
                        break;
                    case "Reporte revisión convenios":
                        Reporte_revision_convenios = listucha.get(i).getVer();
                        break;
                    case "Reporte eventos":
                        Reporte_eventos = listucha.get(i).getVer();
                        break;
                    case "Cartera":
                        Cartera = listucha.get(i).getVer();
                        break;
                    case "Permisos Expediente":
                        Permisos_Expediente = listucha.get(i).getVer();
                        break;
                    case "Intencion Pago":
                        Intencion_Pago = listucha.get(i).getVer();
                        break;
                    case "Razon Deuda":
                        Razon_Deuda = listucha.get(i).getVer();
                        break;
                    case "Tipo Codigo Resultado Contacto":
                        Tipo_Codigo_Resultado_Contacto = listucha.get(i).getVer();
                        break;
                    case "Carga gestion cobros":
                        Carga_gestion_cobros = listucha.get(i).getVer();
                        break;
                    case "Antiguedades":
                        Antiguedades = listucha.get(i).getVer();
                        break;
                    case "Monitor":
                        Monitor = listucha.get(i).getVer();
                        break;
                    default:
                        System.out.println("Not in 10, 20 or 30 => " + listucha.get(i).getMenu());
                }
            }
        } catch (Exception ex) {

        }
    }

    public String Inicio;
    public String Usuarios;
    public String Permisos_Usuarios;
    public String Rol;
    public String Permisos_Rol;
    public String Constantes;
    public String Corporaciones;
    public String Actores;
    public String Deudores;
    public String Juzgados;
    public String Garantias;
    public String Bancos;
    public String Estados_judicial;
    public String Status_judicial;
    public String Estados_extrajudicial;
    public String Status_extrajudicial;
    public String Tipos_de_Descuento;
    public String Tipos_de_Aumento;
    public String Frases_Predeterminadas;
    public String Tipos_Codigo_Resultado;
    public String Codigos_de_Resultado;
    public String Estado_Status_Judicial;
    public String Estado_Status_Extrajudicial;
    public String Estado_Status_Extrajudicial_Bloqueo;
    public String Tipo_Resultado_Resultado;
    public String Asignacion_Cartera;
    public String Expedientes_Asignados;
    public String Expedientes;
    public String Juicios;
    public String Archivo_deudores_carga;
    public String Archivo_deudores_actualizacion;
    public String Archivo_rotacion_de_cartera_automatico;
    public String Archivo_rotacion_de_cartera_manual;
    public String Archivo_carga_de_pagos_masivos;
    public String Archivo_presentar_demanda;
    public String Archivo_caratula;
    public String Archivo_medidas_precautorias;
    public String Archivo_diligenciar_medidas;
    public String Carga_masiva_deudores;
    public String Carga_actualizacion_masiva_deudores;
    public String Carga_rotacion_de_cartera;
    public String Carga_de_pagos_masivos;
    public String Carga_masiva_presentar_demanda;
    public String Carga_masiva_caratula;
    public String Carga_masiva_medidas_precautorias;
    public String Carga_masiva_diligenciar_medidas;
    public String Pagos_por_gestor_y_garantia;
    public String Pagos_por_antigüedad_y_garantia;
    public String Asignacion_de_cartera;
    public String Reporte_consejo;
    public String Reporte_de_procuradores;
    public String Reporte_de_pagos;
    public String Reporte_irrecuperabilidad;
    public String Notificados_por_actor_y_garantia;
    public String Liquidacion_de_gastos_de_demanda;
    public String Reporte_deudores_contactados;
    public String Reporte_historial_de_gestiones;
    public String Reporte_calificacion_casos;
    public String Reporte_revision_convenios;
    public String Reporte_eventos;
    public String Cartera;
    public String Permisos_Expediente;
    public String Intencion_Pago;
    public String Razon_Deuda;
    public String Tipo_Codigo_Resultado_Contacto;
    public String Carga_gestion_cobros;
    public String Antiguedades;
    public String Monitor;

}
