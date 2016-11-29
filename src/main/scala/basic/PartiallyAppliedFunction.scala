package basic




object PartiallyAppliedFunction {

  case class Email(
                    subject: String,
                    text: String,
                    sender: String,
                    recipient: String)

  type EmailFilter = Email => Boolean





}
