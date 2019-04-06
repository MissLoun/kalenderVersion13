package de.projects.janap.a05_kalender;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Kalender_Adapter extends ArrayAdapter<Calendar> {

    //Darstellung eines Monats

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private List<Termin> terminListe;
    private Boolean nichtNochmal;

    private final String logTag = "LogTag_KalenderAdapter";    //Tag um Fehler im Logcat zu finden

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Kalender_Adapter(Context pContext, ArrayList<Calendar> pTage, List<Termin> pTerminListe) {
        super(pContext, R.layout.zelle_aktueller_monat, pTage);
        inflater = LayoutInflater.from(pContext);
        terminListe = pTerminListe;
        nichtNochmal = false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    private void termineAnzeigen(TextView pTerminAnzeige1, TextView pTerminAnzeige2, TextView pTerminAnzeige3, TextView pTerminAnzeige4,
                                 int pFarbe) {
        //Anzeige von bis zu 4 Terminen mit Farbe in der Monatsübersicht

        if (pTerminAnzeige1.getVisibility() != View.VISIBLE) {
            // bisher kein Termin an diesem Tag
            pTerminAnzeige1.setVisibility(View.VISIBLE);
            pTerminAnzeige1.setBackgroundColor(pFarbe); //bei einem Termin soll es eingefärbt werden

        } else if (pTerminAnzeige1.getVisibility() == View.VISIBLE
                && pTerminAnzeige2.getVisibility() != View.VISIBLE) {
            // bisher ein Termin an diesem Tag
            pTerminAnzeige2.setVisibility(View.VISIBLE);
            pTerminAnzeige2.setBackgroundColor(pFarbe); //bei einem Termin soll es eingefärbt werden

        } else if (pTerminAnzeige1.getVisibility() == View.VISIBLE
                && pTerminAnzeige2.getVisibility() == View.VISIBLE
                && pTerminAnzeige3.getVisibility() != View.VISIBLE) {
            // bisher zwei Termine an diesem Tag
            pTerminAnzeige3.setVisibility(View.VISIBLE);
            pTerminAnzeige3.setBackgroundColor(pFarbe); //bei einem Termin soll es eingefärbt werden

        } else if (pTerminAnzeige1.getVisibility() == View.VISIBLE
                && pTerminAnzeige2.getVisibility() == View.VISIBLE
                && pTerminAnzeige3.getVisibility() == View.VISIBLE
                && pTerminAnzeige4.getVisibility() != View.VISIBLE) {
            // bisher drei Termine an diesem Tag
            pTerminAnzeige4.setVisibility(View.VISIBLE);
            pTerminAnzeige4.setBackgroundColor(pFarbe); //bei einem Termin soll es eingefärbt werden
        }
    }

    private void ueberpruefenAufTermin(TextView pTerminAnzeige1, TextView pTerminAnzeige2, TextView pTerminAnzeige3, TextView pTerminAnzeige4,
                                       int pPosition, Calendar pTag) {
        //Überprüfung ob es Termine an diesem Tag gibt
        Termin termin;

        for (int i = 0; i <= terminListe.size(); i++) {
            termin = terminListe.get(i);
            int farbe = termin.getFarbe();

            //Termin Start und Ende in einem Monat
            if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                    && (pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)) {

                termineAnzeigen(pTerminAnzeige1, pTerminAnzeige2, pTerminAnzeige3, pTerminAnzeige4, farbe);

            }
            //Termin Start in anderem Monat als das Ende
            else if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                    && (pTag.get(Calendar.MONTH) != termin.getEnde().get(Calendar.MONTH))) {

                termineAnzeigen(pTerminAnzeige1, pTerminAnzeige2, pTerminAnzeige3, pTerminAnzeige4, farbe);

            }
            //Termin Ende in anderem Monat als der Start
            else if ((pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)
                    && (pTag.get(Calendar.MONTH) != termin.getStart().get(Calendar.MONTH))) {

                termineAnzeigen(pTerminAnzeige1, pTerminAnzeige2, pTerminAnzeige3, pTerminAnzeige4, farbe);
            }

            //Termin Start und Ende in anderem Monat  -> Termin geht jeden Tag in diesem Monat
            else if ((pTag.get(Calendar.MONTH) > termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.MONTH) < termin.getEnde().get(Calendar.MONTH))
                    && (pTag.get(Calendar.YEAR) >= termin.getStart().get(Calendar.YEAR) && pTag.get(Calendar.YEAR) <= termin.getEnde().get(Calendar.YEAR))) {

                termineAnzeigen(pTerminAnzeige1, pTerminAnzeige2, pTerminAnzeige3, pTerminAnzeige4, farbe);
            }

            Log.d(logTag, "Fertig ueberpreuft" + pPosition + " - " + pTag.get(Calendar.DAY_OF_MONTH) + " - ");  //Meldung wenn der Tag überprüft wurde
        }
    }

    private Boolean ueberpruefeObTagHeuteIst(Calendar pTag) {
        //Überprüfung ob es sich um den heutigen Tag handelt
        Calendar heute = Calendar.getInstance();
        return (pTag.get(Calendar.DAY_OF_MONTH) == heute.get(Calendar.DAY_OF_MONTH))
                && (pTag.get(Calendar.MONTH) == heute.get(Calendar.MONTH))
                && (pTag.get(Calendar.YEAR) == heute.get(Calendar.YEAR));
    }

    /*-------------------------Override Methoden--------------------------------------------------*/
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            Calendar tag = getItem(position);    //Tag aus dem Array der diesen Monat mit den jeweiligen Tagen beschreibt

            //wenn convertView = 0 ist, wird eine neue Zelle festgelegt
            if (view == null) {
                view = inflater.inflate(R.layout.zelle_aktueller_monat, parent, false);
            }

            TextView terminAnzeige1 = view.findViewById(R.id.txt_Termin1);
            TextView terminAnzeige2 = view.findViewById(R.id.txt_Termin2);
            TextView terminAnzeige3 = view.findViewById(R.id.txt_Termin3);
            TextView terminAnzeige4 = view.findViewById(R.id.txt_Termin4);

            TextView textView = view.findViewById(R.id.textview_tag);

            //Das TextView bekommt die Zahl des jeweiligen Tages (tag weiß den Tag des Monats,
            // durch Festlegung in der KalenderSteuerung) zugeteilt
            textView.setText(String.valueOf(tag.get(Calendar.DAY_OF_MONTH)));

            Boolean heute = ueberpruefeObTagHeuteIst(tag);
            if (heute) {
                textView.setTextColor(Color.parseColor("#FFA726"));  //beim heutigen Tag soll die Zahl eingefärbt werden
            }

            ueberpruefenAufTermin(terminAnzeige1, terminAnzeige2, terminAnzeige3, terminAnzeige4, position, tag);

            return view;    //Gibt die Zelle (den Tag) aus der Monatsübersicht zurück -> zeigt an

        } catch (Exception e) {
            Log.e(logTag, "getView: " + e.getMessage()+" - "+e.getCause());

        }

        return view;   //Gibt nach einem Fehler in der Darstellung leeres View zurück
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
