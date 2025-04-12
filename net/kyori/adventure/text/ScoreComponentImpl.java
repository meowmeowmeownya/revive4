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
/*     */ final class ScoreComponentImpl
/*     */   extends AbstractComponent
/*     */   implements ScoreComponent
/*     */ {
/*     */   private final String name;
/*     */   private final String objective;
/*     */   @Deprecated
/*     */   @Nullable
/*     */   private final String value;
/*     */   
/*     */   ScoreComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String name, @NotNull String objective, @Nullable String value) {
/*  43 */     super(children, style);
/*  44 */     this.name = name;
/*  45 */     this.objective = objective;
/*  46 */     this.value = value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String name() {
/*  51 */     return this.name;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ScoreComponent name(@NotNull String name) {
/*  56 */     if (Objects.equals(this.name, name)) return this; 
/*  57 */     return new ScoreComponentImpl((List)this.children, this.style, Objects.<String>requireNonNull(name, "name"), this.objective, this.value);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String objective() {
/*  62 */     return this.objective;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ScoreComponent objective(@NotNull String objective) {
/*  67 */     if (Objects.equals(this.objective, objective)) return this; 
/*  68 */     return new ScoreComponentImpl((List)this.children, this.style, this.name, Objects.<String>requireNonNull(objective, "objective"), this.value);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public String value() {
/*  74 */     return this.value;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public ScoreComponent value(@Nullable String value) {
/*  80 */     if (Objects.equals(this.value, value)) return this; 
/*  81 */     return new ScoreComponentImpl((List)this.children, this.style, this.name, this.objective, value);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ScoreComponent children(@NotNull List<? extends ComponentLike> children) {
/*  86 */     return new ScoreComponentImpl(children, this.style, this.name, this.objective, this.value);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ScoreComponent style(@NotNull Style style) {
/*  91 */     return new ScoreComponentImpl((List)this.children, style, this.name, this.objective, this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  97 */     if (this == other) return true; 
/*  98 */     if (!(other instanceof ScoreComponent)) return false; 
/*  99 */     if (!super.equals(other)) return false; 
/* 100 */     ScoreComponent that = (ScoreComponent)other;
/* 101 */     return (Objects.equals(this.name, that.name()) && 
/* 102 */       Objects.equals(this.objective, that.objective()) && 
/* 103 */       Objects.equals(this.value, that.value()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 108 */     int result = super.hashCode();
/* 109 */     result = 31 * result + this.name.hashCode();
/* 110 */     result = 31 * result + this.objective.hashCode();
/* 111 */     result = 31 * result + Objects.hashCode(this.value);
/* 112 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 117 */     return Stream.concat(
/* 118 */         Stream.of(new ExaminableProperty[] {
/* 119 */             ExaminableProperty.of("name", this.name), 
/* 120 */             ExaminableProperty.of("objective", this.objective), 
/* 121 */             ExaminableProperty.of("value", this.value)
/*     */           
/* 123 */           }), super.examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ScoreComponent.Builder toBuilder() {
/* 129 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends AbstractComponentBuilder<ScoreComponent, ScoreComponent.Builder> implements ScoreComponent.Builder { @Nullable
/*     */     private String name;
/*     */     @Nullable
/*     */     private String objective;
/*     */     @Nullable
/*     */     private String value;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull ScoreComponent component) {
/* 142 */       super(component);
/* 143 */       this.name = component.name();
/* 144 */       this.objective = component.objective();
/* 145 */       this.value = component.value();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public ScoreComponent.Builder name(@NotNull String name) {
/* 150 */       this.name = name;
/* 151 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public ScoreComponent.Builder objective(@NotNull String objective) {
/* 156 */       this.objective = objective;
/* 157 */       return this;
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     @NotNull
/*     */     public ScoreComponent.Builder value(@Nullable String value) {
/* 163 */       this.value = value;
/* 164 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public ScoreComponent build() {
/* 169 */       if (this.name == null) throw new IllegalStateException("name must be set"); 
/* 170 */       if (this.objective == null) throw new IllegalStateException("objective must be set"); 
/* 171 */       return new ScoreComponentImpl((List)this.children, buildStyle(), this.name, this.objective, this.value);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\ScoreComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */