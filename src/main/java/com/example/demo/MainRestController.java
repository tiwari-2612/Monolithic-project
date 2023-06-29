package com.example.demo;

import com.example.demo.UserdetailRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainRestController {


    @Autowired
    UserdetailRepository userdetailRepository;

    @GetMapping("dummy")
    public Userdetail dummydetail() {
        Userdetail userdetail = new Userdetail();
        userdetail.setUsername("tiwarri@2612");
        userdetail.setEmail("uttu3101");
        userdetail.setFname("Utkarsh");
        userdetail.setLname("Tiwari");
        userdetail.setPhone("9910002560 ");
        return userdetail;
    }

    @PostMapping("getuserbyemail")
    public ResponseEntity<Userdetail> getUserdetailsByEmail(@RequestParam("email") String email){
        if (userdetailRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>(userdetailRepository.findByEmail(email).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}