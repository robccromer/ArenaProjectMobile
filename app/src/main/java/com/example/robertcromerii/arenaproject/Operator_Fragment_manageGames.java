package com.example.robertcromerii.arenaproject;



        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

/**
 * Created by Robert Cromer II on 2/28/2018.
 */

public class Operator_Fragment_manageGames extends Fragment
{
    EditText ET_gameName;
    Button BTN_addGame;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_operator_manage_games, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ET_gameName = view.findViewById(R.id.ET_gameNameInput);
        BTN_addGame = view.findViewById(R.id.BTN_operator_addGame);

        BTN_addGame.setOnClickListener(new View.OnClickListener()
        {
            String ET_gameNameText = null;
            @Override
            public void onClick(View v)
            {
                ET_gameNameText = ET_gameName.getText().toString().trim();
                if(TextUtils.isEmpty(ET_gameNameText))
                {
                    ET_gameName.setError("Field Cannot Be Empty");
                }
                else
                {
                    new InsertGameBackground().execute();
                }
            }
        });
    }
    private class InsertGameBackground extends AsyncTask<Void,Void,Void>
    {
        private String ET_gameNameText = ET_gameName.getText().toString().trim();
        private PreparedStatement preparedStatement = null;
        private Connection connection = null;
        @Override
        protected Void doInBackground(Void... params)
        {
            Log.d("", "Game Name Entered: " + ET_gameNameText);
            try
            {
                connection = DBHandler.getConnection();
                String insertNewGame = "INSERT INTO game (GameName) VALUES(?)";

                preparedStatement = connection.prepareStatement(insertNewGame);
                preparedStatement.setString(1, ET_gameNameText);
                preparedStatement.execute();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedStatement != null)
                    {
                        preparedStatement.close();
                    }
                    if (connection != null)
                    {
                        connection.close();
                    }
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            ET_gameName.setText(null);
            ET_gameNameText = null;
        }
    }
}

