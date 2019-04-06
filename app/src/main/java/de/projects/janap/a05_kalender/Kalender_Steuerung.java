package de.projects.janap.a05_kalender;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Kalender_Steuerung {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private Kalender_GUI dieGui;
    private final String logTag = "LogTag_KalenderSteuerung";

    /*-------------------------Attribute für den Kalender-----------------------------------------*/
    public static Calendar KALENDER;
    public static String[] FARBEN = new String[11];

    private static Calendar heute;
    private ArrayList<Calendar> tage = new ArrayList<>();
    private String[] bezeichnungen = new String[11];

    private SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");

    /*-------------------------Attribute für die Termine-----------------------------------------*/


    /*-------------------------Schlüssel----------------------------------------------------------*/
    public static final String LETZTER_MONAT_UEBERGABE = "LetzterMonatÜbergabe";
    public static final String LETZTES_JAHR_UEBERGABE = "LetztesJahrÜbergabe";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Kalender_Steuerung(Kalender_GUI pGui) {
        dieGui = pGui;
        initialisieren();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Die On-Click Methoden----------------------------------------------*/
    public void btnZuvorGeklickt() {
        KALENDER.add(Calendar.MONTH, -1);    //der Monat des Kalenders wird um eins reduziert
        aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
    }

    public void btnWeiterGeklickt() {
        KALENDER.add(Calendar.MONTH, 1); //der Monat des Kalenders wird um eins addiert
        aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
    }

    public void txtHeutigerTagGeklickt() {
        KALENDER.set(Calendar.YEAR, heute.get(Calendar.YEAR));
        KALENDER.set(Calendar.MONTH, heute.get(Calendar.MONTH));
        aktualisiereKalender();
        dieGui.setTxtMomentanesDatum(datumFormat.format(heute.getTime()));
    }

    public void btnNeuerTerminGeklickt() {
        dieGui.oeffneActifityNeuerTermin();
    }

    public void einTagInDerTabelleDesAktuellenMonatsGeklickt(View view, int position) {
        //nur wenn angeklickte Zelle nicht null ist (Wenn es ein Tag im Monat ist)
        if (tage.get(position) != null) {
            if (dieGui.getAltesView() == null) {
                dieGui.setAltesView(view);  //wenn noch keine Zelle angeklickt wurde, ist die momentane auch die letzte
            }
            Calendar tag = tage.get(position);

            KALENDER.set(Calendar.DAY_OF_MONTH, tag.get(Calendar.DAY_OF_MONTH));
            dieGui.setTxtMomentanesDatum(datumFormat.format(KALENDER.getTime()));

            //zuvor angeklickte Zelle wird unmarkiert
            dieGui.getAltesView().setBackgroundColor(0x00000000);

            //neu angeklickte Zelle wird markiert -> ausgewählt
            view.setBackgroundColor(Color.parseColor("#5e394046"));

            //neu angeklickte Zelle wird jetzt alte Zelle für später
            dieGui.setAltesView(view);

            zeigeTermineDesTages(tag);

        }
    }

    public void einTagInDerTabelleDesAktuellenMonatsLangGeklickt(View view, int position, long id) {
        if (tage.get(position) != null) {
            dieGui.getTabelleAktuellerMonat().performItemClick(view, position, id); //Zelle wird geklickt
            dieGui.oeffneActifityNeuerTermin(); //Terminerstellung wird geöffnet
        }
    }

    public void einTerminWurdeGeklickt(int pPosition, long pId, ListView pTermineListView) {
        Termin termin = (Termin) pTermineListView.getItemAtPosition(pPosition);
        dieGui.oeffneActifityTerminDetails(termin.getId());
    }

    /*-------------------------public Methoden----------------------------------------------------*/
    public void aktualisiereKalender() {
        setztenDesMonatsArray();
        List<Termin> terminListe = dieGui.getDataSource().getAlleTermine();
        Kalender_Adapter adapterAktuellerMonat = new Kalender_Adapter(dieGui, tage, terminListe); //KalenderAdapter um den Kalender in der Tabelle darzustellen
        dieGui.setTxtMonatAnzeige(bezeichnungen[KALENDER.get(Calendar.MONTH)]);  //setzt die neue Monatsbezeichnung fest

        dieGui.setTxtMomentanesDatum("" + (KALENDER.get(Calendar.YEAR)));
        dieGui.getTabelleAktuellerMonat().setAdapter(adapterAktuellerMonat);    //Kalender wird dargestellt

        Log.d(logTag, "Kalender wurde aktualisiert");
    }

    public void zeigeTermineDesTages(Calendar pTag){
        List<Termin> allesTermine = dieGui.getDataSource().getAlleTermine();

        List<Termin> termineDesTages = new ArrayList<>();

        if (allesTermine != null){
            //Überprüfung ob es Termine an diesem Tag gibt
            Termin termin;

            for (int i = 0; i < allesTermine.size(); i++) {
                termin = allesTermine.get(i);

                //Termin Start und Ende in einem Monat
                if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                        && (pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)) {

                    termineDesTages.add(termin);

                }
                //Termin Start in anderem Monat als das Ende
                else if ((pTag.get(Calendar.MONTH) == termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getStart().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) >= termin.getStart().get(Calendar.DAY_OF_MONTH)
                        && (pTag.get(Calendar.MONTH) != termin.getEnde().get(Calendar.MONTH))) {

                    termineDesTages.add(termin);

                }
                //Termin Ende in anderem Monat als der Start
                else if ((pTag.get(Calendar.MONTH) == termin.getEnde().get(Calendar.MONTH) && pTag.get(Calendar.YEAR) == termin.getEnde().get(Calendar.YEAR)) && pTag.get(Calendar.DAY_OF_MONTH) <= termin.getEnde().get(Calendar.DAY_OF_MONTH)
                        && (pTag.get(Calendar.MONTH) != termin.getStart().get(Calendar.MONTH))) {

                    termineDesTages.add(termin);
                }

                //Termin Start und Ende in anderem Monat  -> Termin geht jeden Tag in diesem Monat
                else if ((pTag.get(Calendar.MONTH) > termin.getStart().get(Calendar.MONTH) && pTag.get(Calendar.MONTH) < termin.getEnde().get(Calendar.MONTH))
                        && (pTag.get(Calendar.YEAR) >= termin.getStart().get(Calendar.YEAR) && pTag.get(Calendar.YEAR) <= termin.getEnde().get(Calendar.YEAR))) {

                    termineDesTages.add(termin);
                }
            }


            if (termineDesTages != null){
                Termine_Adapter termineAdapter = new Termine_Adapter(dieGui, termineDesTages);

                dieGui.getListViewTermineDesTages().setAdapter(termineAdapter);
            }
        }
    }

    public void zeigeAlleTermineAn() {
        List<Termin> terminListe = dieGui.getDataSource().getAlleTermine();
        Termine_Adapter termineAdapter = new Termine_Adapter(dieGui, terminListe);

        dieGui.getListViewTermineDesTages().setAdapter(termineAdapter);
    }



    /*-------------------------private Methoden---------------------------------------------------*/

    private void setztenDesMonatsArray() {
        KALENDER.set(Calendar.DAY_OF_MONTH, 1);
        int ersterTag = KALENDER.get(Calendar.DAY_OF_WEEK) - 2;   //da 1. Position 0 ist und - dem Tag
        tage = new ArrayList<>();
        int datum = 1;
        for (int i = 0; i < 42; i++) {

            /*--------------------Fehlerbehebung--------------------------------------------------*/
            if (i == 0) {
                if (KALENDER.get(Calendar.DAY_OF_WEEK) == 0) {
                    datum = 1;  //Da Fehler mit dem ersten des Monats auftrat
                }
                if (ersterTag == -1) {
                    ersterTag = 6;
                }
                if (ersterTag == 7) {
                    ersterTag = 0;
                }
            }
            /*--------------------Array füllen----------------------------------------------------*/
            if ((i >= ersterTag) && !(i >= KALENDER.getActualMaximum(Calendar.DAY_OF_MONTH) + ersterTag)) {    //wenn die aktuelle Position groeßer ist als der erste Tag des Monats & wenn die position groeßer ist als das Ende des Monats
                Calendar tag = Calendar.getInstance();
                tag.set(KALENDER.get(Calendar.YEAR), KALENDER.get(Calendar.MONTH), datum, 0, 0);
                datum++;
                tage.add(tag);
            } else {
                tage.add(null);
            }
        }

    }

    private void initialisieren() {
        bezeichnungen = dieGui.getResources().getStringArray(R.array.monate);
        FARBEN = dieGui.getResources().getStringArray(R.array.farben);
        heute = Calendar.getInstance();
        KALENDER = Calendar.getInstance();
        KALENDER.set(Calendar.DAY_OF_MONTH, 1);

        dieGui.setTxtHeutigerTag(datumFormat.format(heute.getTime()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse
}
