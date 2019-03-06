package uk.ac.ucl;

public class Patient{
    private String id;
    private String birthdate;
    private String deathdate;
    private String ssn;
    private String drivers;
    private String passport;
    private String prefix;
    private String first;
    private String last;
    private String suffix;
    private String maiden;
    private String marital;
    private String race;
    private String ethnicity;
    private String gender;
    private String birthplace;
    private String address;
    private String city;
    private String state;
    private String zip;

    public Patient(String id, String birthdate, String deathdate, String ssn, String drivers, String passport, String prefix, String first, String last, String suffix, String maiden, String marital, String race, String ethnicity, String gender, String birthplace, String address, String city, String state, String zip) {
        this.id = id;
        this.birthdate = birthdate;
        this.deathdate = deathdate;
        this.ssn = ssn;
        this.drivers = drivers;
        this.passport = passport;
        this.prefix = prefix;
        this.first = first;
        this.last = last;
        this.suffix = suffix;
        this.maiden = maiden;
        this.marital = marital;
        this.race = race;
        this.ethnicity = ethnicity;
        this.gender = gender;
        this.birthplace = birthplace;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Patient(){}

    public String getId() {
        return id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getDeathdate() {
        return deathdate;
    }

    public String getSsn() {
        return ssn;
    }

    public String getDrivers() {
        return drivers;
    }

    public String getPassport() {
        return passport;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getMaiden() {
        return maiden;
    }

    public String getMarital() {
        return marital;
    }

    public String getRace() {
        return race;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setDeathdate(String deathdate) {
        this.deathdate = deathdate;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setDrivers(String drivers) {
        this.drivers = drivers;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setMaiden(String maiden) {
        this.maiden = maiden;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}