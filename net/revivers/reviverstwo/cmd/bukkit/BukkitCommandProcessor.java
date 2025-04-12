/*    */ package net.revivers.reviverstwo.cmd.bukkit;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import net.revivers.reviverstwo.cmd.bukkit.annotation.Permission;
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.SubCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.execution.ExecutionProvider;
/*    */ import net.revivers.reviverstwo.cmd.core.processor.AbstractCommandProcessor;
/*    */ import net.revivers.reviverstwo.cmd.core.processor.AbstractSubCommandProcessor;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.RegistryContainer;
/*    */ import net.revivers.reviverstwo.cmd.core.sender.SenderMapper;
/*    */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.permissions.PermissionDefault;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ 
/*    */ final class BukkitCommandProcessor<S>
/*    */   extends AbstractCommandProcessor<CommandSender, S, BukkitSubCommand<S>, BukkitSubCommandProcessor<S>>
/*    */ {
/*    */   private final CommandPermission basePermission;
/*    */   
/*    */   public BukkitCommandProcessor(@NotNull BaseCommand baseCommand, @NotNull RegistryContainer<S> registryContainer, @NotNull SenderMapper<CommandSender, S> senderMapper, @NotNull SenderValidator<S> senderValidator, @NotNull ExecutionProvider syncExecutionProvider, @NotNull ExecutionProvider asyncExecutionProvider, @Nullable CommandPermission globalBasePermission) {
/* 55 */     super(baseCommand, registryContainer, senderMapper, senderValidator, syncExecutionProvider, asyncExecutionProvider);
/*    */     
/* 57 */     Permission annotation = getBaseCommand().getClass().<Permission>getAnnotation(Permission.class);
/* 58 */     if (annotation == null) {
/* 59 */       this.basePermission = null;
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     this.basePermission = createPermission(globalBasePermission, 
/*    */         
/* 65 */         (List<String>)Arrays.<String>stream(annotation.value()).collect(Collectors.toList()), annotation
/* 66 */         .description(), annotation
/* 67 */         .def());
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   protected BukkitSubCommandProcessor<S> createProcessor(@NotNull Method method) {
/* 73 */     return new BukkitSubCommandProcessor<>(
/* 74 */         getBaseCommand(), 
/* 75 */         getName(), method, 
/*    */         
/* 77 */         getRegistryContainer(), 
/* 78 */         getSenderValidator(), this.basePermission);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   protected BukkitSubCommand<S> createSubCommand(@NotNull BukkitSubCommandProcessor<S> processor, @NotNull ExecutionProvider executionProvider) {
/* 88 */     return new BukkitSubCommand<>(processor, getName(), executionProvider);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static CommandPermission createPermission(@Nullable CommandPermission parent, @NotNull List<String> nodes, @NotNull String description, @NotNull PermissionDefault permissionDefault) {
/* 97 */     return (parent == null) ? 
/* 98 */       new CommandPermission(nodes, description, permissionDefault) : 
/* 99 */       parent.child(nodes, description, permissionDefault);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitCommandProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */