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
