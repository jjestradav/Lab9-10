package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.example.lab9_10.Adapter.CursosAdapter;
import com.example.lab9_10.Database.DataBaseHelper;
import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Helper.RecyclerItemTouchHelper;
import com.example.lab9_10.Model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListCursoActivity extends AppCompatActivity
        implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,  CursosAdapter.CursoAdapterListener{

    SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    CursosAdapter mAdapter;
    private List<Curso> list=new ArrayList<>();
    Model model= new Model(this);
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cursos);
        setTitle("Cursos");
        fab=findViewById(R.id.fabAddCurso);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ListCursoActivity.this,AddCursoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mRecyclerView=findViewById(R.id.recycler_cursos);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initListaCursos();
        mAdapter=new CursosAdapter(this.list,this);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

    }

    private void initListaCursos(){
        try {
            this.list = model.getAllCursos();
        }
        catch (Exception e){
            Toast.makeText(this,"Error al cargar cursos",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Curso clicked = mAdapter.getSwipedItem(viewHolder.getAdapterPosition());
        if (direction != ItemTouchHelper.START) {
        Intent intent = new Intent(this, AddCursoActivity.class);
        intent.putExtra("curso", clicked);
        mAdapter.notifyDataSetChanged(); //restart left swipe view
        startActivity(intent);
        finish();
        mAdapter.notifyDataSetChanged();
          }
        else {
            if (viewHolder instanceof CursosAdapter.MyViewHolder) {

                try {
                    // save the index deleted
                    final int deletedIndex = viewHolder.getAdapterPosition();
                    // remove the item from recyclerView
                    mAdapter.removeItem(viewHolder.getAdapterPosition());
                    if (model.deleteCurso(clicked)) {
                        Toast.makeText(ListCursoActivity.this,"Curso eliminado con Exito",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(ListCursoActivity.this,"Ocurrio un error al eliminar el curso",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){

                }

                // showing snack bar with Undo option

//                Snackbar snackbar = Snackbar.make(getV, name + " removido!", Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // undo is selected, restore the deleted item from adapter
//                        mAdapter.restoreItem(deletedIndex);
//                    }
////                });
//                snackbar.setActionTextColor(Color.YELLOW);
//                snackbar.show();
            }
        }
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
    public void onBackPressed() {




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
    public void onContactSelected(Curso jobApplication) { //TODO get the select item of recycleView
        Toast.makeText(getApplicationContext(), "Curso" + jobApplication.toString(), Toast.LENGTH_SHORT).show();
    }
}
