package de.projects.janap.a05_kalender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

public class TerminBearbeitung_GUI extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private EditText titel;

    private TextView startDatum, startZeit, enddatum, endzeit, txtEnde, txtStart, txtGanztaegig;
    private Switch ganztaegigJaNein;
    private Button speichern, abbruch, oeffneColorPicker;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private Intent intent;
    private int tagPosition;
    private TerminBearbeitung_Steuerung strg;
    private TermineDataSource dataSource;
    private final String logTag = "LogTag_TerminErstellungGui";
    private Long id;
    private Termin termin;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------get-Methoden-------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public Termin getTermin() {
        return termin;
    }

    public EditText getTitel() {
        return titel;
    }

    public TextView getStartDatum() {
        return startDatum;
    }

    public TextView getStartZeit() {
        return startZeit;
    }

    public TextView getEnddatum() {
        return enddatum;
    }

    public TextView getEndzeit() {
        return endzeit;
    }

    public Button getSpeichern() {
        return speichern;
    }

    public TermineDataSource getDataSource() {
        return dataSource;
    }

    public Button getOeffneColorPicker() {
        return oeffneColorPicker;
    }

    /*-------------------------public Methoden----------------------------------------------------*/
     public void oeffneActivityKalenderMonatsUebersicht() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        startActivity(intent);
        finish();
    }

    public void lasseUnwichtigesVerschwinde() {
        startZeit.setVisibility(View.INVISIBLE);
        endzeit.setVisibility(View.INVISIBLE);
        enddatum.setVisibility(View.INVISIBLE);
        txtEnde.setVisibility(View.INVISIBLE);
        txtStart.setText(R.string.am);
        txtGanztaegig.setText(R.string.ganztaegig);
        txtGanztaegig.setGravity(Gravity.END);
    }

    public void allesSichtbarmachen() {
        startZeit.setVisibility(View.VISIBLE);
        endzeit.setVisibility(View.VISIBLE);
        enddatum.setVisibility(View.VISIBLE);
        txtEnde.setVisibility(View.VISIBLE);
        txtStart.setText(R.string.start);
        txtGanztaegig.setText(R.string.zeit);
        txtGanztaegig.setGravity(Gravity.START);
    }

    /*-------------------------private Methoden---------------------------------------------------*/

    private void initialisieren() {
        titel = findViewById(R.id.txt_Terminbearbeitung_Eingabe_Titel);
        ganztaegigJaNein = findViewById(R.id.switch_Terminbearbeitung_Eingabe_JaNein);
        startDatum = findViewById(R.id.txt_Terminbearbeitung_Eingabe_Datum_Start);
        startZeit = findViewById(R.id.txt_Terminbearbeitung_Eingabe_Zeit_Start);
        enddatum = findViewById(R.id.txt_Terminbearbeitung_Eingabe_Datum_Ende);
        endzeit = findViewById(R.id.txt_Terminbearbeitung_Eingabe_Zeit_Ende);
        speichern = findViewById(R.id.btn_Terminbearbeitung_Speichern);
        abbruch = findViewById(R.id.btn_Terminbearbeitung_Abbrechen);
        txtEnde = findViewById(R.id.txt_Terminbearbeitung_Beschriftung_Ende);
        txtStart = findViewById(R.id.txt_Terminbearbeitung_Beschriftung_Start);
        txtGanztaegig = findViewById(R.id.txt_Terminbearbeitung_Beschriftung_Ganztaegig);
        oeffneColorPicker = findViewById(R.id.btn_bearbeitung_oeffne_ColorPicker);

        intent  = new Intent(this, TerminBearbeitung_GUI.class);

        dataSource = new TermineDataSource(this);
        dataSource.open();
        List<Termin> terminListe = dataSource.getAlleTermine();
        for (int i = 0; i < terminListe.size(); i++) {
            if (terminListe.get(i).getId() == id){
                termin = terminListe.get(i);
            }
        }
        dataSource.close();
        strg = new TerminBearbeitung_Steuerung(this);
    }



    private void setztenDerOnClickListener() {
        abbruch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abbruch.setEnabled(false);
                strg.abbruchGeklickt();
            }
        });
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speichern.setEnabled(false);
                strg.speichernGeklickt();
            }
        });
        oeffneColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endzeit.setEnabled(false);
                strg.oeffneColorPicker();
            }
        });
        startDatum.setOnClickListener(this);
        enddatum.setOnClickListener(this);
        startZeit.setOnClickListener(this);
        endzeit.setOnClickListener(this);
        ganztaegigJaNein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.switchGanztaegigGeklickt();
            }
        });
        titel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                strg.titelEingegeben(s);
            }
        });
    }

    /*-------------------------Überschriebene Methode---------------------------------------------*/
    @Override
    protected void onResume(){
        super.onResume();
        oeffneColorPicker.setEnabled(true);
        startDatum.setEnabled(true);
        enddatum.setEnabled(true);
        startZeit.setEnabled(true);
        endzeit.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        try {
            strg.datumZeitAngabeGeklickt(v);
        } catch (IllegalStateException e) {
            Log.e(logTag, "Error bei Datum/Zeitangabe geklickt: " + e.getMessage());
        }

    }

    @Override
    public void onDateSet(DatePicker view, int jahr, int monat, int tag) {
        strg.datumGeklickt(jahr, monat, tag);
    }

    @Override
    public void onTimeSet(TimePicker view, int stunde, int minute) {
        strg.zeitGeklickt(stunde, minute);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_bearbeitung);



        id = getIntent().getExtras().getLong("TerminId");
        initialisieren();
        setztenDerOnClickListener();

        speichern.setEnabled(true);
        abbruch.setEnabled(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ender der Klasse

}
