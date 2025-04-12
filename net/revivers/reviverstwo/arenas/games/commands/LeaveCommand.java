/*    */ package net.revivers.reviverstwo.arenas.games.commands;
/*    */ 
/*    */ import net.revivers.reviverstwo.arenas.games.Game;
/*    */ import net.revivers.reviverstwo.arenas.games.PlayerHandler;
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.annotation.Command;
/*    */ import net.revivers.reviverstwo.cmd.core.annotation.Default;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Command(value = "leave", alias = {"l"})
/*    */ public class LeaveCommand
/*    */   extends BaseCommand {
/*    */   @Default
/*    */   public void defaultCommand(CommandSender sender) {
/*    */     Player player;
/* 17 */     if (sender instanceof Player) { player = (Player)sender; }
/*    */     else
/*    */     { return; }
/* 20 */      if (PlayerHandler.getPlayerGame(player) == null)
/*    */       return; 
/* 22 */     Game game = PlayerHandler.getPlayerGame(player);
/* 23 */     game.leave(player, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\commands\LeaveCommand.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */