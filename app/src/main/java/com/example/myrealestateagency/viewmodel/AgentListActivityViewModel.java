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
        AgentListCreation();
    }



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

    /*public void saveAgentLogin(String name)
    {
        //We save only if there is something to save
       // if (TextUtils.isEmpty(name) == false)
        {
            AppPreferences.saveAgentSelection(getApplication(), name);
        }
    }

    public boolean loadAgentProfile()
    {
        boolean profile_selection = false;
        //We retrieve the name store into the shared preferences
        final String agentName = AppPreferences.getAgentSelection(getApplication());

        //if the name is not null we restore it
        if (TextUtils.isEmpty(agentName) == false)
        {
            profile_selection = true;
            return profile_selection;
        }
        return profile_selection;
    }*/


}
