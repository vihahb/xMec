package com.xtelsolution.xmec.model;

/**
 * Created by Admin on 2/3/2017.
 */

public class Medicine {
    private int id;
    private String name;
    private String type;
    private String group;
    private String component;
    private String indication;
    private String contraindication;
    private String drugInteraction;
    private String sidEeffect;
    private String warning;
    private String dosage;
    private String preservation;

    public Medicine(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Medicine(int id, String name, String type, String group, String component, String indication, String contraindication, String drugInteraction, String sidEeffect, String warning, String dosage, String preservation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.group = group;
        this.component = component;
        this.indication = indication;
        this.contraindication = contraindication;
        this.drugInteraction = drugInteraction;
        this.sidEeffect = sidEeffect;
        this.warning = warning;
        this.dosage =dosage;
        this.preservation = preservation;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    public String getDrugInteraction() {
        return drugInteraction;
    }

    public void setDrugInteraction(String drugInteraction) {
        this.drugInteraction = drugInteraction;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreservation() {
        return preservation;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation;
    }

    public String getSidEeffect() {
        return sidEeffect;
    }

    public void setSidEeffect(String sidEeffect) {
        this.sidEeffect = sidEeffect;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "component='" + component + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", indication='" + indication + '\'' +
                ", contraindication='" + contraindication + '\'' +
                ", drugInteraction='" + drugInteraction + '\'' +
                ", sidEeffect='" + sidEeffect + '\'' +
                ", warning='" + warning + '\'' +
                ", dosage='" + dosage + '\'' +
                ", preservation='" + preservation + '\'' +
                '}';
    }
}
