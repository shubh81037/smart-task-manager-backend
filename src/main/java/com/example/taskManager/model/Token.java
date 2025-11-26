package com.example.taskManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseModel 
{
    private String tokenValue;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // One token belongs to one user
    
    private Date expiryAt;

    public boolean isExpired() {
        return new Date().after(expiryAt);
    }
}
 