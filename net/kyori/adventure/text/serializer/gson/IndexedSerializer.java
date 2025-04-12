/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import net.kyori.adventure.util.Index;
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
/*    */ final class IndexedSerializer<E>
/*    */   extends TypeAdapter<E>
/*    */ {
/*    */   private final String name;
/*    */   private final Index<String, E> map;
/*    */   
/*    */   public static <E> TypeAdapter<E> of(String name, Index<String, E> map) {
/* 38 */     return (new IndexedSerializer(name, map)).nullSafe();
/*    */   }
/*    */   
/*    */   private IndexedSerializer(String name, Index<String, E> map) {
/* 42 */     this.name = name;
/* 43 */     this.map = map;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, E value) throws IOException {
/* 48 */     out.value((String)this.map.key(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public E read(JsonReader in) throws IOException {
/* 53 */     String string = in.nextString();
/* 54 */     E value = (E)this.map.value(string);
/* 55 */     if (value != null) {
/* 56 */       return value;
/*    */     }
/* 58 */     throw new JsonParseException("invalid " + this.name + ":  " + string);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\IndexedSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */