package com.example.robertcromerii.arenaproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.robertcromerii.arenaproject.R;
import com.example.robertcromerii.arenaproject.adapters.controllers.UserListData;

import java.util.List;

/**
 * Created by Robert Cromer II on 3/6/2018.
 */

public class ManageUsersListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<UserListData> userListDataList;

    public ManageUsersListAdapter(Context mContext, List<UserListData> userListDataList) {
        this.mContext = mContext;
        this.userListDataList = userListDataList;
    }
    @Override
    public int getCount() {
        return userListDataList.size();
    }
    @Override
    public Object getItem(int i) {
        return userListDataList.get(i);
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
            view = inflater.inflate(R.layout.listheader_operator_manage_users, null);
        }
        //View v = View.inflate(mContext, R.layout.listheader_operator_manage_users,null);
        TextView TV_pendingUserName= (TextView)view.findViewById(R.id.TV_pendingUserName);
        TV_pendingUserName.setText(userListDataList.get(i).getPendingUserName());
        view.setTag(userListDataList.get(i).getPendingUserID());
        return view;
    }
}

