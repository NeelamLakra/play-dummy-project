package controllers

import javax.inject._

import models.userInfoRepo
import play.api.i18n.I18nSupport
import play.api.mvc._
import users.UserForm
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class Day2Controller @Inject()(userForm: UserForm, userInfoRepo: userInfoRepo, cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {
  implicit val message = cc.messagesApi

  def day2: Action[AnyContent] = Action.async { implicit request =>
    throw new Exception()
    Future.successful(Ok(views.html.day2(userForm.userInfoForm)))
  }

  def storeData: Action[AnyContent] = Action.async { implicit request =>
    userForm.userInfoForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.day2(formWithErrors)))
      },
      data => {
        userInfoRepo.getUser(data.email).flatMap({ optionalRecord =>
          optionalRecord.fold {
            val record = userInfoRepo.UserInfo(data.fname,data.lname,data.email)
            userInfoRepo.store(record).map {_ =>
              Ok("stored")
          }
        } { _ =>
          Future.successful(InternalServerError("user already exist"))
          }
      })
  })
}

  def getData(email:String) :Action[AnyContent]= Action.async{
    userInfoRepo.getUser(email).map{ optionalRecord =>
      optionalRecord.fold{
        NotFound("oops! user not found")
      }{
        record =>
          Ok(s"user name is ${record.fname} ${record.lname}" )
      }
    }
  }


}
