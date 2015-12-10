# SparkUtils
##Spark Submit + Phoenix Table
`
git clone https://github.com/gbraccialli/SparkUtils
cd SparkUtils/
mvn clean package
spark-submit --class com.github.gbraccialli.spark.PhoenixSparkSample target/SparkUtils-1.0.0-SNAPSHOT.jar
`

##Spark Submit + Hive Commands
`
spark-submit \
  --class com.github.gbraccialli.spark.HiveCommand \
  --master yarn-cluster \
  --num-executors 1 \
  --driver-memory 1g \
  --executor-memory 1g \
  --executor-cores 1 \
  --files /usr/hdp/current/spark-client/conf/hive-site.xml \
  --jars /usr/hdp/current/spark-client/lib/datanucleus-api-jdo-3.2.6.jar,/usr/hdp/current/spark-client/lib/datanucleus-rdbms-3.2.9.jar,/usr/hdp/current/spark-client/lib/datanucleus-core-3.2.10.jar \
 target/SparkUtils-1.0.0-SNAPSHOT.jar "show tables" "select * from sample_08"
`
