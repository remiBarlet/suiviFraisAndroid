package fr.cned.emdsgil.suividevosfrais.Vue;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Modele.FraisHf;
import fr.cned.emdsgil.suividevosfrais.Outils.AccesServeur;
import fr.cned.emdsgil.suividevosfrais.Outils.mesOutils;
import fr.cned.emdsgil.suividevosfrais.R;

public class HfRecapActivity extends AppCompatActivity {

	//instance de la classe Controle qui permet d'accéder au controleur
	private Controle controle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf_recap);
        setTitle("GSB : Hors Forfait mensuel");
		this.controle = Controle.getInstance(null);
		// modification de l'affichage du DatePicker
		mesOutils.changeAfficheDate((DatePicker) findViewById(R.id.datHfRecap), false) ;
		Intent thisIntent = getIntent();
		int ijour = 1;
		int imois = thisIntent.getIntExtra("mois", 16) - 1;
		int iannee = thisIntent.getIntExtra("annee", 0);
		if (imois != 15) {
			((DatePicker) findViewById(R.id.datHfRecap)).init(iannee, imois, ijour, null);
		}
		mesOutils.limiteDateToday((DatePicker) findViewById(R.id.datHfRecap));

		// valorisation des propriétés
		afficheListe() ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
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
	 * Affiche la liste des frais hors forfaits de la date sélectionnée
	 */
	private void afficheListe() {
		Integer annee = ((DatePicker)findViewById(R.id.datHfRecap)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHfRecap)).getMonth() + 1 ;
		// récupération des frais HF pour cette date
		Integer key = annee*100 + mois ;
		ArrayList<FraisHf> liste;
		AccesServeur acces = new AccesServeur();
		String message = "{\"0\":\"" + key + "\"}";
		String[] contenu = acces.run("recupListeHf", controle.getProfil().getUserId(), message);
		Gson gson = new Gson();
		List<Map> listeRecu=gson.fromJson(contenu[1],new TypeToken<List<Map>>(){}.getType());
		liste = new ArrayList<>();
		for (Map map: listeRecu) {
			Integer idUnique = Integer.valueOf(map.get("id").toString());
			Float montant = Float.parseFloat(map.get("montant").toString());
			String motif = map.get("libelle").toString();
			String date = map.get("date").toString().substring(8)+"/"+ mois;
			Log.d("substring", date);
			liste.add(new FraisHf(idUnique, montant, motif, date));
		}
		Collections.sort(liste, new Comparator<FraisHf>() {
			@Override
			public int compare(FraisHf o1, FraisHf o2) {
				return Integer.parseInt(o1.getDate().substring(0, 2)) - Integer.parseInt(o2.getDate().substring(0, 2));
			}
		});
		//enregistrement de cette liste dans le profil

		ListView listView = (ListView) findViewById(R.id.lstHfRecap);
		//utilisation de la liste du profil pour créer l'adapter à l'origine de la listView
		FraisHfAdapter adapter = new FraisHfAdapter(HfRecapActivity.this, liste) ;
		listView.setAdapter(adapter) ;
	}
	
	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	findViewById(R.id.imgHfRecapReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker) findViewById(R.id.datHfRecap);
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				afficheListe() ;				
			}
    	});       	
    }
    
    

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfRecapActivity.this, MainActivity.class) ;
		startActivity(intent) ;   					
	}
}
