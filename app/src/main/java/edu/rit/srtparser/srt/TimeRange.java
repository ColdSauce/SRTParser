package edu.rit.srtparser.srt;

import java.util.Date;

/**
 * Created by stefan987 on 3/26/2015.
 */
public class TimeRange implements Comparable<TimeRange> {
    private Date start;
    private Date end;

    public TimeRange(Date start, Date end){
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }


    public Date getEnd() {
        return end;
    }

    /**
     * Compares two TimeRanges based on their start dates.
     * @param another
     * The second TimeRange object to compareTo.
     * @return
     * 1 if the current object is greater than the passed in object.
     * -1 if the current object is less than the passed in object.
     * 0 if the two objects are equal.
     */
    @Override
    public int compareTo(TimeRange another) {
        return this.start.compareTo(another.start);
    }
}
