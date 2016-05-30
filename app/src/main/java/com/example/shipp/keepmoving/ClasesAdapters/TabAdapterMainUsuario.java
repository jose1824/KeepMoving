package com.example.shipp.keepmoving.ClasesAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shipp.keepmoving.ClasesFragments.FragmentAcademias;
import com.example.shipp.keepmoving.ClasesFragments.FragmentAgenda;
import com.example.shipp.keepmoving.ClasesFragments.FragmentEventos;
import com.example.shipp.keepmoving.ClasesFragments.FragmentTips;
import com.example.shipp.keepmoving.ClasesFragments.FragmentVideos;

/**
 * Created by xubudesktop1 on 17/05/16.
 */
public class TabAdapterMainUsuario extends FragmentStatePagerAdapter {

    int tabCount;

    public TabAdapterMainUsuario(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentAgenda tabAgenda = new FragmentAgenda();
                return tabAgenda;
            case 1:
                FragmentAcademias tabAcademias = new FragmentAcademias();
                return tabAcademias;
            case 2:
                FragmentTips tabTips = new FragmentTips();
                return tabTips;
            case 3:
                FragmentEventos tabEventos = new FragmentEventos();
                return tabEventos;
            case 4:
                FragmentVideos fragmentVideos = new FragmentVideos();
                return fragmentVideos;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
