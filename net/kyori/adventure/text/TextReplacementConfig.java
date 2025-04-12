/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Pattern;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.adventure.util.IntFunction2;
/*     */ import net.kyori.examination.Examinable;
/*     */ import org.intellij.lang.annotations.RegExp;
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
/*     */ public interface TextReplacementConfig
/*     */   extends Buildable<TextReplacementConfig, TextReplacementConfig.Builder>, Examinable
/*     */ {
/*     */   @NotNull
/*     */   static Builder builder() {
/*  56 */     return new TextReplacementConfigImpl.Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Pattern matchPattern();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Condition
/*     */   {
/*     */     @NotNull
/*     */     PatternReplacementResult shouldReplace(@NotNull MatchResult param1MatchResult, int param1Int1, int param1Int2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Builder
/*     */     extends Buildable.Builder<TextReplacementConfig>
/*     */   {
/*     */     @Contract("_ -> this")
/*     */     default Builder matchLiteral(String literal) {
/*  90 */       return match(Pattern.compile(literal, 16));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder match(@NotNull @RegExp String pattern) {
/* 102 */       return match(Pattern.compile(pattern));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     default Builder once() {
/* 128 */       return times(1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder times(int times) {
/* 140 */       return condition((index, replaced) -> (replaced < times) ? PatternReplacementResult.REPLACE : PatternReplacementResult.STOP);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder condition(@NotNull IntFunction2<PatternReplacementResult> condition) {
/* 153 */       return condition((result, matchCount, replaced) -> (PatternReplacementResult)condition.apply(matchCount, replaced));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder replacement(@NotNull String replacement) {
/* 182 */       Objects.requireNonNull(replacement, "replacement");
/* 183 */       return replacement(builder -> builder.content(replacement));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder replacement(@Nullable ComponentLike replacement) {
/* 195 */       Component baked = (replacement == null) ? null : replacement.asComponent();
/* 196 */       return replacement((result, input) -> baked);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder replacement(@NotNull Function<TextComponent.Builder, ComponentLike> replacement) {
/* 208 */       Objects.requireNonNull(replacement, "replacement");
/* 209 */       return replacement((result, input) -> (ComponentLike)replacement.apply(input));
/*     */     }
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder match(@NotNull Pattern param1Pattern);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder condition(@NotNull TextReplacementConfig.Condition param1Condition);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder replacement(@NotNull BiFunction<MatchResult, TextComponent.Builder, ComponentLike> param1BiFunction);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\TextReplacementConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */