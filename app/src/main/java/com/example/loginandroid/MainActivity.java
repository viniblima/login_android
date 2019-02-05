package com.example.loginandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String login, senha;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText loginLogin = (EditText) findViewById(R.id.loginLogin);
        final EditText senhaLogin = (EditText) findViewById(R.id.senhaLogin);

        Button loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = loginLogin.getText().toString();
                senha = senhaLogin.getText().toString();

                new logar().execute();
            }
        });
    }

    public class logar extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL ("SUA URL AQUI");
                JSONObject json = new JSONObject();

                json.put("email", login);
                json.put("password", senha);

                String obj = json.toString();

                HttpURLConnection client = (HttpURLConnection)url.openConnection();
                client.setRequestMethod("POST");
                client.setDoInput(true);
                client.setDoOutput(true);

                DataOutputStream os = new DataOutputStream(client.getOutputStream());
                os.writeBytes(obj);
                os.flush();

                BufferedReader in =new BufferedReader(new InputStreamReader(client.getInputStream()));
                String resposta = in.readLine();

                JSONObject js = new JSONObject(resposta);

                js.getString("resposta");
                if(js.getString("resposta").equals("sucesso")){
                    Log.i("STATUS", "LOGADO");
                }else{
                    Toast toast = Toast.makeText(context,"Login ou senha errado", Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
