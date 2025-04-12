/*     */ package net.revivers.reviverstwo.cmd.core.argument;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.Flags;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.internal.FlagGroup;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.internal.FlagOptions;
/*     */ import net.revivers.reviverstwo.cmd.core.flag.internal.FlagParser;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FlagInternalArgument<S>
/*     */   extends LimitlessInternalArgument<S>
/*     */ {
/*     */   private final FlagGroup<S> flagGroup;
/*     */   private final FlagParser<S> flagParser;
/*     */   
/*     */   public FlagInternalArgument(@NotNull String name, @NotNull String description, @NotNull FlagGroup<S> flagGroup, int position, boolean isOptional) {
/*  61 */     super(name, description, Flags.class, (Suggestion<S>)new EmptySuggestion(), position, isOptional);
/*  62 */     this.flagGroup = flagGroup;
/*  63 */     this.flagParser = new FlagParser(flagGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Object resolve(@NotNull S sender, @NotNull List<String> value) {
/*  75 */     return this.flagParser.parse(sender, (value.size() == 1) ? Arrays.<String>asList(((String)value.get(0)).split(" ")) : value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> suggestions(@NotNull S sender, @NotNull List<String> trimmed, @NotNull SuggestionContext context) {
/*  84 */     int size = trimmed.size();
/*  85 */     String current = trimmed.get(size - 1);
/*     */ 
/*     */     
/*  88 */     List<String> flags = this.flagGroup.getAllFlags();
/*     */ 
/*     */     
/*  91 */     List<Map.Entry<FlagOptions<S>, String>> parsed = new ArrayList<>(this.flagParser.parseFlags(trimmed).entrySet());
/*  92 */     List<String> used = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*  96 */     for (Map.Entry<FlagOptions<S>, String> entry : parsed) {
/*  97 */       FlagOptions<S> options = entry.getKey();
/*  98 */       String flag = options.getFlag();
/*  99 */       String longFlag = options.getLongFlag();
/*     */       
/* 101 */       if (flag != null) used.add("-" + flag); 
/* 102 */       if (longFlag != null) used.add("--" + longFlag);
/*     */     
/*     */     } 
/*     */     
/* 106 */     if (!parsed.isEmpty()) {
/*     */       
/* 108 */       Map.Entry<FlagOptions<S>, String> last = parsed.get(parsed.size() - 1);
/* 109 */       FlagOptions<S> flagOptions = last.getKey();
/*     */ 
/*     */       
/* 112 */       if (!current.contains("=")) {
/*     */         
/* 114 */         if (size > 1)
/*     */         {
/* 116 */           if (flagOptions.hasArgument() && flags.contains(trimmed.get(size - 2))) {
/* 117 */             return flagOptions.getArgument().suggestions(sender, Collections.singletonList(current), context);
/*     */           }
/*     */         }
/*     */       } else {
/*     */         
/* 122 */         String[] split = current.split("=");
/*     */         
/* 124 */         if (split.length == 0) return Collections.emptyList();
/*     */         
/* 126 */         String flag = split[0];
/* 127 */         String arg = (split.length != 2) ? "" : split[1];
/*     */ 
/*     */         
/* 130 */         if (flagOptions.hasArgument()) {
/* 131 */           return (List<String>)flagOptions
/* 132 */             .getArgument()
/* 133 */             .suggestions(sender, Collections.singletonList(arg), context)
/* 134 */             .stream()
/* 135 */             .map(it -> flag + "=" + it)
/* 136 */             .collect(Collectors.toList());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 142 */     return (List<String>)flags
/* 143 */       .stream()
/* 144 */       .filter(it -> !used.contains(it))
/* 145 */       .filter(it -> it.toLowerCase().startsWith(current.toLowerCase()))
/* 146 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 151 */     if (this == o) return true; 
/* 152 */     if (o == null || getClass() != o.getClass()) return false; 
/* 153 */     if (!super.equals(o)) return false; 
/* 154 */     FlagInternalArgument<?> that = (FlagInternalArgument)o;
/* 155 */     return this.flagGroup.equals(that.flagGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 160 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.flagGroup });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 165 */     return "FlagArgument{flagGroup=" + this.flagGroup + ", super=" + super
/*     */       
/* 167 */       .toString() + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\FlagInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */