package ahmed.adel.sleeem.clowyy.souq.pojo.request

data class UserRequist(
    val Address: String?="N/F",
    val BirthDate: String?="12-12-2000",
    val Gender: String?="male",
    val email: String?="N/F",
    val name: String?="N/F",
    val phoneNumber: String?="011111111111",
    val profileImage: String?= "https://firebasestorage.googleapis.com/v0/b/sell3a-d7b51.appspot.com/o/profile_pic%2Fuser.png?alt=media&token=ea9dcdfc-079b-40bd-bf9f-9fe76db81cc8"
)