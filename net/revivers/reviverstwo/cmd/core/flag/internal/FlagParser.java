/*     */ package net.revivers.reviverstwo.cmd.core.flag.internal;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.Flags;
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
/*     */ public final class FlagParser<S>
/*     */ {
/*     */   private final FlagGroup<S> flagGroup;
/*     */   private static final String ESCAPE = "\\";
/*     */   private static final String LONG = "--";
/*     */   private static final String SHORT = "-";
/*     */   private static final int EQUALS = 61;
/*     */   private List<String> hi;
/*     */   
/*     */   public FlagParser(@NotNull FlagGroup<S> flagGroup) {
/*  50 */     this.flagGroup = flagGroup;
/*     */   }
/*     */   @NotNull
/*     */   public Map<FlagOptions<S>, String> parseFlags(@NotNull List<String> toParse) {
/*  54 */     return parseInternal(toParse).getKey();
/*     */   }
/*     */   @NotNull
/*     */   public Flags parse(@NotNull S sender, @NotNull List<String> toParse) {
/*  58 */     Map.Entry<Map<FlagOptions<S>, String>, List<String>> parsed = parseInternal(toParse);
/*  59 */     return new FlagsResult<>(sender, parsed.getKey(), parsed.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Map.Entry<Map<FlagOptions<S>, String>, List<String>> parseInternal(@NotNull List<String> toParse) {
/*  65 */     FlagScanner tokens = new FlagScanner(toParse);
/*     */     
/*  67 */     Map<FlagOptions<S>, String> flags = new LinkedHashMap<>();
/*  68 */     List<String> args = new ArrayList<>();
/*     */     
/*  70 */     while (tokens.hasNext()) {
/*  71 */       String token = tokens.next();
/*     */ 
/*     */       
/*  74 */       if (token.startsWith("\\")) {
/*  75 */         args.add(token);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  80 */       if ((!token.startsWith("--") || "--".equals(token)) && (!token.startsWith("-") || "-".equals(token))) {
/*  81 */         args.add(token);
/*     */         
/*     */         continue;
/*     */       } 
/*  85 */       int equals = token.indexOf('=');
/*     */       
/*  87 */       if (equals == -1) {
/*  88 */         FlagOptions<S> flagOptions = this.flagGroup.getMatchingFlag(token);
/*     */         
/*  90 */         if (flagOptions == null) {
/*  91 */           args.add(token);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*  96 */         if (flagOptions.hasArgument()) {
/*     */           
/*  98 */           if (!tokens.hasNext()) {
/*  99 */             flags.put(flagOptions, "");
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 104 */           flags.put(flagOptions, tokens.next());
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 109 */         flags.put(flagOptions, null);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 114 */       String flagToken = token.substring(0, equals);
/* 115 */       String argToken = token.substring(equals + 1);
/*     */       
/* 117 */       FlagOptions<S> flag = this.flagGroup.getMatchingFlag(flagToken);
/*     */       
/* 119 */       if (flag == null) {
/* 120 */         args.add(token);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 125 */       if (!flag.hasArgument()) {
/* 126 */         args.add(token);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 131 */       flags.put(flag, argToken);
/*     */     } 
/*     */     
/* 134 */     return Maps.immutableEntry(flags, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\flag\internal\FlagParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */