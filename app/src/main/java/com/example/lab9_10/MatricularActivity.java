package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lab9_10.Database.DataBaseHelper;
import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Entity.Estudiante;
import com.example.lab9_10.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class MatricularActivity extends AppCompatActivity {

    private ImageView button;
    private ListView lv;
    private List<Curso> selectedItems = new ArrayList<>();
    private ArrayAdapter<Curso> adapter;
    Model model= new Model(this);
    private TextView id;
    private EditText nombre;
    private EditText apellidos;
    private EditText edad;
    private CheckedTextView ctv;
    private List<Curso> oldList = new ArrayList<>();
    Curso[] array;
    Estudiante estudiante = new Estudiante();
    Toolbar toolbar;
    boolean admin=false;
    List<Curso> cursos = new ArrayList<>();
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricular);
        toolbar=findViewById(R.id.toolbar_matricula);

        toolbar.setTitle("Editar Matricula");
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.menu_logout) {
                    Intent intent= new Intent(MatricularActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return  true;
                }

                return false;
            }
        });

        admin=getIntent().getBooleanExtra("admin",false);
        lv = findViewById(R.id.checkeable_list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        id = findViewById(R.id.cedulaText);
        nombre = findViewById(R.id.nombreText);
        apellidos = findViewById(R.id.apellidosText);
        edad = findViewById(R.id.edadText);



        button = findViewById(R.id.matricularBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    if(!admin) {
                        actualizarMatricula();
                        Toast.makeText(MatricularActivity.this,"Informacion actualizada correctamente",Toast.LENGTH_LONG).show();
                    }
                    else{
                        actualizarEstudiante();
                        Toast.makeText(MatricularActivity.this,"Informacion actualizada correctamente",Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(MatricularActivity.this,ListEstudianteActivity.class);
                        intent.putExtra("admin",admin);
                        startActivity(intent);
                        finish();
                    }

                }
                catch (Exception e) {
                    Toast.makeText(MatricularActivity.this,"Ocurrio un error al actualizar la informacion",Toast.LENGTH_LONG).show();
                }
            }
        });


        try {
            cursos = model.getAllCursos();
        } catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error al cargar los cursos", Toast.LENGTH_SHORT).show();
        }

        if (cursos != null) {

            array = new Curso[cursos.size()];
            adapter = new ArrayAdapter<Curso>(this, R.layout.matricular_row, R.id.txt_lan, cursos.toArray(array));


            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(admin){
                        CheckedTextView tv= (CheckedTextView) view;
                        tv.setFocusable(false);
                        tv.setFocusableInTouchMode(false);
                    }
                    Curso selectedItem = adapter.getItem(position);
                    if (contains(selectedItems, selectedItem)) {
                        remove(selectedItems, selectedItem);
                    } else
                        selectedItems.add(selectedItem);

                }
            });

            this.cargarDatos();

        }
    }

    private boolean contains(List<Curso> list, Curso curso) {
        for (Curso i : list) {
            if (i.getId().equals(curso.getId()))
                return true;
        }
        return false;
    }

    private void remove(List<Curso> list, Curso curso) {
        for (Curso i : list) {
            if (i.getId().equals(curso.getId())) {
                list.remove(i);
                break;
            }
        }
    }

    private void cargarDatos() {
        estudiante = (Estudiante) getIntent().getSerializableExtra("estudiante");
        if (estudiante != null) {
            this.id.setText(estudiante.getId());
            this.nombre.setText(estudiante.getNombre());
            this.apellidos.setText(estudiante.getApellidos());
            this.edad.setText("" + estudiante.getEdad());
            if(!admin) {
                this.nombre.setFocusable(false);
                this.apellidos.setFocusable(false);
                this.edad.setFocusable(false);
            }
            setSelectedItems(this.adapter, estudiante.getCursos());
            this.selectedItems = estudiante.getCursos();
            this.oldList = new ArrayList<>(estudiante.getCursos());
            if(admin){
                toolbar.setTitle("Ver Estudiante");
                //this.button.setVisibility(View.GONE);
               // this.lv.setEnabled(false);
                View v = getLayoutInflater().inflate(R.layout.matricular_row, null);
                CheckedTextView txt=findViewById(R.id.txt_lan);
                for(int i=0; i<array.length; i++){
                    adapter.getView(i,txt,(ViewGroup) v.getParent() ).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }

        }


    }

    private void setSelectedItems(ArrayAdapter<Curso> adapter, List<Curso> list) {
        for (Curso curso : list) {
            int position = getPosition(curso, this.array);
            lv.setItemChecked(position, true);

        }
    }

    private int getPosition(Curso curso, Curso[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getId().equals(curso.getId()))
                return i;
        }
        return -1;
    }


    private void eliminaCursos() throws Exception {

        for (Curso curso : this.oldList) {
            if (!contains(selectedItems, curso)) {
                model.deleteEstudianteCurso(estudiante.getId(), curso.getId());
            }
        }


    }

    private void agregarCursos() throws Exception {

        for (Curso curso : selectedItems) {
            if (!contains(oldList, curso)) {
                model.insertEstudianteCurso(estudiante.getId(), curso.getId());
            }
        }


    }

    private void actualizarMatricula() throws Exception {
        this.eliminaCursos();
        this.agregarCursos();
    }

    private void actualizarEstudiante() throws  Exception{
          Estudiante estudiante = new Estudiante(this.id.getText().toString(), this.nombre.getText().toString(),
                    this.apellidos.getText().toString(), Integer.parseInt(this.edad.getText().toString()));
            if (!model.updateEstudiate(estudiante))
                throw  new Exception();




    }
    @Override
    public void onBackPressed() {

        if(admin) {
            Intent intent;
            intent = new Intent(this, ListEstudianteActivity.class);
            startActivity(intent);
            finish();
        }



    }


}
