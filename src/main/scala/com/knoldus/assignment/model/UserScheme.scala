package com.knoldus.assignment.model

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class UserScheme(tag : Tag) extends Table[User](tag , "user")
{
  def id: Rep[Int] = column[Int]("ID" , O.PrimaryKey)
  def name: Rep[String] = column[String]("NAME")

  def * : ProvenShape[User] = (id , name) <> (User.tupled , User.unapply)
}

class UserTable(m_db : slick.jdbc.MySQLProfile.backend.DatabaseDef) extends TableQuery(new UserScheme(_))
{
  def createDB(): Future[Int] =
  {
    import com.knoldus.assignment.DbConfig._
    val db : slick.jdbc.MySQLProfile.backend.DatabaseDef = Database.forURL(url = baseUrl,
      driver = driver,
      user = user,
      password = password)
    val createDatabaseIfNotExists = sqlu"CREATE DATABASE IF NOT EXISTS `UserDB`"
    db.run(createDatabaseIfNotExists)//.as[String])
  }
  def create(): Future[Unit] =
  {
    val db = createDB()
    val query = TableQuery[UserScheme].schema.create
    val table = m_db.run(query)
    for {
      _ <- db
      _ <- table
    } yield()
  }
  def insert(user : User): Future[Int] =
  {
    val query = this += user
    m_db.run(query)
  }

  def find(id : Int): Future[Seq[User]] =
  {
    val query = this.filter(_.id === id).result
    m_db.run(query)
  }

  def update(id : Int , changeToName : String): Future[Int] =
  {
    val query = this.filter(_.id === id).map(_.name).update(changeToName)
    m_db.run(query)
  }

  def delete(id : Int): Future[Int] =
  {
    val query = this.filter(_.id === id).delete
    m_db.run(query)
  }
}