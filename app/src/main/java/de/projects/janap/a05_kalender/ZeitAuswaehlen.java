package de.projects.janap.a05_kalender;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

public class ZeitAuswaehlen extends DialogFragment {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    public static final int KENNZEICHNUNG_START_ZEIT = 0;   //Schlüssel zur Identifizierung einer Startzeit in Terminerstellung
    public static final int KENNZEICHNUNG_ENDE_ZEIT = 1;    //Schlüssel zur Identifizierung einer Endzeit in Terminerstellung

    private int kennzeichnung = 0;  //Kennzeichnung speichert den Identifizeierungsschlüssel für das jeweilige Objekt

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setKennzeichnung(int pKennzeichnung) {
        this.kennzeichnung = pKennzeichnung;
    }

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public int getKennzeichnung() {
        return kennzeichnung;
    }

    /*-------------------------Override Methoden--------------------------------------------------*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar kalender = Calendar.getInstance(); //Kalender mit aktuellen Zeit/Datumangaben des Telefons wird erstellt
        int stunde = kalender.get(Calendar.HOUR_OF_DAY);    //Aktuelle Stunde
        int minute = kalender.get(Calendar.MINUTE);         //Aktuelle Minute
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), stunde, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));   //Ein Timepickerdialog mit den aktuellen Zeitangaben wird zurückgegeben um angezeigt werden zu können
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse


}
