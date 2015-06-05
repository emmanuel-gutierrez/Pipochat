package com.example.emmanuel.pipochat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by Rodolfo on 5/29/2015.
 */
public class ChatListAdapter extends ArrayAdapter<Mensaje2> {
    private String mIDUsuario;

    public ChatListAdapter(Context context,String IDUsuario,List<Mensaje2> mensajes) {
        super(context, 0, mensajes);
        this.mIDUsuario=IDUsuario;
    }

    @Override
    public View getView(int posicion,View convertView,ViewGroup padre)
    {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.chat_item,padre,false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView)convertView.findViewById(R.id.ivPerfilIzq);
            holder.imageRight = (ImageView)convertView.findViewById(R.id.ivPerfilDer);
            holder.body= (TextView)convertView.findViewById(R.id.tvCuerpo);
            convertView.setTag(holder);
        }
        final Mensaje2 mensaje = getItem(posicion);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean soyYo= mensaje.obtenerID().equals(mIDUsuario);

        if(soyYo){
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        }
        else{
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        }
        final ImageView perfilView=soyYo ? holder.imageRight : holder.imageLeft;
        Picasso.with(getContext()).load(obtenerURLPerfil(mensaje.obtenerID())).into(perfilView);
        holder.body.setText(mensaje.obtenerTexto());
        return convertView;
    }

    private static String obtenerURLPerfil (final String IDUsuario)
    {
        String hex="";

        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(IDUsuario.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "http:/www.gravatar.com/avatar/"+hex+"?d=identicon";
    }

    final class ViewHolder {

        public ImageView imageLeft;

        public ImageView imageRight;

        public TextView body;

    }
}
