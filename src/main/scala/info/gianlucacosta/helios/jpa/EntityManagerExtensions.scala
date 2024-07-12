package info.gianlucacosta.helios.jpa

import javax.persistence.{EntityManager, Query, TypedQuery}

import scala.language.implicitConversions

/**
  * Extension methods for EntityManager
  *
  * @param entityManager
  */
case class EntityManagerExtensions private[jpa](entityManager: EntityManager) {
  /**
    * Creates a Query instance out of a previously-defined named query for the given class
    *
    * @param namespaceClass The class acting as a query namespace
    * @param localName      The relative query name
    * @return A suitable query instance
    */
  def createNamedQueryFor(namespaceClass: Class[_], localName: String): Query = {
    val fullName =
      QueryNaming.getFullName(namespaceClass, localName)

    entityManager.createNamedQuery(fullName)
  }


  /**
    * Creates a TypedQuery instance out of a previously-defined named query for the given class
    *
    * @param namespaceClass The class acting as a query namespace
    * @param localName      The relative query name
    * @param resultType     The class of the TypedQuery
    * @tparam T The type parameter of the TypedQuery
    * @return A suitable TypedQuery instance
    */
  def createNamedQueryFor[T](namespaceClass: Class[_], localName: String, resultType: Class[T]): TypedQuery[T] = {
    val fullName =
      QueryNaming.getFullName(namespaceClass, localName)

    entityManager.createNamedQuery(fullName, resultType)
  }
}
