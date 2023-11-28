package com.example.opentablechallenge.core.domain.usecases

abstract class BaseUseCase<P,R> {
    abstract fun buildUseCase(params: P): R
}