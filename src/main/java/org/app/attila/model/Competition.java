package org.app.attila.model;

import java.sql.Date;

public class Competition {

    private int id;
    private String titre_competition;
    private String desc_competition;
    private String lieu_competition;
    private Date date_debut;
    private Date date_fin;
    private String type_competition;
    private String organisateur;


    public Competition() {

    }

    public Competition(int id, String titre_competition, String desc_competition, String lieu_competition, Date date_debut, Date date_fin, String type_competition, String organisateur) {
        this.id = id;
        this.titre_competition = titre_competition;
        this.desc_competition = desc_competition;
        this.lieu_competition = lieu_competition;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.type_competition = type_competition;
        this.organisateur = organisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre_competition() {
        return titre_competition;
    }

    public void setTitre_competition(String titre_competition) {
        this.titre_competition = titre_competition;
    }

    public String getDesc_competition() {
        return desc_competition;
    }

    public void setDesc_competition(String desc_competition) {
        this.desc_competition = desc_competition;
    }

    public String getLieu_competition() {
        return lieu_competition;
    }

    public void setLieu_competition(String lieu_competition) {
        this.lieu_competition = lieu_competition;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getType_competition() {
        return type_competition;
    }

    public void setType_competition(String type_competition) {
        this.type_competition = type_competition;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }
}
