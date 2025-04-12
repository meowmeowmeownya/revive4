/*    */ package net.revivers.reviverstwo.gui.components.util;
/*    */ 
/*    */ import org.bukkit.Material;
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
/*    */ public final class SkullUtil
/*    */ {
/* 35 */   private static final Material SKULL = getSkullMaterial();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Material getSkullMaterial() {
/* 43 */     if (VersionHelper.IS_ITEM_LEGACY) {
/* 44 */       return Material.valueOf("SKULL_ITEM");
/*    */     }
/*    */     
/* 47 */     return Material.PLAYER_HEAD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack skull() {
/* 57 */     return VersionHelper.IS_ITEM_LEGACY ? new ItemStack(SKULL, 1, (short)3) : new ItemStack(SKULL);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isPlayerSkull(@NotNull ItemStack item) {
/* 68 */     if (VersionHelper.IS_ITEM_LEGACY) {
/* 69 */       return (item.getType() == SKULL && item.getDurability() == 3);
/*    */     }
/*    */     
/* 72 */     return (item.getType() == SKULL);
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\component\\util\SkullUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */