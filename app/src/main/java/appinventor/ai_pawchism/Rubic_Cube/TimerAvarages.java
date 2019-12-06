package appinventor.ai_pawchism.Rubic_Cube;

class TimerAvarages {
    private String max = "";
    private String min = "";
    private String avg5 = "";
    private String avg10 = "";
    private String avg20 = "";
    private String avg50 = "";

    public TimerAvarages(String max, String min, String avg5, String avg10, String avg20, String avg50) {
        this.max = max;
        this.min = min;
        this.avg5 = avg5;
        this.avg10 = avg10;
        this.avg20 = avg20;
        this.avg50 = avg50;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    public String getAvg5() {
        return avg5;
    }

    public String getAvg10() {
        return avg10;
    }

    public String getAvg20() {
        return avg20;
    }

    public String getAvg50() {
        return avg50;
    }

}