package com.example.freya.loginfoodos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeLogin extends AppCompatActivity {
public static String home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);
     

    final EditText etUsername = (EditText) findViewById(R.id.etUsername);
    final EditText etPassword = (EditText) findViewById(R.id.etPassword);
    final Button tvRegisterLink = (Button) findViewById(R.id.tvRegisterLink);
    final Button bLogin = (Button) findViewById(R.id.bSignIn);

    tvRegisterLink.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog alertbox = new AlertDialog.Builder(HomeLogin.this)
                    .setMessage("Sorry this is a premium Content.For enabling this feature contact foodos team at foodos@gmail.com")

                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .show();


            // Intent registerIntent = new Intent(HomeLogin.this, HomeRegister.class);
            //HomeLogin.this.startActivity(registerIntent);
        }
    });

    bLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String username = etUsername.getText().toString();
            home=username;
            final String password = etPassword.getText().toString();
            final ProgressDialog dialog=new ProgressDialog(HomeLogin.this);

            Boolean valid=true;

            if (username.isEmpty() ) {
                etUsername.setError("Username is Empty");
                valid = false;
            } else {
                etUsername.setError(null);
            }
            if (password.isEmpty()) {
                etPassword.setError("Password is Empty");
                valid = false;
            }
            if(valid) {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                Intent intent = new Intent(HomeLogin.this, DummyActivity.class);
                                //Create the bundle
                                Bundle bundle = new Bundle();
                                //Add your data from getFactualResults method to bundle
                                bundle.putString("uname", username);
                                intent.putExtras(bundle);
                                //Add the bundle to the intent
                                //intent.putExtra("username",username);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeLogin.this);
                                builder.setMessage("Login Failed.Please Check your Username or Password")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                HomeLoginRequest loginRequest = new HomeLoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HomeLogin.this);
                queue.add(loginRequest);
            }
        }
    });
}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to go to start?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
Intent i=new Intent(HomeLogin.this,MainActivity.class);
                        startActivity(i);
                        //finish();
                        //getActivity().finish();
                        //Intent intent = new Intent(Intent.ACTION_MAIN);
                        //intent.addCategory(Intent.CATEGORY_HOME);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

}
