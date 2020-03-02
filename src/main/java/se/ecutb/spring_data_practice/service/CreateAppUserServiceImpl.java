package se.ecutb.spring_data_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.spring_data_practice.data.AppUserRepository;
import se.ecutb.spring_data_practice.entity.Address;
import se.ecutb.spring_data_practice.entity.AppUser;

import java.util.Arrays;
import java.util.Objects;

@Service
public class CreateAppUserServiceImpl implements CreateAppUserService {

    private AppUserRepository appUserRepository;

    @Autowired
    public CreateAppUserServiceImpl(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser createAndSave(String email, String name, String password, String passwordConfirmation, Address address){
        if(hasNulls(email,name,password,passwordConfirmation)){
            throw new RuntimeException("One or several parameters is null");
        }
        if(appUserRepository.findByEmailIgnoreCase(email).isPresent()){
            throw new RuntimeException("Email is already in use, please choose another one");
        }
        if(!password.equals(passwordConfirmation)){
            throw new RuntimeException("Password confirmation didn't match actual password");
        }

        AppUser appUser = new AppUser(email,name,password,address);
        return appUserRepository.save(appUser);
    }

    public static boolean hasNulls(Object...objects){
        return Arrays.stream(objects)
                .anyMatch(obj -> Objects.isNull(obj));
    }

}
