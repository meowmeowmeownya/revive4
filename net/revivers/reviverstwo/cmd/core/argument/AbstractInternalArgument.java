/*     */ package net.revivers.reviverstwo.cmd.core.argument;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInternalArgument<S, T>
/*     */   implements InternalArgument<S, T>
/*     */ {
/*     */   private final String name;
/*     */   private final String description;
/*     */   private final Class<?> type;
/*     */   private final int position;
/*     */   private final boolean optional;
/*     */   private final Suggestion<S> suggestion;
/*     */   
/*     */   public AbstractInternalArgument(@NotNull String name, @NotNull String description, @NotNull Class<?> type, @NotNull Suggestion<S> suggestion, int position, boolean optional) {
/*  58 */     this.name = name;
/*  59 */     this.description = description;
/*  60 */     this.type = type;
/*  61 */     this.suggestion = suggestion;
/*  62 */     this.position = position;
/*  63 */     this.optional = optional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> suggestions(@NotNull S sender, @NotNull List<String> trimmed, @NotNull SuggestionContext context) {
/*  72 */     return this.suggestion.getSuggestions(sender, trimmed.get(0), context);
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
/*     */   public String getName() {
/*  84 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition() {
/*  89 */     return this.position;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getDescription() {
/*  94 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Class<?> getType() {
/* 105 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptional() {
/* 115 */     return this.optional;
/*     */   }
/*     */   @NotNull
/*     */   protected Suggestion<S> getSuggestion() {
/* 119 */     return this.suggestion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 124 */     if (this == o) return true; 
/* 125 */     if (o == null || getClass() != o.getClass()) return false; 
/* 126 */     AbstractInternalArgument<?, ?> internalArgument = (AbstractInternalArgument<?, ?>)o;
/* 127 */     return (this.optional == internalArgument.optional && this.name.equals(internalArgument.name) && this.type.equals(internalArgument.type));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 132 */     return Objects.hash(new Object[] { this.name, this.type, Boolean.valueOf(this.optional) });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 137 */     return "AbstractInternalArgument{name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", type=" + this.type + ", position=" + this.position + ", optional=" + this.optional + ", suggestion=" + this.suggestion + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\AbstractInternalArgument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */