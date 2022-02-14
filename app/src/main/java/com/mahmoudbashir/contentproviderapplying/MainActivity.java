package com.mahmoudbashir.contentproviderapplying;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {

            // Get the ContentResolver
            ContentResolver cr = getContentResolver();
            // Get the Cursor of all the contacts
         /*   Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            // Move the cursor to first. Also check whether the cursor is empty or not.
            if (cursor.moveToFirst()) {
                // Iterate through the cursor
                do {
                    // Get the contacts name
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Toast.makeText(MainActivity.this, "name : "+name, Toast.LENGTH_SHORT).show();
                    Log.d("phones_no : ",""+name);
                } while (cursor.moveToNext());
            }
            // Close the curosor
            cursor.close();*/

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID));
            String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equals("1")){
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                        null,
                        null);

                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Toast.makeText(MainActivity.this, "name : "+phoneNumber, Toast.LENGTH_SHORT).show();
                    Log.d("phones_no : ",""+phoneNumber);
                }
                phones.close();

            }
          }
        }
    }
}