package com.bounside.mj.cashiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import dynamicviewpager.TotalSpends;
import LanguagePreference.ReadPreference;
import LanguagePreference.SavePreference;
import loan.Investment_Type;
import reminder.AlarmService;

/**
 * Created by MJ on 3/21/16.
 */
public class Home extends Fragment {
    CardView money,loan,payment;
    TextView moneyText,loanText,paymentText;
    ProgressDialog dialog;
    private String txtLanguage = "en_IN";
    private String strLanguage;
    private RadioGroup radioLanguage;
    private Locale myLocale;
    // for language
    private SavePreference savePreference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_main,container,false);

        getActivity().startService(new Intent(getActivity(), AlarmService.class));

        money = (CardView) v.findViewById(R.id.money);
        loan = (CardView) v.findViewById(R.id.loan);
        payment = (CardView) v.findViewById(R.id.payment);


        moneyText = (TextView) v.findViewById(R.id.money_text);
        loanText = (TextView) v.findViewById(R.id.loan_text);
        paymentText = (TextView) v.findViewById(R.id.payment_text);

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TotalSpends.class));
            }
        });

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Investment_Type.class);
                startActivity(in);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(getActivity(), PaymentSelect.class));

                Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        savePreference = new SavePreference(getActivity());

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLocale();

        getLanguageFromSession();
        checkLanguageSession();

    }
    private void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
     changeLang(language);
    }

    public void getLanguageFromSession() {
        ReadPreference readPreference = new ReadPreference(getActivity());
        strLanguage = readPreference.getSaveLanguage();

        Log.d("HOME_ACTIVITY", "on create " + strLanguage);
    }
    private void checkLanguageSession() {
        if (strLanguage.equalsIgnoreCase("noLanguage")) {
            showChangeLangDialog();
        }
    }

    public void showChangeLangDialog() {

//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View dialogView = inflater.inflate(R.layout.choose_language, null);


        ViewGroup root = (ViewGroup) getActivity(). findViewById(R.id.parent);
        final View dialogView = inflater.inflate(R.layout.choose_language, root);

        radioLanguage = (RadioGroup) dialogView.findViewById(R.id.languageRadioGroup);

        dialogBuilder.setView(dialogView);
        TextView title = new TextView(getActivity());

        title.setPadding(0,50,0,20);
        title.setText(" SELECT LANGUAGE");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.colorAccent));
        title.setTextSize(18);

        dialogBuilder.setCustomTitle(title);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int selectedId = radioLanguage.getCheckedRadioButtonId();
                Button selectName = (Button) dialogView.findViewById(selectedId);

                if (txtLanguage!=null) {
                    txtLanguage = selectName.getText().toString();
                    if (txtLanguage.equalsIgnoreCase("English")) {
                        changeLang("en_IN");
                    } else if (txtLanguage.contains("বাংলা")) {
                        changeLang("bn");
                    } else if (txtLanguage.contains("தமிழ்")) {
                        changeLang("ta");
                    } else if (txtLanguage.contains("हिंदी")) {
                        changeLang("hi");
                    } else if (txtLanguage.contains("Kannada")) {
                        changeLang("kn");
                    } else if (txtLanguage.contains("తెలుగు")) {
                        changeLang("te");
                    } else if (txtLanguage.contains("ബസിൽ")) {
                        changeLang("ml");
                    }
                    else {
                        changeLang("en_IN");
                    }
                }

                // save language in Session
                savePreference.saveLanguage(txtLanguage);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                savePreference.saveLanguage("English");
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void changeLang(String lang)
    {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    public void saveLocale(String lang)
    {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
//        editor.commit();
    }
    private void updateTexts()
    {
        moneyText.setText(R.string.YourMoney);
        loanText.setText(R.string.InvestmentPlaning);
        paymentText.setText(R.string.AllPaymentAndRecharge);

    }
}

