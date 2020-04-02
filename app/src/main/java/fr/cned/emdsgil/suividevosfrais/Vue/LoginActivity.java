package fr.cned.emdsgil.suividevosfrais.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Outils.AccesServeur;
import fr.cned.emdsgil.suividevosfrais.R;

public class LoginActivity extends AppCompatActivity {

    //controleur
    private Controle controle;

    //variables stockant la tentative de username/password
    private String userAttempt;
    private String passwordAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setTitle("GSB : connexion");
        //instanciation du controleur
        controle = Controle.getInstance(this);
        cmdConnexion_clic();
    }

    /**
     * Renvoie le booléen traduisant le succès ou l'échec de la combinaison user/password
     */
    private boolean etatLog(String userLogin, String password, String userAttempt, String passwordAttempt) {
        Boolean result;
        if (userLogin.equals(userAttempt) && password.equals(passwordAttempt) && !userLogin.isEmpty()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Traitement du message reçu du serveur pour authentification
     * Initialisation des propriétés du profil (instance unique) userId, userLogin et pwd
     * à partir des infos tirées du message
     * @param contenu(string) du message reçu du serveur
     */
    private void authAction(String contenu) {

    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void allerActivityPrincipale() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
        startActivity(intent) ;
    }

    /**
     * La validation des champs user/password ouvre l'application
     */
    private void cmdConnexion_clic() {
        findViewById(R.id.login).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                userAttempt = ((EditText) findViewById(R.id.username)).getText().toString();
                passwordAttempt = ((EditText) findViewById(R.id.password)).getText().toString();
                //transformation du message en JSON
                String message = "{\"0\":\"" + userAttempt + "\",\"1\":\"" + passwordAttempt + "\"}";
                //tentative de connexion
                AccesServeur acces = new AccesServeur();
                String[]retourServeur = acces.run("authentification", "", message);
                if (retourServeur.length > 1 && retourServeur[0].equals("authentification")) {
                    //ttt du message
                    try {
                        JSONObject infosVisiteur = new JSONObject(retourServeur[2]);
                        controle.getProfil().setUserId(infosVisiteur.getString("id"));
                        controle.getProfil().setUserLogin(infosVisiteur.getString("login"));
                        controle.getProfil().setPwd(infosVisiteur.getString("mdp"));
                    } catch (
                            JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (etatLog(controle.getProfil().getUserLogin(), controle.getProfil().getPwd(), userAttempt, passwordAttempt)) {
                    allerActivityPrincipale();
                } else {
                    ((TextView) findViewById(R.id.loginFailed)).setText(R.string.login_failed);
                }
            }
        });
    }
}
