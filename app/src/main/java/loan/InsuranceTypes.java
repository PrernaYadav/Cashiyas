package loan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bounside.mj.cashiya.BikeInsuranceActivity;
import com.bounside.mj.cashiya.CarInsuranceActivity;
import com.bounside.mj.cashiya.ChildLifeInsuranceActivity;
import com.bounside.mj.cashiya.CriticalIllnessInsuranceActivity;
import com.bounside.mj.cashiya.HealthInsuranceActivity;
import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.TermLifeInsuranceActivity;
import com.bounside.mj.cashiya.TravelInsuranceActivity;
import com.bounside.mj.cashiya.UlipInsuranceActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import shared_pref.UserInformation;

/**
 * Created by Neeraj Sain on 9/21/2016.
 */

public class InsuranceTypes extends AppCompatActivity implements View.OnClickListener {
    static String insurance_type = "";
    ImageView health, term, travel, car, bike, accident, critical, ulip, child;
    String currentDateTime;

    Jsonparse jsonp = new Jsonparse(InsuranceTypes.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insurance_types);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle(R.string.Insurance);
        }

        currentDateTime = new Date().toString();

        health = (ImageView) findViewById(R.id.healthinsurance);
        term = (ImageView) findViewById(R.id.terminsurance);
        travel = (ImageView) findViewById(R.id.travelinsurance);
        car = (ImageView) findViewById(R.id.carinsurance);
        bike = (ImageView) findViewById(R.id.bikeinsurance);
        accident = (ImageView) findViewById(R.id.personalinsurance);
        critical = (ImageView) findViewById(R.id.criticalinsurance);
        ulip = (ImageView) findViewById(R.id.ulipinsurance);
        child = (ImageView) findViewById(R.id.childinsurance);

        health.setOnClickListener(this);
        term.setOnClickListener(this);
        travel.setOnClickListener(this);
        car.setOnClickListener(this);
        bike.setOnClickListener(this);
        accident.setOnClickListener(this);
        critical.setOnClickListener(this);
        ulip.setOnClickListener(this);
        child.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.healthinsurance:
                startActivity(new Intent(InsuranceTypes.this, HealthInsuranceActivity.class));
                insurance_type = "Health Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.terminsurance:
                startActivity(new Intent(InsuranceTypes.this, TermLifeInsuranceActivity.class));
                insurance_type = "Term Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.travelinsurance:
                Intent i4 = new Intent(InsuranceTypes.this, TravelInsuranceActivity.class);
                startActivity(i4);
                insurance_type = "Travel Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.carinsurance:
                Intent i5 = new Intent(InsuranceTypes.this, CarInsuranceActivity.class);
                startActivity(i5);
                insurance_type = "Car Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.bikeinsurance:
                Intent i6 = new Intent(InsuranceTypes.this, BikeInsuranceActivity.class);
                startActivity(i6);
                insurance_type = "Bike Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.personalinsurance:
//               Intent i7 = new Intent(InsuranceTypes.this, PersonalAccidentInsur_type.class);
//               startActivity(i7);

                Toast.makeText(InsuranceTypes.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                insurance_type = "Personal Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.criticalinsurance:
                Intent i8 = new Intent(InsuranceTypes.this, CriticalIllnessInsuranceActivity.class);
                startActivity(i8);
                insurance_type = "Critical Illness Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.ulipinsurance:
                Intent i9 = new Intent(InsuranceTypes.this, UlipInsuranceActivity.class);
                startActivity(i9);
                insurance_type = "ULIP Insurance";
                new sendInsuranceType().execute();

                break;

            case R.id.childinsurance:
                Intent i10 = new Intent(InsuranceTypes.this, ChildLifeInsuranceActivity.class);
                startActivity(i10);
                insurance_type = "Child Insurance";
                new sendInsuranceType().execute();

                break;

            default:
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in1 = new Intent(InsuranceTypes.this, MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class sendInsuranceType extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("fname", UserInformation.UserFirstName));
            param.add(new BasicNameValuePair("lname", UserInformation.UserLastName));
            param.add(new BasicNameValuePair("email", UserInformation.Email));
            param.add(new BasicNameValuePair("phone", UserInformation.Phone));
            param.add(new BasicNameValuePair("type_of_insurance", insurance_type));
            param.add(new BasicNameValuePair("date", currentDateTime));

            Log.i("date", currentDateTime);
//
            JSONObject jobj = jsonp.makeHttpRequest(UserInformation.insurance_type, "POST", param);
//
            String status = "";
            String msg = "";

            try {
                JSONArray responseBody = jobj.getJSONArray("product");
                JSONObject jsonobj = responseBody.getJSONObject(0);
                status = jsonobj.getString("status");
                msg = jsonobj.getString("msg");
//
            } catch (Exception e) {
                e.printStackTrace();
            }

            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("1")) {


            } else if (result.equals("0")) {

            }

        }
    }
}
