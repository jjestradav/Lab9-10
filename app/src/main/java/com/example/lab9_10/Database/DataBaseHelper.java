package com.example.lab9_10.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Entity.Estudiante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(@Nullable Context context) {
        super(context, "universidad.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            createTableEstudiante(db);
            createTableCurso(db);
            createTableEstudianteCurso(db);
            createTableUsuario(db);
        }
        catch (Exception e){
            Log.e("Error",e.getMessage());
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableEstudiante(SQLiteDatabase db){

        String TableEstudiante="CREATE TABLE IF NOT EXISTS Estudiante(" +
                "id VARCHAR(10) PRIMARY KEY,"+
                "nombre VARCHAR(30),"+
                "apellidos VARCHAR(50),"+
                "edad INTEGER"+
                ");";

        db.execSQL(TableEstudiante);
    }


    private void createTableCurso(SQLiteDatabase db){

        String TableCurso="CREATE TABLE IF NOT EXISTS Curso(" +
                "id VARCHAR(5) PRIMARY KEY,"+
                "descripcion VARCHAR(30),"+
                "creditos INTEGER"+
                ");";
        db.execSQL(TableCurso);
    }


    private void createTableEstudianteCurso(SQLiteDatabase db){
        String TableEstudianteCurso="CREATE TABLE IF NOT EXISTS EstudianteCurso (" +
                "estudiante VARCHAR(10),"+
                "curso VARCHAR(5),"+
                "constraint pk_estudianteCurso primary key(estudiante,curso)"+
                ");";

        db.execSQL(TableEstudianteCurso);

    }

    private void createTableUsuario(SQLiteDatabase db){
        String TableUsuario="CREATE TABLE IF NOT EXISTS Usuario(" +
                "id VARCHAR(10) PRIMARY KEY,"+
                "rol VARCHAR(10)"+
                ");";
        db.execSQL(TableUsuario);
    }

    public boolean insertEstudiante(Estudiante estudiante) throws Exception{

           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues cv = new ContentValues();
           cv.put("id", estudiante.getId());
           cv.put("nombre", estudiante.getNombre());
           cv.put("apellidos", estudiante.getApellidos());
           cv.put("edad", estudiante.getEdad());
           db.insert("Estudiante",null,cv);
           for(Curso curso: estudiante.getCursos()){
               insertEstudianteCurso(estudiante.getId(),curso.getId());
           }
           return true;




    }

    public boolean insertCurso(Curso curso) throws Exception{

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", curso.getId());
        cv.put("descripcion", curso.getDescripcion());
        cv.put("creditos", curso.getCreditos());
        return db.insert("Curso",null,cv) == -1 ? false : true;




    }

    public boolean insertEstudianteCurso(String estudiante,String curso) throws Exception{

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("estudiante", estudiante);
        cv.put("curso", curso);
        return db.insert("EstudianteCurso",null,cv) == -1 ? false : true;




    }



    public List<Estudiante> getAllEstudiantes() throws Exception {

       // List<Estudiante> result = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db=null;

        try {
            String queryString = "SELECT Estudiante.id,Estudiante.nombre,Estudiante.apellidos,Estudiante.edad,Curso.id as idCurso,Curso.descripcion,Curso.creditos" +
                    " FROM Estudiante,Curso,EstudianteCurso WHERE EstudianteCurso.estudiante=Estudiante.id AND EstudianteCurso.curso=Curso.id;";
            db = this.getWritableDatabase();
            cursor = db.rawQuery(queryString,null);
            Map<String,Estudiante> map= new HashMap<>();
            while (cursor.moveToNext()) {
                String estudianteId=cursor.getString(0);
                Estudiante es=map.get(estudianteId);
                if(es==null) {
                    Estudiante estudiante = new Estudiante(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getInt(3));
                    estudiante.getCursos().add(new Curso(cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
                    map.put(estudianteId,estudiante);

                }
                else{
                    es.getCursos().add(new Curso(cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
                }
            }
            return new ArrayList<>(map.values());
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if(cursor != null)
                  cursor.close();
            if(db != null)
                db.close();
        }



       // return new ArrayList<>();
    }

    public Estudiante getEstudiante(String id) throws Exception {

        // List<Estudiante> result = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db=null;

        try {
            String queryString = "SELECT Estudiante.id,Estudiante.nombre,Estudiante.apellidos,Estudiante.edad,Curso.id as idCurso,Curso.descripcion,Curso.creditos" +
                    " FROM Estudiante,Curso,EstudianteCurso WHERE EstudianteCurso.estudiante="+id+ "AND EstudianteCurso.curso=Curso.id;";
            db = this.getWritableDatabase();
            cursor = db.rawQuery(queryString,null);
            Map<String,Estudiante> map= new HashMap<>();
            while (cursor.moveToNext()) {
                String estudianteId=cursor.getString(0);
                Estudiante es=map.get(estudianteId);
                if(es==null) {
                    Estudiante estudiante = new Estudiante(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getInt(3));
                    estudiante.getCursos().add(new Curso(cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
                    map.put(estudianteId,estudiante);

                }
                else{
                    es.getCursos().add(new Curso(cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
                }
            }
            List<Estudiante> list= new ArrayList<>(map.values());
            return list.get(0);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }



        // return new ArrayList<>();
    }

    public List<Curso> getAllCursos() throws Exception{
       List<Curso> result= new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase db=null;

        try{
            String query="SELECT * FROM CURSO";
            db= this.getWritableDatabase();
            cursor = db.rawQuery(query, null);
            while(cursor.moveToNext()){
                Curso curso= new Curso(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
                result.add(curso);
            }

            return result;
        }
        catch (Exception e){
            throw e;
        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }

    }

}
