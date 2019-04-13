package com.planyourlifeapp.api.controllers;


import com.planyourlifeapp.api.models.Feed;
import com.planyourlifeapp.api.models.User;
import com.planyourlifeapp.api.repository.FeedRepository;
import com.planyourlifeapp.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value="/api/v1/user")
public class UserController {

    private UserRepository userRepository;
    private FeedRepository feedRepository;

    @Autowired
    UserController(UserRepository userRepository, FeedRepository feedRepository){
        this.userRepository = userRepository;
        this.feedRepository = feedRepository;
    }
    //Controller for submitting feeds
    @PostMapping(value = "/feed")
    public ResponseEntity<?> createFeed(@RequestBody Feed feed, Principal principal){
        //get the user from the database by the principals details
        User user = userRepository.getByUsername(principal.getName());
        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);
        }
        //set new feed
        user.getFeed().add(feed);
        //save
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Get users feed
    @GetMapping(value = "/feed")
    public ResponseEntity<?> getUserFeed(Principal principal){
        User user = userRepository.getByUsername(principal.getName());
        //check if user exists
        if(user == null) return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);
        //return the feed
        return new ResponseEntity<>(user.getFeed(),HttpStatus.OK);
    }
    //marks a feed as done
    @GetMapping(value = "/feed/modify")
    public ResponseEntity<?> doneFeed(Principal principal, @RequestParam long id, @RequestParam String mod){
        Feed feed = feedRepository.getFeedById(id);
        if(feed == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<User> users = feed.getUsers();
        for(User user : users){
            //if one username equals the requesters name
            if(user.getUsername().equals(principal.getName())){
                if (mod.equals("done")){
                    feed.setDone(true);
                    feedRepository.save(feed);
                }
                else if(mod.equals("del")){
                    feedRepository.delete(feed);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        //if its not okay, return unauthorized
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
