package com.github.gbraccialli.spark;

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import java.sql.{Connection, DriverManager, DatabaseMetaData, ResultSet}
import org.apache.phoenix.spark._
import org.apache.spark.rdd.JdbcRDD

object PhoenixSparkSample{

  def main(args: Array[String]) {

   val sparkConf = new SparkConf()
   val sc = new SparkContext(sparkConf)
   val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)

   //option 1, read table
   val jdbcDF = sqlContext.read.format("jdbc").options( 
     Map(
     "driver" -> "org.apache.phoenix.jdbc.PhoenixDriver",
     "url" -> "jdbc:phoenix:sandbox.hortonworks.com:2181:/hbase-unsecure",
     "dbtable" -> "TABLE1")).load()
  
   jdbcDF.show
 
 
   //option 2, read custom query
   def getConn(driverClass: => String, connStr: => String, user: => String, pass: => String): Connection = {
     var conn:Connection = null
     try{
       Class.forName(driverClass)
        conn = DriverManager.getConnection(connStr, user, pass)
     }catch{ case e: Exception => e.printStackTrace }
     conn
   }
 
   val myRDD = new JdbcRDD( sc, () => getConn("org.apache.phoenix.jdbc.PhoenixDriver", "jdbc:phoenix:localhost:2181:/hbase-unsecure", "", "") ,
     "select sum(10) total from TABLE1 where ? <= id and id <= ?",
     1, 10, 2,
     r => r.getString("total")
   )
   
   myRDD.collect().foreach(line => println(line))
 

   //option 3, using phoenix-spark package
   val df = sqlContext.load(
     "org.apache.phoenix.spark",
     Map("table" -> "TABLE1", "zkUrl" -> "localhost:2181:/hbase-unsecure")
   )

   df.show

   sc.stop()
  }

}
