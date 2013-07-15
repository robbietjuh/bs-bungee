package net.robbytu.banjoserver.bungee.mail;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MailCommand extends Command {
    private final String usage = "/mail [send] [user] [message]";

    public MailCommand() {
        super("mail", null, "psm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length > 3 && args[0].equalsIgnoreCase("send")) {
            Mail mail = new Mail();

            String message = "";
            for(int i = 2; i < args.length; i++) message += ((message.length() == 0) ? "" : " ") + args[i];

            mail.from_user = sender.getName();
            mail.to_user = args[1];
            mail.message = message;

            Mails.sendMail(mail);

            sender.sendMessage(ChatColor.GREEN + "Bericht is verstuurd.");
        }
    }
}
