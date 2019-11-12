package com.mesirendon.sqlite.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mesirendon.sqlite.Model.Enterprise;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseOperations {
  public static final String LOGTAG = "ENT_MANAGEMENT_SYS";

  SQLiteOpenHelper dbHandler;
  SQLiteDatabase database;

  private static final String[] allColumns = {
      EnterpriseDBHandler.COLUMN_ID,
      EnterpriseDBHandler.COLUMN_NAME,
      EnterpriseDBHandler.COLUMN_URL,
      EnterpriseDBHandler.COLUMN_PHONE,
      EnterpriseDBHandler.COLUMN_EMAIL,
      EnterpriseDBHandler.COLUMN_PRODUCTS_AND_SERVICES,
      EnterpriseDBHandler.COLUMN_TYPE,
  };

  public EnterpriseOperations(Context context) {
    dbHandler = new EnterpriseDBHandler(context);
  }

  public Enterprise addEnterprise(Enterprise enterprise) {
    ContentValues values = new ContentValues();
    values.put(EnterpriseDBHandler.COLUMN_NAME, enterprise.getName());
    values.put(EnterpriseDBHandler.COLUMN_URL, enterprise.getUrl());
    values.put(EnterpriseDBHandler.COLUMN_PHONE, enterprise.getPhone());
    values.put(EnterpriseDBHandler.COLUMN_EMAIL, enterprise.getEmail());
    values.put(EnterpriseDBHandler.COLUMN_PRODUCTS_AND_SERVICES, enterprise.getProductsAndServices());
    values.put(EnterpriseDBHandler.COLUMN_TYPE, enterprise.getType());
    database = dbHandler.getWritableDatabase();
    long insertId = database.insert(EnterpriseDBHandler.TABLE_ENTERPRISES, null, values);
    database.close();
    enterprise.setId(insertId);
    return enterprise;
  }

  public Enterprise getEnterprise(long id) {
    database = dbHandler.getReadableDatabase();
    Cursor cursor = database.query(
        EnterpriseDBHandler.TABLE_ENTERPRISES,
        allColumns,
        EnterpriseDBHandler.COLUMN_ID + "=?",
        new String[]{String.valueOf(id)},
        null,
        null,
        null
    );
    if (cursor != null)
      cursor.moveToFirst();
    Enterprise enterprise = new Enterprise(
        Long.parseLong(cursor.getString(0)),
        cursor.getString(1),
        cursor.getString(2),
        cursor.getString(3),
        cursor.getString(4),
        cursor.getString(5),
        cursor.getString(6)
    );
    database.close();
    return enterprise;
  }

  public List<Enterprise> getAllEnterprises() {
    database = dbHandler.getReadableDatabase();
    Cursor cursor = database.query(
        EnterpriseDBHandler.TABLE_ENTERPRISES,
        allColumns,
        null,
        null,
        null,
        null,
        null
    );
    List<Enterprise> enterprises = new ArrayList<>();
    if (cursor.getCount() > 0)
      while (cursor.moveToNext()) {
        Enterprise enterprise = new Enterprise(
            cursor.getLong(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_NAME)),
            cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_URL)),
            cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_PHONE)),
            cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_EMAIL)),
            cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_PRODUCTS_AND_SERVICES)),
            cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_TYPE))
        );
        enterprises.add(enterprise);
      }
    database.close();
    return enterprises;
  }

  public void updateEnterprise(Enterprise enterprise) {
    ContentValues values = new ContentValues();
    values.put(EnterpriseDBHandler.COLUMN_NAME, enterprise.getName());
    values.put(EnterpriseDBHandler.COLUMN_URL, enterprise.getUrl());
    values.put(EnterpriseDBHandler.COLUMN_PHONE, enterprise.getPhone());
    values.put(EnterpriseDBHandler.COLUMN_EMAIL, enterprise.getEmail());
    values.put(EnterpriseDBHandler.COLUMN_PRODUCTS_AND_SERVICES, enterprise.getProductsAndServices());
    values.put(EnterpriseDBHandler.COLUMN_TYPE, enterprise.getType());

    database = dbHandler.getReadableDatabase();
    database.update(
        EnterpriseDBHandler.TABLE_ENTERPRISES,
        values,
        EnterpriseDBHandler.COLUMN_ID + "=?",
        new String[]{String.valueOf(enterprise.getId())}
    );
    database.close();
  }

  public void removeEnterprise(Enterprise enterprise) {
    database = dbHandler.getWritableDatabase();
    database.delete(
        EnterpriseDBHandler.TABLE_ENTERPRISES,
        EnterpriseDBHandler.COLUMN_ID + "=" + enterprise.getId(),
        null
    );
    database.close();
  }
}
