package com.example.myrealestateagency.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.adapter.AgentsAdapter.AgentViewHolder;
import com.example.myrealestateagency.bo.Agent;
import com.example.myrealestateagency.preferences.AppPreferences;
import com.example.myrealestateagency.view.PropertyDetailActivity;
import com.example.myrealestateagency.view.PropertyListActivity;
import com.example.myrealestateagency.viewmodel.AgentListActivityViewModel;


import java.util.List;

import static java.security.AccessController.getContext;

public class AgentsAdapter extends Adapter<AgentViewHolder>  {



    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class AgentViewHolder
            extends ViewHolder
             {

        private final TextView name;
        private ImageView rowImage;
        private int[] agentAvatar  = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4};



        public AgentViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //We find the references of the widgets
            name = itemView.findViewById(R.id.name);
            rowImage = itemView.findViewById(R.id.avatar);


        }

        public void update(final Agent agent)
        {
            //We update the UI binding the current user to the current item
            name.setText(agent.AgentName);

            setAgentIcon(name.getText().toString());



            //We handle the click on the current item in order to display a new activity : redirection to the home page of the app
            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the PropertyListActivity.
                    //The current agent is added as an extra
                    //The Agent class implement the "Serializable" interface so  the whole object can be put as an extra
                    final Intent intent = new Intent(itemView.getContext(), PropertyListActivity.class);
                    intent.putExtra(PropertyListActivity.AGENT_EXTRA, agent);

                    AppPreferences.saveAgentSelection(itemView.getContext(), name.getText().toString());


                    itemView.getContext().startActivity(intent);
                    ((Activity)itemView.getContext()).finish();
                }


            });
        }

                 //Method to display a specific profile picture for each agents initialised in the database
                 public void setAgentIcon(String agentName){
                     if(agentName.equals("Noah") ){
                         rowImage.setImageResource(agentAvatar[0]);
                     }

                     if(agentName.equals("Emma")){
                         rowImage.setImageResource(agentAvatar[1]);
                     }

                     if(agentName.equals("Sasha")){
                         rowImage.setImageResource(agentAvatar[2]);
                     }

                     if(agentName.equals("Ivy")){
                         rowImage.setImageResource(agentAvatar[3]);
                     }
                 }


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
