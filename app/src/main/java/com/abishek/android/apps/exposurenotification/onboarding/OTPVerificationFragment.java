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

package com.abishek.android.apps.exposurenotification.onboarding;

import static com.abishek.android.apps.exposurenotification.home.ExposureNotificationActivity.HOME_FRAGMENT_TAG;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;
import com.abishek.android.apps.exposurenotification.home.ExposureNotificationActivity;
import com.abishek.android.apps.exposurenotification.storage.ExposureNotificationSharedPreferences;
import com.google.android.apps.exposurenotification.R;
import com.abishek.android.apps.exposurenotification.storage.ExposureNotificationSharedPreferences;
import com.google.android.material.snackbar.Snackbar;


public class OTPVerificationFragment extends Fragment {

  private String otp = "";
  private String phone_number = "";
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Bundle bundle = getArguments();
    if(bundle != null) {
      otp = bundle.getString("OTP");
      phone_number = bundle.getString("PHONE_NUMBER");
    }
    if(otp.equals("") || phone_number.equals("")){
      FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
      fragmentTransaction.replace(
          R.id.home_fragment, new PhoneNumberFragment(), ExposureNotificationActivity.HOME_FRAGMENT_TAG);
      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      fragmentTransaction.addToBackStack(null);
      fragmentTransaction.commit();
    }
    return inflater.inflate(R.layout.fragment_otp_verification, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    EditText otpEditText = view.findViewById(R.id.text_field_otp);
    Button verifyOTPButton = view.findViewById(R.id.verify_otp_button);
    Button changePhoneNumberButton = view.findViewById(R.id.change_phone_no_button);
    verifyOTPButton.setOnClickListener(v->{
      if(!otpEditText.getText().toString().isEmpty() && otpEditText.getText().toString().equals(otp)){
        ExposureNotificationSharedPreferences exposureNotificationSharedPreferences =
            new ExposureNotificationSharedPreferences(requireContext());
        exposureNotificationSharedPreferences.setPhoneNumber(phone_number);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(
            R.id.home_fragment, new OnboardingPermissionFragment(), ExposureNotificationActivity.HOME_FRAGMENT_TAG);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
      } else {
        Snackbar.make(view, "Invalid OTP, please try again", Snackbar.LENGTH_LONG)
            .show();
      }
    });
    changePhoneNumberButton.setOnClickListener(v -> {
      FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
      fragmentTransaction.replace(
          R.id.home_fragment, new PhoneNumberFragment(), ExposureNotificationActivity.HOME_FRAGMENT_TAG);
      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      fragmentTransaction.addToBackStack(null);
      fragmentTransaction.commit();
    });
  }
}
