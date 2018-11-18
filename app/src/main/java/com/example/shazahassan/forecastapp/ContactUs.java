package com.example.shazahassan.forecastapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shazahassan.forecastapp.firebase.ContactUsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    private EditText nameE, emailE, messageE;
    private String name, email, message;

    private DatabaseReference messageTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        nameE = findViewById(R.id.name_editText);
        emailE = findViewById(R.id.email_editText);
        messageE = findViewById(R.id.message_editText);
        messageTable = FirebaseDatabase.getInstance().getReference().child("Messages");
    }

    private void checkFields() {
        name = nameE.getText().toString();
        email = emailE.getText().toString();
        message = messageE.getText().toString();
        if (name.equals("")) {
            nameE.setError("Enter Your name Please");
        } else if (email.equals("")) {
            emailE.setError("Enter your mail");
        } else if (message.equals("")) {
            messageE.setError("Enter your message");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailE.setError("Enter email in right format");
        } else {
            saveData(name, email, message);
        }

    }

    private void saveData(String name, String email, String message) {
        ContactUsModel contactUsModel = new ContactUsModel(name, email, message);
        String id = messageTable.push().getKey();
        messageTable.child(id).setValue(contactUsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ContactUs.this, "Message Sent", Toast.LENGTH_LONG).show();
                nameE.setText("");
                emailE.setText("");
                messageE.setText("");
            }
        });

    }

    public void sendMessage(View view) {
        checkFields();
    }
}
