package net.robbytu.banjoserver.bungee.mute;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.robbytu.banjoserver.bungee.perms.Permissions;

public class MuteCommand extends Command {
    private final String usage = "/mute [user]";

    public MuteCommand() {
        super("mute");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!Permissions.hasPermission(sender.getName(), "bs.bungee.mute")) {
            this.failCommand(sender, "You do not have permission to execute this command.");
            return;
        }

        if(args.length != 1) {
            this.failCommand(sender, "Please specify the user to mute.");
            return;
        }

//        if(Permissions.hasPermission(args[0], "bs.bungee.mute")) {
//            this.failCommand(sender, "You cannot mute this user.");
//            return;
//        }

        if(MuteUtil.isMuted(args[0])) {
            MuteUtil.unmute(args[0]);
            sender.sendMessage(ChatColor.GREEN + args[0] + " unmuted.");
        }
        else {
            MuteUtil.mute(args[0]);
            sender.sendMessage(ChatColor.GREEN + args[0] + " muted.");
        }
    }

    private void failCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
        sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.ITALIC + this.usage);
    }
}
