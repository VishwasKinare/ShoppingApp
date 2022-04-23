package com.zivame.shopping.domain.usecases

import com.zivame.shopping.R
import com.zivame.shopping.core.Resource
import com.zivame.shopping.data.dto.ShoppingListDto
import com.zivame.shopping.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetShoppingListUseCase @Inject constructor(
    private val repository: ShoppingListRepository
)  {

    operator fun invoke(): Flow<Resource<ShoppingListDto>> = flow {
        try {
            emit(Resource.loading())
            val response =  repository.getShoppingListItems()
            if (response.isSuccessful){
                response.body()?.let {
                    emit(Resource.success(it))
                } ?:  emit(Resource.error(R.string.unable_to_process_request, null))
            } else {
                emit(Resource.error(response, null))
            }
        } catch (e: HttpException){
            emit(Resource.error(e.localizedMessage ?: R.string.unable_to_process_request, null))
        } catch (e: IOException){
            emit(Resource.error(e.localizedMessage ?: R.string.unable_to_process_request, null))
        }
    }
}