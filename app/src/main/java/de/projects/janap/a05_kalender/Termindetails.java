package de.projects.janap.a05_kalender;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;

public class Termindetails extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    /*-------------------------Darstellung--------------------------------------------------------*/
    private Button btnLoeschen, btnBearbeiten, btnZurueck, btnFarbe;
    private TextView txtGanztaegig, txtStartdatum, txtEnddatum, txtStartzeit, txtEndzeit, txtTitel, txtStartBeschriftung, txtEndeBeschriftung;


    /*-------------------------Andere Variablen---------------------------------------------------*/
    private SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat zeitFormat = new SimpleDateFormat("HH:mm");
    private Termin termin;
    private TermineDataSource dataSource;
    private Long id;
    private String logTag = "LogTag_Termindetails";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Get Methoden-------------------------------------------------------*/

    /*-------------------------Set Methoden-------------------------------------------------------*/

    /*-------------------------public Methoden----------------------------------------------------*/
    public void oeffneActifityKalender() {
        Intent intent = new Intent(this, Kalender_GUI.class);
        startActivity(intent);
        finish();
    }

    public void oeffneActifityBearbeiten() {
        Intent intent = new Intent(this, TerminBearbeitung_GUI.class);
        intent.putExtra("TerminId", id);
        startActivity(intent);
        finish();
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        btnLoeschen = findViewById(R.id.btn_Ein_Termin_Loeschen);
        btnBearbeiten = findViewById(R.id.btn_Ein_Termin_Bearbeiten);
        txtGanztaegig = findViewById(R.id.txt_Ein_Termin_Ganztaegig);
        btnZurueck = findViewById(R.id.btn_zurueckZumKalender);

        txtTitel = findViewById(R.id.txt_Ein_Termin_Titel);

        txtStartBeschriftung = findViewById(R.id.txt_Ein_Termin_Beschriftung_Start);
        txtStartdatum = findViewById(R.id.txt_Ein_Termin_Datum_Start);
        txtStartzeit = findViewById(R.id.txt_Ein_Termin_Zeit_Start);

        txtEndeBeschriftung = findViewById(R.id.txt_Ein_Termin_Beschriftung_Ende);
        txtEnddatum = findViewById(R.id.txt_Ein_Termin_Datum_Ende);
        txtEndzeit = findViewById(R.id.txt_Ein_Termin_Zeit_Ende);

        btnFarbe = findViewById(R.id.btn_farbe);

        dataSource = new TermineDataSource(this);

        dataSource.open();
        List<Termin> terminListe = dataSource.getAlleTermine();
        for (int i = 0; i < terminListe.size(); i++) {
            if (terminListe.get(i).getId() == id){
                termin = terminListe.get(i);
            }
        }
        dataSource.close();
    }

    private void setztenDerTerminInfos(){
        txtTitel.setText(termin.getTitel());
        txtStartzeit.setText(zeitFormat.format(termin.getStart().getTime()));
        txtStartdatum.setText(datumFormat.format(termin.getStart().getTime()));

        if (termin.getGanztaegig()) {
            txtGanztaegig.setText(R.string.ganztaegig);
            txtGanztaegig.setGravity(Gravity.END);

            txtStartBeschriftung.setText(R.string.am);

            txtEndzeit.setVisibility(View.INVISIBLE);
            txtEnddatum.setVisibility(View.INVISIBLE);
            txtStartzeit.setVisibility(View.INVISIBLE);
            txtEndeBeschriftung.setVisibility(View.INVISIBLE);

        } else if (!termin.getGanztaegig()) {
            txtGanztaegig.setText(R.string.zeit);
            txtGanztaegig.setGravity(Gravity.START);

            txtEndzeit.setText(zeitFormat.format(termin.getEnde().getTime()));
            txtEnddatum.setText(datumFormat.format(termin.getEnde().getTime()));

            txtStartBeschriftung.setText(R.string.start);
            txtStartzeit.setVisibility(View.VISIBLE);
            txtEndzeit.setVisibility(View.VISIBLE);
            txtEnddatum.setVisibility(View.VISIBLE);
            txtEndeBeschriftung.setVisibility(View.VISIBLE);
        }

        btnFarbe.setBackgroundColor(termin.getFarbe());

    }
    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        btnLoeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoeschen.setEnabled(false);
                dataSource.open();
                dataSource.loescheTermin(termin);
                dataSource.close();
                Log.d(logTag, termin.getTitel() + " wurde gelöscht.");
                finish();

            }
        });

        btnZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnZurueck.setEnabled(false);
                oeffneActifityKalender();
            }
        });

        btnBearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBearbeiten.setEnabled(false);
                oeffneActifityBearbeiten();
            }
        });
    }

    /*-------------------------andere Methoden----------------------------------------------------*/

    /*-------------------------Override Methoden--------------------------------------------------*/
    @Override
    public void onBackPressed() {
        oeffneActifityKalender();
    } //App wird beendet, wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung der Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ein_termin);

        id = getIntent().getExtras().getLong("TerminId");

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet
        setztenDerOnClickListener();

        btnLoeschen.setEnabled(true);
        btnBearbeiten.setEnabled(true);
        btnZurueck.setEnabled(true);

        setztenDerTerminInfos();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse

}
