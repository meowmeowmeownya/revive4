/*     */ package net.revivers.reviverstwo.arenas.games.hats;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ public class Hat
/*     */ {
/*     */   private final String id;
/*  17 */   private String name = "A Hat";
/*  18 */   private List<String> lore = Arrays.asList(new String[] {
/*  19 */         ChatColor.translateAlternateColorCodes('&', "&7This is an"), 
/*  20 */         ChatColor.translateAlternateColorCodes('&', "&7example hat.")
/*     */       });
/*  22 */   private Integer data = null;
/*  23 */   private String permission = null;
/*  24 */   private Material material = Material.DIRT;
/*     */   private boolean isEnchanted = false;
/*     */   
/*     */   protected Hat(String id) {
/*  28 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String id() {
/*  32 */     return this.id;
/*     */   }
/*     */   
/*     */   public String name() {
/*  36 */     return this.name;
/*     */   }
/*     */   public void name(String name) {
/*  39 */     this.name = name;
/*     */   }
/*     */   
/*     */   public List<String> lore() {
/*  43 */     return this.lore;
/*     */   }
/*     */   public void lore(List<String> lore) {
/*  46 */     this.lore = lore;
/*     */   }
/*     */   
/*     */   public Integer data() {
/*  50 */     return this.data;
/*     */   }
/*     */   public void data(Integer data) {
/*  53 */     this.data = data;
/*     */   }
/*     */   
/*     */   public String permission() {
/*  57 */     return this.permission;
/*     */   }
/*     */   public void permission(String permission) {
/*  60 */     this.permission = permission;
/*     */   }
/*     */   
/*     */   public Material material() {
/*  64 */     return this.material;
/*     */   }
/*     */   public void material(Material material) {
/*  67 */     this.material = material;
/*     */   }
/*     */   
/*     */   public boolean enchanted() {
/*  71 */     return this.isEnchanted;
/*     */   }
/*     */   public void enchanted(boolean enchanted) {
/*  74 */     this.isEnchanted = enchanted;
/*     */   }
/*     */   
/*     */   public ItemStack item() {
/*     */     ItemStack hat;
/*  79 */     if (this.data != null) {
/*  80 */       hat = new ItemStack(this.material, 1, (short)(byte)this.data.intValue());
/*     */     } else {
/*  82 */       hat = new ItemStack(this.material, 1);
/*     */     } 
/*     */     
/*  85 */     if (this.material != Material.AIR) {
/*  86 */       ItemMeta hatMeta = hat.getItemMeta();
/*     */       
/*  88 */       hatMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
/*  89 */       if (this.isEnchanted)
/*  90 */         hatMeta.addEnchant(Enchantment.DURABILITY, 255, false); 
/*  91 */       hatMeta.spigot().setUnbreakable(true);
/*  92 */       hatMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES });
/*     */       
/*  94 */       List<String> lore = new ArrayList<>();
/*  95 */       lore.add(ChatColor.translateAlternateColorCodes('&', "&7"));
/*  96 */       this.lore.forEach(loreLine -> lore.add(ChatColor.translateAlternateColorCodes('&', loreLine)));
/*  97 */       lore.add(ChatColor.translateAlternateColorCodes('&', "&7"));
/*  98 */       if (this.permission != null)
/*  99 */         lore.add(ChatColor.translateAlternateColorCodes('&', "&bSpecial")); 
/* 100 */       hatMeta.setLore(lore);
/*     */       
/* 102 */       hat.setItemMeta(hatMeta);
/*     */     } 
/* 104 */     return hat;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\hats\Hat.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */