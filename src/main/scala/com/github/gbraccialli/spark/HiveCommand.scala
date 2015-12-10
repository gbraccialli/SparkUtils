package com.github.gbraccialli.spark;

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import java.sql.{Connection, DriverManager, DatabaseMetaData, ResultSet}
import org.apache.phoenix.spark._

object HiveCommand{

  def main(args: Array[String]) {

   val sparkConf = new SparkConf()
   val sc = new SparkContext(sparkConf)
   val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)

   for (arg <- args) sqlContext.sql(arg).collect().foreach(println) 

   sc.stop()
  }

}
