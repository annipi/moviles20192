package com.mesirendon.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mesirendon.sqlite.DB.EnterpriseOperations;
import com.mesirendon.sqlite.Model.Enterprise;
import com.mesirendon.sqlite.Model.EnterpriseType;

public class AddUpdateEnterprise extends AppCompatActivity {

  private static final String EXTRA_ENT_ID = "com.mesirendon.entId";
  private static final String EXTRA_ADD_UPDATE = "com.mesirendon.add_update";

  private EditText name;
  private EditText url;
  private EditText phone;
  private EditText email;
  private EditText productsAndServices;
  private RadioGroup classification;
  private RadioButton consulting, tailoredDevelopment, softwareFactory;
  private Button addUpdateEnterpriseButton;

  private Enterprise newEnterprise, oldEnterprise;
  private long id;
  private EnterpriseOperations enterpriseOperations;
  private String mode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_update_enterprise);
    newEnterprise = new Enterprise();
    oldEnterprise = new Enterprise();
    enterpriseOperations = new EnterpriseOperations(this);

    name = (EditText) findViewById(R.id.edit_text_name);
    url = (EditText) findViewById(R.id.edit_text_url);
    phone = (EditText) findViewById(R.id.edit_text_phone);
    email = (EditText) findViewById(R.id.edit_text_email);
    productsAndServices = (EditText) findViewById(R.id.edit_products_and_services);
    classification = (RadioGroup) findViewById(R.id.radio_classification);
    consulting = (RadioButton) findViewById(R.id.radio_consulting);
    tailoredDevelopment = (RadioButton) findViewById(R.id.radio_tailored_development);
    softwareFactory = (RadioButton) findViewById(R.id.radio_software_factory);
    addUpdateEnterpriseButton = (Button) findViewById(R.id.button_add_update_enterprise);

    mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
    if (mode.equals("Update")) {
      addUpdateEnterpriseButton.setText(R.string.update_enterprise);
      id = getIntent().getLongExtra(EXTRA_ENT_ID, 0);
      initializeEnterprise(id);
    }

    classification.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
          case R.id.radio_tailored_development:
            newEnterprise.setType(EnterpriseType.TAILORED_DEVELOPMENT);
            if (mode.equals("Update")) oldEnterprise.setType(EnterpriseType.TAILORED_DEVELOPMENT);
            break;
          case R.id.radio_software_factory:
            newEnterprise.setType(EnterpriseType.SOFTWARE_FACTORY);
            if (mode.equals("Update")) oldEnterprise.setType(EnterpriseType.SOFTWARE_FACTORY);
            break;
          default:
            newEnterprise.setType(EnterpriseType.CONSULTING);
            if (mode.equals("Update")) oldEnterprise.setType(EnterpriseType.CONSULTING);
        }
      }
    });

    addUpdateEnterpriseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String text;
        if (mode.equals("Add")) {
          newEnterprise.setName(name.getText().toString());
          newEnterprise.setUrl(url.getText().toString());
          newEnterprise.setPhone(phone.getText().toString());
          newEnterprise.setEmail(email.getText().toString());
          newEnterprise.setProductsAndServices(productsAndServices.getText().toString());
          enterpriseOperations.addEnterprise(newEnterprise);
          text = "La empresa " + newEnterprise.getName() + " se ha agredado correctamente";
        } else {
          oldEnterprise.setName(name.getText().toString());
          oldEnterprise.setUrl(url.getText().toString());
          oldEnterprise.setPhone(phone.getText().toString());
          oldEnterprise.setEmail(email.getText().toString());
          oldEnterprise.setProductsAndServices(productsAndServices.getText().toString());
          enterpriseOperations.updateEnterprise(oldEnterprise);
          text = "La empresa " + oldEnterprise.getName() + " se ha actualizado correctamente";
        }
        Toast toast = Toast.makeText(AddUpdateEnterprise.this, text, Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(AddUpdateEnterprise.this, MainActivity.class);
        startActivity(intent);
      }
    });
  }

  private void initializeEnterprise(long id) {
    oldEnterprise = enterpriseOperations.getEnterprise(id);
    name.setText(oldEnterprise.getName());
    url.setText(oldEnterprise.getUrl());
    phone.setText(oldEnterprise.getPhone());
    email.setText(oldEnterprise.getEmail());
    productsAndServices.setText(oldEnterprise.getProductsAndServices());
    switch (oldEnterprise.getType()) {
      case EnterpriseType.TAILORED_DEVELOPMENT:
        classification.check(R.id.radio_tailored_development);
        break;
      case EnterpriseType.SOFTWARE_FACTORY:
        classification.check(R.id.radio_software_factory);
        break;
      default:
        classification.check(R.id.radio_consulting);
    }
  }
}
