package com.example.shipp.keepmoving.ClasesFirebase;
import android.content.Context;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by shipp on 3/21/2016.
 */
public class FirebaseControl {
    private final String urlFirebase = "https://keep-moving-data.firebaseio.com/";

    public FirebaseControl(){    }

    public String  obtieneUrlFirebase(){
        return urlFirebase;
    }



    /*public String getuIdSession(){
        String uID;
        AuthData authData = ref.getAuth();
        if (authData != null){
            uID = authData.getUid().toString();
        }else{
            uID = null;
        }
        return uID;
    }

    public boolean logOff(){
        ref.unauth();

        return true;
    }*/
}
