/*     */ package net.kyori.adventure.text.format;
/*     */ 
/*     */ import net.kyori.adventure.util.Index;
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
/*     */ public enum TextDecoration
/*     */   implements StyleBuilderApplicable, TextFormat
/*     */ {
/*  42 */   OBFUSCATED("obfuscated"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   BOLD("bold"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   STRIKETHROUGH("strikethrough"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   UNDERLINED("underlined"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   ITALIC("italic");
/*     */   
/*     */   public static final Index<String, TextDecoration> NAMES;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   static {
/*  73 */     NAMES = Index.create(TextDecoration.class, constant -> constant.name);
/*     */   }
/*     */   
/*     */   TextDecoration(String name) {
/*  77 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final TextDecorationAndState as(boolean state) {
/*  88 */     return as(State.byBoolean(state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final TextDecorationAndState as(@NotNull State state) {
/*  99 */     return new TextDecorationAndStateImpl(this, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void styleApply(Style.Builder style) {
/* 104 */     style.decorate(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 109 */     return this.name;
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
/*     */   public enum State
/*     */   {
/* 123 */     NOT_SET("not_set"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     FALSE("false"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     TRUE("true");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     State(String name) {
/* 140 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 145 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static State byBoolean(boolean flag) {
/* 156 */       return flag ? TRUE : FALSE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static State byBoolean(@Nullable Boolean flag) {
/* 167 */       return (flag == null) ? NOT_SET : byBoolean(flag.booleanValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\format\TextDecoration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */