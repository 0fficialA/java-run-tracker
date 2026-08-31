package controller;

import java.util.HashMap;
import dao.UserSQL;
import model.User;

public class UserController {
    private UserSQL repo;

    public UserController() {
        this.repo = new UserSQL();
    }

    // Returns a User object if valid, or null if auth fails
    public User authenticateUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null;
        }

        HashMap<String, Object> credentials = repo.retrieveCredentialsByUsername(username.trim());

        if (credentials != null && credentials.get("password").equals(password)) {
            int id = (int) credentials.get("id");
            String name = (String) credentials.get("name");
            double miles = (double) credentials.get("miles");

            return new User(id, name, miles);
        }

        return null; // Invalid credentials or user not found
    }

    public boolean registerUser(String name, String username, String password) {
        if (name.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        User newUser = new User(name.trim());
        return repo.createUserWithCredentials(newUser, username.trim(), password);
    }
}