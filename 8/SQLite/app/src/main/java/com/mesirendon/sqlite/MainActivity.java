package com.mesirendon.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mesirendon.sqlite.DB.EnterpriseOperations;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private Button addEnterpriseButton;
  private Button editEnterpriseButton;
  private Button deleteEnterpriseButton;
  private Button viewAllEnterprisesButton;
  private EnterpriseOperations enterpriseOperations;
  private static final String EXTRA_ENT_ID = "com.mesirendon.entId";
  private static final String EXTRA_ADD_UPDATE = "com.mesirendon.add_update";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    addEnterpriseButton = (Button) findViewById(R.id.button_add_enterprise);
    editEnterpriseButton = (Button) findViewById(R.id.button_edit_enterprise);
    deleteEnterpriseButton = (Button) findViewById(R.id.button_delete_enterprise);
    viewAllEnterprisesButton = (Button) findViewById(R.id.button_view_enterprises);

    addEnterpriseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddUpdateEnterprise.class);
        intent.putExtra(EXTRA_ADD_UPDATE, "Add");
        startActivity(intent);
      }
    });

    editEnterpriseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getEnterpriseIdAndUpdateEnterprise();
      }
    });

    deleteEnterpriseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getEnterpriseIdAndRemoveEnterprise();
      }
    });

    viewAllEnterprisesButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ViewAllEnterprises.class);
        startActivity(intent);
      }
    });

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void getEnterpriseIdAndRemoveEnterprise() {
    LayoutInflater layoutInflater = LayoutInflater.from(this);
    View getEnterpriseIdView = layoutInflater.inflate(R.layout.fragment_dialog_get_enterprise_id, null);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setView(getEnterpriseIdView);

    final EditText enterpriseIdEditText = (EditText) getEnterpriseIdView.findViewById(R.id.text_edit_enterprise_id);

    builder
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int enterpriseId) {
            enterpriseOperations = new EnterpriseOperations(MainActivity.this);
            enterpriseOperations.removeEnterprise(enterpriseOperations.getEnterprise(Long.parseLong(enterpriseIdEditText.getText().toString())));
            Toast toast = Toast.makeText(MainActivity.this, "Compañía borrada exitosamente", Toast.LENGTH_SHORT);
            toast.show();
          }
        })
        .create()
        .show();
  }

  private void getEnterpriseIdAndUpdateEnterprise() {
    LayoutInflater layoutInflater = LayoutInflater.from(this);
    View getEnterpriseIdView = layoutInflater.inflate(R.layout.fragment_dialog_get_enterprise_id, null);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setView(getEnterpriseIdView);

    final EditText enterpriseIdEditText = (EditText) getEnterpriseIdView.findViewById(R.id.text_edit_enterprise_id);

    builder
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int enterpriseId) {
            Intent intent = new Intent(MainActivity.this, AddUpdateEnterprise.class);
            intent.putExtra(EXTRA_ADD_UPDATE, "Update");
            intent.putExtra(EXTRA_ENT_ID, Long.parseLong(enterpriseIdEditText.getText().toString()));
            startActivity(intent);
          }
        })
        .create()
        .show();
  }

}
