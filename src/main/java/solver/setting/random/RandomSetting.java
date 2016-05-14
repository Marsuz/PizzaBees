package solver.setting.random;

import model.Delivery;
import model.Restaurant;
import model.Setting;
import solver.setting.FailedShuffleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jakub Kudzia on 5/5/16.
 */
public class RandomSetting extends Setting {

    private final Random random = new Random();
    private static final int NEIGHBOURHOOD_METHODS = 5;
    private Shuffler shuffler;

    public RandomSetting(List<Restaurant> restaurants) {
        super(restaurants);
        this.shuffler = new Shuffler();
    }

    public RandomSetting(RandomSetting other){
        super(other);

        this.shuffler = new Shuffler();
    }

    @Override
    public Setting getNeighbour(int shuffles) {

        RandomSetting newSetting = new RandomSetting(this);

        for(int i = 0; i < shuffles; i++)
            newSetting.doShuffle();

        return newSetting;
    }

    void doShuffle(){
        try {
            switch(random.nextInt(NEIGHBOURHOOD_METHODS)){
                case 0:
                    swapInDelivery();
                case 1:
                    swapDeliveries();
                case 2:
                    swapRestaurants();
                case 3:
                    moveDeliveries();
                case 4:
                    moveRestaurants();
            }
        } catch (FailedShuffleException e) {
            doShuffle();
        }
    }

    /**
     * @throws FailedShuffleException
     * swap inside one delivery
     */
    void swapInDelivery() throws FailedShuffleException {

        Restaurant r = restaurants.get(random.nextInt(restaurants.size()));
        List<Delivery> deliveries = r.getDeliveries();
        Delivery d = deliveries.get(random.nextInt(deliveries.size()));
        shuffler.swap(d);
    }

    /**
     * @throws FailedShuffleException
     * swap between 2 deliveries from 1 restaurant
     */
    private void swapDeliveries() throws FailedShuffleException {
        Restaurant r = restaurants.get(random.nextInt(restaurants.size()));
        List<Delivery> deliveries = r.getDeliveries();
        int deliveriesNum = deliveries.size();
        if(deliveriesNum < 2)
            throw new FailedShuffleException();
        int i = random.nextInt(deliveriesNum);
        int j = RandomGenerator.generateRandomExclusive(deliveriesNum, i);
        shuffler.swap(deliveries.get(i), deliveries.get(j));
    }

    /**
     * @throws FailedShuffleException
     * swap between 2 deliveries in 2 different restaurants
     */
    private void swapRestaurants() throws FailedShuffleException {
        int i = random.nextInt(restaurants.size());
        int j = RandomGenerator.generateRandomExclusive(restaurants.size(), i);
        Restaurant r1 = restaurants.get(i);
        Restaurant r2 = restaurants.get(j);
        shuffler.swap(r1, r2);
    }

    /**
     * @throws FailedShuffleException
     * move between 2 deliveries in 1 restaurant
     */
    private void moveDeliveries() throws FailedShuffleException {
        Restaurant r = restaurants.get(random.nextInt(restaurants.size()));
        List<Delivery> deliveries = r.getDeliveries();
        int deliveriesNum = deliveries.size();
        if(deliveriesNum < 2)
            throw new FailedShuffleException();
        int i = random.nextInt(deliveriesNum);
        int j = RandomGenerator.generateRandomExclusive(deliveriesNum, i);
        shuffler.move(deliveries.get(i), deliveries.get(j));
    }

    /**
     * @throws FailedShuffleException
     * move between 2 deliveries in 2 different restaurants
     */
    private void moveRestaurants() throws FailedShuffleException {
        final int sizeOfRestaurants = restaurants.size();
        int i = random.nextInt(sizeOfRestaurants);
        int j = RandomGenerator.generateRandomExclusive(sizeOfRestaurants, i);
        Restaurant r1 = restaurants.get(i);
        Restaurant r2 = restaurants.get(j);
        shuffler.move(r1, r2);
    }
}
