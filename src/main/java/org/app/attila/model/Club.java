package org.app.attila.model;

public class Club {

    private int id;
    private String nom_club;
    private String adresse;
    private int contact_id;

    public Club(int id, String nom_club, String adresse, int contact_id) {
        this.id = id;
        this.nom_club = nom_club;
        this.adresse = adresse;
        this.contact_id = contact_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_club() {
        return nom_club;
    }

    public void setNom_club(String nom_club) {
        this.nom_club = nom_club;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", nom_club='" + nom_club + '\'' +
                ", adresse='" + adresse + '\'' +
                ", contact_id=" + contact_id +
                '}';
    }
}
