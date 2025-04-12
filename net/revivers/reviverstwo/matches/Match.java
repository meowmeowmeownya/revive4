/*     */ package net.revivers.reviverstwo.matches;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.ClickEvent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import net.revivers.reviverstwo.ReviversTwo;
/*     */ import net.revivers.reviverstwo.arenas.Arena;
/*     */ import net.revivers.reviverstwo.arenas.ArenaManager;
/*     */ import net.revivers.reviverstwo.arenas.games.Game;
/*     */ import net.revivers.reviverstwo.arenas.games.GameManager;
/*     */ import net.revivers.reviverstwo.arenas.games.PlayerHandler;
/*     */ import net.revivers.reviverstwo.arenas.games.hats.Hat;
/*     */ import net.revivers.reviverstwo.arenas.games.hats.HatManager;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class Match {
/*     */   private Player leader;
/*  27 */   private final HashMap<Player, Hat> players = new HashMap<>();
/*     */   
/*     */   private boolean isDisbanded;
/*  30 */   private Arena selectedMap = null;
/*     */   
/*  32 */   private Arena lastPlayedArena = null;
/*  33 */   private long lastGameStartTime = 0L;
/*     */   
/*  35 */   private int blastProtection = ReviversTwo.getConfiguration().getInt("Blast Protection Percentage");
/*     */   
/*     */   public Match(Player leader) {
/*  38 */     this.leader = leader;
/*  39 */     this.players.put(leader, (Hat)HatManager.getHats().get(ReviversTwo.getConfiguration().getString("Default Hat")));
/*     */   }
/*     */   
/*     */   public Player getLeader() {
/*  43 */     return this.leader;
/*     */   }
/*     */   
/*     */   public List<Player> getPlayers() {
/*  47 */     return new ArrayList<>(this.players.keySet());
/*     */   }
/*     */   public Hat getPlayerHat(Player player) {
/*  50 */     return this.players.get(player);
/*     */   }
/*     */   public void setPlayerHat(Player player, Hat hat) {
/*  53 */     this.players.put(player, hat);
/*     */   }
/*     */   
/*     */   public void start() {
/*     */     Arena arena;
/*  58 */     if (this.selectedMap == null) {
/*  59 */       if (this.lastPlayedArena != null) {
/*  60 */         arena = ArenaManager.getRandomArena(getLastPlayedArena());
/*     */       } else {
/*  62 */         arena = ArenaManager.getRandomArena();
/*     */       } 
/*     */     } else {
/*  65 */       arena = this.selectedMap;
/*     */     } 
/*  67 */     this.lastPlayedArena = arena;
/*     */     
/*  69 */     String gameId = ((this.players.size() >= 8) ? "b" : "m") + ((this.players.size() >= 8) ? "b" : "m");
/*  70 */     Game game = new Game(gameId, arena, this.players.size(), 2, this.blastProtection);
/*  71 */     GameManager.createGame(game);
/*     */     
/*  73 */     getPlayers().forEach(player -> {
/*     */           if (PlayerHandler.getPlayerGame(player) != null) {
/*     */             PlayerHandler.getPlayerGame(player).leave(player, true);
/*     */           }
/*     */         });
/*  78 */     for (Player player : getPlayers()) {
/*  79 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*  80 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lGAME SETTINGS:"));
/*  81 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╠ Map: &6" + arena.getName()));
/*  82 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╚ Blast Protection: &6" + this.blastProtection + "%"));
/*  83 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */     
/*  86 */     Objects.requireNonNull(game); getPlayers().forEach(game::join);
/*     */     
/*  88 */     this.lastGameStartTime = Instant.now().getEpochSecond();
/*     */   }
/*     */ 
/*     */   
/*     */   public void promote(Player promotedPlayer) {
/*  93 */     for (ListIterator<ItemStack> listIterator = this.leader.getInventory().iterator(); listIterator.hasNext(); ) { ItemStack item = listIterator.next();
/*  94 */       if (item != null && 
/*  95 */         item.hasItemMeta() && 
/*  96 */         item.getItemMeta().hasDisplayName() && 
/*  97 */         item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lPlay Again &7(Right Click)"))) {
/*  98 */         this.leader.getInventory().remove(item);
/*     */       } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     this.leader = promotedPlayer;
/*     */     
/* 107 */     for (Player player : getPlayers()) {
/* 108 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 109 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(promotedPlayer) + ReviversTwo.getChat().getPlayerPrefix(promotedPlayer) + "&e has been promoted to Match Leader."));
/* 110 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void add(Player addPlayer) {
/* 115 */     this.players.put(addPlayer, (Hat)HatManager.getHats().get(ReviversTwo.getConfiguration().getString("Default Hat")));
/* 116 */     MatchManager.addPlayerMatch(addPlayer, this);
/*     */     
/* 118 */     for (Player player : getPlayers()) {
/* 119 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 120 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(addPlayer) + ReviversTwo.getChat().getPlayerPrefix(addPlayer) + "&a joined the match."));
/* 121 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invite(Player invitedPlayer, Player inviter) {
/* 126 */     for (Player player : getPlayers()) {
/* 127 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 128 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(invitedPlayer) + ReviversTwo.getChat().getPlayerPrefix(invitedPlayer) + "&e has been invited to the match."));
/* 129 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThey have &c60&e seconds to accept."));
/* 130 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */     
/* 133 */     MatchManager.addPlayerMatchInvite(invitedPlayer, this);
/* 134 */     invitedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 135 */     invitedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(inviter) + ReviversTwo.getChat().getPlayerPrefix(inviter) + "&e has invited you to join their match!"));
/* 136 */     TextComponent acceptMessage = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&eYou have &c60&e seconds to accept. &aClick here to join!"));
/* 137 */     acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/match accept"));
/* 138 */     invitedPlayer.spigot().sendMessage((BaseComponent)acceptMessage);
/* 139 */     invitedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     
/* 141 */     Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)ReviversTwo.getPlugin(), () -> { if (MatchManager.hasPlayerMatchInvite(invitedPlayer)) { invitedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------")); invitedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(inviter) + ReviversTwo.getChat().getPlayerPrefix(inviter) + "&c's match invite has expired.")); invitedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------")); for (Player player : getPlayers()) { player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------")); player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(invitedPlayer) + ReviversTwo.getChat().getPlayerPrefix(invitedPlayer) + "&c has not accepted the match invite in time.")); player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------")); }  MatchManager.removePlayerMatchInvite(invitedPlayer); if (getPlayers().size() == 1) { this.isDisbanded = true; Player lastPlayerStanding = getPlayers().get(0); MatchManager.removePlayerMatch(lastPlayerStanding); lastPlayerStanding.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------")); lastPlayerStanding.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe match has been disbanded since all players left.")); lastPlayerStanding.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------")); MatchManager.removePlayerMatch(lastPlayerStanding); }  }  }1200L);
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
/*     */   public void leave(Player leavePlayer) {
/* 174 */     for (Player player : getPlayers()) {
/* 175 */       if (player == leavePlayer)
/* 176 */         continue;  player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 177 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(leavePlayer) + ReviversTwo.getChat().getPlayerPrefix(leavePlayer) + "&e left the match."));
/* 178 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */     
/* 181 */     removePlayer(leavePlayer);
/*     */     
/* 183 */     leavePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 184 */     leavePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou've left the match."));
/* 185 */     leavePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */   }
/*     */   
/*     */   public void kick(Player kickPlayer) {
/* 189 */     for (Player player : getPlayers()) {
/* 190 */       if (player == kickPlayer)
/* 191 */         continue;  player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 192 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(kickPlayer) + ReviversTwo.getChat().getPlayerPrefix(kickPlayer) + "&e has been kicked from your match."));
/* 193 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */     
/* 196 */     removePlayer(kickPlayer);
/*     */     
/* 198 */     kickPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 199 */     kickPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou've been kicked from the match."));
/* 200 */     kickPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */   }
/*     */   
/*     */   public void removePlayer(Player player) {
/* 204 */     MatchManager.removePlayerMatch(player);
/* 205 */     this.players.remove(player);
/*     */     
/* 207 */     if (this.players.size() == 0) {
/* 208 */       disband();
/*     */     } else {
/* 210 */       if (this.players.size() == 1) {
/* 211 */         this.isDisbanded = true;
/*     */         
/* 213 */         Player lastPlayerStanding = getPlayers().get(0);
/*     */         
/* 215 */         MatchManager.removePlayerMatch(lastPlayerStanding);
/* 216 */         lastPlayerStanding.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 217 */         lastPlayerStanding.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe match has been disbanded since all players left."));
/* 218 */         lastPlayerStanding.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */         
/* 220 */         MatchManager.removePlayerMatch(lastPlayerStanding);
/*     */         
/*     */         return;
/*     */       } 
/* 224 */       if (this.leader == player) {
/* 225 */         this.leader = getPlayers().get(0);
/*     */         
/* 227 */         for (Player loopPlayer : getPlayers()) {
/* 228 */           loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 229 */           loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerPrefix(this.leader) + ReviversTwo.getChat().getPlayerPrefix(this.leader) + "&e has been automatically promoted to Match Leader."));
/* 230 */           loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDisbanded() {
/* 237 */     return this.isDisbanded;
/*     */   }
/*     */   public void disband() {
/* 240 */     this.isDisbanded = true;
/*     */     
/* 242 */     for (Player player : getPlayers()) {
/* 243 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 244 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe match has been disbanded by the Match Leader."));
/* 245 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 246 */       MatchManager.removePlayerMatch(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Arena getSelectedMap() {
/* 251 */     return this.selectedMap;
/*     */   }
/*     */   public void setSelectedMap(Arena selectedMap) {
/* 254 */     this.selectedMap = selectedMap;
/*     */   }
/*     */   
/*     */   public Arena getLastPlayedArena() {
/* 258 */     return this.lastPlayedArena;
/*     */   }
/*     */   
/*     */   public long getLastGameStartTime() {
/* 262 */     return this.lastGameStartTime;
/*     */   }
/*     */   
/*     */   public void setBlastProtection(int blastProtection) {
/* 266 */     this.blastProtection = blastProtection;
/*     */     
/* 268 */     for (Player player : getPlayers()) {
/* 269 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 270 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe blast protection % has been set to " + blastProtection + " by the Match Leader."));
/* 271 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\matches\Match.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */