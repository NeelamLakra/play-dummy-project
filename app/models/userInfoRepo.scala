package models

import javax.inject.Inject

import akka.Done
import play.api.cache.AsyncCacheApi

import scala.concurrent.Future

class userInfoRepo @Inject()(cache: AsyncCacheApi){

  case class UserInfo(fname: String, lname: String, email: String)

  def store(userInfo: UserInfo): Future[Done] = {
    cache.set(userInfo.email, userInfo)
  }

  def getUser(email: String): Future[Option[UserInfo]] = {
    cache.get[UserInfo](email)
  }

}
