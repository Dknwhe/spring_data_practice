package se.ecutb.spring_data_practice.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @ManyToOne(
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
        )
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "owner"
    )
    private Collection<Car> ownedCars;

    public AppUser(int userId, String email, String name, String password, Address address) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public AppUser(String email, String name, String password, Address address) {
        this(0, email,name,password,address);
    }

    AppUser(){}

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<Car> getOwnedCars() {
        return ownedCars;
    }

    public void setOwnedCars(Collection<Car> ownedCars) {
        if(this.ownedCars == null) initializeCollection();
        if(ownedCars == null){
            ownedCars.forEach(car -> car.setOwner(null));
        }else {
            ownedCars.forEach(this::addCar);
        }
        this.ownedCars = ownedCars;
    }

    public boolean addCar(Car car){
        if(ownedCars == null) initializeCollection();
        if(car == null) return false;
        if(ownedCars.add(car)){
            car.setOwner(this);
            return true;
        }
        return false;
    }

    public boolean removeCar(Car car){
        if(ownedCars == null) initializeCollection();
        if(car == null) return false;
        if(ownedCars.remove(car)){
            car.setOwner(null);
            return true;
        }
        return false;
    }

    private void initializeCollection(){
        ownedCars = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return userId == appUser.userId &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(name, appUser.name) &&
                Objects.equals(password, appUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppUser{");
        sb.append("userId=").append(userId);
        sb.append(", email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }
}
