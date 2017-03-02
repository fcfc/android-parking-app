package com.ice.parkingapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;



public class CityList extends ListActivity {


    private ListView mylist;
    public static List<City> theList = new ArrayList<City>();

	private List<City> cityList = new ArrayList<City>();
	String xmlFile = "xml/cities.xml";               // put this value in setup files
    @SuppressWarnings("unchecked")
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


		/* set up handlers for UI elements  */
        setContentView(R.layout.citylist);


		theList.clear();
		XmlPullParser xrp = grabXML(xmlFile);
		getAnswerFromXML(xrp);

		AssetManager assetManager = getAssets();
		String[] files = null;


		setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList));
    }



	public void onListItemClick(ListView parent, View v, int position, long id) {
        City city = (City) getListAdapter().getItem(position);

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("longitude", (float) city.longitude);
        intent.putExtra("latitude", (float) city.latitude);
        intent.putExtra("name", city.name);
        intent.putExtra("zoom", city.zoom);
        intent.putExtra("dataset", 1);
        String item = city.name + "  " + city.longitude + "  " + city.latitude;
        startActivity(intent);
    }






	public XmlPullParser grabXML(String xmlFile)
	{
		// OPEN XML FILE
		InputStream istr = null;
		XmlPullParserFactory factory = null;
		XmlPullParser xrp = null;

		try {
			istr = this.getAssets().open(xmlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		factory.setNamespaceAware(true);

		try {
			xrp = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		try {
			xrp.setInput(istr, "UTF-8");
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return xrp;
	}



	public void getAnswerFromXML(XmlPullParser xrp)
	{
		int i = 0;
		int eventType = 0;
		String tagtext = "";
		String id = "";
		String cityname = "";
		String country = "";
		Double atext = 0.0;
		Double btext = 0.0;
		int ctext = 0;
		int dtext = 0;

		String correcttext = "";


		try {
			eventType = xrp.getEventType();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		int  begin = 0;

		String starttag = "";

		while (eventType != XmlPullParser.END_DOCUMENT) {

			if(eventType == XmlPullParser.START_DOCUMENT) {
			}
			else if(eventType == XmlPullParser.START_TAG) {
				starttag = xrp.getName();
				if (starttag.equals("city"))  {
					begin = 1;
				}
			}
			else if(eventType == XmlPullParser.END_TAG) {
				String endtag = xrp.getName();
				starttag = "";
				if (endtag.equals("city"))  {
					begin = 0;
					cityList.add(new City(id, cityname, country, atext, btext, ctext, dtext));
				}
			}
			else if(eventType == XmlPullParser.TEXT) {
				tagtext = xrp.getText();

				if (starttag.equals("name"))
					cityname = tagtext;
				else if (starttag.equals("country"))
					country = tagtext;
				else if (starttag.equals("lat"))
					atext = Double.parseDouble(tagtext);
				else if (starttag.equals("long"))
					btext = Double.parseDouble(tagtext);
				else if (starttag.equals("correct"))
					correcttext = tagtext;


			}

			try {
				eventType = xrp.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}