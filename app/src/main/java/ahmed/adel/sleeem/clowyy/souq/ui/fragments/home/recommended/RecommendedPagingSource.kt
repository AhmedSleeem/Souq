package ahmed.adel.sleeem.clowyy.souq.ui.fragments.home.recommended

import ahmed.adel.sleeem.clowyy.souq.api.ItemWebServices
import ahmed.adel.sleeem.clowyy.souq.pojo.FilterParams
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ProductResponse
import androidx.paging.PagingSource
import androidx.paging.PagingState

class RecommendedPagingSource(private val webServices:ItemWebServices ,
private val filterParams: FilterParams) : PagingSource<Int, ProductResponse.Item>() {
    private val  INITIAL_PAGE = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResponse.Item> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = webServices.filterProducts(
                filterParams.min,
                filterParams.max,
                filterParams.category,
                filterParams.sale,
                filterParams.brand,
                filterParams.title,
                filterParams.price,
                page=page
            ).body()!!.items!!
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (response.isEmpty()||page==2) null else page + 1
                )
        } catch (e: Exception) {
                LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductResponse.Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}