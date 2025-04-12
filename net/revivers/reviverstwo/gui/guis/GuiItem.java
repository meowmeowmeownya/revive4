/*     */ package net.revivers.reviverstwo.gui.guis;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.revivers.reviverstwo.gui.components.GuiAction;
/*     */ import net.revivers.reviverstwo.gui.components.util.ItemNbt;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiItem
/*     */ {
/*     */   private GuiAction<InventoryClickEvent> action;
/*     */   private ItemStack itemStack;
/*  51 */   private final UUID uuid = UUID.randomUUID();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiItem(@NotNull ItemStack itemStack, @Nullable GuiAction<InventoryClickEvent> action) {
/*  60 */     Validate.notNull(itemStack, "The ItemStack for the GUI Item cannot be null!");
/*     */     
/*  62 */     this.action = action;
/*     */ 
/*     */     
/*  65 */     this.itemStack = ItemNbt.setString(itemStack, "mf-gui", this.uuid.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiItem(@NotNull ItemStack itemStack) {
/*  74 */     this(itemStack, (GuiAction<InventoryClickEvent>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiItem(@NotNull Material material) {
/*  83 */     this(new ItemStack(material), (GuiAction<InventoryClickEvent>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiItem(@NotNull Material material, @Nullable GuiAction<InventoryClickEvent> action) {
/*  93 */     this(new ItemStack(material), action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(@NotNull ItemStack itemStack) {
/* 102 */     Validate.notNull(itemStack, "The ItemStack for the GUI Item cannot be null!");
/* 103 */     this.itemStack = ItemNbt.setString(itemStack, "mf-gui", this.uuid.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAction(@Nullable GuiAction<InventoryClickEvent> action) {
/* 112 */     this.action = action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ItemStack getItemStack() {
/* 122 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   UUID getUuid() {
/* 130 */     return this.uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   GuiAction<InventoryClickEvent> getAction() {
/* 138 */     return this.action;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\guis\GuiItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */