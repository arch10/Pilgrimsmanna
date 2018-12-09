package com.gigaworks.tech.bible;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    public void address(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode("Raghunath Vihar"));

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    public void website(View view) {
        String url = getResources().getString(R.string.website).trim();
        Uri webpage = Uri.parse("http:" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void phone(View view) {
        String phone = getResources().getString(R.string.phone).trim();
        phone = phone.replace(" ","");
        String uri = "tel:" + phone ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void dev(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/email");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"arch1824@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bible App "+BuildConfig.VERSION_NAME
                +" // "+Build.MANUFACTURER+" "+Build.MODEL +"("+Build.DEVICE+")"+
                " // " + getResources().getDisplayMetrics().densityDpi);
        startActivity(intent);
    }
}
