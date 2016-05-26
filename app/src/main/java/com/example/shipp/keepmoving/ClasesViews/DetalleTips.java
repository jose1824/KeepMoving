package com.example.shipp.keepmoving.ClasesViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipp.keepmoving.R;

public class DetalleTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tips);

        String title = getIntent().getStringExtra("titulo");
        Integer id = getIntent().getIntExtra("imagen", 0);

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        Toast.makeText(getApplicationContext(), "" + id, Toast.LENGTH_LONG).show();

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(id);
    }
}
