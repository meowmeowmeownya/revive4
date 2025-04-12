/*     */ package net.kyori.adventure.text.serializer.legacy;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Pattern;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.TextComponent;
/*     */ import net.kyori.adventure.text.flattener.ComponentFlattener;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.text.serializer.ComponentSerializer;
/*     */ import net.kyori.adventure.util.Buildable;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ 
/*     */ public interface LegacyComponentSerializer
/*     */   extends ComponentSerializer<Component, TextComponent, String>, Buildable<LegacyComponentSerializer, LegacyComponentSerializer.Builder>
/*     */ {
/*     */   public static final char SECTION_CHAR = '§';
/*     */   public static final char AMPERSAND_CHAR = '&';
/*     */   public static final char HEX_CHAR = '#';
/*     */   
/*     */   @NotNull
/*     */   static LegacyComponentSerializer legacySection() {
/*  60 */     return LegacyComponentSerializerImpl.Instances.SECTION;
/*     */   }
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
/*     */   @NotNull
/*     */   static LegacyComponentSerializer legacyAmpersand() {
/*  74 */     return LegacyComponentSerializerImpl.Instances.AMPERSAND;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static LegacyComponentSerializer legacy(char legacyCharacter) {
/*  87 */     if (legacyCharacter == '§')
/*  88 */       return legacySection(); 
/*  89 */     if (legacyCharacter == '&') {
/*  90 */       return legacyAmpersand();
/*     */     }
/*  92 */     return builder().character(legacyCharacter).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static LegacyFormat parseChar(char character) {
/* 103 */     return LegacyComponentSerializerImpl.legacyFormat(character);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder builder() {
/* 113 */     return new LegacyComponentSerializerImpl.BuilderImpl();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   TextComponent deserialize(@NotNull String paramString);
/*     */   
/*     */   @NotNull
/*     */   String serialize(@NotNull Component paramComponent);
/*     */   
/*     */   @Internal
/*     */   public static interface Provider {
/*     */     @Internal
/*     */     @NotNull
/*     */     LegacyComponentSerializer legacyAmpersand();
/*     */     
/*     */     @Internal
/*     */     @NotNull
/*     */     LegacyComponentSerializer legacySection();
/*     */     
/*     */     @Internal
/*     */     @NotNull
/*     */     Consumer<LegacyComponentSerializer.Builder> legacy();
/*     */   }
/*     */   
/*     */   public static interface Builder extends Buildable.Builder<LegacyComponentSerializer> {
/*     */     @NotNull
/*     */     Builder character(char param1Char);
/*     */     
/*     */     @NotNull
/*     */     Builder hexCharacter(char param1Char);
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls();
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls(@NotNull Pattern param1Pattern);
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls(@Nullable Style param1Style);
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls(@NotNull Pattern param1Pattern, @Nullable Style param1Style);
/*     */     
/*     */     @NotNull
/*     */     Builder hexColors();
/*     */     
/*     */     @NotNull
/*     */     Builder useUnusualXRepeatedCharacterHexFormat();
/*     */     
/*     */     @NotNull
/*     */     Builder flattener(@NotNull ComponentFlattener param1ComponentFlattener);
/*     */     
/*     */     @NotNull
/*     */     LegacyComponentSerializer build();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\legacy\LegacyComponentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */