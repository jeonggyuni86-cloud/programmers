package container;

import bean.Bean;
import bean.CoffeeBean;
import service.CoffeeMaker;

public class CoffeeContainer {
    private final Bean bean = CoffeeBean.COLOMBIA;

    public CoffeeMaker getCoffeeMaker() {
        return new CoffeeMaker(bean);
    }
}
