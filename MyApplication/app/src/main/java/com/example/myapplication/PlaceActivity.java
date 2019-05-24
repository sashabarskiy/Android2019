package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.Contact.ContactAdapter;
import com.example.myapplication.Messgae.Message;
import com.example.myapplication.Messgae.MessagesAdapter;
import com.example.myapplication.async.MessgesTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// получение данных о месте расположения
public class PlaceActivity extends AppCompatActivity {

    private Button button1;

    private Button button2; // получить сообщения

    private TextView textView;

    private EditText editText2;

    private RecyclerView recyclerView;

    private String token;

    List<Message> messages = new ArrayList<>();

    List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_place);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        editText2 = findViewById(R.id.editText2);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlaceActivity.this));
        Bundle arguments = getIntent().getExtras();
        String fullName = (String) arguments.get("fullName");
        token = (String) arguments.get("token");
        textView.setText("Доброго времени суток, " + fullName);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInitialDataMeggages();
                MessagesAdapter adapter = new MessagesAdapter(PlaceActivity.this, messages);
                recyclerView.setAdapter(adapter);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInitialDataContacts();
                ContactAdapter adapter = new ContactAdapter(PlaceActivity.this, contacts);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    private void setInitialDataContacts(){

        // TODO getContacts

        contacts.clear();

        if (ContextCompat.checkSelfPermission(PlaceActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(PlaceActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);

            contacts.add(new Contact("Попробуйте","Ещё раз"));

        } else {

            Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            ContentResolver cr = getContentResolver();

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                            contacts.add(new Contact(name, phoneNo));

                        }
                        pCur.close();
                    }
                }
            }
            if (cur != null) {
                cur.close();
            }

        }


    }

    private void setInitialDataMeggages(){
        messages.clear();
        MessgesTask messgesTask = new MessgesTask(
                editText2.getText().toString(),
                "2019-05-01",
                "2019-05-23",
                token
        );
        try {
            String response = messgesTask.execute().get();
            if(response==null) {
                messages.add(new Message("null", 0D, 0D, 0D));
                return;
            }
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject);
                messages.add(new Message(jsonObject.getString("modTime"),
                        jsonObject.getDouble("dv0"),
                        jsonObject.getDouble("temp"),
                        jsonObject.getDouble("vbat"))
                );
            }
        } catch (Exception ex){
            messages.add(new Message(ex.getMessage(), 0D, 0D, 0D));
        }

    }

}





