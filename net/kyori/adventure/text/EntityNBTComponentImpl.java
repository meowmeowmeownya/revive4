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
/*     */ final class EntityNBTComponentImpl
/*     */   extends NBTComponentImpl<EntityNBTComponent, EntityNBTComponent.Builder>
/*     */   implements EntityNBTComponent
/*     */ {
/*     */   private final String selector;
/*     */   
/*     */   EntityNBTComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, String nbtPath, boolean interpret, @Nullable ComponentLike separator, String selector) {
/*  38 */     super(children, style, nbtPath, interpret, separator);
/*  39 */     this.selector = selector;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntityNBTComponent nbtPath(@NotNull String nbtPath) {
/*  44 */     if (Objects.equals(this.nbtPath, nbtPath)) return this; 
/*  45 */     return new EntityNBTComponentImpl((List)this.children, this.style, nbtPath, this.interpret, this.separator, this.selector);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntityNBTComponent interpret(boolean interpret) {
/*  50 */     if (this.interpret == interpret) return this; 
/*  51 */     return new EntityNBTComponentImpl((List)this.children, this.style, this.nbtPath, interpret, this.separator, this.selector);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component separator() {
/*  56 */     return this.separator;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntityNBTComponent separator(@Nullable ComponentLike separator) {
/*  61 */     return new EntityNBTComponentImpl((List)this.children, this.style, this.nbtPath, this.interpret, separator, this.selector);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String selector() {
/*  66 */     return this.selector;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntityNBTComponent selector(@NotNull String selector) {
/*  71 */     if (Objects.equals(this.selector, selector)) return this; 
/*  72 */     return new EntityNBTComponentImpl((List)this.children, this.style, this.nbtPath, this.interpret, this.separator, selector);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntityNBTComponent children(@NotNull List<? extends ComponentLike> children) {
/*  77 */     return new EntityNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.selector);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EntityNBTComponent style(@NotNull Style style) {
/*  82 */     return new EntityNBTComponentImpl((List)this.children, style, this.nbtPath, this.interpret, this.separator, this.selector);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  87 */     if (this == other) return true; 
/*  88 */     if (!(other instanceof EntityNBTComponent)) return false; 
/*  89 */     if (!super.equals(other)) return false; 
/*  90 */     EntityNBTComponentImpl that = (EntityNBTComponentImpl)other;
/*  91 */     return Objects.equals(this.selector, that.selector());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     int result = super.hashCode();
/*  97 */     result = 31 * result + this.selector.hashCode();
/*  98 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 103 */     return Stream.concat(
/* 104 */         Stream.of(
/* 105 */           ExaminableProperty.of("selector", this.selector)), super
/*     */         
/* 107 */         .examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityNBTComponent.Builder toBuilder() {
/* 113 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends NBTComponentImpl.BuilderImpl<EntityNBTComponent, EntityNBTComponent.Builder> implements EntityNBTComponent.Builder {
/*     */     @Nullable
/*     */     private String selector;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull EntityNBTComponent component) {
/* 123 */       super(component);
/* 124 */       this.selector = component.selector();
/*     */     }
/*     */ 
/*     */     
/*     */     public EntityNBTComponent.Builder selector(@NotNull String selector) {
/* 129 */       this.selector = selector;
/* 130 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public EntityNBTComponent build() {
/* 135 */       if (this.nbtPath == null) throw new IllegalStateException("nbt path must be set"); 
/* 136 */       if (this.selector == null) throw new IllegalStateException("selector must be set"); 
/* 137 */       return new EntityNBTComponentImpl((List)this.children, buildStyle(), this.nbtPath, this.interpret, this.separator, this.selector);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\EntityNBTComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */