package com.example.emmanuel.pipochat;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.Console;

import static com.parse.ParseUser.*;

/**
 * Created by Emilio on 6/5/2015.
 */
public class ParseBusiness {

    public void Initialize(Context context){
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "ha9NfBtO6Vc8Vn0YQamkDwuj8lPniInF3jvRsj8a", "kkDA0D3gFjqV92QIkXWNcUSN5lPTMRoijnV0St9x");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject.registerSubclass(Mensaje.class);
    }

    public Intent AutoLoginCheck(Context context)
    {
        Intent intent = null;
        if (!ParseAnonymousUtils.isLinked(getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class

            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                //intent = new Intent(context, Welcome.class);
                //startActivity(intent);
                //finish();
            }
        }
        return intent;
    }

    ParseUser tempUser;
    public ParseUser Login(String username, String password, final Context context)
    {
        //tempUser = ParseUser.logInInBackground(username, password).waitForCompletion();
        try {
            tempUser = logIn(username, password);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempUser;
    }

    public boolean SignUp (String username, String password)
    {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.signUpInBackground(new SignUpCallback() {
                                       public void done(ParseException e) {
                                           if (e == null) {
                                               String w = "";
                                           }
                                       }
                                   }
        );
        return true;
    }

}
