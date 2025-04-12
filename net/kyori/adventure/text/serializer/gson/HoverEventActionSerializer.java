/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import net.kyori.adventure.text.event.HoverEvent;
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
/*    */ final class HoverEventActionSerializer
/*    */ {
/* 30 */   static final TypeAdapter<HoverEvent.Action<?>> INSTANCE = IndexedSerializer.of("hover action", HoverEvent.Action.NAMES);
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\HoverEventActionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */