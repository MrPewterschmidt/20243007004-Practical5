package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MUNICIPALITY =
            "com.example.practicalfive.MUNICIPALITY";

    private EditText municipalityEditText;

    private final TextProcessor textProcessor =
            new TextProcessor();

    private boolean isNavigating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        municipalityEditText =
                findViewById(R.id.municipalityEditText);

        municipalityEditText.setOnEditorActionListener(
                (view, actionId, event) -> {

                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        openMunicipality();
                        return true;
                    }

                    return false;
                }
        );
    }

    private void openMunicipality() {

        if (isNavigating) {
            return;
        }

        String input =
                municipalityEditText
                        .getText()
                        .toString();

        String processedMunicipality =
                textProcessor.processMunicipality(input);

        if (processedMunicipality == null) {

            if (input.trim().isEmpty()) {

                municipalityEditText.setError(
                        getString(
                                R.string.error_empty_municipality
                        )
                );

            } else {

                municipalityEditText.setError(
                        getString(
                                R.string.error_invalid_municipality
                        )
                );
            }

            municipalityEditText.requestFocus();
            return;
        }

        isNavigating = true;

        Intent intent =
                new Intent(
                        MainActivity.this,
                        Municipality.class
                );

        intent.putExtra(
                EXTRA_MUNICIPALITY,
                processedMunicipality
        );

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isNavigating = false;
    }
}