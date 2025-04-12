/*     */ package net.kyori.adventure.text.serializer.gson;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import net.kyori.adventure.key.Key;
/*     */ import net.kyori.adventure.nbt.api.BinaryTagHolder;
/*     */ import net.kyori.adventure.text.event.HoverEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ShowItemSerializer
/*     */   extends TypeAdapter<HoverEvent.ShowItem>
/*     */ {
/*     */   static final String ID = "id";
/*     */   static final String COUNT = "count";
/*     */   static final String TAG = "tag";
/*     */   private final Gson gson;
/*     */   
/*     */   static TypeAdapter<HoverEvent.ShowItem> create(Gson gson) {
/*  44 */     return (new ShowItemSerializer(gson)).nullSafe();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ShowItemSerializer(Gson gson) {
/*  50 */     this.gson = gson;
/*     */   }
/*     */ 
/*     */   
/*     */   public HoverEvent.ShowItem read(JsonReader in) throws IOException {
/*  55 */     in.beginObject();
/*     */     
/*  57 */     Key key = null;
/*  58 */     int count = 1;
/*  59 */     BinaryTagHolder nbt = null;
/*     */     
/*  61 */     while (in.hasNext()) {
/*  62 */       String fieldName = in.nextName();
/*  63 */       if (fieldName.equals("id")) {
/*  64 */         key = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE); continue;
/*  65 */       }  if (fieldName.equals("count")) {
/*  66 */         count = in.nextInt(); continue;
/*  67 */       }  if (fieldName.equals("tag")) {
/*  68 */         JsonToken token = in.peek();
/*  69 */         if (token == JsonToken.STRING || token == JsonToken.NUMBER) {
/*  70 */           nbt = BinaryTagHolder.of(in.nextString()); continue;
/*  71 */         }  if (token == JsonToken.BOOLEAN) {
/*  72 */           nbt = BinaryTagHolder.of(String.valueOf(in.nextBoolean())); continue;
/*  73 */         }  if (token == JsonToken.NULL) {
/*  74 */           in.nextNull(); continue;
/*     */         } 
/*  76 */         throw new JsonParseException("Expected tag to be a string");
/*     */       } 
/*     */       
/*  79 */       in.skipValue();
/*     */     } 
/*     */ 
/*     */     
/*  83 */     if (key == null) {
/*  84 */       throw new JsonParseException("Not sure how to deserialize show_item hover event");
/*     */     }
/*  86 */     in.endObject();
/*     */     
/*  88 */     return HoverEvent.ShowItem.of(key, count, nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, HoverEvent.ShowItem value) throws IOException {
/*  93 */     out.beginObject();
/*     */     
/*  95 */     out.name("id");
/*  96 */     this.gson.toJson(value.item(), SerializerFactory.KEY_TYPE, out);
/*     */     
/*  98 */     int count = value.count();
/*  99 */     if (count != 1) {
/* 100 */       out.name("count");
/* 101 */       out.value(count);
/*     */     } 
/*     */     
/* 104 */     BinaryTagHolder nbt = value.nbt();
/* 105 */     if (nbt != null) {
/* 106 */       out.name("tag");
/* 107 */       out.value(nbt.string());
/*     */     } 
/*     */     
/* 110 */     out.endObject();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\kyori\adventure\text\serializer\gson\ShowItemSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */