/*     */ package net.revivers.reviverstwo.cmd.bukkit;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.message.BukkitMessageKey;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.message.NoPermissionMessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.CommandManager;
/*     */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandRegistrationException;
/*     */ import net.revivers.reviverstwo.cmd.core.execution.ExecutionProvider;
/*     */ import net.revivers.reviverstwo.cmd.core.execution.SyncExecutionProvider;
/*     */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.DefaultMessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.InvalidArgumentContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.registry.RegistryContainer;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderMapper;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionContext;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandMap;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginIdentifiableCommand;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public final class BukkitCommandManager<S>
/*     */   extends CommandManager<CommandSender, S>
/*     */ {
/*     */   private final Plugin plugin;
/*  57 */   private final RegistryContainer<S> registryContainer = new RegistryContainer();
/*     */   
/*  59 */   private final Map<String, BukkitCommand<S>> commands = new HashMap<>();
/*     */   
/*  61 */   private final ExecutionProvider syncExecutionProvider = (ExecutionProvider)new SyncExecutionProvider();
/*     */   
/*     */   private final ExecutionProvider asyncExecutionProvider;
/*     */   
/*     */   private final CommandMap commandMap;
/*     */   
/*     */   private final Map<String, Command> bukkitCommands;
/*  68 */   private final CommandPermission basePermission = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BukkitCommandManager(@NotNull Plugin plugin, @NotNull SenderMapper<CommandSender, S> senderMapper, @NotNull SenderValidator<S> senderValidator) {
/*  75 */     super(senderMapper, senderValidator);
/*  76 */     this.plugin = plugin;
/*  77 */     this.asyncExecutionProvider = new BukkitAsyncExecutionProvider(plugin);
/*     */     
/*  79 */     this.commandMap = getCommandMap();
/*  80 */     this.bukkitCommands = getBukkitCommands(this.commandMap);
/*     */ 
/*     */     
/*  83 */     registerArgument(Material.class, (sender, arg) -> Material.matchMaterial(arg));
/*  84 */     registerArgument(Player.class, (sender, arg) -> Bukkit.getPlayer(arg));
/*  85 */     registerArgument(World.class, (sender, arg) -> Bukkit.getWorld(arg));
/*     */     
/*  87 */     registerSuggestion(Player.class, (sender, context) -> (List)Bukkit.getOnlinePlayers().stream().map(OfflinePlayer::getName).collect(Collectors.toList()));
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
/*     */   @Contract("_ -> new")
/*     */   @NotNull
/*     */   public static BukkitCommandManager<CommandSender> create(@NotNull Plugin plugin) {
/* 101 */     BukkitCommandManager<CommandSender> commandManager = new BukkitCommandManager<>(plugin, SenderMapper.defaultMapper(), new BukkitSenderValidator());
/*     */ 
/*     */     
/* 104 */     setUpDefaults(commandManager);
/* 105 */     return commandManager;
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
/*     */   @Contract("_, _, _ -> new")
/*     */   @NotNull
/*     */   public static <S> BukkitCommandManager<S> create(@NotNull Plugin plugin, @NotNull SenderMapper<CommandSender, S> senderMapper, @NotNull SenderValidator<S> senderValidator) {
/* 123 */     return new BukkitCommandManager<>(plugin, senderMapper, senderValidator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerCommand(@NotNull BaseCommand baseCommand) {
/* 132 */     BukkitCommandProcessor<S> processor = new BukkitCommandProcessor<>(baseCommand, this.registryContainer, getSenderMapper(), getSenderValidator(), this.syncExecutionProvider, this.asyncExecutionProvider, this.basePermission);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     BukkitCommand<S> command = this.commands.computeIfAbsent(processor.getName(), ignored -> createAndRegisterCommand(processor.getName(), processor));
/*     */     
/* 140 */     processor.addSubCommands(command);
/*     */     
/* 142 */     processor.getAlias().forEach(it -> {
/*     */           BukkitCommand<S> aliasCommand = this.commands.computeIfAbsent(it, ());
/*     */           processor.addSubCommands(aliasCommand);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterCommand(@NotNull BaseCommand command) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected RegistryContainer<S> getRegistryContainer() {
/* 156 */     return this.registryContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private BukkitCommand<S> createAndRegisterCommand(@NotNull String name, @NotNull BukkitCommandProcessor<S> processor) {
/* 162 */     Command oldCommand = this.commandMap.getCommand(name);
/* 163 */     if (oldCommand instanceof PluginIdentifiableCommand && ((PluginIdentifiableCommand)oldCommand).getPlugin() == this.plugin) {
/* 164 */       this.bukkitCommands.remove(name);
/* 165 */       oldCommand.unregister(this.commandMap);
/*     */     } 
/*     */     
/* 168 */     BukkitCommand<S> newCommand = new BukkitCommand<>(name, processor);
/* 169 */     this.commandMap.register(this.plugin.getName(), newCommand);
/* 170 */     return newCommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setUpDefaults(@NotNull BukkitCommandManager<CommandSender> manager) {
/* 179 */     manager.registerMessage((ContextualKey)MessageKey.UNKNOWN_COMMAND, (sender, context) -> sender.sendMessage("Unknown command: `" + context.getCommand() + "`."));
/* 180 */     manager.registerMessage((ContextualKey)MessageKey.TOO_MANY_ARGUMENTS, (sender, context) -> sender.sendMessage("Invalid usage."));
/* 181 */     manager.registerMessage((ContextualKey)MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, context) -> sender.sendMessage("Invalid usage."));
/* 182 */     manager.registerMessage((ContextualKey)MessageKey.INVALID_ARGUMENT, (sender, context) -> sender.sendMessage("Invalid argument `" + context.getTypedArgument() + "` for type `" + context.getArgumentType().getSimpleName() + "`."));
/*     */     
/* 184 */     manager.registerMessage((ContextualKey)BukkitMessageKey.NO_PERMISSION, (sender, context) -> sender.sendMessage("You do not have permission to perform this command. Permission needed: `" + context.getNodes() + "`."));
/* 185 */     manager.registerMessage((ContextualKey)BukkitMessageKey.PLAYER_ONLY, (sender, context) -> sender.sendMessage("This command can only be used by players."));
/* 186 */     manager.registerMessage((ContextualKey)BukkitMessageKey.CONSOLE_ONLY, (sender, context) -> sender.sendMessage("This command can only be used by the console."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private CommandMap getCommandMap() {
/*     */     try {
/* 196 */       Server server = Bukkit.getServer();
/* 197 */       Method getCommandMap = server.getClass().getDeclaredMethod("getCommandMap", new Class[0]);
/* 198 */       getCommandMap.setAccessible(true);
/*     */       
/* 200 */       return (CommandMap)getCommandMap.invoke(server, new Object[0]);
/* 201 */     } catch (Exception ignored) {
/* 202 */       throw new CommandRegistrationException("Unable get Command Map. Commands will not be registered!");
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   private Map<String, Command> getBukkitCommands(@NotNull CommandMap commandMap) {
/*     */     try {
/* 208 */       Field bukkitCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
/* 209 */       bukkitCommands.setAccessible(true);
/*     */       
/* 211 */       return (Map<String, Command>)bukkitCommands.get(commandMap);
/* 212 */     } catch (NoSuchFieldException|IllegalAccessException e) {
/* 213 */       throw new CommandRegistrationException("Unable get Bukkit commands. Commands might not be registered correctly!");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\bukkit\BukkitCommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */