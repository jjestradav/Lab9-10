package com.example.lab9_10.Model;

import android.content.Context;

import com.example.lab9_10.Database.DataBaseHelper;
import com.example.lab9_10.Entity.Curso;
import com.example.lab9_10.Entity.Estudiante;
import com.example.lab9_10.Entity.Usuario;

import java.util.List;

public class Model {

    private DataBaseHelper db=null;
    public Model(Context context){
      db=new DataBaseHelper(context);
    }

    public boolean insertEstudiante(Estudiante e) throws Exception{
        return db.insertEstudiante(e);
    }

    public boolean insertUsuario(Usuario us) throws  Exception{
        return db.insertUsuario(us);
    }

    public boolean insertCurso(Curso cur) throws Exception{
        return db.insertCurso(cur);
    }

    public boolean insertEstudianteCurso(String estudiante, String curso) throws Exception{
        return  db.insertEstudianteCurso(estudiante,curso);
    }

    public List<Estudiante> getAllEstudiantes() throws Exception{
        return db.getAllEstudiantes();
    }

    public List<Curso> getAllCursos() throws Exception{
        return db.getAllCursos();

    }

    public String login(String username, String password) throws Exception{
        return db.login(username,password);
    }

    public boolean deleteEstudianteCurso(String estudiante, String curso) throws Exception{
        return  db.deleteEstudianteCurso(estudiante,curso);

    }

    public Estudiante getEstudiante(String id) throws Exception{
        return db.getEstudiante(id);
    }

    public Estudiante getSingleEstudiante(String id) throws Exception{
        return db.getSingleEstudiante(id);
    }

    public  boolean updateEstudiate(Estudiante es) throws Exception{
        return db.updateEstudiante(es);
    }

    public boolean updateCurso(Curso curso) throws  Exception {
        return db.updateCurso(curso);
    }

    public boolean deleteCurso(Curso curso) throws Exception {
        return db.deleteCurso(curso);
    }

}
