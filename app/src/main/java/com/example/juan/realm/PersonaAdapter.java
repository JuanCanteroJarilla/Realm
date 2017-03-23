package com.example.juan.realm;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import io.realm.internal.Context;

/**
 * Created by Juan on 13/03/2017.
 */

public class PersonaAdapter extends RealmBaseAdapter<Persona> implements ListAdapter {

    LlistarActivity listact;
    private ArrayList<Persona> persList;
    private Context context;
    private static final String TAG = "TAG";
    Realm realm;

    public PersonaAdapter(@Nullable OrderedRealmCollection<Persona> data) {
        super(data);
    }

    private static class ViewHolder{

        TextView txNom;
        TextView txNaix;
        TextView txSexe;
        TextView txEdat;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.txNom = (TextView) convertView.findViewById(R.id.txNom);
            viewHolder.txNaix = (TextView) convertView.findViewById(R.id.txNaixement);
            viewHolder.txSexe = (TextView) convertView.findViewById(R.id.txSexe);
            viewHolder.txEdat = (TextView) convertView.findViewById(R.id.txEdat);




            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Persona item = adapterData.get(position);


        viewHolder.txNom.setText(item.getNom());
        viewHolder.txNaix.setText(item.getDataNaixament());
        viewHolder.txSexe.setText(item.getGenere());
        try {
            viewHolder.txEdat.setText(String.valueOf(item.getAge()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return convertView;
    }


}
