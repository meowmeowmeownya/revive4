/*    */ package net.kyori.adventure.text.flattener;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import net.kyori.adventure.util.Buildable;
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
/*    */ public interface ComponentFlattener
/*    */   extends Buildable<ComponentFlattener, ComponentFlattener.Builder>
/*    */ {
/*    */   @NotNull
/*    */   static Builder builder() {
/* 47 */     return new ComponentFlattenerImpl.BuilderImpl();
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
/*    */   static ComponentFlattener basic() {
/* 60 */     return ComponentFlattenerImpl.BASIC;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static ComponentFlattener textOnly() {
/* 72 */     return ComponentFlattenerImpl.TEXT_ONLY;
/*    */   }
/*    */   
/*    */   void flatten(@NotNull Component paramComponent, @NotNull FlattenerListener paramFlattenerListener);
/*    */   
/*    */   public static interface Builder extends Buildable.Builder<ComponentFlattener> {
/*    */     @NotNull
/*    */     <T extends Component> Builder mapper(@NotNull Class<T> param1Class, @NotNull Function<T, String> param1Function);
/*    */     
/*    */     @NotNull
/*    */     <T extends Component> Builder complexMapper(@NotNull Class<T> param1Class, @NotNull BiConsumer<T, Consumer<Component>> param1BiConsumer);
/*    */     
/*    */     @NotNull
/*    */     Builder unknownMapper(@Nullable Function<Component, String> param1Function);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\flattener\ComponentFlattener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */