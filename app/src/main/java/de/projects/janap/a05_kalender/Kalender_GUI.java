package de.projects.janap.a05_kalender;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Kalender_GUI extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute

    /*-------------------------Darstellung--------------------------------------------------------*/
    private TextView txtMonatAnzeige, txtMomentanesDatum;
    private ListView listViewTermineDesTages;

    private FloatingActionButton fabNeuerTermin;
    private GridView tabelleAktuellerMonat;
    private Button txtHeutigerTag, zurStartseite;
    private ImageButton btnZuvor, btnWeiter;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private Kalender_Steuerung strg;
    private View altesView;
    private final String logTag = "LogTag_KalenderGui";
    private TermineDataSource dataSource;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public ListView getListViewTermineDesTages() {
        return listViewTermineDesTages;
    }

    public GridView getTabelleAktuellerMonat() {
        return tabelleAktuellerMonat;
    }

    public View getAltesView() {
        return altesView;
    }

    public FloatingActionButton getNeuerTermin() {
        return fabNeuerTermin;
    }

    public TermineDataSource getDataSource() {
        return dataSource;
    }

    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setTxtMonatAnzeige(String pTxtMonatAnzeige) {
        this.txtMonatAnzeige.setText(pTxtMonatAnzeige);
    }

    public void setTxtHeutigerTag(String pTxtHeutigerTag) {
        this.txtHeutigerTag.setText(pTxtHeutigerTag);
    }

    public void setTxtMomentanesDatum(String pTxtMomentanesDatum) {
        this.txtMomentanesDatum.setText(pTxtMomentanesDatum);
    }

    public void setAltesView(View pAltesView) {
        this.altesView = pAltesView;
    }

    /*-------------------------public Methoden----------------------------------------------------*/
    public void oeffneActifityNeuerTermin() {
        Intent intent = new Intent(this, TerminErstellung_GUI.class);
        startActivity(intent);
        finish();
    }

    public void oeffneActifityStartbildschirm() {
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);
        finish();
    }

    public void oeffneActifityTerminDetails(long pTerminId) {
        Intent intent = new Intent(this, Termindetails.class);
        intent.putExtra("TerminId", pTerminId);
        startActivity(intent);
        finish();
    }

    /*-------------------------private Methoden---------------------------------------------------*/
    private void initialisieren() {
        tabelleAktuellerMonat = findViewById(R.id.gridView_Kalender_Tabelle_AktuellerMonat);
        listViewTermineDesTages = findViewById(R.id.listView_Termine);

        txtMonatAnzeige = findViewById(R.id.txt_Kalender_Monat);
        txtHeutigerTag = findViewById(R.id.txt_Kalender_HeutigerTag);
        txtMomentanesDatum = findViewById(R.id.txt_Kalender_Momentanes_Datum);
        btnZuvor = findViewById(R.id.btn_Kalender_Zuvor);
        btnWeiter = findViewById(R.id.btn_Kalender_Weiter);
        fabNeuerTermin = findViewById(R.id.fab_neuerTermin);
        zurStartseite = findViewById(R.id.btn_Kalender_zurueckZurStartseit);

        strg = new Kalender_Steuerung(this);
        dataSource = new TermineDataSource(this);
    }

    private void setztenDerOnClickListener() {
        //OnCLickListener rufen Methoden in der Steuerung auf
        btnZuvor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.btnZuvorGeklickt();
            }
        });
        btnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.btnWeiterGeklickt();
            }
        });
        txtHeutigerTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strg.txtHeutigerTagGeklickt();
            }
        });
        fabNeuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabNeuerTermin.setEnabled(false);
                strg.btnNeuerTerminGeklickt();
            }
        });
        tabelleAktuellerMonat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                strg.einTagInDerTabelleDesAktuellenMonatsGeklickt(view, position);
            }
        });
        tabelleAktuellerMonat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                strg.einTagInDerTabelleDesAktuellenMonatsLangGeklickt(view, position, id);
                return true;
            }
        });
        listViewTermineDesTages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewTermineDesTages.setEnabled(false);
                final ListView termineListView = findViewById(R.id.listView_Termine);
                strg.einTerminWurdeGeklickt(position, id, termineListView);
            }
        });
        zurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zurStartseite.setEnabled(false);
                oeffneActifityStartbildschirm();
            }
        });
    }

    /*-------------------------andere Methoden----------------------------------------------------*/

    /*-------------------------Override Methoden--------------------------------------------------*/
    @Override
    protected void onResume() {
        //Wird die Activity aus der Pause wieder geöffnet, führt sie folgendes aus:
        super.onResume();
        dataSource.open();
        strg.aktualisiereKalender();
        //strg.zeigeAlleTermineAn();

        //Wurde die Activity in einem anderen Monat als dem aktuellen verlassen, wird der Kalender mit dem zuletzt geöffneten
        //Monat und Jahr gesetzt -> Info durch übergebenes Intent
        try {
            try {
                Kalender_Steuerung.KALENDER.set(Calendar.MONTH, Integer.parseInt(getIntent().getExtras().getString(Kalender_Steuerung.LETZTER_MONAT_UEBERGABE)));
                Kalender_Steuerung.KALENDER.set(Calendar.YEAR, Integer.parseInt(getIntent().getExtras().getString(Kalender_Steuerung.LETZTES_JAHR_UEBERGABE)));
                strg.aktualisiereKalender();
            } catch (NumberFormatException s) {
                Log.e(logTag, "Error: " + s.getMessage());
            }
        } catch (NullPointerException e) {
            Log.e(logTag, "Error: " + e.getMessage());
        }


    }

    @Override
    protected void onPause() {
        //Wird die Activity pausiert, führt sie folgendes aus:
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onBackPressed() {
        oeffneActifityStartbildschirm();
    } //Startbildschirm wird geöffnet, wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung der Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender__gui);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet


        dataSource.open();  //Datenbank geöffnet
        setztenDerOnClickListener();
        strg.aktualisiereKalender();     //der Monat wird mit den momentanen Daten des Kalenders dargestellt
        //strg.zeigeAlleTermineAn();

        listViewTermineDesTages.setEnabled(true);
        zurStartseite.setEnabled(true);
        listViewTermineDesTages.setEnabled(true);
        fabNeuerTermin.setEnabled(true);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}

