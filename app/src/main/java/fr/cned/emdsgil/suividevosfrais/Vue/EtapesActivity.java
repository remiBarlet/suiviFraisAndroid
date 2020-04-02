package fr.cned.emdsgil.suividevosfrais.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Modele.FraisMois;
import fr.cned.emdsgil.suividevosfrais.Outils.AccesServeur;
import fr.cned.emdsgil.suividevosfrais.Outils.mesOutils;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.Outils.Serializer;

public class EtapesActivity extends AppCompatActivity {

    //instance de la classe Controle qui permet d'accéder au controleur
    private Controle controle;

    //informations affichées dans l'activité
    private Integer annee ;
    private Integer mois ;
    private Integer qte ;

    /**
     * Récupération des variables du profil de l'instance unique du controle pour affichage
     */
    private void recupProfil() {
        annee = ((DatePicker)findViewById(R.id.datEtape)).getYear() ;
        mois = ((DatePicker)findViewById(R.id.datEtape)).getMonth() + 1 ;
        // récupération de la qte correspondant au mois actuel
        qte = 0 ;
        Integer key = annee*100+mois ;
        AccesServeur acces = new AccesServeur();
        String[]retourServeur = acces.run("recupEtape", controle.getProfil().getUserId(), ""+key);
        try {
            //Si la BDD n'a pas de resultat pour les kilometrages pour le mois passé en paramètre,
            //le serveur renvoie false pour retourServeur[1], qte reste à 0
            JSONObject infosQte = new JSONObject(retourServeur[1]);
            qte = Integer.valueOf(infosQte.getString("quantite"));
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        ((EditText)findViewById(R.id.txtEtape)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
    }

    /**
     * Initialisation du controleur
     */
    private void init() {
        controle = Controle.getInstance(this);
        recupProfil();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapes);
        setTitle("GSB: Frais d'étapes");
        // modification de l'affichage du DatePicker
        mesOutils.changeAfficheDate((DatePicker) findViewById(R.id.datEtape), false) ;
        mesOutils.limiteDateToday((DatePicker) findViewById(R.id.datEtape));
        // valorisation des propriétés
        init() ;
        // chargement des méthodes événementielles
        imgReturn_clic() ;
        cmdValider_clic() ;
        cmdPlus_clic() ;
        cmdPlusLong_clic() ;
        cmdMoins_clic() ;
        cmdMoinsLong_clic() ;
        dat_clic() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.retour_accueil))) {
            retourActivityPrincipale() ;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        findViewById(R.id.imgEtapes).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton valider : sérialisation
     */
    private void cmdValider_clic() {
        findViewById(R.id.cmdEtapeValider).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                enregNewQte();
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton plus : ajout de 1 dans la quantité
     * Modification : on passe de 10 à 1
     */
    private void cmdPlus_clic() {
        findViewById(R.id.cmdEtapePlus).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte+=1 ;
                afficheNewQte();
            }
        }) ;
    }

    /**
     * Sur le clic du bouton moins : enlève 1 dans la quantité si c'est possible
     * Modification : on passe de 10 à 1
     */
    private void cmdMoins_clic() {
        findViewById(R.id.cmdEtapeMoins).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte = Math.max(0, qte-1) ; // suppression de 10 si possible
                afficheNewQte();
            }
        }) ;
    }

    /**
     * Sur le clic long sur le bouton plus : ajout de 10 à la quantité
     */
    private void cmdPlusLong_clic() {
        findViewById(R.id.cmdEtapePlus).setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                qte += 10;
                afficheNewQte();
                return true;
            }
        });
    }


    /**
     * Sur le clic long sur le bouton moins : retrait de 10 à la quantité si c'est possible
     */
    private void cmdMoinsLong_clic() {
        findViewById(R.id.cmdEtapeMoins).setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                qte = Math.max(0, qte - 10); // suppression de 10 si possible
                afficheNewQte();
                return true;
            }
        });
    }

    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {
        final DatePicker uneDate = (DatePicker) findViewById(R.id.datEtape);
        uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //valoriseProprietes() ;
                recupProfil() ;
            }
        });
    }

    /**
     * Enregistrement dans la zone de texte de la nouvelle quantité
     */
    private void afficheNewQte() {
        // enregistrement dans la zone de texte
        ((EditText)findViewById(R.id.txtEtape)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
    }

    /**
     * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, à la date choisie
     */
    private void enregNewQte() {
        // enregistrement dans la liste
        Integer key = annee*100+mois ;
        AccesServeur acces = new AccesServeur();
        //transformation du message en JSON
        String message = mesOutils.keyQteToJSON(key, qte);
        acces.run("setEtape", controle.getProfil().getUserId(), message);
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(EtapesActivity.this, MainActivity.class) ;
        startActivity(intent) ;
    }
}
