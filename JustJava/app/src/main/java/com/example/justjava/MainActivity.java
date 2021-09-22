package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int orderQuantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayPrice();
    }

    public void submitOrder(View view) {


        EditText nameTextView = (EditText) findViewById(R.id.name_text_view);
        String output = orderSummary(nameTextView.getText().toString() , displayPrice());
        if(output.equals("")){
            return;
        }
        sendEmail(output);

    }

    public void sendEmail(String summary){
        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.setData(Uri.parse("mailto:smarttalksinfo@gmail.com"));
        sendMail.putExtra(Intent.EXTRA_EMAIL, "anuragtyagi1201@gmail.com");
        sendMail.putExtra(Intent.EXTRA_SUBJECT, "Order My Coffee");
        sendMail.putExtra(Intent.EXTRA_TEXT, summary);
        if (sendMail.resolveActivity(getPackageManager()) != null) {
            startActivity(sendMail);
        }
    }

    public void increament(View view) {
        if (orderQuantity<= 99) {
            orderQuantity+=1;
            display(orderQuantity);
        }
    }

    public void decreament(View view) {

        if (orderQuantity != 1) {
            orderQuantity-=1;
            display(orderQuantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String displayPrice() {

        int finalPrice = orderQuantity * 50;
        CheckBox wippedCream = (CheckBox) findViewById(R.id.wipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        if(wippedCream.isChecked()){
            finalPrice+= orderQuantity*10;
        }
        if(chocolate.isChecked()){
            finalPrice+=orderQuantity*20;
        }
        if(finalPrice == 0){

            return "Total = " + NumberFormat.getCurrencyInstance().format(finalPrice);


        }else
        return "Total = " + NumberFormat.getCurrencyInstance().format(finalPrice);
    }

    private String orderSummary(String name,String price){
        if(name.equals("")){
            Toast.makeText(this, "Please Enter Name a valid Name ", Toast.LENGTH_SHORT).show();
            return "";
        }
        CheckBox wippedCream = (CheckBox) findViewById(R.id.wipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        String orderSummary = "";

        orderSummary+= "Name = " + name + "\n" + "Quantity = " + orderQuantity;

        if(wippedCream.isChecked()){
            orderSummary+=  "\n"+
                   "Toppings Added = Wipped cream";

            if(chocolate.isChecked()){
                orderSummary+=" , Chocolate";


            }


        }else if(chocolate.isChecked()){
            orderSummary+=  "\n"+
                    "Toppings Added = Chocolate";

        }

        orderSummary+="\n"+
                price + "\n" + "Thank You !";

        return orderSummary;
    }
}