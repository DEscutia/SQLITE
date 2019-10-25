package net.ivanvega.mibasedatosp77a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnGuardar;
    Button btnEliminar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar = findViewById(R.id.btnAgregar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ContactoActivity.class);
                startActivityForResult(i,1000);
            }
        });

        final DAOContactos dao = new DAOContactos(this);
        if (dao.getAll() !=null){
            recargarLista();
        }

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                final Contacto contacto = dao.cursor((Cursor) lv.getItemAtPosition(pos));
                Snackbar.make(view, "Estás seguro de eliminar este contacto?", Snackbar.LENGTH_LONG)
                        .setAction("si", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dao.delete(contacto.getId());
                                recargarLista();
                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode==RESULT_OK){
                Bundle b = data.getExtras();
                Contacto obj;
                    if (b != null){
                        obj = b.getParcelable("contacto");
                        DAOContactos dao = new DAOContactos(this);
                        dao.insert(obj);
                        recargarLista();
                    }
            }
        }
    }

    public void recargarLista(){
        DAOContactos dao = new DAOContactos(this);
        for (Contacto c : dao.getAll()){
            Toast.makeText(this,
                    c.usuario,
                    Toast.LENGTH_SHORT).show();
        }

        lv = findViewById(R.id.lv);

        SimpleCursorAdapter adp =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        dao.getAllCursor(),
                        new String[]{"usuario","_id","tel"},
                        new int[]{android.R.id.text1, android.R.id.text2
                        },
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE

                );
        lv.setAdapter(adp);
    }
}
