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

import java.util.Hashtable;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Modele.FraisMois;
import fr.cned.emdsgil.suividevosfrais.Modele.Global;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.Outils.Serializer;

public class NuitActivity extends AppCompatActivity {

    private Controle controle;

    //informations affichées dans l'activité
    private Integer annee ;
    private Integer mois ;
    private Integer qte ;

    private void recupProfil() {
        annee = ((DatePicker)findViewById(R.id.datNuitee)).getYear() ;
        mois = ((DatePicker)findViewById(R.id.datNuitee)).getMonth() + 1 ;
        // récupération de la qte correspondant au mois actuel
        qte = 0 ;
        Integer key = annee*100+mois ;
        if (controle.getProfil() != null) {
            if (controle.getProfil().getTable().containsKey(key)) {
                qte = controle.getProfil().getTable().get(key).getNuitee() ;
            }
        } else {
            controle.creerProfil(new Hashtable<Integer, FraisMois>(), this);
        }

        ((EditText)findViewById(R.id.txtNuitee)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
    }

    private void init() {
        controle = Controle.getInstance(this);
        recupProfil();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuit);
        setTitle("GSB: Frais de nuitées");
        // modification de l'affichage du DatePicker
        Global.changeAfficheDate((DatePicker) findViewById(R.id.datNuitee), false) ;
        // valorisation des propriétés
        //valoriseProprietes() ;
        init();
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
     * Valorisation des propriétés avec les informations affichées
     */
    /**private void valoriseProprietes() {
        annee = ((DatePicker)findViewById(R.id.datNuitee)).getYear() ;
        mois = ((DatePicker)findViewById(R.id.datNuitee)).getMonth() + 1 ;
        // récupération de la qte correspondant au mois actuel
        qte = 0 ;
        Integer key = annee*100+mois ;
        if (Global.listFraisMois.containsKey(key)) {
            qte = Global.listFraisMois.get(key).getNuitee() ;
        }
        ((EditText)findViewById(R.id.txtNuitee)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
    }*/

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        findViewById(R.id.imgNuitee).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton valider : sérialisation
     */
    private void cmdValider_clic() {
        findViewById(R.id.cmdNuiteeValider).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Serializer.serialize("saveProfil", controle.getProfil().getTable(), NuitActivity.this) ;
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton plus : ajout de 1 dans la quantité
     * Modification : on passe de 10 à 1
     */
    private void cmdPlus_clic() {
        findViewById(R.id.cmdNuiteePlus).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte+=1 ;
                enregNewQte() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton moins : enlève 1 dans la quantité si c'est possible
     * Modification : on passe de 10 à 1
     */
    private void cmdMoins_clic() {
        findViewById(R.id.cmdNuiteeMoins).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte = Math.max(0, qte-1) ; // suppression de 10 si possible
                enregNewQte() ;
            }
        }) ;
    }

    /**
     * Sur le clic long sur le bouton plus : ajout de 10 à la quantité
     */
    private void cmdPlusLong_clic() {
        findViewById(R.id.cmdNuiteePlus).setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                qte += 10;
                enregNewQte();
                return true;
            }
        });
    }


    /**
     * Sur le clic long sur le bouton moins : retrait de 10 à la quantité si c'est possible
     */
    private void cmdMoinsLong_clic() {
        findViewById(R.id.cmdNuiteeMoins).setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                qte = Math.max(0, qte - 10); // suppression de 10 si possible
                enregNewQte();
                return true;
            }
        });
    }

    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {
        final DatePicker uneDate = (DatePicker) findViewById(R.id.datNuitee);
        uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //valoriseProprietes() ;
                recupProfil();
            }
        });
    }

    /**
     * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, à la date choisie
     */
    private void enregNewQte() {
        // enregistrement dans la zone de texte
        ((EditText)findViewById(R.id.txtNuitee)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
        // enregistrement dans la liste
        Integer key = annee*100+mois ;
        if (!controle.getProfil().getTable().containsKey(key)) {
            // creation du mois et de l'annee s'ils n'existent pas déjà
            controle.getProfil().getTable().put(key, new FraisMois(annee, mois)) ;
        }
        controle.getProfil().getTable().get(key).setNuitee(qte) ;
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(NuitActivity.this, MainActivity.class) ;
        startActivity(intent) ;
    }
}


