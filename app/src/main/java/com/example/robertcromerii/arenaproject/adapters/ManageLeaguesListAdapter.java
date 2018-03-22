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
import com.example.robertcromerii.arenaproject.models.LeagueListData;

import java.util.List;

/**
 * Created by Robert Cromer II on 3/6/2018.
 */

public class ManageLeaguesListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<LeagueListData> leagueListDataList;
    public ManageLeaguesListAdapter(Context mContext, List<LeagueListData> arenaListDataList) {
        this.mContext = mContext;
        this.leagueListDataList = arenaListDataList;
    }
    @Override
    public int getCount() {
        return leagueListDataList.size();
    }
    @Override
    public Object getItem(int i) {
        return leagueListDataList.get(i);
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
            view = inflater.inflate(R.layout.listheader_leagueowner_manage_leagues, null);
        }
        TextView TV_leagueName= (TextView)view.findViewById(R.id.TV_leagueName);
        TextView TV_leagueID= (TextView)view.findViewById(R.id.TV_leagueID);

        TV_leagueName.setText(leagueListDataList.get(i).getLeagueName());
        TV_leagueID.setText(leagueListDataList.get(i).getLeagueID());

        return view;
    }
}

