package com.example.testcompose.domain.usecases

import kz.viled.domain.entities.BaseErrorResponse


/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
@Suppress("EqualsOrHashCode")
sealed class Failure(
    val exception: Exception = Exception("Failure"),
    val error: BaseErrorResponse? = null
) {

    object None : Failure()

    object NetworkConnection : Failure()

    /** * Extend this class for feature specific failures.*/
    open class FeatureFailure(
        featureException: Exception = Exception("Feature failure"),
        featureError: BaseErrorResponse? = null
    ) : Failure(featureException, featureError)

    override fun equals(other: Any?): Boolean {
        return other is Failure
    }
}