package com.example.emmanuel.pipochat;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;


public class login_activity extends ActionBarActivity {
    private String _error;
    ParseBusiness _business = new ParseBusiness();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        _business.Initialize(this);
        //Intent intent = _business.AutoLoginCheck(this);
        //if(intent != null)
         //   startActivity(intent);
        Button loginbtn = (Button)findViewById(R.id.btnLogin);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ModelValidation())
                {
                    String username = ((TextView)findViewById(R.id.tbLogin)).getText().toString();
                    String password = ((TextView)findViewById(R.id.tbPassword)).getText().toString();
                    _business.Login(username,password,getApplicationContext());
                }
                else
                {
                    GenerateAlert("El usuario/contraseña son incorrectos.");
                }
            }
        });
        Button registerbtn = (Button)findViewById(R.id.btnRegister);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent().setClass(getBaseContext(), register.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_activity, menu);
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

    boolean ModelValidation()
    {
        String username = ((TextView)findViewById(R.id.tbLogin)).getText().toString();
        String password = ((TextView)findViewById(R.id.tbPassword)).getText().toString();
        if (username.length() > 2 && password.length() > 5)
            return true;
        else
            return false;
    }

    void GenerateAlert(String alertText)
    {
        AlertDialog builder = new AlertDialog.Builder(this)
                .setMessage(alertText).show();

    }



}
