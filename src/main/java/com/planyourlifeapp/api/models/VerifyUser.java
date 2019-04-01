package com.planyourlifeapp.api.models;


import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;

@Data
@Entity
@Table(name="verify_user")
public class VerifyUser {
    public VerifyUser(){}
    public VerifyUser(User user){
        this.user = user;
        this.verifyKey = RandomStringUtils.randomAlphabetic(40);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String verifyKey;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
