import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseLoginClass {
    private var myAuth: FirebaseAuth = Firebase.auth

    fun loginWithUserNameAndPassword(eplm: EmailPasswordLoginModel, onSuccess:(EmailPasswordLoginModel)->Unit, onFailure:(EmailPasswordLoginModel)->Unit) {
        myAuth.signInWithEmailAndPassword(eplm.email, eplm.password).addOnCompleteListener {
                task->
            if(task.isSuccessful){
                logError("Sign in with email and password: successful")
                eplm.user = myAuth.currentUser!!
                eplm.toastMessage = "Welcome!!"
                onSuccess(eplm)
            } else {
                createLoginWithUserAndPassword(eplm, onSuccess, onFailure)
                // TODO - Consider refactoring this to go to a separate
                // registration page rather than automatically creating
                // a new user
            }
        }
    }

    fun createLoginWithUserAndPassword(eplm: EmailPasswordLoginModel, onSuccess:(EmailPasswordLoginModel)->Unit, onFailure:(EmailPasswordLoginModel)->Unit) {
        myAuth.createUserWithEmailAndPassword(eplm.email, eplm.password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    eplm.toastMessage = "User created successfully!"
                    eplm.user = myAuth.currentUser!!
                    logError("createuserWithEmail:Success")
                    onSuccess(eplm)
                } else {
                    eplm.toastMessage = "Login Failed: try again"
                    eplm.errorMessage = "Either your password is wrong or you are trying to create a new user with an email that already exists in the system."
                    logError("createUserWithEmail:failure")
                    onFailure(eplm)
                }
            }
    }

    private fun logError(message: String){
        Log.w(ContentValues.TAG, message)
    }
}