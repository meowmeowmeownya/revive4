/*     */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.Flags;
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
/*     */ class FlagsResult<S>
/*     */   implements Flags
/*     */ {
/*  42 */   private final Map<String, FlagValue> flags = new HashMap<>();
/*     */ 
/*     */   
/*     */   private final List<String> args;
/*     */   
/*     */   private final S sender;
/*     */ 
/*     */   
/*     */   FlagsResult(@NotNull S sender, @NotNull Map<FlagOptions<S>, String> flags, @NotNull List<String> args) {
/*  51 */     this.sender = sender;
/*  52 */     flags.forEach(this::addFlag);
/*  53 */     this.args = args;
/*     */   }
/*     */   
/*     */   void addFlag(@NotNull FlagOptions<S> flag, @Nullable String value) {
/*  57 */     String shortFlag = flag.getFlag();
/*  58 */     String longFlag = flag.getLongFlag();
/*     */     
/*  60 */     FlagValue flagValue = (value == null) ? EmptyFlagValue.INSTANCE : new ArgFlagValue(value, flag.getArgument());
/*     */     
/*  62 */     if (shortFlag != null) {
/*  63 */       this.flags.put(shortFlag, flagValue);
/*     */     }
/*     */     
/*  66 */     if (longFlag != null) {
/*  67 */       this.flags.put(longFlag, flagValue);
/*     */     }
/*     */   }
/*     */   
/*     */   void addArg(@NotNull String arg) {
/*  72 */     this.args.add(arg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFlag(@NotNull String flag) {
/*  80 */     return this.flags.containsKey(flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public <T> Optional<T> getValue(@NotNull String flag, @NotNull Class<T> type) {
/*  88 */     FlagValue flagValue = this.flags.get(flag);
/*  89 */     if (flagValue == null) return Optional.empty(); 
/*  90 */     if (!(flagValue instanceof ArgFlagValue)) return Optional.empty(); 
/*  91 */     ArgFlagValue<S> argFlagValue = (ArgFlagValue<S>)flagValue;
/*  92 */     return Optional.ofNullable((T)argFlagValue.getValue(this.sender, type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Optional<String> getValue(@NotNull String flag) {
/* 100 */     FlagValue flagValue = this.flags.get(flag);
/* 101 */     if (flagValue == null) return Optional.empty(); 
/* 102 */     if (!(flagValue instanceof ArgFlagValue)) return Optional.empty(); 
/* 103 */     ArgFlagValue<S> argFlagValue = (ArgFlagValue<S>)flagValue;
/* 104 */     return Optional.of(argFlagValue.getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getText() {
/* 112 */     return getText(" ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getText(@NotNull String delimiter) {
/* 120 */     return String.join(delimiter, (Iterable)this.args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getArgs() {
/* 128 */     return Collections.unmodifiableList(this.args);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\FlagsResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */