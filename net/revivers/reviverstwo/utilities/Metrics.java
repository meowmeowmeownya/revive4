/*     */ package net.revivers.reviverstwo.utilities;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Metrics
/*     */ {
/*     */   private final Plugin plugin;
/*     */   private final MetricsBase metricsBase;
/*     */   
/*     */   public Metrics(JavaPlugin plugin, int serviceId) {
/*  50 */     this.plugin = (Plugin)plugin;
/*     */     
/*  52 */     File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
/*  53 */     File configFile = new File(bStatsFolder, "config.yml");
/*  54 */     YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
/*  55 */     if (!config.isSet("serverUuid")) {
/*  56 */       config.addDefault("enabled", Boolean.valueOf(true));
/*  57 */       config.addDefault("serverUuid", UUID.randomUUID().toString());
/*  58 */       config.addDefault("logFailedRequests", Boolean.valueOf(false));
/*  59 */       config.addDefault("logSentData", Boolean.valueOf(false));
/*  60 */       config.addDefault("logResponseStatusText", Boolean.valueOf(false));
/*     */       
/*  62 */       config
/*  63 */         .options()
/*  64 */         .header("bStats (https://bStats.org) collects some basic information for plugin authors, like how\nmany people use their plugin and their total player count. It's recommended to keep bStats\nenabled, but if you're not comfortable with this, you can turn this setting off. There is no\nperformance penalty associated with having metrics enabled, and data sent to bStats is fully\nanonymous.")
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  70 */         .copyDefaults(true);
/*     */       try {
/*  72 */         config.save(configFile);
/*  73 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/*  77 */     boolean enabled = config.getBoolean("enabled", true);
/*  78 */     String serverUUID = config.getString("serverUuid");
/*  79 */     boolean logErrors = config.getBoolean("logFailedRequests", false);
/*  80 */     boolean logSentData = config.getBoolean("logSentData", false);
/*  81 */     boolean logResponseStatusText = config.getBoolean("logResponseStatusText", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     Objects.requireNonNull(plugin); this.metricsBase = new MetricsBase("bukkit", serverUUID, serviceId, enabled, this::appendPlatformData, this::appendServiceData, submitDataTask -> Bukkit.getScheduler().runTask((Plugin)plugin, submitDataTask), plugin::isEnabled, (message, error) -> this.plugin.getLogger().log(Level.WARNING, message, error), message -> this.plugin.getLogger().log(Level.INFO, message), logErrors, logSentData, logResponseStatusText);
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
/*     */   public void addCustomChart(CustomChart chart) {
/* 105 */     this.metricsBase.addCustomChart(chart);
/*     */   }
/*     */   
/*     */   private void appendPlatformData(JsonObjectBuilder builder) {
/* 109 */     builder.appendField("playerAmount", getPlayerAmount());
/* 110 */     builder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
/* 111 */     builder.appendField("bukkitVersion", Bukkit.getVersion());
/* 112 */     builder.appendField("bukkitName", Bukkit.getName());
/* 113 */     builder.appendField("javaVersion", System.getProperty("java.version"));
/* 114 */     builder.appendField("osName", System.getProperty("os.name"));
/* 115 */     builder.appendField("osArch", System.getProperty("os.arch"));
/* 116 */     builder.appendField("osVersion", System.getProperty("os.version"));
/* 117 */     builder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
/*     */   }
/*     */   
/*     */   private void appendServiceData(JsonObjectBuilder builder) {
/* 121 */     builder.appendField("pluginVersion", this.plugin.getDescription().getVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPlayerAmount() {
/*     */     try {
/* 129 */       Method onlinePlayersMethod = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers", new Class[0]);
/* 130 */       return onlinePlayersMethod.getReturnType().equals(Collection.class) ? (
/* 131 */         (Collection)onlinePlayersMethod.invoke(Bukkit.getServer(), new Object[0])).size() : (
/* 132 */         (Player[])onlinePlayersMethod.invoke(Bukkit.getServer(), new Object[0])).length;
/* 133 */     } catch (Exception e) {
/*     */       
/* 135 */       return Bukkit.getOnlinePlayers().size();
/*     */     } 
/*     */   }
/*     */   public static class MetricsBase { public static final String METRICS_VERSION = "2.2.1"; private static final ScheduledExecutorService scheduler; private static final String REPORT_URL = "https://bStats.org/api/v2/data/%s";
/*     */     private final String platform;
/*     */     private final String serverUuid;
/*     */     private final int serviceId;
/*     */     private final Consumer<Metrics.JsonObjectBuilder> appendPlatformDataConsumer;
/*     */     
/*     */     static {
/* 145 */       scheduler = Executors.newScheduledThreadPool(1, task -> new Thread(task, "bStats-Metrics"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final Consumer<Metrics.JsonObjectBuilder> appendServiceDataConsumer;
/*     */ 
/*     */     
/*     */     private final Consumer<Runnable> submitTaskConsumer;
/*     */ 
/*     */     
/*     */     private final Supplier<Boolean> checkServiceEnabledSupplier;
/*     */ 
/*     */     
/*     */     private final BiConsumer<String, Throwable> errorLogger;
/*     */ 
/*     */     
/*     */     private final Consumer<String> infoLogger;
/*     */ 
/*     */     
/*     */     private final boolean logErrors;
/*     */ 
/*     */     
/*     */     private final boolean logSentData;
/*     */ 
/*     */     
/*     */     private final boolean logResponseStatusText;
/*     */     
/* 173 */     private final Set<Metrics.CustomChart> customCharts = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean enabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MetricsBase(String platform, String serverUuid, int serviceId, boolean enabled, Consumer<Metrics.JsonObjectBuilder> appendPlatformDataConsumer, Consumer<Metrics.JsonObjectBuilder> appendServiceDataConsumer, Consumer<Runnable> submitTaskConsumer, Supplier<Boolean> checkServiceEnabledSupplier, BiConsumer<String, Throwable> errorLogger, Consumer<String> infoLogger, boolean logErrors, boolean logSentData, boolean logResponseStatusText) {
/* 212 */       this.platform = platform;
/* 213 */       this.serverUuid = serverUuid;
/* 214 */       this.serviceId = serviceId;
/* 215 */       this.enabled = enabled;
/* 216 */       this.appendPlatformDataConsumer = appendPlatformDataConsumer;
/* 217 */       this.appendServiceDataConsumer = appendServiceDataConsumer;
/* 218 */       this.submitTaskConsumer = submitTaskConsumer;
/* 219 */       this.checkServiceEnabledSupplier = checkServiceEnabledSupplier;
/* 220 */       this.errorLogger = errorLogger;
/* 221 */       this.infoLogger = infoLogger;
/* 222 */       this.logErrors = logErrors;
/* 223 */       this.logSentData = logSentData;
/* 224 */       this.logResponseStatusText = logResponseStatusText;
/* 225 */       checkRelocation();
/* 226 */       if (enabled) {
/* 227 */         startSubmitting();
/*     */       }
/*     */     }
/*     */     
/*     */     public void addCustomChart(Metrics.CustomChart chart) {
/* 232 */       this.customCharts.add(chart);
/*     */     }
/*     */     
/*     */     private void startSubmitting() {
/* 236 */       Runnable submitTask = () -> {
/*     */           if (!this.enabled || !((Boolean)this.checkServiceEnabledSupplier.get()).booleanValue()) {
/*     */             scheduler.shutdown();
/*     */ 
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           if (this.submitTaskConsumer != null) {
/*     */             this.submitTaskConsumer.accept(this::submitData);
/*     */           } else {
/*     */             submitData();
/*     */           } 
/*     */         };
/*     */ 
/*     */ 
/*     */       
/* 256 */       long initialDelay = (long)(60000.0D * (3.0D + Math.random() * 3.0D));
/* 257 */       long secondDelay = (long)(60000.0D * Math.random() * 30.0D);
/* 258 */       scheduler.schedule(submitTask, initialDelay, TimeUnit.MILLISECONDS);
/* 259 */       scheduler.scheduleAtFixedRate(submitTask, initialDelay + secondDelay, 1800000L, TimeUnit.MILLISECONDS);
/*     */     }
/*     */ 
/*     */     
/*     */     private void submitData() {
/* 264 */       Metrics.JsonObjectBuilder baseJsonBuilder = new Metrics.JsonObjectBuilder();
/* 265 */       this.appendPlatformDataConsumer.accept(baseJsonBuilder);
/* 266 */       Metrics.JsonObjectBuilder serviceJsonBuilder = new Metrics.JsonObjectBuilder();
/* 267 */       this.appendServiceDataConsumer.accept(serviceJsonBuilder);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 272 */       Metrics.JsonObjectBuilder.JsonObject[] chartData = (Metrics.JsonObjectBuilder.JsonObject[])this.customCharts.stream().map(customChart -> customChart.getRequestJsonObject(this.errorLogger, this.logErrors)).filter(Objects::nonNull).toArray(x$0 -> new Metrics.JsonObjectBuilder.JsonObject[x$0]);
/* 273 */       serviceJsonBuilder.appendField("id", this.serviceId);
/* 274 */       serviceJsonBuilder.appendField("customCharts", chartData);
/* 275 */       baseJsonBuilder.appendField("service", serviceJsonBuilder.build());
/* 276 */       baseJsonBuilder.appendField("serverUUID", this.serverUuid);
/* 277 */       baseJsonBuilder.appendField("metricsVersion", "2.2.1");
/* 278 */       Metrics.JsonObjectBuilder.JsonObject data = baseJsonBuilder.build();
/* 279 */       scheduler.execute(() -> {
/*     */ 
/*     */             
/*     */             try {
/*     */               sendData(data);
/* 284 */             } catch (Exception e) {
/*     */               if (this.logErrors) {
/*     */                 this.errorLogger.accept("Could not submit bStats metrics data", e);
/*     */               }
/*     */             } 
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     private void sendData(Metrics.JsonObjectBuilder.JsonObject data) throws Exception {
/* 294 */       if (this.logSentData) {
/* 295 */         this.infoLogger.accept("Sent bStats metrics data: " + data.toString());
/*     */       }
/* 297 */       String url = String.format("https://bStats.org/api/v2/data/%s", new Object[] { this.platform });
/* 298 */       HttpsURLConnection connection = (HttpsURLConnection)(new URL(url)).openConnection();
/*     */       
/* 300 */       byte[] compressedData = compress(data.toString());
/* 301 */       connection.setRequestMethod("POST");
/* 302 */       connection.addRequestProperty("Accept", "application/json");
/* 303 */       connection.addRequestProperty("Connection", "close");
/* 304 */       connection.addRequestProperty("Content-Encoding", "gzip");
/* 305 */       connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
/* 306 */       connection.setRequestProperty("Content-Type", "application/json");
/* 307 */       connection.setRequestProperty("User-Agent", "Metrics-Service/1");
/* 308 */       connection.setDoOutput(true);
/* 309 */       DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream()); 
/* 310 */       try { outputStream.write(compressedData);
/* 311 */         outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 312 */        StringBuilder builder = new StringBuilder();
/*     */       
/* 314 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
/*     */       try { String line;
/* 316 */         while ((line = bufferedReader.readLine()) != null) {
/* 317 */           builder.append(line);
/*     */         }
/* 319 */         bufferedReader.close(); } catch (Throwable throwable) { try { bufferedReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 320 */        if (this.logResponseStatusText) {
/* 321 */         this.infoLogger.accept("Sent data to bStats and received response: " + builder);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void checkRelocation() {
/* 328 */       if (System.getProperty("bstats.relocatecheck") == null || 
/* 329 */         !System.getProperty("bstats.relocatecheck").equals("false")) {
/*     */ 
/*     */         
/* 332 */         String defaultPackage = new String(new byte[] { 111, 114, 103, 46, 98, 115, 116, 97, 116, 115 });
/*     */         
/* 334 */         String examplePackage = new String(new byte[] { 121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101 });
/*     */ 
/*     */ 
/*     */         
/* 338 */         if (MetricsBase.class.getPackage().getName().startsWith(defaultPackage) || MetricsBase.class
/* 339 */           .getPackage().getName().startsWith(examplePackage)) {
/* 340 */           throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static byte[] compress(String str) throws IOException {
/* 352 */       if (str == null) {
/* 353 */         return null;
/*     */       }
/* 355 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 356 */       GZIPOutputStream gzip = new GZIPOutputStream(outputStream); 
/* 357 */       try { gzip.write(str.getBytes(StandardCharsets.UTF_8));
/* 358 */         gzip.close(); } catch (Throwable throwable) { try { gzip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 359 */        return outputStream.toByteArray();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AdvancedBarChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, int[]>> callable;
/*     */ 
/*     */ 
/*     */     
/*     */     public AdvancedBarChart(String chartId, Callable<Map<String, int[]>> callable) {
/* 374 */       super(chartId);
/* 375 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 380 */       Metrics.JsonObjectBuilder valuesBuilder = new Metrics.JsonObjectBuilder();
/* 381 */       Map<String, int[]> map = this.callable.call();
/* 382 */       if (map == null || map.isEmpty())
/*     */       {
/* 384 */         return null;
/*     */       }
/* 386 */       boolean allSkipped = true;
/* 387 */       for (Map.Entry<String, int[]> entry : map.entrySet()) {
/* 388 */         if (((int[])entry.getValue()).length == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 392 */         allSkipped = false;
/* 393 */         valuesBuilder.appendField(entry.getKey(), entry.getValue());
/*     */       } 
/* 395 */       if (allSkipped)
/*     */       {
/* 397 */         return null;
/*     */       }
/* 399 */       return (new Metrics.JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleBarChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable) {
/* 414 */       super(chartId);
/* 415 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 420 */       Metrics.JsonObjectBuilder valuesBuilder = new Metrics.JsonObjectBuilder();
/* 421 */       Map<String, Integer> map = this.callable.call();
/* 422 */       if (map == null || map.isEmpty())
/*     */       {
/* 424 */         return null;
/*     */       }
/* 426 */       for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 427 */         valuesBuilder.appendField(entry.getKey(), new int[] { ((Integer)entry.getValue()).intValue() });
/*     */       } 
/* 429 */       return (new Metrics.JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MultiLineChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */     
/*     */     public MultiLineChart(String chartId, Callable<Map<String, Integer>> callable) {
/* 444 */       super(chartId);
/* 445 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 450 */       Metrics.JsonObjectBuilder valuesBuilder = new Metrics.JsonObjectBuilder();
/* 451 */       Map<String, Integer> map = this.callable.call();
/* 452 */       if (map == null || map.isEmpty())
/*     */       {
/* 454 */         return null;
/*     */       }
/* 456 */       boolean allSkipped = true;
/* 457 */       for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 458 */         if (((Integer)entry.getValue()).intValue() == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 462 */         allSkipped = false;
/* 463 */         valuesBuilder.appendField(entry.getKey(), ((Integer)entry.getValue()).intValue());
/*     */       } 
/* 465 */       if (allSkipped)
/*     */       {
/* 467 */         return null;
/*     */       }
/* 469 */       return (new Metrics.JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AdvancedPie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */     
/*     */     public AdvancedPie(String chartId, Callable<Map<String, Integer>> callable) {
/* 484 */       super(chartId);
/* 485 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 490 */       Metrics.JsonObjectBuilder valuesBuilder = new Metrics.JsonObjectBuilder();
/* 491 */       Map<String, Integer> map = this.callable.call();
/* 492 */       if (map == null || map.isEmpty())
/*     */       {
/* 494 */         return null;
/*     */       }
/* 496 */       boolean allSkipped = true;
/* 497 */       for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 498 */         if (((Integer)entry.getValue()).intValue() == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 502 */         allSkipped = false;
/* 503 */         valuesBuilder.appendField(entry.getKey(), ((Integer)entry.getValue()).intValue());
/*     */       } 
/* 505 */       if (allSkipped)
/*     */       {
/* 507 */         return null;
/*     */       }
/* 509 */       return (new Metrics.JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class CustomChart
/*     */   {
/*     */     private final String chartId;
/*     */     
/*     */     protected CustomChart(String chartId) {
/* 518 */       if (chartId == null) {
/* 519 */         throw new IllegalArgumentException("chartId must not be null");
/*     */       }
/* 521 */       this.chartId = chartId;
/*     */     }
/*     */ 
/*     */     
/*     */     public Metrics.JsonObjectBuilder.JsonObject getRequestJsonObject(BiConsumer<String, Throwable> errorLogger, boolean logErrors) {
/* 526 */       Metrics.JsonObjectBuilder builder = new Metrics.JsonObjectBuilder();
/* 527 */       builder.appendField("chartId", this.chartId);
/*     */       try {
/* 529 */         Metrics.JsonObjectBuilder.JsonObject data = getChartData();
/* 530 */         if (data == null)
/*     */         {
/* 532 */           return null;
/*     */         }
/* 534 */         builder.appendField("data", data);
/* 535 */       } catch (Throwable t) {
/* 536 */         if (logErrors) {
/* 537 */           errorLogger.accept("Failed to get data for custom chart with id " + this.chartId, t);
/*     */         }
/* 539 */         return null;
/*     */       } 
/* 541 */       return builder.build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SingleLineChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Integer> callable;
/*     */ 
/*     */     
/*     */     public SingleLineChart(String chartId, Callable<Integer> callable) {
/* 558 */       super(chartId);
/* 559 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 564 */       int value = ((Integer)this.callable.call()).intValue();
/* 565 */       if (value == 0)
/*     */       {
/* 567 */         return null;
/*     */       }
/* 569 */       return (new Metrics.JsonObjectBuilder()).appendField("value", value).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimplePie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<String> callable;
/*     */ 
/*     */ 
/*     */     
/*     */     public SimplePie(String chartId, Callable<String> callable) {
/* 584 */       super(chartId);
/* 585 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 590 */       String value = this.callable.call();
/* 591 */       if (value == null || value.isEmpty())
/*     */       {
/* 593 */         return null;
/*     */       }
/* 595 */       return (new Metrics.JsonObjectBuilder()).appendField("value", value).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DrilldownPie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Map<String, Integer>>> callable;
/*     */ 
/*     */ 
/*     */     
/*     */     public DrilldownPie(String chartId, Callable<Map<String, Map<String, Integer>>> callable) {
/* 610 */       super(chartId);
/* 611 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     public Metrics.JsonObjectBuilder.JsonObject getChartData() throws Exception {
/* 616 */       Metrics.JsonObjectBuilder valuesBuilder = new Metrics.JsonObjectBuilder();
/* 617 */       Map<String, Map<String, Integer>> map = this.callable.call();
/* 618 */       if (map == null || map.isEmpty())
/*     */       {
/* 620 */         return null;
/*     */       }
/* 622 */       boolean reallyAllSkipped = true;
/* 623 */       for (Map.Entry<String, Map<String, Integer>> entryValues : map.entrySet()) {
/* 624 */         Metrics.JsonObjectBuilder valueBuilder = new Metrics.JsonObjectBuilder();
/* 625 */         boolean allSkipped = true;
/* 626 */         for (Map.Entry<String, Integer> valueEntry : (Iterable<Map.Entry<String, Integer>>)((Map)map.get(entryValues.getKey())).entrySet()) {
/* 627 */           valueBuilder.appendField(valueEntry.getKey(), ((Integer)valueEntry.getValue()).intValue());
/* 628 */           allSkipped = false;
/*     */         } 
/* 630 */         if (!allSkipped) {
/* 631 */           reallyAllSkipped = false;
/* 632 */           valuesBuilder.appendField(entryValues.getKey(), valueBuilder.build());
/*     */         } 
/*     */       } 
/* 635 */       if (reallyAllSkipped)
/*     */       {
/* 637 */         return null;
/*     */       }
/* 639 */       return (new Metrics.JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class JsonObjectBuilder
/*     */   {
/* 651 */     private StringBuilder builder = new StringBuilder();
/*     */     
/*     */     private boolean hasAtLeastOneField = false;
/*     */     
/*     */     public JsonObjectBuilder() {
/* 656 */       this.builder.append("{");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendNull(String key) {
/* 666 */       appendFieldUnescaped(key, "null");
/* 667 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendField(String key, String value) {
/* 678 */       if (value == null) {
/* 679 */         throw new IllegalArgumentException("JSON value must not be null");
/*     */       }
/* 681 */       appendFieldUnescaped(key, "\"" + escape(value) + "\"");
/* 682 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendField(String key, int value) {
/* 693 */       appendFieldUnescaped(key, String.valueOf(value));
/* 694 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendField(String key, JsonObject object) {
/* 705 */       if (object == null) {
/* 706 */         throw new IllegalArgumentException("JSON object must not be null");
/*     */       }
/* 708 */       appendFieldUnescaped(key, object.toString());
/* 709 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendField(String key, String[] values) {
/* 720 */       if (values == null) {
/* 721 */         throw new IllegalArgumentException("JSON values must not be null");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 726 */       String escapedValues = Arrays.<String>stream(values).map(value -> "\"" + escape(value) + "\"").collect(Collectors.joining(","));
/* 727 */       appendFieldUnescaped(key, "[" + escapedValues + "]");
/* 728 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendField(String key, int[] values) {
/* 739 */       if (values == null) {
/* 740 */         throw new IllegalArgumentException("JSON values must not be null");
/*     */       }
/*     */       
/* 743 */       String escapedValues = Arrays.stream(values).<CharSequence>mapToObj(String::valueOf).collect(Collectors.joining(","));
/* 744 */       appendFieldUnescaped(key, "[" + escapedValues + "]");
/* 745 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObjectBuilder appendField(String key, JsonObject[] values) {
/* 756 */       if (values == null) {
/* 757 */         throw new IllegalArgumentException("JSON values must not be null");
/*     */       }
/*     */       
/* 760 */       String escapedValues = Arrays.<JsonObject>stream(values).map(JsonObject::toString).collect(Collectors.joining(","));
/* 761 */       appendFieldUnescaped(key, "[" + escapedValues + "]");
/* 762 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void appendFieldUnescaped(String key, String escapedValue) {
/* 772 */       if (this.builder == null) {
/* 773 */         throw new IllegalStateException("JSON has already been built");
/*     */       }
/* 775 */       if (key == null) {
/* 776 */         throw new IllegalArgumentException("JSON key must not be null");
/*     */       }
/* 778 */       if (this.hasAtLeastOneField) {
/* 779 */         this.builder.append(",");
/*     */       }
/* 781 */       this.builder.append("\"").append(escape(key)).append("\":").append(escapedValue);
/* 782 */       this.hasAtLeastOneField = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObject build() {
/* 791 */       if (this.builder == null) {
/* 792 */         throw new IllegalStateException("JSON has already been built");
/*     */       }
/* 794 */       JsonObject object = new JsonObject(this.builder.append("}").toString());
/* 795 */       this.builder = null;
/* 796 */       return object;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String escape(String value) {
/* 809 */       StringBuilder builder = new StringBuilder();
/* 810 */       for (int i = 0; i < value.length(); i++) {
/* 811 */         char c = value.charAt(i);
/* 812 */         if (c == '"') {
/* 813 */           builder.append("\\\"");
/* 814 */         } else if (c == '\\') {
/* 815 */           builder.append("\\\\");
/* 816 */         } else if (c <= '\017') {
/* 817 */           builder.append("\\u000").append(Integer.toHexString(c));
/* 818 */         } else if (c <= '\037') {
/* 819 */           builder.append("\\u00").append(Integer.toHexString(c));
/*     */         } else {
/* 821 */           builder.append(c);
/*     */         } 
/*     */       } 
/* 824 */       return builder.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static class JsonObject
/*     */     {
/*     */       private final String value;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private JsonObject(String value) {
/* 839 */         this.value = value;
/*     */       }
/*     */       
/*     */       public String toString()
/*     */       {
/* 844 */         return this.value; } } } public static class JsonObject { public String toString() { return this.value; }
/*     */ 
/*     */     
/*     */     private final String value;
/*     */     
/*     */     private JsonObject(String value) {
/*     */       this.value = value;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\cat\Desktop\ReviversTwo-PUBLIC.jar!\net\revivers\reviverstw\\utilities\Metrics.class
 * Java compiler version: 16 (60.0)
 * JD-Core Version:       1.1.3
 */