package com.trillsolution.dominobook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.trillsolution.dominobook.adapter.RVAdapter;
import com.trillsolution.dominobook.model.Domino;
import com.trillsolution.dominobook.preferences.SharedPreferencesUtil;
import com.trillsolution.dominobook.recycler.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity {

    Gson gson;

    Activity activity;
    Context context;

    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;

    RVAdapter adapter;

    Dialog dialog;

    EditText player1;
    EditText player2;
    EditText player3;
    EditText player4;

    private void initUI() {
        gson = new Gson();
        activity = this;
        context = activity.getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameDialog().show();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rv_home);
        rv.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(linearLayoutManager);

        adapter = new RVAdapter(activity, context);

        rv.setAdapter(adapter);

        //rv.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, rv, new itemClickListener()));
    }

    private Dialog startGameDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_start_game);
        dialog.setTitle("Begin a domino game...");
        dialog.getWindow().getAttributes().width = RecyclerView.LayoutParams.FILL_PARENT;
        dialog.setCancelable(true);

        player1 = (EditText) dialog.findViewById(R.id.player1_name);
        player2 = (EditText) dialog.findViewById(R.id.player2_name);
        player3 = (EditText) dialog.findViewById(R.id.player3_name);
        player4 = (EditText) dialog.findViewById(R.id.player4_name);

        Button start = (Button) dialog.findViewById(R.id.btn_start_game);
        Button cancel = (Button) dialog.findViewById(R.id.btn_cancel_game);

        start.setOnClickListener(new StartGameOnClickListener());
        cancel.setOnClickListener(new CancelGameOnClickListener());

        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (adapter != null)
            adapter.updateAdapter();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StartGameOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
            Intent in = new Intent(activity, GameActivity.class);
            if (player1.getText().toString().isEmpty() && player2.getText().toString().isEmpty())
                return;
            Domino domino = new Domino(player1.getText().toString(),
                    player2.getText().toString(),
                    player3.getText().toString(),
                    player4.getText().toString());
            String data = gson.toJson(domino);
            in.putExtra("domino", data);
            startActivity(in);
        }
    }

    private class CancelGameOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    }

    /*private class itemClickListener implements RecyclerItemClickListener.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            if (adapter != null) adapter.getGameDetail(position).show();
        }
    }*/

}
