/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NonExtendable
/*     */ public interface JoinConfiguration
/*     */   extends Buildable<JoinConfiguration, JoinConfiguration.Builder>, Examinable
/*     */ {
/*     */   @NotNull
/*     */   static Builder builder() {
/*  89 */     return new JoinConfigurationImpl.BuilderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static JoinConfiguration noSeparators() {
/*  99 */     return JoinConfigurationImpl.NULL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static JoinConfiguration separator(@Nullable ComponentLike separator) {
/* 110 */     if (separator == null) return JoinConfigurationImpl.NULL; 
/* 111 */     return (JoinConfiguration)builder().separator(separator).build();
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
/*     */   static JoinConfiguration separators(@Nullable ComponentLike separator, @Nullable ComponentLike lastSeparator) {
/* 123 */     if (separator == null && lastSeparator == null) return JoinConfigurationImpl.NULL; 
/* 124 */     return (JoinConfiguration)builder().separator(separator).lastSeparator(lastSeparator).build();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Component prefix();
/*     */   
/*     */   @Nullable
/*     */   Component suffix();
/*     */   
/*     */   @Nullable
/*     */   Component separator();
/*     */   
/*     */   @Nullable
/*     */   Component lastSeparator();
/*     */   
/*     */   @Nullable
/*     */   Component lastSeparatorIfSerial();
/*     */   
/*     */   @NotNull
/*     */   Function<ComponentLike, Component> convertor();
/*     */   
/*     */   @NotNull
/*     */   Predicate<ComponentLike> predicate();
/*     */   
/*     */   public static interface Builder extends Buildable.Builder<JoinConfiguration> {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder prefix(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder suffix(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder separator(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder lastSeparator(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder lastSeparatorIfSerial(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder convertor(@NotNull Function<ComponentLike, Component> param1Function);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder predicate(@NotNull Predicate<ComponentLike> param1Predicate);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\JoinConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */