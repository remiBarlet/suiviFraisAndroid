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
	private final String date ;
	private final Integer idUnique;

	/**
	 * Constructeur
	 * @param montant
	 * @param motif
	 * @param date
	 */
	public FraisHf(Integer idUnique, Float montant, String motif, String date) {
		this.idUnique = idUnique;
		this.montant = montant ;
		this.motif = motif ;
		this.date = date ;
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
	public String getDate() {
		return date;
	}

	public Integer getIdUnique() { return idUnique; }
}
