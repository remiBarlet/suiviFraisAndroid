package fr.cned.emdsgil.suividevosfrais.Vue;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Modele.FraisMois;
import fr.cned.emdsgil.suividevosfrais.Outils.mesOutils;
import fr.cned.emdsgil.suividevosfrais.R;
import fr.cned.emdsgil.suividevosfrais.Outils.Serializer;

public class HfActivity extends AppCompatActivity {

	//instance de la classe Controle qui permet d'accéder au controleur
	private Controle controle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf);
        setTitle("GSB : Frais HF");
        // modification de l'affichage du DatePicker
        mesOutils.changeAfficheDate((DatePicker) findViewById(R.id.datHf), true) ;
		// mise à 0 du montant
		controle = Controle.getInstance(this);
		((EditText)findViewById(R.id.txtHf)).setText("0") ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdAjouter_clic() ;
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
    	findViewById(R.id.imgHfReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton ajouter : enregistrement dans la liste et sérialisation
     */
    private void cmdAjouter_clic() {
    	findViewById(R.id.cmdHfAjouter).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			enregListe() ;
    			Serializer.serialize("saveProfil", controle.getProfil().getTable(), HfActivity.this) ;
    			retourActivityPrincipale() ;    		
    		}
    	}) ;    	
    }
    
	/**
	 * Enregistrement dans la liste du nouveau frais hors forfait
	 */
	private void enregListe() {
		// récupération des informations saisies
		Integer annee = ((DatePicker)findViewById(R.id.datHf)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHf)).getMonth() + 1 ;
		Integer jour = ((DatePicker)findViewById(R.id.datHf)).getDayOfMonth() ;
		Float montant = Float.valueOf((((EditText)findViewById(R.id.txtHf)).getText().toString()));
		String motif = ((EditText)findViewById(R.id.txtHfMotif)).getText().toString() ;
		// enregistrement dans la liste
		Integer key = annee*100+mois ;
		if (!controle.getProfil().getTable().containsKey(key)) {
			// creation du mois et de l'annee s'ils n'existent pas déjà
			controle.getProfil().getTable().put(key, new FraisMois(annee, mois)) ;
		}
		controle.getProfil().getTable().get(key).addFraisHf(montant, motif, jour) ;
	}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfActivity.this, MainActivity.class) ;
		startActivity(intent) ;   					
	}
}