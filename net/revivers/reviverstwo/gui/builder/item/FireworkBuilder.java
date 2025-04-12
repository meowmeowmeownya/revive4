/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.FireworkEffectMeta;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public class FireworkBuilder
/*     */   extends BaseItemBuilder<FireworkBuilder>
/*     */ {
/*  46 */   private static final Material STAR = Material.FIREWORK_STAR;
/*  47 */   private static final Material ROCKET = Material.FIREWORK_ROCKET;
/*     */   
/*     */   FireworkBuilder(@NotNull ItemStack itemStack) {
/*  50 */     super(itemStack);
/*  51 */     if (itemStack.getType() != STAR && itemStack.getType() != ROCKET) {
/*  52 */       throw new GuiException("FireworkBuilder requires the material to be a FIREWORK_STAR/FIREWORK_ROCKET!");
/*     */     }
/*     */   }
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
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public FireworkBuilder effect(@NotNull FireworkEffect... effects) {
/*  68 */     return effect(Arrays.asList(effects));
/*     */   }
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
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public FireworkBuilder effect(@NotNull List<FireworkEffect> effects) {
/*  83 */     if (effects.isEmpty()) {
/*  84 */       return this;
/*     */     }
/*     */     
/*  87 */     if (getItemStack().getType() == STAR) {
/*  88 */       FireworkEffectMeta effectMeta = (FireworkEffectMeta)getMeta();
/*     */       
/*  90 */       effectMeta.setEffect(effects.get(0));
/*  91 */       setMeta((ItemMeta)effectMeta);
/*  92 */       return this;
/*     */     } 
/*     */     
/*  95 */     FireworkMeta fireworkMeta = (FireworkMeta)getMeta();
/*     */     
/*  97 */     fireworkMeta.addEffects(effects);
/*  98 */     setMeta((ItemMeta)fireworkMeta);
/*  99 */     return this;
/*     */   }
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
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public FireworkBuilder power(int power) {
/* 114 */     if (getItemStack().getType() == ROCKET) {
/* 115 */       FireworkMeta fireworkMeta = (FireworkMeta)getMeta();
/*     */       
/* 117 */       fireworkMeta.setPower(power);
/* 118 */       setMeta((ItemMeta)fireworkMeta);
/*     */     } 
/*     */     
/* 121 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\FireworkBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */