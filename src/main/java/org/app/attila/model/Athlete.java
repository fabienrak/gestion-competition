package org.app.attila.model;

import org.app.attila.util.ValueRange;

public class Athlete {

    private int id;
    private String nom;
    private String prenom;
    private String age;
    private String sexe;
    private String poids;
    private String club;
    private String ceinture;
    private String combat_gi;  // NOGI ou GI
    private String combat_nogi;  // NOGI ou GI
    private String categorie;
    private String ID_ATH;

    public Athlete(){

    }

    public Athlete(int id, String nom, String prenom, String age, String sexe, String poids, String club, String ceinture, String combat_gi, String combat_nogi, String categorie, String ID_ATH) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.poids = poids;
        this.club = club;
        this.ceinture = ceinture;
        this.combat_gi = combat_gi;
        this.combat_nogi = combat_nogi;
        this.categorie = categorie;
        this.ID_ATH = ID_ATH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getCeinture() {
        return ceinture;
    }

    public void setCeinture(String ceinture) {
        this.ceinture = ceinture;
    }

    public String getCombat_gi() {
        return combat_gi;
    }

    public void setCombat_gi(String combat_gi) {
        this.combat_gi = combat_gi;
    }

    public String getCombat_nogi() {
        return combat_nogi;
    }

    public void setCombat_nogi(String combat_nogi) {
        this.combat_nogi = combat_nogi;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getID_ATH() {
        return ID_ATH;
    }

    public void setID_ATH(String ID_ATH) {
        this.ID_ATH = ID_ATH;
    }
}

