package com.example.lab9_10.Entity;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String password;
    private String rol;

    public Usuario(String id, String password, String rol) {
        this.id = id;
        this.password = password;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
