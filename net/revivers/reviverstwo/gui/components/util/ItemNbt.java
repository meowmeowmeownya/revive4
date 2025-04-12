/*    */ package net.revivers.reviverstwo.gui.components.util;
/*    */ 
/*    */ import net.revivers.reviverstwo.gui.components.nbt.LegacyNbt;
/*    */ import net.revivers.reviverstwo.gui.components.nbt.NbtWrapper;
/*    */ import net.revivers.reviverstwo.gui.components.nbt.Pdc;
/*    */ import org.bukkit.inventory.ItemStack;
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
/*    */ public final class ItemNbt
/*    */ {
/* 38 */   private static final NbtWrapper nbt = selectNbt();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack setString(@NotNull ItemStack itemStack, @NotNull String key, @NotNull String value) {
/* 49 */     return nbt.setString(itemStack, key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getString(@NotNull ItemStack itemStack, @NotNull String key) {
/* 60 */     return nbt.getString(itemStack, key);
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
/*    */   
/*    */   public static ItemStack setBoolean(@NotNull ItemStack itemStack, @NotNull String key, boolean value) {
/* 73 */     return nbt.setBoolean(itemStack, key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack removeTag(@NotNull ItemStack itemStack, @NotNull String key) {
/* 84 */     return nbt.removeTag(itemStack, key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static NbtWrapper selectNbt() {
/* 93 */     if (VersionHelper.IS_PDC_VERSION) return (NbtWrapper)new Pdc(); 
/* 94 */     return (NbtWrapper)new LegacyNbt();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\component\\util\ItemNbt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */