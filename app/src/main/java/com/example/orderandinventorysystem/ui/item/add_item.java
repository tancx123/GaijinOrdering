package com.example.orderandinventorysystem.ui.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;


import com.example.orderandinventorysystem.ConnectionPhpMyAdmin;
import com.example.orderandinventorysystem.Model.Customer;
import com.example.orderandinventorysystem.Model.Item;
import com.example.orderandinventorysystem.R;
import com.example.orderandinventorysystem.ui.customer.CustomerMain;
import com.example.orderandinventorysystem.ui.customer.new_customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class add_item extends AppCompatActivity {

    EditText itemName;
    EditText itemUnit;
    EditText itemDesc;
    EditText sellPrice;
    EditText costPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.text_item_name_input);
        itemUnit = findViewById(R.id.text_item_unit_input);
        itemDesc = findViewById(R.id.text_item_description_input);
        sellPrice = findViewById(R.id.text_selling_price_input);
        costPrice = findViewById(R.id.text_purchase_price_input);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                //constructor
                Item item1 = new Item("0",itemName.getText().toString(), itemUnit.getText().toString(), itemDesc.getText().toString(), Double.parseDouble(sellPrice.getText().toString()), Double.parseDouble(costPrice.getText().toString()));
                AddItem addItem = new AddItem(item1);
                addItem.execute("");
                this.finish();
                Intent intent = new Intent(this, ItemMain.class);
                intent.putExtra("Item", item1);
                startActivity(intent);

                //Log.d("HAHA",itemName.getText().toString() + itemUnit.getText().toString() + itemDesc.getText().toString());
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_main_menu, menu);
        return true;
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class AddItem extends AsyncTask<String,String,String> {

        Item item;

        AddItem(Item item) {

            this.item = item;
        }

        String checkConnection = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... params) {

            ConnectionPhpMyAdmin connectionClass = new ConnectionPhpMyAdmin();
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    checkConnection = "No";
                } else {
                    String query = "SELECT * FROM ITEM ORDER BY itemID DESC LIMIT 1";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String latestID;

                    if (rs.next()) {
                        latestID = rs.getString(1);
                        int numID = Integer.parseInt(latestID.substring(2, 7)) + 1;
                        if (numID < 10)
                            latestID = "I-0000" + Integer.toString(numID);
                        else if (numID < 100)
                            latestID = "I-000" + Integer.toString(numID);
                        else if (numID < 1000)
                            latestID = "I-00" + Integer.toString(numID);
                        else if (numID < 10000)
                            latestID = "I-0" + Integer.toString(numID);
                        else if (numID < 100000)
                            latestID = "I-" + Integer.toString(numID);

                        Log.d("ID", latestID);
                    } else {
                        latestID = "I-00001";
                        Log.d("ID", latestID);
                    }

                    query = "INSERT INTO ITEM VALUES('" + latestID + "', '" + item.getItemName() + "', '" +
                            item.getItemUnit() + "', '" + item.getItemDesc() + "', '" + item.getQuantity() + "', '" +
                            item.getSellPrice() + "', '" + item.getCostPrice() + "')";

                    stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    checkConnection = "Yes";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                Log.d("Error", ex.toString());
                isSuccess = false;
                checkConnection = "No";
            }

            return checkConnection;
        }
    }
}