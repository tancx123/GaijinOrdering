package com.example.orderandinventorysystem.ui.item;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.orderandinventorysystem.ConnectionPhpMyAdmin;
import com.example.orderandinventorysystem.Model.Item;
import com.example.orderandinventorysystem.R;
import com.example.orderandinventorysystem.ui.customer.CustomerMain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ItemMain extends AppCompatActivity {

    Item item;
    String itemID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_main);
        Intent intent = getIntent();
        String intentItemID = intent.getStringExtra("itemID");
        itemID = intentItemID;

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RetrieveItem retrieveItem = new RetrieveItem(itemID);
        retrieveItem.execute("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle item selection
        switch (item.getItemId()) {
            case R.id.delete: {
                DeleteItem deleteItem = new DeleteItem(itemID);
                deleteItem.execute("");
                this.finish();

                return true;
            }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class RetrieveItem extends AsyncTask<String,String,String> {

        String id;

        RetrieveItem(String id) {

            this.id = id;
        }

        String checkConnection = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            ConnectionPhpMyAdmin connectionClass = new ConnectionPhpMyAdmin();
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    checkConnection = "Please check your internet connection.";
                } else {

                    String query = " SELECT * FROM ITEM WHERE itemID ='" + itemID + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if(rs.next()){
                        item = new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getDouble(6), rs.getDouble(7));
                    }

                    Log.d("Success", "Done");
                    checkConnection = "Done";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                Log.d("Error", ex.toString());
                isSuccess = false;
                checkConnection = "Exceptions" + ex;
            }

            return checkConnection;
        }

        @Override
        protected void onPostExecute(String s) {

            //Log.d("hahaha", item.getItemName());

            TextView itemName = findViewById(R.id.item_name);
            TextView itemUnit = findViewById(R.id.item_unit);
            TextView itemSellPrice = findViewById(R.id.sell_price);
            TextView itemPurchasePrice = findViewById(R.id.purchase_cost);
            TextView itemPurchaseUnit = findViewById(R.id.item_unit_2);
            TextView itemStock = findViewById(R.id.account_stock);
            TextView phyStock = findViewById(R.id.phy_stock);

            itemName.setText(item.getItemName());
            itemUnit.setText(item.getItemUnit());
            itemSellPrice.setText(String.format("%.2f",item.getSellPrice()));
            itemPurchasePrice.setText(String.format("%.2f",item.getCostPrice()));
            itemPurchaseUnit.setText(item.getItemUnit());
            itemStock.setText(String.format("%d", item.getQuantity()));
            phyStock.setText(String.format("%d", item.getQuantity()));

        }
    }

    public class DeleteItem extends AsyncTask<String,String,String> {
        String id;

        DeleteItem(String id) {

            this.id = id;
        }

        String checkConnection = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            ConnectionPhpMyAdmin connectionClass = new ConnectionPhpMyAdmin();
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    checkConnection = "Please check your internet connection.";
                } else {

                    String query = " DELETE FROM ITEM WHERE itemID ='" + itemID + "'";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    Log.d("Success", "Done");
                    checkConnection = "Done";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                Log.d("Error", ex.toString());
                isSuccess = false;
                checkConnection = "Exceptions" + ex;
            }

            return checkConnection;
        }

        @Override
        protected void onPostExecute(String s) {}
    }
}
