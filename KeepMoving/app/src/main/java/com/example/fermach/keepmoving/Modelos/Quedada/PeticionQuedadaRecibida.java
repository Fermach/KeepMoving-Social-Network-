package com.example.fermach.keepmoving.Modelos.Quedada;

import android.graphics.Bitmap;

/**
 * Created by Fermach on 19/05/2018.
 */

public class PeticionQuedadaRecibida {

    private Bitmap foto;

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    private String id_peticion;
    private String id;
    private String autor;
    private String autor_uid;
    private String lugar;
    private String fecha;
    private String hora;
    private String deporte;
    private String info;
    private String plazas;
    private String longitud;
    private String latitud;
    private String autor_peticion;
    private String autor_peticion_nombre;
    private String num_plazas_solicitadas;
    private String estado;

    public PeticionQuedadaRecibida() {
    }

    public PeticionQuedadaRecibida(String id, String autor_peticion_nombre, String autor, String autor_uid, String lugar, String fecha, String hora, String deporte, String info, String plazas,
                                   String longitud, String latitud, String num_plazas_solicitadas, String estado,  String autor_peticion) {
        this.id = id;
        this.autor_peticion_nombre=autor_peticion_nombre;
        this.autor = autor;
        this.autor_uid = autor_uid;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
        this.deporte = deporte;
        this.info = info;
        this.plazas = plazas;
        this.longitud = longitud;
        this.latitud = latitud;
        this.num_plazas_solicitadas = num_plazas_solicitadas;
        this.estado = estado;
        this.autor_peticion=autor_peticion;
    }

    public String getAutor_peticion_nombre() {
        return autor_peticion_nombre;
    }

    public void setAutor_peticion_nombre(String autor_peticion_nombre) {
        this.autor_peticion_nombre = autor_peticion_nombre;
    }

    public String getId_peticion() {
        return id_peticion;
    }

    public void setId_peticion(String id_peticion) {
        this.id_peticion = id_peticion;
    }

    public String getId() {
        return id;
    }

    public String getAutor_peticion() {
        return autor_peticion;
    }

    public void setAutor_peticion(String autor_peticion) {
        this.autor_peticion = autor_peticion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor_uid() {
        return autor_uid;
    }

    public void setAutor_uid(String autor_uid) {
        this.autor_uid = autor_uid;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPlazas() {
        return plazas;
    }

    public void setPlazas(String plazas) {
        this.plazas = plazas;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getNum_plazas_solicitadas() {
        return num_plazas_solicitadas;
    }

    public void setNum_plazas_solicitadas(String num_plazas_solicitadas) {
        this.num_plazas_solicitadas = num_plazas_solicitadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PeticionQuedadaRecibida{" +
                "foto=" + foto +
                ", id_peticion='" + id_peticion + '\'' +
                ", id='" + id + '\'' +
                ", autor='" + autor + '\'' +
                ", autor_uid='" + autor_uid + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", deporte='" + deporte + '\'' +
                ", info='" + info + '\'' +
                ", plazas='" + plazas + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                ", autor_peticion='" + autor_peticion + '\'' +
                ", autor_peticion_nombre='" + autor_peticion_nombre + '\'' +
                ", num_plazas_solicitadas='" + num_plazas_solicitadas + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
