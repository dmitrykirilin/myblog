package best.project.myblog.models;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor @NoArgsConstructor
@ToString(of = {"roleName"})
public class Role{

    private Set<Permission> permissions;

    @NonNull
    private String roleName;

    public Role(@NonNull String roleName) {
        this.roleName = roleName;
        this.permissions = new HashSet<>();
        if(roleName.equals("USER")){
            permissions.add(Permission.USER_READ);
        }
        if(roleName.equals("ADMIN")){
            permissions.addAll(Arrays.asList(Permission.USER_READ, Permission.USER_WRITE));
        }
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}