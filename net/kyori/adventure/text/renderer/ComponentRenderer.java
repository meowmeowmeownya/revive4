/*    */ package net.kyori.adventure.text.renderer;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ComponentRenderer<C>
/*    */ {
/*    */   @NotNull
/*    */   Component render(@NotNull Component paramComponent, @NotNull C paramC);
/*    */   
/*    */   default <T> ComponentRenderer<T> mapContext(Function<T, C> transformer) {
/* 56 */     return (component, ctx) -> render(component, transformer.apply(ctx));
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\renderer\ComponentRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */