package com.taliano.file_di_testo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<Studente> list;
    ListView lstSudenti;
    String path;
    ArrayAdapter<Studente> adapter;
    int selectedIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path= MainActivity.this.getFilesDir().getPath();
        path+="/studenti.txt";
        list=new ArrayList<Studente>();

        leggiFile();

        //per fare il collegamneto tra l'arraylist e la listview
        adapter=new ArrayAdapter<Studente>(MainActivity.this, android.R.layout.simple_list_item_1, list); //layout che si voule utilizzare per i singoli record
        lstSudenti=(ListView)findViewById(R.id.listView);
        lstSudenti.setAdapter(adapter);
        lstSudenti.setOnItemClickListener(listener);
    }

    private ListView.OnItemClickListener listener=new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedIndex=position;
        }
    };

    private void leggiFile()
    {
        File file=new File(path);
        if(file.isFile())
        {
            try {
                FileReader reader=new FileReader(file);

                BufferedReader bufferedReader=new BufferedReader(reader); //riga per riga

                String tmp;
                while((tmp=bufferedReader.readLine())!=null)
                {
                    Studente studente=new Studente();
                    studente.setFirstName(bufferedReader.readLine());
                    studente.setLastName(bufferedReader.readLine());
                    studente.setGender(bufferedReader.readLine());
                    studente.setNationality(bufferedReader.readLine());
                    studente.setImg(bufferedReader.readLine());

                    list.add(studente);
                }

                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            alert("File non TROVATO!!");
    }

    private void scriviFile()
    {
        if(!list.isEmpty()) {
            try {
                FileWriter writer=new FileWriter(path,false); //secondo parametro: false overwrite, true append
                BufferedWriter bufferedWriter=new BufferedWriter(writer);

                for(Studente studente:list)
                {
                    bufferedWriter.newLine();
                    bufferedWriter.write(studente.getFirstName());
                    bufferedWriter.newLine();
                    bufferedWriter.write(studente.getLastName());
                    bufferedWriter.newLine();
                    bufferedWriter.write(studente.getGender());
                    bufferedWriter.newLine();
                    bufferedWriter.write(studente.getNationality());
                    bufferedWriter.newLine();
                    bufferedWriter.write(studente.getImg());
                    bufferedWriter.newLine();
                }

                bufferedWriter.flush();
                bufferedWriter.close();
                writer.close();

                alert("File salvato CORRETTAMENTE!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                alert("Lista VUOTA!!");

                FileWriter writer = new FileWriter(path,false);
                BufferedWriter bufferedWriter=new BufferedWriter(writer);

                bufferedWriter.write("");
                bufferedWriter.close();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,1,1,"Add");
        menu.add(Menu.NONE,2,2,"Delete");
        menu.add(Menu.NONE,3,3,"Save");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                visualizzaLayoutAggiungi();
                break;
            case 2:
                cancella();
                break;
            case 3:
                scriviFile();
        }
        return super.onOptionsItemSelected(item);
    }

    private void visualizzaLayoutAggiungi(){
        setContentView(R.layout.aggiungi);
    }

    public void salva (View v){
        EditText nome = findViewById(R.id.editTextNome);
        EditText cognome = findViewById(R.id.editTextCognome);
        EditText nazionalita = findViewById(R.id.editTextNazionalita);

        Studente studente = new Studente();
        studente.setFirstName(nome.getText().toString());
        studente.setLastName(cognome.getText().toString());
        studente.setNationality(nazionalita.getText().toString());
        studente.setGender("");
        studente.setImg("");
        list.add(studente);
        setContentView(R.layout.activity_main);

        adapter=new ArrayAdapter<Studente>(MainActivity.this, android.R.layout.simple_list_item_1, list); //layout che si voule utilizzare per i singoli record
        lstSudenti=(ListView)findViewById(R.id.listView);
        lstSudenti.setAdapter(adapter);
        lstSudenti.setOnItemClickListener(listener);
    }
    public void annulla (View v){

    }

    private void cancella()
    {
        if((selectedIndex!=-1)&&(selectedIndex<list.size())) {
            list.remove(selectedIndex);
            adapter.notifyDataSetChanged(); //refresha
            alert("Record cancellato CORRETTAMENTE!!");
        }
    }

        public void alert(String s)
        {
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        }
}
