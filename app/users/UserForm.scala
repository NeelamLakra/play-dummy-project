package users
import play.api.data.Form
import play.api.data.Forms.{mapping,text,email}
case class userInformation(fname:String, lname:String,email:String)

class UserForm {
val userInfoForm= Form(mapping(
  "fname" ->text.verifying("please fill", _.nonEmpty),
  "lname" ->text.verifying("please fill",_.nonEmpty),
  "email" -> email
)(userInformation.apply)(userInformation.unapply))
}
