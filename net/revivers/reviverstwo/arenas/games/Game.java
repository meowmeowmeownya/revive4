/*     */ package net.revivers.reviverstwo.arenas.games;
/*     */ import com.nametagedit.plugin.NametagEdit;
/*     */ import java.text.DateFormat;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.revivers.reviverstwo.ReviversTwo;
/*     */ import net.revivers.reviverstwo.arenas.Arena;
/*     */ import net.revivers.reviverstwo.matches.Match;
/*     */ import net.revivers.reviverstwo.matches.MatchManager;
/*     */ import net.revivers.reviverstwo.utilities.ActionBar;
/*     */ import net.revivers.reviverstwo.utilities.Chat;
/*     */ import net.revivers.reviverstwo.utilities.board.FastBoard;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Firework;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class Game {
/*     */   private final Arena arena;
/*     */   private final String id;
/*     */   private int maxPlayers;
/*     */   private final int minPlayers;
/*     */   private final int blastProtection;
/*  42 */   private final int fireworkPercentage = ReviversTwo.getConfiguration().getInt("Firework Percentage");
/*     */   
/*  44 */   private int round = 0;
/*  45 */   private final AtomicInteger timer = new AtomicInteger(15);
/*     */   
/*  47 */   private final HashMap<Player, FastBoard> boards = new HashMap<>();
/*  48 */   private final LinkedHashMap<Player, Boolean> players = new LinkedHashMap<>();
/*  49 */   private final ArrayList<Player> spectators = new ArrayList<>();
/*     */ 
/*     */   
/*  52 */   private final List<Player> first = new ArrayList<>();
/*  53 */   private final List<Player> second = new ArrayList<>();
/*  54 */   private final List<Player> third = new ArrayList<>();
/*     */   
/*     */   private final World world;
/*     */   
/*     */   private final Location spawnLocation;
/*     */   private final Location lobbyLocation;
/*  60 */   private final AtomicInteger compassUpdaterTask = new AtomicInteger();
/*  61 */   private final AtomicInteger timerTaskId = new AtomicInteger(0);
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
/*     */   private boolean hasGameEnded = false;
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
/*     */   private boolean white;
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
/*     */   public void join(Player player) {
/*  97 */     PlayerHandler.setPlayerGame(player, this);
/*     */     
/*  99 */     player.setFoodLevel(20);
/* 100 */     player.setHealth(20.0D);
/*     */     
/* 102 */     player.setGameMode(GameMode.ADVENTURE);
/* 103 */     player.closeInventory();
/*     */     
/* 105 */     if (this.round != 0) {
/* 106 */       addSpectator(player); return;
/*     */     } 
/* 108 */     if (this.players.size() >= this.maxPlayers) {
/* 109 */       addSpectator(player);
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     updateScoreboards();
/*     */     
/* 115 */     for (Player loopPlayer : this.players.keySet()) {
/* 116 */       loopPlayer.showPlayer(player);
/* 117 */       player.showPlayer(loopPlayer);
/*     */     } 
/*     */     
/* 120 */     this.players.put(player, Boolean.valueOf(false));
/* 121 */     player.teleport(this.lobbyLocation);
/*     */     
/* 123 */     ItemStack bed = new ItemStack(Material.BED, 1);
/* 124 */     ItemMeta meta = bed.getItemMeta();
/* 125 */     meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lReturn to Lobby &7(Right Click)"));
/* 126 */     bed.setItemMeta(meta);
/* 127 */     player.getInventory().setItem(8, bed);
/*     */     
/* 129 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 130 */     totalPlayers.addAll(this.spectators);
/* 131 */     totalPlayers.forEach(loopPlayer -> {
/*     */           loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerSuffix(player) + ReviversTwo.getChat().getPlayerSuffix(player) + "&e has joined (&b" + player.getName() + "&e/&b" + this.players.size() + "&e)!"));
/*     */           
/*     */           loopPlayer.showPlayer(player);
/*     */           player.showPlayer(loopPlayer);
/*     */         });
/* 137 */     if (this.players.size() == this.minPlayers) {
/* 138 */       startLobbyTimer();
/*     */     }
/*     */   }
/*     */   
/*     */   public void leave(Player player, boolean inMatch) {
/* 143 */     if (this.players.containsKey(player) && (
/* 144 */       (Boolean)this.players.get(player)).booleanValue()) untag(player);
/*     */ 
/*     */     
/* 147 */     NametagEdit.getApi().setPrefix(player, "&f");
/*     */     
/* 149 */     List<Player> playersToList = new ArrayList<>(this.players.keySet());
/* 150 */     runWinnerUpdater(playersToList);
/*     */     
/* 152 */     this.players.remove(player);
/* 153 */     this.spectators.remove(player);
/*     */ 
/*     */ 
/*     */     
/* 157 */     updateScoreboards();
/*     */     
/* 159 */     player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
/*     */ 
/*     */     
/* 162 */     ((FastBoard)this.boards.get(player)).delete();
/* 163 */     this.boards.remove(player);
/*     */     
/* 165 */     PlayerHandler.removePlayerGame(player);
/*     */     
/* 167 */     player.setExp(0.0F);
/* 168 */     player.setLevel(0);
/*     */     
/* 170 */     ItemStack is = new ItemStack(Material.AIR, 1);
/* 171 */     player.getInventory().setHelmet(is);
/* 172 */     player.getInventory().clear();
/*     */     
/* 174 */     ActionBar actionBar = new ActionBar();
/* 175 */     actionBar.sendActionBar(player, "");
/*     */     
/* 177 */     player.setAllowFlight(false);
/* 178 */     player.setGameMode(GameMode.ADVENTURE);
/*     */     
/* 180 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 181 */     totalPlayers.addAll(this.spectators);
/*     */     
/* 183 */     if (this.round == 0) {
/* 184 */       totalPlayers.forEach(loopPlayer -> loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerSuffix(player) + ReviversTwo.getChat().getPlayerSuffix(player) + "&e has quit!")));
/*     */     }
/*     */     
/* 187 */     for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
/* 188 */       loopPlayer.showPlayer(player);
/* 189 */       player.showPlayer(loopPlayer);
/*     */     } 
/*     */     
/* 192 */     if (inMatch) {
/* 193 */       if (this.round != 0) {
/* 194 */         if (this.players.size() == 1 && !this.hasGameEnded && (MatchManager.hasPlayerMatch(playersToList.get(0)) & (
/* 195 */           (MatchManager.getPlayerMatch(player) == MatchManager.getPlayerMatch(playersToList.get(0))) ? 1 : 0)) != 0) {
/* 196 */           destroyGameRemains();
/* 197 */         } else if (this.players.size() <= 1 && !this.hasGameEnded) {
/*     */           try {
/* 199 */             Bukkit.getScheduler().cancelTask(this.timerTaskId.get());
/* 200 */             playersToList.forEach(this::untag);
/* 201 */             endGame();
/* 202 */           } catch (Exception exception) {}
/*     */         } 
/*     */       }
/*     */       
/* 206 */       Location reviversLobbyLocation = ReviversTwo.getLobbyLocation();
/* 207 */       player.teleport(reviversLobbyLocation);
/*     */     }
/* 209 */     else if (this.round != 0 && 
/* 210 */       this.players.size() <= 1 && !this.hasGameEnded) {
/*     */       try {
/* 212 */         Bukkit.getScheduler().cancelTask(this.timerTaskId.get());
/* 213 */         playersToList.forEach(this::untag);
/* 214 */         endGame();
/* 215 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     for (Player onlinePlayer : this.players.keySet()) {
/* 222 */       onlinePlayer.showPlayer(player);
/* 223 */       player.showPlayer(onlinePlayer);
/*     */     } 
/*     */     
/* 226 */     if (this.round == 0 && this.players.size() == 0) {
/* 227 */       destroyGameRemains();
/*     */     }
/*     */   }
/*     */   
/*     */   private void startRound() {
/* 232 */     this.round++;
/*     */     
/* 234 */     this.players.forEach((player, tagState) -> untag(player));
/*     */ 
/*     */     
/* 237 */     ArrayList<Player> itPlayers = new ArrayList<>();
/*     */     
/* 239 */     Random randomizer = new Random();
/* 240 */     if (this.players.size() <= 6) {
/* 241 */       int random = randomizer.nextInt(this.players.size());
/*     */       
/* 243 */       List<Player> playersList = new ArrayList<>(this.players.keySet());
/*     */       
/* 245 */       Player selectedIt = playersList.get(random);
/*     */       
/* 247 */       itPlayers.add(selectedIt);
/*     */     } else {
/* 249 */       int isItAmount = Math.round(this.players.size() * ReviversTwo.getConfiguration().getInt("TNT Percentage") / 100.0F);
/*     */       
/* 251 */       List<Player> playerList = new ArrayList<>(this.players.keySet());
/* 252 */       for (int i = 0; i < isItAmount; i++) {
/* 253 */         int random = randomizer.nextInt(playerList.size());
/*     */         
/* 255 */         itPlayers.add(playerList.get(random));
/* 256 */         playerList.remove(random);
/*     */       } 
/*     */     } 
/*     */     
/* 260 */     if (this.round == 1) {
/* 261 */       this.players.forEach((player, tagState) -> player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 3.0F, 1.0F));
/*     */     }
/*     */     
/* 264 */     StringBuilder stringBuilder = new StringBuilder();
/* 265 */     stringBuilder.append("&eThe TNT has been released to ");
/* 266 */     AtomicInteger loopNumber = new AtomicInteger();
/*     */     
/* 268 */     itPlayers.forEach(player -> {
/*     */           loopNumber.getAndIncrement();
/*     */           
/*     */           String separator = (loopNumber.get() == itPlayers.size() - 1) ? " and " : ((loopNumber.get() == itPlayers.size()) ? "!" : ", ");
/*     */           stringBuilder.append(ReviversTwo.getChat().getPlayerSuffix(player)).append(player.getName()).append("&e").append(separator);
/*     */           tag(player);
/*     */         });
/* 275 */     this.players.forEach((player, tagState) -> {
/*     */           player.sendMessage("\n");
/*     */           
/*     */           String roundStartMessage = (this.players.size() > 6) ? ("&f&lRound " + this.round + " has started!\n") : "&f&lDeathmatch has started!";
/*     */           Chat.sendCenteredMessageV2(player, roundStartMessage);
/*     */           Chat.sendCenteredMessageV2(player, stringBuilder.toString());
/*     */           player.sendMessage("\n");
/*     */           String startAs = tagState.booleanValue() ? "&cYou started as IT! Give it to someone else quickly!\n" : "&aYou did NOT start as IT! Run away!\n";
/*     */           Chat.sendCenteredMessageV2(player, startAs);
/*     */           player.sendMessage("\n");
/*     */         });
/* 286 */     this.spectators.forEach(player -> {
/*     */           player.sendMessage("\n");
/*     */           
/*     */           String roundStartMessage = (this.players.size() > 6) ? ("&f&lRound " + this.round + " has started!\n") : "&f&lDeathmatch has started!";
/*     */           Chat.sendCenteredMessageV2(player, roundStartMessage);
/*     */           Chat.sendCenteredMessageV2(player, stringBuilder.toString());
/*     */           player.sendMessage("\n");
/*     */         });
/* 294 */     if (this.players.size() <= 6) {
/* 295 */       for (int i = 0; i < 3; i++) {
/* 296 */         this.players.forEach((player, tagState) -> player.teleport(this.spawnLocation));
/*     */         
/* 298 */         this.spectators.forEach(player -> player.teleport(this.spawnLocation));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 303 */     startGameTimer();
/*     */   }
/*     */   
/*     */   private void startGameTimer() {
/* 307 */     int secondsToRemove = this.round * 5;
/* 308 */     if (secondsToRemove > 35) secondsToRemove = 35;
/*     */     
/* 310 */     int originalTimer = 60 - secondsToRemove + 1;
/*     */     
/* 312 */     this.timer.set(originalTimer);
/* 313 */     AtomicBoolean hasReachedZero = new AtomicBoolean(false);
/*     */     
/* 315 */     this.timerTaskId.set(Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)ReviversTwo.getPlugin(), () -> { List<Player> totalPlayers = new ArrayList<>(this.players.keySet()); totalPlayers.addAll(this.spectators); if (this.timer.get() > 0) this.timer.getAndDecrement();  for (Player player : totalPlayers) { player.setExp(1.0F / (float)Integer.toUnsignedLong(originalTimer) * (float)Integer.toUnsignedLong(this.timer.get())); player.setLevel(this.timer.get()); }  updateScoreboards(); if (this.timer.get() == 0 && !hasReachedZero.get()) { hasReachedZero.set(true); List<Player> dominoEffect = new ArrayList<>(); List<Player> toKill = new ArrayList<>(); List<Player> players = new CopyOnWriteArrayList<>(); List<Player> itPlayers = new CopyOnWriteArrayList<>(); this.players.forEach(()); while (this.players.containsValue(Boolean.valueOf(true))) { for (Player player : itPlayers) { if (!dominoEffect.contains(player)) totalPlayers.forEach(());  List<Entity> nearbyEntities = player.getNearbyEntities(2.6D, 2.6D, 2.6D); for (Entity entity : nearbyEntities) { if (entity instanceof Player) { Player nearbyPlayer = (Player)entity; if (PlayerHandler.getPlayerGame(nearbyPlayer) == null || PlayerHandler.getPlayerGame(nearbyPlayer) != this || !players.contains(nearbyPlayer)) continue;  if (toKill.contains(nearbyPlayer)) continue;  Random random = new Random(); int blastProtectionPercentage = random.nextInt(100) + 1; if (blastProtectionPercentage <= this.blastProtection) { nearbyPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYour &bBlast Protection&e saved you from " + ReviversTwo.getChat().getPlayerSuffix(player) + player.getName() + "&e's explosion!")); continue; }  this.players.replace(nearbyPlayer, Boolean.valueOf(true)); players.remove(nearbyPlayer); dominoEffect.add(nearbyPlayer); itPlayers.add(nearbyPlayer); totalPlayers.forEach(()); }  }  List<Player> combinedLists = (List<Player>)Stream.<List>of(new List[] { players, itPlayers }).flatMap(Collection::stream).collect(Collectors.toList()); runWinnerUpdater(combinedLists); this.world.playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 0); for (Player loopPlayer : totalPlayers) { if (loopPlayer == player) continue;  loopPlayer.playSound(player.getLocation(), Sound.EXPLODE, 100.0F, 0.5F); }  untag(player); itPlayers.remove(player); toKill.add(player); addSpectator(player); player.playSound(player.getLocation(), Sound.EXPLODE, 100.0F, 0.5F); runWinnerUpdater(players); }  }  if (toKill.size() == this.players.size()) this.first.clear();  Objects.requireNonNull(toKill); this.players.keySet().removeIf(toKill::contains); if (players.size() <= 1) { endGame(); } else { Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)ReviversTwo.getPlugin(), this::startRound, 160L); }  Bukkit.getScheduler().cancelTask(this.timerTaskId.get()); updateScoreboards(); }  }0L, 20L));
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
/*     */   public void runWinnerUpdater(List<Player> playerList) {
/* 442 */     playerList.forEach(winPlayerUpdate -> {
/*     */           switch (playerList.size()) {
/*     */             case 3:
/*     */               this.third.add(winPlayerUpdate);
/*     */               break;
/*     */             case 2:
/*     */               this.third.remove(winPlayerUpdate);
/*     */               this.second.add(winPlayerUpdate);
/*     */               break;
/*     */             case 1:
/*     */               this.second.remove(winPlayerUpdate);
/*     */               this.first.add(winPlayerUpdate);
/*     */               break;
/*     */           } 
/*     */         });
/*     */   }
/*     */   public void tag(Player attacker, Player victim) {
/* 459 */     if (!isIt(attacker).booleanValue() || isIt(victim).booleanValue())
/*     */       return; 
/* 461 */     untag(attacker);
/* 462 */     tag(victim);
/*     */     
/* 464 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 465 */     totalPlayers.addAll(this.spectators);
/*     */     
/* 467 */     attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou tagged " + ReviversTwo.getChat().getPlayerSuffix(victim) + victim.getName() + "&a!"));
/* 468 */     victim.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + ReviversTwo.getChat().getPlayerSuffix(attacker) + attacker.getName() + "&c tagged you!"));
/* 469 */     totalPlayers.forEach(player -> {
/*     */           if (player.equals(attacker) || player.equals(victim))
/*     */             return; 
/*     */           player.sendMessage(ChatColor.translateAlternateColorCodes('&', ReviversTwo.getChat().getPlayerSuffix(victim) + ReviversTwo.getChat().getPlayerSuffix(victim) + "&7 is IT!"));
/*     */         });
/*     */   }
/*     */   public void tag(Player player) {
/* 476 */     this.players.replace(player, Boolean.valueOf(true));
/*     */     
/* 478 */     player.removePotionEffect(PotionEffectType.SPEED);
/* 479 */     player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));
/*     */     
/* 481 */     player.playSound(player.getLocation(), Sound.NOTE_PLING, 3.0F, 2.0F);
/*     */     
/* 483 */     NametagEdit.getApi().setPrefix(player, "&c[IT] ");
/*     */     
/* 485 */     player.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
/* 486 */     player.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
/*     */     
/* 488 */     ItemStack compass = new ItemStack(Material.COMPASS, 1);
/* 489 */     ItemMeta meta = compass.getItemMeta();
/* 490 */     meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aNearest Player"));
/* 491 */     compass.setItemMeta(meta);
/* 492 */     player.getInventory().setItem(1, compass);
/*     */     
/* 494 */     updateScoreboards();
/*     */     
/* 496 */     Firework fw = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
/* 497 */     FireworkMeta fwm = fw.getFireworkMeta();
/*     */     
/* 499 */     fwm.setPower(0);
/* 500 */     fwm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.CREEPER).withColor(Color.GREEN).withFade(Color.RED).build());
/*     */     
/* 502 */     fw.setFireworkMeta(fwm);
/*     */     
/* 504 */     Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)ReviversTwo.getPlugin(), () -> { Random random = new Random(); int fireworkChance = random.nextInt(100) + 1; if (fireworkChance <= this.fireworkPercentage) { Objects.requireNonNull(fw); Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)ReviversTwo.getPlugin(), fw::detonate, 7L); } else { fw.remove(); }  }2L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void untag(Player player) {
/*     */     ItemStack is;
/* 516 */     player.removePotionEffect(PotionEffectType.SPEED);
/* 517 */     player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 1));
/*     */     
/* 519 */     this.players.replace(player, Boolean.valueOf(false));
/* 520 */     updateScoreboards();
/*     */     
/* 522 */     NametagEdit.getApi().setPrefix(player, "&f");
/*     */     
/* 524 */     if (MatchManager.hasPlayerMatch(player)) {
/* 525 */       Match playerMatch = MatchManager.getPlayerMatch(player);
/* 526 */       is = playerMatch.getPlayerHat(player).item();
/*     */     } else {
/* 528 */       is = new ItemStack(Material.AIR, 1);
/*     */     } 
/* 530 */     player.getInventory().setHelmet(is);
/*     */     
/* 532 */     player.getInventory().setItem(0, new ItemStack(Material.AIR, 0));
/* 533 */     player.getInventory().setItem(1, new ItemStack(Material.AIR, 0));
/*     */   }
/*     */   
/*     */   public void kill(Player player) {
/* 537 */     this.players.remove(player);
/* 538 */     untag(player);
/* 539 */     addSpectator(player);
/*     */     
/* 541 */     updateScoreboards();
/* 542 */     runWinnerUpdater(new ArrayList<>(this.players.keySet()));
/*     */     
/* 544 */     if (this.players.size() <= 1)
/* 545 */       endGame(); 
/*     */   }
/*     */   
/*     */   public void addSpectator(Player player) {
/* 549 */     ActionBar actionBar = new ActionBar();
/* 550 */     actionBar.sendActionBar(player, "");
/* 551 */     player.teleport(this.lobbyLocation);
/* 552 */     this.spectators.add(player);
/*     */     
/* 554 */     NametagEdit.getApi().setPrefix(player, "&7[DEAD] ");
/*     */     
/* 556 */     player.setAllowFlight(true);
/*     */     
/* 558 */     player.getInventory().clear();
/* 559 */     player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
/*     */ 
/*     */     
/* 562 */     player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0));
/*     */     
/* 564 */     for (Player loopedPlayer : this.players.keySet()) {
/* 565 */       loopedPlayer.hidePlayer(player);
/* 566 */       for (Player spectatorLoopPlayer : this.spectators) {
/* 567 */         loopedPlayer.hidePlayer(spectatorLoopPlayer);
/*     */       }
/*     */     } 
/*     */     
/* 571 */     for (Player spectatorLoopPlayer : this.spectators) {
/* 572 */       player.hidePlayer(spectatorLoopPlayer);
/* 573 */       spectatorLoopPlayer.hidePlayer(player);
/*     */     } 
/*     */     
/* 576 */     if (MatchManager.hasPlayerMatch(player) && 
/* 577 */       MatchManager.getPlayerMatch(player).getLeader() == player) {
/*     */       
/* 579 */       ItemStack paper = new ItemStack(Material.PAPER, 1);
/* 580 */       ItemMeta paperMeta = paper.getItemMeta();
/* 581 */       paperMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lPlay Again &7(Right Click)"));
/* 582 */       paper.setItemMeta(paperMeta);
/* 583 */       player.getInventory().setItem(7, paper);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 588 */     ItemStack bed = new ItemStack(Material.BED, 1);
/* 589 */     ItemMeta bedMeta = bed.getItemMeta();
/* 590 */     bedMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lReturn to Lobby &7(Right Click)"));
/* 591 */     bed.setItemMeta(bedMeta);
/* 592 */     player.getInventory().setItem(8, bed);
/*     */ 
/*     */     
/* 595 */     ItemStack playerTeleporter = new ItemStack(Material.COMPASS, 1);
/* 596 */     ItemMeta playerTeleporterMeta = playerTeleporter.getItemMeta();
/* 597 */     playerTeleporterMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lTeleporter &7(Right Click)"));
/* 598 */     playerTeleporter.setItemMeta(playerTeleporterMeta);
/* 599 */     player.getInventory().setItem(0, playerTeleporter);
/*     */   }
/*     */   
/*     */   public void endGame() {
/* 603 */     this.hasGameEnded = true;
/*     */     
/* 605 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 606 */     totalPlayers.addAll(this.spectators);
/*     */     
/* 608 */     totalPlayers.forEach(player -> {
/*     */           player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
/*     */           Chat.sendCenteredMessageV2(player, "&f&lTNT Tag");
/*     */           player.sendMessage("");
/*     */           if (this.first.size() >= 1) {
/*     */             Chat.sendCenteredMessageV2(player, "&e&l1st Place: " + ReviversTwo.getChat().getPlayerPrefix(this.first.get(0)) + ((Player)this.first.get(0)).getName());
/*     */             if (this.second.size() > 0) {
/*     */               Chat.sendCenteredMessageV2(player, "&e&l2nd Place: " + ReviversTwo.getChat().getPlayerPrefix(this.second.get(0)) + ((Player)this.second.get(0)).getName());
/*     */             }
/*     */             if (this.third.size() > 0)
/*     */               Chat.sendCenteredMessageV2(player, "&e&l3rd Place: " + ReviversTwo.getChat().getPlayerPrefix(this.third.get(0)) + ((Player)this.third.get(0)).getName()); 
/*     */           } else {
/*     */             Chat.sendCenteredMessageV2(player, "&eWinner &7-&c DRAW!");
/*     */           } 
/*     */           player.sendMessage("");
/*     */           player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
/*     */           Location reviversLobbyLocation = ReviversTwo.getLobbyLocation();
/*     */           player.teleport(reviversLobbyLocation);
/*     */         });
/* 627 */     Bukkit.getScheduler().runTaskLater((Plugin)ReviversTwo.getPlugin(), this::destroyGameRemains, 200L);
/*     */   }
/*     */   
/*     */   public void destroyGameRemains() {
/* 631 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 632 */     totalPlayers.addAll(this.spectators);
/*     */     
/* 634 */     totalPlayers.forEach(player -> leave(player, false));
/*     */     
/* 636 */     if (this.round != 0) {
/* 637 */       Bukkit.getScheduler().cancelTask(this.compassUpdaterTask.get());
/*     */     }
/* 639 */     GameManager.removeGame(this.id);
/*     */   }
/*     */   
/*     */   public void startLobbyTimer() {
/* 643 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 644 */     totalPlayers.addAll(this.spectators);
/*     */     
/* 646 */     this.timer.set(15);
/* 647 */     AtomicInteger taskId = new AtomicInteger();
/*     */     
/* 649 */     AtomicBoolean hasReachedZero = new AtomicBoolean(false);
/*     */     
/* 651 */     taskId.set(Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)ReviversTwo.getPlugin(), () -> { if (!hasReachedZero.get()) { this.timer.getAndDecrement(); if (this.players.size() < this.minPlayers) { Bukkit.getScheduler().cancelTask(taskId.get()); this.timer.set(15); updateScoreboards(); return; }  updateScoreboards(); if (this.timer.get() == 10) { String message = ChatColor.translateAlternateColorCodes('&', "&eThe game starts in &6" + this.timer.get() + "&e seconds!"); totalPlayers.forEach(()); }  if (this.timer.get() <= 5 && this.timer.get() != 0) { String message = ChatColor.translateAlternateColorCodes('&', "&eThe game starts in &c" + this.timer.get() + "&e seconds!"); totalPlayers.forEach(()); }  if (this.timer.get() == 0) { hasReachedZero.set(true); this.players.forEach(()); startRound(); this.compassUpdaterTask.set(Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)ReviversTwo.getPlugin(), (), 0L, 1L)); Bukkit.getScheduler().cancelTask(taskId.get()); }  }  }0L, 20L));
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
/*     */   public Game(String id, Arena arena, int maxPlayers, int minPlayers, int blastProtection) {
/* 739 */     this.white = true; this.arena = arena; this.id = id; this.maxPlayers = maxPlayers; this.minPlayers = minPlayers; this.blastProtection = blastProtection; this.world = WorldManager.cloneWorld(this, arena); this.spawnLocation = new Location(this.world, arena.getSpawnLocation().getX(), arena.getSpawnLocation().getY(), arena.getSpawnLocation().getZ(), arena.getSpawnLocation().getYaw(), arena.getSpawnLocation().getPitch());
/*     */     this.lobbyLocation = new Location(this.world, arena.getLobbyLocation().getX(), arena.getLobbyLocation().getY(), arena.getLobbyLocation().getZ(), arena.getLobbyLocation().getYaw(), arena.getLobbyLocation().getPitch());
/* 741 */     updateScoreboards(); } private void updateScoreboards() { DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
/* 742 */     List<Player> totalPlayers = new ArrayList<>(this.players.keySet());
/* 743 */     totalPlayers.addAll(this.spectators);
/* 744 */     if (this.round == 0) {
/* 745 */       totalPlayers.forEach(player -> {
/*     */             if (!this.boards.containsKey(player)) {
/*     */               this.boards.put(player, new FastBoard(player));
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
/*     */               ((FastBoard)this.boards.get(player)).updateTitle(ChatColor.translateAlternateColorCodes('&', "&e&lTNT TAG"));
/*     */             } 
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
/*     */             if (this.players.size() < this.minPlayers) {
/*     */               ((FastBoard)this.boards.get(player)).updateLines(new String[] { 
/*     */                     ChatColor.translateAlternateColorCodes('&', "&7" + dateFormat.format(Date.from(Instant.now())) + " &8" + this.id), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fMap: &a" + this.arena.getName()), ChatColor.translateAlternateColorCodes('&', "&fPlayers: &a" + this.players.size() + "/" + this.maxPlayers), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fStarting in &a" + this.timer.get() + "s&f if"), ChatColor.translateAlternateColorCodes('&', "&a" + this.minPlayers - this.players.size() + "&f more players join"), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fGame: &aTNT Tag"), ChatColor.translateAlternateColorCodes('&', ""), 
/*     */                     ChatColor.translateAlternateColorCodes('&', "&e" + ReviversTwo.getConfiguration().getString("Server IP")) });
/*     */             } else {
/*     */               ((FastBoard)this.boards.get(player)).updateLines(new String[] { 
/*     */                     ChatColor.translateAlternateColorCodes('&', "&7" + dateFormat.format(Date.from(Instant.now())) + " &8" + this.id), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fMap: &a" + this.arena.getName()), ChatColor.translateAlternateColorCodes('&', "&fPlayers: &a" + this.players.size() + "/" + this.maxPlayers), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fStarting in &a" + this.timer.get() + "s&f to"), ChatColor.translateAlternateColorCodes('&', "&fallow time for"), ChatColor.translateAlternateColorCodes('&', "&fadditional players"), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fGame: &aTNT Tag"), 
/*     */                     ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&e" + ReviversTwo.getConfiguration().getString("Server IP")) });
/*     */             } 
/*     */           });
/*     */     } else {
/* 786 */       this.white = !this.white;
/*     */       
/* 788 */       this.players.forEach((player, tagState) -> {
/*     */             if (!this.boards.containsKey(player)) {
/*     */               this.boards.put(player, new FastBoard(player));
/*     */ 
/*     */ 
/*     */               
/*     */               ((FastBoard)this.boards.get(player)).updateTitle(ChatColor.translateAlternateColorCodes('&', "&e&lTNT TAG"));
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             ((FastBoard)this.boards.get(player)).updateLines(new String[] { ChatColor.translateAlternateColorCodes('&', "&7Round #" + this.round), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&eExplosion in " + ((this.timer.get() <= 15) ? ((this.timer.get() <= 5) ? ("&c" + this.timer.get() + "s") : ("&6" + this.timer.get() + "s")) : ("&a" + this.timer.get() + "s"))), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fGoal: " + (tagState.booleanValue() ? "&cTag someone!" : "&aRun away!")), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fAlive: &a" + this.players.size() + " Players"), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&7" + dateFormat.format(Date.from(Instant.now())) + " &8" + this.id), ChatColor.translateAlternateColorCodes('&', "&e" + ReviversTwo.getConfiguration().getString("Server IP")) });
/*     */ 
/*     */ 
/*     */             
/*     */             ActionBar actionBar = new ActionBar();
/*     */ 
/*     */             
/*     */             if (tagState.booleanValue()) {
/*     */               String color = this.white ? "&f" : "&c";
/*     */ 
/*     */               
/*     */               actionBar.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', color + "You're IT, tag someone!"));
/*     */             } else {
/*     */               actionBar.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', "&aRun away!"));
/*     */             } 
/*     */           });
/*     */ 
/*     */       
/* 817 */       this.spectators.forEach(player -> {
/*     */             if (!this.boards.containsKey(player)) {
/*     */               this.boards.put(player, new FastBoard(player));
/*     */               ((FastBoard)this.boards.get(player)).updateTitle(ChatColor.translateAlternateColorCodes('&', "&e&lTNT TAG"));
/*     */             } 
/*     */             ((FastBoard)this.boards.get(player)).updateLines(new String[] { ChatColor.translateAlternateColorCodes('&', "&7Round #" + this.round), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&eExplosion in " + ((this.timer.get() <= 15) ? ((this.timer.get() <= 5) ? ("&c" + this.timer.get() + "s") : ("&6" + this.timer.get() + "s")) : ("&a" + this.timer.get() + "s"))), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&fAlive: &a" + this.players.size() + " Players"), ChatColor.translateAlternateColorCodes('&', ""), ChatColor.translateAlternateColorCodes('&', "&7" + dateFormat.format(Date.from(Instant.now())) + " &8" + this.id), ChatColor.translateAlternateColorCodes('&', "&e" + ReviversTwo.getConfiguration().getString("Server IP")) });
/*     */           });
/*     */     }  }
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
/*     */   public Boolean isIt(Player player) {
/* 840 */     if (!this.players.containsKey(player)) return null; 
/* 841 */     return this.players.get(player);
/*     */   }
/*     */   
/*     */   public ArrayList<Player> getSpectators() {
/* 845 */     return this.spectators;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 849 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getMinPlayers() {
/* 853 */     return this.minPlayers;
/*     */   }
/*     */   
/*     */   public int getMaxPlayers() {
/* 857 */     return this.maxPlayers;
/*     */   }
/*     */   
/*     */   public void setMaxPlayers(int maxPlayers) {
/* 861 */     this.maxPlayers = maxPlayers;
/*     */   }
/*     */   
/*     */   public LinkedHashMap<Player, Boolean> getPlayers() {
/* 865 */     return this.players;
/*     */   }
/*     */   
/*     */   public int getRound() {
/* 869 */     return this.round;
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\Game.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */