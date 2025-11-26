package com.example.taskManager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel  {

    
    private String firstName;
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true , nullable = false)
    private String email;
    @NotBlank       // many users can have same password and it is not a security issue untile you are hashing it properly
    private String password; // store hashed passwords in real apps

    private boolean active = true;

    //Persist - help to create new user with new role , Merge - help to update role in all places if changed
    @ManyToMany(fetch = FetchType.EAGER , cascade = { CascadeType.PERSIST , CascadeType.MERGE }  )
    @JoinTable( name = "users_roles" ,
                joinColumns = @JoinColumn(name = "user_id") ,
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles ;

    //orphanRemoval = true  -> for automatic cleanup from the list when a task is removed
    @OneToMany(mappedBy = "assignedTo" , cascade = CascadeType.ALL , orphanRemoval = true ,fetch = FetchType.LAZY)
    @JsonBackReference              //you want this side suppress
    private List<Task> tasks ;
    
    public boolean isActive() {
        return active;
    }

}