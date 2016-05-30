package com.example.shipp.keepmoving.ClasesAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shipp.keepmoving.ClasesFragments.FragmentAcademias;
import com.example.shipp.keepmoving.ClasesFragments.FragmentEventos;
import com.example.shipp.keepmoving.ClasesFragments.FragmentPerfilAcademia;

/**
 * Created by xubudesktop1 on 19/05/16.
 */
public class TabAdapterMainAcademia extends FragmentStatePagerAdapter {

    int tabCount;

    public TabAdapterMainAcademia(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentEventos tabEventos = new FragmentEventos();
                return tabEventos;
            case 1:
                FragmentAcademias fragmentAcademias = new FragmentAcademias();
                return fragmentAcademias;
            case 2:
                FragmentPerfilAcademia tabPerfilAcademia = new FragmentPerfilAcademia();
                return tabPerfilAcademia;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
