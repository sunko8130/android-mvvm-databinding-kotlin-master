package br.com.wellingtoncosta.amdk.data.remote.response

data class Resource<out T>(
        val status: Int,
        val data: T?,
        val error: Throwable?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, error = null)
        }

        fun <T> error(error: Throwable?): Resource<T> {
            return Resource(status = Status.ERROR, data = null, error = error)
        }
    }
}