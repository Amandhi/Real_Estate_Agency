package com.example.myrealestateagency.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrealestateagency.R;

import java.text.DecimalFormat;

public class LoanSimulatorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView propertyIcon;
    private TextView propertyID;
    private TextView propertyPrice;
    private EditText loanPrice;
    private EditText loanRate;
    private EditText loanDuration;
    private TextView monthlyPayments;
    private TextView totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_loan_simulator);

        propertyIcon = findViewById(R.id.property_icon);
        propertyID = findViewById(R.id.property_ID);
        propertyPrice = findViewById(R.id.property_price);
        loanPrice = findViewById(R.id.loan_price);
        loanRate = findViewById(R.id.loan_rate);
        loanDuration = findViewById(R.id.loan_duration);
        monthlyPayments = findViewById(R.id.simulation_info);
        totalAmount = findViewById(R.id.simulation_info2);

        findViewById(R.id.btn_simulator).setOnClickListener(this);

        restorePropertyDetails();
    }

    @Override
    public void onClick(View view) {

        double loan = Double.parseDouble(loanPrice.getText().toString());
        double rate = Integer.parseInt(loanRate.getText().toString());
        double duration = Integer.parseInt(loanDuration.getText().toString());


        double a = rate/1200;
        double b = (1+a);
        double c = Math.pow(b, -duration);
        double payments = (loan * a) / (1-c);
        double total = payments * duration;

        monthlyPayments.setText("Monthly payments : "+ new DecimalFormat("##.##").format(payments) + " €");
        totalAmount.setText("Total payments : "+ new DecimalFormat("##.##").format(total) + " €");

    }

    public void restorePropertyDetails() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            propertyID.setText(extras.getString("pID"));
            propertyPrice.setText("Price : "+ extras.getString("pPrice"));
            String type = extras.getString("pType");
            restorePropertyIcon(type);

        }
    }

    public void restorePropertyIcon(String propertyType) {

            if(propertyType.equals("Apartment")){
                propertyIcon.setImageResource(R.drawable.building);
            }
            if(propertyType.equals("House")){
                propertyIcon.setImageResource(R.drawable.house);
            }
            if(propertyType.equals("Office")){
                propertyIcon.setImageResource(R.drawable.briefcase);

            }

        Log.i("type", propertyType);


    }
}
