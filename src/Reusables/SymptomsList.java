/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class SymptomsList {

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    public List<SymptomsObject> symptoms = new ArrayList<>();
    // public List<String> signs = new ArrayList<>();

    public List<SymptomsObject> getSymptoms() {
        return symptoms;
    }

    public SymptomsList() {

        Query query = entityManager.createNativeQuery("SELECT sum(fever) as fever, sum(conjunctival_congestion) "
                + "as conjunctival_congestion, sum(nasal_congestion) as nasal_congestion, sum(headache) as headache, "
                + "sum(dry_cough) as dry_cough, sum(productive_cough) as productive_cough, sum(pharyngodynia) as "
                + "pharyngodynia, sum(hemoptysis) as hemoptysis, sum(fatigue) as fatigue, sum(breathe_shortness) as "
                + "breathe_shortness, sum(diarrhea) as diarrhea, sum(nausea) as nausea , sum(myalgia) as myalgia, sum(arthalgia) "
                + "as arthalgia, sum(chills) as chills, sum(chest_pain) as chest_pain from symptoms");

        TempSymptoms s = new TempSymptoms((Object[]) query.getSingleResult());
        
        symptoms.add(new SymptomsObject("Arthalgia", s.getArthalgia()));
        symptoms.add(new SymptomsObject("Breath Shortness", s.getBreathShortness()));
        symptoms.add(new SymptomsObject("Chest Pain", s.getChestPain()));
        symptoms.add(new SymptomsObject("Chills", s.getChestPain()));
        symptoms.add(new SymptomsObject("Conjunctival Congestion", s.getConjunctivalCongestion()));
        symptoms.add(new SymptomsObject("Diarrhea", s.getDiarrhea()));
        symptoms.add(new SymptomsObject("Dry Cough", s.getDryCough()));
        symptoms.add(new SymptomsObject("Fatigue", s.getFatigue()));
        symptoms.add(new SymptomsObject("Fever", s.getFever()));
        symptoms.add(new SymptomsObject("Headache", s.getHeadache()));
        symptoms.add(new SymptomsObject("Hemoptysis", s.getHemptysis()));
        symptoms.add(new SymptomsObject("Myalgia", s.getMyalgia()));
        symptoms.add(new SymptomsObject("Nasal Congestion", s.getNasalCongestion()));
        symptoms.add(new SymptomsObject("Nausea", s.getNausea()));
        symptoms.add(new SymptomsObject("Pharyngodynia", s.getPharyngodynia()));
        symptoms.add(new SymptomsObject("Productive Cough", s.getProductiveCough()));
  
    }

}
