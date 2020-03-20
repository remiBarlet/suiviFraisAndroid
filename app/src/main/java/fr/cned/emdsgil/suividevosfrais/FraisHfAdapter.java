package fr.cned.emdsgil.suividevosfrais;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

class FraisHfAdapter extends BaseAdapter {

	private final ArrayList<FraisHf> lesFrais ; // liste des frais du mois
	private final LayoutInflater inflater ;
	private final Context context; // contexte permettant de retrouver les éléments HfRecapActivity

    /**
	 * Constructeur de l'adapter pour valoriser les propriétés
     * @param context Accès au contexte de l'application
     * @param lesFrais Liste des frais hors forfait
     */
	public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais) {
		inflater = LayoutInflater.from(context) ;
		this.lesFrais = lesFrais ;
		this.context = context ;
    }
	
	/**
	 * retourne le nombre d'éléments de la listview
	 */
	@Override
	public int getCount() {
		return lesFrais.size() ;
	}

	/**
	 * retourne l'item de la listview à un index précis
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index) ;
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
		ImageButton cmdSuppHf ;
	}
	
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		//declaration de la structure contenant les éléments d'une ligne (classe privée)
		ViewHolder holder ;
		//declaration de la position en 'final'
		final Integer pos = index;
		//cas ou la vue entrée en paramètre (c.a.d la vue générique de la ligne) n'existe pas encore
		if (convertView == null) {
			holder = new ViewHolder() ;
			// la ligne est construite à partir de la structure de layout_liste
			convertView = inflater.inflate(R.layout.layout_liste, parent, false) ;
			// chaque propriété de holder, correspondant aux objets graphiques est liée à l'un d'eux
			holder.txtListJour = convertView.findViewById(R.id.txtListJour);
			holder.txtListMontant = convertView.findViewById(R.id.txtListMontant);
			holder.txtListMotif = convertView.findViewById(R.id.txtListMotif);
			holder.cmdSuppHf = convertView.findViewById(R.id.cmdSuppHf) ;
			// affecte le holder comme tag de la ligne
			convertView.setTag(holder) ;
		}else{
		//cas où la ligne existe déjà: le holder est récupéré dans le tag
			holder = (ViewHolder)convertView.getTag();
		}
		// valorisation des propriétés de holder avec le frais en position 'index' dans lesFrais
		holder.txtListJour.setText(String.format(Locale.FRANCE, "%d", lesFrais.get(index).getJour()));
		holder.txtListMontant.setText(String.format(Locale.FRANCE, "%.2f", lesFrais.get(index).getMontant())) ;
		holder.txtListMotif.setText(lesFrais.get(index).getMotif()) ;
		// mémorisation de l'indice de ligne en étiquette de cmdSuppHf pour ensuite récupérer cet indice dans l'événement
		holder.cmdSuppHf.setTag(index) ;
		// gestion de l'événement clic sur le bouton de suppression
		holder.cmdSuppHf.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// code a exécuter
				// récupération des informations saisies
				Integer annee = ((DatePicker) ((Activity)context).findViewById(R.id.datHfRecap)).getYear() ;
				Integer mois = ((DatePicker) ((Activity)context).findViewById(R.id.datHfRecap)).getMonth() + 1 ;
				Integer key = annee*100+mois;
				//suppression + mémorisation
				Global.listFraisMois.get(key).supprFraisHf(pos);
				Serializer.serialize(Global.listFraisMois, context);
				// rafraichit la liste visuelle
				notifyDataSetChanged() ;
			}
		}) ;
		return convertView ;
	}
	
}
