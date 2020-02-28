package se.ecutb.spring_data_practice.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.ecutb.spring_data_practice.entity.Address;
import se.ecutb.spring_data_practice.entity.AppUser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired AppUserRepository testObject;
    @Autowired TestEntityManager em;

    private List<AppUser> data(){
        return Arrays.asList(
            new AppUser("test@test.com","Test Testsson","test123", null),
            new AppUser("test1@test.com", "Test2, Testsson", "test456",null)
        );
    }

    private int testAddressId;
    private AppUser testUser;

    @BeforeEach
    void setUp() {
        Address address = em.persist(new Address("Test street 1", "12345", "Test city"));
        testAddressId = address.getAddressId();

        List<AppUser> persisted = data().stream()
                .map(testObject::save)
                .collect(Collectors.toList());
        persisted.forEach(appUser -> appUser.setAddress(address));
        testUser = testObject.save(new AppUser("nisse@gmail.com", "Nisse Nys", "1234", new Address("TDD avenue", "12355", "Test city")));
        em.flush();
    }

    @Test
    public void given_email_findByEmailIgnoreCase_should_return_optional_isPresent(){
        String email = "NISSE@gmail.com";
        Optional<AppUser> result = testObject.findByEmailIgnoreCase(email);

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    public void given_email_findByEmailIgnoreCaseWithQuery_should_return_optional_isPresent(){
        String email = "NISSE@gmail.com";
        Optional<AppUser> result = testObject.findByEmailIgnoreCaseWithQuery(email);

        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    public void given_email_and_password_findByEmailIgnoreCaseAndPassword_return_optional_isPresent(){
        String email = "Nisse@gmail.com";
        String password = "1234";

        Optional<AppUser> result = testObject.findByEmailIgnoreCaseAndPassword(email, password);
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    public void given_email_and_password_findByEmailIgnoreCaseAndPasswordWithQuery_return_optional_isPresent(){
        String email = "Nisse@gmail.com";
        String password = "1234";

        Optional<AppUser> result = testObject.findByEmailIgnoreCaseAndPasswordWithQuery(email, password);
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }

    @Test
    public void given_name_findByNameContainsIgnoreCase_should_return_list_size_2(){
        String name = "test";
        List<AppUser> result = testObject.findByNameContainsIgnoreCase(name);

        assertEquals(2, result.size());
    }

    @Test
    public void given_name_findByNameContainsIgnoreCaseWithQuery_should_return_list_size_2(){
        String name = "test";
        List<AppUser> result = testObject.findByNameContainsIgnoreCaseWithQuery(name);

        assertEquals(2, result.size());
    }

    @Test
    public void given_testAddressId_findByAddressAddressId_should_return_list_size_2(){
        List<AppUser> result = testObject.findByAddressAddressId(testAddressId);
        assertEquals(2, result.size());
    }

    @Test
    public void given_testAddressId_findByAddressAddressIdWithQuery_should_return_list_size_2(){
        List<AppUser> result = testObject.findByAddressAddressIdWithQuery(testAddressId);
        assertEquals(2, result.size());
    }

    @Test
    public void given_city_findByAddressCityIgnoreCase_should_return_list_size_3(){
        String city = "test city";
        List<AppUser> appUsers = testObject.findByAddressCityIgnoreCase(city);
        assertEquals(3, appUsers.size());
    }

    @Test
    public void given_city_findByAddressCityIgnoreCaseWithQuery_should_return_list_size_3(){
        String city = "test city";
        List<AppUser> appUsers = testObject.findByAddressCityIgnoreCaseWithQuery(city);
        assertEquals(3, appUsers.size());
    }




}
