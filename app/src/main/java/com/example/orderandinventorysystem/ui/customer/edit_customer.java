package com.example.orderandinventorysystem.ui.customer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.orderandinventorysystem.ConnectionPhpMyAdmin;
import com.example.orderandinventorysystem.Model.Customer;
import com.example.orderandinventorysystem.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class edit_customer extends AppCompatActivity {
    RadioGroup custType;
    RadioGroup custGender;
    RadioButton radioCustType;
    RadioButton radioGender;
    EditText custName;
    EditText companyName;
    EditText custEmail;
    EditText companyPhone;
    EditText custMobile;
    EditText custAddress;
    EditText custIC;

    String gender;
    String custTypeText;

    RadioButton radioMale;
    RadioButton radioFemale;
    RadioButton radioBusiness;
    RadioButton radioIndividual;


    String custID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        Intent intent = getIntent();
        String intentItemID = intent.getStringExtra("CustomerID");
        custID = intentItemID;

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RetrieveCust retrieveCust = new RetrieveCust(custID);
        retrieveCust.execute("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                //constructor
                Customer cust = new Customer("0", custName.getText().toString(), custIC.getText().toString(), custEmail.getText().toString(), companyPhone.getText().toString(), custMobile.getText().toString(), companyName.getText().toString(), gender, custTypeText, custAddress.getText().toString());
                UpdateCust updateCust = new UpdateCust(cust);
                updateCust.execute("");
                this.finish();
                Intent intent = new Intent(this, CustomerMain.class);
                intent.putExtra("CustomerID", custID);
                startActivity(intent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class RetrieveCust extends AsyncTask<String, String, String> {

        Customer cust;
        String id;

        RetrieveCust(String id) {
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

                    String query = " SELECT * FROM CUSTOMER WHERE custID = '" + custID + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        cust = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
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



            custName = findViewById(R.id.text_custName_input);
            companyName = findViewById(R.id.text_company_name_input);
            custEmail = findViewById(R.id.text_email_input);
            companyPhone = findViewById(R.id.text_phone_input);
            custMobile = findViewById(R.id.text_custMobile_input);
            custAddress = findViewById(R.id.text_address_input);
            custIC = findViewById(R.id.text_custIc_input);
            radioMale = findViewById(R.id.radioBtn_Male);
            radioFemale = findViewById(R.id.radioBtn_Female);
            radioBusiness = findViewById(R.id.radioBtn_Business);
            radioIndividual = findViewById(R.id.radioBtn_Individual);


            custType = findViewById(R.id.group_custType);
            custGender = findViewById(R.id.group_gender);

            custName.setText(cust.getCustName());
            custIC.setText(cust.getIcNo());
            custEmail.setText(cust.getEmail());
            companyPhone.setText(cust.getPhone());
            custMobile.setText(cust.getMobile());
            companyName.setText(cust.getCompanyName());
            custAddress.setText(cust.getAddress());
            gender = cust.getGender();
            custTypeText = cust.getCustType();

            if(gender.equals("Male")){
                radioMale.setChecked(true);
            }
            else if(gender.equals("Female")){
                radioFemale.setChecked(true);
            }

            custGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.radioBtn_Male:
                            radioGender = findViewById(R.id.radioBtn_Male);
                            gender = radioGender.getText().toString();
                            Log.d("hahaha",gender);
                            break;

                        case R.id.radioBtn_Female:
                            radioGender = findViewById(R.id.radioBtn_Female);
                            gender = radioGender.getText().toString();
                            break;

                    }
                }
            });

            if(custTypeText.equals("Business")){
                radioBusiness.setChecked(true);
            }
            else if(custTypeText.equals("Individual")){
                radioIndividual.setChecked(true);
            }

            custType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId) {
                        case R.id.radioBtn_Business:
                            radioCustType = findViewById(R.id.radioBtn_Business);
                            custTypeText = radioCustType.getText().toString();
                            break;

                        case R.id.radioBtn_Individual:
                            radioCustType = findViewById(R.id.radioBtn_Individual);
                            custTypeText = radioCustType.getText().toString();
                            break;

                    }
                }
            });

        }
    }

    public class UpdateCust extends AsyncTask<String, String, String> {

        Customer customer;

        UpdateCust(Customer customer) {

            this.customer = customer;
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
                    checkConnection = "No";
                } else {
                    String query = " UPDATE CUSTOMER SET custName = '" + customer.getCustName() + "', "+
                                "icNo = '" + customer.getIcNo() + "'," +
                                "email = '" + customer.getEmail() + "'," +
                                "phone = '" + customer.getPhone() + "'," +
                                "mobile = '" + customer.getMobile() + "'," +
                                "companyName = '" + customer.getCompanyName() + "'," +
                                "gender = '" + customer.getGender() + "'," +
                                "custType = '" + customer.getCustType() + "'," +
                                "address = '" + customer.getAddress() + "'" +
                                " WHERE custID = '" + custID + "'";

                    Statement stmt = con.createStatement();
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

        @Override
        protected void onPostExecute(String s) {

        }
    }
}
