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


public class register extends ActionBarActivity {


    ParseBusiness _business = new ParseBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button signupbtn = (Button)findViewById(R.id.register);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((TextView)findViewById(R.id.tbLogin)).getText().toString();
                String password = ((TextView)findViewById(R.id.tbPassword)).getText().toString();
                if (ModelValidation())
                {
                    _business.SignUp(username, password);
                }
                else
                    GenerateAlert("Datos inválidos.\nVerifique su información, y vuelva a intentarlo.");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
        String name = ((TextView)findViewById(R.id.tbName)).getText().toString();
        String username = ((TextView)findViewById(R.id.tbLogin)).getText().toString();
        String password = ((TextView)findViewById(R.id.tbPassword)).getText().toString();
        if (username.length() > 2 && password.length() > 5 && name.length()>1)
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
