package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthorizationServiceImp implements AuthorizationService{
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    public User authorize(String token) throws Exception {
        boolean isValidToken = jwtTokenUtil.verify(token);
        if(!isValidToken){
            throw new Exception();
        }
        if(jwtTokenUtil.getExpirationDate(token).before(new Date())){
            throw new Exception();
        }
        String subject = jwtTokenUtil.getSubject(token);

        User userDB = userService.findByUsername(subject);
        if(userDB==null) {
            throw new Exception();
        }
        return userDB;
    }
}
