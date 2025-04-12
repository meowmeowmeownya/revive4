/*     */ package net.revivers.reviverstwo.utilities;
/*     */ 
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public enum Chat
/*     */ {
/*   8 */   A('A', 5),
/*   9 */   a('a', 5),
/*  10 */   B('B', 5),
/*  11 */   b('b', 5),
/*  12 */   C('C', 5),
/*  13 */   c('c', 5),
/*  14 */   D('D', 5),
/*  15 */   d('d', 5),
/*  16 */   E('E', 5),
/*  17 */   e('e', 5),
/*  18 */   F('F', 5),
/*  19 */   f('f', 4),
/*  20 */   G('G', 5),
/*  21 */   g('g', 5),
/*  22 */   H('H', 5),
/*  23 */   h('h', 5),
/*  24 */   I('I', 3),
/*  25 */   i('i', 1),
/*  26 */   J('J', 5),
/*  27 */   j('j', 5),
/*  28 */   K('K', 5),
/*  29 */   k('k', 4),
/*  30 */   L('L', 5),
/*  31 */   l('l', 1),
/*  32 */   M('M', 5),
/*  33 */   m('m', 5),
/*  34 */   N('N', 5),
/*  35 */   n('n', 5),
/*  36 */   O('O', 5),
/*  37 */   o('o', 5),
/*  38 */   P('P', 5),
/*  39 */   p('p', 5),
/*  40 */   Q('Q', 5),
/*  41 */   q('q', 5),
/*  42 */   R('R', 5),
/*  43 */   r('r', 5),
/*  44 */   S('S', 5),
/*  45 */   s('s', 5),
/*  46 */   T('T', 5),
/*  47 */   t('t', 4),
/*  48 */   U('U', 5),
/*  49 */   u('u', 5),
/*  50 */   V('V', 5),
/*  51 */   v('v', 5),
/*  52 */   W('W', 5),
/*  53 */   w('w', 5),
/*  54 */   X('X', 5),
/*  55 */   x('x', 5),
/*  56 */   Y('Y', 5),
/*  57 */   y('y', 5),
/*  58 */   Z('Z', 5),
/*  59 */   z('z', 5),
/*  60 */   NUM_1('1', 5),
/*  61 */   NUM_2('2', 5),
/*  62 */   NUM_3('3', 5),
/*  63 */   NUM_4('4', 5),
/*  64 */   NUM_5('5', 5),
/*  65 */   NUM_6('6', 5),
/*  66 */   NUM_7('7', 5),
/*  67 */   NUM_8('8', 5),
/*  68 */   NUM_9('9', 5),
/*  69 */   NUM_0('0', 5),
/*  70 */   EXCLAMATION_POINT('!', 1),
/*  71 */   AT_SYMBOL('@', 6),
/*  72 */   NUM_SIGN('#', 5),
/*  73 */   DOLLAR_SIGN('$', 5),
/*  74 */   PERCENT('%', 5),
/*  75 */   UP_ARROW('^', 5),
/*  76 */   AMPERSAND('&', 5),
/*  77 */   ASTERISK('*', 5),
/*  78 */   LEFT_PARENTHESIS('(', 4),
/*  79 */   RIGHT_PARENTHESES(')', 4),
/*  80 */   MINUS('-', 5),
/*  81 */   UNDERSCORE('_', 5),
/*  82 */   PLUS_SIGN('+', 5),
/*  83 */   EQUALS_SIGN('=', 5),
/*  84 */   LEFT_CURL_BRACE('{', 4),
/*  85 */   RIGHT_CURL_BRACE('}', 4),
/*  86 */   LEFT_BRACKET('[', 3),
/*  87 */   RIGHT_BRACKET(']', 3),
/*  88 */   COLON(':', 1),
/*  89 */   SEMI_COLON(';', 1),
/*  90 */   DOUBLE_QUOTE('"', 3),
/*  91 */   SINGLE_QUOTE('\'', 1),
/*  92 */   LEFT_ARROW('<', 4),
/*  93 */   RIGHT_ARROW('>', 4),
/*  94 */   QUESTION_MARK('?', 5),
/*  95 */   SLASH('/', 5),
/*  96 */   BACK_SLASH('\\', 5),
/*  97 */   LINE('|', 1),
/*  98 */   TILDE('~', 5),
/*  99 */   TICK('`', 2),
/* 100 */   PERIOD('.', 1),
/* 101 */   COMMA(',', 1),
/* 102 */   SPACE(' ', 3),
/* 103 */   DEFAULT('a', 4);
/*     */   
/*     */   private final char character;
/*     */   
/*     */   private final int length;
/*     */   
/*     */   private static final int CENTER_PX = 127;
/*     */   
/*     */   private static final int MAX_PX = 240;
/*     */   private static final int CENTER_CHAT_PX = 154;
/*     */   private static final int MAX_CHAT_PX = 250;
/*     */   
/*     */   Chat(char character, int length) {
/* 116 */     this.character = character;
/* 117 */     this.length = length;
/*     */   }
/*     */ 
/*     */   
/*     */   public char getCharacter() {
/* 122 */     return this.character;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 127 */     return this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBoldLength() {
/* 132 */     if (this == SPACE)
/*     */     {
/* 134 */       return getLength();
/*     */     }
/* 136 */     return this.length + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Chat getDefaultFontInfo(char c) {
/* 141 */     for (Chat dFI : values()) {
/*     */       
/* 143 */       if (dFI.getCharacter() == c)
/*     */       {
/* 145 */         return dFI;
/*     */       }
/*     */     } 
/* 148 */     return DEFAULT;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendCenteredMessageV1(Player player, String message) {
/* 153 */     if (message == null || message.equals(""))
/*     */     {
/* 155 */       player.sendMessage("");
/*     */     }
/* 157 */     assert message != null;
/* 158 */     message = ChatColor.translateAlternateColorCodes('&', message);
/*     */     
/* 160 */     int messagePxSize = 0;
/* 161 */     boolean previousCode = false;
/* 162 */     boolean isBold = false;
/*     */     
/* 164 */     for (char c : message.toCharArray()) {
/*     */       
/* 166 */       if (c == '§') {
/*     */         
/* 168 */         previousCode = true;
/* 169 */       } else if (previousCode) {
/*     */         
/* 171 */         previousCode = false;
/* 172 */         isBold = (c == 'l' || c == 'L');
/*     */       } else {
/*     */         
/* 175 */         Chat dFI = getDefaultFontInfo(c);
/* 176 */         messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
/* 177 */         messagePxSize++;
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     int halvedMessageSize = messagePxSize / 2;
/* 182 */     int toCompensate = 127 - halvedMessageSize;
/* 183 */     int spaceLength = SPACE.getLength() + 1;
/* 184 */     int compensated = 0;
/* 185 */     StringBuilder sb = new StringBuilder();
/* 186 */     while (compensated < toCompensate) {
/*     */       
/* 188 */       sb.append(" ");
/* 189 */       compensated += spaceLength;
/*     */     } 
/* 191 */     player.sendMessage("" + sb + sb);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendCenteredMessageV2(Player player, String message) {
/* 196 */     message = ChatColor.translateAlternateColorCodes('&', message);
/* 197 */     int messagePxSize = 0;
/* 198 */     boolean previousCode = false;
/* 199 */     boolean isBold = false;
/* 200 */     int charIndex = 0;
/* 201 */     int lastSpaceIndex = 0;
/* 202 */     String toSendAfter = null;
/* 203 */     String recentColorCode = "";
/* 204 */     char[] arrayOfChar = message.toCharArray(); int i = arrayOfChar.length; byte b = 0; while (true) { if (b < i) { char c = arrayOfChar[b];
/*     */         
/* 206 */         if (c == '§')
/*     */         
/* 208 */         { previousCode = true; }
/*     */         
/* 210 */         else if (previousCode)
/*     */         
/* 212 */         { previousCode = false;
/* 213 */           recentColorCode = "§" + c;
/* 214 */           if (c == 'l' || c == 'L')
/*     */           
/* 216 */           { isBold = true; }
/*     */           
/*     */           else
/*     */           
/* 220 */           { isBold = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 231 */             if (messagePxSize >= 250)
/*     */             
/* 233 */             { toSendAfter = recentColorCode + recentColorCode;
/* 234 */               message = message.substring(0, lastSpaceIndex + 1); break; }  }  } else { if (c == ' ') { lastSpaceIndex = charIndex; } else { Chat dFI = getDefaultFontInfo(c); messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength(); messagePxSize++; }  if (messagePxSize >= 250) { toSendAfter = recentColorCode + recentColorCode; message = message.substring(0, lastSpaceIndex + 1); break; }
/*     */            }
/*     */          }
/*     */       else { break; }
/*     */        b++; }
/* 239 */      int halvedMessageSize = messagePxSize / 2;
/* 240 */     int toCompensate = 154 - halvedMessageSize;
/* 241 */     int spaceLength = SPACE.getLength() + 1;
/* 242 */     int compensated = 0;
/* 243 */     StringBuilder sb = new StringBuilder();
/* 244 */     while (compensated < toCompensate) {
/*     */       
/* 246 */       sb.append(" ");
/* 247 */       compensated += spaceLength;
/*     */     } 
/* 249 */     player.sendMessage("" + sb + sb);
/* 250 */     if (toSendAfter != null)
/*     */     {
/* 252 */       sendCenteredMessageV2(player, toSendAfter);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\Chat.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */