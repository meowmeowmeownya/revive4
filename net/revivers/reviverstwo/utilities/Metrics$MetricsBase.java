/*     */ package net.revivers.reviverstwo.utilities;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetricsBase
/*     */ {
/*     */   public static final String METRICS_VERSION = "2.2.1";
/*     */   private static final ScheduledExecutorService scheduler;
/*     */   private static final String REPORT_URL = "https://bStats.org/api/v2/data/%s";
/*     */   private final String platform;
/*     */   private final String serverUuid;
/*     */   private final int serviceId;
/*     */   private final Consumer<Metrics.JsonObjectBuilder> appendPlatformDataConsumer;
/*     */   private final Consumer<Metrics.JsonObjectBuilder> appendServiceDataConsumer;
/*     */   private final Consumer<Runnable> submitTaskConsumer;
/*     */   private final Supplier<Boolean> checkServiceEnabledSupplier;
/*     */   private final BiConsumer<String, Throwable> errorLogger;
/*     */   private final Consumer<String> infoLogger;
/*     */   private final boolean logErrors;
/*     */   private final boolean logSentData;
/*     */   private final boolean logResponseStatusText;
/*     */   
/*     */   static {
/* 145 */     scheduler = Executors.newScheduledThreadPool(1, task -> new Thread(task, "bStats-Metrics"));
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
/* 173 */   private final Set<Metrics.CustomChart> customCharts = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean enabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetricsBase(String platform, String serverUuid, int serviceId, boolean enabled, Consumer<Metrics.JsonObjectBuilder> appendPlatformDataConsumer, Consumer<Metrics.JsonObjectBuilder> appendServiceDataConsumer, Consumer<Runnable> submitTaskConsumer, Supplier<Boolean> checkServiceEnabledSupplier, BiConsumer<String, Throwable> errorLogger, Consumer<String> infoLogger, boolean logErrors, boolean logSentData, boolean logResponseStatusText) {
/* 212 */     this.platform = platform;
/* 213 */     this.serverUuid = serverUuid;
/* 214 */     this.serviceId = serviceId;
/* 215 */     this.enabled = enabled;
/* 216 */     this.appendPlatformDataConsumer = appendPlatformDataConsumer;
/* 217 */     this.appendServiceDataConsumer = appendServiceDataConsumer;
/* 218 */     this.submitTaskConsumer = submitTaskConsumer;
/* 219 */     this.checkServiceEnabledSupplier = checkServiceEnabledSupplier;
/* 220 */     this.errorLogger = errorLogger;
/* 221 */     this.infoLogger = infoLogger;
/* 222 */     this.logErrors = logErrors;
/* 223 */     this.logSentData = logSentData;
/* 224 */     this.logResponseStatusText = logResponseStatusText;
/* 225 */     checkRelocation();
/* 226 */     if (enabled) {
/* 227 */       startSubmitting();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addCustomChart(Metrics.CustomChart chart) {
/* 232 */     this.customCharts.add(chart);
/*     */   }
/*     */   
/*     */   private void startSubmitting() {
/* 236 */     Runnable submitTask = () -> {
/*     */         if (!this.enabled || !((Boolean)this.checkServiceEnabledSupplier.get()).booleanValue()) {
/*     */           scheduler.shutdown();
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         if (this.submitTaskConsumer != null) {
/*     */           this.submitTaskConsumer.accept(this::submitData);
/*     */         } else {
/*     */           submitData();
/*     */         } 
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 256 */     long initialDelay = (long)(60000.0D * (3.0D + Math.random() * 3.0D));
/* 257 */     long secondDelay = (long)(60000.0D * Math.random() * 30.0D);
/* 258 */     scheduler.schedule(submitTask, initialDelay, TimeUnit.MILLISECONDS);
/* 259 */     scheduler.scheduleAtFixedRate(submitTask, initialDelay + secondDelay, 1800000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   private void submitData() {
/* 264 */     Metrics.JsonObjectBuilder baseJsonBuilder = new Metrics.JsonObjectBuilder();
/* 265 */     this.appendPlatformDataConsumer.accept(baseJsonBuilder);
/* 266 */     Metrics.JsonObjectBuilder serviceJsonBuilder = new Metrics.JsonObjectBuilder();
/* 267 */     this.appendServiceDataConsumer.accept(serviceJsonBuilder);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     Metrics.JsonObjectBuilder.JsonObject[] chartData = (Metrics.JsonObjectBuilder.JsonObject[])this.customCharts.stream().map(customChart -> customChart.getRequestJsonObject(this.errorLogger, this.logErrors)).filter(Objects::nonNull).toArray(x$0 -> new Metrics.JsonObjectBuilder.JsonObject[x$0]);
/* 273 */     serviceJsonBuilder.appendField("id", this.serviceId);
/* 274 */     serviceJsonBuilder.appendField("customCharts", chartData);
/* 275 */     baseJsonBuilder.appendField("service", serviceJsonBuilder.build());
/* 276 */     baseJsonBuilder.appendField("serverUUID", this.serverUuid);
/* 277 */     baseJsonBuilder.appendField("metricsVersion", "2.2.1");
/* 278 */     Metrics.JsonObjectBuilder.JsonObject data = baseJsonBuilder.build();
/* 279 */     scheduler.execute(() -> {
/*     */ 
/*     */           
/*     */           try {
/*     */             sendData(data);
/* 284 */           } catch (Exception e) {
/*     */             if (this.logErrors) {
/*     */               this.errorLogger.accept("Could not submit bStats metrics data", e);
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendData(Metrics.JsonObjectBuilder.JsonObject data) throws Exception {
/* 294 */     if (this.logSentData) {
/* 295 */       this.infoLogger.accept("Sent bStats metrics data: " + data.toString());
/*     */     }
/* 297 */     String url = String.format("https://bStats.org/api/v2/data/%s", new Object[] { this.platform });
/* 298 */     HttpsURLConnection connection = (HttpsURLConnection)(new URL(url)).openConnection();
/*     */     
/* 300 */     byte[] compressedData = compress(data.toString());
/* 301 */     connection.setRequestMethod("POST");
/* 302 */     connection.addRequestProperty("Accept", "application/json");
/* 303 */     connection.addRequestProperty("Connection", "close");
/* 304 */     connection.addRequestProperty("Content-Encoding", "gzip");
/* 305 */     connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
/* 306 */     connection.setRequestProperty("Content-Type", "application/json");
/* 307 */     connection.setRequestProperty("User-Agent", "Metrics-Service/1");
/* 308 */     connection.setDoOutput(true);
/* 309 */     DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream()); 
/* 310 */     try { outputStream.write(compressedData);
/* 311 */       outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 312 */      StringBuilder builder = new StringBuilder();
/*     */     
/* 314 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
/*     */     try { String line;
/* 316 */       while ((line = bufferedReader.readLine()) != null) {
/* 317 */         builder.append(line);
/*     */       }
/* 319 */       bufferedReader.close(); } catch (Throwable throwable) { try { bufferedReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 320 */      if (this.logResponseStatusText) {
/* 321 */       this.infoLogger.accept("Sent data to bStats and received response: " + builder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkRelocation() {
/* 328 */     if (System.getProperty("bstats.relocatecheck") == null || 
/* 329 */       !System.getProperty("bstats.relocatecheck").equals("false")) {
/*     */ 
/*     */       
/* 332 */       String defaultPackage = new String(new byte[] { 111, 114, 103, 46, 98, 115, 116, 97, 116, 115 });
/*     */       
/* 334 */       String examplePackage = new String(new byte[] { 121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101 });
/*     */ 
/*     */ 
/*     */       
/* 338 */       if (MetricsBase.class.getPackage().getName().startsWith(defaultPackage) || MetricsBase.class
/* 339 */         .getPackage().getName().startsWith(examplePackage)) {
/* 340 */         throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] compress(String str) throws IOException {
/* 352 */     if (str == null) {
/* 353 */       return null;
/*     */     }
/* 355 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 356 */     GZIPOutputStream gzip = new GZIPOutputStream(outputStream); 
/* 357 */     try { gzip.write(str.getBytes(StandardCharsets.UTF_8));
/* 358 */       gzip.close(); } catch (Throwable throwable) { try { gzip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 359 */      return outputStream.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\Metrics$MetricsBase.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */