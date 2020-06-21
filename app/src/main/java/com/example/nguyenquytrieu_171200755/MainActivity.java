package com.example.nguyenquytrieu_171200755;

import android.content.Intent;
import android.os.Bundle;

import com.example.nguyenquytrieu_171200755.DBHelper;
import com.example.nguyenquytrieu_171200755.EditActivity;
import com.example.nguyenquytrieu_171200755.R;
import com.example.nguyenquytrieu_171200755.Ticket;
import com.example.nguyenquytrieu_171200755.TicketAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listRes;
    DBHelper dbHelper;
    ArrayList<Ticket> tickets;
    TicketAdapter adapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        search = (EditText) findViewById(R.id.edSearch);
        listRes = (ListView) findViewById(R.id.listRes);
        dbHelper = new DBHelper(MainActivity.this);
        dbHelper.openDB();


        tickets = dbHelper.getAll();
        adapter = new TicketAdapter(tickets, MainActivity.this);
        listRes.setAdapter(adapter);
        registerForContextMenu(listRes);
        // search
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper.openDB();
        ArrayList<Ticket> tickets1 = dbHelper.getAll();
        adapter = new TicketAdapter(tickets1, MainActivity.this);
        listRes.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.itEdit:
                int editPosition = info.position;
                Toast.makeText(this, tickets.get(editPosition).isTheLoai() + "", Toast.LENGTH_SHORT).show();
                Ticket ticket = tickets.get(editPosition);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("TICKET", ticket);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }
}
