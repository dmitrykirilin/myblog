package best.project.myblog.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor @RequiredArgsConstructor @NoArgsConstructor
@Table(name = "users")
@ToString(of = {"username", "password"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
//    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @NonNull
//    @Length(min = 3, message = "From 3 characters!")
    private String password;

    //
//    @Transient
//    @NotBlank(message = "Password confirmation cannot be empty!")
//    private String password2;
//
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    private Role role;
//
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Post> posts;
//
////    @ManyToMany(fetch = FetchType.EAGER)
////    @JoinTable(name = "user_subscriptions",
////            joinColumns = {@JoinColumn(name = "channel_id")},
////            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")}
////    )
////    private Set<User> subscribers = new HashSet<>();
////
////    @ManyToMany(fetch = FetchType.EAGER)
////    @JoinTable(name = "user_subscriptions",
////                joinColumns = {@JoinColumn(name = "subscriber_id")},
////                inverseJoinColumns = {@JoinColumn(name = "channel_id")}
////                )
////    private Set<User> subscriptions = new HashSet<>();
//
//    public Set<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(Set<Post> posts) {
//        this.posts = posts;
//    }
////
////    public Set<User> getSubscribers() {
////        return subscribers;
////    }
////
////    public void setSubscribers(Set<User> subscribers) {
////        this.subscribers = subscribers;
////    }
////
////    public Set<User> getSubscriptions() {
////        return subscriptions;
////    }
////
////    public void setSubscriptions(Set<User> subscriptions) {
////        this.subscriptions = subscriptions;
////    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }
//
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
//
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
//
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
            authorities.addAll(role.getAuthorities());
        return authorities;
    }

//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getPassword2() {
//        return password2;
//    }
//
//    public void setPassword2(String password2) {
//        this.password2 = password2;
//    }
//
//    @Transactional
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        User user = (User) o;
//        return Objects.equals(id, user.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
