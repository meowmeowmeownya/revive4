/*     */ package net.revivers.reviverstwo.matches;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.revivers.reviverstwo.ReviversTwo;
/*     */ import net.revivers.reviverstwo.arenas.Arena;
/*     */ import net.revivers.reviverstwo.arenas.games.Game;
/*     */ import net.revivers.reviverstwo.arenas.games.PlayerHandler;
/*     */ import net.revivers.reviverstwo.arenas.games.hats.Hat;
/*     */ import net.revivers.reviverstwo.arenas.games.hats.HatManager;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.annotation.Permission;
/*     */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Command;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Default;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.SubCommand;
/*     */ import net.revivers.reviverstwo.gui.builder.gui.SimpleBuilder;
/*     */ import net.revivers.reviverstwo.gui.builder.item.ItemBuilder;
/*     */ import net.revivers.reviverstwo.gui.guis.Gui;
/*     */ import net.revivers.reviverstwo.gui.guis.GuiItem;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ @Command(value = "match", alias = {"m", "party", "p"})
/*     */ public class MatchCommand extends BaseCommand {
/*     */   @Default
/*     */   @Permission({"revivers.match"})
/*     */   public void defaultCommand(CommandSender sender) {
/*  38 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*  39 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match leave"));
/*  40 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match message"));
/*  41 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match list"));
/*  42 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match hat"));
/*  43 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match promote (Player)"));
/*  44 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match invite (Player)"));
/*  45 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match kick (Player)"));
/*  46 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match teleport"));
/*  47 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match disband"));
/*  48 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match start"));
/*  49 */     if (ReviversTwo.getPermissions().has(sender, "revivers.match.custommap"))
/*  50 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match map")); 
/*  51 */     if (ReviversTwo.getPermissions().has(sender, "revivers.match.custombp"))
/*  52 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &e/match bp (0-100)")); 
/*  53 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */   }
/*     */   
/*     */   @SubCommand(value = "leave", alias = {"exit", "l"})
/*     */   public void leaveSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/*  59 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/*  61 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/*  62 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*  63 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/*  64 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     Match match = MatchManager.getPlayerMatch(playerSender);
/*  69 */     match.leave(playerSender);
/*     */   }
/*     */   @SubCommand(value = "message", alias = {"m", "chat", "c"})
/*     */   public void messageSubCommand(CommandSender sender, List<String> args) {
/*     */     Player playerSender;
/*  74 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/*  76 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/*  77 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*  78 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/*  79 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     if (args.isEmpty()) {
/*  84 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*  85 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe message can't be blank."));
/*  86 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     Match match = MatchManager.getPlayerMatch(playerSender);
/*  91 */     StringBuilder message = new StringBuilder();
/*  92 */     for (String arg : args) {
/*  93 */       message.append(arg).append(" ");
/*     */     }
/*     */     
/*  96 */     for (Player player : match.getPlayers())
/*  97 */       player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lMatch &7≫ " + ReviversTwo.getChat().getPlayerPrefix(playerSender) + playerSender.getName() + "&7: ") + ChatColor.translateAlternateColorCodes('&', "&6&lMatch &7≫ " + ReviversTwo.getChat().getPlayerPrefix(playerSender) + playerSender.getName() + "&7: ")); 
/*     */   }
/*     */   
/*     */   @SubCommand(value = "list", alias = {"ls"})
/*     */   public void listSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 103 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 105 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 106 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 107 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 108 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 113 */     Player matchLeader = match.getLeader();
/*     */     
/* 115 */     int loopCount = 0;
/* 116 */     StringBuilder players = new StringBuilder();
/* 117 */     for (Player player : match.getPlayers()) {
/* 118 */       loopCount++;
/* 119 */       if (player == matchLeader)
/*     */         continue; 
/* 121 */       if (loopCount == match.getPlayers().size()) {
/* 122 */         players.append(ReviversTwo.getChat().getPlayerPrefix(player)).append(player.getName()); continue;
/*     */       } 
/* 124 */       players.append(ReviversTwo.getChat().getPlayerPrefix(player)).append(player.getName()).append("&e, ");
/*     */     } 
/*     */ 
/*     */     
/* 128 */     playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 129 */     playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eMatch Leader: &r" + ReviversTwo.getChat().getPlayerPrefix(matchLeader) + matchLeader.getName()));
/* 130 */     playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e"));
/* 131 */     playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&ePlayers: &r" + players));
/* 132 */     playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */   }
/*     */   @SubCommand(value = "sethat", alias = {"sethat", "pickhat", "hat", "h"})
/*     */   public void setHatSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 137 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 139 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 140 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 141 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 142 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 146 */     openHatsGui(playerSender);
/*     */   }
/*     */   @SubCommand(value = "promote", alias = {"prom"})
/*     */   public void promoteSubCommand(CommandSender sender, Player promotedPlayer) {
/*     */     Player playerSender;
/* 151 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 153 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 154 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 155 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 156 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 161 */     if (match.getLeader() != playerSender) {
/* 162 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 163 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the leader of the match can promote players."));
/* 164 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     if (promotedPlayer == null) {
/* 169 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 170 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown player. Please, enter a valid player username."));
/* 171 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     if (promotedPlayer == playerSender) {
/* 176 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 177 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't promote yourself."));
/* 178 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 182 */     if (!match.getPlayers().contains(promotedPlayer)) {
/* 183 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 184 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified player is not in your match."));
/* 185 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 189 */     match.promote(promotedPlayer);
/*     */   }
/*     */   @SubCommand(value = "invite", alias = {"add", "i", "inv"})
/*     */   public void inviteSubCommand(CommandSender sender, Player invitedPlayer) {
/*     */     Player playerSender;
/* 194 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 196 */      if (MatchManager.getPlayerMatch(playerSender) == null) {
/* 197 */       MatchManager.addPlayerMatch(playerSender, new Match(playerSender));
/*     */     }
/* 199 */     Match match = MatchManager.getPlayerMatch(playerSender);
/*     */     
/* 201 */     if (match.getLeader() != playerSender) {
/* 202 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 203 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the leader of the match can invite players."));
/* 204 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 208 */     if (invitedPlayer == null) {
/* 209 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 210 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown player. Please, enter a valid player username."));
/* 211 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 215 */     if (MatchManager.hasPlayerMatch(invitedPlayer)) {
/* 216 */       Match invitedPlayerMatch = MatchManager.getPlayerMatch(invitedPlayer);
/* 217 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 218 */       if (invitedPlayerMatch != match) {
/* 219 */         playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified player is in another match."));
/*     */       } else {
/* 221 */         playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified player is already in your match."));
/*     */       } 
/* 223 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 227 */     if (MatchManager.hasPlayerMatchInvite(invitedPlayer)) {
/* 228 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 229 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified player already has a pending match invite."));
/* 230 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 234 */     if (match.getPlayers().size() >= 8 && 
/* 235 */       !ReviversTwo.getPermissions().has(playerSender, "revivers.match.big")) {
/* 236 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 237 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to create matches with more than 8 players."));
/* 238 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 243 */     match.invite(invitedPlayer, playerSender);
/*     */   }
/*     */   @SubCommand(value = "kick", alias = {"k", "remove", "r"})
/*     */   public void kickSubCommand(CommandSender sender, Player kickPlayer) {
/*     */     Player playerSender;
/* 248 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 250 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 251 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 252 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 253 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 257 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 258 */     if (match.getLeader() != playerSender) {
/* 259 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 260 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the leader of the match can kick players."));
/* 261 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 265 */     if (kickPlayer == null) {
/* 266 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 267 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown player. Please, enter a valid player username."));
/* 268 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 272 */     if (kickPlayer == playerSender) {
/* 273 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 274 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't kick yourself."));
/* 275 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 279 */     if (!match.getPlayers().contains(kickPlayer)) {
/* 280 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 281 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified player is not in your match."));
/* 282 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 286 */     match.kick(kickPlayer);
/*     */   }
/*     */   @SubCommand(value = "teleport", alias = {"tpall", "tp", "bring", "bringall"})
/*     */   public void teleportSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 291 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 293 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 294 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 295 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 296 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 300 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 301 */     if (match.getLeader() != playerSender) {
/* 302 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 303 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the Match Leader can teleport all players to the game."));
/* 304 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 308 */     if (!PlayerHandler.hasPlayerGame(playerSender)) {
/* 309 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 310 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to be in a game to use this command."));
/* 311 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       return;
/*     */     } 
/* 314 */     Game game = PlayerHandler.getPlayerGame(playerSender);
/*     */     
/* 316 */     for (Player player : match.getPlayers()) {
/* 317 */       if (!PlayerHandler.hasPlayerGame(player)) {
/* 318 */         if (game.getRound() == 0)
/* 319 */           game.setMaxPlayers(game.getMaxPlayers() + 1); 
/* 320 */         game.join(player);
/*     */         
/*     */         continue;
/*     */       } 
/* 324 */       if (PlayerHandler.getPlayerGame(player) != game) {
/* 325 */         PlayerHandler.getPlayerGame(player).leave(player, false);
/* 326 */         if (game.getRound() == 0)
/* 327 */           game.setMaxPlayers(game.getMaxPlayers() + 1); 
/* 328 */         game.join(player);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @SubCommand(value = "disband", alias = {"disb", "db"})
/*     */   public void disbandSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 335 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 337 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 338 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 339 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 340 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 344 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 345 */     if (match.getLeader() != playerSender) {
/* 346 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 347 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe match can only be disbanded by the Match Leader."));
/* 348 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 352 */     match.disband();
/*     */   }
/*     */   @SubCommand(value = "startgame", alias = {"start", "s", "warp", "sg"})
/*     */   public void startGameSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 357 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 359 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 360 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 361 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 362 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 366 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 367 */     if (match.getLeader() != playerSender) {
/* 368 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 369 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the leader of the match can start a game."));
/* 370 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 374 */     if (match.getPlayers().size() < 2) {
/* 375 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 376 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThere's not enough players to start the game."));
/* 377 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 381 */     if (match.getLastGameStartTime() + 15L > Instant.now().getEpochSecond()) {
/* 382 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 383 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must wait before starting another game."));
/* 384 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 388 */     match.start();
/*     */   }
/*     */   @SubCommand(value = "mapselector", alias = {"arena", "maps", "m", "map", "setmap", "selectmap"})
/*     */   @Permission({"revivers.match.custommap"})
/*     */   public void mapSelectorSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 394 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 396 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 397 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 398 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 399 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 403 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 404 */     if (match.getLeader() != playerSender) {
/* 405 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 406 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the leader of the match can select the map."));
/* 407 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 411 */     ArrayList<Integer> skippedSlots = new ArrayList<>(Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(9), Integer.valueOf(18), Integer.valueOf(27), Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(19), Integer.valueOf(28) }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 417 */     Gui mapGui = ((SimpleBuilder)((SimpleBuilder)((SimpleBuilder)Gui.gui().disableAllInteractions()).title((Component)Component.text("Select a map:"))).rows(4)).create();
/* 418 */     int count = 0;
/* 419 */     for (Arena arena : ArenaManager.getArenas()) {
/* 420 */       count++;
/* 421 */       for (; skippedSlots.contains(Integer.valueOf(count)); count++);
/*     */       
/* 423 */       ItemStack item = new ItemStack(Material.PAPER, 1);
/*     */       
/* 425 */       ItemMeta itemMeta = item.getItemMeta();
/* 426 */       itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + arena.getName()));
/* 427 */       ArrayList<String> lore = new ArrayList<>();
/* 428 */       lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click this item to"));
/* 429 */       lore.add(ChatColor.translateAlternateColorCodes('&', "&7select this map."));
/*     */       
/* 431 */       if (match.getSelectedMap() != null && 
/* 432 */         match.getSelectedMap().equals(arena)) {
/* 433 */         itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
/* 434 */         itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
/* 435 */         lore.add(ChatColor.translateAlternateColorCodes('&', "&7"));
/* 436 */         lore.add(ChatColor.translateAlternateColorCodes('&', "&a&lSELECTED"));
/*     */       } 
/*     */ 
/*     */       
/* 440 */       itemMeta.setLore(lore);
/* 441 */       item.setItemMeta(itemMeta);
/*     */       
/* 443 */       GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
/*     */             match.setSelectedMap(arena);
/*     */             
/*     */             mapGui.close((HumanEntity)playerSender);
/*     */             for (Player player : match.getPlayers()) {
/*     */               player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */               player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe match map has been set to &a" + arena.getName() + "&e by the Match Leader."));
/*     */               player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */             } 
/*     */           });
/* 453 */       mapGui.setItem(count, guiItem);
/*     */     } 
/*     */ 
/*     */     
/* 457 */     ItemStack randomMapItem = new ItemStack(Material.BOOK, 1);
/* 458 */     ItemMeta randomMapItemMeta = randomMapItem.getItemMeta();
/* 459 */     randomMapItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eUse Random Maps"));
/* 460 */     ArrayList<String> randomMapLore = new ArrayList<>();
/* 461 */     randomMapLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click this item to"));
/* 462 */     randomMapLore.add(ChatColor.translateAlternateColorCodes('&', "&7have maps be random."));
/* 463 */     randomMapItemMeta.setLore(randomMapLore);
/* 464 */     if (match.getSelectedMap() == null) {
/* 465 */       randomMapItemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
/* 466 */       randomMapItemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
/*     */     } 
/* 468 */     randomMapItem.setItemMeta(randomMapItemMeta);
/* 469 */     GuiItem randomMapGuiItem = ItemBuilder.from(randomMapItem).asGuiItem(event -> {
/*     */           match.setSelectedMap(null);
/*     */           mapGui.close((HumanEntity)playerSender);
/*     */           for (Player player : match.getPlayers()) {
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe match map has been set to &a&lRANDOM&e by the Match Leader."));
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */           } 
/*     */         });
/* 478 */     mapGui.setItem(9, randomMapGuiItem);
/*     */ 
/*     */     
/* 481 */     ItemStack closeGuiItem = new ItemStack(Material.BARRIER, 1);
/* 482 */     ItemMeta closeGuiItemMeta = closeGuiItem.getItemMeta();
/* 483 */     closeGuiItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cClose"));
/* 484 */     ArrayList<String> closeGuiLore = new ArrayList<>();
/* 485 */     closeGuiLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click this item to"));
/* 486 */     closeGuiLore.add(ChatColor.translateAlternateColorCodes('&', "&7close this menu."));
/* 487 */     closeGuiItemMeta.setLore(closeGuiLore);
/* 488 */     closeGuiItem.setItemMeta(closeGuiItemMeta);
/* 489 */     GuiItem closeGuiGuiItem = ItemBuilder.from(closeGuiItem).asGuiItem(event -> mapGui.close((HumanEntity)playerSender));
/* 490 */     mapGui.setItem(18, closeGuiGuiItem);
/*     */ 
/*     */     
/* 493 */     ItemStack blackStainedGlassPaneItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
/* 494 */     ItemMeta blackStainedGlassPaneMeta = blackStainedGlassPaneItem.getItemMeta();
/* 495 */     blackStainedGlassPaneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8"));
/* 496 */     blackStainedGlassPaneItem.setItemMeta(blackStainedGlassPaneMeta);
/* 497 */     for (int slot = 1; slot < 29; slot += 9) {
/* 498 */       mapGui.setItem(slot, ItemBuilder.from(blackStainedGlassPaneItem).asGuiItem());
/*     */     }
/*     */     
/* 501 */     mapGui.open((HumanEntity)playerSender);
/*     */   }
/*     */   @SubCommand(value = "setblastprotection", alias = {"setbp", "setblast", "setprotection", "bp", "blast", "protection"})
/*     */   @Permission({"revivers.match.custombp"})
/*     */   public void setBlastProtectionSubCommand(CommandSender sender, Integer blastProtection) {
/*     */     Player playerSender;
/* 507 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 509 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 510 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 511 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou aren't in a match."));
/* 512 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 516 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 517 */     if (match.getLeader() != playerSender) {
/* 518 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 519 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly the leader of the match can adjust the blast protection %."));
/* 520 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 524 */     if (blastProtection == null) {
/* 525 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 526 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe provided number is invalid."));
/* 527 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 531 */     if (blastProtection.intValue() > 100 || blastProtection.intValue() < 0) {
/* 532 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 533 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe provided number is out of bounds. Percentages may only range from 0 to 100."));
/* 534 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 538 */     match.setBlastProtection(blastProtection.intValue());
/*     */   }
/*     */   @SubCommand(value = "accept", alias = {"acc", "a"})
/*     */   public void acceptSubCommand(CommandSender sender) {
/*     */     Player playerSender;
/* 543 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 545 */      if (!MatchManager.hasPlayerMatchInvite(playerSender)) {
/* 546 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 547 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have any pending match invite."));
/* 548 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 552 */     Match match = MatchManager.getPlayerMatchInvite(playerSender);
/* 553 */     MatchManager.removePlayerMatchInvite(playerSender);
/* 554 */     if (match.isDisbanded()) {
/* 555 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/* 556 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe match was disbanded before you joined."));
/* 557 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 561 */     match.add(playerSender);
/*     */   }
/*     */   
/*     */   @SubCommand(value = "revivers", alias = {"rvrs", "credits", "creds", "plugin"})
/*     */   public void reviversSubCommand(CommandSender sender) {
/* 566 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
/* 567 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lRevivers: &c2nd Edition &7(PLUGIN)"));
/* 568 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╠ &cPlugin made by sdap &7(@dap#7998)"));
/* 569 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 ╚ &cDesigned and made for Revivers &7(revivers.net)"));
/* 570 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----------------------------"));
/*     */   }
/*     */   
/*     */   private void openHatsGui(Player player) {
/* 574 */     Match match = MatchManager.getPlayerMatch(player);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 580 */     Gui gui = ((SimpleBuilder)((SimpleBuilder)((SimpleBuilder)Gui.gui().disableAllInteractions()).title((Component)Component.text("Hat Selector"))).rows(3)).create();
/*     */ 
/*     */     
/* 583 */     Hat defaultHat = (Hat)HatManager.getHats().get(ReviversTwo.getConfiguration().getString("Default Hat"));
/*     */     
/* 585 */     ItemStack defaultHatItem = new ItemStack(Material.BARRIER, 1);
/* 586 */     ItemMeta defaultHatItemMeta = defaultHatItem.getItemMeta();
/*     */     
/* 588 */     defaultHatItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Default Hat &7(" + defaultHat.name() + "&7)"));
/*     */     
/* 590 */     List<String> defaultHatLore = new ArrayList<>();
/* 591 */     defaultHatLore.add(ChatColor.translateAlternateColorCodes('&', "&7"));
/* 592 */     defaultHat.lore().forEach(loreLine -> defaultHatLore.add(ChatColor.translateAlternateColorCodes('&', loreLine)));
/* 593 */     defaultHatLore.add(ChatColor.translateAlternateColorCodes('&', "&7"));
/* 594 */     if (match.getPlayerHat(player) == defaultHat)
/* 595 */       defaultHatLore.add(ChatColor.translateAlternateColorCodes('&', "&a&lSELECTED")); 
/* 596 */     defaultHatItemMeta.setLore(defaultHatLore);
/*     */     
/* 598 */     defaultHatItem.setItemMeta(defaultHatItemMeta);
/*     */     
/* 600 */     GuiItem defaultHatGuiItem = ItemBuilder.from(defaultHatItem).asGuiItem(event -> {
/*     */           if (match.getPlayerHat(player) != defaultHat) {
/*     */             match.setPlayerHat(player, defaultHat);
/*     */             
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYour new hat for this match is: &a" + defaultHat.name() + "&e."));
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */           } else {
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are already using this hat."));
/*     */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m-----------------------------"));
/*     */           } 
/*     */           gui.close((HumanEntity)player);
/*     */         });
/* 614 */     gui.setItem(11, defaultHatGuiItem);
/*     */ 
/*     */     
/* 617 */     ItemStack defaultHatsItem = new ItemStack(Material.GOLD_BLOCK, 1);
/* 618 */     ItemMeta defaultHatsItemMeta = defaultHatsItem.getItemMeta();
/*     */     
/* 620 */     defaultHatsItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eBasic Hats"));
/*     */     
/* 622 */     List<String> defaultHatsLore = new ArrayList<>();
/* 623 */     defaultHatsLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click here to show"));
/* 624 */     defaultHatsLore.add(ChatColor.translateAlternateColorCodes('&', "&7the basic hats."));
/* 625 */     defaultHatsItemMeta.setLore(defaultHatsLore);
/*     */     
/* 627 */     defaultHatsItem.setItemMeta(defaultHatsItemMeta);
/*     */     
/* 629 */     GuiItem defaultHatsGuiItem = ItemBuilder.from(defaultHatsItem).asGuiItem(event -> {
/*     */           Gui defaultHatsGui = ((SimpleBuilder)((SimpleBuilder)((SimpleBuilder)Gui.gui().disableAllInteractions()).title((Component)Component.text("Select a Hat:"))).rows(3)).create();
/*     */           
/*     */           ArrayList<Integer> skippedSlots = new ArrayList<>(Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(9), Integer.valueOf(18), Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(19) }));
/*     */           
/*     */           int count = 0;
/*     */           
/*     */           for (Hat hat : HatManager.getHats().values()) {
/*     */             if (hat.permission() != null || hat.equals(defaultHat)) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             while (skippedSlots.contains(Integer.valueOf(++count))) {
/*     */               count++;
/*     */             }
/*     */             
/*     */             ItemStack hatItem = hat.item();
/*     */             
/*     */             if (match.getPlayerHat(player) == hat) {
/*     */               ItemMeta hatItemMeta = hatItem.getItemMeta();
/*     */               
/*     */               List<String> hatLore = new ArrayList<>(hatItemMeta.getLore());
/*     */               
/*     */               hatLore.add(ChatColor.translateAlternateColorCodes('&', "&a&lSELECTED"));
/*     */               
/*     */               hatItemMeta.setLore(hatLore);
/*     */               
/*     */               hatItem.setItemMeta(hatItemMeta);
/*     */             } 
/*     */             
/*     */             GuiItem guiItem = ItemBuilder.from(hatItem).asGuiItem(());
/*     */             
/*     */             defaultHatsGui.setItem(count, guiItem);
/*     */           } 
/*     */           
/*     */           ItemStack closeGuiItem = new ItemStack(Material.ARROW, 1);
/*     */           
/*     */           ItemMeta closeGuiItemMeta = closeGuiItem.getItemMeta();
/*     */           
/*     */           closeGuiItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Go back"));
/*     */           
/*     */           ArrayList<String> closeGuiLore = new ArrayList<>();
/*     */           
/*     */           closeGuiLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click this item to go back"));
/*     */           
/*     */           closeGuiLore.add(ChatColor.translateAlternateColorCodes('&', "&7to the hats main menu."));
/*     */           
/*     */           closeGuiItemMeta.setLore(closeGuiLore);
/*     */           
/*     */           closeGuiItem.setItemMeta(closeGuiItemMeta);
/*     */           
/*     */           GuiItem closeGuiGuiItem = ItemBuilder.from(closeGuiItem).asGuiItem(());
/*     */           
/*     */           defaultHatsGui.setItem(9, closeGuiGuiItem);
/*     */           
/*     */           ItemStack blackStainedGlassPaneItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
/*     */           
/*     */           ItemMeta blackStainedGlassPaneMeta = blackStainedGlassPaneItem.getItemMeta();
/*     */           
/*     */           blackStainedGlassPaneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8"));
/*     */           
/*     */           blackStainedGlassPaneItem.setItemMeta(blackStainedGlassPaneMeta);
/*     */           
/*     */           for (int slot = 1; slot < 20; slot += 9) {
/*     */             defaultHatsGui.setItem(slot, ItemBuilder.from(blackStainedGlassPaneItem).asGuiItem());
/*     */           }
/*     */           
/*     */           defaultHatsGui.open((HumanEntity)player);
/*     */         });
/* 698 */     gui.setItem(13, defaultHatsGuiItem);
/*     */ 
/*     */     
/* 701 */     ItemStack specialHatsItem = new ItemStack(Material.DIAMOND_BLOCK, 1);
/* 702 */     ItemMeta specialHatsItemMeta = specialHatsItem.getItemMeta();
/*     */     
/* 704 */     specialHatsItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bSpecial Hats"));
/*     */     
/* 706 */     List<String> specialHatsLore = new ArrayList<>();
/* 707 */     specialHatsLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click here to show"));
/* 708 */     specialHatsLore.add(ChatColor.translateAlternateColorCodes('&', "&7the special hats."));
/* 709 */     specialHatsItemMeta.setLore(specialHatsLore);
/*     */     
/* 711 */     specialHatsItem.setItemMeta(specialHatsItemMeta);
/*     */     
/* 713 */     GuiItem specialHatsItemGui = ItemBuilder.from(specialHatsItem).asGuiItem(event -> {
/*     */           Gui specialHatsGui = ((SimpleBuilder)((SimpleBuilder)((SimpleBuilder)Gui.gui().disableAllInteractions()).title((Component)Component.text("Select an Special Hat:"))).rows(3)).create();
/*     */           
/*     */           ArrayList<Integer> skippedSlots = new ArrayList<>(Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(9), Integer.valueOf(18), Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(19) }));
/*     */           
/*     */           int count = 0;
/*     */           
/*     */           for (Hat hat : HatManager.getHats().values()) {
/*     */             if (hat.permission() == null || hat.equals(defaultHat)) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             if (!ReviversTwo.getPermissions().has(player, "revivers.match.hats." + hat.permission())) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             while (skippedSlots.contains(Integer.valueOf(++count))) {
/*     */               count++;
/*     */             }
/*     */             
/*     */             ItemStack hatItem = hat.item();
/*     */             
/*     */             if (match.getPlayerHat(player) == hat) {
/*     */               ItemMeta hatItemMeta = hatItem.getItemMeta();
/*     */               
/*     */               List<String> hatLore = new ArrayList<>(hatItemMeta.getLore());
/*     */               
/*     */               hatLore.add(ChatColor.translateAlternateColorCodes('&', "&a&lSELECTED"));
/*     */               
/*     */               hatItemMeta.setLore(hatLore);
/*     */               
/*     */               hatItem.setItemMeta(hatItemMeta);
/*     */             } 
/*     */             
/*     */             GuiItem guiItem = ItemBuilder.from(hatItem).asGuiItem(());
/*     */             
/*     */             specialHatsGui.setItem(count, guiItem);
/*     */           } 
/*     */           
/*     */           ItemStack closeGuiItem = new ItemStack(Material.ARROW, 1);
/*     */           
/*     */           ItemMeta closeGuiItemMeta = closeGuiItem.getItemMeta();
/*     */           
/*     */           closeGuiItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Go back"));
/*     */           
/*     */           ArrayList<String> closeGuiLore = new ArrayList<>();
/*     */           
/*     */           closeGuiLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click this item to go back"));
/*     */           
/*     */           closeGuiLore.add(ChatColor.translateAlternateColorCodes('&', "&7to the hats main menu."));
/*     */           
/*     */           closeGuiItemMeta.setLore(closeGuiLore);
/*     */           
/*     */           closeGuiItem.setItemMeta(closeGuiItemMeta);
/*     */           
/*     */           GuiItem closeGuiGuiItem = ItemBuilder.from(closeGuiItem).asGuiItem(());
/*     */           
/*     */           specialHatsGui.setItem(9, closeGuiGuiItem);
/*     */           
/*     */           ItemStack blackStainedGlassPaneItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
/*     */           
/*     */           ItemMeta blackStainedGlassPaneMeta = blackStainedGlassPaneItem.getItemMeta();
/*     */           
/*     */           blackStainedGlassPaneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8"));
/*     */           
/*     */           blackStainedGlassPaneItem.setItemMeta(blackStainedGlassPaneMeta);
/*     */           
/*     */           for (int slot = 1; slot < 20; slot += 9) {
/*     */             specialHatsGui.setItem(slot, ItemBuilder.from(blackStainedGlassPaneItem).asGuiItem());
/*     */           }
/*     */           specialHatsGui.open((HumanEntity)player);
/*     */         });
/* 785 */     gui.setItem(15, specialHatsItemGui);
/*     */     
/* 787 */     gui.open((HumanEntity)player);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\matches\MatchCommand.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */