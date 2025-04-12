/*     */ package net.revivers.reviverstwo.cmd.core.argument;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.Arguments;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.NamedArgumentParser;
/*     */ import net.revivers.reviverstwo.cmd.core.argument.named.NamedArgumentResult;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.EmptySuggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.Suggestion;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionContext;
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
/*     */ public final class NamedInternalArgument<S>
/*     */   extends LimitlessInternalArgument<S>
/*     */ {
/*     */   private final Map<String, InternalArgument<S, ?>> arguments;
/*     */   
/*     */   public NamedInternalArgument(@NotNull String name, @NotNull String description, @NotNull Map<String, InternalArgument<S, ?>> arguments, int position, boolean isOptional) {
/*  52 */     super(name, description, Arguments.class, (Suggestion<S>)new EmptySuggestion(), position, isOptional);
/*  53 */     this.arguments = arguments;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Object resolve(@NotNull S sender, @NotNull List<String> value) {
/*  58 */     Map<String, String> parsedArgs = NamedArgumentParser.parse(String.join(" ", (Iterable)value));
/*  59 */     Map<String, Object> mapped = new HashMap<>(parsedArgs.size());
/*     */     
/*  61 */     for (Map.Entry<String, String> entry : parsedArgs.entrySet()) {
/*  62 */       String key = entry.getKey();
/*  63 */       InternalArgument<S, ?> argument = this.arguments.get(key);
/*  64 */       if (argument == null)
/*  65 */         continue;  Object resolved = resolveArgument(sender, argument, entry.getValue());
/*  66 */       mapped.put(key, resolved);
/*     */     } 
/*     */     
/*  69 */     return new NamedArgumentResult(mapped);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> suggestions(@NotNull S sender, @NotNull List<String> trimmed, @NotNull SuggestionContext context) {
/*     */     String argName;
/*  78 */     Map<String, String> parsedArgs = NamedArgumentParser.parse(String.join(" ", (Iterable)trimmed));
/*  79 */     String current = trimmed.get(trimmed.size() - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     List<String> notUsed = (List<String>)this.arguments.keySet().stream().filter(it -> (parsedArgs.get(it) == null)).filter(it -> it.startsWith(current)).map(it -> it + ":").collect(Collectors.toList());
/*     */     
/*  88 */     if (notUsed.size() > 1) return notUsed;
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (notUsed.size() == 1) {
/*  93 */       argName = ((String)notUsed.get(0)).replace(":", "");
/*     */     } else {
/*  95 */       List<String> parsed = new ArrayList<>(parsedArgs.keySet());
/*  96 */       if (parsed.size() == 0) return Collections.emptyList(); 
/*  97 */       argName = parsed.get(parsed.size() - 1);
/*     */     } 
/*     */     
/* 100 */     InternalArgument<S, ?> argument = this.arguments.get(argName);
/*     */     
/* 102 */     if (argument != null) {
/* 103 */       String raw = argName + ":";
/* 104 */       List<String> parsed = argument.suggestions(sender, 
/*     */           
/* 106 */           Collections.singletonList(!current.contains(raw) ? "" : current.replace(raw, "")), context);
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (parsed.isEmpty()) return Collections.singletonList(raw);
/*     */       
/* 112 */       return (List<String>)parsed
/* 113 */         .stream()
/* 114 */         .map(it -> argName + ":" + it)
/* 115 */         .collect(Collectors.toList());
/*     */     } 
/*     */     
/* 118 */     return notUsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Object resolveArgument(@NotNull S sender, @NotNull InternalArgument<S, ?> argument, @NotNull String value) {
/* 127 */     if (argument instanceof StringInternalArgument) {
/* 128 */       return ((StringInternalArgument)argument).resolve(sender, value);
/*     */     }
/*     */     
/* 131 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 136 */     return "NamedInternalArgument{arguments=" + this.arguments + ", super=" + super
/*     */       
/* 138 */       .toString() + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\NamedInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */