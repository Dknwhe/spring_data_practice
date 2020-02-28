package se.ecutb.spring_data_practice.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.ecutb.spring_data_practice.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    //Hitta AppUser med specifik email.
    Optional<AppUser> findByEmailIgnoreCase(String email);

    @Query("SELECT user FROM AppUser user WHERE UPPER(user.email) = UPPER(:email)")
    Optional<AppUser> findByEmailIgnoreCaseWithQuery(@Param("email") String email);

    //Hitta AppUser med specifik email OCH lösenord.
    Optional<AppUser> findByEmailIgnoreCaseAndPassword(String email, String password);

    @Query("SELECT user FROM AppUser user WHERE UPPER(user.email) = UPPER(:email) AND user.password = :password")
    Optional<AppUser> findByEmailIgnoreCaseAndPasswordWithQuery(@Param("email") String email, @Param("password") String password);

    //Hitta AppUsers som har namn som innehåller en String.
    List<AppUser> findByNameContainsIgnoreCase(String name);

    @Query("SELECT user FROM AppUser user WHERE UPPER(user.name) LIKE UPPER(CONCAT('%',:name,'%'))")
    List<AppUser> findByNameContainsIgnoreCaseWithQuery(@Param("name") String name);

    //Hitta AppUsers som bor på samma address.
    List<AppUser> findByAddressAddressId(int addressId);

    @Query("SELECT user FROM AppUser user WHERE user.address.addressId = :addressId")
    List<AppUser> findByAddressAddressIdWithQuery(@Param("addressId") int addressId);

    //Hitta AppUsers som bor i samma stad.
    List<AppUser> findByAddressCityIgnoreCase(String city);

    @Query("SELECT user FROM AppUser user WHERE UPPER(user.address.city) = UPPER(:city)")
    List<AppUser> findByAddressCityIgnoreCaseWithQuery(@Param("city") String city);


}
