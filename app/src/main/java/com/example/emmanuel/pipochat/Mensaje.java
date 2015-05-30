package com.example.emmanuel.pipochat;

import com.parse.ParseObject;

/**
 * Created by Rodolfo on 5/29/2015.
 */
public class Mensaje extends ParseObject {
    public String obtenerID()
    {
        return getString("IDUsuario");
    }
    public String obtenerTexto(){
        return getString("texto");
    }
    public void ponerID(String id){
        put("IDUsuario",id);
    }
    public void ponerTexto(String texto){
        put("texto",texto);
    }
}
