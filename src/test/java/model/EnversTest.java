package model;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EnversTest extends AbstractTest {

    @DataProvider(name = "pus")
    public static Object[][] primeNumbers() {
        return new Object[][] { //
                // { "pu-test1", ShareEntity.class } , //
                { "pu-test2", ShareAudited.class } //
        };
    }

    @Test(dataProvider = "pus")
    public void test3(String pu, Class<Share> c) throws InstantiationException, IllegalAccessException {
        init(pu);
        
        // test setup
        Shareholder alice = new Shareholder("alice");
        Shareholder bob = new Shareholder("bob");
        Share adidas = c.newInstance();
        adidas.setName("adidas");
        Share basf = c.newInstance();
        basf.setName("BASF");
        Share volkswagen = c.newInstance();
        volkswagen.setName("VW AG");
        

        adidas.setValue(BigDecimal.valueOf(180));
        basf.setValue(BigDecimal.valueOf(90));
        volkswagen.setValue(BigDecimal.valueOf(136));
        
        alice.addShare(adidas);
        alice.addShare(basf);
        bob.addShare(volkswagen);
        
        save(alice);
        save(bob);

        adidas = entityManager.createQuery("select s from "+c.getSimpleName()+" s where s.name='adidas'", c).getSingleResult();
        adidas.setValue(BigDecimal.valueOf(183));
        save(adidas);
    }

}
