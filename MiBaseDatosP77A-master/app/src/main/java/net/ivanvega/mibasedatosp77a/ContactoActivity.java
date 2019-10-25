package net.ivanvega.mibasedatosp77a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;


public class ContactoActivity extends AppCompatActivity {
    Button btn;
    Contacto obj;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        btn = findViewById(R.id.btnGuardar);

        final DAOContactos dao = new DAOContactos(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = ((EditText) findViewById(R.id.txtNombre)).getText().toString();
                String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
                String telefono = ((EditText) findViewById(R.id.txtTelefono)).getText().toString();
                String fecha = ((EditText) findViewById(R.id.txtFecha)).getText().toString();

                obj = new Contacto(dao.getAll().size()+1,nombre,email,telefono,fecha);

                Intent i = new Intent(ContactoActivity.this,MainActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("contacto",obj);
                i.putExtras(b);

                setResult(RESULT_OK,i);

                finish();
            }
        });
    }

}
