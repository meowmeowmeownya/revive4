/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.examination.ExaminableProperty;
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
/*     */ final class SelectorComponentImpl
/*     */   extends AbstractComponent
/*     */   implements SelectorComponent
/*     */ {
/*     */   private final String pattern;
/*     */   @Nullable
/*     */   private final Component separator;
/*     */   
/*     */   SelectorComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String pattern, @Nullable ComponentLike separator) {
/*  41 */     super(children, style);
/*  42 */     this.pattern = pattern;
/*  43 */     this.separator = ComponentLike.unbox(separator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String pattern() {
/*  48 */     return this.pattern;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SelectorComponent pattern(@NotNull String pattern) {
/*  53 */     if (Objects.equals(this.pattern, pattern)) return this; 
/*  54 */     return new SelectorComponentImpl((List)this.children, this.style, Objects.<String>requireNonNull(pattern, "pattern"), this.separator);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component separator() {
/*  59 */     return this.separator;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SelectorComponent separator(@Nullable ComponentLike separator) {
/*  64 */     return new SelectorComponentImpl((List)this.children, this.style, this.pattern, separator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SelectorComponent children(@NotNull List<? extends ComponentLike> children) {
/*  69 */     return new SelectorComponentImpl(children, this.style, this.pattern, this.separator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SelectorComponent style(@NotNull Style style) {
/*  74 */     return new SelectorComponentImpl((List)this.children, style, this.pattern, this.separator);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  79 */     if (this == other) return true; 
/*  80 */     if (!(other instanceof SelectorComponent)) return false; 
/*  81 */     if (!super.equals(other)) return false; 
/*  82 */     SelectorComponent that = (SelectorComponent)other;
/*  83 */     return (Objects.equals(this.pattern, that.pattern()) && Objects.equals(this.separator, that.separator()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  88 */     int result = super.hashCode();
/*  89 */     result = 31 * result + this.pattern.hashCode();
/*  90 */     result = 31 * result + Objects.hashCode(this.separator);
/*  91 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/*  96 */     return Stream.concat(
/*  97 */         Stream.of(new ExaminableProperty[] {
/*  98 */             ExaminableProperty.of("pattern", this.pattern), 
/*  99 */             ExaminableProperty.of("separator", this.separator)
/*     */           
/* 101 */           }), super.examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SelectorComponent.Builder toBuilder() {
/* 107 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends AbstractComponentBuilder<SelectorComponent, SelectorComponent.Builder> implements SelectorComponent.Builder { @Nullable
/*     */     private String pattern;
/*     */     @Nullable
/*     */     private Component separator;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull SelectorComponent component) {
/* 118 */       super(component);
/* 119 */       this.pattern = component.pattern();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public SelectorComponent.Builder pattern(@NotNull String pattern) {
/* 124 */       this.pattern = pattern;
/* 125 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public SelectorComponent.Builder separator(@Nullable ComponentLike separator) {
/* 130 */       this.separator = ComponentLike.unbox(separator);
/* 131 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public SelectorComponent build() {
/* 136 */       if (this.pattern == null) throw new IllegalStateException("pattern must be set"); 
/* 137 */       return new SelectorComponentImpl((List)this.children, buildStyle(), this.pattern, this.separator);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\SelectorComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */