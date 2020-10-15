package Entities

import org.mongodb.scala.MongoClient

/** Model is an object that groups all other entities */

object Model{

  private val mongoClient: MongoClient = MongoClient()
  val db: DBConnection = new DBConnection(mongoClient)
  val menu: CLUI = new CLUI(db)

  def runModel(): Unit = {
    menu.menu()
  }
}
