package com.example.scrollingimagegallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scrollingimagegallery.recyclerview.ImageGalleryData
import com.example.scrollingimagegallery.repo.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val pageSize = 20

    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.getImages(currentPage, pageSize)
            if (result.isSuccess) {
                val images = result.getOrNull().orEmpty()
                _uiState.value = UiState.Success( images)
                if (images.isNotEmpty()) {
                    currentPage++
                }
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

}

sealed class UiState {
    data object Loading : UiState()
    class Success(val list: List<ImageGalleryData>) : UiState()
    data object Error : UiState()
}