/*     */ package net.revivers.reviverstwo.cmd.core.processor;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.Command;
/*     */ import net.revivers.reviverstwo.cmd.core.SubCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Command;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Description;
/*     */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandRegistrationException;
/*     */ import net.revivers.reviverstwo.cmd.core.execution.ExecutionProvider;
/*     */ import net.revivers.reviverstwo.cmd.core.registry.RegistryContainer;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderMapper;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
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
/*     */ public abstract class AbstractCommandProcessor<SD, S, SC extends SubCommand<S>, P extends AbstractSubCommandProcessor<S>>
/*     */ {
/*     */   private String name;
/*  57 */   private String description = "No description provided.";
/*  58 */   private final List<String> alias = new ArrayList<>();
/*  59 */   private final Map<String, SC> subCommands = new HashMap<>();
/*  60 */   private final Map<String, SC> subCommandsAlias = new HashMap<>();
/*     */ 
/*     */   
/*     */   private final BaseCommand baseCommand;
/*     */ 
/*     */   
/*     */   private final RegistryContainer<S> registryContainer;
/*     */   
/*     */   private final SenderMapper<SD, S> senderMapper;
/*     */   
/*     */   private final SenderValidator<S> senderValidator;
/*     */   
/*     */   private final ExecutionProvider syncExecutionProvider;
/*     */   
/*     */   private final ExecutionProvider asyncExecutionProvider;
/*     */ 
/*     */   
/*     */   protected AbstractCommandProcessor(@NotNull BaseCommand baseCommand, @NotNull RegistryContainer<S> registryContainer, @NotNull SenderMapper<SD, S> senderMapper, @NotNull SenderValidator<S> senderValidator, @NotNull ExecutionProvider syncExecutionProvider, @NotNull ExecutionProvider asyncExecutionProvider) {
/*  78 */     this.baseCommand = baseCommand;
/*  79 */     this.registryContainer = registryContainer;
/*  80 */     this.senderMapper = senderMapper;
/*  81 */     this.senderValidator = senderValidator;
/*  82 */     this.syncExecutionProvider = syncExecutionProvider;
/*  83 */     this.asyncExecutionProvider = asyncExecutionProvider;
/*     */     
/*  85 */     extractCommandNames();
/*  86 */     extractDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSubCommands(Command<S, SC> command) {
/*  91 */     for (Method method : this.baseCommand.getClass().getDeclaredMethods()) {
/*  92 */       if (!Modifier.isPrivate(method.getModifiers())) {
/*     */         
/*  94 */         P processor = createProcessor(method);
/*  95 */         String subCommandName = processor.getName();
/*  96 */         if (subCommandName != null) {
/*     */           
/*  98 */           ExecutionProvider executionProvider = processor.isAsync() ? this.asyncExecutionProvider : this.syncExecutionProvider;
/*     */           
/* 100 */           SC subCommand = createSubCommand(processor, executionProvider);
/* 101 */           command.addSubCommand(subCommandName, (SubCommand)subCommand);
/*     */           
/* 103 */           processor.getAlias().forEach(alias -> command.addSubCommandAlias(alias, subCommand));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getName() {
/* 117 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getAlias() {
/* 126 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public BaseCommand getBaseCommand() {
/* 135 */     return this.baseCommand;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public RegistryContainer<S> getRegistryContainer() {
/* 140 */     return this.registryContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SenderMapper<SD, S> getSenderMapper() {
/* 149 */     return this.senderMapper;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SenderValidator<S> getSenderValidator() {
/* 154 */     return this.senderValidator;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, SC> getSubCommands() {
/* 158 */     return this.subCommands;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, SC> getSubCommandsAlias() {
/* 162 */     return this.subCommandsAlias;
/*     */   }
/*     */   @NotNull
/*     */   public ExecutionProvider getSyncExecutionProvider() {
/* 166 */     return this.syncExecutionProvider;
/*     */   }
/*     */   @NotNull
/*     */   public ExecutionProvider getAsyncExecutionProvider() {
/* 170 */     return this.asyncExecutionProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getDescription() {
/* 179 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractCommandNames() {
/* 186 */     Command commandAnnotation = this.baseCommand.getClass().<Command>getAnnotation(Command.class);
/*     */     
/* 188 */     if (commandAnnotation == null) {
/* 189 */       String commandName = this.baseCommand.getCommand();
/* 190 */       if (commandName == null) {
/* 191 */         throw new CommandRegistrationException("Command name or \"@" + Command.class.getSimpleName() + "\" annotation missing", this.baseCommand.getClass());
/*     */       }
/*     */       
/* 194 */       this.name = commandName;
/* 195 */       this.alias.addAll(this.baseCommand.getAlias());
/*     */     } else {
/* 197 */       this.name = commandAnnotation.value();
/* 198 */       Collections.addAll(this.alias, commandAnnotation.alias());
/*     */     } 
/*     */     
/* 201 */     this.alias.addAll(this.baseCommand.getAlias());
/*     */     
/* 203 */     if (this.name.isEmpty()) {
/* 204 */       throw new CommandRegistrationException("Command name must not be empty", this.baseCommand.getClass());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractDescription() {
/* 212 */     Description description = this.baseCommand.getClass().<Description>getAnnotation(Description.class);
/* 213 */     if (description == null)
/* 214 */       return;  this.description = description.value();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected abstract P createProcessor(@NotNull Method paramMethod);
/*     */   
/*     */   @NotNull
/*     */   protected abstract SC createSubCommand(@NotNull P paramP, @NotNull ExecutionProvider paramExecutionProvider);
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\processor\AbstractCommandProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */