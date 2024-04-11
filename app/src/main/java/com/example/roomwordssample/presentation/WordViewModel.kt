package com.example.roomwordssample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomwordssample.domain.Word
import com.example.roomwordssample.data.WordRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

//ViewModel convertirá los datos del repositorio de flujo en LiveData, y le mostrará a la IU la
// lista de palabras como LiveData. De esta forma, nos aseguraremos de que nuestra IU se actualice
// automáticamente cada vez que se modifiquen los datos en la base de datos.
class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

//Cuando usas viewModels y ViewModelProvider.Factory, el framework se encarga del ciclo de vida del ViewModel.
// Sobrevivirá a los cambios de configuración y siempre obtendrás la instancia correcta de la clase WordViewModel,
// incluso si se recrea la actividad.

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

