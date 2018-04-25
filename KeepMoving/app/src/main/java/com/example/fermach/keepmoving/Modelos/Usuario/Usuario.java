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
        private String apellidos;
        private String biografia;
        private String aficiones;
        private byte[] foto;

    public Usuario(String nombre, String apellidos, String correo, String biografia, String aficiones) {
        this.nombre = nombre;
        this.apellidos=apellidos;
        this.correo = correo;
        this.biografia= biografia;
        this.aficiones=aficiones;
    }

    public Usuario(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", biografia='" + biografia + '\'' +
                ", aficiones='" + aficiones + '\'' +
                '}';
    }
}
