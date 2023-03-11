package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.services.CashierCRUDService;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import com.example.sd_assignment_1_7th_try.services.ShowCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final CashierCRUDService cashierCRUDService;

    @GetMapping("/cashiers")
    public ResponseEntity<String> getCashiers(){
        authorizeAdmin();

        return ResponseEntity.ok(cashierCRUDService.findCashiers());
    }

    @PutMapping("/update/{cashier_id}")
    public ResponseEntity<String> updateCashier(@PathVariable("cashier_id") String id,
                                                @RequestParam("email") String email,
                                                @RequestParam("password") String password,
                                                @RequestParam("role") String role){
        authorizeAdmin();

        if(cashierCRUDService.updateCashier(id, email, password, role))
            return ResponseEntity.ok("User updated successfully!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please verify credentials and id");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCashier(@RequestParam("email") String email,
                                                @RequestParam("password") String password){
        authorizeAdmin();

        if(cashierCRUDService.createCashier(email, password))
            return ResponseEntity.ok().body("Cashier created successfully!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please verify credentials and id");
    }

    @DeleteMapping("/delete/{cashier_id}")
    public ResponseEntity<String> createCashier(@PathVariable("cashier_id") String id){
        authorizeAdmin();

        if(cashierCRUDService.deleteCashier(id))
            return ResponseEntity.ok().body("Cashier deleted successfully!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cashier with id " + id + " does not exist");
    }

    private void authorizeAdmin() {
        try{
            sessionService.isLoggedInAsAdmin();
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
