package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.repository

import ahmed.adel.sleeem.clowyy.souq.api.ItemWebServices
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended.RecommendedPagingSource
import ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.saleProducts.SalePagingSource
import androidx.paging.Pager
import androidx.paging.PagingConfig

class HomeRepository(
    private val api: ItemWebServices,

) {

    fun saleRepos() = Pager(
        pagingSourceFactory = {
            SalePagingSource(
                api
            )
        },
        config = PagingConfig ( pageSize = 10 )
    ).flow

    fun recommendedRepos(filterParams: FilterParams) = Pager(
        pagingSourceFactory = {
            RecommendedPagingSource(
                api,
                filterParams = filterParams
            )
        } ,
        config = PagingConfig(pageSize = 10)
    ).flow



}