/*    */ package net.revivers.reviverstwo.credits;
/*    */ 
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.annotation.Command;
/*    */ import net.revivers.reviverstwo.cmd.core.annotation.Default;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ @Command(value = "revivers", alias = {"reviversplugin", "reviverstwo"})
/*    */ public class CreditsCommand
/*    */   extends BaseCommand {
/*    */   @Default
/*    */   public void defaultCommand(CommandSender sender) {
/* 14 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
/* 15 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lRevivers: &c2nd Edition &7(PLUGIN)"));
/* 16 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╠ &cPlugin made by sdap &7(@dap#7998)"));
/* 17 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╠ &cModified by shimato & pcranaway"));
/* 18 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╚&cDesigned and made for Revivers &7(revivers.net)"));
/* 19 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\credits\CreditsCommand.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */