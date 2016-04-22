package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class CourierTest {
    private Courier courier;

    @Before
    public void init(){
        courier = new Courier();
        Courier.velocity = 1;
    }

    @Test
    public void testExecuteDelivery(){
        courier.executeDelivery(10, 10);
        assertEquals(20, courier.getTime());
    }

}
