package se.ecutb.spring_data_practice.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.ecutb.spring_data_practice.entity.Address;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository testObject;

    private List<Address> data(){
        return Arrays.asList(
                new Address("Testgatan 1", "12345", "Test town"),
                new Address( "Skogen", "43969", "Test town"),
                new Address("Skogsgläntan 3", "57468" , "Växjö")
        );
    }

    @BeforeEach
    void setUp() {
        data().forEach(testObject::save);
    }

    @Test
    public void given_city_findByCityIgnoreCase_return_list_size_2(){
        String city = "test town";
        List<Address> addresses = testObject.findByCityIgnoreCase(city);
        assertEquals(2, addresses.size());
    }

    @Test
    public void given_city_findByCityWithQuery_return_list_size_2(){
        String city = "test town";
        List<Address> addresses = testObject.findByCityWithQuery(city);
        assertEquals(2, addresses.size());
    }

}
