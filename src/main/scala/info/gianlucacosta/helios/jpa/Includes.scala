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
