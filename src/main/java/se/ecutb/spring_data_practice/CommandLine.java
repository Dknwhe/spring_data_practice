package se.ecutb.spring_data_practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ecutb.spring_data_practice.entity.Address;
import se.ecutb.spring_data_practice.entity.AppUser;
import se.ecutb.spring_data_practice.service.CreateAppUserService;

@Component
public class CommandLine implements CommandLineRunner {

    private CreateAppUserService createAppUserService;

    @Autowired
    public CommandLine(CreateAppUserService createAppUserService) {
        this.createAppUserService = createAppUserService;
    }

    @Override
    public void run(String... args) throws Exception {
        AppUser user = createAppUserService.createAndSave(
                "test@test.com",
                "Test Testsson",
                "1234",
                "1234",
                new Address("Test street 1", "12345","Testville")
        );
        System.out.println(user);
    }
}
