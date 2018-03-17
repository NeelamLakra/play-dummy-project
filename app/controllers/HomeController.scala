package controllers

import javax.inject._

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
  * case class user(name:String,id:Int)

  */

case class user(name:String,email:String)
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {
  implicit val message =cc.messagesApi

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
//    def index= Action { implicit request: Request[AnyContent] =>
//      val data= user("neel",21)
//      val content = views.html.index(data)
//      Ok(content)
//    }

val recordForm = Form(mapping(
  "name" -> nonEmptyText,
  "email" -> email
)(user.apply)(user.unapply)
)

  def index = Action { implicit request: Request[AnyContent] =>
    {
    Ok(views.html.index(recordForm))
    }
  }
def validate= Action { implicit request: Request[AnyContent] =>

  recordForm.bindFromRequest.fold(
    formWithErrors => BadRequest(views.html.index(formWithErrors)),
    input =>{
      Redirect(routes.HomeController.data()).withSession("connected" -> input.email).flashing("success" -> "congo")
            })
}

  def data = Action {
    req => req.session.get("connected").map{
      user => Ok("Hello" + user)
    }.getOrElse {
      Unauthorized("Oops")
    }
  }
}