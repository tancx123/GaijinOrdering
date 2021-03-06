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
import android.widget.Toast;

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
    EditText postCode;
    EditText city;
    EditText state;

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

                Toast toast = Toast.makeText(getApplicationContext(), "Please fill up the error field", Toast.LENGTH_SHORT);
                boolean nameValidate = false, ICValidate = false, emailValidate = false, phoneValidate = false, mobileValidate = false, postCodeValidate = false, cityValidate = false, stateValidate = false;

                if (custName.getText().toString().isEmpty() || custIC.getText().toString().isEmpty() || custEmail.getText().toString().isEmpty() || companyPhone.getText().toString().isEmpty() || custMobile.getText().toString().isEmpty() || companyName.getText().toString().isEmpty() || custAddress.getText().toString().isEmpty() || postCode.getText().toString().isEmpty() || city.getText().toString().isEmpty() || state.getText().toString().isEmpty()) {

                    if (custName.getText().toString().isEmpty())
                        custName.setError("Please enter this field");

                    if (custIC.getText().toString().isEmpty())
                        custIC.setError("Please enter this field");

                    if (custEmail.getText().toString().isEmpty())
                        custEmail.setError("Please enter this field");

                    if (companyPhone.getText().toString().isEmpty())
                        companyPhone.setError("Please enter this field");

                    if (custMobile.getText().toString().isEmpty())
                        custMobile.setError("Please enter this field");

                    if (companyName.getText().toString().isEmpty())
                        companyName.setError("Please enter this field");

                    if (custAddress.getText().toString().isEmpty())
                        custAddress.setError("Please enter this field");

                    if (postCode.getText().toString().isEmpty())
                        postCode.setError("Please enter this field");

                    if (city.getText().toString().isEmpty())
                        city.setError("Please enter this field");

                    if (state.getText().toString().isEmpty())
                        state.setError("Please enter this field");

                    toast.show();

                } else {

                    if (!custName.getText().toString().matches("[a-zA-Z ]+")) {
                        custName.setError("Only A-Z allow");
                    } else {
                        nameValidate = true;
                    }

                    if (!custIC.getText().toString().matches("^[0-9]*$")) {
                        custIC.setError("IC No. only contain 0-9");
                    } else if (!custIC.getText().toString().matches("^(\\d{12})$")) {
                        custIC.setError("IC No. have 12 digit");
                    } else {
                        ICValidate = true;
                    }

                    if (!custEmail.getText().toString().matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) {
                        custEmail.setError("example@gmail.com");
                    } else {
                        emailValidate = true;
                    }

                    if (!companyPhone.getText().toString().matches("^[0-9]*$")) {
                        companyPhone.setError("Enter your company Phone No. without (-)");
                    } else if (!companyPhone.getText().toString().matches("^(\\d{9}|\\d{10}|\\d{11}|\\d{12})$")) {
                        companyPhone.setError("phone No. should be 9-12 digit");
                    } else {
                        phoneValidate = true;
                    }

                    if (!custMobile.getText().toString().matches("^[0-9]*$")) {
                        custMobile.setError("Enter your Mobile No. without (-)");
                    } else if (!custMobile.getText().toString().matches("^(\\d{10}|\\d{11}|\\d{12})$")) {
                        custMobile.setError("phone No. should be 10-12 digit");
                    } else {
                        mobileValidate = true;
                    }

                    if (!city.getText().toString().matches("[a-zA-Z ]+")) {
                        city.setError("Only A-Z allow");
                    } else {
                        cityValidate = true;
                    }

                    if (!state.getText().toString().matches("[a-zA-Z ]+")) {
                        state.setError("Only A-Z allow");
                    } else {
                        stateValidate = true;
                    }

                    if (!postCode.getText().toString().matches("^[0-9]*$")) {
                        postCode.setError("Only 0-9 allow");
                    } else if (!postCode.getText().toString().matches("^(\\d{5})$")) {
                        postCode.setError("post code only have 5 digit");
                    } else {
                        postCodeValidate = true;
                    }

                    toast.show();
                }

                if (nameValidate == true && ICValidate == true && emailValidate == true && phoneValidate == true && mobileValidate == true && postCodeValidate == true && cityValidate == true && stateValidate == true) {
                    //constructor
                    Customer cust = new Customer("0", custName.getText().toString(), custIC.getText().toString(), custEmail.getText().toString(), companyPhone.getText().toString(), custMobile.getText().toString(), companyName.getText().toString(), gender, custTypeText, custAddress.getText().toString(), postCode.getText().toString(), city.getText().toString(), state.getText().toString());
                    UpdateCust updateCust = new UpdateCust(cust);
                    updateCust.execute("");
                    this.finish();
                    Intent intent = new Intent(this, CustomerMain.class);
                    intent.putExtra("CustomerID", custID);
                    startActivity(intent);
                    return true;
                }else{

                    toast.show();
                }
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
                        cust = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
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
            postCode = findViewById(R.id.text_postCode_input);
            city = findViewById(R.id.text_city_input);
            state = findViewById(R.id.text_state_input);

            custType = findViewById(R.id.group_custType);
            custGender = findViewById(R.id.group_gender);

            custName.setText(cust.getCustName());
            custIC.setText(cust.getIcNo());
            custEmail.setText(cust.getEmail());
            companyPhone.setText(cust.getPhone());
            custMobile.setText(cust.getMobile());
            companyName.setText(cust.getCompanyName());
            custAddress.setText(cust.getAddress());
            postCode.setText(cust.getPostCode());
            city.setText(cust.getCity());
            state.setText(cust.getState());
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
                                "address = '" + customer.getAddress() + "'," +
                                "postCode = '" + customer.getPostCode() + "'," +
                                "city = '" + customer.getCity() + "'," +
                                "state = '" + customer.getState() + "'" +
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
