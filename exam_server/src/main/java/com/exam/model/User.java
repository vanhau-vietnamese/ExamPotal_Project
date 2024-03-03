package com.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@NamedQuery(
        name = "User.getAllUser",
        query = "SELECT new com.exam.dto.response.UserResponse(u.name, u.username, u.password, u.email, u.role) " +
                "FROM User u"
)
@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long userId;
    private String name;
    @Column(name = "UserName", nullable = false, unique = true)
    private String username;
    @Column(name = "PassWord", nullable = false, unique = true)
    private String password;
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    // user use 1 role
    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "createBy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Quiz> quizzes = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
