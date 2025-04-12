/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import net.kyori.adventure.text.format.TextDecoration;
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
/*    */ final class TextDecorationSerializer
/*    */ {
/* 30 */   static final TypeAdapter<TextDecoration> INSTANCE = IndexedSerializer.of("text decoration", TextDecoration.NAMES);
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\TextDecorationSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */