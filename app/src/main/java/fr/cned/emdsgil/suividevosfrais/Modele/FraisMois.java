package fr.cned.emdsgil.suividevosfrais.Modele;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe métier contenant les informations des frais d'un mois
 */
public class FraisMois implements Serializable {

    private Integer mois; // mois concerné
    private Integer annee; // année concernée
    private Integer etape; // nombre d'étapes du mois
    private Integer km; // nombre de km du mois
    private Integer nuitee; // nombre de nuitées du mois
    private Integer repas; // nombre de repas du mois
    private final ArrayList<FraisHf> lesFraisHf; // liste des frais hors forfait du mois

    public FraisMois(Integer annee, Integer mois) {
        this.annee = annee;
        this.mois = mois;
        this.etape = 0;
        this.km = 0;
        this.nuitee = 0;
        this.repas = 0;
        lesFraisHf = new ArrayList<>();
    }

    /**
     * Ajout d'un frais hors forfait
     *
     * @param montant Montant en euros du frais hors forfait
     * @param motif Justification du frais hors forfait
     */
    public void addFraisHf(Integer idUnique, Float montant, String motif, String date) {
        lesFraisHf.add(new FraisHf(idUnique, montant, motif, date));
    }

    /**
     * Suppression d'un frais hors forfait
     * @param index Indice du frais hors forfait à supprimer
     */
    public void supprFraisHf(int index) {
        lesFraisHf.remove(index);
    }

    /**
     * Getter de la propriété 'mois'
     * @return Integer : le mois concerné
     */
    public Integer getMois() {
        return mois;
    }

    /**
     * Setter de la propriété 'mois'
     * @param mois : la valeur à attribuer à la propriété 'mois'
     */
    public void setMois(Integer mois) {
        this.mois = mois;
    }

    /**
     * Getter de la propriété 'année'
     * @return Integer : l'année concernée
     */
    public Integer getAnnee() {
        return annee;
    }

    /**
     * Setter de la propriété 'annee'
     * @param annee : la valeur à attribuer à la propriété 'annee'
     */
    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    /**
     * Getter de la propriété 'étape'
     * @return Integer : l'étape concernée
     */
    public Integer getEtape() {
        return etape;
    }

    /**
     * Setter de la propriété 'etape'
     * @param etape : la valeur à attribuer à la propriété 'etape'
     */
    public void setEtape(Integer etape) {
        this.etape = etape;
    }

    /**
     * Getter de la propriété 'km'
     * @return Integer : le kilométrage concerné
     */
    public Integer getKm() {
        return km;
    }

    /**
     * Setter de la propriété 'km'
     * @param km : la valeur à attribuer à la propriété 'km'
     */
    public void setKm(Integer km) {
        this.km = km;
    }

    /**
     * Getter de la propriété 'nuitee'
     * @return Integer : le nombre de nuitées
     */
    public Integer getNuitee() {
        return nuitee;
    }

    /**
     * Setter de la propriété 'nuitee'
     * @param nuitee : la valeur à attribuer à la propriété 'nuitee'
     */
    public void setNuitee(Integer nuitee) {
        this.nuitee = nuitee;
    }

    /**
     * Getter de la propriété 'repas'
     * @return Integer : le nombre de repas
     */
    public Integer getRepas() {
        return repas;
    }

    /**
     * Setter de la propriété 'repas'
     * @param repas : la valeur à attribuer à la propriété 'repas'
     */
    public void setRepas(Integer repas) {
        this.repas = repas;
    }

    /**
     * Getter de l'arrayList des frais hors forfait
     * @return ArrayList : la liste des frais hors forfait, chacun sous forme de tableau
     */
    public ArrayList<FraisHf> getLesFraisHf() {
        return lesFraisHf;
    }

    /**
     * Getter d'un frais hors forfait en particulier
     * @param index : l'index (int) du frais dans l'arrayList des frais hors forfait
     * @return le tableau du frais hors forfait
     */
    public FraisHf getLeFraisHf(int index) { return lesFraisHf.get(index); }

}
