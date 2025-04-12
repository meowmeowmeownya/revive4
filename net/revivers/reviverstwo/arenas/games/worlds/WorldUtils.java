/*    */ package net.revivers.reviverstwo.arenas.games.worlds;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.WorldCreator;
/*    */ 
/*    */ public class WorldUtils {
/*    */   private static void copyFileStructure(File source, File target) {
/*    */     try {
/* 16 */       ArrayList<String> ignore = new ArrayList<>(Arrays.asList(new String[] { "uid.dat", "session.lock" }));
/* 17 */       if (!ignore.contains(source.getName())) {
/* 18 */         if (source.isDirectory()) {
/* 19 */           if (!target.exists() && 
/* 20 */             !target.mkdirs())
/* 21 */             throw new IOException("Couldn't create world directory!"); 
/* 22 */           String[] files = source.list();
/* 23 */           assert files != null;
/* 24 */           for (String file : files) {
/* 25 */             File srcFile = new File(source, file);
/* 26 */             File destFile = new File(target, file);
/* 27 */             copyFileStructure(srcFile, destFile);
/*    */           } 
/*    */         } else {
/* 30 */           InputStream in = new FileInputStream(source);
/* 31 */           OutputStream out = new FileOutputStream(target);
/* 32 */           byte[] buffer = new byte[1024];
/*    */           int length;
/* 34 */           while ((length = in.read(buffer)) > 0)
/* 35 */             out.write(buffer, 0, length); 
/* 36 */           in.close();
/* 37 */           out.close();
/*    */         } 
/*    */       }
/* 40 */     } catch (IOException e) {
/* 41 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static World copyWorld(World originalWorld, String newWorldName) {
/* 46 */     Thread thread = new Thread(() -> {
/*    */           File copiedFile = new File(Bukkit.getWorldContainer(), newWorldName);
/*    */           copyFileStructure(originalWorld.getWorldFolder(), copiedFile);
/*    */         });
/* 50 */     thread.start();
/*    */     try {
/* 52 */       thread.join();
/* 53 */     } catch (Exception exception) {}
/* 54 */     (new WorldCreator(newWorldName)).createWorld();
/* 55 */     return Bukkit.getWorld(newWorldName);
/*    */   }
/*    */   
/*    */   public static void deleteWorld(File path) {
/* 59 */     (new Thread(() -> {
/*    */           
/*    */           try {
/*    */             FileUtils.deleteDirectory(path);
/* 63 */           } catch (Exception exception) {}
/* 64 */         })).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\worlds\WorldUtils.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */