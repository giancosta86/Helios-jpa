/*^
  ===========================================================================
  Helios - JPA
  ===========================================================================
  Copyright (C) 2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.helios.jpa

import javax.persistence.{EntityManager, EntityManagerFactory, Query}

import scala.language.implicitConversions


/**
  * Extension methods for EntityManagerFactory
  *
  * @param entityManagerFactory
  */
class EntityManagerFactoryExtensions private[jpa](entityManagerFactory: EntityManagerFactory) {
  /**
    * Creates an EntityManager and passes it to the unit of work - automatically closing it in the end
    *
    * @param unitOfWork A function receiving an EntityManager and returning a value - which will be returned by this function as well
    * @tparam T The result type of the unit of work
    * @return The result of the unit of work
    */
  def runUnitOfWork[T](unitOfWork: EntityManager => T): T = {
    val entityManager =
      entityManagerFactory.createEntityManager()

    try {
      unitOfWork(entityManager)
    } finally {
      entityManager.close()
    }
  }


  /**
    * Similar to runUnitOfWork(), but the given unit of work is run in the context of a transaction which is automatically opened and then committed in the end
    *
    * @param transactionalUnit The unit of work, receiving an EntityManager and returning a value
    * @tparam T The result type of the unit of work
    * @return The result of the unit of work
    */
  def runTransaction[T](
                         transactionalUnit: EntityManager => T
                       ): T = {

    runUnitOfWork(entityManager => {
      entityManager.getTransaction.begin()

      val result: T =
        transactionalUnit(entityManager)

      entityManager.getTransaction.commit()

      result
    })
  }


  /**
    * Creates a named query for the given class - therefore simplifying the creation of global query names
    *
    * @param namespaceClass The class providing the first part of the global namespace
    * @param localName      A local name, which - consequently - does not need to be unique
    * @param query          The query object to be named
    */
  def addNamedQueryFor(namespaceClass: Class[_], localName: String, query: Query): Unit = {
    val fullName =
      QueryNaming.getFullName(namespaceClass, localName)

    entityManagerFactory.addNamedQuery(
      fullName,
      query
    )
  }


  /**
    * Creates a named query for the given class - therefore simplifying the creation of global query names
    *
    * @param namespaceClass  The class providing the first part of the global namespace
    * @param localName       A local name, which - consequently - does not need to be unique
    * @param queryDefinition The query definition string
    */
  def addNamedQueryFor(namespaceClass: Class[_], localName: String, queryDefinition: String): Unit = {
    val entityManager =
      entityManagerFactory.createEntityManager()

    val query =
      entityManager.createQuery(queryDefinition)


    addNamedQueryFor(namespaceClass, localName, query)
  }
}
