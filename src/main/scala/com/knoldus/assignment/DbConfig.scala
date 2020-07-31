package com.knoldus.assignment

import com.knoldus.assignment.model.UserTable
import slick.jdbc.MySQLProfile.api._

object DbConfig
{
  val DB_Name = "userdb"
  val url = "jdbc:mysql://localhost:3306"
  val urlParameters = "?useSSL=false"
  val driver = "com.mysql.cj.jdbc.Driver"
  val user = "username"
  val password = "password"

  val baseUrl = url + urlParameters
  val urlAfterDbCreation = url + "/" + DB_Name + urlParameters

  val db : slick.jdbc.MySQLProfile.backend.DatabaseDef = Database.forURL(
    url = urlAfterDbCreation,
    driver = driver,
    user = user,
    password = password
  )
  val table = new UserTable(db)
}
