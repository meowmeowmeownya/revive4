/*    */ package net.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import net.kyori.adventure.text.event.ClickEvent;
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
/*    */ final class ClickEventActionSerializer
/*    */ {
/* 30 */   static final TypeAdapter<ClickEvent.Action> INSTANCE = IndexedSerializer.of("click action", ClickEvent.Action.NAMES);
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\ClickEventActionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */