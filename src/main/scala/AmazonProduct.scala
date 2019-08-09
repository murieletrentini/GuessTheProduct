class AmazonProduct(val id: Int, val title: String, val group: String) extends java.io.Serializable {
  override def toString: String = {
    id + " " + title + " " + group
  }
}

object AmazonProduct {
  private val delimiter = ": "

  def make(recordString: String): AmazonProduct = {
    val lines = recordString.split("\n")
      .map(l => l.trim())

    val id = parseLine(lines, "Id")
    val title = parseLine(lines, "title")
    val group = parseLine(lines, "group")

    new AmazonProduct(Integer.parseInt(id), title, group)
  }

  private def parseLine(lines: Array[String], keyWord: String) = {
    lines
      .filter(l => l.startsWith(keyWord))(0)
      .split(delimiter, 2)(1)
      .trim()
  }
}
