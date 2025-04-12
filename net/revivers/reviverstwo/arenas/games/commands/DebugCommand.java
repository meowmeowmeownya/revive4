/*     */ package net.revivers.reviverstwo.arenas.games.commands;
/*     */ import net.revivers.reviverstwo.arenas.games.Game;
/*     */ import net.revivers.reviverstwo.arenas.games.PlayerHandler;
/*     */ import net.revivers.reviverstwo.cmd.bukkit.annotation.Permission;
/*     */ import net.revivers.reviverstwo.cmd.core.BaseCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Command;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Default;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Optional;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.SubCommand;
/*     */ import net.revivers.reviverstwo.cmd.core.annotation.Suggestion;
/*     */ import net.revivers.reviverstwo.matches.Match;
/*     */ import net.revivers.reviverstwo.matches.MatchManager;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ @Command(value = "debug", alias = {"d", "debugtools", "dt", "dtools"})
/*     */ public class DebugCommand extends BaseCommand {
/*     */   @Default
/*     */   @Permission({"revivers.debug"})
/*     */   public void defaultCommand(CommandSender sender) {
/*  23 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  24 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &a/debug tag (Player)"));
/*  25 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &a/debug untag (Player)"));
/*  26 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &a/debug give (To) [(From)]"));
/*  27 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &a/debug kill (Player)"));
/*  28 */     sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */   }
/*     */   
/*     */   @SubCommand(value = "tag", alias = {"t"})
/*     */   public void tagSubCommand(CommandSender sender, Player toPlayer) {
/*     */     Player playerSender;
/*  34 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/*  36 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/*  37 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  38 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You aren't in a match."));
/*  39 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  43 */     Match match = MatchManager.getPlayerMatch(playerSender);
/*  44 */     if (match.getLeader() != playerSender) {
/*  45 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  46 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Only the leader of the match can use the debug tools."));
/*  47 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     if (toPlayer == null) {
/*  52 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  53 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Unknown player. Please, enter a valid player username."));
/*  54 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     if (!match.getPlayers().contains(toPlayer)) {
/*  59 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  60 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8The target must be in your match."));
/*  61 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  65 */     if (!PlayerHandler.hasPlayerGame(toPlayer)) {
/*  66 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  67 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8The target must be in a game."));
/*  68 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     Game senderGame = PlayerHandler.getPlayerGame(playerSender);
/*  73 */     Game toPlayerGame = PlayerHandler.getPlayerGame(toPlayer);
/*  74 */     if (senderGame != toPlayerGame) {
/*  75 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  76 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You need to be in the same game as your target."));
/*  77 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     if (senderGame.getRound() == 0) {
/*  82 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  83 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Please, wait for the game to start."));
/*  84 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  88 */     if (senderGame.getSpectators().contains(toPlayer)) {
/*  89 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  90 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l" + toPlayer.getDisplayName() + "&8 is an spectator."));
/*  91 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/*  95 */     if (senderGame.isIt(toPlayer).booleanValue()) {
/*  96 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*  97 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Error. &7&l" + toPlayer.getDisplayName() + "&7 is already IT."));
/*  98 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     playerSender.playSound(playerSender.getLocation(), Sound.LEVEL_UP, 100.0F, 2.0F);
/* 103 */     senderGame.tag(toPlayer);
/*     */   }
/*     */   
/*     */   @SubCommand(value = "untag", alias = {"u", "ut"})
/*     */   public void untagSubCommand(CommandSender sender, Player toPlayer) {
/*     */     Player playerSender;
/* 109 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 111 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 112 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 113 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You aren't in a match."));
/* 114 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 119 */     if (match.getLeader() != playerSender) {
/* 120 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 121 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Only the leader of the match can use the debug tools."));
/* 122 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     if (toPlayer == null) {
/* 127 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 128 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Unknown player. Please, enter a valid player username."));
/* 129 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     if (!match.getPlayers().contains(toPlayer)) {
/* 134 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 135 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8The target must be in your match."));
/* 136 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 140 */     if (!PlayerHandler.hasPlayerGame(toPlayer)) {
/* 141 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 142 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8The target must be in a game."));
/* 143 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 147 */     Game senderGame = PlayerHandler.getPlayerGame(playerSender);
/* 148 */     Game toPlayerGame = PlayerHandler.getPlayerGame(toPlayer);
/* 149 */     if (senderGame != toPlayerGame) {
/* 150 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 151 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You need to be in the same game as your target."));
/* 152 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 156 */     if (senderGame.getRound() == 0) {
/* 157 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 158 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Please, wait for the game to start."));
/* 159 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 163 */     if (senderGame.getSpectators().contains(toPlayer)) {
/* 164 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 165 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l" + toPlayer.getDisplayName() + "&8 is an spectator."));
/* 166 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 170 */     if (!senderGame.isIt(toPlayer).booleanValue()) {
/* 171 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 172 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Error. &7&l" + toPlayer.getDisplayName() + "&7 is not IT."));
/* 173 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     playerSender.playSound(playerSender.getLocation(), Sound.LEVEL_UP, 100.0F, 2.0F);
/* 178 */     senderGame.untag(toPlayer);
/*     */   }
/*     */   
/*     */   @SubCommand(value = "give", alias = {"g", "transfer", "tr"})
/*     */   public void giveSubCommand(CommandSender sender, Player toPlayer, @Optional Player fromPlayer) {
/*     */     Player playerSender;
/* 184 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 186 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 187 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 188 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You aren't in a match."));
/* 189 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 193 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 194 */     if (match.getLeader() != playerSender) {
/* 195 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 196 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Only the leader of the match can use the debug tools."));
/* 197 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 201 */     if (fromPlayer == null) {
/* 202 */       fromPlayer = playerSender;
/*     */     }
/* 204 */     if (toPlayer == null) {
/* 205 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 206 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Unknown player. Please, enter a valid player username."));
/* 207 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 211 */     if (!match.getPlayers().contains(fromPlayer) || !match.getPlayers().contains(toPlayer)) {
/* 212 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 213 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8All of the players need to be in your same match."));
/* 214 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     if (!PlayerHandler.hasPlayerGame(toPlayer) || !PlayerHandler.hasPlayerGame(fromPlayer)) {
/* 219 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 220 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8All of the players need to be in a game."));
/* 221 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 225 */     Game senderGame = PlayerHandler.getPlayerGame(playerSender);
/* 226 */     Game fromPlayerGame = PlayerHandler.getPlayerGame(fromPlayer);
/* 227 */     Game toPlayerGame = PlayerHandler.getPlayerGame(toPlayer);
/* 228 */     if (toPlayerGame != fromPlayerGame) {
/* 229 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 230 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Both players need to be in the same game."));
/* 231 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       return;
/*     */     } 
/* 234 */     if (senderGame != fromPlayerGame) {
/* 235 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 236 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You need to be in the same game as your targets."));
/* 237 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 241 */     if (senderGame.getRound() == 0) {
/* 242 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 243 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Please, wait for the game to start."));
/* 244 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 248 */     if (senderGame.getSpectators().contains(fromPlayer)) {
/* 249 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 250 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l" + fromPlayer.getDisplayName() + "&8 is an spectator."));
/* 251 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 255 */     if (senderGame.getSpectators().contains(toPlayer)) {
/* 256 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 257 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l" + toPlayer.getDisplayName() + "&8 is an spectator."));
/* 258 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 262 */     if (!senderGame.isIt(fromPlayer).booleanValue()) {
/* 263 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 264 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Error. &7&l" + fromPlayer.getDisplayName() + "&7 is not IT."));
/* 265 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 269 */     if (senderGame.isIt(toPlayer).booleanValue()) {
/* 270 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 271 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Error. &7&l" + fromPlayer.getDisplayName() + "&7 is already IT."));
/* 272 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 276 */     playerSender.playSound(playerSender.getLocation(), Sound.LEVEL_UP, 100.0F, 2.0F);
/* 277 */     senderGame.tag(fromPlayer, toPlayer);
/*     */   }
/*     */   
/*     */   @SubCommand(value = "kill", alias = {"k", "remove", "r"})
/*     */   @Suggestion(value = "", strict = true)
/*     */   public void killSubCommand(CommandSender sender, Player toPlayer) {
/*     */     Player playerSender;
/* 284 */     if (sender instanceof Player) { playerSender = (Player)sender; }
/*     */     else { return; }
/* 286 */      if (!MatchManager.hasPlayerMatch(playerSender)) {
/* 287 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 288 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You aren't in a match."));
/* 289 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 293 */     Match match = MatchManager.getPlayerMatch(playerSender);
/* 294 */     if (match.getLeader() != playerSender) {
/* 295 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 296 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Only the leader of the match can use the debug tools."));
/* 297 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 301 */     if (toPlayer == null) {
/* 302 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 303 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Unknown player. Please, enter a valid player username."));
/* 304 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 308 */     if (!match.getPlayers().contains(toPlayer)) {
/* 309 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 310 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8The target must be in your match."));
/* 311 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 315 */     if (!PlayerHandler.hasPlayerGame(toPlayer)) {
/* 316 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 317 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8The target must be in a game."));
/* 318 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 322 */     Game senderGame = PlayerHandler.getPlayerGame(playerSender);
/* 323 */     Game toPlayerGame = PlayerHandler.getPlayerGame(toPlayer);
/* 324 */     if (senderGame != toPlayerGame) {
/* 325 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 326 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8You need to be in the same game as your target."));
/* 327 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 331 */     if (senderGame.getRound() == 0) {
/* 332 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 333 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Please, wait for the game to start."));
/* 334 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 338 */     if (senderGame.getSpectators().contains(toPlayer)) {
/* 339 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/* 340 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l" + toPlayer.getDisplayName() + "&8 is an spectator."));
/* 341 */       playerSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2&l&m-----------------------------"));
/*     */       
/*     */       return;
/*     */     } 
/* 345 */     playerSender.playSound(playerSender.getLocation(), Sound.LEVEL_UP, 100.0F, 2.0F);
/* 346 */     senderGame.kill(toPlayer);
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstwo\arenas\games\commands\DebugCommand.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */