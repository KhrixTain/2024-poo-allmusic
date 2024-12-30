package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.util.JwtTokenUtil;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService{
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String authenticate(User user) throws Exception {
        User userDB = userService.findByUsername(user.getUsername());
        if(userDB == null) {
            throw new Exception();
        }
        if(!passwordEncoder.verify(user.getPassword(), userDB.getPassword())){
            throw new Exception();
        }
        return jwtTokenUtil.generateToken(user.getUsername());
    }
}
