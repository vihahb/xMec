package com.xtelsolution.xmec.entity;

/**
 * Created by phimau on 1/23/2017.
 */

public class BodyMapPosition {
    private float[][] positionBody;
    private String[] nameBodyPart;
    private float x;
    private float y;

    public BodyMapPosition(){

    }

    public BodyMapPosition(String[] nameBodyPart, float[][] positionBody, float x, float y) {
        this.nameBodyPart = nameBodyPart;
        this.positionBody = positionBody;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public String[] getNameBodyPart() {
        return nameBodyPart;
    }

    public void setNameBodyPart(String[] nameBodyPart) {
        this.nameBodyPart = nameBodyPart;
    }

    public float[][] getPositionBody() {
        return positionBody;
    }

    public void setPositionBody(float[][] positionBody) {
        this.positionBody = positionBody;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public String getNamePosition(){

        for (int i=0;i< positionBody.length;i++){
            if(y>positionBody[i][2]&&y<positionBody[i][3]){
                if (x>positionBody[i][0]&&x<positionBody[i][1])
                    return nameBodyPart[i];
            }
        }
        return null;
    }

}
