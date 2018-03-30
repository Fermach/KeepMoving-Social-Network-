package com.example.fermach.keepmoving.Modelos.Usuario;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Fermach on 28/03/2018.
 */

public class Usuario implements Serializable {
        private String correo;
        private String contraseña;
        private String nombre;
        private String biografia;
        private String aficiones;
        private Uri foto;

    public Usuario(String nombre, String biografia, String aficiones) {
        this.nombre = nombre;
        this.biografia = biografia;
        this.aficiones = aficiones;
    }

    public Usuario(String nombre, String biografia, String aficiones, Uri foto) {
        this.nombre = nombre;
        this.biografia = biografia;
        this.aficiones = aficiones;
        this.foto = foto;
    }

    public Usuario(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getAficiones() {
        return aficiones;
    }

    public void setAficiones(String aficiones) {
        this.aficiones = aficiones;
    }

    public Uri getFoto() {
        return foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", biografia='" + biografia + '\'' +
                ", aficiones='" + aficiones + '\'' +
                ", foto=" + foto +
                '}';
    }
}
