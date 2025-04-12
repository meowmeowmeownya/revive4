/*     */ package net.revivers.reviverstwo.arenas.games;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.revivers.reviverstwo.ReviversTwo;
/*     */ import net.revivers.reviverstwo.gui.builder.gui.PaginatedBuilder;
/*     */ import net.revivers.reviverstwo.gui.builder.item.ItemBuilder;
/*     */ import net.revivers.reviverstwo.gui.guis.Gui;
/*     */ import net.revivers.reviverstwo.gui.guis.GuiItem;
/*     */ import net.revivers.reviverstwo.gui.guis.PaginatedGui;
/*     */ import net.revivers.reviverstwo.matches.Match;
/*     */ import net.revivers.reviverstwo.matches.MatchManager;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class GameEventHandler implements Listener {
/*  40 */   private final Map<Player, Integer> pendingLeave = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerInteract(PlayerInteractEvent event) {
/*  46 */     if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/*     */       return;
/*     */     }
/*  49 */     if (PlayerHandler.getPlayerGame(event.getPlayer()) == null)
/*     */       return; 
/*  51 */     Player player = event.getPlayer();
/*  52 */     Game game = PlayerHandler.getPlayerGame(player);
/*     */ 
/*     */     
/*  55 */     if (!game.getSpectators().contains(player) && game.getRound() != 0) {
/*     */       return;
/*     */     }
/*  58 */     if (player.getItemInHand().getType() == Material.BED) {
/*  59 */       ItemStack item = player.getItemInHand();
/*  60 */       if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lReturn to Lobby &7(Right Click)")))
/*     */       {
/*  62 */         if (this.pendingLeave.containsKey(player)) {
/*     */           
/*  64 */           player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTeleport cancelled!"));
/*  65 */           Bukkit.getScheduler().cancelTask(((Integer)this.pendingLeave.get(player)).intValue());
/*  66 */           this.pendingLeave.remove(player);
/*     */         } else {
/*     */           
/*  69 */           player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lTeleporting you to the lobby in 3 seconds... Right-click again to cancel the teleport!"));
/*     */           
/*  71 */           int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)ReviversTwo.getPlugin(), () -> { game.leave(player, false); this.pendingLeave.remove(player); }60L);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  76 */           this.pendingLeave.put(player, Integer.valueOf(taskId));
/*     */         }
/*     */       
/*     */       }
/*  80 */     } else if (player.getItemInHand().getType() == Material.COMPASS) {
/*  81 */       ItemStack item = player.getItemInHand();
/*  82 */       if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a&lTeleporter &7(Right Click)")))
/*     */       {
/*  84 */         AtomicInteger oldPlayerSize = new AtomicInteger(2147483647);
/*  85 */         AtomicInteger taskId = new AtomicInteger();
/*  86 */         AtomicBoolean hasBeenOpened = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  92 */         AtomicReference<PaginatedGui> gui = new AtomicReference<>(((PaginatedBuilder)((PaginatedBuilder)((PaginatedBuilder)Gui.paginated().disableAllInteractions()).title((Component)Component.text("Teleporter"))).pageSize(26).rows(4)).create());
/*     */         
/*  94 */         ((PaginatedGui)gui.get()).setItem(29, ((ItemBuilder)ItemBuilder.from(Material.ARROW).name((Component)Component.text(ChatColor.translateAlternateColorCodes('&', "&aPrevious")))).asGuiItem(guiEvent -> ((PaginatedGui)gui.get()).previous()));
/*  95 */         ((PaginatedGui)gui.get()).setItem(33, ((ItemBuilder)ItemBuilder.from(Material.ARROW).name((Component)Component.text(ChatColor.translateAlternateColorCodes('&', "&aNext")))).asGuiItem(guiEvent -> ((PaginatedGui)gui.get()).next()));
/*     */         
/*  97 */         taskId.set(Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)ReviversTwo.getPlugin(), () -> { if (PlayerHandler.getPlayerGame(player) == null || PlayerHandler.getPlayerGame(player) != game) { ((PaginatedGui)gui.get()).close((HumanEntity)player); Bukkit.getScheduler().cancelTask(taskId.get()); return; }  if (game.getPlayers().size() != oldPlayerSize.get()) { oldPlayerSize.set(game.getPlayers().size()); if (!hasBeenOpened.get()) { ((PaginatedGui)gui.get()).open((HumanEntity)player); hasBeenOpened.set(true); } else { ((PaginatedGui)gui.get()).clearPageItems(); ((PaginatedGui)gui.get()).setItem(29, ((ItemBuilder)ItemBuilder.from(Material.ARROW).name((Component)Component.text(ChatColor.translateAlternateColorCodes('&', "&aPrevious")))).asGuiItem(())); ((PaginatedGui)gui.get()).setItem(33, ((ItemBuilder)ItemBuilder.from(Material.ARROW).name((Component)Component.text(ChatColor.translateAlternateColorCodes('&', "&aNext")))).asGuiItem(())); }  for (Player gamePlayer : game.getPlayers().keySet()) { ItemStack gamePlayerSkull = new ItemStack(Material.SKULL_ITEM, 1, (short)3); SkullMeta meta = (SkullMeta)gamePlayerSkull.getItemMeta(); meta.setOwner(gamePlayer.getName()); meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + gamePlayer.getName())); ArrayList<String> gamePlayerSkullLore = new ArrayList<>(); gamePlayerSkullLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click this item to")); gamePlayerSkullLore.add(ChatColor.translateAlternateColorCodes('&', "&7teleport to the player.")); meta.setLore(gamePlayerSkullLore); gamePlayerSkull.setItemMeta((ItemMeta)meta); GuiItem guiItem = ItemBuilder.from(gamePlayerSkull).asGuiItem(()); ((PaginatedGui)gui.get()).addItem(guiItem); }  ((PaginatedGui)gui.get()).update(); }  }0L, 15L));
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
/*     */       }
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
/*     */     }
/* 144 */     else if (player.getItemInHand().getType() == Material.PAPER) {
/* 145 */       ItemStack item = player.getItemInHand();
/* 146 */       if (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lPlay Again &7(Right Click)"))) {
/* 147 */         player.performCommand("m start");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDamage(EntityDamageByEntityEvent event) {
/* 157 */     if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
/*     */       return;
/*     */     }
/* 160 */     if (PlayerHandler.getPlayerGame((Player)event.getDamager()) == null || PlayerHandler.getPlayerGame((Player)event.getEntity()) == null) {
/*     */       return;
/*     */     }
/* 163 */     if (PlayerHandler.getPlayerGame((Player)event.getDamager()) != PlayerHandler.getPlayerGame((Player)event.getEntity()))
/*     */       return; 
/* 165 */     Game game = PlayerHandler.getPlayerGame((Player)event.getEntity());
/*     */ 
/*     */     
/* 168 */     if (game.getSpectators().contains(event.getEntity()) || game.getSpectators().contains(event.getDamager())) {
/* 169 */       event.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 174 */     if (game.getRound() == 0) event.setCancelled(true);
/*     */     
/* 176 */     event.setDamage(0.0D);
/* 177 */     game.tag((Player)event.getDamager(), (Player)event.getEntity());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/* 185 */     if (!(event.getEntity() instanceof Player)) {
/*     */       return;
/*     */     }
/* 188 */     if (PlayerHandler.getPlayerGame((Player)event.getEntity()) != null) {
/* 189 */       Game game = PlayerHandler.getPlayerGame((Player)event.getEntity());
/* 190 */       if (game.getRound() == 0 || game.getSpectators().contains(event.getEntity())) {
/* 191 */         event.setCancelled(true);
/*     */       }
/*     */     } 
/*     */     
/* 195 */     if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
/* 196 */       event.setCancelled(true);
/*     */     }
/* 198 */     event.setDamage(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onFoodLevelChange(FoodLevelChangeEvent event) {
/* 204 */     event.setFoodLevel(20);
/* 205 */     event.setCancelled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDropItem(PlayerDropItemEvent event) {
/* 212 */     if (PlayerHandler.getPlayerGame(event.getPlayer()) == null)
/*     */       return; 
/* 214 */     event.setCancelled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/* 222 */     if (PlayerHandler.getPlayerGame((Player)event.getWhoClicked()) == null)
/*     */       return; 
/* 224 */     event.setCancelled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryDrag(InventoryDragEvent event) {
/* 232 */     if (PlayerHandler.getPlayerGame((Player)event.getWhoClicked()) == null)
/*     */       return; 
/* 234 */     event.setCancelled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryOpen(InventoryOpenEvent event) {
/* 242 */     if (PlayerHandler.getPlayerGame((Player)event.getPlayer()) != null) {
/* 243 */       Game game = PlayerHandler.getPlayerGame((Player)event.getPlayer());
/* 244 */       if (game.getRound() == 0)
/* 245 */         return;  if (game.getSpectators().contains(event.getPlayer()))
/*     */         return; 
/*     */     } else {
/*     */       return;
/*     */     } 
/* 250 */     event.setCancelled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 258 */     if (MatchManager.hasPlayerMatch(event.getPlayer())) {
/* 259 */       Match match = MatchManager.getPlayerMatch(event.getPlayer());
/* 260 */       MatchManager.removePlayerMatch(event.getPlayer());
/*     */       
/* 262 */       for (Player loopPlayer : match.getPlayers()) {
/* 263 */         loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 264 */         loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(event.getPlayer()) + ReviversTwo.getChat().getPlayerPrefix(event.getPlayer()) + "&e has been kicked from the match since they left."));
/* 265 */         loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       } 
/*     */     } 
/* 268 */     MatchManager.removePlayerMatchInvite(event.getPlayer());
/*     */ 
/*     */     
/* 271 */     if (PlayerHandler.getPlayerGame(event.getPlayer()) == null)
/*     */       return; 
/* 273 */     Game game = PlayerHandler.getPlayerGame(event.getPlayer());
/*     */     
/* 275 */     game.leave(event.getPlayer(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerKick(PlayerKickEvent event) {
/* 283 */     if (MatchManager.hasPlayerMatch(event.getPlayer())) {
/* 284 */       Match match = MatchManager.getPlayerMatch(event.getPlayer());
/* 285 */       MatchManager.removePlayerMatch(event.getPlayer());
/*     */       
/* 287 */       for (Player loopPlayer : match.getPlayers()) {
/* 288 */         loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 289 */         loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(event.getPlayer()) + ReviversTwo.getChat().getPlayerPrefix(event.getPlayer()) + "&e has been kicked from the match since they left."));
/* 290 */         loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       } 
/*     */     } 
/* 293 */     MatchManager.removePlayerMatchInvite(event.getPlayer());
/*     */ 
/*     */     
/* 296 */     if (PlayerHandler.getPlayerGame(event.getPlayer()) == null)
/*     */       return; 
/* 298 */     Game game = PlayerHandler.getPlayerGame(event.getPlayer());
/*     */     
/* 300 */     game.leave(event.getPlayer(), false);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\GameEventHandler.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */