package net.robbytu.banjoserver.bungee.warns;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Warn {
    public int id = 0;
    public String username = "";
    public String mod = "";
    public String warn = "";
    public int date = (int) (System.currentTimeMillis() / 1000L);
    public String server = "";
    public boolean active = true;

    public void setActive(int active) {
        this.active = (active == 1);
    }

    public String getFriendlyDate() {
        Date time = new Date((long) this.date * 1000);
        return new SimpleDateFormat("dd-MM-yyy").format(time);
    }
}
