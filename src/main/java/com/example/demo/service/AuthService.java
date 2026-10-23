package com.example.demo.service;

import com.example.demo.config.JwtUtils;
import com.example.demo.model.Role;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils; // Injection de JwtUtils ici

    public AuthService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Utilisateur inscrireUtilisateur(String email, String motDePasseBrut) {
        if (utilisateurRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }

        Utilisateur nouvelUtilisateur = new Utilisateur();
        nouvelUtilisateur.setEmail(email);
        nouvelUtilisateur.setMotDePasse(passwordEncoder.encode(motDePasseBrut));
        nouvelUtilisateur.setRole(Role.SPORTIF);

        return utilisateurRepository.save(nouvelUtilisateur);
    }

    // Méthode de connexion simplifiée
    public String connecterUtilisateur(String email, String motDePasseBrut) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect."));

        if (!passwordEncoder.matches(motDePasseBrut, utilisateur.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect.");
        }

        return jwtUtils.generateJwtToken(utilisateur.getEmail());
    }
}