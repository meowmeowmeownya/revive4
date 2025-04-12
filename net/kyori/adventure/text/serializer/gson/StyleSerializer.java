/*     */ package net.kyori.adventure.text.serializer.gson;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.event.ClickEvent;
/*     */ import net.kyori.adventure.text.event.HoverEvent;
/*     */ import net.kyori.adventure.text.event.HoverEventSource;
/*     */ import net.kyori.adventure.text.format.Style;
/*     */ import net.kyori.adventure.text.format.TextColor;
/*     */ import net.kyori.adventure.text.format.TextDecoration;
/*     */ import net.kyori.adventure.util.Codec;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StyleSerializer
/*     */   extends TypeAdapter<Style>
/*     */ {
/*  51 */   private static final TextDecoration[] DECORATIONS = new TextDecoration[] { TextDecoration.BOLD, TextDecoration.ITALIC, TextDecoration.UNDERLINED, TextDecoration.STRIKETHROUGH, TextDecoration.OBFUSCATED };
/*     */   
/*     */   static final String FONT = "font";
/*     */   
/*     */   static final String COLOR = "color";
/*     */   
/*     */   static final String INSERTION = "insertion";
/*     */   static final String CLICK_EVENT = "clickEvent";
/*     */   static final String CLICK_EVENT_ACTION = "action";
/*     */   static final String CLICK_EVENT_VALUE = "value";
/*     */   
/*     */   static {
/*  63 */     Set<TextDecoration> knownDecorations = EnumSet.allOf(TextDecoration.class);
/*  64 */     for (TextDecoration decoration : DECORATIONS) {
/*  65 */       knownDecorations.remove(decoration);
/*     */     }
/*  67 */     if (!knownDecorations.isEmpty()) {
/*  68 */       throw new IllegalStateException("Gson serializer is missing some text decorations: " + knownDecorations);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final String HOVER_EVENT = "hoverEvent";
/*     */   
/*     */   static final String HOVER_EVENT_ACTION = "action";
/*     */   static final String HOVER_EVENT_CONTENTS = "contents";
/*     */   @Deprecated
/*     */   static final String HOVER_EVENT_VALUE = "value";
/*     */   private final LegacyHoverEventSerializer legacyHover;
/*     */   private final boolean emitLegacyHover;
/*     */   private final Gson gson;
/*     */   
/*     */   static TypeAdapter<Style> create(@Nullable LegacyHoverEventSerializer legacyHover, boolean emitLegacyHover, Gson gson) {
/*  84 */     return (new StyleSerializer(legacyHover, emitLegacyHover, gson)).nullSafe();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StyleSerializer(@Nullable LegacyHoverEventSerializer legacyHover, boolean emitLegacyHover, Gson gson) {
/*  92 */     this.legacyHover = legacyHover;
/*  93 */     this.emitLegacyHover = emitLegacyHover;
/*  94 */     this.gson = gson;
/*     */   }
/*     */ 
/*     */   
/*     */   public Style read(JsonReader in) throws IOException {
/*  99 */     in.beginObject();
/* 100 */     Style.Builder style = Style.style();
/*     */     
/* 102 */     while (in.hasNext()) {
/* 103 */       String fieldName = in.nextName();
/* 104 */       if (fieldName.equals("font")) {
/* 105 */         style.font((Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE)); continue;
/* 106 */       }  if (fieldName.equals("color")) {
/* 107 */         TextColorWrapper color = (TextColorWrapper)this.gson.fromJson(in, SerializerFactory.COLOR_WRAPPER_TYPE);
/* 108 */         if (color.color != null) {
/* 109 */           style.color(color.color); continue;
/* 110 */         }  if (color.decoration != null)
/* 111 */           style.decoration(color.decoration, TextDecoration.State.TRUE);  continue;
/*     */       } 
/* 113 */       if (TextDecoration.NAMES.keys().contains(fieldName)) {
/* 114 */         style.decoration((TextDecoration)TextDecoration.NAMES.value(fieldName), in.nextBoolean()); continue;
/* 115 */       }  if (fieldName.equals("insertion")) {
/* 116 */         style.insertion(in.nextString()); continue;
/* 117 */       }  if (fieldName.equals("clickEvent")) {
/* 118 */         in.beginObject();
/* 119 */         ClickEvent.Action action = null;
/* 120 */         String value = null;
/* 121 */         while (in.hasNext()) {
/* 122 */           String clickEventField = in.nextName();
/* 123 */           if (clickEventField.equals("action")) {
/* 124 */             action = (ClickEvent.Action)this.gson.fromJson(in, SerializerFactory.CLICK_ACTION_TYPE); continue;
/* 125 */           }  if (clickEventField.equals("value")) {
/* 126 */             value = (in.peek() == JsonToken.NULL) ? null : in.nextString(); continue;
/*     */           } 
/* 128 */           in.skipValue();
/*     */         } 
/*     */         
/* 131 */         if (action != null && action.readable() && value != null) {
/* 132 */           style.clickEvent(ClickEvent.clickEvent(action, value));
/*     */         }
/* 134 */         in.endObject(); continue;
/* 135 */       }  if (fieldName.equals("hoverEvent")) {
/* 136 */         JsonObject hoverEventObject = (JsonObject)this.gson.fromJson(in, JsonObject.class);
/* 137 */         if (hoverEventObject != null) {
/* 138 */           JsonPrimitive serializedAction = hoverEventObject.getAsJsonPrimitive("action");
/* 139 */           if (serializedAction == null) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 144 */           HoverEvent.Action<Object> action = (HoverEvent.Action<Object>)this.gson.fromJson((JsonElement)serializedAction, SerializerFactory.HOVER_ACTION_TYPE);
/* 145 */           if (action.readable()) {
/*     */             Object value;
/* 147 */             if (hoverEventObject.has("contents")) {
/* 148 */               JsonElement rawValue = hoverEventObject.get("contents");
/* 149 */               Class<?> actionType = action.type();
/* 150 */               if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(actionType)) {
/* 151 */                 value = this.gson.fromJson(rawValue, SerializerFactory.COMPONENT_TYPE);
/* 152 */               } else if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(actionType)) {
/* 153 */                 value = this.gson.fromJson(rawValue, SerializerFactory.SHOW_ITEM_TYPE);
/* 154 */               } else if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(actionType)) {
/* 155 */                 value = this.gson.fromJson(rawValue, SerializerFactory.SHOW_ENTITY_TYPE);
/*     */               } else {
/* 157 */                 value = null;
/*     */               } 
/* 159 */             } else if (hoverEventObject.has("value")) {
/* 160 */               Component rawValue = (Component)this.gson.fromJson(hoverEventObject.get("value"), SerializerFactory.COMPONENT_TYPE);
/* 161 */               value = legacyHoverEventContents(action, rawValue);
/*     */             } else {
/* 163 */               value = null;
/*     */             } 
/*     */             
/* 166 */             if (value != null)
/* 167 */               style.hoverEvent((HoverEventSource)HoverEvent.hoverEvent(action, value)); 
/*     */           } 
/*     */         } 
/*     */         continue;
/*     */       } 
/* 172 */       in.skipValue();
/*     */     } 
/*     */ 
/*     */     
/* 176 */     in.endObject();
/* 177 */     return style.build();
/*     */   }
/*     */   
/*     */   private Object legacyHoverEventContents(HoverEvent.Action<?> action, Component rawValue) {
/* 181 */     if (action == HoverEvent.Action.SHOW_TEXT)
/* 182 */       return rawValue; 
/* 183 */     if (this.legacyHover != null) {
/*     */       try {
/* 185 */         if (action == HoverEvent.Action.SHOW_ENTITY)
/* 186 */           return this.legacyHover.deserializeShowEntity(rawValue, (Codec.Decoder)decoder()); 
/* 187 */         if (action == HoverEvent.Action.SHOW_ITEM) {
/* 188 */           return this.legacyHover.deserializeShowItem(rawValue);
/*     */         }
/* 190 */       } catch (IOException ex) {
/* 191 */         throw new JsonParseException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 195 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private Codec.Decoder<Component, String, JsonParseException> decoder() {
/* 199 */     return string -> (Component)this.gson.fromJson(string, SerializerFactory.COMPONENT_TYPE);
/*     */   }
/*     */   
/*     */   private Codec.Encoder<Component, String, JsonParseException> encoder() {
/* 203 */     return component -> this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Style value) throws IOException {
/* 208 */     out.beginObject();
/*     */     
/* 210 */     for (int i = 0, length = DECORATIONS.length; i < length; i++) {
/* 211 */       TextDecoration decoration = DECORATIONS[i];
/* 212 */       TextDecoration.State state = value.decoration(decoration);
/* 213 */       if (state != TextDecoration.State.NOT_SET) {
/* 214 */         String name = (String)TextDecoration.NAMES.key(decoration);
/* 215 */         assert name != null;
/* 216 */         out.name(name);
/* 217 */         out.value((state == TextDecoration.State.TRUE));
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     TextColor color = value.color();
/* 222 */     if (color != null) {
/* 223 */       out.name("color");
/* 224 */       this.gson.toJson(color, SerializerFactory.COLOR_TYPE, out);
/*     */     } 
/*     */     
/* 227 */     String insertion = value.insertion();
/* 228 */     if (insertion != null) {
/* 229 */       out.name("insertion");
/* 230 */       out.value(insertion);
/*     */     } 
/*     */     
/* 233 */     ClickEvent clickEvent = value.clickEvent();
/* 234 */     if (clickEvent != null) {
/* 235 */       out.name("clickEvent");
/* 236 */       out.beginObject();
/* 237 */       out.name("action");
/* 238 */       this.gson.toJson(clickEvent.action(), SerializerFactory.CLICK_ACTION_TYPE, out);
/* 239 */       out.name("value");
/* 240 */       out.value(clickEvent.value());
/* 241 */       out.endObject();
/*     */     } 
/*     */     
/* 244 */     HoverEvent<?> hoverEvent = value.hoverEvent();
/* 245 */     if (hoverEvent != null) {
/* 246 */       out.name("hoverEvent");
/* 247 */       out.beginObject();
/* 248 */       out.name("action");
/* 249 */       HoverEvent.Action<?> action = hoverEvent.action();
/* 250 */       this.gson.toJson(action, SerializerFactory.HOVER_ACTION_TYPE, out);
/* 251 */       out.name("contents");
/* 252 */       if (action == HoverEvent.Action.SHOW_ITEM) {
/* 253 */         this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ITEM_TYPE, out);
/* 254 */       } else if (action == HoverEvent.Action.SHOW_ENTITY) {
/* 255 */         this.gson.toJson(hoverEvent.value(), SerializerFactory.SHOW_ENTITY_TYPE, out);
/* 256 */       } else if (action == HoverEvent.Action.SHOW_TEXT) {
/* 257 */         this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
/*     */       } else {
/* 259 */         throw new JsonParseException("Don't know how to serialize " + hoverEvent.value());
/*     */       } 
/* 261 */       if (this.emitLegacyHover) {
/* 262 */         out.name("value");
/* 263 */         serializeLegacyHoverEvent(hoverEvent, out);
/*     */       } 
/*     */       
/* 266 */       out.endObject();
/*     */     } 
/*     */     
/* 269 */     Key font = value.font();
/* 270 */     if (font != null) {
/* 271 */       out.name("font");
/* 272 */       this.gson.toJson(font, SerializerFactory.KEY_TYPE, out);
/*     */     } 
/*     */     
/* 275 */     out.endObject();
/*     */   }
/*     */   
/*     */   private void serializeLegacyHoverEvent(HoverEvent<?> hoverEvent, JsonWriter out) throws IOException {
/* 279 */     if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
/* 280 */       this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, out);
/* 281 */     } else if (this.legacyHover != null) {
/* 282 */       Component serialized = null;
/*     */       try {
/* 284 */         if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
/* 285 */           serialized = this.legacyHover.serializeShowEntity((HoverEvent.ShowEntity)hoverEvent.value(), (Codec.Encoder)encoder());
/* 286 */         } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
/* 287 */           serialized = this.legacyHover.serializeShowItem((HoverEvent.ShowItem)hoverEvent.value());
/*     */         } 
/* 289 */       } catch (IOException ex) {
/* 290 */         throw new JsonSyntaxException(ex);
/*     */       } 
/* 292 */       if (serialized != null) {
/* 293 */         this.gson.toJson(serialized, SerializerFactory.COMPONENT_TYPE, out);
/*     */       } else {
/* 295 */         out.nullValue();
/*     */       } 
/*     */     } else {
/* 298 */       out.nullValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\StyleSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */