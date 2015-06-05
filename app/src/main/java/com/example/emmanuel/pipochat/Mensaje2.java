package com.example.emmanuel.pipochat;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Rodolfo on 6/5/2015.
 */
@ParseClassName("Mensaje2")
public class Mensaje2 extends ParseObject {
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
    public String obtenerIDConv()
    {
        return getString("IDConversacion");
    }
    public void ponerIDConv(String id){
        put("IDConversacion",id);
    }
}
