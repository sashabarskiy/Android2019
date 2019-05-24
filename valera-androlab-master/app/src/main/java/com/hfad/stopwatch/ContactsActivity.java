package com.hfad.stopwatch;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ListView listView = (ListView) findViewById(R.id.contactsLV);

	ActivityCompat.requestPermissions(ContactsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getContactList());

        listView.setAdapter(arrayAdapter);

    }


    private ArrayList<String> getContactList() {
        ArrayList<String> arr = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            arr.add(name + "   " + phoneNumber);
        }
        phones.close();

        return arr;
    }

}
