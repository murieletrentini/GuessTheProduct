class ProductAssociation(val from: Int, val to: Int) extends java.io.Serializable {
  override def toString: String = {
    from + "->" + to
  }
}

object ProductAssociation {
  private val delimiter = "\t"

  def make(associationString: String): ProductAssociation = {
    val ids = associationString.trim().split(delimiter)
    new ProductAssociation(Integer.parseInt(ids(0)), Integer.parseInt(ids(1)))
  }
}
