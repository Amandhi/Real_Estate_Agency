package com.example.myrealestateagency.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.view.PropertyDetailActivity;
import com.example.myrealestateagency.adapter.PropertiesAdapter.PropertyViewHolder;

import java.util.List;

public final class PropertiesAdapter
    extends Adapter<PropertyViewHolder> {



    public static final class PropertyViewHolder extends ViewHolder {

            private final TextView propertyType;
            private final TextView address;
            private final TextView surface;
            private final TextView rooms;


        public PropertyViewHolder(@NonNull View itemView)
            {
                super(itemView);

                //We find the references of the widgets
                propertyType = itemView.findViewById(R.id.name);
                address = itemView.findViewById(R.id.address);
                surface = itemView.findViewById(R.id.surface);
                rooms = itemView.findViewById(R.id.rooms);
            }

            public void update(final Property property) {
            //We update the UI binding the current property to the current item
            propertyType.setText(property.PropertyType);
            address.setText(property.Address);
            surface.setText(property.PropertySurface);
            rooms.setText(property.Number_of_Rooms);

            //We handle the click on the current item in order to display a new activity
            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the UserDetailActivity.
                    //The current property is added as an extra
                    //The Property class implement the "Serializable" interface so  the whole object can be put as an extra
                    final Intent intent = new Intent(itemView.getContext(), PropertyDetailActivity.class);
                    intent.putExtra(PropertyDetailActivity.PROPERTY_EXTRA, property);

                    itemView.getContext().startActivity(intent);
                }

            });
        }
        }

    private final List<Property> properties;

    public PropertiesAdapter(List<Property> properties)
    {
        this.properties = properties;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We create the ViewHolder
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_property, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertiesAdapter.PropertyViewHolder holder, int position)
    {
        //We update the ViewHolder
        holder.update(properties.get(position));
    }

    @Override
    public int getItemCount()
    {
        return properties.size();
    }

}

