package com.example.myrealestateagency.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myrealestateagency.R;
import com.example.myrealestateagency.bo.Property;
import com.example.myrealestateagency.view.PropertyDetailActivity;
import com.example.myrealestateagency.adapter.PropertiesAdapter.PropertyViewHolder;

import java.util.List;

public final class PropertiesAdapter
    extends Adapter<PropertyViewHolder> {


    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class PropertyViewHolder extends ViewHolder {

        private ConstraintLayout propertyLayout;
            private final TextView propertyType;
            private final TextView propertyPrice;
            private final TextView address;
            private final TextView surface;
            private final TextView rooms;
            public ImageView rowImage;
            private int[] propertyTypeIcon  = {R.drawable.building, R.drawable.house, R.drawable.briefcase};





        public PropertyViewHolder(@NonNull View itemView)
            {
                super(itemView);

                //We find the references of the widgets
                propertyLayout = itemView.findViewById(R.id.propertyLayout);
                propertyType = itemView.findViewById(R.id.property_type);
                propertyPrice = itemView.findViewById(R.id.price);
                address = itemView.findViewById(R.id.address);
                surface = itemView.findViewById(R.id.surface);
                rooms = itemView.findViewById(R.id.rooms);
                rowImage = itemView.findViewById(R.id.type_icon);


            }

            public void update(final Property property) {
            //We update the UI binding the current property to the current item
            propertyType.setText(property.PropertyType);
            propertyPrice.setText(String.valueOf(property.Price)+ " €");
            address.setText(property.Address);
            surface.setText(String.valueOf(property.PropertySurface)+" m2");


            rooms.setText(String.valueOf(property.Number_of_Rooms));
            String room=null;
            if(Integer.parseInt(rooms.getText().toString()) > 1){
                room = " rooms";
                rooms.setText(String.valueOf(property.Number_of_Rooms)+ room);
            }else{
                room = " room";
                rooms.setText(String.valueOf(property.Number_of_Rooms)+ room);
            }

            setPropertyIconLayout(propertyType.getText().toString());

                //We handle the click on the current item in order to display a new activity : property detail page
            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the PropertyDetailActivity.
                    //The current property is added as an extra
                    //The Property class implement the "Serializable" interface so  the whole object can be put as an extra
                    final Intent intent = new Intent(itemView.getContext(), PropertyDetailActivity.class);
                    intent.putExtra(PropertyDetailActivity.PROPERTY_EXTRA, property);



                    itemView.getContext().startActivity(intent);
                }

            });
        }


        //Method to display a specific image and layout design according to the type of the property
       public void setPropertyIconLayout(String propertyType){
           if(propertyType.equals("Apartment") ){
               rowImage.setImageResource(propertyTypeIcon[0]);
               propertyLayout.setBackgroundResource(R.drawable.apartment_layout);
           }

           if(propertyType.equals("House")){
               rowImage.setImageResource(propertyTypeIcon[1]);
               propertyLayout.setBackgroundResource(R.drawable.house_layout);
           }

           if(propertyType.equals("Office")){
               rowImage.setImageResource(propertyTypeIcon[2]);
               propertyLayout.setBackgroundResource(R.drawable.office_layout);
           }
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

