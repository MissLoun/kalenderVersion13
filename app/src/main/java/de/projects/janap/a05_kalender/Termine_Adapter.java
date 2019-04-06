package de.projects.janap.a05_kalender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Termine_Adapter extends ArrayAdapter<Termin> {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Termine_Adapter(Context pContext, List<Termin> pTermine) {

        super(pContext, R.layout.termine_aktueller_tag, pTermine);
        inflater = LayoutInflater.from(pContext);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Get Methoden-------------------------------------------------------*/
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.termine_aktueller_tag, parent, false);
        }

        TextView txtTitel = view.findViewById(R.id.txt_Titel_Termin);
        txtTitel.setText(getItem(position).getTitel());
        TextView txtBeschreibung = view.findViewById(R.id.txt_Beschreibung_Termin);
        txtBeschreibung.setText(getItem(position).printFuerList());

        TextView txtFarbe = view.findViewById(R.id.farbe_Des_Termins);
        txtFarbe.setBackgroundColor(getItem(position).getFarbe());

        return view;
    }
    /*-------------------------Andere Methoden----------------------------------------------------*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse


}
