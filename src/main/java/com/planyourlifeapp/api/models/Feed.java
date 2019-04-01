package com.planyourlifeapp.api.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "feed")
    private List<User> users = new ArrayList<>();
    //1: reminder, 2: meeting
    private int type;
    private String title;
    private Date date;
    private Boolean done;
}
