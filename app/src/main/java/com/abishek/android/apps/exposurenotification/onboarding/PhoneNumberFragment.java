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
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.abishek.android.apps.exposurenotification.home.ExposureNotificationActivity;
import com.google.android.apps.exposurenotification.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.Random;

public class PhoneNumberFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_phone_number, container, false);
  }
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    Button sendOTP = view.findViewById(R.id.verify_phone_number_button);
    Button skipOTP = view.findViewById(R.id.skip_phone_number_button);
    EditText phoneNumberEditText = view.findViewById(R.id.text_field_phone_number);
    String otp = generateOTP();
    sendOTP.setOnClickListener(v -> {
      if(phoneNumberEditText.getText().toString().isEmpty() || phoneNumberEditText.getText().toString().length() != 10){
        Snackbar.make(view, "Invalid phone number, please enter a valid one", Snackbar.LENGTH_LONG)
            .show();
      }else{
        try{
          SmsManager sms = SmsManager.getDefault();
          String msg = "<#> Your OTP for COVID_19 Exposure application is: ".concat(otp);
          sms.sendTextMessage(phoneNumberEditText.getText().toString(), null, msg, null, null);
          Bundle bundle = new Bundle();
          bundle.putString("OTP", otp);
          bundle.putString("PHONE_NUMBER", phoneNumberEditText.getText().toString());
          OTPVerificationFragment otpVerificationFragment = new OTPVerificationFragment();
          otpVerificationFragment.setArguments(bundle);
          FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
          fragmentTransaction.replace(
              R.id.home_fragment, otpVerificationFragment, ExposureNotificationActivity.HOME_FRAGMENT_TAG);
          fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
        }catch (Exception e){
          Snackbar.make(view, "Cannot send SMS. Please try again later", Snackbar.LENGTH_LONG)
              .show();
        }
      }
    });
    /*TODO: disable in release?*/
    skipOTP.setOnClickListener(v -> {
      FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
      fragmentTransaction.replace(
          R.id.home_fragment, new OnboardingPermissionFragment(), ExposureNotificationActivity.HOME_FRAGMENT_TAG);
      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      fragmentTransaction.addToBackStack(null);
      fragmentTransaction.commit();
    });
  }
  private static String generateOTP(){
    Random rnd = new Random();
    int number = rnd.nextInt(999999);
    return String.format("%06d", number);
  }
}
