package com.example.orderandinventorysystem.ui.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.orderandinventorysystem.R;


public class Sales_Details extends Fragment {


 public Sales_Details() {

 }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sales_order_details, container, false);
    }

}
