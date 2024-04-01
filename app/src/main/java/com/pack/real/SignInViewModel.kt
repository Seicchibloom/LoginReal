package com.pack.real
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.text.Typography.dagger

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
}