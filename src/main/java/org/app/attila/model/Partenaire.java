package org.app.attila.model;

public class Partenaire {

    private int id;
    private String nom_partenaire;
    private byte[] logo;
    private String contact;

    public Partenaire() {

    }

    public Partenaire(int id, String nom_partenaire, byte[] logo, String contact) {
        this.id = id;
        this.nom_partenaire = nom_partenaire;
        this.logo = logo;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_partenaire() {
        return nom_partenaire;
    }

    public void setNom_partenaire(String nom_partenaire) {
        this.nom_partenaire = nom_partenaire;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
