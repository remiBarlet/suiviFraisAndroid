package fr.cned.emdsgil.suividevosfrais.Outils;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AccesServeur {

    /**
     * Constante contenant l'adresse du serveur permettant l'accès à la BDD
     */
    private static final String  SERVERADDR = "http://51.91.77.130/GSB/accesBDDAndroid.php";
    private Controle controle;

    /**
     * Envoi d'un message composé du type d'opération et du message proprement dit
     * Traitement de la réponse (X finalités: authentification,
     * @param operationType
     * @param message
     */
    public String[] run(String operationType, String id, String message) {

        controle = Controle.getInstance(null);

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("operation", operationType)
                .add("id", id)
                .add("message", message)
                .build();
        Request request = new Request.Builder()
                .url(SERVERADDR)
                .post(formBody)
                .build();
        String[] contenu;
        try (Response response = client.newCall(request).execute()) {
            String output = response.body().string();
            Log.d("DEBUG ********** Envoi", operationType+"%"+id+"%"+message);
            Log.d("DEBUG STRING RECEPTION ***************** ", output);
            // traitement de la chaine de caractères reçue pour utilisation
            contenu = output.split("%");
        } catch (IOException e) {
            e.printStackTrace();
            contenu = null;
        }
        return contenu;
    }
}
