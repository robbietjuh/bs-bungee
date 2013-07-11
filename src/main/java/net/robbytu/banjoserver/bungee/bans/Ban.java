package net.robbytu.banjoserver.bungee.bans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ban {
    public int id;
    public String username;
    public String mod;
    public String reason;
    public int date;
    public String server;
    public int tempban;
    public boolean active;

    public Ban() {
        this.id = 0;
        this.date = (int) (System.currentTimeMillis() / 1000L);
        this.active = true;
        this.tempban = 0;
    }

    public void setActive(int active) {
        this.active = (active == 1);
    }

    public boolean isTempban() {
        return (this.tempban > 0);
    }

    public String getFriendlyDate() {
        Date time = new Date((long) this.date * 1000);
        return new SimpleDateFormat("dd-MM-yyy").format(time);
    }
}
