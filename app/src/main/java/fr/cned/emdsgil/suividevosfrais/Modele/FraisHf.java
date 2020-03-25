package fr.cned.emdsgil.suividevosfrais.Modele;

import java.io.Serializable;

/**
 * Classe métier contenant la description d'un frais hors forfait
 *
 */
public class FraisHf  implements Serializable {

	//propriétés du Frais hors forfait
	private final Float montant ;
	private final String motif ;
	private final Integer jour ;

	public FraisHf(Float montant, String motif, Integer jour) {
		this.montant = montant ;
		this.motif = motif ;
		this.jour = jour ;
	}

	/**
	 * Getter du montant du frais
	 * @return float : le montant
	 */
	public Float getMontant() {
		return montant;
	}

	/**
	 * Getter du motif du frais
	 * @return String : le motif
	 */
	public String getMotif() {
		return motif;
	}

	/**
	 * Getter du jour du frais
	 * @return Integer : le jour du frais
	 */
	public Integer getJour() {
		return jour;
	}

}
