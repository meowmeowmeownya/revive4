/*    */ package net.revivers.reviverstwo.cmd.bukkit;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.revivers.reviverstwo.cmd.bukkit.message.BukkitMessageKey;
/*    */ import net.revivers.reviverstwo.cmd.core.SubCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*    */ import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.DefaultMessageContext;
/*    */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*    */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BukkitSenderValidator
/*    */   implements SenderValidator<CommandSender>
/*    */ {
/*    */   @NotNull
/*    */   public Set<Class<? extends CommandSender>> getAllowedSenders() {
/* 49 */     return (Set<Class<? extends CommandSender>>)ImmutableSet.of(CommandSender.class, ConsoleCommandSender.class, Player.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate(@NotNull MessageRegistry<CommandSender> messageRegistry, @NotNull SubCommand<CommandSender> subCommand, @NotNull CommandSender sender) {
/* 58 */     Class<? extends CommandSender> senderClass = subCommand.getSenderType();
/*    */     
/* 60 */     if (Player.class.isAssignableFrom(senderClass) && !(sender instanceof Player)) {
/* 61 */       messageRegistry.sendMessage((ContextualKey)BukkitMessageKey.PLAYER_ONLY, sender, (MessageContext)new DefaultMessageContext(subCommand
/*    */ 
/*    */             
/* 64 */             .getParentName(), subCommand.getName()));
/*    */       
/* 66 */       return false;
/*    */     } 
/*    */     
/* 69 */     if (ConsoleCommandSender.class.isAssignableFrom(senderClass) && !(sender instanceof ConsoleCommandSender)) {
/* 70 */       messageRegistry.sendMessage((ContextualKey)BukkitMessageKey.CONSOLE_ONLY, sender, (MessageContext)new DefaultMessageContext(subCommand
/*    */ 
/*    */             
/* 73 */             .getParentName(), subCommand.getName()));
/*    */       
/* 75 */       return false;
/*    */     } 
/*    */     
/* 78 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitSenderValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */