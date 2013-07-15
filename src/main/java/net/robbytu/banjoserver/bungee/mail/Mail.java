package net.robbytu.banjoserver.bungee.mail;

public class Mail {
    String from_user = "";
    String to_user = "";
    int date = (int) (System.currentTimeMillis() / 1000L);
    String message = "";
    boolean unread = true;

    public void setUnread(int unread) {
        this.unread = (unread == 1);
    }
}
