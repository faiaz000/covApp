/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicine.findAll", query = "SELECT m FROM Medicine m"),
    @NamedQuery(name = "Medicine.findById", query = "SELECT m FROM Medicine m WHERE m.id = :id"),
    @NamedQuery(name = "Medicine.findByName", query = "SELECT m FROM Medicine m WHERE m.name = :name"),
    @NamedQuery(name = "Medicine.findByType", query = "SELECT m FROM Medicine m WHERE m.type = :type"),
    @NamedQuery(name = "Medicine.findByAmount", query = "SELECT m FROM Medicine m WHERE m.amount = :amount"),
    @NamedQuery(name = "Medicine.findByDay", query = "SELECT m FROM Medicine m WHERE m.day = :day"),
    @NamedQuery(name = "Medicine.findByNight", query = "SELECT m FROM Medicine m WHERE m.night = :night"),
    @NamedQuery(name = "Medicine.findByNoon", query = "SELECT m FROM Medicine m WHERE m.noon = :noon"),
    @NamedQuery(name = "Medicine.findByDuration", query = "SELECT m FROM Medicine m WHERE m.duration = :duration")})
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "amount")
    private String amount;
    @Basic(optional = false)
    @Column(name = "day")
    private boolean day;
    @Basic(optional = false)
    @Column(name = "night")
    private boolean night;
    @Basic(optional = false)
    @Column(name = "noon")
    private boolean noon;
    @Basic(optional = false)
    @Column(name = "duration")
    private int duration;
    @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Treatment treatmentId;

    public Medicine() {
    }

    public Medicine(Integer id) {
        this.id = id;
    }

    public Medicine(Integer id, String name, String type, String amount, boolean day, boolean night, boolean noon, int duration) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.day = day;
        this.night = night;
        this.noon = noon;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean getDay() {
        return day;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public boolean getNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public boolean getNoon() {
        return noon;
    }

    public void setNoon(boolean noon) {
        this.noon = noon;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Treatment getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Treatment treatmentId) {
        this.treatmentId = treatmentId;
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
        if (!(object instanceof Medicine)) {
            return false;
        }
        Medicine other = (Medicine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Medicine[ id=" + id + " ]";
    }
    
}
