package fr.cned.emdsgil.suividevosfrais;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker.OnDateChangedListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Locale;

public class KmActivity extends AppCompatActivity {

	// informations affichées dans l'activité
	private Integer annee ;
	private Integer mois ;
	private Integer qte ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_km);
        setTitle("GSB : Frais Km");
		// modification de l'affichage du DatePicker
		Global.changeAfficheDate((DatePicker) findViewById(R.id.datKm), false) ;
		// valorisation des propriétés
		valoriseProprietes() ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdValider_clic() ;
		cmdPlus_clic() ;
		//cmdPlusLong_clic() ;
		cmdPlus_hold() ;
		cmdMoins_clic() ;
		cmdMoins_hold() ;
		//cmdMoinsLong_clic() ;
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
	private void valoriseProprietes() {
		annee = ((DatePicker)findViewById(R.id.datKm)).getYear() ;
		mois = ((DatePicker)findViewById(R.id.datKm)).getMonth() + 1 ;
		// récupération de la qte correspondant au mois actuel
		qte = 0 ;
		Integer key = annee*100+mois ;
		if (Global.listFraisMois.containsKey(key)) {
			qte = Global.listFraisMois.get(key).getKm() ;
		}
		((EditText)findViewById(R.id.txtKm)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
	}
	
	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	findViewById(R.id.imgKmReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton valider : sérialisation
     */
    private void cmdValider_clic() {
    	findViewById(R.id.cmdKmValider).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			Serializer.serialize(Global.listFraisMois, KmActivity.this) ;
    			retourActivityPrincipale() ;    		
    		}
    	}) ;    	
    }
    
    /**
     * Sur le clic du bouton plus : ajout de 1 dans la quantité
	 * Modification : on passe de 10 à 1
     */
    private void cmdPlus_clic() {
    	findViewById(R.id.cmdKmPlus).setOnClickListener(new Button.OnClickListener() {
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
    	findViewById(R.id.cmdKmMoins).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
   				qte = Math.max(0, qte-1) ; // suppression de 10 si possible
    			enregNewQte() ;
     		}
    	}) ;    	
    }

	/**
	 * Version n°1 de la gestion du clic long sur les boutons + et -
	 * cmdPlusLong_clic()
	 * cmdMoinsLong_clic()
	 */

	/**
	 * Sur le clic long sur le bouton plus : ajout de 10 à la quantité
	 */
	/**
	private void cmdPlusLong_clic() {
		findViewById(R.id.cmdKmPlus).setOnLongClickListener(new Button.OnLongClickListener() {
			public boolean onLongClick(View v) {
				qte+=10 ;
				enregNewQte() ;
				return true;
			}
		}) ;
	}
	 */


	/**
	 * Sur le clic long sur le bouton moins : retrait de 10 à la quantité si c'est possible
	 */
	/**
	private void cmdMoinsLong_clic() {
		findViewById(R.id.cmdKmMoins).setOnLongClickListener(new Button.OnLongClickListener() {
			public boolean onLongClick(View v) {
				qte = Math.max(0, qte-10) ; // suppression de 10 si possible
				enregNewQte() ;
				return true;
			}
		}) ;
	}
	 */

	/**
	 * Version n°2 de la gestion du clic long: incremente et decremente de 10 en 10 en continu
	 */
	//handler
	private Handler handler = new Handler();
	//runnable pour incrementer
	private Runnable plusRunnable = new Runnable() {
		@Override
		public void run() {
			handler.removeCallbacks(plusRunnable);
			//vérification de la pression sur le bouton +
			if(findViewById(R.id.cmdKmPlus).isPressed()) {
				//augmentation de 10km
				qte+=10;
				enregNewQte();
				//appel de la re-verification de la pression sur cmdKmPlus après délai
				handler.postDelayed(plusRunnable, 250);
			}
		}
	};
	//runnable pour decrementer
	private Runnable moinsRunnable = new Runnable() {
		@Override
		public void run() {
			handler.removeCallbacks(moinsRunnable);
			//vérification de la pression sur le bouton +
			if(findViewById(R.id.cmdKmMoins).isPressed()) {
				//Diminution de 10km si possible
				qte = Math.max(0, qte-10);
				enregNewQte();
				//appel de la re-verification de la pression sur cmdKmMoins après délai
				handler.postDelayed(moinsRunnable, 250);
			}
		}
	};
	//appels sur clics longs
	private void cmdPlus_hold() {
		findViewById(R.id.cmdKmPlus).setOnLongClickListener(new Button.OnLongClickListener() {
			public boolean onLongClick(View v) {
				handler.postDelayed(plusRunnable, 0);
				return true;
			}
		});
	}
	private void cmdMoins_hold() {
		findViewById(R.id.cmdKmMoins).setOnLongClickListener(new Button.OnLongClickListener() {
			public boolean onLongClick(View v) {
				handler.postDelayed(moinsRunnable, 0);
				return true;
			}
		});
	}




    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker) findViewById(R.id.datKm);
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				valoriseProprietes() ;				
			}
    	});       	
    }

	/**
	 * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, à la date choisie
	 */
	private void enregNewQte() {
		// enregistrement dans la zone de texte
		((EditText)findViewById(R.id.txtKm)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
		// enregistrement dans la liste
		Integer key = annee*100+mois ;
		if (!Global.listFraisMois.containsKey(key)) {
			// creation du mois et de l'annee s'ils n'existent pas déjà
			Global.listFraisMois.put(key, new FraisMois(annee, mois)) ;
		}
		Global.listFraisMois.get(key).setKm(qte) ;		
	}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(KmActivity.this, MainActivity.class) ;
		startActivity(intent) ;   					
	}
}
