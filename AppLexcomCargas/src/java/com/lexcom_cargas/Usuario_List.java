package com.lexcom_cargas;

import java.io.Serializable;

public class Usuario_List implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer usuario;
    private String nombre_completo;
    private String nombre;
    private String contrasena;
    private String estado;
    private String gestor;
    private String procurador;
    private String asistente;
    private String digitador;
    private String investigador;
    private String tipo_usuario;
    private Integer reinicio;
    private Integer rol;
    

    public Usuario_List(Integer usuario, String nombre_completo, String nombre, String contrasena, String estado, String gestor, String procurador, String asistente, String digitador, String investigador, String tipo_usuario,Integer reinicio,Integer rol) {
        this.usuario = usuario;
        this.nombre_completo = nombre_completo;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.estado = estado;
        this.gestor = gestor;
        this.procurador = procurador;
        this.asistente = asistente;
        this.digitador = digitador;
        this.investigador = investigador;
        this.tipo_usuario = tipo_usuario;
        this.reinicio= reinicio;
        this.rol=rol;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getProcurador() {
        return procurador;
    }

    public void setProcurador(String procurador) {
        this.procurador = procurador;
    }

    public String getAsistente() {
        return asistente;
    }

    public void setAsistente(String asistente) {
        this.asistente = asistente;
    }

    public String getDigitador() {
        return digitador;
    }

    public void setDigitador(String digitador) {
        this.digitador = digitador;
    }

    public String getInvestigador() {
        return investigador;
    }

    public void setInvestigador(String investigador) {
        this.investigador = investigador;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public Integer getReinicio() {
        return reinicio;
    }

    public void setReinicio(Integer reinicio) {
        this.reinicio = reinicio;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }
    
    
    
}
