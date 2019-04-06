package de.projects.janap.a05_kalender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TermineDatenbankHelfer extends SQLiteOpenHelper {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    public static final String DB_NAME = "termine.db";  //Name der Datenbank
    public static final int DB_VERSION = 1; //Versionsnummer der Datenbank

    public static final String TABELLE_NAME_TERMINE = "termine";    //Name der Tabelle

    /*-------------------------andere Spalten-----------------------------------------------------*/
    public static final String SPALTE_ID = "_id";   //späterer Primärschlüssel
    public static final String SPALTE_TITEL = "titel";  //Spaltenbezeichnung Termin Titel

    /*-------------------------Spalte Start-------------------------------------------------------*/
    public static final String SPALTE_START_TAG = "starttag";    //Speicherort für Starttage
    public static final String SPALTE_START_MONAT = "startmonat";    //Speicherort für Startmonate
    public static final String SPALTE_START_JAHR = "startjahr";    //Speicherort für Startjahre
    public static final String SPALTE_START_STUNDE = "startstunde"; //Speicherort für Startstunden
    public static final String SPALTE_START_MINUTE = "startminute";    //Speicherort für Startminuten

    /*-------------------------Spalte Ende--------------------------------------------------------*/
    public static final String SPALTE_ENDE_TAG = "endtag";    //Speicherort für Endtage
    public static final String SPALTE_ENDE_MONAT = "endmonat";    //Speicherort für Endmonate
    public static final String SPALTE_ENDE_JAHR = "endjahr";    //Speicherort für Endjahre
    public static final String SPALTE_ENDE_STUNDE = "endstunde"; //Speicherort für Endstunden
    public static final String SPALTE_ENDE_MINUTE = "endminute";    //Speicherort für Endminuten

    public static final String SPALTE_GANZTAEGIG = "ganztaegig";    //Speicherort ob es ein ganztägiger Termin ist
    public static final String SPALTE_FARBE = "farbe";    //Speicherort für Farbe

    /*-------------------------Datenbankeneigenschaften-------------------------------------------*/
    //Einzelne Spalten dürfen nicht null sein
    //Id wird automatisch nach oben gezählt
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABELLE_NAME_TERMINE +
                    "(" + SPALTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    SPALTE_TITEL + " TEXT NOT NULL, " +

                    SPALTE_START_TAG + " INTEGER NOT NULL, " +
                    SPALTE_START_MONAT + " INTEGER NOT NULL, " +
                    SPALTE_START_JAHR + " INTEGER NOT NULL, " +
                    SPALTE_START_STUNDE + " INTEGER NOT NULL, " +
                    SPALTE_START_MINUTE + " INTEGER NOT NULL, " +

                    SPALTE_ENDE_TAG + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_MONAT + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_JAHR + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_STUNDE + " INTEGER NOT NULL, " +
                    SPALTE_ENDE_MINUTE + " INTEGER NOT NULL, " +

                    SPALTE_FARBE + " INTEGER NOT NULL, " +

                    SPALTE_GANZTAEGIG + " INTEGER NOT NULL );";
    /*-------------------------Andere Variablen---------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public TermineDatenbankHelfer(Context context) {
        super(context, DB_NAME, null, DB_VERSION);  //Konstruktor der Elternklasse (SQLiteOpenHelper)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------Andere Methoden----------------------------------------------------*/

    /*-------------------------Override Methoden--------------------------------------------------*/
    // Die onCreate-Methode wird nur aufgerufen, wenn die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("da", "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        } catch (Exception ex) {
            Log.e("da", "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse

}
