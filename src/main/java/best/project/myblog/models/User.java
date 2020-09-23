package best.project.myblog.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @RequiredArgsConstructor @NoArgsConstructor
@Table(name = "users")
@ToString(of = {"email", "firstName", "lastName"})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
//    @NotBlank(message = "Username cannot be empty!")
    private String email;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
//    @Length(min = 3, message = "From 3 characters!")
    private String password;

    //
//    @Transient
//    @NotBlank(message = "Password confirmation cannot be empty!")
//    private String password2;
//
    @NonNull
    @Column(name = "role")
    private String role;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
//
//    @Override
//    @JsonIgnore
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new HashSet<>();
//            authorities.addAll(role.getAuthorities());
//        return authorities;
//    }
}