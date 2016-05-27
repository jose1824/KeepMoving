package com.example.shipp.keepmoving.ClasesViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipp.keepmoving.R;

public class DetalleTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tips);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaTabsUsuario.class);
                startActivity(i);
                finish();
            }
        });

        String title = getIntent().getStringExtra("titulo");
        Integer id = getIntent().getIntExtra("imagen", 0);

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        Toast.makeText(getApplicationContext(), "" + id, Toast.LENGTH_LONG).show();

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(id);
    }
}
