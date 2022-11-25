// LAURA E EVANDRO
package com.example.restaurante;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInserir = findViewById(R.id.btnInserir);
        EditText txtMesa = findViewById(R.id.txtMesa);
        EditText txtItem = findViewById(R.id.txtItem);
        EditText txtPrato = findViewById(R.id.txtPrato);
        ListView lista = findViewById(R.id.lista);

        DatabaseReference bd = FirebaseDatabase.getInstance().getReference();
        DatabaseReference restaurante = bd.child("restaurante");

        btnInserir.setOnClickListener(view -> {
            int mesa = Integer.parseInt(txtMesa.getText().toString());
            int item = Integer.parseInt(txtItem.getText().toString());
            String prato = txtPrato.getText().toString();

            String chave = restaurante.push().getKey();

            Pedido pedido = new Pedido(chave, mesa, item, prato);
            restaurante.child(chave).setValue(pedido);

            txtMesa.setText("");
            txtItem.setText("");
            txtPrato.setText("");
        });

        FirebaseListOptions<Pedido> options = new FirebaseListOptions.Builder<Pedido>()
                .setLayout(R.layout.item_lista)
                .setQuery(restaurante, Pedido.class)
                .setLifecycleOwner(this)
                .build();

        ItensAdapter adapter = new ItensAdapter(options);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            Pedido pedido = adapter.getItem(i);
            pedido.setAtendido(true);

            String chave = pedido.getChave();
            TextView txtAtendido = view.findViewById(R.id.txtAtendido);
            txtAtendido.setText("SIM");
            txtAtendido.setTextColor(Color.GREEN);

            restaurante.child(chave).setValue(pedido);
        });

        lista.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Pedido pedido = adapter.getItem(i);
            String chave = pedido.getChave();
            restaurante.child(chave).setValue(null);
            return true;
        });

    }

    private class ItensAdapter extends FirebaseListAdapter<Pedido> {
        public ItensAdapter(FirebaseListOptions options) {
            super(options);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void populateView(View view, Pedido pedido, int position) {
            TextView tvMesaLista = view.findViewById(R.id.txtMesaLista);
            TextView tvItemLista = view.findViewById(R.id.txtItemLista);
            TextView tvPratoLista = view.findViewById(R.id.txtPratoLista);

            tvMesaLista.setText(Integer.toString(pedido.getMesa()));
            tvItemLista.setText(Integer.toString(pedido.getItem()));
            tvPratoLista.setText(pedido.getPrato());
        }
    }
}