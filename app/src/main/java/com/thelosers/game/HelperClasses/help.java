package com.thelosers.game.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.thelosers.game.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by deepak on 24/09/17.
 */

public class help {

    public static String getWebsiteName(int number) {

        switch (number) {

            case 9:
                return "Youtube";

            case 8:
                return "Instagram";

            case 5:
                return "Twitter";

            case 4:
                return "facebook";

            case 3:
                return "Wikipedia";

            case 1:
                return "Official";

            default:
                return null;


        }


    }

    public static int getWebsiteIcon(int number) {

        switch (number) {

            case 9:
                return R.drawable.youtube;

            case 8:
                return R.drawable.instagram;

            case 5:
                return R.drawable.twitter;

            case 4:
                return R.drawable.facebook;

            case 3:
                return R.drawable.wikipedialogo;

            case 1:
                return R.drawable.official;

            default:
                return 0;


        }


    }

    public static long getDaysLeft(String unix) {

        long unixSeconds = Long.parseLong(unix.substring(0, unix.length() - 3));
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy"); // the format of your date
        String nextDate = sdf.format(date);

        try {
            long CurrDate = System.currentTimeMillis();
            String currentDate = sdf.format(CurrDate);

            Date date1 = null;
            Date date2 = null;

            date1 = sdf.parse(currentDate);
            date2 = sdf.parse(nextDate);
            long diff = Math.abs(date1.getTime() - date2.getTime());

            return diff / (24 * 60 * 60 * 1000);


        } catch (Exception e1) {
            System.out.println("exception " + e1);
        }
        return 0;
    }

    public static String getReleaseDate(String unix) {

        long unixSeconds = Long.parseLong(unix.substring(0, unix.length() - 3));
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
        return sdf.format(date);
    }


    public static String getGameModes(int mode) {
        switch (mode) {
            case 2:
                return "Multiplayer";
            case 3:
                return "Co operative";
            case 4:

                return "Split screen";
            case 5:
                return "Massively Multiplayer Online";

            case 1:
                return "Single player";

            default:
                return "";

        }
    }


    public static String getThemes(int mode) {
        switch (mode) {
            case 17:
                return "Fantasy";
            case 18:
                return "Science fiction";
            case 19:

                return "Horror";
            case 20:
                return "Thriller";

            case 21:
                return "Survival";

            case 22:
                return "Historical";
            case 23:
                return "Stealth";
            case 27:

                return "Comedy";
            case 28:
                return "Business";

            case 31:
                return "Drama";

            default:
                return "";

        }
    }

    public static String getGeners(int mode) {
        switch (mode) {
            case 10:
                return "Racing";
            case 11:
                return "Real Time Strategy";
            case 12:
                return "Role playing";
            case 13:
                return "Simulator";

            case 14:
                return "Sport";

            case 15:
                return "Strategy";
            case 16:
                return "Turn-based strategy";
            case 24:

                return "Tactical";
            case 25:
                return "Hack and slash/Beat";

            case 26:
                return "Quiz/Trivia";

            default:
                return "";

        }
    }


    public static int getChoosePlatformLogo(String mode) {
        switch (mode) {
            case "Xbox":
                return R.drawable.xboxlogo;
            case "Window":
                return R.drawable.windowlogo;
            case "Playstation":
                return R.drawable.playstaionlogo;
            case "Nintendo":
                return R.drawable.nintendologo;


            default:
                return R.drawable.xboxlogo;

        }
    }

    public static String getPlatform(int mode) {
        switch (mode) {
            case 3:
                return "Linux";
            case 4:
                return "Nintendo 64";
            case 5:
                return "Wii";
            case 6:
                return "PC";

            case 7:
                return "PlayStation";

            case 8:
                return "PlayStation 2";
            case 9:
                return "PlayStation 3";

            case 11:
                return "Xbox";
            case 12:
                return "Xbox 360";

            case 48:
                return "PlayStation 4";
            case 49:
                return "Xbox One";

            case 130:
                return "Nintendo Switch";

            default:
                return "";

        }
    }

    public static String getStartEndOFWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long CurrDate = System.currentTimeMillis();
        String currentDate = sdf.format(CurrDate);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 6);
        Date enddate = calendar.getTime();
        String endDaString = sdf.format(enddate);
        return currentDate + "&%&%&%" + endDaString;
    }

    public static String getLastEndOFWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long CurrDate = System.currentTimeMillis();
        String currentDate = sdf.format(CurrDate);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        Date enddate = calendar.getTime();
        String endDaString = sdf.format(enddate);
        return currentDate + "&%&%&%" + endDaString;
    }


    public static long convertTimeIntoMilliSeconds(String givenDateString) {
        long timeInMilliseconds = 0;
//        String givenDateString = "2017-08-10 16:08:28";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(getReleaseDate(givenDateString));
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static void addToCalender(Context context, String eventName, String description, String startTime, String endTime) {


        Intent l_intent = new Intent(Intent.ACTION_EDIT);
        l_intent.setType("vnd.android.cursor.item/event");
        //l_intent.putExtra("calendar_id", m_selectedCalendarId);  //this doesn't work
        l_intent.putExtra("title", eventName);
        l_intent.putExtra("description", description);
        l_intent.putExtra("eventLocation", "");
        l_intent.putExtra("beginTime", Long.parseLong(startTime));
        l_intent.putExtra("endTime", Long.parseLong(startTime));
        l_intent.putExtra("allDay", 0);
        //status: 0~ tentative; 1~ confirmed; 2~ canceled
        l_intent.putExtra("eventStatus", 1);
        //0~ default; 1~ confidential; 2~ private; 3~ public
        l_intent.putExtra("visibility", 0);
        //0~ opaque, no timing conflict is allowed; 1~ transparency, allow overlap of scheduling
        l_intent.putExtra("transparency", 0);
        //0~ false; 1~ true
        l_intent.putExtra("hasAlarm", 1);
        try {
            l_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(l_intent);
        } catch (Exception e) {

            Toast.makeText(context.getApplicationContext(), "Sorry, no compatible calendar is found!", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void shareTextUrl(Context context, String message, String subject) {
        String str = "Hey ! Check out this cool game  ";
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, str + message);
        Intent new_intent = Intent.createChooser(shareIntent, "Share via");
        new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(new_intent);
    }


    public static void composeEmail(Context context, String sendTo, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + sendTo));
        // intent.putExtra(Intent.EXTRA_EMAIL, fromTo);
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else
            Toast.makeText(context, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
    }


    public static void openWebPage(Context context, String url) {

        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean checkDate(String upcomingDate) {

        Date todayDate = null, date2 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //For declaring values in new date objects. use same date format when creating dates

            long CurrDate = System.currentTimeMillis();
            String currentDate = sdf.format(CurrDate);
            todayDate = sdf.parse(currentDate); //date1 is February 23, 1995
            date2 = sdf.parse(upcomingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date2.after(todayDate)) {
            return true;
        }

        return false;
    }


    public static  String generateRandomDate() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String str_date1 = "2000-01-01";
        try {
            cal.setTime(formatter.parse(str_date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long value1 = cal.getTimeInMillis();
        Long value2 = System.currentTimeMillis();

        long value3 = (long) (value1 + Math.random() * (value2 - value1));
        cal.setTimeInMillis(value3);
        System.out.println(formatter.format(cal.getTime()));

        return formatter.format(cal.getTime());

    }
}
