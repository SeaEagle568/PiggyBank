package com.seaeagle.pbmicroservice.persistence;

import com.seaeagle.pbmicroservice.entites.PiggyBank;
import com.seaeagle.pbmicroservice.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBManager {
    private PiggyBankRepository pbRepo;
    private UserRepository userRepo;

    @Autowired
    public void setPbRepo(PiggyBankRepository pbRepo) {
        this.pbRepo = pbRepo;
    }

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void savePB(PiggyBank pb) {
        pbRepo.save(pb);
    }

    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean userExists(String username) {
        return userRepo.existsByUsername(username);
    }
}
