/*
 * Copyright 2020 Google LLC
 * Modifications Copyright 2020 Abishek V Ashok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.abishek.android.apps.exposurenotification.exposure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.apps.exposurenotification.R;

/**
 * Activity to learn more about a given exposure.
 */
public class ExposureLearnMoreActivity extends AppCompatActivity {

  private static final String TAG = "ExposureLearnMoreActivity";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exposure_learn_more);

    View upButton = findViewById(android.R.id.home);
    TextView timestampHolder = findViewById(R.id.textview_exposure_timestamp);
    Button callButton = findViewById(R.id.call_helpline);

    upButton.setContentDescription(getString(R.string.navigate_up));
    upButton.setOnClickListener((v) -> onBackPressed());

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String timestamp = extras.getString("TIMESTAMP");
      if(!timestamp.isEmpty()){
        String text = getString(R.string.possible_exposure_on).concat(" ").concat(timestamp);
        timestampHolder.setText(text);
      }
    }

    callButton.setOnClickListener(v -> {
      Intent intent = new Intent(Intent.ACTION_DIAL);
      intent.setData(Uri.parse("tel:1056"));
      startActivity(intent);
    });

  }

}