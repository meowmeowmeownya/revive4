/*    */ package net.revivers.reviverstwo.arenas.games.hats;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.LinkedHashMap;
/*    */ import net.revivers.reviverstwo.ReviversTwo;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HatManager
/*    */ {
/* 15 */   private static LinkedHashMap<String, Hat> hats = new LinkedHashMap<>();
/*    */   
/*    */   public static void loadHats() {
/* 18 */     File file = new File("" + ReviversTwo.getPlugin().getDataFolder() + "/hats");
/* 19 */     boolean wereFoldersCreated = file.mkdir();
/* 20 */     if (wereFoldersCreated) {
/*    */       try {
/* 22 */         File file1 = new File("" + ReviversTwo.getPlugin().getDataFolder() + "/hats/default.yml");
/* 23 */         String defaultHatContent = "Name: '&7No Hat'\nLore:\n - '&7Default hat.'\nMaterial: air";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 29 */         FileUtils.writeStringToFile(file1, defaultHatContent, StandardCharsets.UTF_8);
/* 30 */       } catch (Exception ignored) {
/* 31 */         ReviversTwo.getPlugin().getLogger().warning("Could not create default hat");
/*    */       } 
/*    */     }
/* 34 */     for (File child : file.listFiles()) {
/* 35 */       YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(child);
/*    */       
/* 37 */       String hatId = child.getName().replaceFirst("[.][^.]+$", "");
/*    */       
/* 39 */       Hat hat = new Hat(hatId);
/* 40 */       hat.name(yamlConfiguration.getString("Name"));
/* 41 */       hat.lore(yamlConfiguration.getStringList("Lore"));
/* 42 */       if (yamlConfiguration.get("Data") != null)
/* 43 */         hat.data(Integer.valueOf(yamlConfiguration.getInt("Data"))); 
/* 44 */       if (yamlConfiguration.get("Permission") != null)
/* 45 */         hat.permission(yamlConfiguration.getString("Permission")); 
/* 46 */       hat.material(Material.valueOf(yamlConfiguration.getString("Material").toUpperCase()));
/* 47 */       hat.enchanted(yamlConfiguration.getBoolean("Enchanted"));
/*    */       
/* 49 */       hats.put(hatId, hat);
/*    */       
/* 51 */       ReviversTwo.getPlugin().getLogger().info("[Dynamite] Loaded hat \"" + yamlConfiguration.getString("Name") + "\" (" + child.getName() + ")");
/*    */     } 
/*    */     
/* 54 */     Hat defaultHat = hats.get(ReviversTwo.getConfiguration().getString("Default Hat"));
/* 55 */     hats.remove(defaultHat.id());
/*    */     
/* 57 */     LinkedHashMap<String, Hat> reorder = new LinkedHashMap<>();
/* 58 */     reorder.put(defaultHat.id(), defaultHat);
/* 59 */     reorder.putAll(hats);
/*    */     
/* 61 */     hats = reorder;
/*    */   }
/*    */ 
/*    */   
/*    */   public static LinkedHashMap<String, Hat> getHats() {
/* 66 */     return hats;
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\hats\HatManager.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */