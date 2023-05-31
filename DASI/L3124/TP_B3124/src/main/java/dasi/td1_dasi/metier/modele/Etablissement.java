/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.metier.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author cchabal
 */
@Entity
public class Etablissement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @Column(unique=true)
    private String code;
    private String secteur;
    private String codeCommune;
    private String nomCommunce;
    private String codeDepartement;
    private String nomDepartement;
    private String academie;
    private String ips;
    
    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + ", nom=" + nom + ", code=" + code + ", secteur=" + secteur + ", codeCommune=" + codeCommune + ", nomCommunce=" + nomCommunce + ", codeDepartement=" + codeDepartement + ", nomDepartement=" + nomDepartement + ", academie=" + academie + ", ips=" + ips + '}';
    }

    public Etablissement(String nom, String code, String secteur, String codeCommune, String nomCommunce, String codeDepartement, String nomDepartement, String academie, String ips) {
        this.nom = nom;
        this.code = code;
        this.secteur = secteur;
        this.codeCommune = codeCommune;
        this.nomCommunce = nomCommunce;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
        this.academie = academie;
        this.ips = ips;
    }

    public Etablissement() {
    }
    
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getNomCommunce() {
        return nomCommunce;
    }

    public void setNomCommunce(String nomCommunce) {
        this.nomCommunce = nomCommunce;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    public String getAcademie() {
        return academie;
    }

    public void setAcademie(String academie) {
        this.academie = academie;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }
    
}
