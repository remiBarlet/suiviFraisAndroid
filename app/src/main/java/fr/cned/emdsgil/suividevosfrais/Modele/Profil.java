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
    private static String userLogin = "";
    private static String pwd = "";
    private static String userId = "";

    /**
    * Constructeur
    */
    public Profil(Hashtable <Integer, FraisMois> liste, Context context) {
        if (liste != null) {
            this.listFraisMois = liste;
        } else {
            this.listFraisMois = new Hashtable<>();
        }
        //Serializer.serialize("saveProfil", this.listFraisMois, context);
    }

    /**
     * Getter du login de l'utilisateur
     * @return String : userLogin
     */
    public String getUserLogin() {return userLogin; }

    /**
     * Setter du login de l'utilisateur
     * @param userLogin
     */
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }

    /**
     * Getter du mot de passe
     * @return String : pwd
     */
    public String getPwd() { return pwd; }

    /**
     * Setter du mot de passe
     * @param pwd
     */
    public void setPwd(String pwd) { this.pwd = pwd; }

    /**
     * Getter de l'ID de l'utilisateur
     * @return String : userId
     */
    public String getUserId() {return userId; }

    /**
     * Setter de l'ID de l'utilisateur
     * @param userId
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * Getter du tableau d'informations
     * @return hashtable<Integer, FraisMois> : le tableau des frais
     */
    public Hashtable<Integer, FraisMois> getTable() {
        return this.listFraisMois;
    }
}
