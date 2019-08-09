import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat

object AmazonRDDGenerator {
  val conf = new SparkConf()
    .setAppName("GuessTheProduct")
    .setMaster("local")
  val sc = new SparkContext(conf)

  def createRDDOfRecords(withPath: String) = {
    val conf = new Configuration()
    conf.set("textinputformat.record.delimiter", "\n\r\n")
    val dataset = sc.newAPIHadoopFile(withPath, classOf[TextInputFormat], classOf[LongWritable], classOf[Text], conf)

    dataset
      .map(x => x._2.toString)
      .filter(record => record.startsWith("Id"))
      .filter(record => record.contains("title"))
      .filter(record => record.contains("group"))
  }

  def createRDDOfAssociatedProducts(withPath: String) = {
    val conf = new Configuration()
    conf.set("textinputformat.record.delimiter", "\n")
    val dataset = sc.newAPIHadoopFile(withPath, classOf[TextInputFormat], classOf[LongWritable], classOf[Text], conf)

    val associationRegex = """\d+\t\d+""".r

    dataset
      .map(x => x._2.toString)
      .filter(association => associationRegex.findFirstIn(association).isDefined)
  }
}
