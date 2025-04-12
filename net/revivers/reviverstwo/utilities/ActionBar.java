/*     */ package net.revivers.reviverstwo.utilities;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import net.revivers.reviverstwo.ReviversTwo;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionBar
/*     */ {
/*     */   private Class<?> nmsChatSerializer;
/*     */   private Class<?> nmsPacketTitle;
/*     */   private Class<?> nmsPacketChat;
/*     */   private Class<?> nmsChatBaseComponent;
/*     */   private Object nmsIChatBaseComponent;
/*     */   
/*     */   private void loadClasses() {
/*  25 */     this.nmsIChatBaseComponent = getNMSClass("IChatBaseComponent");
/*  26 */     this.nmsPacketChat = getNMSClass("PacketPlayOutChat");
/*     */     
/*  28 */     this.nmsChatBaseComponent = getNMSClass("IChatBaseComponent");
/*     */     
/*  30 */     if (getVersion().contains("1_8")) {
/*  31 */       if (getVersion().contains("R1")) {
/*  32 */         this.nmsChatSerializer = getNMSClass("ChatSerializer");
/*  33 */         this.nmsPacketTitle = getNMSClass("PacketPlayOutTitle");
/*  34 */         getNMSClass("EnumTitleAction");
/*  35 */       } else if (getVersion().contains("R2") || getVersion().contains("R3")) {
/*  36 */         this.nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
/*  37 */         this.nmsPacketTitle = getNMSClass("PacketPlayOutTitle");
/*  38 */         getNMSClass("PacketPlayOutTitle$EnumTitleAction");
/*     */       } 
/*  40 */     } else if (getVersion().contains("1_9") || getVersion().contains("1_10") || getVersion().contains("1_11")) {
/*  41 */       this.nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
/*  42 */       this.nmsPacketTitle = getNMSClass("PacketPlayOutTitle");
/*  43 */       getNMSClass("PacketPlayOutTitle$EnumTitleAction");
/*     */     } 
/*     */   }
/*     */   
/*     */   public Class<?> getNMSChatSerializer() {
/*  48 */     return this.nmsChatSerializer;
/*     */   }
/*     */   
/*     */   public Class<?> getNMSIChatBaseComponent() {
/*  52 */     return this.nmsIChatBaseComponent.getClass();
/*     */   }
/*     */   
/*     */   public Class<?> getNMSPacketTitle() {
/*  56 */     return this.nmsPacketTitle;
/*     */   }
/*     */   
/*     */   public void sendActionBar(Player player, String message) {
/*  60 */     loadClasses();
/*     */     try {
/*  62 */       String handle1 = "getHandle";
/*  63 */       Object handle = getMethod(player.getClass(), handle1, new Class[0]).invoke(player, new Object[0]);
/*  64 */       String playerConnection1 = "playerConnection";
/*  65 */       Object playerConnection = getField(handle.getClass(), playerConnection1).get(handle);
/*  66 */       Object serializedMessage = getMethod(this.nmsChatSerializer, "a", new Class[] { String.class }).invoke(this.nmsChatSerializer, new Object[] { "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}" });
/*  67 */       Object packet = this.nmsPacketChat.getConstructor(new Class[] { this.nmsChatBaseComponent, byte.class }).newInstance(new Object[] { serializedMessage, Byte.valueOf((byte)2) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  73 */       String sendPacket = "sendPacket";
/*  74 */       getMethod(playerConnection.getClass(), sendPacket, new Class[0]).invoke(playerConnection, new Object[] { packet });
/*  75 */     } catch (Exception localException) {
/*  76 */       localException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendActionBar(final Player player, final String message, int duration) {
/*  81 */     sendActionBar(player, message);
/*     */     
/*  83 */     if (duration >= 0)
/*     */     {
/*  85 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/*  88 */             ActionBar.this.sendActionBar(player, "");
/*     */           }
/*  90 */         }).runTaskLater((Plugin)ReviversTwo.getPlugin(), (duration + 1));
/*     */     }
/*     */ 
/*     */     
/*  94 */     while (duration > 60) {
/*  95 */       duration -= 60;
/*  96 */       int sched = duration % 60;
/*  97 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/* 100 */             ActionBar.this.sendActionBar(player, message);
/*     */           }
/* 102 */         }).runTaskLater((Plugin)ReviversTwo.getPlugin(), sched);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendActionBarToAllPlayers(String message) {
/* 107 */     sendActionBarToAllPlayers(message, -1);
/*     */   }
/*     */   
/*     */   public void sendActionBarToAllPlayers(String message, int duration) {
/* 111 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 112 */       sendActionBar(p, message, duration);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getVersion() {
/* 117 */     String name = Bukkit.getServer().getClass().getPackage().getName();
/* 118 */     return name.substring(name.lastIndexOf('.') + 1) + ".";
/*     */   }
/*     */   
/*     */   private Class<?> getNMSClass(String className) {
/* 122 */     String fullName = "net.minecraft.server." + getVersion() + className;
/* 123 */     Class<?> clazz = null;
/*     */     try {
/* 125 */       clazz = Class.forName(fullName);
/* 126 */     } catch (Exception e) {
/* 127 */       e.printStackTrace();
/*     */     } 
/* 129 */     return clazz;
/*     */   }
/*     */   
/*     */   private Field getField(Class<?> clazz, String name) {
/*     */     try {
/* 134 */       Field field = clazz.getDeclaredField(name);
/* 135 */       field.setAccessible(true);
/* 136 */       return field;
/* 137 */     } catch (Exception e) {
/* 138 */       e.printStackTrace();
/* 139 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
/* 144 */     for (Method m : clazz.getMethods()) {
/* 145 */       if (m.getName().equals(name) && (args.length == 0 || classListEqual(args, m.getParameterTypes()))) {
/* 146 */         m.setAccessible(true);
/* 147 */         return m;
/*     */       } 
/* 149 */     }  return null;
/*     */   }
/*     */   
/*     */   private boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
/* 153 */     boolean equal = true;
/* 154 */     if (l1.length != l2.length)
/* 155 */       return false; 
/* 156 */     for (int i = 0; i < l1.length; i++) {
/* 157 */       if (l1[i] != l2[i]) {
/* 158 */         equal = false; break;
/*     */       } 
/*     */     } 
/* 161 */     return equal;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\ActionBar.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */