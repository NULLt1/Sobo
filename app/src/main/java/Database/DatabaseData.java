package Database;

public class DatabaseData {
    private String modul;
    private String date;
    private float physicalvalues;

    //constructor DatabaseData
    public DatabaseData(String modul, String date, float physicalvalues) {
        this.modul = modul;
        this.date = date;
        this.physicalvalues = physicalvalues;
    }

    //function get modul
    public String getModul() {
        return modul;
    }

    //function get date
    public String getDate() {
        return date;
    }

    //function get physicalvalues
    public float getPhysicalvalues() {
        return physicalvalues;
    }

    //function getDays for ViewGraph
    public float getDays() {
        float days = 0;

        int dayinteger = Integer.parseInt(date.substring(8));
        int monthinteger = Integer.parseInt(date.substring(5, 7));
        int yearinteger = Integer.parseInt(date.substring(0, 4));

        days += dayinteger;

        switch (monthinteger) {
            case 2:
                days += 31;
                break;
            case 3:
                if (yearinteger == 2016)
                    days += 60;
                else
                    days += 59;
                break;
            case 4:
                if (yearinteger == 2016)
                    days += 91;
                else
                    days += 90;
                break;
            case 5:
                if (yearinteger == 2016)
                    days += 121;
                else
                    days += 120;
                break;
            case 6:
                if (yearinteger == 2016)
                    days += 152;
                else
                    days += 151;
                break;
            case 7:
                if (yearinteger == 2016)
                    days += 182;
                else
                    days += 181;
                break;
            case 8:
                if (yearinteger == 2016)
                    days += 213;
                else
                    days += 212;
                break;
            case 9:
                if (yearinteger == 2016)
                    days += 244;
                else
                    days += 243;
                break;
            case 10:
                if (yearinteger == 2016)
                    days += 274;
                else
                    days += 273;
                break;
            case 11:
                if (yearinteger == 2016)
                    days += 306;
                else
                    days += 305;
                break;
            case 12:
                if (yearinteger == 2016)
                    days += 335;
                else
                    days += 334;
                break;
            default:
                break;
        }

        switch (yearinteger) {
            case 2017:
                days += 366;
                break;
            case 2018:
                days += 731;
                break;
            case 2019:
                days += 1096;
                break;
            default:
                break;
        }
        return days;
    }
}
