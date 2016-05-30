package com.example.shipp.keepmoving.ClasesFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shipp.keepmoving.ClasesFirebase.FirebaseControl;
import com.example.shipp.keepmoving.R;


public class FragmentVideos extends Fragment {
    ImageButton jvideo1, jvideo2, jvideo3, jvideo4, jyoutube;
    CoordinatorLayout cLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cLayout = (CoordinatorLayout)
                inflater.inflate(R.layout.fragment_fragment_videos, container, false);

        cLayout.findViewById(R.id.videos_coordinator);

        inicializaComponentes();

        jvideo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaVideo1();
            }
        });

        jvideo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaVideo2();
            }
        });

        jvideo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaVideo3();
            }
        });

        jvideo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaVideo4();
            }
        });

        jyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaYoutube();
            }
        });

        return cLayout;
    }

    private void inicializaComponentes(){
        jvideo1 = (ImageButton)cLayout.findViewById(R.id.xvideo1);
        jvideo2 = (ImageButton)cLayout.findViewById(R.id.xvideo2);
        jvideo3 = (ImageButton)cLayout.findViewById(R.id.xvideo3);
        jvideo4 = (ImageButton)cLayout.findViewById(R.id.xvideo4);
        jyoutube = (ImageButton)cLayout.findViewById(R.id.xyoutube);
    }

    public void iniciaVideo1(){
        String link = "https://www.youtube.com/watch?v=mSt-OsN4QjU";
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    public void iniciaVideo2(){
        String link = "https://www.youtube.com/watch?v=ulTszGXn2GU&feature=youtu.be";
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    public void iniciaVideo3(){
        String link = "https://www.youtube.com/watch?v=mSt-OsN4QjU";
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    public void iniciaVideo4(){
        String link = "https://www.youtube.com/watch?v=i1rIzW52sNg";
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    public void iniciaYoutube(){
        String link = "https://www.youtube.com/user/IslandTouchChannel";
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

}
