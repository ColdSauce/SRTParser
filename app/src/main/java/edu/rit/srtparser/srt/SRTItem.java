package edu.rit.srtparser.srt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan987 on 3/26/2015.
 */
public class SRTItem implements Comparable<SRTItem> {

    private List<String> contents = null;
    private TimeRange timeRange = null;
    private int count = -1;

    protected SRTItem(){
        contents = new ArrayList<String>();
    }
    protected SRTItem(int count, TimeRange timeRange, List<String> contents){
        this.timeRange = timeRange;
        this.contents = contents;
        this.count = count;
    }

    public void setTimeRange(TimeRange timeRange) {
        if(this.timeRange != null){
             throw new IllegalStateException("Setting a value for time range when time range is " +
                     "already a thing in SRTItem.");
        }
        this.timeRange = timeRange;
    }

    public void setCount(int count) {
        if(this.count != -1){
            throw new IllegalStateException("Setting a value for count when count is already a " +
                    "thing in SRTItem.");
        }
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public List<String> getContents() {
        return contents;
    }

    @Override
    public int compareTo(SRTItem another) {

        return timeRange.compareTo(another.getTimeRange());
    }

    @Override
    public String toString() {
        return "SRTItem{" +
                "contents=" + contents +
                ", timeRange=" + timeRange +
                ", count=" + count +
                '}';
    }
}
