package de.projects.janap.a05_kalender;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatumAuswaehlen extends DialogFragment {

    //Datepicker
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    public static final int KENNZEICHNUNG_START_DATUM = 0;  //Schlüssel zur Identifizierung eines Startdatums in Terminerstellung
    public static final int KENNZEICHNUNG_ENDE_DATUM = 1;   //Schlüssel zur Identifizierung eines Enddatums in Terminerstellung
    private int kennzeichnung = 0;  //Kennzeichnung speichert den Identifizeierungsschlüssel für das jeweilige Objekt

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    public void setKennzeichnung(int pKennzeichnung) {
        this.kennzeichnung = pKennzeichnung;    //Um Schlüssel des Objekts zu setzten
    }
    public int getKennzeichnung() {
        return kennzeichnung;
    }

    /*-------------------------Override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Datepickerdialog soll mit dem zuletzt ausgewählten (im normalen Kalender) Datum geöffnet werden
        Calendar kalender = Kalender_Steuerung.KALENDER;
        int jahr = kalender.get(Calendar.YEAR);
        int monat = kalender.get(Calendar.MONTH);
        int tag = kalender.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), jahr, monat, tag);   //Ein Datepickerdialog mit den richtigen Kalenderdaten wird zurückgegeben um angezeigt werden zu können
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
