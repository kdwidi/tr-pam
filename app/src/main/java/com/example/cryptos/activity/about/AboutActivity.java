package com.example.cryptos.activity.about;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptos.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);

        try {
            PackageInfo pInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0);
            TextView appBuild = findViewById(R.id.app_build_text);
            appBuild.setText(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        LinearLayout addressLayout = findViewById(R.id.about_address_linear_layout);
        LinearLayout telpLayout = findViewById(R.id.about_telp_linear_layout);
        LinearLayout emailLayout = findViewById(R.id.about_email_linear_layout);
        TextView telpNumber = findViewById(R.id.about_telp_number_text);
        TextView emailAddress = findViewById(R.id.about_email_text);

        addressLayout.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), MapsActivity.class))
        );
        telpLayout.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel",
                                telpNumber.getText().toString(),
                                null)))
        );
        emailLayout.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto",
                                emailAddress.getText().toString(),
                                null)))
        );
    }
}