package com.example.adoptaunamascotaapp.repository;

public class SessionManager {
    private static SessionManager instance;
    private long userId;

    private SessionManager() {
        // Constructor privado para evitar la creación de múltiples instancias
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

