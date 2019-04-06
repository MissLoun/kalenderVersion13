package de.projects.janap.a05_kalender;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Termin {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private long id;
    private Boolean ganztaegig;
    private String titel;
    private Calendar start, ende;
    private SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat zeitFormat = new SimpleDateFormat("HH:mm");
    private int farbe;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Termin(long pId, String pTitel, Calendar pStart, Calendar pEnde, Boolean pGanztaegig, int pFarbe) {
        id = pId;
        titel = pTitel;
        start = pStart;
        ende = pEnde;
        ganztaegig = pGanztaegig;
        farbe = pFarbe;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public Calendar getStart() {
        return start;
    }

    public Calendar getEnde() {
        return ende;
    }

    public long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public Boolean getGanztaegig(){return ganztaegig;}

    public int getFarbe() {
        return farbe;
    }

    /*-------------------------Andere Methoden----------------------------------------------------*/
    public String print() {
        String ausgabe;
        if (ganztaegig) {
            ausgabe = titel + " findet am " +
                    datumFormat.format(start.getTime()) + " den ganzen Tag statt. ";
        } else {
            ausgabe = titel + " findet am " +
                    datumFormat.format(start.getTime()) + " um " +
                    zeitFormat.format(start.getTime()) + " bis zum " +
                    datumFormat.format(ende.getTime()) + " um " +
                    zeitFormat.format(ende.getTime()) + " statt. ";
        }
        return ausgabe;
    }

    public String printFuerList() {
        String ausgabe;
        if (ganztaegig) {
            ausgabe = "Am " +
                    datumFormat.format(start.getTime());
        } else {
            ausgabe = "Vom " +
                    datumFormat.format(start.getTime()) + " um " +
                    zeitFormat.format(start.getTime()) + " Uhr - " +
                    datumFormat.format(ende.getTime()) + " um " +
                    zeitFormat.format(ende.getTime()) + " Uhr. ";
        }
        return ausgabe;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
