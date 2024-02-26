package com.exam.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private Long roleId;
    @Column(name = "RoleName", nullable = false, unique = true)
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<User> users = new HashSet<>();
}
