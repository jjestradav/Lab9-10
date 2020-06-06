package com.example.lab9_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MatricularActivity extends AppCompatActivity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricular);
        lv=findViewById(R.id.checkeable_list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
}
