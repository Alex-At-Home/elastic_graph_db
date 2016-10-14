package org.elastic.elasticsearch.graphdb.server

import org.elastic.elasticsearch.graphdb.client.GraphDbApi._
import org.elastic.rest.scala.driver.RestBase.{BaseDriverOp, RestDriver}

import scala.concurrent.Future
import scala.concurrent.duration.Duration

/**
  * A server side interpreter for the
  */
class GraphDbInterpreter(esDriver: RestDriver) {
  protected implicit val driver = esDriver

  def act(op: BaseDriverOp): Unit = (op.resource.asInstanceOf[GraphDbOps], op) match {
    case (`/node/$index/$type`(index, nodeType), b) => //TODO: have a smaller method/mods/body unapply for BaseDriverOps

      ()

      //TODO: others ... why isn't this returning an error on not covering?!
  }

}
