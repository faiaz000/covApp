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
@Table(name = "medical_tests")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicalTests.findAll", query = "SELECT m FROM MedicalTests m"),
    @NamedQuery(name = "MedicalTests.findById", query = "SELECT m FROM MedicalTests m WHERE m.id = :id"),
    @NamedQuery(name = "MedicalTests.findByName", query = "SELECT m FROM MedicalTests m WHERE m.name = :name")})
public class MedicalTests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Treatment treatmentId;

    public MedicalTests() {
    }

    public MedicalTests(Integer id) {
        this.id = id;
    }

    public MedicalTests(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        if (!(object instanceof MedicalTests)) {
            return false;
        }
        MedicalTests other = (MedicalTests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
