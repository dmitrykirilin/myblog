package best.project.myblog;

import best.project.myblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyblogApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addStartedUsers();
    }
}
