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
/*     */ 
/*     */ final class KeybindComponentImpl
/*     */   extends AbstractComponent
/*     */   implements KeybindComponent
/*     */ {
/*     */   private final String keybind;
/*     */   
/*     */   KeybindComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String keybind) {
/*  40 */     super(children, style);
/*  41 */     this.keybind = Objects.<String>requireNonNull(keybind, "keybind");
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String keybind() {
/*  46 */     return this.keybind;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public KeybindComponent keybind(@NotNull String keybind) {
/*  51 */     if (Objects.equals(this.keybind, keybind)) return this; 
/*  52 */     return new KeybindComponentImpl((List)this.children, this.style, Objects.<String>requireNonNull(keybind, "keybind"));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public KeybindComponent children(@NotNull List<? extends ComponentLike> children) {
/*  57 */     return new KeybindComponentImpl(children, this.style, this.keybind);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public KeybindComponent style(@NotNull Style style) {
/*  62 */     return new KeybindComponentImpl((List)this.children, style, this.keybind);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  67 */     if (this == other) return true; 
/*  68 */     if (!(other instanceof KeybindComponent)) return false; 
/*  69 */     if (!super.equals(other)) return false; 
/*  70 */     KeybindComponent that = (KeybindComponent)other;
/*  71 */     return Objects.equals(this.keybind, that.keybind());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  76 */     int result = super.hashCode();
/*  77 */     result = 31 * result + this.keybind.hashCode();
/*  78 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/*  83 */     return Stream.concat(
/*  84 */         Stream.of(
/*  85 */           ExaminableProperty.of("keybind", this.keybind)), super
/*     */         
/*  87 */         .examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public KeybindComponent.Builder toBuilder() {
/*  93 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends AbstractComponentBuilder<KeybindComponent, KeybindComponent.Builder> implements KeybindComponent.Builder {
/*     */     @Nullable
/*     */     private String keybind;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull KeybindComponent component) {
/* 103 */       super(component);
/* 104 */       this.keybind = component.keybind();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public KeybindComponent.Builder keybind(@NotNull String keybind) {
/* 109 */       this.keybind = keybind;
/* 110 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public KeybindComponent build() {
/* 115 */       if (this.keybind == null) throw new IllegalStateException("keybind must be set"); 
/* 116 */       return new KeybindComponentImpl((List)this.children, buildStyle(), this.keybind);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\KeybindComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */