package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nameField;
    EditText phoneField;
    EditText emailField;
    EditText addressField;
    EditText jobField;
    EditText companyField;
    EditText websiteField;
    EditText imField;

    Button saveButton;
    Button cancelButton;
    Button showButton;

    LinearLayout additionalFields;

    final public static int CONTACTS_MANAGER_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nameField = (EditText) findViewById(R.id.name_field);
        phoneField = (EditText) findViewById(R.id.phone_field);
        emailField = (EditText) findViewById(R.id.email_field);
        addressField = (EditText) findViewById(R.id.address_field);
        jobField = (EditText) findViewById(R.id.job_field);
        companyField = (EditText) findViewById(R.id.company_field);
        websiteField = (EditText) findViewById(R.id.website_field);
        imField = (EditText) findViewById(R.id.im_field);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
        showButton = (Button) findViewById(R.id.show_button);
        showButton.setOnClickListener(buttonClickListener);

        additionalFields = (LinearLayout) findViewById(R.id.additional_fields);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneField.setText(phone);
            } else {
                Toast.makeText(this, "eroare", Toast.LENGTH_LONG).show();
            }
        }
    }
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.show_button:
                    switch (additionalFields.getVisibility()) {
                        case View.VISIBLE:
                            showButton.setText("Show additional fields");
                            additionalFields.setVisibility(View.INVISIBLE);
                            break;
                        case View.INVISIBLE:
                            showButton.setText("Hide additional fields");
                            additionalFields.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case R.id.save_button:
                    String name = nameField.getText().toString();
                    String phone = phoneField.getText().toString();
                    String email = emailField.getText().toString();
                    String address = addressField.getText().toString();
                    String jobTitle = jobField.getText().toString();
                    String company = companyField.getText().toString();
                    String website = websiteField.getText().toString();
                    String im = imField.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivity(intent);
                    startActivityForResult(intent, 123);
                    break;
                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
