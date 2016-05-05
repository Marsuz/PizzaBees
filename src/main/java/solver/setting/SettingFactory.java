package solver.setting;

import model.Order;
import model.Setting;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public interface SettingFactory {

    Setting getInitialSetting(List<Order> orders);
}
