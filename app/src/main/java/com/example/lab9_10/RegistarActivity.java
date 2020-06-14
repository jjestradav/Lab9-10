package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab9_10.Entity.Estudiante;
import com.example.lab9_10.Entity.Usuario;
import com.example.lab9_10.Model.Model;

public class RegistarActivity extends AppCompatActivity {

    EditText id;
    EditText nombre;
    EditText apellidos;
    EditText edad;
    EditText password;
    ImageView btn;
    Model model= new Model(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        setTitle("Registrarse");
        id=findViewById(R.id.registar_id);
        nombre=findViewById(R.id.registrar_nombre);
        apellidos=findViewById(R.id.registrar_apellidos);
        edad=findViewById(R.id.registrar_edad);
        password=findViewById(R.id.registrar_contraseña);
        btn=findViewById(R.id.registarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

    }

    private boolean verificar(){
        if(id.getText().toString().isEmpty())
            return  false;
        if(nombre.getText().toString().isEmpty())
            return false;
        if(apellidos.getText().toString().isEmpty())
            return false;
        if(edad.getText().toString().isEmpty())
            return false;
        if(password.getText().toString().isEmpty())
            return false;

        return true;
    }

    private void registrar(){
        if(verificar()){

            try {
                Usuario us = new Usuario(this.id.getText().toString(), this.password.getText().toString(), "estudiante");
                Estudiante es = new Estudiante(us.getId(), this.nombre.getText().toString(), this.apellidos.getText().toString(),
                        Integer.parseInt(this.edad.getText().toString()));

                if(model.getEstudiante(es.getId())==null) {
                   boolean a= model.insertUsuario(us);
                   boolean b= model.insertEstudiante(es);
                    Toast.makeText(this, "Estudiante Registrado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this,ListEstudianteActivity.class);
                    intent.putExtra("register", true);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this,"Error! Estudiante ya existe",Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }


        }
        else{
            Toast.makeText(this,"Por favor revise los cambios",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
