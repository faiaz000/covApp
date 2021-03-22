/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findById", query = "SELECT a FROM Address a WHERE a.id = :id"),
    @NamedQuery(name = "Address.findByCountry", query = "SELECT a FROM Address a WHERE a.country = :country"),
    @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city"),
    @NamedQuery(name = "Address.findByZip", query = "SELECT a FROM Address a WHERE a.zip = :zip"),
    @NamedQuery(name = "Address.findByRoad", query = "SELECT a FROM Address a WHERE a.road = :road"),
    @NamedQuery(name = "Address.findByHouse", query = "SELECT a FROM Address a WHERE a.house = :house"),
    @NamedQuery(name = "Address.findByState", query = "SELECT a FROM Address a WHERE a.state = :state")})
public class Address implements Serializable {

    @Basic(optional = false)
    @Column(name = "area")
    private String area;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "zip")
    private String zip;
    @Basic(optional = false)
    @Column(name = "road")
    private String road;
    @Basic(optional = false)
    @Column(name = "house")
    private String house;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressId")
    private Collection<Patient> patientCollection;

    public Address() {
    }

    public Address(Integer id) {
        this.id = id;
    }

    public Address(Integer id, String country, String city, String zip, String road, String house, String state) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.road = road;
        this.house = house;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public Collection<Patient> getPatientCollection() {
        return patientCollection;
    }

    public void setPatientCollection(Collection<Patient> patientCollection) {
        this.patientCollection = patientCollection;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Address[ id=" + id + " ]";
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
}
