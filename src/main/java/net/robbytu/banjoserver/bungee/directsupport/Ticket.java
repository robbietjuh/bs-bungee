package net.robbytu.banjoserver.bungee.directsupport;

public class Ticket {
    public int id = 0;
    public String username = "";
    public String admin = "";
    public String status = "open";
    public String question = "";
    public String server = "";
    public int date_created = (int) (System.currentTimeMillis() / 1000L);
    public int date_accepted = 0;
    public int date_resolved = 0;
}
