/*     */ package net.kyori.adventure.text;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
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
/*     */ final class TranslatableComponentImpl
/*     */   extends AbstractComponent
/*     */   implements TranslatableComponent
/*     */ {
/*     */   private final String key;
/*     */   private final List<Component> args;
/*     */   
/*     */   TranslatableComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String key, @NotNull ComponentLike[] args) {
/*  44 */     this((List)children, style, key, Arrays.asList(args));
/*     */   }
/*     */   
/*     */   TranslatableComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String key, @NotNull List<? extends ComponentLike> args) {
/*  48 */     super(children, style);
/*  49 */     this.key = Objects.<String>requireNonNull(key, "key");
/*     */     
/*  51 */     this.args = ComponentLike.asComponents(args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String key() {
/*  56 */     return this.key;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent key(@NotNull String key) {
/*  61 */     if (Objects.equals(this.key, key)) return this; 
/*  62 */     return new TranslatableComponentImpl((List)this.children, this.style, Objects.<String>requireNonNull(key, "key"), (List)this.args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<Component> args() {
/*  67 */     return this.args;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent args(@NotNull ComponentLike... args) {
/*  72 */     return new TranslatableComponentImpl(this.children, this.style, this.key, args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent args(@NotNull List<? extends ComponentLike> args) {
/*  77 */     return new TranslatableComponentImpl((List)this.children, this.style, this.key, args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent children(@NotNull List<? extends ComponentLike> children) {
/*  82 */     return new TranslatableComponentImpl(children, this.style, this.key, (List)this.args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent style(@NotNull Style style) {
/*  87 */     return new TranslatableComponentImpl((List)this.children, style, this.key, (List)this.args);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/*  92 */     if (this == other) return true; 
/*  93 */     if (!(other instanceof TranslatableComponent)) return false; 
/*  94 */     if (!super.equals(other)) return false; 
/*  95 */     TranslatableComponent that = (TranslatableComponent)other;
/*  96 */     return (Objects.equals(this.key, that.key()) && Objects.equals(this.args, that.args()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     int result = super.hashCode();
/* 102 */     result = 31 * result + this.key.hashCode();
/* 103 */     result = 31 * result + this.args.hashCode();
/* 104 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
/* 109 */     return Stream.concat(
/* 110 */         Stream.of(new ExaminableProperty[] {
/* 111 */             ExaminableProperty.of("key", this.key), 
/* 112 */             ExaminableProperty.of("args", this.args)
/*     */           
/* 114 */           }), super.examinablePropertiesWithoutChildren());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent.Builder toBuilder() {
/* 120 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends AbstractComponentBuilder<TranslatableComponent, TranslatableComponent.Builder> implements TranslatableComponent.Builder { @Nullable
/*     */     private String key;
/* 125 */     private List<? extends Component> args = Collections.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BuilderImpl(@NotNull TranslatableComponent component) {
/* 131 */       super(component);
/* 132 */       this.key = component.key();
/* 133 */       this.args = component.args();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder key(@NotNull String key) {
/* 138 */       this.key = key;
/* 139 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull ComponentBuilder<?, ?> arg) {
/* 144 */       return args(Collections.singletonList((ComponentLike)arg.build()));
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull ComponentBuilder<?, ?>... args) {
/* 150 */       if (args.length == 0) return args(Collections.emptyList()); 
/* 151 */       return args((List<? extends ComponentLike>)Stream.<ComponentBuilder<?, ?>>of(args).map(ComponentBuilder::build).collect(Collectors.toList()));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull Component arg) {
/* 156 */       return args(Collections.singletonList(arg));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull ComponentLike... args) {
/* 161 */       if (args.length == 0) return args(Collections.emptyList()); 
/* 162 */       return args(Arrays.asList(args));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull List<? extends ComponentLike> args) {
/* 167 */       this.args = ComponentLike.asComponents(args);
/* 168 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponentImpl build() {
/* 173 */       if (this.key == null) throw new IllegalStateException("key must be set"); 
/* 174 */       return new TranslatableComponentImpl((List)this.children, buildStyle(), this.key, (List)this.args);
/*     */     }
/*     */     
/*     */     BuilderImpl() {} }
/*     */ 
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\TranslatableComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */