/*     */ package net.revivers.reviverstwo.cmd.bukkit;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.message.BukkitMessageKey;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.message.NoPermissionMessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.Command;
/*     */ import net.revivers.reviverstwo.cmd.core.SubCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandExecutionException;
/*     */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.DefaultMessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderMapper;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public final class BukkitCommand<S>
/*     */   extends Command
/*     */   implements Command<S, BukkitSubCommand<S>>
/*     */ {
/*     */   private final MessageRegistry<S> messageRegistry;
/*     */   private final SenderMapper<CommandSender, S> senderMapper;
/*  54 */   private final Map<String, BukkitSubCommand<S>> subCommands = new HashMap<>();
/*  55 */   private final Map<String, BukkitSubCommand<S>> subCommandAliases = new HashMap<>();
/*     */   
/*     */   public BukkitCommand(@NotNull String name, @NotNull BukkitCommandProcessor<S> processor) {
/*  58 */     super(name);
/*     */     
/*  60 */     this.description = processor.getDescription();
/*  61 */     this.messageRegistry = processor.getRegistryContainer().getMessageRegistry();
/*  62 */     this.senderMapper = processor.getSenderMapper();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSubCommand(@NotNull String name, @NotNull BukkitSubCommand<S> subCommand) {
/*  67 */     this.subCommands.putIfAbsent(name, subCommand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSubCommandAlias(@NotNull String alias, @NotNull BukkitSubCommand<S> subCommand) {
/*  72 */     this.subCommandAliases.putIfAbsent(alias, subCommand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
/*  81 */     BukkitSubCommand<S> subCommand = getDefaultSubCommand();
/*     */     
/*  83 */     String subCommandName = "";
/*  84 */     if (args.length > 0) subCommandName = args[0].toLowerCase(); 
/*  85 */     if (subCommand == null || subCommandExists(subCommandName)) {
/*  86 */       subCommand = getSubCommand(subCommandName);
/*     */     }
/*     */     
/*  89 */     S mappedSender = (S)this.senderMapper.map(sender);
/*  90 */     if (mappedSender == null) {
/*  91 */       throw new CommandExecutionException("Invalid sender. Sender mapper returned null");
/*     */     }
/*     */     
/*  94 */     if (subCommand == null || (args.length > 0 && subCommand.isDefault() && !subCommand.hasArguments())) {
/*  95 */       this.messageRegistry.sendMessage((ContextualKey)MessageKey.UNKNOWN_COMMAND, mappedSender, (MessageContext)new DefaultMessageContext(getName(), subCommandName));
/*  96 */       return true;
/*     */     } 
/*     */     
/*  99 */     CommandPermission permission = subCommand.getPermission();
/* 100 */     if (!CommandPermission.hasPermission(sender, permission)) {
/* 101 */       this.messageRegistry.sendMessage((ContextualKey)BukkitMessageKey.NO_PERMISSION, mappedSender, (MessageContext)new NoPermissionMessageContext(getName(), subCommand.getName(), permission));
/* 102 */       return true;
/*     */     } 
/*     */     
/* 105 */     List<String> commandArgs = Arrays.asList(!subCommand.isDefault() ? Arrays.<String>copyOfRange(args, 1, args.length) : args);
/*     */     
/* 107 */     subCommand.execute(mappedSender, commandArgs);
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
/* 113 */     if (args.length == 0) return Collections.emptyList(); 
/* 114 */     BukkitSubCommand<S> subCommand = getDefaultSubCommand();
/*     */     
/* 116 */     String arg = args[0].toLowerCase();
/*     */     
/* 118 */     if (args.length == 1 && (subCommand == null || !subCommand.hasArguments())) {
/* 119 */       return (List<String>)this.subCommands.entrySet().stream()
/* 120 */         .filter(it -> !((BukkitSubCommand)it.getValue()).isDefault())
/* 121 */         .filter(it -> ((String)it.getKey()).startsWith(arg))
/* 122 */         .filter(it -> {
/*     */             CommandPermission permission = ((BukkitSubCommand)it.getValue()).getPermission();
/*     */             
/*     */             return CommandPermission.hasPermission(sender, permission);
/* 126 */           }).map(Map.Entry::getKey)
/* 127 */         .collect(Collectors.toList());
/*     */     }
/*     */     
/* 130 */     if (subCommandExists(arg)) subCommand = getSubCommand(arg); 
/* 131 */     if (subCommand == null) return Collections.emptyList();
/*     */     
/* 133 */     CommandPermission permission = subCommand.getPermission();
/* 134 */     if (!CommandPermission.hasPermission(sender, permission)) return Collections.emptyList();
/*     */     
/* 136 */     S mappedSender = (S)this.senderMapper.map(sender);
/* 137 */     if (mappedSender == null) {
/* 138 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 141 */     List<String> commandArgs = Arrays.asList(args);
/* 142 */     return subCommand.getSuggestions(mappedSender, !subCommand.isDefault() ? commandArgs.subList(1, commandArgs.size()) : commandArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BukkitSubCommand<S> getDefaultSubCommand() {
/* 151 */     return this.subCommands.get("TH_DEFAULT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BukkitSubCommand<S> getSubCommand(@NotNull String key) {
/* 161 */     BukkitSubCommand<S> subCommand = this.subCommands.get(key);
/* 162 */     if (subCommand != null) return subCommand; 
/* 163 */     return this.subCommandAliases.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean subCommandExists(@NotNull String key) {
/* 173 */     return (this.subCommands.containsKey(key) || this.subCommandAliases.containsKey(key));
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */