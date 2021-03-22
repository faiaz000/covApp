/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "outcome")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Outcome.findAll", query = "SELECT o FROM Outcome o"),
    @NamedQuery(name = "Outcome.findById", query = "SELECT o FROM Outcome o WHERE o.id = :id"),
    @NamedQuery(name = "Outcome.findByArds", query = "SELECT o FROM Outcome o WHERE o.ards = :ards"),
    @NamedQuery(name = "Outcome.findByPneumonia", query = "SELECT o FROM Outcome o WHERE o.pneumonia = :pneumonia"),
    @NamedQuery(name = "Outcome.findByCardiacArrest", query = "SELECT o FROM Outcome o WHERE o.cardiacArrest = :cardiacArrest"),
    @NamedQuery(name = "Outcome.findBySecondaryInfection", query = "SELECT o FROM Outcome o WHERE o.secondaryInfection = :secondaryInfection"),
    @NamedQuery(name = "Outcome.findByStroke", query = "SELECT o FROM Outcome o WHERE o.stroke = :stroke"),
    @NamedQuery(name = "Outcome.findByHeartAttack", query = "SELECT o FROM Outcome o WHERE o.heartAttack = :heartAttack"),
    @NamedQuery(name = "Outcome.findByDate", query = "SELECT o FROM Outcome o WHERE o.date = :date")})
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ards")
    private boolean ards;
    @Basic(optional = false)
    @Column(name = "pneumonia")
    private boolean pneumonia;
    @Basic(optional = false)
    @Column(name = "cardiac_arrest")
    private boolean cardiacArrest;
    @Basic(optional = false)
    @Column(name = "secondary_infection")
    private boolean secondaryInfection;
    @Basic(optional = false)
    @Column(name = "stroke")
    private boolean stroke;
    @Basic(optional = false)
    @Column(name = "heart_attack")
    private boolean heartAttack;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;

    public Outcome() {
    }

    public Outcome(Integer id) {
        this.id = id;
    }

    public Outcome(Integer id, boolean ards, boolean pneumonia, boolean cardiacArrest, boolean secondaryInfection, boolean stroke, boolean heartAttack, Date date) {
        this.id = id;
        this.ards = ards;
        this.pneumonia = pneumonia;
        this.cardiacArrest = cardiacArrest;
        this.secondaryInfection = secondaryInfection;
        this.stroke = stroke;
        this.heartAttack = heartAttack;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getArds() {
        return ards;
    }

    public void setArds(boolean ards) {
        this.ards = ards;
    }

    public boolean getPneumonia() {
        return pneumonia;
    }

    public void setPneumonia(boolean pneumonia) {
        this.pneumonia = pneumonia;
    }

    public boolean getCardiacArrest() {
        return cardiacArrest;
    }

    public void setCardiacArrest(boolean cardiacArrest) {
        this.cardiacArrest = cardiacArrest;
    }

    public boolean getSecondaryInfection() {
        return secondaryInfection;
    }

    public void setSecondaryInfection(boolean secondaryInfection) {
        this.secondaryInfection = secondaryInfection;
    }

    public boolean getStroke() {
        return stroke;
    }

    public void setStroke(boolean stroke) {
        this.stroke = stroke;
    }

    public boolean getHeartAttack() {
        return heartAttack;
    }

    public void setHeartAttack(boolean heartAttack) {
        this.heartAttack = heartAttack;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Outcome)) {
            return false;
        }
        Outcome other = (Outcome) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Outcome[ id=" + id + " ]";
    }
    
}
