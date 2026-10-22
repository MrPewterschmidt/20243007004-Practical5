package com.example.practicalfive;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

public class Municipality extends AppCompatActivity {

    private final TextProcessor textProcessor = new TextProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
            );

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        TextView municipalityNameTextView =
                findViewById(R.id.municipalityNameTextView);

        ImageView municipalityImageView =
                findViewById(R.id.municipalityImageView);

        TextView municipalityInfoTextView =
                findViewById(R.id.municipalityInfoTextView);

        Button visitSiteButton =
                findViewById(R.id.visitSiteButton);

        String receivedMunicipality =
                getIntent().getStringExtra(
                        MainActivity.EXTRA_MUNICIPALITY
                );

        String processedMunicipality =
                textProcessor.processMunicipality(
                        receivedMunicipality
                );

        if (processedMunicipality == null) {

            Toast.makeText(
                    this,
                    getString(R.string.error_invalid_municipality_data),
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        String[] municipalityInformation =
                getResources().getStringArray(
                        R.array.municipalities_array
                );

        int informationIndex;
        int imageResource;
        int colourResource;
        String websiteUrl;

        switch (processedMunicipality) {

            case "Beijing":
                informationIndex = 0;
                imageResource = R.drawable.beijing;
                colourResource = R.color.municipality_beijing;
                websiteUrl = "https://english.beijing.gov.cn/";
                break;

            case "Shanghai":
                informationIndex = 1;
                imageResource = R.drawable.shanghai;
                colourResource = R.color.municipality_shanghai;
                websiteUrl = "https://english.shanghai.gov.cn/";
                break;

            case "Chongqing":
                informationIndex = 2;
                imageResource = R.drawable.chongqing;
                colourResource = R.color.municipality_chongqing;
                websiteUrl = "https://english.cq.gov.cn/";
                break;

            case "Tianjin":
                informationIndex = 3;
                imageResource = R.drawable.tianjin;
                colourResource = R.color.municipality_tianjin;
                websiteUrl = "https://en.tj.gov.cn/";
                break;

            default:
                Toast.makeText(
                        this,
                        getString(R.string.error_invalid_municipality_data),
                        Toast.LENGTH_SHORT
                ).show();

                finish();
                return;
        }

        int themeColour =
                ContextCompat.getColor(
                        this,
                        colourResource
                );

        municipalityNameTextView.setText(
                processedMunicipality
        );

        municipalityNameTextView.setBackgroundColor(
                themeColour
        );

        municipalityImageView.setImageResource(
                imageResource
        );

        municipalityInfoTextView.setText(
                municipalityInformation[informationIndex]
        );

        municipalityInfoTextView.setTextColor(
                themeColour
        );

        visitSiteButton.setBackgroundTintList(
                ColorStateList.valueOf(themeColour)
        );

        int lightText =
                ContextCompat.getColor(
                        this,
                        R.color.color_text_light
                );

        int darkText =
                ContextCompat.getColor(
                        this,
                        R.color.color_text_dark
                );

        if (processedMunicipality.equals("Chongqing")) {
            municipalityNameTextView.setTextColor(darkText);
            visitSiteButton.setTextColor(darkText);
        } else {
            municipalityNameTextView.setTextColor(lightText);
            visitSiteButton.setTextColor(lightText);
        }
        visitSiteButton.setOnClickListener(view -> {
            Intent websiteIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));

            try {
                startActivity(websiteIntent);
            } catch (ActivityNotFoundException exception) {
                Toast.makeText(
                        Municipality.this,
                        getString(R.string.error_no_browser),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}