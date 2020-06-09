package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Model.Model;

public class AddCursoActivity extends AppCompatActivity {

    private EditText id;
    private EditText descripcion;
    private EditText creditos;
    ImageView btn;
    Model model= new Model(this);
    boolean edit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curso);
        setTitle("Agregar Curso");
        id=findViewById(R.id.add_curso_id);
        descripcion=findViewById(R.id.add_curso_descripcion);
        creditos=findViewById(R.id.add_curso_creditos);
        recuperar();
        btn=findViewById(R.id.add_curso_Btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit)
                    agregar();
                else
                    editar();
            }
        });

    }

    private void editar(){
        if(validar()){

            try{
                Curso curso = new Curso(this.id.getText().toString(),this.descripcion.getText().toString(),
                        Integer.parseInt(this.creditos.getText().toString()));

                if( model.updateCurso(curso) ){
                    Toast.makeText(this,"Curso actualizado con Exito",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(this,ListCursoActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    throw new Exception();

            }
            catch (Exception e){
                Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"Por favor revise los datos",Toast.LENGTH_LONG).show();
        }
    }
    private boolean validar(){
        if(id.getText().toString().isEmpty())
            return false;
        if(descripcion.getText().toString().isEmpty())
            return false;
        if(creditos.getText().toString().isEmpty())
            return false;

        return true;

    }

    private void agregar(){
        if(validar()){

            try{
                Curso curso = new Curso(this.id.getText().toString(),this.descripcion.getText().toString(),
                        Integer.parseInt(this.creditos.getText().toString()));
                model.insertCurso(curso);
                Toast.makeText(this,"Curso agregado con Exito",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(this,ListCursoActivity.class);
                startActivity(intent);
                finish();
            }
            catch (Exception e){
                Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"Por favor revise los datos",Toast.LENGTH_LONG).show();
        }
    }

    private void recuperar(){
        Curso curso= (Curso)getIntent().getSerializableExtra("curso");
        if(curso != null){
            this.id.setText(curso.getId());
            this.id.setFocusable(false);
            this.descripcion.setText(curso.getDescripcion());
            this.creditos.setText(""+curso.getCreditos());
            edit=true;
            setTitle("Editar Curso");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,ListCursoActivity.class);
        startActivity(intent);
        finish();
    }



}
