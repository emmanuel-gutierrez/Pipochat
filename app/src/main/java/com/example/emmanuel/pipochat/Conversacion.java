package com.example.emmanuel.pipochat;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Rodolfo on 6/5/2015.
 */
@ParseClassName("Conversacion")
public class Conversacion extends ParseObject{
    public String obtenerID()
    {
        return getString("IDUsuario");
    }
    public void ponerID(String id){
        put("IDUsuario",id);
    }
    public String obtenerID2()
    {
        return getString("IDUsuario2");
    }
    public void ponerID2(String id){
        put("IDUsuario2",id);
    }
}
