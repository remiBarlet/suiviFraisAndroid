package fr.cned.emdsgil.suividevosfrais.Outils;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Date;

import fr.cned.emdsgil.suividevosfrais.Modele.Profil;


public abstract class mesOutils {

    /**
     * Modification de l'affichage de la date (juste le mois et l'année, sans le jour)
     */
    public static void changeAfficheDate(DatePicker datePicker, boolean afficheJours) {
        try {
            Field f[] = datePicker.getClass().getDeclaredFields();
            for (Field field : f) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), null);
                if (daySpinnerId != 0)
                {
                    View daySpinner = datePicker.findViewById(daySpinnerId);
                    if (!afficheJours)
                    {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    /**
     * Limitation des dates affichées à la date du jour
     */
    public static void limiteDateToday(DatePicker datePicker) {
        Date now = new Date();
        datePicker.setMaxDate(now.getTime());
    }


    /**
     *
     */
    public static String keyQteToJSON ( Integer key, Integer qte) {
        return "{\"0\":\"" + key + "\",\"1\":\"" + qte + "\"}";
    }
}
