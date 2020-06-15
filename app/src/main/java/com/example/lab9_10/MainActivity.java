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
//        register=findViewById(R.id.registar_text);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getApplicationContext(), RegistarActivity.class);
//                startActivity(intent);
//
//
//            }
//        });


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
