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
@Table(name = "signs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Signs.findAll", query = "SELECT s FROM Signs s"),
    @NamedQuery(name = "Signs.findBySignsId", query = "SELECT s FROM Signs s WHERE s.signsId = :signsId"),
    @NamedQuery(name = "Signs.findByThroatCongestion", query = "SELECT s FROM Signs s WHERE s.throatCongestion = :throatCongestion"),
    @NamedQuery(name = "Signs.findByTonsil", query = "SELECT s FROM Signs s WHERE s.tonsil = :tonsil"),
    @NamedQuery(name = "Signs.findBySwelling", query = "SELECT s FROM Signs s WHERE s.swelling = :swelling"),
    @NamedQuery(name = "Signs.findByLymphNodeEnlargement", query = "SELECT s FROM Signs s WHERE s.lymphNodeEnlargement = :lymphNodeEnlargement"),
    @NamedQuery(name = "Signs.findByRashes", query = "SELECT s FROM Signs s WHERE s.rashes = :rashes"),
    @NamedQuery(name = "Signs.findByUnconsciousness", query = "SELECT s FROM Signs s WHERE s.unconsciousness = :unconsciousness")})
public class Signs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "signs_id")
    private Integer signsId;
    @Basic(optional = false)
    @Column(name = "throat_congestion")
    private boolean throatCongestion;
    @Basic(optional = false)
    @Column(name = "tonsil")
    private boolean tonsil;
    @Basic(optional = false)
    @Column(name = "swelling")
    private boolean swelling;
    @Basic(optional = false)
    @Column(name = "lymph_node_enlargement")
    private boolean lymphNodeEnlargement;
    @Basic(optional = false)
    @Column(name = "rashes")
    private boolean rashes;
    @Basic(optional = false)
    @Column(name = "unconsciousness")
    private boolean unconsciousness;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;

    public Signs() {
    }

    public Signs(Integer signsId) {
        this.signsId = signsId;
    }

    public Signs(Integer signsId, boolean throatCongestion, boolean tonsil, boolean swelling, boolean lymphNodeEnlargement, boolean rashes, boolean unconsciousness) {
        this.signsId = signsId;
        this.throatCongestion = throatCongestion;
        this.tonsil = tonsil;
        this.swelling = swelling;
        this.lymphNodeEnlargement = lymphNodeEnlargement;
        this.rashes = rashes;
        this.unconsciousness = unconsciousness;
    }

    public Integer getSignsId() {
        return signsId;
    }

    public void setSignsId(Integer signsId) {
        this.signsId = signsId;
    }

    public boolean getThroatCongestion() {
        return throatCongestion;
    }

    public void setThroatCongestion(boolean throatCongestion) {
        this.throatCongestion = throatCongestion;
    }

    public boolean getTonsil() {
        return tonsil;
    }

    public void setTonsil(boolean tonsil) {
        this.tonsil = tonsil;
    }

    public boolean getSwelling() {
        return swelling;
    }

    public void setSwelling(boolean swelling) {
        this.swelling = swelling;
    }

    public boolean getLymphNodeEnlargement() {
        return lymphNodeEnlargement;
    }

    public void setLymphNodeEnlargement(boolean lymphNodeEnlargement) {
        this.lymphNodeEnlargement = lymphNodeEnlargement;
    }

    public boolean getRashes() {
        return rashes;
    }

    public void setRashes(boolean rashes) {
        this.rashes = rashes;
    }

    public boolean getUnconsciousness() {
        return unconsciousness;
    }

    public void setUnconsciousness(boolean unconsciousness) {
        this.unconsciousness = unconsciousness;
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
        hash += (signsId != null ? signsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Signs)) {
            return false;
        }
        Signs other = (Signs) object;
        if ((this.signsId == null && other.signsId != null) || (this.signsId != null && !this.signsId.equals(other.signsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Signs[ signsId=" + signsId + " ]";
    }
    
}
