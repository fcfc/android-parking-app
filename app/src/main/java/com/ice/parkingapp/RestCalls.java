package com.ice.parkingapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;


public class RestCalls extends Activity {

    TabHost tabHost;

    private ListView listView;
    private static final String LAT = "37.75";
    private static final String LONGIT = "-121.43";
    private static final String HOST = "http://192.168.1.113/PARK/";
    private static final String theArgs = "?latitude=" + LAT + "&longitude=" + LONGIT+ "&zoom=" + 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restcalls);
        Resources res = getResources(); // Resource object to get Drawables

        listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView((TextView) findViewById(R.id.empty));

        List<String> pointsList = new ArrayList<String>();
        TypedArray apis = res.obtainTypedArray(R.array.apis);

        for (int i=0; i<apis.length(); i++)    {
            pointsList.add(new String(apis.getString(i)));
        }


        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pointsList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View view, int position, long id) {
                CharSequence testString = ((TextView) view).getText();
                String serverURL = HOST + "/" + testString + theArgs;
                // Use AsyncTask execute Method To Prevent ANR Problem
                setContentView(R.layout.result);
                Log.v("Resuming REST", serverURL);

                new LongOperation().execute(serverURL);
            }
        });

    }

    @Override
    public void onResume()
    {
        Log.v("Resuming REST", String.format("Resuming with %d", 25));

        super.onResume();
    }


    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String URLString;
        private String Error = null;
        String outputData = "";

        private ProgressDialog Dialog = new ProgressDialog(RestCalls.this);
        String data ="";
        TextView uiUpdate = (TextView) findViewById(R.id.output);
        TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
        TextView urlText = (TextView) findViewById(R.id.urlText);
        int sizeData = 0;
        EditText serverText = (EditText) findViewById(R.id.serverText);

        protected void onPreExecute() {
            // NOTE: You can call UI Element here
            Dialog.setMessage("Please wait..");
            Dialog.show();

            try{
                // Set Request parameter
                data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            /************ Make Post Call To Web Server ***********/
            BufferedReader reader=null;
            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL(urls[0]);
                URLString = url.getPath();
                // Send POST data request
//                  urlText.setText("text");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "");
                }
                // Append Server Response To Content String
                Content = sb.toString();
            }
            catch(Exception ex)
            {
                Error = ex.getMessage();
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch(Exception ex) {}
            }

            /*****************************************************/
            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
            Dialog.dismiss();

            if (Error != null) {
                uiUpdate.setText("Output : "+ Error);
            } else {


                if (URLString.equals("/get_parking_rules"))
                    doSkusJSONDecode(Content, "parking_rules");
                else if (URLString.equals("/get_closest_parking"))
                    doSkusJSONDecode(Content, "closest_parking");
                else if (URLString.equals("/get_streetsweeping"))
                    doSkusJSONDecode(Content, "streetsweeping");

                else
                    jsonParsed.setText(Content);

            }

        }


        protected void doSkusJSONDecode(String Content, String category)
        {
            String outputData = "";
            JSONObject jsonResponse;
/*
            try {
                JSONArray jsonMainNode = new JSONArray(Content);
                DBAdapter db = new DBAdapter(getApplicationContext());
                db.open();
                //*********** Process each JSON Node ************
                JSONCatalog entry = new JSONCatalog();
                int lengthJsonArr = jsonMainNode.length();

                for(int i=0; i < lengthJsonArr; i++)
                {
                    //***** Get Object for each JSON node.***********
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    //****** Fetch node values **********
                    entry.SKUType           = jsonChildNode.optString("SKUType");
                    db.insertProduct(entry);

                }
                db.close();
                //***************** End Parse Response JSON Data *************
            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
        }

    }
}