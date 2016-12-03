package com.meguillaume.contactsselectionapplication;

/*
     Purpose of this application is to demonstrate a Contact Intent Application in Android Studio by:
     Creating a Contact Object.
     Creating a Contact Intent and Contact Page Activities.
     Creating Explicit and Implicit Intents.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ContactIntentActivity extends AppCompatActivity {

    private final int PHONE = 0;
    private final int WEBSITE = 1;
    private ListView intentListView;
    private ArrayAdapter<String> adapter;
    private List<ContactObject> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_intent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inflate my view
        setContentView(R.layout.activity_contact_intent);

        intentListView = (ListView) findViewById(R.id.listView1);

        // initialize the list and add item
        contactsList = new ArrayList<>();
        contactsList.add(new ContactObject("Android One", "111-111-1111", "www.naruto.com"));
        contactsList.add(new ContactObject("Android Two", "222-222-2222", "www.naruto.com"));
        contactsList.add(new ContactObject("Android Three", "333-333-3333", "www.naruto.com"));
        contactsList.add(new ContactObject("Android Four", "444-444-4444", "www.naruto.com"));

        List<String> listName = new ArrayList<>();
        for (int i = 0; i < contactsList.size(); i++) {
            listName.add(contactsList.get(i).getName());
        }

        // initialize the ArrayAdapter object
        adapter = new ArrayAdapter<>(ContactIntentActivity.this, android.R.layout.simple_list_item_1, listName);

        // set the adapter of the ListView
        intentListView.setAdapter(adapter);

        // setonclicklistener in adapterview cannot be applied here. changed to setonItemClickListener
        intentListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ContactIntentActivity.this, ContactPageActivity.class);
                i.putExtra("Object", contactsList.get(position));
                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Bundle resultData = data.getExtras();
        String value = resultData.getString("value");
        switch (resultCode) {
            case PHONE:
                //Implicit intent to make a call
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + value)));
                    break;
                case WEBSITE:
                    //Implicit intent to visit website
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + value)));
                    break;
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_intent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
