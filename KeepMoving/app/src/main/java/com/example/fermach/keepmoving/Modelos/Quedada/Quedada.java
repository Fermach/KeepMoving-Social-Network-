package com.example.fermach.keepmoving.Modelos.Quedada;

import java.io.Serializable;

/**
 * Objeto Quedada
 *
 * Created by Fermach on 28/03/2018.
 */

public class Quedada implements Serializable {
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

    public Quedada() {
    }

    public Quedada(String id,String autor, String lugar, String fecha, String hora, String deporte,
                   String info, String plazas, String longitud, String latitud) {
        this.id = id;
        this.autor = autor;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
        this.deporte = deporte;
        this.info = info;
        this.plazas = plazas;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getId() {
        return id;
    }

    public String getAutor_uid() {
        return autor_uid;
    }

    public void setAutor_uid(String autor_uid) {
        this.autor_uid = autor_uid;
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

    @Override
    public String toString() {
        return "Quedada{" +
                "ID=" +id+  '\'' +
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
                '}';
    }
}
