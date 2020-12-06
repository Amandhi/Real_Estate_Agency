package com.example.myrealestateagency.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.adapter.AgentsAdapter.AgentViewHolder;
import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.view.PropertyListActivity;
import com.example.myrealestateagency.viewmodel.AgentListActivityViewModel;


import java.util.List;

public class AgentsAdapter extends Adapter<AgentViewHolder>  {

    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class AgentViewHolder
            extends ViewHolder
             {

        private final TextView name;
        private AgentListActivityViewModel viewModel;


        public AgentViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //We find the references of the widgets
            name = itemView.findViewById(R.id.name);

        }

        public void update(final Agent agent)
        {
            //We update the UI binding the current user to the current item
            name.setText(agent.AgentName);


            //We handle the click on the current item in order to display a new activity
            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the UserDetailActivity.
                    //The current agent is added as an extra
                    //The Agent class implement the "Serializable" interface so  the whole object can be put as an extra
                    final Intent intent = new Intent(itemView.getContext(), PropertyListActivity.class);
                    intent.putExtra(PropertyListActivity.AGENT_EXTRA, agent);

                    itemView.getContext().startActivity(intent);
                }

            });
        }

       /* @Override
        public void onClick(View v)
        {
            saveLogin();
        }

        private void saveLogin()
        {
            viewModel = new ViewModelProvider(this).get(AgentListActivityViewModel.class);
            viewModel.saveAgentLogin(name.getText().toString());

        }



        @NonNull
        @Override
        public ViewModelStore getViewModelStore() {
            return null;
        }*/
    }

    private final List<Agent> agents;

    public AgentsAdapter(List<Agent> agents)
    {
        this.agents = agents;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We create the ViewHolder
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_agent, parent, false);
        return new AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position)
    {
        //We update the ViewHolder
        holder.update(agents.get(position));
    }

    @Override
    public int getItemCount()
    {
        return agents.size();
    }



}
