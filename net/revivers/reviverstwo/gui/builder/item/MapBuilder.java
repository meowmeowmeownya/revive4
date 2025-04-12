/*     */ package net.revivers.reviverstwo.gui.builder.item;
/*     */ 
/*     */ import net.revivers.reviverstwo.gui.components.exception.GuiException;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.MapMeta;
/*     */ import org.bukkit.map.MapView;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ public class MapBuilder
/*     */   extends BaseItemBuilder<MapBuilder>
/*     */ {
/*  44 */   private static final Material MAP = Material.MAP;
/*     */   
/*     */   MapBuilder() {
/*  47 */     super(new ItemStack(MAP));
/*     */   }
/*     */   
/*     */   MapBuilder(@NotNull ItemStack itemStack) {
/*  51 */     super(itemStack);
/*  52 */     if (itemStack.getType() != MAP) {
/*  53 */       throw new GuiException("MapBuilder requires the material to be a MAP!");
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
/*     */   public MapBuilder color(@Nullable Color color) {
/*  69 */     MapMeta mapMeta = (MapMeta)getMeta();
/*     */     
/*  71 */     mapMeta.setColor(color);
/*  72 */     setMeta((ItemMeta)mapMeta);
/*  73 */     return this;
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
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public MapBuilder locationName(@Nullable String name) {
/*  87 */     MapMeta mapMeta = (MapMeta)getMeta();
/*     */     
/*  89 */     mapMeta.setLocationName(name);
/*  90 */     setMeta((ItemMeta)mapMeta);
/*  91 */     return this;
/*     */   }
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
/*     */   public MapBuilder scaling(boolean scaling) {
/* 104 */     MapMeta mapMeta = (MapMeta)getMeta();
/*     */     
/* 106 */     mapMeta.setScaling(scaling);
/* 107 */     setMeta((ItemMeta)mapMeta);
/* 108 */     return this;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract("_ -> this")
/*     */   public MapBuilder view(@NotNull MapView view) {
/* 126 */     MapMeta mapMeta = (MapMeta)getMeta();
/*     */     
/* 128 */     mapMeta.setMapView(view);
/* 129 */     setMeta((ItemMeta)mapMeta);
/* 130 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\gui\builder\item\MapBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */