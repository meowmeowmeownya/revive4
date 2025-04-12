/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import net.kyori.adventure.text.BlockNBTComponent;
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
/*    */ final class BlockNBTComponentPosSerializer
/*    */   extends TypeAdapter<BlockNBTComponent.Pos>
/*    */ {
/* 34 */   static final TypeAdapter<BlockNBTComponent.Pos> INSTANCE = (new BlockNBTComponentPosSerializer()).nullSafe();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockNBTComponent.Pos read(JsonReader in) throws IOException {
/* 41 */     String string = in.nextString();
/*    */     try {
/* 43 */       return BlockNBTComponent.Pos.fromString(string);
/* 44 */     } catch (IllegalArgumentException ex) {
/* 45 */       throw new JsonParseException("Don't know how to turn " + string + " into a Position");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, BlockNBTComponent.Pos value) throws IOException {
/* 51 */     out.value(value.asString());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\BlockNBTComponentPosSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */