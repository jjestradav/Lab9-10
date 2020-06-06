package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.database.sqlite.*;
import android.util.Log;

import com.example.lab9_10.Database.DataBaseHelper;
import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Entity.Estudiante;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper db=new DataBaseHelper(this);
        Estudiante es= new Estudiante("117370720","Jonathan","Estrada",21);
        Curso curso= new Curso("1","Matematicas",4);
        es.getCursos().add(curso);
       try {
           db.insertCurso(curso);
           db.insertEstudiante(es);

       }
       catch (Exception e){
           Log.e("ERRORRR, ",e.getMessage());
       }
    }


}
