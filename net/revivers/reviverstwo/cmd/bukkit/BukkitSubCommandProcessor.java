/*    */ package net.revivers.reviverstwo.cmd.bukkit;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import net.revivers.reviverstwo.cmd.bukkit.annotation.Permission;
/*    */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*    */ import net.revivers.reviverstwo.cmd.core.processor.AbstractSubCommandProcessor;
/*    */ import net.revivers.reviverstwo.cmd.core.registry.RegistryContainer;
/*    */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
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
/*    */ 
/*    */ final class BukkitSubCommandProcessor<S>
/*    */   extends AbstractSubCommandProcessor<S>
/*    */ {
/*    */   private final CommandPermission permission;
/*    */   
/*    */   public BukkitSubCommandProcessor(@NotNull BaseCommand baseCommand, @NotNull String parentName, @NotNull Method method, @NotNull RegistryContainer<S> registryContainer, @NotNull SenderValidator<S> senderValidator, @Nullable CommandPermission basePermission) {
/* 50 */     super(baseCommand, parentName, method, registryContainer, senderValidator);
/*    */     
/* 52 */     Permission annotation = method.<Permission>getAnnotation(Permission.class);
/* 53 */     if (annotation == null) {
/* 54 */       this.permission = basePermission;
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     this.permission = BukkitCommandProcessor.createPermission(basePermission, 
/*    */         
/* 60 */         (List<String>)Arrays.<String>stream(annotation.value()).collect(Collectors.toList()), annotation
/* 61 */         .description(), annotation
/* 62 */         .def());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public CommandPermission getPermission() {
/* 67 */     return this.permission;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitSubCommandProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */