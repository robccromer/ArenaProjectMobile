package com.example.robertcromerii.arenaproject;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Robert Cromer II on 3/3/2018.
 */

public class ManageGamesExpandableListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<String> manageGameListHeader;    //Game Name List
    private HashMap<String,List<String>> manageGameListMap;   //Holds The Game Game

    public ManageGamesExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.manageGameListHeader = listDataHeader;
        this.manageGameListMap = listHashMap;
    }
    @Override
    public int getGroupCount()
    {
            return manageGameListHeader.size();
    }
    @Override
    public int getChildrenCount(int i) {
        return manageGameListMap.get(manageGameListHeader.get(i)).size();
    }
    @Override
    public Object getGroup(int i)
    {
        return manageGameListHeader.get(i);
    }
    @Override
    public Object getChild(int i, int i1) {
        return manageGameListMap.get(manageGameListHeader.get(i)).get(i1);  //i = Group Item, i1 = child Item
    }
    @Override
    public long getGroupId(int i) {
        return i;
    }
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listheader_operator_manage_games,null);
        }

        TextView TV_listHeaderManageGames = (TextView)view.findViewById(R.id.TV_listHeaderManageGames);
        TV_listHeaderManageGames.setTypeface(null, Typeface.BOLD);
        TV_listHeaderManageGames.setText(headerTitle);
        return view;
    }
    @Override
    public View getChildView(final int i, int i1, boolean b, View view, final ViewGroup viewGroup) {

        final String childText = (String)getChild(i,i1);
        final String groupText = (String)getGroup(i);
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_operator_manage_games, null);
        }
        Button BTN_removeMangeGame = (Button)view.findViewById(R.id.BTN_removeMangeGame);
        TextView TV_descriptionManageGame = (TextView)view.findViewById(R.id.TV_descriptionManageGame);

        TV_descriptionManageGame.setText(childText);
        BTN_removeMangeGame.setOnClickListener(new View.OnClickListener()
        {
            Operator_Fragment_manageGames operator_fragment_manageGames = new Operator_Fragment_manageGames();
            @Override
            public void onClick(View view)
            {
                Log.d("ManageGamesExpListAdap","OnClick call. Remove " +  groupText);
                operator_fragment_manageGames.DeleteDesiredGameCall(childText, i);
            }
        });
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    public void setNewItems(List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this.manageGameListHeader = manageGameListHeader;
        this.manageGameListMap = manageGameListMap;
        notifyDataSetChanged();
    }
    public void removeGroup(int group) {
        //TODO: Remove the according group. Dont forget to remove the children aswell!
        Log.v("Adapter", "Removing group"+group);
        notifyDataSetChanged();
    }
    public void removeChild(int group, int child) {
        //TODO: Remove the according child
        Log.v("Adapter", "Removing child "+child+" in group "+group);
        notifyDataSetChanged();
    }
}
