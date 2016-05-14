package solver.setting.random;

import java.util.Random;

/**
 * Created by Jakub Kudzia on 5/12/16.
 */
public class RandomGenerator {

    private static final Random random = new Random();

    static int generateRandomExclusive(int bound, int excluded){
        int r;
        if((random.nextInt(2) == 0 || bound == excluded + 1) && excluded != 0)
            r = random.nextInt(excluded);
        else
            r = random.nextInt(bound - excluded -1) + excluded + 1;
        return r;
    }
}
