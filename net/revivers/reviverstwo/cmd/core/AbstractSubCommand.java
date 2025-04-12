/*     */ package net.revivers.reviverstwo.cmd.core;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.InternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.LimitlessInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.StringInternalArgument;
/*     */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandExecutionException;
/*     */ import net.revivers.reviverstwo.cmd.core.execution.ExecutionProvider;
/*     */ import net.revivers.reviverstwo.cmd.core.message.ContextualKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageKey;
/*     */ import net.revivers.reviverstwo.cmd.core.message.MessageRegistry;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.DefaultMessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.InvalidArgumentContext;
/*     */ import net.revivers.reviverstwo.cmd.core.message.context.MessageContext;
/*     */ import net.revivers.reviverstwo.cmd.core.processor.AbstractSubCommandProcessor;
/*     */ import net.revivers.reviverstwo.cmd.core.requirement.Requirement;
/*     */ import net.revivers.reviverstwo.cmd.core.sender.SenderValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSubCommand<S>
/*     */   implements SubCommand<S>
/*     */ {
/*     */   private final BaseCommand baseCommand;
/*     */   private final Method method;
/*     */   private final String parentName;
/*     */   private final String name;
/*     */   private final List<String> alias;
/*     */   private final boolean isDefault;
/*     */   private final Class<? extends S> senderType;
/*     */   private final List<InternalArgument<S, ?>> internalArguments;
/*     */   private final Set<Requirement<S, ?>> requirements;
/*     */   private final MessageRegistry<S> messageRegistry;
/*     */   private final ExecutionProvider executionProvider;
/*     */   private final SenderValidator<S> senderValidator;
/*     */   private final boolean hasArguments;
/*     */   private final boolean containsLimitless;
/*     */   
/*     */   public AbstractSubCommand(@NotNull AbstractSubCommandProcessor<S> processor, @NotNull String parentName, @NotNull ExecutionProvider executionProvider) {
/*  85 */     this.baseCommand = processor.getBaseCommand();
/*  86 */     this.method = processor.getMethod();
/*  87 */     this.name = processor.getName();
/*  88 */     this.alias = processor.getAlias();
/*  89 */     this.internalArguments = processor.getArguments();
/*  90 */     this.requirements = processor.getRequirements();
/*  91 */     this.messageRegistry = processor.getMessageRegistry();
/*  92 */     this.isDefault = processor.isDefault();
/*  93 */     this.senderValidator = processor.getSenderValidator();
/*     */     
/*  95 */     this.senderType = processor.getSenderType();
/*     */     
/*  97 */     this.parentName = parentName;
/*     */     
/*  99 */     this.executionProvider = executionProvider;
/*     */     
/* 101 */     this.hasArguments = !this.internalArguments.isEmpty();
/* 102 */     Objects.requireNonNull(LimitlessInternalArgument.class); this.containsLimitless = this.internalArguments.stream().anyMatch(LimitlessInternalArgument.class::isInstance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 113 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Class<? extends S> getSenderType() {
/* 119 */     return this.senderType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getParentName() {
/* 129 */     return this.parentName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getName() {
/* 139 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArguments() {
/* 144 */     return this.hasArguments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected MessageRegistry<S> getMessageRegistry() {
/* 153 */     return this.messageRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(@NotNull S sender, @NotNull List<String> args) {
/* 164 */     if (!this.senderValidator.validate(this.messageRegistry, this, sender))
/* 165 */       return;  if (!meetRequirements(sender)) {
/*     */       return;
/*     */     }
/* 168 */     List<Object> invokeArguments = new ArrayList();
/* 169 */     invokeArguments.add(sender);
/*     */     
/* 171 */     if (!validateAndCollectArguments(sender, invokeArguments, args)) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     if (!this.containsLimitless && args.size() >= invokeArguments.size()) {
/* 176 */       this.messageRegistry.sendMessage((ContextualKey)MessageKey.TOO_MANY_ARGUMENTS, sender, (MessageContext)new DefaultMessageContext(this.parentName, this.name));
/*     */       
/*     */       return;
/*     */     } 
/* 180 */     this.executionProvider.execute(() -> {
/*     */           try {
/*     */             this.method.invoke(this.baseCommand, invokeArguments.toArray());
/* 183 */           } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException exception) {
/*     */             throw (new CommandExecutionException("An error occurred while executing the command", this.parentName, this.name)).initCause((exception instanceof java.lang.reflect.InvocationTargetException) ? exception.getCause() : exception);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected List<InternalArgument<S, ?>> getArguments() {
/* 196 */     return this.internalArguments;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected InternalArgument<S, ?> getArgument(@NotNull String name) {
/* 202 */     List<InternalArgument<S, ?>> foundArgs = (List<InternalArgument<S, ?>>)this.internalArguments.stream().filter(internalArgument -> internalArgument.getName().toLowerCase().startsWith(name)).collect(Collectors.toList());
/*     */     
/* 204 */     if (foundArgs.size() != 1) return null; 
/* 205 */     return foundArgs.get(0);
/*     */   }
/*     */   @Nullable
/*     */   protected InternalArgument<S, ?> getArgument(int index) {
/* 209 */     int size = this.internalArguments.size();
/* 210 */     if (size == 0) return null; 
/* 211 */     if (index >= size) {
/* 212 */       InternalArgument<S, ?> last = this.internalArguments.get(size - 1);
/* 213 */       if (last instanceof LimitlessInternalArgument) return last; 
/* 214 */       return null;
/*     */     } 
/*     */     
/* 217 */     return this.internalArguments.get(index);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<String> mapArguments(@NotNull Map<String, String> args) {
/* 222 */     List<String> arguments = (List<String>)getArguments().stream().map(InternalArgument::getName).collect(Collectors.toList());
/* 223 */     return (List<String>)arguments.stream().map(it -> {
/*     */           String value = (String)args.get(it);
/*     */           return (value == null) ? "" : value;
/* 226 */         }).collect(Collectors.toList());
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
/*     */   private boolean validateAndCollectArguments(@NotNull S sender, @NotNull List<Object> invokeArguments, @NotNull List<String> commandArgs) {
/* 243 */     for (int i = 0; i < this.internalArguments.size(); i++) {
/* 244 */       InternalArgument<S, ?> internalArgument = this.internalArguments.get(i);
/*     */       
/* 246 */       if (internalArgument instanceof LimitlessInternalArgument) {
/* 247 */         LimitlessInternalArgument<S> limitlessArgument = (LimitlessInternalArgument)internalArgument;
/* 248 */         List<String> leftOvers = leftOvers(commandArgs, i);
/*     */         
/* 250 */         Object result = limitlessArgument.resolve(sender, leftOvers);
/*     */         
/* 252 */         if (result == null) {
/* 253 */           return false;
/*     */         }
/*     */         
/* 256 */         invokeArguments.add(result);
/* 257 */         return true;
/*     */       } 
/*     */       
/* 260 */       if (!(internalArgument instanceof StringInternalArgument)) {
/* 261 */         throw new CommandExecutionException("Found unsupported internalArgument", this.parentName, this.name);
/*     */       }
/*     */       
/* 264 */       StringInternalArgument<S> stringArgument = (StringInternalArgument)internalArgument;
/* 265 */       String arg = valueOrNull(commandArgs, i);
/*     */       
/* 267 */       if (arg == null || arg.isEmpty()) {
/* 268 */         if (internalArgument.isOptional()) {
/* 269 */           invokeArguments.add((Object)null);
/*     */         }
/*     */         else {
/*     */           
/* 273 */           this.messageRegistry.sendMessage((ContextualKey)MessageKey.NOT_ENOUGH_ARGUMENTS, sender, (MessageContext)new DefaultMessageContext(this.parentName, this.name));
/* 274 */           return false;
/*     */         } 
/*     */       } else {
/* 277 */         Object result = stringArgument.resolve(sender, arg);
/* 278 */         if (result == null) {
/* 279 */           this.messageRegistry.sendMessage((ContextualKey)MessageKey.INVALID_ARGUMENT, sender, (MessageContext)new InvalidArgumentContext(this.parentName, this.name, arg, internalArgument
/*     */ 
/*     */                 
/* 282 */                 .getName(), internalArgument.getType()));
/*     */           
/* 284 */           return false;
/*     */         } 
/*     */         
/* 287 */         invokeArguments.add(result);
/*     */       } 
/*     */     } 
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean meetRequirements(@NotNull S sender) {
/* 300 */     for (Requirement<S, ?> requirement : this.requirements) {
/* 301 */       if (!requirement.isMet(sender)) {
/* 302 */         requirement.sendMessage(this.messageRegistry, sender, this.parentName, this.name);
/* 303 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String valueOrNull(@NotNull List<String> list, int index) {
/* 318 */     if (index >= list.size()) return null; 
/* 319 */     return list.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private List<String> leftOvers(@NotNull List<String> list, int from) {
/* 330 */     if (from > list.size()) return Collections.emptyList(); 
/* 331 */     return list.subList(from, list.size());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 336 */     return "SimpleSubCommand{baseCommand=" + this.baseCommand + ", method=" + this.method + ", name='" + this.name + '\'' + ", alias=" + this.alias + ", isDefault=" + this.isDefault + ", arguments=" + this.internalArguments + ", requirements=" + this.requirements + ", messageRegistry=" + this.messageRegistry + ", containsLimitlessArgument=" + this.containsLimitless + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\AbstractSubCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */