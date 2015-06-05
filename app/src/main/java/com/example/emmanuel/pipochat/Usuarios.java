package com.example.emmanuel.pipochat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmanuel on 6/5/2015.
 */
public class Usuarios extends Activity{

    private ArrayList<ParseUser> losUsuarios;

    private static ParseUser Usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        getActionBar().setDisplayHomeAsUpEnabled(false);

        ActualizarEstadoUsuario(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActualizarEstadoUsuario(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarListaUsuarios();

    }

    private void ActualizarEstadoUsuario(boolean online) {
        Usuario.put("online", online);
        Usuario.saveEventually();
    }

    private void cargarListaUsuarios() {
        final ProgressDialog dia = ProgressDialog.show(this, null, "Loading...");
        ParseUser.getQuery().whereNotEqualTo("username", Usuario.getUsername())
                .findInBackground(new FindCallback<ParseUser>() {

                    @Override
                    public void done(List<ParseUser> li, ParseException e) {
                        dia.dismiss();
                        if (li != null) {
                            if (li.size() == 0) {
                                Toast.makeText(Usuarios.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            }

                            losUsuarios = new ArrayList<ParseUser>(li);
                            ListView list = (ListView) findViewById(R.id.ListaUsuarios);
                            list.setAdapter(new UserAdapter());
                            list.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0,
                                                        View arg1, int pos, long arg3) {
                                    startActivity(new Intent(Usuarios.this, MainActivity.class).putExtra("ExtraData", losUsuarios.get(pos).getUsername()));
                                }
                            });
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(Usuarios.this).create();
                            alertDialog.setTitle("Alert!");
                            alertDialog.setMessage(e.getMessage());
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });
    }
    private class UserAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return losUsuarios.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public ParseUser getItem(int arg0)
        {
            return losUsuarios.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            if (v == null)
                v = getLayoutInflater().inflate(R.layout.chat_item, null);

            ParseUser c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getUsername());
//            lbl.setCompoundDrawablesWithIntrinsicBounds(
//                    c.getBoolean("online") ? R.drawable.ic_online
//                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

            return v;
        }

    }
}



