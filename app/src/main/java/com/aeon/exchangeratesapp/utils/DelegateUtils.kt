package com.aeon.exchangeratesapp.utils

object DelegateUtils {
    fun <T> lazyUnsafe(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
}
