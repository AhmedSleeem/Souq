package ahmed.adel.sleeem.clowyy.souq.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val flag:Int=0) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T , flag:Int=0): Resource<T> {
            return Resource(Status.SUCCESS, data, null, flag)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}