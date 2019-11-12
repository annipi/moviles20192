package com.mesirendon.sqlite;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.mesirendon.sqlite.DB.EnterpriseOperations;
import com.mesirendon.sqlite.Model.Enterprise;

import java.util.List;

public class ViewAllEnterprises extends ListActivity {

  private EnterpriseOperations enterpriseOperations;
  List<Enterprise> enterprises;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_all_enterprises);
    enterpriseOperations = new EnterpriseOperations(this);
    enterprises = enterpriseOperations.getAllEnterprises();
    ArrayAdapter<Enterprise> adapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_list_item_1,
        enterprises
    );
    setListAdapter(adapter);
  }
}
