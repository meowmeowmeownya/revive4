/*     */ package net.kyori.adventure.title;
/*     */ 
/*     */ import java.time.Duration;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.util.Ticks;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ @NonExtendable
/*     */ public interface Title
/*     */   extends Examinable
/*     */ {
/*  48 */   public static final Times DEFAULT_TIMES = Times.of(Ticks.duration(10L), Ticks.duration(70L), Ticks.duration(20L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Title title(@NotNull Component title, @NotNull Component subtitle) {
/*  59 */     return title(title, subtitle, DEFAULT_TIMES);
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
/*     */   @NotNull
/*     */   static Title title(@NotNull Component title, @NotNull Component subtitle, @Nullable Times times) {
/*  72 */     return new TitleImpl(title, subtitle, times);
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
/*     */   Component title();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Component subtitle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   Times times();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T> T part(@NotNull TitlePart<T> paramTitlePart);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Times
/*     */     extends Examinable
/*     */   {
/*     */     @NotNull
/*     */     static Times of(@NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
/* 125 */       return new TitleImpl.TimesImpl(fadeIn, stay, fadeOut);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     Duration fadeIn();
/*     */     
/*     */     @NotNull
/*     */     Duration stay();
/*     */     
/*     */     @NotNull
/*     */     Duration fadeOut();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\title\Title.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */