package com.example.david.adiaryforamarriedwoman;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.HebrewCalendar;
import android.icu.util.ULocale;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import net.sourceforge.zmanim.ComplexZmanimCalendar;
import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;
import net.sourceforge.zmanim.hebrewcalendar.JewishDate;
import net.sourceforge.zmanim.util.GeoLocation;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	Button mNextMonthButton;
	Button mNextDayButton;
	Button mPreviousMonthButton;
	Button mPreviousDayButton;

	Calendar hebrewCalendar;
	ULocale locale;

	TextView dateTextView;


	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		String locationName = "Lakewood, NJ";
		double latitude = 40.096; //Lakewood, NJ
		double longitude = -74.222; //Lakewood, NJ
		double elevation = 0; //optional elevation
		TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
		GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
		ComplexZmanimCalendar czc = new ComplexZmanimCalendar(location);

		JewishCalendar jewishCalendar = new JewishCalendar();
		jewishCalendar.getJewishDayOfMonth();

		mNextMonthButton = findViewById(R.id.nextMonth);
		mNextDayButton = findViewById(R.id.nextDay);
		mPreviousMonthButton = findViewById(R.id.previousMonth);
		mPreviousDayButton = findViewById(R.id.previousDay);

		mNextMonthButton.setOnClickListener(this);
		mNextDayButton.setOnClickListener(this);
		mPreviousMonthButton.setOnClickListener(this);
		mPreviousDayButton.setOnClickListener(this);

		/*locale = new ULocale( "@calendar=hebrew");
		//Locale local = new Locale("iw", "IL");
		hebrewCalendar = Calendar.getInstance(locale);*/

		dateTextView = findViewById(R.id.mainText);

		Log.i("Jewish", "Day of month " + jewishCalendar.getJewishDayOfMonth());
		Log.i("Jewish", "Month " + jewishCalendar.getJewishMonth());
		Log.i("Jewish", "Year " + jewishCalendar.getJewishYear());
		JewishDate jewishDate = new JewishCalendar(jewishCalendar.getJewishYear(), jewishCalendar.getJewishMonth(), jewishCalendar.getJewishDayOfMonth());
		HebrewDateFormatter hebrewDateFormatter = new HebrewDateFormatter();
		hebrewDateFormatter.setHebrewFormat(true);
		dateTextView.setText(hebrewDateFormatter.format(jewishDate));
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.nextMonth:
				hebrewCalendar.add(Calendar.MONTH, 1);
				break;
			case R.id.nextDay:
				hebrewCalendar.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case R.id.previousMonth:
				hebrewCalendar.add(Calendar.MONTH, -1);
				break;
			case R.id.previousDay:
				hebrewCalendar.add(Calendar.DAY_OF_MONTH, -1);
				break;
		}
		dateTextView.setText(getStringFormatedDate(hebrewCalendar.getTime()));
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private String getStringFormatedDate(Date date) {
		DateFormat dt = DateFormat.getDateInstance(DateFormat.FULL, locale);
		return dt.format(date);
	}
}
