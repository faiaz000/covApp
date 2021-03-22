/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

import java.math.BigDecimal;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class TempSymptoms {

    private BigDecimal fever;

    private BigDecimal conjunctival_congestion;

    private BigDecimal nasal_congestion;

    private BigDecimal headache;

    private BigDecimal dry_cough;

    private BigDecimal productive_cough;

    private BigDecimal pharyngodynia;

    private BigDecimal hemoptysis;
    private BigDecimal fatigue;
    private BigDecimal breath_shortness;
    private BigDecimal diarrhea;
    private BigDecimal nausea;
    private BigDecimal myalgia;
    private BigDecimal arthalgia;
    private BigDecimal chills;
    private BigDecimal chest_pain;

    public TempSymptoms(Object[] singleResult) {
        this.fever = (BigDecimal) singleResult[0];
        this.conjunctival_congestion = (BigDecimal) singleResult[1];
        this.nasal_congestion = (BigDecimal) singleResult[2];
        this.headache = (BigDecimal) singleResult[3];
        this.dry_cough = (BigDecimal) singleResult[4];
        this.productive_cough = (BigDecimal) singleResult[5];
        this.pharyngodynia = (BigDecimal) singleResult[6];
        this.hemoptysis = (BigDecimal) singleResult[7];
        this.fatigue = (BigDecimal) singleResult[8];
        this.breath_shortness = (BigDecimal) singleResult[9];
        this.diarrhea = (BigDecimal) singleResult[10];
        this.nausea = (BigDecimal) singleResult[11];
        this.myalgia = (BigDecimal) singleResult[12];
        this.arthalgia = (BigDecimal) singleResult[13];
        this.chills = (BigDecimal) singleResult[14];
        this.chest_pain = (BigDecimal) singleResult[15];
    }

    public void setFever(BigDecimal fever) {
        this.fever = fever;
    }

    public void setConjunctivalCongestion(BigDecimal conjunctival_congestion) {
        this.conjunctival_congestion = conjunctival_congestion;
    }

    public void setNasalCongestion(BigDecimal nasal_congestion) {
        this.nasal_congestion = nasal_congestion;
    }

    public void setHeadache(BigDecimal headache) {
        this.headache = headache;
    }

    public void setDryCough(BigDecimal dry_cough) {
        this.dry_cough = dry_cough;
    }

    public void setProductiveCough(BigDecimal productive_cough) {
        this.productive_cough = productive_cough;
    }

    public void setPharyngodynia(BigDecimal pharyngodynia) {
        this.pharyngodynia = pharyngodynia;
    }

    public void setHemoptysis(BigDecimal hemoptysis) {
        this.hemoptysis = hemoptysis;
    }

    public void setFatigue(BigDecimal fatigue) {
        this.fatigue = fatigue;
    }

    public void setBreathShortness(BigDecimal breathe_shortness) {
        this.breath_shortness = breathe_shortness;
    }

    public int getBreathShortness() {
        return breath_shortness.intValue();
    }

    public void setDiarrhea(BigDecimal diarrhea) {
        this.diarrhea = diarrhea;
    }

    public int getDiarrhea() {
        return diarrhea.intValue();
    }

    public void setNausea(BigDecimal nausea) {
        this.nausea = nausea;
    }

    public int getNausea() {
        return nausea.intValue();
    }

    public void setMyalgia(BigDecimal myalgia) {
        this.myalgia = myalgia;
    }

    public int getMyalgia() {
        return myalgia.intValue();
    }

    public void setArthalgia(BigDecimal arthalgia) {
        this.arthalgia = arthalgia;
    }

    public int getArthalgia() {
        return arthalgia.intValue();
    }

    public void setChills(BigDecimal chills) {
        this.chills = chills;
    }

    public int getChills() {
        return arthalgia.intValue();
    }

    public void setChestPain(BigDecimal chest_pain) {
        this.chest_pain = chest_pain;
    }

    public int getChestPain() {
        return chest_pain.intValue();
    }

    public int getFatigue() {
        return fatigue.intValue();
    }

    public int getProductiveCough() {
        return productive_cough.intValue();
    }

    public int getDryCough() {
        return dry_cough.intValue();
    }

    public int getHeadache() {
        return headache.intValue();
    }

    public int getFever() {
        return fever.intValue();
    }

    public int getNasalCongestion() {
        return nasal_congestion.intValue();
    }

    public int getConjunctivalCongestion() {
        return conjunctival_congestion.intValue();
    }

    public int getPharyngodynia() {
        return pharyngodynia.intValue();
    }

    public int getHemptysis() {
        return hemoptysis.intValue();
    }

}
