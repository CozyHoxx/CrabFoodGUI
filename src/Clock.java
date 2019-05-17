public class Clock {
    private int HOUR = 0;
    private int MINUTE = 0;

    public Clock() {
    }
    public int getHOUR() {
        return HOUR;
    }

    public int getMINUTE() {
        return MINUTE;
    }

    public void updateClock() {
        MINUTE++;
        if (MINUTE == 60) {
            HOUR++;
            MINUTE = 0;
        }
    }

    public String getTime(){
        String str = Main.pad(2, '0', Integer.toString(HOUR) + "");
        str+=":" + Main.pad(2,'0',Integer.toString(MINUTE));
        return str;
    }
}
