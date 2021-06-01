package ahmed.adel.sleeem.clowyy.souq.pojo.request

data class PasswordRequest(
    val email: String?,
    val new_password: String?,
    val old_password: String?
)