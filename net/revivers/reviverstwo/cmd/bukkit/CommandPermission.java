/*     */ package net.revivers.reviverstwo.cmd.bukkit;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionDefault;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CommandPermission
/*     */ {
/*     */   private final List<String> nodes;
/*     */   private final PermissionDefault permissionDefault;
/*     */   private final String description;
/*     */   
/*     */   public CommandPermission(@NotNull List<String> nodes, @NotNull String description, @NotNull PermissionDefault permissionDefault) {
/*  52 */     this.nodes = nodes;
/*  53 */     this.description = description;
/*  54 */     this.permissionDefault = permissionDefault;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasPermission(@NotNull CommandSender sender, @Nullable CommandPermission permission) {
/*  74 */     return (permission == null || permission.hasPermission(sender));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CommandPermission child(@NotNull List<String> nodes, @NotNull String description, @NotNull PermissionDefault permissionDefault) {
/*  84 */     List<String> newNodes = (List<String>)this.nodes.stream().flatMap(parent -> nodes.stream().map(())).collect(Collectors.toList());
/*     */     
/*  86 */     return new CommandPermission(newNodes, description, permissionDefault);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  93 */     PluginManager pluginManager = Bukkit.getPluginManager();
/*     */     
/*  95 */     this.nodes.forEach(node -> {
/*     */           Permission permission = pluginManager.getPermission(node);
/*     */           if (permission != null) {
/*     */             return;
/*     */           }
/*     */           pluginManager.addPermission(new Permission(node, this.description, this.permissionDefault));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getNodes() {
/* 110 */     return this.nodes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPermission(@NotNull CommandSender sender) {
/* 120 */     Objects.requireNonNull(sender); return this.nodes.stream().anyMatch(sender::hasPermission);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\CommandPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */