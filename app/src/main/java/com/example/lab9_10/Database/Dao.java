package com.example.lab9_10.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

public class Dao {

    private static SQLiteDatabase db=null;
    private static Dao instance=null;

    private Dao(){
        initDatabase();
    }

    public Dao getInstance(){
        return db == null ? instance=new Dao() : instance;
    }

    private static void initDatabase(){
       try{
            db=SQLiteDatabase.openDatabase(
                    "/data/data/cis493.sqldatabases/Universidad",
                    null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);
       }
       catch (SQLiteException e){
           Log.e("error",e.getMessage());
       }
    }

    private static void initEstudiantesTable(){

    }
}
