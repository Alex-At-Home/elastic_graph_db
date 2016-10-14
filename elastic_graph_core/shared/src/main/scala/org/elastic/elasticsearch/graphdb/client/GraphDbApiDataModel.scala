package org.elastic.elasticsearch.graphdb.client

/**
  * The API data model
  */
object GraphDbApiDataModel {

  case class NewAttribute(key: String, value: Any)

  case class NewEdge(`type`: String, attrs: List[NewAttribute])

}
