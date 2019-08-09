import org.apache.spark.rdd.RDD

object GuessTheProduct {

  def containsBothProducts(randomAssociation: ProductAssociation, amazonProducts: RDD[AmazonProduct]) = {
    amazonProducts.filter(p => p.id == randomAssociation.to || p.id == randomAssociation.from).count() == 2
  }

  def main(args: Array[String]): Unit = {
    val amazonProductsRaw = AmazonRDDGenerator.createRDDOfRecords("file:///Users/mutr/workspaces/guess-the-product/src/main/resources/amazon-products.txt")

    val randomAssociation: ProductAssociation = getRandomAssociation

    println(s"association is: $randomAssociation")

    //
    //    if (!containsBothProducts(randomAssociation, amazonProducts)) {
    //      throw new Exception("Something's wrong")
    //    }

    val amazonProducts = amazonProductsRaw
      .map(AmazonProduct.make)

    println(amazonProducts.countApprox(1000,0.90))

    val startProduct: AmazonProduct = amazonProducts
      .filter(p => p.id == randomAssociation.from)
      .first()

    println(s"start is: $startProduct")
    println(amazonProducts.countApprox(1000,0.90))

    val goalProduct: AmazonProduct = amazonProducts
      .filter(p => p.id == randomAssociation.to)
      .first()

    if (goalProduct == null) {
      println(s"goal could not be found. Id: *${randomAssociation.to}*")
    } else {
      println(s"goal is: ${goalProduct}")
    }

    val randomRecords = amazonProducts
      .takeSample(withReplacement = false, 8)

    println(s"solution set is: ${randomRecords.map(r => r.id).mkString(" ")}")

    println("done")


  }

  private def getRandomAssociation = {
    val amazonProductAssociationsRaw = AmazonRDDGenerator.createRDDOfAssociatedProducts("/Users/mutr/workspaces/guess-the-product/src/main/resources/amazon-relations.txt")

    val amazonProductAssociations = amazonProductAssociationsRaw
      .map(ProductAssociation.make)

    amazonProductAssociations
      .takeSample(withReplacement = false, 1)(0)
  }

  def doesHaveAssociation(record: AmazonProduct, associations: RDD[ProductAssociation]): Boolean = {
    associations.filter(a => a.from == record.id).count() > 0
  }
}

