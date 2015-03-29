package edu.rit.srtparser.srt;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import edu.rit.srtparser.R;

/**
 * Created by stefan987 on 3/26/2015.
 */
public class SRTFile {
    private List<SRTItem> sortedSRTItems = null;
    private String TAG = "SRTFile";

    public SRTFile(Context c, String fileName) {
        sortedSRTItems = new ArrayList<>();
        InputStream rawResourceStream = c.getResources().openRawResource(R.raw.example1);
        String contents = convertStreamToString(rawResourceStream);
        Scanner contentsScanner = new Scanner(contents);
        ParserState previousState = ParserState.SPACE;
        SRTItem currentItem = null;
        while (contentsScanner.hasNextLine()) {
            if (previousState == ParserState.SPACE) {
                //This means that this isn't the start of the while loop.
                if (currentItem != null) {
                    //flush everything

                    //These cases happen if there was something wrong before with how contents
                    //and time range worked.
                    if (currentItem.getContents() == null) {
                        throw new IllegalStateException();
                    } else if (currentItem.getTimeRange() == null) {
                        throw new IllegalStateException();
                    }

                    //If everything went well the program inserts it into the binary search tree.

                    sortedSRTItems.add(currentItem);


                    //reset everything
                    currentItem = new SRTItem();
                } else {
                    currentItem = new SRTItem();
                }

            }

            String currentLine = contentsScanner.nextLine();
            //For every previous state, a different kind of parsing needs to occur.
            switch (previousState) {
                case COUNT:
                    //The current MUST be a time range.
                    TimeRange currentTimeRange = getTimeRangeFromString(currentLine);
                    currentItem.setTimeRange(currentTimeRange);
                    previousState = ParserState.TIME;
                    break;
                case TIME:
                    //The current MUST be Contents.
                    currentItem.getContents().add(getContents(currentLine));
                    previousState = ParserState.CONTENTS;
                    break;
                case CONTENTS:
                    //There exist two possibilities:
                    //  It's contents again.
                    //or
                    //  It's a space.
                    //Must test for both.


                    //If this is the case, it MUST be a space
                    if (currentLine.length() == 0) {
                        previousState = ParserState.SPACE;
                    }
                    //otherwise, it MUST be a line of contents, again.
                    else {
                        currentItem.getContents().add(getContents(currentLine));
                        previousState = ParserState.CONTENTS;
                    }
                    break;
                case SPACE:
                    //The current MUST be count.
                    previousState = ParserState.COUNT;
                    break;
            }
        }
        Collections.sort(sortedSRTItems);
    }


    private int getCountFromString(String s) {
        return Integer.parseInt(s);
    }

    /**
     * Time range in SRT files works this way:
     * hh:mm:ss,mmm --> hh:mm:ss,mmm
     *
     * This method parses that string to create a TimeRange object that is a lot easier to work
     * with.
     *
     * @param s
     * The string needing to be parsed.
     * @return
     * The TimeRange object that corresponds to the string passed in.
     */
    private TimeRange getTimeRangeFromString(String s) {
        //First, split the string into two parts.
        String start = s.split(" --> ")[0];
        String end = s.split(" --> ")[1];
        //Then, convert it to a Date object using java's built in formatting.
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss,SSS");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse(start);
            endDate = formatter.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new TimeRange(startDate,endDate);
    }

    /**
     * This method is simply here to make the switch/case code above more readable.
     *
     * @param s The string of the contents.
     * @return The contents.
     */
    private String getContents(String s) {
        return s;
    }


    private String convertStreamToString(java.io.InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}

