package capstone.product.tim.majorsmatch.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val statusLoginUser: Boolean = false
)