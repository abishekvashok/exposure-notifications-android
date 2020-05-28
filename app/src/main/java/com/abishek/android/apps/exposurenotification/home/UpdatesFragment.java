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

package com.abishek.android.apps.exposurenotification.home;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.apps.exposurenotification.R;

public class UpdatesFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_updates, container, false);
  }
  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    WebView webView = view.findViewById(R.id.webview);
    webView.setWebViewClient(new WebViewClient());
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webSettings.setGeolocationEnabled(false);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
    webSettings.setDisplayZoomControls(true);
    webSettings.setAllowFileAccess(false);
    webView.loadUrl("https://twitter.com/KeralaHealth");
  }
}
