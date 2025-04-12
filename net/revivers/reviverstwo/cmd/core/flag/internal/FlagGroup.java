/*     */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class FlagGroup<S>
/*     */ {
/*  42 */   private final Map<String, FlagOptions<S>> flags = new HashMap<>();
/*  43 */   private final Map<String, FlagOptions<S>> longFlags = new HashMap<>();
/*     */   
/*  45 */   private final List<String> allFlags = new ArrayList<>();
/*     */   @NotNull
/*     */   public Map<String, FlagOptions<S>> getFlags() {
/*  48 */     return this.flags;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, FlagOptions<S>> getLongFlags() {
/*  52 */     return this.longFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFlag(@NotNull FlagOptions<S> flagOptions) {
/*  61 */     String key = flagOptions.getKey();
/*     */     
/*  63 */     String longFlag = flagOptions.getLongFlag();
/*  64 */     if (longFlag != null) {
/*  65 */       this.allFlags.add("--" + longFlag);
/*  66 */       this.longFlags.put(longFlag, flagOptions);
/*     */     } 
/*     */     
/*  69 */     this.allFlags.add("-" + key);
/*  70 */     this.flags.put(key, flagOptions);
/*     */   }
/*     */   @NotNull
/*     */   public List<String> getAllFlags() {
/*  74 */     return this.allFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  83 */     return (this.flags.isEmpty() && this.longFlags.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public FlagOptions<S> getMatchingFlag(@NotNull String token) {
/*  93 */     String stripped = stripLeadingHyphens(token);
/*     */     
/*  95 */     FlagOptions<S> flag = this.flags.get(stripped);
/*  96 */     return (flag != null) ? flag : this.longFlags.get(stripped);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private String stripLeadingHyphens(@NotNull String token) {
/* 106 */     if (token.startsWith("--")) return token.substring(2); 
/* 107 */     if (token.startsWith("-")) return token.substring(1); 
/* 108 */     return token;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\FlagGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */