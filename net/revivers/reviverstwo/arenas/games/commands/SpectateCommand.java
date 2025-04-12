/*    */ package net.revivers.reviverstwo.arenas.games.commands;
/*    */ 
/*    */ import net.revivers.reviverstwo.arenas.games.Game;
/*    */ import net.revivers.reviverstwo.arenas.games.PlayerHandler;
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.annotation.Command;
/*    */ import net.revivers.reviverstwo.cmd.core.annotation.Default;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Command("spectate")
/*    */ public class SpectateCommand extends BaseCommand {
/*    */   @Default
/*    */   public void defaultCommand(CommandSender sender, Player targetPlayer) {
/*    */     Player player;
/* 17 */     if (sender instanceof Player) { player = (Player)sender; }
/*    */     else { return; }
/* 19 */      if (targetPlayer == null) {
/* 20 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown player."));
/*    */       
/*    */       return;
/*    */     } 
/* 24 */     if (!PlayerHandler.hasPlayerGame(targetPlayer)) {
/* 25 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified player is not in a game."));
/*    */       
/*    */       return;
/*    */     } 
/* 29 */     if (PlayerHandler.hasPlayerGame(player)) {
/* 30 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to leave the current game you're in before spectating another game."));
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     Game game = PlayerHandler.getPlayerGame(targetPlayer);
/* 35 */     PlayerHandler.setPlayerGame(player, game);
/* 36 */     game.addSpectator(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\commands\SpectateCommand.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */