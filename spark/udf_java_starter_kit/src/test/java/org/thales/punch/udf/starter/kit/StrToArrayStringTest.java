package org.thales.punch.udf.starter.kit;

import static org.junit.Assert.assertEquals;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

public class StrToArrayStringTest {
	
	@org.junit.Test
	public void StrToArrayString(){
		System.out.println("Testing UDF: String to Array of String");
		
		SparkConf sparkConf = new SparkConf();
		sparkConf
				.setAppName("Test")
				.setMaster("local[*]");
		
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
		SparkContext sc = sparkContext.sc().getOrCreate();
		SparkSession ss = SparkSession
				.builder()
				.getOrCreate();
		ss
			.sqlContext()
			.udf()
			.registerJava(
					"myFunc", 
					"org.thales.punch.udf.starter.kit.StrToArrayString", 
					DataTypes.createArrayType(DataTypes.StringType)
				);
		
		System.out.println("Before applying our function:");
		
		Dataset<Row> before = ss
				.sql("SELECT '[1, 2.0, 3, 5]'");
		before.show();
		before.printSchema();
		
		System.out.println("After applying our function:");
		
		Dataset<Row> after = ss
				.sql("SELECT myFunc('[1, 2.0, 3, 5]')");
		after.show();
		after.printSchema();

		assertEquals(after.schema().toDDL().toString(), "`UDF:myFunc([1, 2.0, 3, 5])` ARRAY<STRING>");
		
		sc.stop();
		
		System.out.println("End UDF TEST: String to Array of String");
	}

}
