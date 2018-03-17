package models

import akka.Done
import org.specs2.mutable.Specification
import play.api.Application

import scala.reflect.ClassTag
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, duration}


class modelsTest[T:ClassTag]{
  def fakeApp: Application = {
    new GuiceApplicationBuilder().build()

  }

lazy val app2dao = Application.instanceCache[T]
lazy val repository:T = app2dao(fakeApp)
}

class userInfoRepoSpec extends Specification {
  val repo = new modelsTest[userInfoRepo]

  "user info repository" should{
    "store associate detail of a user" in {
      val user= repo.repository.UserInfo("neelam","lakra","abc@xyz.com")
      val storeResult = Await.result(repo.repository.store(user),Duration.Inf)
      storeResult must equalTo(Done)
    }
  }
}
