package com.example.lab9_10.Entity;

import java.io.Serializable;

public class Curso implements Serializable {

    private  String id;
    private  String Descripcion;
    private  int creditos;

    public Curso() {
    }

    public Curso(String id, String descripcion, int creditos) {
        this.id = id;
        Descripcion = descripcion;
        this.creditos = creditos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString(){
        return this.Descripcion;
    }
}
