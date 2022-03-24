package io.github.starter.punchline.spark.udf;

import org.apache.spark.sql.api.java.UDF1;

/**
 * This class is intended to be used in a Spark Sql Query statement as a function.
 * <br>
 * Expected return type: <b>DataTypes.createArrayType(DataTypes.StringType)</b>
 * <br><br>
 * <b>USAGE:</b>
 * <br>
 * <pre>
 * 	Take as input a <b>STRING</b> and return a Scala <b>{@literal WrappedArray<String>}</b> object
 * <br>
 * 	The input String should be in the following format: <b>{@literal [1.0, 2.0, 5, 6.0]}</b>
 * <br>
 * 	The result of the above input String should be:
 * 		<b>{@literal WrappedArray(1.0, 2.0, 5, 6.0)}</b> of type <b>String</b>
 * <br>
 * 	<b>Example in a query Statement:</b>
 * <br>
 *        {@literal SELECT your_registered_function_name('[1, 2, 3, 4.0]')}
 * </pre>
 *
 * @author jonathan yue chun
 */
public class StrToArrayString implements UDF1<String, String[]> {
    private static final long serialVersionUID = 1L;

    @Override
    public String[] call(String tuple) {
        String[] tupleValuesAsList = tuple.substring(1, tuple.length() - 1).split(",");
        String[] result = new String[tupleValuesAsList.length];
        for (int i = 0; i < tupleValuesAsList.length; i++) {
            result[i] = tupleValuesAsList[i].replace(" ", "");
        }
        return result;
    }
}
