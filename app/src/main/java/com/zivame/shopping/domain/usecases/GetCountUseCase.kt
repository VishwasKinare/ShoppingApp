package com.zivame.shopping.domain.usecases

import androidx.lifecycle.LiveData
import com.zivame.shopping.domain.repository.CartRepository
import javax.inject.Inject

class GetCountUseCase @Inject constructor(
    private val repository: CartRepository
)  {
    operator fun invoke(): LiveData<Int>{
        return repository.getCount()
    }
}