package info.gianlucacosta.helios.jpa

import javax.persistence.{EntityManager, EntityManagerFactory}

import scala.language.implicitConversions

/**
  * Central point containing all the extensions provided by the library
  */
object Includes {
  implicit def extendEntityManagerFactory(entityManagerFactory: EntityManagerFactory): EntityManagerFactoryExtensions =
    new EntityManagerFactoryExtensions(entityManagerFactory)

  implicit def extendEntityManager(entityManager: EntityManager): EntityManagerExtensions =
    EntityManagerExtensions(entityManager)
}
