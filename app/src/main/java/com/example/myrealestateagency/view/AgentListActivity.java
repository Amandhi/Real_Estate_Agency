package com.example.myrealestateagency.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.adapter.AgentsAdapter;
import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.viewmodel.AgentListActivityViewModel;

import java.util.ArrayList;
import java.util.List;

final public class AgentListActivity extends AppCompatActivity  {

    public static final String TAG = AgentListActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private AgentListActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_agent_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));




        viewModel = new ViewModelProvider(this).get(AgentListActivityViewModel.class);

        //PopulateAgentTable();
        observeAgents();
        //RestoreAgentProfileSelection();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        //viewModel.loadAgents();
        PopulateAgentTable();
    }



    private void observeAgents()
    {
        viewModel.agents.observe(this, new Observer<List<Agent>>()
        {
            @Override
            public void onChanged(List<Agent> agents)
            {
                final AgentsAdapter agentsAdapter = new AgentsAdapter(agents);
                recyclerView.setAdapter(agentsAdapter);
            }
        });
    }

    //We initialise our database one time and display all profiles on the page (case of 1st use of the app).
    // If the database is already populated, we simply display what is already saved in the database, onto our app page.
    private void PopulateAgentTable(){
        boolean agent_list = viewModel.AgentsCreation();

        if(agent_list==true){
            viewModel.AgentListCreation();
            viewModel.loadAgents();
        }else{
            viewModel.loadAgents();
        }
    }

}





