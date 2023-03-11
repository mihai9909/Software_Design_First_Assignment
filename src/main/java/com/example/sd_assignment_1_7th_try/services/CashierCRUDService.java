package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.models.User;
import com.example.sd_assignment_1_7th_try.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CashierCRUDService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public String findCashiers(){
        return userRepository.findUserByRole("Cashier").toString();
    }

    public boolean updateCashier(String id, String email, String password, String role){
        User user = userRepository.findUserById(Long.parseLong(id));
        if(password.length() < 5 || user == null || emailTaken(user, email) || !user.getRole().equals("Cashier")) // check if valid password or email is taken
            return false;
        user.setEmail(email);
        user.setPassword(passwordEncoder.hashPassword(password));
        user.setRole(role);
        userRepository.save(user);
        return true;
    }

    public boolean createCashier(String email, String password) {
        if (password.length() < 5 || emailTaken(null, email)) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.hashPassword(password));
        user.setRole("Cashier");
        userRepository.save(user);
        return true;
    }

    public boolean deleteCashier(String id){
        User user = userRepository.findUserById(Long.parseLong(id));
        if(user == null || !user.getRole().equals("Cashier"))
            return false;

        userRepository.delete(user);
        return true;
    }

    private boolean emailTaken(User user, String email){
        User userInDB = userRepository.findByEmail(email);
        return userInDB != null && (user == null || !userInDB.getEmail().equals(user.getEmail()));
    }

}
