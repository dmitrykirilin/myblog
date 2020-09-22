package best.project.myblog.services;

import best.project.myblog.models.Status;
import best.project.myblog.models.User;
import best.project.myblog.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean add(User user) {
        Optional<User> userFromDB = userRepo.findByEmail(user.getEmail());
        if(userFromDB.isPresent()){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

//        if(!StringUtils.isEmpty(user.getEmail())){
//            String message = String.format(
//                    "Hello, %s, \n" +
//                            "Welcome to myApp. Please, check this link to activate your account: http://localhost:8081/activate/%s",
//                    user.getUsername(),
//                    user.getActivationCode()
//            );
//
//            mailSender.send(user.getEmail(), "Activation code", message);
//        }
        return true;
    }

    @Transactional
    public boolean addStartedUsers(){
        if(userRepo.findAll().isEmpty()){
            userRepo.save(new User("admin@mail.com", "Admin", "Adminin",
                                    passwordEncoder.encode("admin"),
                                    "ADMIN", Status.ACTIVE));
            userRepo.save(new User("user@mail.com", "User", "Userov",
                                    passwordEncoder.encode("user"),
                                    "USER", Status.ACTIVE));
            return true;
        }
        return false;
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public Optional<User> getById(Integer id) {
        return userRepo.findById(id);
    }

    @Transactional
    public User updateUser(User user, User storageUser) {
        BeanUtils.copyProperties(user, storageUser, "id", "roles");
        return userRepo.save(storageUser);
    }

    @Transactional
    public Boolean deleteById(Integer id) {
        try{
            userRepo.deleteById(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

//    public User findByUsername(String username){
//        return userRepo.findByUsername(username);
//    }


//    @Transactional
//    public void subscribe(User currentUser, User user) {
//        currentUser.getSubscriptions().add(user);
//
//        userRepo.save(user);
//    }
//
//    @Transactional
//    public void unsubscribe(User currentUser, User user) {
//        currentUser.getSubscriptions().remove(user);
//
//        userRepo.save(user);
//    }
}
