package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.lab9_10.Adapter.CursosAdapter;
import com.example.lab9_10.Adapter.EstudiantesAdapter;
import com.example.lab9_10.Database.DataBaseHelper;
import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Entity.Estudiante;
import com.example.lab9_10.Helper.RecyclerItemTouchHelper;
import com.example.lab9_10.Model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListEstudianteActivity extends AppCompatActivity
        implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,  EstudiantesAdapter.EstudianteAdapterListener{

    private RecyclerView mRecyclerView;
    EstudiantesAdapter mAdapter;
    private List<Estudiante> list=new ArrayList<>();
    FloatingActionButton fab;
    Model model= new Model(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_estudiantes);
        setTitle("Estudiantes");
        fab=findViewById(R.id.fabAddEstudiante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ListEstudianteActivity.this,RegistarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mRecyclerView=findViewById(R.id.recycler_estudiantes);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initListaEstudiantes();
        mAdapter=new EstudiantesAdapter(this.list,this);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }


    private void initListaEstudiantes(){
        try {
            List<Estudiante> haveMatricula = model.getAllEstudiantes();
            List<Estudiante> all= model.getAllEstudiantesTable();
            List<Estudiante> result=new ArrayList<>(haveMatricula);
            for(Estudiante i: all){
                if(!contains(haveMatricula,i)){
                   result.add(i);
                }
            }
            this.list= new ArrayList<>(result);

        }
        catch (Exception e){
            Toast.makeText(this,"Error al cargar estudiantes",Toast.LENGTH_LONG).show();

        }
    }

    private boolean contains(List<Estudiante> list, Estudiante es){

        for(Estudiante i: list){
            if(i.getId().equals(es.getId()))
                return true;
        }
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Estudiante clicked = mAdapter.getSwipedItem(viewHolder.getAdapterPosition());
        if (direction != ItemTouchHelper.START) {

        Intent intent = new Intent(this, MatricularActivity.class);
        intent.putExtra("estudiante", clicked);
        intent.putExtra("admin", true);
        mAdapter.notifyDataSetChanged(); //restart left swipe view
        startActivity(intent);
        finish();
        mAdapter.notifyDataSetChanged();
          }
        else{
            try {
                if (model.deleteEstudiante(clicked)) {
                    Toast.makeText(this, "Estudiante Eliminado Correctamente", Toast.LENGTH_LONG).show();
                    mAdapter.removeItem(position);
                    mAdapter.notifyDataSetChanged();

                }
                else
                    throw  new Exception();

            }
            catch (Exception e){
                Toast.makeText(this,"Ocurrio un error al eliminar el estudiante",Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.menu_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               mAdapter.getFilter().filter(newText);
               return false;
            }
        });

        return true;
    }

    @Override
    public void onItemMove(int source, int target) {
        mAdapter.onItemMove(source, target);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //TODO it's not working yet


        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Estudiante jobApplication) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Curso" + jobApplication.getNombre(), Toast.LENGTH_SHORT).show();
    }
}
