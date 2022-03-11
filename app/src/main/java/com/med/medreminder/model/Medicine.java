package com.med.medreminder.model;

public class Medicine {

        //public int id;
        String name;
        String form;
        String dose;
//        String reason;
//        String isDaily;
//        String often;
        String time;
        String timeZone;
        String unit;
        String quantity;
        int image;

        public Medicine(int image, String name, String form, String dose, String time, String timeZone, String unit, String quantity) {
            this.image = image;
            this.name = name;
            this.form = form;
            this.dose = dose;
//            this.reason = reason;
//            this.isDaily = isDaily;
//            this.often = often;
            this.time = time;
            this.timeZone = timeZone;
            this.unit = unit;
            this.quantity = quantity;
        }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
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

        public String getDose() {
            return dose;
        }

        public void setDose(String dose) {
            this.dose = dose;
        }

//        public String getReason() {
//            return reason;
//        }
//
//        public void setReason(String reason) {
//            this.reason = reason;
//        }
//
//        public String getIsDaily() {
//            return isDaily;
//        }
//
//        public void setIsDaily(String isDaily) {
//            this.isDaily = isDaily;
//        }
//
//        public String getOften() {
//            return often;
//        }
//
//        public void setOften(String often) {
//            this.often = often;
//        }

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
                    ", dose='" + dose + '\'' +
                    ", unit='" + unit + '\'' +
                    ", quantity='" + quantity + '\'' +
//                    ", reason='" + reason + '\'' +
//                    ", isDaily='" + isDaily + '\'' +
//                    ", often='" + often + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }


