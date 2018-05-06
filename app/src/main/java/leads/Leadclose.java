package leads;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JKB-2 on 4/6/2016.
 */
public class Leadclose extends Fragment {
    Jsonparse jsonParser = new Jsonparse(getActivity());
    ProgressDialog dialog;
    ListView lv1;
    ListAdapter adapter1;
    ArrayList<HashMap<String, String>> list;
    String successs;
    String inctype,leadstatus,userenquiry,name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.leadclose, container, false);


        lv1 = (ListView) v.findViewById(R.id.listView1);

        new insertusermain().execute();
        return v;
    }
    public class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            list = new ArrayList<HashMap<String, String>>();
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
            // Toast.makeText(getActivity(), "finally invoked !", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("close", UserInformation.userid));


            JSONObject jobj = jsonParser.makeHttpRequest(
                    UserInformation.leads, "POST", param);
            String message = "";
            String close = "";

            try {
                // message = jobj.getString("msg");
                successs = jobj.getString("status");
                close = jobj.getString("close");



            } catch (Exception e) {
                e.printStackTrace();
            }

            return close ;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

//            Toast.makeText(getActivity(), "insert data" + result, Toast.LENGTH_SHORT)
//                    .show();
            String[] s = {result};
            Log.i("gds", "" + result);
            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                Log.i("values", "" + ja);
                for( int i = 0;i<ja.length();i++)
                {
                    JSONObject jo = ja.getJSONObject(i);
                    inctype = jo.getString("incType");
                    Log.i("db",""+inctype);
                    leadstatus = jo.getString("lead_status");
                    Log.i("db", "" + leadstatus);
                    userenquiry = jo.getString("user_enquiry");
                    Log.i("db", "" + userenquiry);
                    name = jo.getString("name");
                    Log.i("db",""+name);




                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("inctype", inctype);
//                    map.put("leadstatus", leadstatus);
                    map.put("userenquiry", userenquiry);
                    map.put("name", name);

                    list.add(map);
                    Log.i("ddd",",.........."+list);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] from = {"inctype","userenquiry","name"};
            int[] to = {R.id.inctype, R.id.enquiry, R.id.nameleads};
            adapter1 = new SimpleAdapter(getActivity(),list, R.layout.layout_sturcuture_leads, from, to);
            lv1.setAdapter(adapter1);

//            adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 ,s);
//            lv1.setAdapter(adapter1);

        }

    }
}
