package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.database.sqlite.*;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab9_10.Database.DataBaseHelper;
import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Entity.Estudiante;
import com.example.lab9_10.Entity.Usuario;
import com.example.lab9_10.Model.Model;

public class MainActivity extends AppCompatActivity {
        private ImageView login;
        private EditText id;
        EditText password;
        TextView register;
        Model model= new Model(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        id=findViewById(R.id.login_id);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        register=findViewById(R.id.registar_text);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), RegistarActivity.class);
                startActivity(intent);


            }
        });


//        Estudiante es= new Estudiante("117370720","Jonathan","Estrada",21);
//       Usuario usuario= new Usuario("root","root","admin");
//        Curso curso= new Curso("1","Matematicas",4);
//        Curso curso2= new Curso("2","Fundamentos de Informatica",4);
//        Curso curso3= new Curso("3","Programacion I",4);
//        Curso curso4= new Curso("4","Programacion II",4);
//        Curso curso5= new Curso("5","Programacion III",4);
//        Curso curso6= new Curso("6","Programacion IV",4);
//        Curso curso7= new Curso("7","Paradigmas de programacion",4);
//        Curso curso8= new Curso("8","Estructuras de Datos",4);
//        Curso curso9= new Curso("9","Estructuras Discretas",4);
//        Curso curso10= new Curso("10","Bases de Datos",4);
//        es.getCursos().add(curso);
//       try {
//        db.insertUsuario(usuario);
//
//       }
//       catch (Exception e){
//           Log.e("ERRORRR, ",e.getMessage());
//       }
//
//        //Intent intent= new Intent(this,MatricularActivity.class);
//        //startActivity(intent);
    }


    private void login(){

        try{
            String username=this.id.getText().toString();
            String password=this.password.getText().toString();
            String rol= this.model.login(username,password);
            if(rol != null){
                if(rol.equals("estudiante")){
                // boolean isNew=getIntent().getBooleanExtra("register",false);


                     Intent intent = new Intent(this, MatricularActivity.class);
                     Estudiante estudiante = this.model.getEstudiante(username);
                     if(estudiante==null)
                         estudiante = model.getSingleEstudiante(username);

                     intent.putExtra("estudiante", estudiante);
                     startActivity(intent);
                     finish();

                }
                else{
                    Intent intent= new Intent(this,NavBarActivity.class);
                    intent.putExtra("admin",true);
                    startActivity(intent);
                    finish();
                }
            }
            else{
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }

        }

        catch (Exception e){
            System.out.println(e.getMessage());
            Log.v("ERROR",e.getMessage());
        }
    }


}
