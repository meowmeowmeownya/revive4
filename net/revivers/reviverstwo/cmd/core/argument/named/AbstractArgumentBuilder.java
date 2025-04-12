/*     */ package net.revivers.reviverstwo.cmd.core.argument.named;
/*     */ 
/*     */ import net.revivers.reviverstwo.cmd.core.exceptions.CommandRegistrationException;
/*     */ import net.revivers.reviverstwo.cmd.core.suggestion.SuggestionKey;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ abstract class AbstractArgumentBuilder<T extends AbstractArgumentBuilder<T>>
/*     */ {
/*     */   private final Class<?> type;
/*     */   private String name;
/*  37 */   private String description = "Description!";
/*     */   private SuggestionKey suggestionKey;
/*     */   
/*     */   public AbstractArgumentBuilder(@NotNull Class<?> type) {
/*  41 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract("_ -> this")
/*     */   @NotNull
/*     */   public T name(@NotNull String name) {
/*  52 */     this.name = name;
/*  53 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract("_ -> this")
/*     */   @NotNull
/*     */   public T description(@NotNull String description) {
/*  64 */     this.description = description;
/*  65 */     return (T)this;
/*     */   }
/*     */   @Contract("_ -> this")
/*     */   @NotNull
/*     */   public T suggestion(@NotNull SuggestionKey suggestionKey) {
/*  70 */     this.suggestionKey = suggestionKey;
/*  71 */     return (T)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(" -> new")
/*     */   @NotNull
/*     */   public Argument build() {
/*  81 */     return new SimpleArgument(this);
/*     */   }
/*     */   @NotNull
/*     */   Class<?> getType() {
/*  85 */     return this.type;
/*     */   }
/*     */   @NotNull
/*     */   String getName() {
/*  89 */     if (this.name == null || this.name.isEmpty()) {
/*  90 */       throw new CommandRegistrationException("Argument is missing a name!");
/*     */     }
/*  92 */     return this.name;
/*     */   }
/*     */   @NotNull
/*     */   String getDescription() {
/*  96 */     return this.description;
/*     */   }
/*     */   @Nullable
/*     */   SuggestionKey getSuggestionKey() {
/* 100 */     return this.suggestionKey;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\cmd\core\argument\named\AbstractArgumentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */