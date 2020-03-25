package fr.cned.emdsgil.suividevosfrais.Modele;

import android.content.Context;

import java.io.Serializable;
import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.Outils.Serializer;

public class Profil implements Serializable {

    // tableau d'informations mémorisées
    private static Hashtable<Integer, FraisMois> listFraisMois = new Hashtable<>();

    //constructeur
    public Profil(Hashtable <Integer, FraisMois> liste, Context context) {
        if (liste != null) {
            this.listFraisMois = liste;
        } else {
            this.listFraisMois = new Hashtable<>();
        }
        Serializer.serialize("saveProfil", this.listFraisMois, context);
    }

    public Hashtable<Integer, FraisMois> getTable() {
        return this.listFraisMois;
    }
}
