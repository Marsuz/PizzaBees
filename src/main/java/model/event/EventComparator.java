package model.event;

import java.util.Comparator;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class EventComparator implements Comparator<RestaurantEvent> {
    @Override
    public int compare(RestaurantEvent o1, RestaurantEvent o2) {
        if(o1.getTime() > o2.getTime()) return -1;
        if(o1.getTime() == o2.getTime()) return 0;
        return 1;
    }
}
