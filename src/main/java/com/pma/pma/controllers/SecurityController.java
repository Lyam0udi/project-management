package com.pma.pma.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pma.pma.dao.UserAccountRepository;
import com.pma.pma.entities.UserAccount;

@Controller
public class SecurityController {

    @Autowired
    UserAccountRepository accountRepo;

    @Autowired
    BCryptPasswordEncoder bCryptEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        UserAccount userAccount = new UserAccount();
        model.addAttribute("userAccount", userAccount);

        return "security/register";
    }

    @PostMapping("/register/save")
    public String saveUser(Model model, UserAccount user) {
        user.setPassword(bCryptEncoder.encode(user.getPassword()));
        user.setRole("user"); // Set the role to "user"
        accountRepo.save(user);

        // Check if it's the admin account
        if (isAdminAccount(user)) {
            UserAccount adminAccount = new UserAccount();
            adminAccount.setUserName("admin");
            adminAccount.setPassword(bCryptEncoder.encode("admin")); // Set the admin password
            adminAccount.setRole("ADMIN"); // Set the role to "admin"
            accountRepo.save(adminAccount);
        }

        return "redirect:/login";
    }

    private boolean isAdminAccount(UserAccount user) {
        String adminUsername = "admin"; // Define the admin username
        return user.getUserName().equals(adminUsername);
    }

    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // User is already logged in, redirect to dashboard or desired page
            return "redirect:/dashboard";
        }

        // Add necessary model attributes if needed

        // Return the login page view name
        return "security/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Implement your logout logic here

        // Invalidate the session
        request.getSession().invalidate();

        // Redirect the user to the desired page after logout
        return "redirect:/login";
    }
}
