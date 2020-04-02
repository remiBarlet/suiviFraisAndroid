package fr.cned.emdsgil.suividevosfrais.Controleur;

import android.content.Context;

import java.util.Hashtable;

import fr.cned.emdsgil.suividevosfrais.Modele.FraisMois;
import fr.cned.emdsgil.suividevosfrais.Modele.Profil;
import fr.cned.emdsgil.suividevosfrais.Outils.Serializer;

public final class Controle {

    //Unique instance du controleur
    private static Controle instance;

    //login/password permettant l'accès à l'appli
    //initialisation user/pwd en attendant la connexion à la base distante

    private static Profil profil;

    // fichier contenant les informations sérialisées
    private static final String filename = "saveProfil";

    /**
     * Initialise profil avec les données sérialisées
     * @param context le contexte
     */
    private static void recupSerialize (Context context) {
        Hashtable table = (Hashtable) Serializer.deSerialize(filename, context);
        profil = new Profil(table, context);
    }

    /**
     * Constructeur
     */
    private Controle() {
        super();
    }

    /**
     * Getter de l'instance (Controle)
     * Singleton pattern: si l'instance n'est pas encore initialisée, la méthode le fait
     * @return l'instance unique
     */
    public final static Controle getInstance(Context context) {
        if (Controle.instance == null) {
            Controle.instance = new Controle();
            recupSerialize(context);
        }
        return Controle.instance;
    }

    /**
     * Creation du profil : initialise la propriété profil de l'instance
     * @param liste : le hashtable <Integer, FraisMois>
     *              qui lie la période (key annee/mois) à un FraisMois
     * @param context
     */
    public void creerProfil(Hashtable<Integer, FraisMois> liste, Context context) {
        this.profil = new Profil(liste, context);
    }

    /**
     * Getter du profil concervé par l'instance
     * @return profil : le profil unique
     */
    public Profil getProfil() {
        return profil;
    }
}
