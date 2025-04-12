/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import net.kyori.adventure.key.Key;
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
/*    */ final class KeySerializer
/*    */   extends TypeAdapter<Key>
/*    */ {
/* 33 */   static final TypeAdapter<Key> INSTANCE = (new KeySerializer()).nullSafe();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, Key value) throws IOException {
/* 40 */     out.value(value.asString());
/*    */   }
/*    */ 
/*    */   
/*    */   public Key read(JsonReader in) throws IOException {
/* 45 */     return Key.key(in.nextString());
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\KeySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */