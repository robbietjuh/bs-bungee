package net.robbytu.banjoserver.bungee.mail;

public class Mail {
    public int id = 0;
    public String from_user = "";
    public String to_user = "";
    public int date = (int) (System.currentTimeMillis() / 1000L);
    public String message = "";
    public boolean unread = true;

    public void setUnread(int unread) {
        this.unread = (unread == 1);
    }
}
