package best.project.myblog.rest;

import best.project.myblog.models.User;
import best.project.myblog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class restController {

    private final UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Optional<User> user = userService.getById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("User with this id is not found!");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public ResponseEntity<?> removeById(@PathVariable Integer id){
        Boolean success = userService.deleteById(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Success!");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users:write')")
    public ResponseEntity<?> createNewUser(@RequestBody User user){
        boolean success = userService.add(user);
        if(!success){
            return ResponseEntity.badRequest().body("Wrong data!");
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('users:write')")
    public ResponseEntity<?> updateUser(@PathVariable User storageUser,
                                        @RequestBody User user){
        User updatedUser = userService.updateUser(user, storageUser);
        if(updatedUser == null){
            return ResponseEntity.badRequest().body("Wrong data!");
        }
        return ResponseEntity.ok(updatedUser);
    }
}
