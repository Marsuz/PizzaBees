package solver.setting.random;

import model.Delivery;
import model.Order;
import model.Restaurant;
import solver.setting.FailedShuffleException;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jakub Kudzia on 5/5/16.
 */
public class Shuffler {

    Random random;

    public Shuffler() {
        this.random = new Random();
    }

    public void swap(Delivery d) throws FailedShuffleException {
        List<Order> orders = d.getOrders();
        int ordersNum = orders.size();
        if(ordersNum == 1)
            throw new FailedShuffleException();

        int i = random.nextInt(ordersNum);
        int j = generateRandomExclusive(ordersNum, i);
        Collections.swap(orders, i, j);
    }

    public void swap(Delivery d1, Delivery d2) throws FailedShuffleException {
        List<Order> orders1 = d1.getOrders();
        List<Order> orders2 = d1.getOrders();
        int ordersNum1 = orders1.size();
        int ordersNum2 = orders2.size();
        if(ordersNum1 == 0 || ordersNum2 == 0)
            throw new FailedShuffleException();

        int i = random.nextInt(ordersNum1);
        int j = random.nextInt(ordersNum2);

        Order o1 = orders1.remove(i);
        Order o2 = orders2.get(j);

        orders1.add(i, o1);
        orders2.add(j, o2);
    }

    public void swap(Restaurant r1, Restaurant r2) throws FailedShuffleException {

        List<Delivery> deliveries1 = r1.getDeliveries();
        List<Delivery> deliveries2 = r2.getDeliveries();

        //TODO deliveries moze byc puste

        int i = random.nextInt(deliveries1.size());
        int j = random.nextInt(deliveries2.size());
        Delivery d1 = deliveries1.get(i);
        Delivery d2 = deliveries2.get(j);
        swap(d1, d2);

    }

    public void move(Delivery d1, Delivery d2) throws FailedShuffleException {

        List<Order> from;
        List<Order> to;

        if(random.nextInt(2)==0){
            from = d1.getOrders();
            to = d2.getOrders();
        }else{
            from = d2.getOrders();
            to = d1.getOrders();
        }

        int ordersNum1 = from.size();
        int ordersNum2 = to.size();
        if(ordersNum1 == 0)
            throw new FailedShuffleException();

        int i = random.nextInt(ordersNum1);
        int j;
        try{
            j = random.nextInt(ordersNum2);
        } catch (IllegalArgumentException e ){
            j = 0;
        }

        Order o = from.remove(i);
        to.add(j, o);
    }

    public void move(Restaurant r1, Restaurant r2) throws FailedShuffleException {

        List<Delivery> deliveries1 = r1.getDeliveries();
        List<Delivery> deliveries2 = r2.getDeliveries();

        //TODO deliveries moze byc puste
        int i = random.nextInt(deliveries1.size());
        int j = random.nextInt(deliveries2.size());
        Delivery d1 = deliveries1.get(i);
        Delivery d2 = deliveries2.get(j);
        move(d1, d2);

    }

    private int generateRandomExclusive(int bound, int excluded){
        int r;
        if((random.nextInt(2) == 0 || bound == excluded + 1) && excluded != 0)
            r = random.nextInt(excluded);
        else
            r = random.nextInt(bound - excluded -1) + excluded + 1;
        return r;
    }
}
