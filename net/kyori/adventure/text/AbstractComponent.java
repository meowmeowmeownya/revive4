/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.examination.ExaminableProperty;
/*     */ import net.kyori.examination.Examiner;
/*     */ import net.kyori.examination.string.StringExaminer;
/*     */ import org.jetbrains.annotations.Debug.Renderer;
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
/*     */ @Renderer(text = "this.debuggerString()", childrenArray = "this.children().toArray()", hasChildren = "!this.children().isEmpty()")
/*     */ public abstract class AbstractComponent
/*     */   implements Component
/*     */ {
/*     */   private static final Predicate<Component> NOT_EMPTY;
/*     */   protected final List<Component> children;
/*     */   protected final Style style;
/*     */   
/*     */   static {
/*  48 */     NOT_EMPTY = (component -> (component != Component.empty()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractComponent(@NotNull List<? extends ComponentLike> children, @NotNull Style style) {
/*  54 */     this.children = ComponentLike.asComponents(children, NOT_EMPTY);
/*  55 */     this.style = style;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public final List<Component> children() {
/*  60 */     return this.children;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public final Style style() {
/*  65 */     return this.style;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component replaceText(@NotNull Consumer<TextReplacementConfig.Builder> configurer) {
/*  70 */     Objects.requireNonNull(configurer, "configurer");
/*  71 */     return replaceText((TextReplacementConfig)Buildable.configureAndBuild(TextReplacementConfig.builder(), configurer));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component replaceText(@NotNull TextReplacementConfig config) {
/*  76 */     Objects.requireNonNull(config, "replacement");
/*  77 */     if (!(config instanceof TextReplacementConfigImpl)) {
/*  78 */       throw new IllegalArgumentException("Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
/*     */     }
/*  80 */     return TextReplacementRenderer.INSTANCE.render(this, ((TextReplacementConfigImpl)config).createState());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component compact() {
/*  85 */     return ComponentCompaction.compact(this, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  90 */     if (this == other) return true; 
/*  91 */     if (!(other instanceof AbstractComponent)) return false; 
/*  92 */     AbstractComponent that = (AbstractComponent)other;
/*  93 */     return (Objects.equals(this.children, that.children) && 
/*  94 */       Objects.equals(this.style, that.style));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  99 */     int result = this.children.hashCode();
/* 100 */     result = 31 * result + this.style.hashCode();
/* 101 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private String debuggerString() {
/* 106 */     return (String)StringExaminer.simpleEscaping().examine(examinableName(), examinablePropertiesWithoutChildren());
/*     */   }
/*     */   
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 110 */     return Stream.of(ExaminableProperty.of("style", this.style));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 115 */     return Stream.concat(
/* 116 */         examinablePropertiesWithoutChildren(), 
/* 117 */         Stream.of(
/* 118 */           ExaminableProperty.of("children", this.children)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\AbstractComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */