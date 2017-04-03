package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by HUNGNT on 4/3/2017.
 */

//private String ;
//private String ;
//private String ;
//private int id;
//private String ;
//private String ;
//private String ;
public class RESP_Medicine_Detail extends RESP_Basic{
    @Expose
    private int id;
    @Expose
    @SerializedName("ten_thuoc")
    private String name;
    @Expose
    @SerializedName("dang_bao_che")
    private String type;
    @Expose
    @SerializedName("nhom_duoc_ly")
    private String group;
    @Expose
    @SerializedName("thanh_phan")
    private String component;
    @Expose
    @SerializedName("chi_dinh")
    private String indication;
    @Expose
    @SerializedName("chong_chi_dinh")
    private String contraindication;
    @Expose
    @SerializedName("tuong_tac_thuoc")
    private String drugInteraction;
    @Expose
    @SerializedName("tac_dung_phu")
    private String sidEeffect;
    @Expose
    @SerializedName("chu_y_de_phong")
    private String warning;
    @Expose
    @SerializedName("lieu_luong")
    private String dosage;
    @Expose
    @SerializedName("bao_quan")
    private String preservation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
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

    public String getSidEeffect() {
        return sidEeffect;
    }

    public void setSidEeffect(String sidEeffect) {
        this.sidEeffect = sidEeffect;
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

    public String getPreservation() {
        return preservation;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation;
    }

    @Override
    public String toString() {
        return "RESP_Medicine_Detail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", component='" + component + '\'' +
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
