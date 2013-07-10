package net.robbytu.banjoserver.bungee.warns;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Warn {
    public int id;
    public String username;
    public String mod;
    public String warn;
    public int date;
    public String server;

    public String getFriendlyDate() {
        Date time = new Date((long) this.date * 1000);
        return new SimpleDateFormat("dd-MM-yyy").format(time);
    }
}
