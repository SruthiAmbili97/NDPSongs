package sg.edu.rp.c346.id21006564.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etTitle;
    EditText etSinger;
    EditText etYear;
    RadioButton r1 ,r2,r3,r4,r5;
    Button btnInsert , btnShowlst;
    ArrayList<Song> al;
    ArrayAdapter<Song>aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTitle = findViewById(R.id.etTitle);
        etSinger=findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        r5 = findViewById(R.id.rbtn5);
        btnInsert=findViewById(R.id.btnInsert);
        btnShowlst=findViewById(R.id.btnShowlst);

         al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1,al);
       // lv.setAdapter(aa);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etTitle.getText().toString();
                String data1 = etSinger.getText().toString();
                int  data2= Integer.parseInt(etYear.getText().toString());
                int data3 = Integer.parseInt(r5.getText().toString());

                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertSong(data,data1,data2,data3);

                if (inserted_id != -1){
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}