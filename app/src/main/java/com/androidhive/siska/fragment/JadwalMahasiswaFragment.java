package com.androidhive.siska.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.androidhive.siska.activity.MainActivity;
import com.androidhive.siska.app.AppConfig;
import com.androidhive.siska.helper.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.androidhive.siska.R;


public class JadwalMahasiswaFragment extends Fragment {

    private String TAG = JadwalMahasiswaFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url;

    ArrayList<HashMap<String, String>> dataList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflate=inflater.inflate(R.layout.fragment_jadwal_mahasiswa, container, false);

        TextView username = (TextView) myInflate.findViewById(R.id.username);
        TextView name =  (TextView) myInflate.findViewById(R.id.name);

        username.setText(MainActivity.username);
        name.setText(MainActivity.name);

        dataList = new ArrayList<>();

        lv = (ListView) myInflate.findViewById(R.id.list_jadwal_mahasiswa);

        new getData().execute();
        return myInflate;

    }

    private class getData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            url = AppConfig.URL_JADWAL_MAHASISWA+ MainActivity.username;

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String judul = c.getString("judul");
                        String pem1 = c.getString("pem1");
                        String pem2 = c.getString("pem2");
                        String status = c.getString("status");
                        String uji1 = c.getString("uji1");
                        String uji2 = c.getString("uji2");
                        String tglKonfirmasi = c.getString("tgl_konfirmasi");
                        String tglUjian = c.getString("tgl_ujian");
                        String jamUjian=c.getString("jam_ujian");

                        // tmp hash map for single contact
                        HashMap<String, String> data = new HashMap<>();

                        // adding each child node to HashMap key => value
                        data.put("judul", judul);
                        data.put("pem1", pem1);
                        data.put("pem2", pem2);
                        data.put("status", status);
                        data.put("uji1", uji1);
                        data.put("uji2", uji2);
                        data.put("tgl_konfirmasi", tglKonfirmasi);
                        data.put("tgl_ujian", tglUjian);
                        data.put("jam_ujian", jamUjian);
                        // adding contact to contact list
                        dataList.add(data);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), dataList,
                    R.layout.list_jadwal_mahasiswa, new String[]{"judul", "uji1", "uji2","pem1","pem2","status","tgl_ujian","tgl_konfirmasi","jam_ujian"
            }, new int[]{R.id.judul, R.id.uji1, R.id.uji2,R.id.pem1, R.id.pem2, R.id.status,R.id.tgl_ujian, R.id.tgl_konfirmasi,R.id.jam_ujian});

            lv.setAdapter(adapter);
        }

    }


}