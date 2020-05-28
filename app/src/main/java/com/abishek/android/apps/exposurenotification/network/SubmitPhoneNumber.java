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

package com.abishek.android.apps.exposurenotification.network;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.apps.exposurenotification.R;

public class SubmitPhoneNumber {
  private static final String TAG = "PhoneNumberUploader";

  private String url;
  private RequestQueue queue;

  public SubmitPhoneNumber(Context context, String phoneNumber){
    url = context.getString(R.string.key_server_upload_uri).concat(phoneNumber);
    queue = Volley.newRequestQueue(context);
  }

  // Request a string response from the provided URL.
  private StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
      response -> {
        // Display the first 500 characters of the response string.
        if(response.equals("OK")){
          Log.v(TAG, "Phone Number posted successfully");
        } else{
          // retry
          postPhoneNumber();
        }
      }, error -> {
        Log.v(TAG, "Error: ".concat(error.toString()));
        // retry
        postPhoneNumber();
      });

  // Add the request to the RequestQueue.
  public void postPhoneNumber(){
    queue.add(stringRequest);
  }
}
