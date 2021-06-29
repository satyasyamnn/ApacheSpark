import com.sf.fire.analysis.readers.FireDataReader;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;

public class Application {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("Fire Department");
        SparkContext context = new SparkContext(conf);
        SparkSession session = SparkSession.builder().sparkContext(context).getOrCreate();
        FireDataReader reader = new FireDataReader(session);
        reader.loadData();
    }
}
