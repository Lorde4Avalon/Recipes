package com.recipes.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userId")
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @Column
    private String username;
    @Column
    @NotBlank
    @Pattern(regexp = ".{8,}")
    private String password;
    @Column
    @Email(regexp = ".+@.+\\..+")
    private String email;
    @Column
//    @Value("${isActive:true}") //Default is enable
    private boolean isActive;
    @Column
    private String roles;
    @Column(name = "date")
    private LocalDateTime dateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Recipe> recipes = new ArrayList<>();
}
