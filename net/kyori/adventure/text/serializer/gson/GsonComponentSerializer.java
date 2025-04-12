/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonElement;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.UnaryOperator;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import net.kyori.adventure.text.serializer.ComponentSerializer;
/*    */ import net.kyori.adventure.util.Buildable;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface GsonComponentSerializer
/*    */   extends ComponentSerializer<Component, Component, String>, Buildable<GsonComponentSerializer, GsonComponentSerializer.Builder>
/*    */ {
/*    */   @NotNull
/*    */   static GsonComponentSerializer gson() {
/* 54 */     return GsonComponentSerializerImpl.Instances.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static GsonComponentSerializer colorDownsamplingGson() {
/* 67 */     return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Builder builder() {
/* 77 */     return new GsonComponentSerializerImpl.BuilderImpl();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   Gson serializer();
/*    */   
/*    */   @NotNull
/*    */   UnaryOperator<GsonBuilder> populator();
/*    */   
/*    */   @NotNull
/*    */   Component deserializeFromTree(@NotNull JsonElement paramJsonElement);
/*    */   
/*    */   @NotNull
/*    */   JsonElement serializeToTree(@NotNull Component paramComponent);
/*    */   
/*    */   @Internal
/*    */   public static interface Provider {
/*    */     @Internal
/*    */     @NotNull
/*    */     GsonComponentSerializer gson();
/*    */     
/*    */     @Internal
/*    */     @NotNull
/*    */     GsonComponentSerializer gsonLegacy();
/*    */     
/*    */     @Internal
/*    */     @NotNull
/*    */     Consumer<GsonComponentSerializer.Builder> builder();
/*    */   }
/*    */   
/*    */   public static interface Builder extends Buildable.Builder<GsonComponentSerializer> {
/*    */     @NotNull
/*    */     Builder downsampleColors();
/*    */     
/*    */     @NotNull
/*    */     Builder legacyHoverEventSerializer(@Nullable LegacyHoverEventSerializer param1LegacyHoverEventSerializer);
/*    */     
/*    */     @NotNull
/*    */     Builder emitLegacyHoverEvent();
/*    */     
/*    */     @NotNull
/*    */     GsonComponentSerializer build();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\GsonComponentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */