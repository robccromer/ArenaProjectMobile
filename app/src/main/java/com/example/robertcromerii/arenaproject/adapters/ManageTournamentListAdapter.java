package com.example.robertcromerii.arenaproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.models.TournamentData;

import java.util.List;

/**
 * Created by Robert Cromer II on 3/29/2018.
 */

public class ManageTournamentListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<TournamentData> tournamentDataList;

    public ManageTournamentListAdapter(Context mContext, List<TournamentData> tournamentDataList) {
        this.mContext = mContext;
        this.tournamentDataList = tournamentDataList;
    }
    @Override
    public int getCount() {
        return tournamentDataList.size();
    }
    @Override
    public Object getItem(int i) {
        return tournamentDataList.get(i);
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
            view = inflater.inflate(R.layout.listheader_leagueowner_manage_tournaments, null);
        }

        TextView TV_TournamentID = (TextView)view.findViewById(R.id.TV_TournamentID);
        TextView TV_TournamentName = (TextView)view.findViewById(R.id.TV_TournamentName);
        TextView TV_TournamentDesc = (TextView)view.findViewById(R.id.TV_TournamentDesc);
        TextView TV_TournamentStyle = (TextView)view.findViewById(R.id.TV_TournamentStyle);

        TV_TournamentID.setText(tournamentDataList.get(i).getTournamentID());
        TV_TournamentName.setText(tournamentDataList.get(i).getTournamentName());
        TV_TournamentDesc.setText(tournamentDataList.get(i).getTournamentDescription());
        TV_TournamentStyle.setText(tournamentDataList.get(i).getTournamentStyle());
        return view;
    }
}
