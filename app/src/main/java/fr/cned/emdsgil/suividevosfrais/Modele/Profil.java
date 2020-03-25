package fr.cned.emdsgil.suividevosfrais.Modele;

import android.content.Context;

import java.io.Serializable;
import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.Outils.Serializer;

public class Profil implements Serializable {

    /**
     * tableau d'informations mémorisées liant une clé annee/mois à un FraisMois,
     */
    private static Hashtable<Integer, FraisMois> listFraisMois = new Hashtable<>();

    /**
    * Constructeur
    */
    public Profil(Hashtable <Integer, FraisMois> liste, Context context) {
        if (liste != null) {
            this.listFraisMois = liste;
        } else {
            this.listFraisMois = new Hashtable<>();
        }
        Serializer.serialize("saveProfil", this.listFraisMois, context);
    }

    /**
     * Getter du tableau d'informations
     * @return hashtable<Integer, FraisMois> : le tableau des frais
     */
    public Hashtable<Integer, FraisMois> getTable() {
        return this.listFraisMois;
    }
}
