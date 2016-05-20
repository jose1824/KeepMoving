package com.example.shipp.keepmoving.Clases;

import com.example.shipp.keepmoving.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubudesktop1 on 17/05/16.
 */
public class DataSource {

    public static List ACADEMIAS = new ArrayList<Academia>();

    static {
        ACADEMIAS.add(new Academia("Academia 1", R.mipmap.ic_account_circle_black_24dp));
        ACADEMIAS.add(new Academia("Academia 2", R.mipmap.ic_done_black_24dp));
    }

}
