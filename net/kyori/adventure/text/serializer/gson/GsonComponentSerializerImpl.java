/*     */ package net.kyori.adventure.text.serializer.gson;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.UnaryOperator;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import net.kyori.adventure.util.Services;
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
/*     */ final class GsonComponentSerializerImpl
/*     */   implements GsonComponentSerializer
/*     */ {
/*  38 */   private static final Optional<GsonComponentSerializer.Provider> SERVICE = Services.service(GsonComponentSerializer.Provider.class); private final Gson serializer; private final UnaryOperator<GsonBuilder> populator; private final boolean downsampleColor;
/*  39 */   static final Consumer<GsonComponentSerializer.Builder> BUILDER = SERVICE
/*  40 */     .<Consumer<GsonComponentSerializer.Builder>>map(GsonComponentSerializer.Provider::builder)
/*  41 */     .orElseGet(() -> ());
/*     */   @Nullable
/*     */   private final LegacyHoverEventSerializer legacyHoverSerializer;
/*     */   private final boolean emitLegacyHover;
/*     */   
/*     */   static final class Instances {
/*  47 */     static final GsonComponentSerializer INSTANCE = GsonComponentSerializerImpl.SERVICE
/*  48 */       .map(GsonComponentSerializer.Provider::gson)
/*  49 */       .orElseGet(() -> new GsonComponentSerializerImpl(false, null, false));
/*  50 */     static final GsonComponentSerializer LEGACY_INSTANCE = GsonComponentSerializerImpl.SERVICE
/*  51 */       .map(GsonComponentSerializer.Provider::gsonLegacy)
/*  52 */       .orElseGet(() -> new GsonComponentSerializerImpl(true, null, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GsonComponentSerializerImpl(boolean downsampleColor, @Nullable LegacyHoverEventSerializer legacyHoverSerializer, boolean emitLegacyHover) {
/*  62 */     this.downsampleColor = downsampleColor;
/*  63 */     this.legacyHoverSerializer = legacyHoverSerializer;
/*  64 */     this.emitLegacyHover = emitLegacyHover;
/*  65 */     this.populator = (builder -> {
/*     */         builder.registerTypeAdapterFactory(new SerializerFactory(downsampleColor, legacyHoverSerializer, emitLegacyHover));
/*     */         return builder;
/*     */       });
/*  69 */     this.serializer = ((GsonBuilder)this.populator.apply(new GsonBuilder())).create();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Gson serializer() {
/*  74 */     return this.serializer;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public UnaryOperator<GsonBuilder> populator() {
/*  79 */     return this.populator;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component deserialize(@NotNull String string) {
/*  84 */     Component component = (Component)serializer().fromJson(string, Component.class);
/*  85 */     if (component == null) throw ComponentSerializerImpl.notSureHowToDeserialize(string); 
/*  86 */     return component;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String serialize(@NotNull Component component) {
/*  91 */     return serializer().toJson(component);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component deserializeFromTree(@NotNull JsonElement input) {
/*  96 */     Component component = (Component)serializer().fromJson(input, Component.class);
/*  97 */     if (component == null) throw ComponentSerializerImpl.notSureHowToDeserialize(input); 
/*  98 */     return component;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public JsonElement serializeToTree(@NotNull Component component) {
/* 103 */     return serializer().toJsonTree(component);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public GsonComponentSerializer.Builder toBuilder() {
/* 108 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl implements GsonComponentSerializer.Builder { private boolean downsampleColor = false;
/*     */     @Nullable
/*     */     private LegacyHoverEventSerializer legacyHoverSerializer;
/*     */     private boolean emitLegacyHover = false;
/*     */     
/*     */     BuilderImpl() {
/* 117 */       GsonComponentSerializerImpl.BUILDER.accept(this);
/*     */     }
/*     */     
/*     */     BuilderImpl(GsonComponentSerializerImpl serializer) {
/* 121 */       this();
/* 122 */       this.downsampleColor = serializer.downsampleColor;
/* 123 */       this.emitLegacyHover = serializer.emitLegacyHover;
/* 124 */       this.legacyHoverSerializer = serializer.legacyHoverSerializer;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer.Builder downsampleColors() {
/* 129 */       this.downsampleColor = true;
/* 130 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer.Builder legacyHoverEventSerializer(@Nullable LegacyHoverEventSerializer serializer) {
/* 135 */       this.legacyHoverSerializer = serializer;
/* 136 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer.Builder emitLegacyHoverEvent() {
/* 141 */       this.emitLegacyHover = true;
/* 142 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer build() {
/* 147 */       if (this.legacyHoverSerializer == null) {
/* 148 */         return this.downsampleColor ? GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE : GsonComponentSerializerImpl.Instances.INSTANCE;
/*     */       }
/* 150 */       return new GsonComponentSerializerImpl(this.downsampleColor, this.legacyHoverSerializer, this.emitLegacyHover);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\GsonComponentSerializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */