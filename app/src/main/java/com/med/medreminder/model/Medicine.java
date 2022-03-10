package com.med.medreminder.model;

public class Medicine {

    String name;
    String form;
    String strength;
    String reason;
    String isDaily;
    String often;
    String time;

    public Medicine(String name, String form, String strength, String reason, String isDaily, String often, String time) {
        this.name = name;
        this.form = form;
        this.strength = strength;
        this.reason = reason;
        this.isDaily = isDaily;
        this.often = often;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIsDaily() {
        return isDaily;
    }

    public void setIsDaily(String isDaily) {
        this.isDaily = isDaily;
    }

    public String getOften() {
        return often;
    }

    public void setOften(String often) {
        this.often = often;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", strength='" + strength + '\'' +
                ", reason='" + reason + '\'' +
                ", isDaily='" + isDaily + '\'' +
                ", often='" + often + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
