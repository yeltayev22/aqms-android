package kz.yeltayev.aqms.api

import kotlinx.coroutines.Deferred
import kz.yeltayev.aqms.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String):
            Deferred<Response<List<Repo>>>
}