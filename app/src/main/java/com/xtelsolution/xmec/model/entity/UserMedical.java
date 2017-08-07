package com.xtelsolution.xmec.model.entity;

import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.RESP_User;

import java.util.ArrayList;

/**
 * Created by vivhp on 8/5/2017.
 */

public class UserMedical {

    private RESP_User user;

    private ArrayList<RESP_Medical> medical;

    public UserMedical() {
    }

    public UserMedical(RESP_User user, ArrayList<RESP_Medical> medical) {
        this.user = user;
        this.medical = medical;
    }

    public RESP_User getUser() {
        return user;
    }

    public void setUser(RESP_User user) {
        this.user = user;
    }

    public ArrayList<RESP_Medical> getMedical() {
        return medical;
    }

    public void setMedical(ArrayList<RESP_Medical> medical) {
        this.medical = medical;
    }
}
