/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id"),
    @NamedQuery(name = "Patient.findByFirstName", query = "SELECT p FROM Patient p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Patient.findByDob", query = "SELECT p FROM Patient p WHERE p.dob = :dob"),
    @NamedQuery(name = "Patient.findByAge", query = "SELECT p FROM Patient p WHERE p.age = :age"),
    @NamedQuery(name = "Patient.findByPhone", query = "SELECT p FROM Patient p WHERE p.phone = :phone"),
    @NamedQuery(name = "Patient.findByEmail", query = "SELECT p FROM Patient p WHERE p.email = :email"),
    @NamedQuery(name = "Patient.findByMaritalStatus", query = "SELECT p FROM Patient p WHERE p.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "Patient.findByChildrenNo", query = "SELECT p FROM Patient p WHERE p.childrenNo = :childrenNo"),
    @NamedQuery(name = "Patient.findBySex", query = "SELECT p FROM Patient p WHERE p.sex = :sex"),
    @NamedQuery(name = "Patient.findByOccupation", query = "SELECT p FROM Patient p WHERE p.occupation = :occupation"),
    @NamedQuery(name = "Patient.findByAddmissionDate", query = "SELECT p FROM Patient p WHERE p.addmissionDate = :addmissionDate"),
    @NamedQuery(name = "Patient.findBySecurityNumber", query = "SELECT p FROM Patient p WHERE p.securityNumber = :securityNumber"),
    @NamedQuery(name = "Patient.findByMiddleName", query = "SELECT p FROM Patient p WHERE p.middleName = :middleName"),
    @NamedQuery(name = "Patient.findByLastName", query = "SELECT p FROM Patient p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Patient.findByReleaseDate", query = "SELECT p FROM Patient p WHERE p.releaseDate = :releaseDate")})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @Column(name = "age")
    private int age;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "marital_status")
    private String maritalStatus;
    @Basic(optional = false)
    @Column(name = "children_no")
    private int childrenNo;
    @Basic(optional = false)
    @Column(name = "sex")
    private String sex;
    @Basic(optional = false)
    @Column(name = "occupation")
    private String occupation;
    @Basic(optional = false)
    @Column(name = "addmission_date")
    @Temporal(TemporalType.DATE)
    private Date addmissionDate;
    @Basic(optional = false)
    @Column(name = "security_number")
    private String securityNumber;
    @Column(name = "middle_name")
    private String middleName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<Symptoms> symptomsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<Treatment> treatmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<Signs> signsCollection;
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address addressId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<PatientState> patientStateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<MedicalHistory> medicalHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private Collection<Outcome> outcomeCollection;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(Integer id, String firstName, Date dob, int age, String phone, String email, String maritalStatus, int childrenNo, String sex, String occupation, Date addmissionDate, String securityNumber, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.dob = dob;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.childrenNo = childrenNo;
        this.sex = sex;
        this.occupation = occupation;
        this.addmissionDate = addmissionDate;
        this.securityNumber = securityNumber;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getChildrenNo() {
        return childrenNo;
    }

    public void setChildrenNo(int childrenNo) {
        this.childrenNo = childrenNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Date getAddmissionDate() {
        return addmissionDate;
    }

    public void setAddmissionDate(Date addmissionDate) {
        this.addmissionDate = addmissionDate;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @XmlTransient
    public Collection<Symptoms> getSymptomsCollection() {
        return symptomsCollection;
    }

    public void setSymptomsCollection(Collection<Symptoms> symptomsCollection) {
        this.symptomsCollection = symptomsCollection;
    }

    @XmlTransient
    public Collection<Treatment> getTreatmentCollection() {
        return treatmentCollection;
    }

    public void setTreatmentCollection(Collection<Treatment> treatmentCollection) {
        this.treatmentCollection = treatmentCollection;
    }

    @XmlTransient
    public Collection<Signs> getSignsCollection() {
        return signsCollection;
    }

    public void setSignsCollection(Collection<Signs> signsCollection) {
        this.signsCollection = signsCollection;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    @XmlTransient
    public Collection<PatientState> getPatientStateCollection() {
        return patientStateCollection;
    }

    public void setPatientStateCollection(Collection<PatientState> patientStateCollection) {
        this.patientStateCollection = patientStateCollection;
    }

    @XmlTransient
    public Collection<MedicalHistory> getMedicalHistoryCollection() {
        return medicalHistoryCollection;
    }

    public void setMedicalHistoryCollection(Collection<MedicalHistory> medicalHistoryCollection) {
        this.medicalHistoryCollection = medicalHistoryCollection;
    }

    @XmlTransient
    public Collection<Outcome> getOutcomeCollection() {
        return outcomeCollection;
    }

    public void setOutcomeCollection(Collection<Outcome> outcomeCollection) {
        this.outcomeCollection = outcomeCollection;
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
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Patient[ id=" + id + " ]";
    }
    public String getFullName(){
    
        String fullname = getFirstName()+ " " + getMiddleName() + " " + getLastName();
        return  fullname;
    }
    
}
