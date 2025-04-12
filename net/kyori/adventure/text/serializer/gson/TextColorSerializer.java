/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import net.kyori.adventure.text.format.NamedTextColor;
/*    */ import net.kyori.adventure.text.format.TextColor;
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
/*    */ final class TextColorSerializer
/*    */   extends TypeAdapter<TextColor>
/*    */ {
/* 36 */   static final TypeAdapter<TextColor> INSTANCE = (new TextColorSerializer(false)).nullSafe();
/* 37 */   static final TypeAdapter<TextColor> DOWNSAMPLE_COLOR = (new TextColorSerializer(true)).nullSafe();
/*    */   
/*    */   private final boolean downsampleColor;
/*    */   
/*    */   private TextColorSerializer(boolean downsampleColor) {
/* 42 */     this.downsampleColor = downsampleColor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, TextColor value) throws IOException {
/* 47 */     if (value instanceof NamedTextColor) {
/* 48 */       out.value((String)NamedTextColor.NAMES.key(value));
/* 49 */     } else if (this.downsampleColor) {
/* 50 */       out.value((String)NamedTextColor.NAMES.key(NamedTextColor.nearestTo(value)));
/*    */     } else {
/* 52 */       out.value(value.asHexString());
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public TextColor read(JsonReader in) throws IOException {
/* 58 */     TextColor color = fromString(in.nextString());
/* 59 */     if (color == null) return null;
/*    */     
/* 61 */     return this.downsampleColor ? (TextColor)NamedTextColor.nearestTo(color) : color;
/*    */   }
/*    */   @Nullable
/*    */   static TextColor fromString(@NotNull String value) {
/* 65 */     if (value.startsWith("#")) {
/* 66 */       return TextColor.fromHexString(value);
/*    */     }
/* 68 */     return (TextColor)NamedTextColor.NAMES.value(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\TextColorSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */