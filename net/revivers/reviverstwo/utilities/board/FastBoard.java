/*     */ package net.revivers.reviverstwo.utilities.board;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class FastBoard {
/*     */   private static final String[] COLOR_CODES;
/*     */   private static final VersionType VERSION_TYPE;
/*     */   private static final Class<?> CHAT_COMPONENT_CLASS;
/*     */   private static final Class<?> CHAT_FORMAT_ENUM;
/*     */   private static final Object EMPTY_MESSAGE;
/*     */   private static final Object RESET_FORMATTING;
/*  17 */   private static final Map<Class<?>, Field[]> PACKETS = (Map)new HashMap<>(8); private static final MethodHandle MESSAGE_FROM_STRING; private static final MethodHandle PLAYER_CONNECTION; private static final MethodHandle SEND_PACKET; private static final MethodHandle PLAYER_GET_HANDLE; private static final FastReflection.PacketConstructor PACKET_SB_OBJ;
/*     */   
/*     */   static {
/*  20 */     COLOR_CODES = (String[])Arrays.<ChatColor>stream(ChatColor.values()).map(Object::toString).toArray(x$0 -> new String[x$0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  46 */       MethodHandles.Lookup lookup = MethodHandles.lookup();
/*     */       
/*  48 */       if (FastReflection.isRepackaged()) {
/*  49 */         VERSION_TYPE = VersionType.V1_17;
/*  50 */       } else if (FastReflection.nmsOptionalClass(null, "ScoreboardServer$Action").isPresent()) {
/*  51 */         VERSION_TYPE = VersionType.V1_13;
/*  52 */       } else if (FastReflection.nmsOptionalClass(null, "IScoreboardCriteria$EnumScoreboardHealthDisplay").isPresent()) {
/*  53 */         VERSION_TYPE = VersionType.V1_8;
/*     */       } else {
/*  55 */         VERSION_TYPE = VersionType.V1_7;
/*     */       } 
/*     */       
/*  58 */       String gameProtocolPackage = "network.protocol.game";
/*  59 */       Class<?> craftPlayerClass = FastReflection.obcClass("entity.CraftPlayer");
/*  60 */       Class<?> craftChatMessageClass = FastReflection.obcClass("util.CraftChatMessage");
/*  61 */       Class<?> entityPlayerClass = FastReflection.nmsClass("server.level", "EntityPlayer");
/*  62 */       Class<?> playerConnectionClass = FastReflection.nmsClass("server.network", "PlayerConnection");
/*  63 */       Class<?> packetClass = FastReflection.nmsClass("network.protocol", "Packet");
/*  64 */       Class<?> packetSbObjClass = FastReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardObjective");
/*  65 */       Class<?> packetSbDisplayObjClass = FastReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardDisplayObjective");
/*  66 */       Class<?> packetSbScoreClass = FastReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardScore");
/*  67 */       Class<?> packetSbTeamClass = FastReflection.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardTeam");
/*     */       
/*  69 */       Class<?> sbTeamClass = VersionType.V1_17.isHigherOrEqual() ? FastReflection.innerClass(packetSbTeamClass, innerClass -> !innerClass.isEnum()) : null;
/*     */ 
/*     */       
/*  72 */       Field playerConnectionField = (Field)Arrays.<Field>stream(entityPlayerClass.getFields()).filter(field -> field.getType().isAssignableFrom(playerConnectionClass)).findFirst().orElseThrow(NoSuchFieldException::new);
/*     */       
/*  74 */       MESSAGE_FROM_STRING = lookup.unreflect(craftChatMessageClass.getMethod("fromString", new Class[] { String.class }));
/*  75 */       CHAT_COMPONENT_CLASS = FastReflection.nmsClass("network.chat", "IChatBaseComponent");
/*  76 */       CHAT_FORMAT_ENUM = FastReflection.nmsClass(null, "EnumChatFormat");
/*  77 */       EMPTY_MESSAGE = Array.get(MESSAGE_FROM_STRING.invoke(""), 0);
/*  78 */       RESET_FORMATTING = FastReflection.enumValueOf(CHAT_FORMAT_ENUM, "RESET", 21);
/*  79 */       PLAYER_GET_HANDLE = lookup.findVirtual(craftPlayerClass, "getHandle", MethodType.methodType(entityPlayerClass));
/*  80 */       PLAYER_CONNECTION = lookup.unreflectGetter(playerConnectionField);
/*  81 */       SEND_PACKET = lookup.findVirtual(playerConnectionClass, "sendPacket", MethodType.methodType(void.class, packetClass));
/*  82 */       PACKET_SB_OBJ = FastReflection.findPacketConstructor(packetSbObjClass, lookup);
/*  83 */       PACKET_SB_DISPLAY_OBJ = FastReflection.findPacketConstructor(packetSbDisplayObjClass, lookup);
/*  84 */       PACKET_SB_SCORE = FastReflection.findPacketConstructor(packetSbScoreClass, lookup);
/*  85 */       PACKET_SB_TEAM = FastReflection.findPacketConstructor(packetSbTeamClass, lookup);
/*  86 */       PACKET_SB_SERIALIZABLE_TEAM = (sbTeamClass == null) ? null : FastReflection.findPacketConstructor(sbTeamClass, lookup);
/*     */       
/*  88 */       for (Class<?> clazz : Arrays.<Class<?>[]>asList((Class<?>[][])new Class[] { packetSbObjClass, packetSbDisplayObjClass, packetSbScoreClass, packetSbTeamClass, sbTeamClass })) {
/*  89 */         if (clazz == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/*  94 */         Field[] fields = (Field[])Arrays.<Field>stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).toArray(x$0 -> new Field[x$0]);
/*  95 */         for (Field field : fields) {
/*  96 */           field.setAccessible(true);
/*     */         }
/*  98 */         PACKETS.put(clazz, fields);
/*     */       } 
/*     */       
/* 101 */       if (VersionType.V1_8.isHigherOrEqual()) {
/*     */ 
/*     */         
/* 104 */         String enumSbActionClass = VersionType.V1_13.isHigherOrEqual() ? "ScoreboardServer$Action" : "PacketPlayOutScoreboardScore$EnumScoreboardAction";
/* 105 */         ENUM_SB_HEALTH_DISPLAY = FastReflection.nmsClass("world.scores.criteria", "IScoreboardCriteria$EnumScoreboardHealthDisplay");
/* 106 */         ENUM_SB_ACTION = FastReflection.nmsClass("server", enumSbActionClass);
/* 107 */         ENUM_SB_HEALTH_DISPLAY_INTEGER = FastReflection.enumValueOf(ENUM_SB_HEALTH_DISPLAY, "INTEGER", 0);
/* 108 */         ENUM_SB_ACTION_CHANGE = FastReflection.enumValueOf(ENUM_SB_ACTION, "CHANGE", 0);
/* 109 */         ENUM_SB_ACTION_REMOVE = FastReflection.enumValueOf(ENUM_SB_ACTION, "REMOVE", 1);
/*     */       } else {
/* 111 */         ENUM_SB_HEALTH_DISPLAY = null;
/* 112 */         ENUM_SB_ACTION = null;
/* 113 */         ENUM_SB_HEALTH_DISPLAY_INTEGER = null;
/* 114 */         ENUM_SB_ACTION_CHANGE = null;
/* 115 */         ENUM_SB_ACTION_REMOVE = null;
/*     */       } 
/* 117 */     } catch (Throwable t) {
/* 118 */       throw new ExceptionInInitializerError(t);
/*     */     } 
/*     */   }
/*     */   private static final FastReflection.PacketConstructor PACKET_SB_DISPLAY_OBJ; private static final FastReflection.PacketConstructor PACKET_SB_SCORE; private static final FastReflection.PacketConstructor PACKET_SB_TEAM; private static final FastReflection.PacketConstructor PACKET_SB_SERIALIZABLE_TEAM; private static final Class<?> ENUM_SB_HEALTH_DISPLAY; private static final Class<?> ENUM_SB_ACTION; private static final Object ENUM_SB_HEALTH_DISPLAY_INTEGER; private static final Object ENUM_SB_ACTION_CHANGE;
/*     */   private static final Object ENUM_SB_ACTION_REMOVE;
/*     */   private final Player player;
/*     */   private final String id;
/* 125 */   private final List<String> lines = new ArrayList<>();
/* 126 */   private String title = ChatColor.RESET.toString();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deleted = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBoard(Player player) {
/* 136 */     this.player = Objects.<Player>requireNonNull(player, "player");
/* 137 */     this.id = "fb-" + Integer.toHexString(ThreadLocalRandom.current().nextInt());
/*     */     
/*     */     try {
/* 140 */       sendObjectivePacket(ObjectiveMode.CREATE);
/* 141 */       sendDisplayObjectivePacket();
/* 142 */     } catch (Throwable t) {
/* 143 */       throw new RuntimeException("Unable to create scoreboard", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 153 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTitle(String title) {
/* 164 */     if (this.title.equals(Objects.requireNonNull(title, "title"))) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     if (!VersionType.V1_13.isHigherOrEqual() && title.length() > 32) {
/* 169 */       throw new IllegalArgumentException("Title is longer than 32 chars");
/*     */     }
/*     */     
/* 172 */     this.title = title;
/*     */     
/*     */     try {
/* 175 */       sendObjectivePacket(ObjectiveMode.UPDATE);
/* 176 */     } catch (Throwable t) {
/* 177 */       throw new RuntimeException("Unable to update scoreboard title", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getLines() {
/* 187 */     return new ArrayList<>(this.lines);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLine(int line) {
/* 198 */     checkLineNumber(line, true, false);
/*     */     
/* 200 */     return this.lines.get(line);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void updateLine(int line, String text) {
/* 211 */     checkLineNumber(line, false, true);
/*     */     
/*     */     try {
/* 214 */       if (line < size()) {
/* 215 */         this.lines.set(line, text);
/*     */         
/* 217 */         sendTeamPacket(getScoreByLine(line), TeamMode.UPDATE);
/*     */         
/*     */         return;
/*     */       } 
/* 221 */       List<String> newLines = new ArrayList<>(this.lines);
/*     */       
/* 223 */       if (line > size()) {
/* 224 */         for (int i = size(); i < line; i++) {
/* 225 */           newLines.add("");
/*     */         }
/*     */       }
/*     */       
/* 229 */       newLines.add(text);
/*     */       
/* 231 */       updateLines(newLines);
/* 232 */     } catch (Throwable t) {
/* 233 */       throw new RuntimeException("Unable to update scoreboard lines", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeLine(int line) {
/* 243 */     checkLineNumber(line, false, false);
/*     */     
/* 245 */     if (line >= size()) {
/*     */       return;
/*     */     }
/*     */     
/* 249 */     List<String> newLines = new ArrayList<>(this.lines);
/* 250 */     newLines.remove(line);
/* 251 */     updateLines(newLines);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLines(String... lines) {
/* 262 */     updateLines(Arrays.asList(lines));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void updateLines(Collection<String> lines) {
/* 273 */     Objects.requireNonNull(lines, "lines");
/* 274 */     checkLineNumber(lines.size(), false, true);
/*     */     
/* 276 */     if (!VersionType.V1_13.isHigherOrEqual()) {
/* 277 */       int lineCount = 0;
/* 278 */       for (String s : lines) {
/* 279 */         if (s != null && s.length() > 30) {
/* 280 */           throw new IllegalArgumentException("Line " + lineCount + " is longer than 30 chars");
/*     */         }
/* 282 */         lineCount++;
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     List<String> oldLines = new ArrayList<>(this.lines);
/* 287 */     this.lines.clear();
/* 288 */     this.lines.addAll(lines);
/*     */     
/* 290 */     int linesSize = this.lines.size();
/*     */     
/*     */     try {
/* 293 */       if (oldLines.size() != linesSize) {
/* 294 */         List<String> oldLinesCopy = new ArrayList<>(oldLines);
/*     */         
/* 296 */         if (oldLines.size() > linesSize) {
/* 297 */           for (int j = oldLinesCopy.size(); j > linesSize; j--) {
/* 298 */             sendTeamPacket(j - 1, TeamMode.REMOVE);
/* 299 */             sendScorePacket(j - 1, ScoreboardAction.REMOVE);
/*     */             
/* 301 */             oldLines.remove(0);
/*     */           } 
/*     */         } else {
/* 304 */           for (int j = oldLinesCopy.size(); j < linesSize; j++) {
/* 305 */             sendScorePacket(j, ScoreboardAction.CHANGE);
/* 306 */             sendTeamPacket(j, TeamMode.CREATE);
/*     */             
/* 308 */             oldLines.add(oldLines.size() - j, getLineByScore(j));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 313 */       for (int i = 0; i < linesSize; i++) {
/* 314 */         if (!Objects.equals(getLineByScore(oldLines, i), getLineByScore(i))) {
/* 315 */           sendTeamPacket(i, TeamMode.UPDATE);
/*     */         }
/*     */       } 
/* 318 */     } catch (Throwable t) {
/* 319 */       throw new RuntimeException("Unable to update scoreboard lines", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/* 329 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 338 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeleted() {
/* 347 */     return this.deleted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 356 */     return this.lines.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {
/*     */     try {
/* 367 */       for (int i = 0; i < this.lines.size(); i++) {
/* 368 */         sendTeamPacket(i, TeamMode.REMOVE);
/*     */       }
/*     */       
/* 371 */       sendObjectivePacket(ObjectiveMode.REMOVE);
/* 372 */     } catch (Throwable t) {
/* 373 */       throw new RuntimeException("Unable to delete scoreboard", t);
/*     */     } 
/*     */     
/* 376 */     this.deleted = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasLinesMaxLength() {
/* 387 */     return !VersionType.V1_13.isHigherOrEqual();
/*     */   }
/*     */   
/*     */   private void checkLineNumber(int line, boolean checkInRange, boolean checkMax) {
/* 391 */     if (line < 0) {
/* 392 */       throw new IllegalArgumentException("Line number must be positive");
/*     */     }
/*     */     
/* 395 */     if (checkInRange && line >= this.lines.size()) {
/* 396 */       throw new IllegalArgumentException("Line number must be under " + this.lines.size());
/*     */     }
/*     */     
/* 399 */     if (checkMax && line >= COLOR_CODES.length - 1) {
/* 400 */       throw new IllegalArgumentException("Line number is too high: " + line);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getScoreByLine(int line) {
/* 405 */     return this.lines.size() - line - 1;
/*     */   }
/*     */   
/*     */   private String getLineByScore(int score) {
/* 409 */     return getLineByScore(this.lines, score);
/*     */   }
/*     */   
/*     */   private String getLineByScore(List<String> lines, int score) {
/* 413 */     return lines.get(lines.size() - score - 1);
/*     */   }
/*     */   
/*     */   private void sendObjectivePacket(ObjectiveMode mode) throws Throwable {
/* 417 */     Object packet = PACKET_SB_OBJ.invoke();
/*     */     
/* 419 */     setField(packet, String.class, this.id);
/* 420 */     setField(packet, int.class, Integer.valueOf(mode.ordinal()));
/*     */     
/* 422 */     if (mode != ObjectiveMode.REMOVE) {
/* 423 */       setComponentField(packet, this.title, 1);
/*     */       
/* 425 */       if (VersionType.V1_8.isHigherOrEqual()) {
/* 426 */         setField(packet, ENUM_SB_HEALTH_DISPLAY, ENUM_SB_HEALTH_DISPLAY_INTEGER);
/*     */       }
/* 428 */     } else if (VERSION_TYPE == VersionType.V1_7) {
/* 429 */       setField(packet, String.class, "", 1);
/*     */     } 
/*     */     
/* 432 */     sendPacket(packet);
/*     */   }
/*     */   
/*     */   private void sendDisplayObjectivePacket() throws Throwable {
/* 436 */     Object packet = PACKET_SB_DISPLAY_OBJ.invoke();
/*     */     
/* 438 */     setField(packet, int.class, Integer.valueOf(1));
/* 439 */     setField(packet, String.class, this.id);
/*     */     
/* 441 */     sendPacket(packet);
/*     */   }
/*     */   
/*     */   private void sendScorePacket(int score, ScoreboardAction action) throws Throwable {
/* 445 */     Object packet = PACKET_SB_SCORE.invoke();
/*     */     
/* 447 */     setField(packet, String.class, COLOR_CODES[score], 0);
/*     */     
/* 449 */     if (VersionType.V1_8.isHigherOrEqual()) {
/* 450 */       setField(packet, ENUM_SB_ACTION, (action == ScoreboardAction.REMOVE) ? ENUM_SB_ACTION_REMOVE : ENUM_SB_ACTION_CHANGE);
/*     */     } else {
/* 452 */       setField(packet, int.class, Integer.valueOf(action.ordinal()), 1);
/*     */     } 
/*     */     
/* 455 */     if (action == ScoreboardAction.CHANGE) {
/* 456 */       setField(packet, String.class, this.id, 1);
/* 457 */       setField(packet, int.class, Integer.valueOf(score));
/*     */     } 
/*     */     
/* 460 */     sendPacket(packet);
/*     */   }
/*     */   
/*     */   private void sendTeamPacket(int score, TeamMode mode) throws Throwable {
/* 464 */     if (mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS) {
/* 465 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/* 468 */     int maxLength = hasLinesMaxLength() ? 16 : 1024;
/* 469 */     Object packet = PACKET_SB_TEAM.invoke();
/*     */     
/* 471 */     setField(packet, String.class, this.id + ":" + this.id);
/* 472 */     setField(packet, int.class, Integer.valueOf(mode.ordinal()), (VERSION_TYPE == VersionType.V1_8) ? 1 : 0);
/*     */     
/* 474 */     if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
/* 475 */       String prefix, line = getLineByScore(score);
/*     */       
/* 477 */       String suffix = null;
/*     */       
/* 479 */       if (line == null || line.isEmpty()) {
/* 480 */         prefix = COLOR_CODES[score] + COLOR_CODES[score];
/* 481 */       } else if (line.length() <= maxLength) {
/* 482 */         prefix = line;
/*     */       } else {
/*     */         
/* 485 */         int index = (line.charAt(maxLength - 1) == '§') ? (maxLength - 1) : maxLength;
/* 486 */         prefix = line.substring(0, index);
/* 487 */         String suffixTmp = line.substring(index);
/* 488 */         ChatColor chatColor = null;
/*     */         
/* 490 */         if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == '§') {
/* 491 */           chatColor = ChatColor.getByChar(suffixTmp.charAt(1));
/*     */         }
/*     */         
/* 494 */         String color = ChatColor.getLastColors(prefix);
/* 495 */         boolean addColor = (chatColor == null || chatColor.isFormat());
/*     */         
/* 497 */         suffix = (addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "") + (addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "");
/*     */       } 
/*     */       
/* 500 */       if (prefix.length() > maxLength || (suffix != null && suffix.length() > maxLength)) {
/*     */         
/* 502 */         prefix = prefix.substring(0, maxLength);
/* 503 */         suffix = (suffix != null) ? suffix.substring(0, maxLength) : null;
/*     */       } 
/*     */       
/* 506 */       if (VersionType.V1_17.isHigherOrEqual()) {
/* 507 */         Object team = PACKET_SB_SERIALIZABLE_TEAM.invoke();
/*     */         
/* 509 */         setComponentField(team, "", 0);
/* 510 */         setField(team, CHAT_FORMAT_ENUM, RESET_FORMATTING);
/* 511 */         setComponentField(team, prefix, 1);
/* 512 */         setComponentField(team, (suffix == null) ? "" : suffix, 2);
/* 513 */         setField(team, String.class, "always", 0);
/* 514 */         setField(team, String.class, "always", 1);
/* 515 */         setField(packet, Optional.class, Optional.of(team));
/*     */       } else {
/* 517 */         setComponentField(packet, prefix, 2);
/* 518 */         setComponentField(packet, (suffix == null) ? "" : suffix, 3);
/* 519 */         setField(packet, String.class, "always", 4);
/* 520 */         setField(packet, String.class, "always", 5);
/*     */       } 
/*     */       
/* 523 */       if (mode == TeamMode.CREATE) {
/* 524 */         setField(packet, Collection.class, Collections.singletonList(COLOR_CODES[score]));
/*     */       }
/*     */     } 
/*     */     
/* 528 */     sendPacket(packet);
/*     */   }
/*     */   
/*     */   private void sendPacket(Object packet) throws Throwable {
/* 532 */     if (this.deleted) {
/* 533 */       throw new IllegalStateException("This FastBoard is deleted");
/*     */     }
/*     */     
/* 536 */     if (this.player.isOnline()) {
/* 537 */       Object entityPlayer = PLAYER_GET_HANDLE.invoke(this.player);
/* 538 */       Object playerConnection = PLAYER_CONNECTION.invoke(entityPlayer);
/* 539 */       SEND_PACKET.invoke(playerConnection, packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setField(Object object, Class<?> fieldType, Object value) throws ReflectiveOperationException {
/* 544 */     setField(object, fieldType, value, 0);
/*     */   }
/*     */   
/*     */   private void setField(Object packet, Class<?> fieldType, Object value, int count) throws ReflectiveOperationException {
/* 548 */     int i = 0;
/* 549 */     for (Field field : (Field[])PACKETS.get(packet.getClass())) {
/* 550 */       if (field.getType() == fieldType && count == i++) {
/* 551 */         field.set(packet, value);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setComponentField(Object packet, String value, int count) throws Throwable {
/* 557 */     if (!VersionType.V1_13.isHigherOrEqual()) {
/* 558 */       setField(packet, String.class, value, count);
/*     */       
/*     */       return;
/*     */     } 
/* 562 */     int i = 0;
/* 563 */     for (Field field : (Field[])PACKETS.get(packet.getClass())) {
/* 564 */       if ((field.getType() == String.class || field.getType() == CHAT_COMPONENT_CLASS) && count == i++)
/* 565 */         field.set(packet, value.isEmpty() ? EMPTY_MESSAGE : Array.get(MESSAGE_FROM_STRING.invoke(value), 0)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   enum ObjectiveMode
/*     */   {
/* 571 */     CREATE, REMOVE, UPDATE;
/*     */   }
/*     */   
/*     */   enum TeamMode {
/* 575 */     CREATE, REMOVE, UPDATE, ADD_PLAYERS, REMOVE_PLAYERS;
/*     */   }
/*     */   
/*     */   enum ScoreboardAction {
/* 579 */     CHANGE, REMOVE;
/*     */   }
/*     */   
/*     */   enum VersionType {
/* 583 */     V1_7, V1_8, V1_13, V1_17;
/*     */     
/*     */     public boolean isHigherOrEqual() {
/* 586 */       return (FastBoard.VERSION_TYPE.ordinal() >= ordinal());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\board\FastBoard.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */