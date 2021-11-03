import com.google.firebase.auth.FirebaseUser

class EmailPasswordLoginModel(var email:String, var password:String) {
    lateinit var toastMessage: String
    lateinit var errorMessage: String
    lateinit var user: FirebaseUser
}