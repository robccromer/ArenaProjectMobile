package com.example.robertcromerii.arenaproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.models.ArenaListData;

import java.util.List;

/**
 * Created by Robert Cromer II on 3/6/2018.
 */

public class ManageArenasListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<ArenaListData> arenaListDataList;
    public ManageArenasListAdapter(Context mContext, List<ArenaListData> arenaListDataList) {
        this.mContext = mContext;
        this.arenaListDataList = arenaListDataList;
    }
    @Override
    public int getCount() {
        return arenaListDataList.size();
    }
    @Override
    public Object getItem(int i) {
        return arenaListDataList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listheader_operator_manage_arenas, null);
        }
        TextView TV_ArenaName= (TextView)view.findViewById(R.id.TV_ArenaName);
        TextView TV_ArenaID= (TextView)view.findViewById(R.id.TV_ArenaID);

        TV_ArenaName.setText(arenaListDataList.get(i).getArenaName());
        TV_ArenaID.setText(Integer.toString(arenaListDataList.get(i).getArenaID()));

        return view;
    }
}

