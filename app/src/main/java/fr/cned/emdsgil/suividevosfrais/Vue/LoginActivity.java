package fr.cned.emdsgil.suividevosfrais.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
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
        setTitle("GSB : connexion");
        //instanciation du controleur
        controle = Controle.getInstance(this);
        cmdConnexion_clic();
    }

    /**
     * Renvoie le booléen traduisant le succès ou l'échec de la combinaison user/password
     */
    private boolean etatLog(String user, String password, String userAttempt, String passwordAttempt) {
        Boolean result;
        if (user.equals(userAttempt) && password.equals(passwordAttempt)) {
            result = true;
        } else {
            result = false;
        }
        return result;
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
                if (etatLog(controle.getUser(), controle.getPwd(), userAttempt, passwordAttempt)) {
                    allerActivityPrincipale();
                } else {
                    ((TextView) findViewById(R.id.loginFailed)).setText(R.string.login_failed);
                }
            }
        });
    }
}
