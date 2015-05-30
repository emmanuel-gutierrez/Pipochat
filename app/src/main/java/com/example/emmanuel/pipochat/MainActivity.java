package com.example.emmanuel.pipochat;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getName();
    private static String sIdUsuario;
    private EditText etMensaje;
    private Button btnEnviar;
    private ListView lvChat;
    private ArrayList<Mensaje> mMensajes;
    private ChatListAdapter mAdapter;
    private static final int max=50;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ha9NfBtO6Vc8Vn0YQamkDwuj8lPniInF3jvRsj8a", "kkDA0D3gFjqV92QIkXWNcUSN5lPTMRoijnV0St9x");
        ParseObject.registerSubclass(Mensaje.class);

        /*ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().saveInBackground(); // <--- This Line
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        */



        if(ParseUser.getCurrentUser()!=null){
            Iniciar();
        }
        else{
            IniciaSesion();
        }

        handler.postDelayed(runnable, 100);
      /*  ParseUser user = new ParseUser();
        user.setUsername("my name");
        user.setPassword("my pass");
        user.setEmail("email@example.com");

// other fields can be set just like with ParseObject
        user.put("phone", "650-555-0000");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });*/
    }

    private void Iniciar() {
        sIdUsuario=ParseUser.getCurrentUser().getObjectId();
        EmpezarMensajeria();
    }

    private void IniciaSesion() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Fallo el login: " + e.toString());
                } else {
                    Iniciar();
                }
            }
        });
    }


    private void EmpezarMensajeria ()
    {
        etMensaje=(EditText)findViewById(R.id.etMensaje);
        btnEnviar=(Button)findViewById(R.id.btnEnviar);
        lvChat=(ListView)findViewById(R.id.lvChat);
        mMensajes= new ArrayList<>();
        mAdapter= new ChatListAdapter(MainActivity.this,sIdUsuario,mMensajes);
        lvChat.setAdapter(mAdapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etMensaje.getText().toString();
                //ParseObject mensaje = new ParseObject("Mensaje");

                Mensaje mensaje = new Mensaje();
                mensaje.put("IDUsuario", sIdUsuario);
                mensaje.put("texto", info);
                mensaje.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        recibirMensaje();
                    }
                });
                etMensaje.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void recibirMensaje(){
        ParseQuery<Mensaje> query = ParseQuery.getQuery(Mensaje.class);

        query.setLimit(max);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Mensaje>() {
            @Override
            public void done(List<Mensaje> mensajes, ParseException e) {
                if(e==null){
                    mMensajes.clear();
                    mMensajes.addAll(mensajes);
                    mAdapter.notifyDataSetChanged();
                    lvChat.invalidate();
                }else{
                    Log.d("message","Error: "+e.getMessage());
                }
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            actualizarMensajes();
            handler.postDelayed(this,100);
        }
    };

    private void actualizarMensajes() {
        recibirMensaje();
    }
}
