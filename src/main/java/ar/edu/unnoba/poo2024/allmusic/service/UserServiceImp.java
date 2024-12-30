package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.repository.UserRepository;
import ar.edu.unnoba.poo2024.allmusic.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(User user) throws Exception {
        if(this.repository.findByUsername(user.getUsername())!=null) {
            throw new Exception();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        this.repository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.repository.findByUsername(username);
    }


}
