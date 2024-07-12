package info.gianlucacosta.helios.jpa

private object QueryNaming {
  def getFullName(namespaceClass: Class[_], localName: String): String =
    s"${namespaceClass.getName}.${localName}"
}
