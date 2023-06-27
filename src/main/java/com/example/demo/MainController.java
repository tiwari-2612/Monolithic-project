package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    CredentialRepository credentialRepository;
    @Autowired
    UserdetailRepository userdetailRepository;
    @Autowired
    UsertypelinkRepository usertypelinkRepository;

    @GetMapping("/")
    public String getLandingPage() {
        return "landingpage";
    }

    @GetMapping("/save")
    public String saveCredential() {
        Credential cd = new Credential();
        cd.setUsername("utkarsh");
        cd.setPassword("2025");
        credentialRepository.save(cd);
        return "New repo saved";

    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        session.setAttribute("username",username);
        credentialRepository.save(credential);
        return "dashboard";

    }

    @PostMapping("/login")
            public String signin(@RequestParam("username") String username,
                @RequestParam("password") String password,
                HttpSession session, Model model){

            Optional<Credential> credential=credentialRepository.findById(username);

            if(credential.isPresent() && credential.get().getPassword().equals(password)){

                session.setAttribute("username", username);
                Optional<Userdetail> userdetail=userdetailRepository.findById(username);
                List<Usertypelink> usertypelinks=usertypelinkRepository.findAll();
                Optional<Usertypelink> usertypelink=usertypelinks.stream().filter(usertypelink1 -> usertypelink1.getUsername().equals(username)).findAny();
                if (userdetail.isPresent()){
                    if (usertypelink.isPresent() && usertypelink.get().getType().equals("Seller")){
                        model.addAttribute("userdetail",userdetail.get());
                        return "seller";
                    }else if (usertypelink.isPresent() && usertypelink.get().getType().equals("Buyer")){
                        model.addAttribute("userdetail",userdetail.get());
                        return "buyer";
                    }
                }
                else {
                    return "dashboard";
                }

            };

            return "landingpage";
        }


    @PostMapping("/detail")
    public String detail(@RequestParam("fname") String fname, @RequestParam("lname") String lname, @RequestParam("email") String email, @RequestParam("phone") String phone, HttpSession session, @RequestParam("type") String type, Model model) {
        Userdetail ud = new Userdetail();
        Usertypelink utl = new Usertypelink();
        Random random = new Random();
        ud.setUsername((String) session.getAttribute("username"));
        utl.setUsername((String) session.getAttribute("username"));
        ud.setFname(fname);
        ud.setLname(lname);
        ud.setEmail(email);
        ud.setPhone(phone);
        utl.setType(type);
        int x = random.nextInt(5);
        String id;
        utl.setId(id = String.valueOf(x));
        userdetailRepository.save(ud);
        usertypelinkRepository.save(utl);

        return "dashboard";


    }
}
