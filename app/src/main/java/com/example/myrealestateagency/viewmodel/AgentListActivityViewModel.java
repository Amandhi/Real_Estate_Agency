package com.example.myrealestateagency.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.repository.AgentRepository;
import com.example.myrealestateagency.view.PropertyListActivity;

import java.util.List;

public final class AgentListActivityViewModel extends AndroidViewModel {



    Agent agentNoah = new Agent("Noah");
    Agent agentEmma = new Agent("Emma");
    Agent agentSasha = new Agent("Sasha");
    Agent agentIvy = new Agent("Ivy");

    public MutableLiveData<List<Agent>> agents = new MutableLiveData<>();



    public AgentListActivityViewModel(@NonNull Application application)
    {

        super(application);

    }

    //Check needed to make sure we initialise our agent database only one time
    public boolean AgentsCreation(){
        boolean agent_list_state=false;
        final List<Agent> agents = AgentRepository.getInstance(getApplication()).getAgents();
        if(agents.isEmpty()){
            agent_list_state = true;
            return agent_list_state;
        }
        return agent_list_state;
    }


    //Creation of our agent database
    public void AgentListCreation(){
        AgentRepository.getInstance(getApplication()).addAgent(agentNoah);
        AgentRepository.getInstance(getApplication()).addAgent(agentEmma);
        AgentRepository.getInstance(getApplication()).addAgent(agentSasha);
        AgentRepository.getInstance(getApplication()).addAgent(agentIvy);

    }



    public void loadAgents()

    {
        agents.postValue(AgentRepository.getInstance(getApplication()).getAgents());
    }



}
