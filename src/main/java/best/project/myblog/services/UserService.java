package best.project.myblog.services;

import best.project.myblog.models.Permission;
import best.project.myblog.models.Role;
import best.project.myblog.models.User;
import best.project.myblog.repo.RoleRepo;
import best.project.myblog.repo.UserRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepo.findByUsername(username);
//        Hibernate.initialize(byUsername.getRole());
        return byUsername;
    }

    @Transactional
    public boolean add(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if(userFromDB != null){
            return false;
        }
        Role userRole = roleRepo.findByRoleName("USER");
        user.setRole(userRole);
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
            Role admin = roleRepo.save(new Role(new HashSet<Permission>(){{
                add(Permission.USER_READ);
                add(Permission.USER_WRITE);
            }}, "ADMIN"));
            Role user = roleRepo.save(new Role(Collections.singleton(Permission.USER_READ),
                    "USER"));
            userRepo.save(new User("admin", passwordEncoder.encode("admin"), admin));
            userRepo.save(new User("user", passwordEncoder.encode("user"), user));
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
