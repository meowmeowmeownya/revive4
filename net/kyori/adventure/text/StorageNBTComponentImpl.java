/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.kyori.adventure.key.Key;
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
/*     */ final class StorageNBTComponentImpl
/*     */   extends NBTComponentImpl<StorageNBTComponent, StorageNBTComponent.Builder>
/*     */   implements StorageNBTComponent
/*     */ {
/*     */   private final Key storage;
/*     */   
/*     */   StorageNBTComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, String nbtPath, boolean interpret, @Nullable ComponentLike separator, Key storage) {
/*  39 */     super(children, style, nbtPath, interpret, separator);
/*  40 */     this.storage = storage;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public StorageNBTComponent nbtPath(@NotNull String nbtPath) {
/*  45 */     if (Objects.equals(this.nbtPath, nbtPath)) return this; 
/*  46 */     return new StorageNBTComponentImpl((List)this.children, this.style, nbtPath, this.interpret, this.separator, this.storage);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public StorageNBTComponent interpret(boolean interpret) {
/*  51 */     if (this.interpret == interpret) return this; 
/*  52 */     return new StorageNBTComponentImpl((List)this.children, this.style, this.nbtPath, interpret, this.separator, this.storage);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component separator() {
/*  57 */     return this.separator;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public StorageNBTComponent separator(@Nullable ComponentLike separator) {
/*  62 */     return new StorageNBTComponentImpl((List)this.children, this.style, this.nbtPath, this.interpret, separator, this.storage);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Key storage() {
/*  67 */     return this.storage;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public StorageNBTComponent storage(@NotNull Key storage) {
/*  72 */     if (Objects.equals(this.storage, storage)) return this; 
/*  73 */     return new StorageNBTComponentImpl((List)this.children, this.style, this.nbtPath, this.interpret, this.separator, storage);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public StorageNBTComponent children(@NotNull List<? extends ComponentLike> children) {
/*  78 */     return new StorageNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.storage);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public StorageNBTComponent style(@NotNull Style style) {
/*  83 */     return new StorageNBTComponentImpl((List)this.children, style, this.nbtPath, this.interpret, this.separator, this.storage);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  88 */     if (this == other) return true; 
/*  89 */     if (!(other instanceof StorageNBTComponent)) return false; 
/*  90 */     if (!super.equals(other)) return false; 
/*  91 */     StorageNBTComponentImpl that = (StorageNBTComponentImpl)other;
/*  92 */     return Objects.equals(this.storage, that.storage());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     int result = super.hashCode();
/*  98 */     result = 31 * result + this.storage.hashCode();
/*  99 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 104 */     return Stream.concat(
/* 105 */         Stream.of(
/* 106 */           ExaminableProperty.of("storage", this.storage)), super
/*     */         
/* 108 */         .examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StorageNBTComponent.Builder toBuilder() {
/* 114 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static class BuilderImpl extends NBTComponentImpl.BuilderImpl<StorageNBTComponent, StorageNBTComponent.Builder> implements StorageNBTComponent.Builder {
/*     */     @Nullable
/*     */     private Key storage;
/*     */     
/*     */     BuilderImpl() {}
/*     */     
/*     */     BuilderImpl(@NotNull StorageNBTComponent component) {
/* 124 */       super(component);
/* 125 */       this.storage = component.storage();
/*     */     }
/*     */ 
/*     */     
/*     */     public StorageNBTComponent.Builder storage(@NotNull Key storage) {
/* 130 */       this.storage = storage;
/* 131 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public StorageNBTComponent build() {
/* 136 */       if (this.nbtPath == null) throw new IllegalStateException("nbt path must be set"); 
/* 137 */       if (this.storage == null) throw new IllegalStateException("storage must be set"); 
/* 138 */       return new StorageNBTComponentImpl((List)this.children, buildStyle(), this.nbtPath, this.interpret, this.separator, this.storage);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\StorageNBTComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */