package Entities
/** Model is an object that groups all other entities */

object Model{
  private val username = "username"
  private val password = "passsword"
  val menu: CLUI = new CLUI()
  val db: DBConnection = new DBConnection(username, password)

  def runModel(): Unit = {
    menu.menu()
  }
}
