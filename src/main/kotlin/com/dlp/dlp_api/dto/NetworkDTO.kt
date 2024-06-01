package com.dlp.dlp_api.dto

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

class CreateNetworkDTO(
    @field:ValidSubnet
    var subnet: String,
    var name: String,
    var description: String
)


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SubnetValidator::class])
annotation class ValidSubnet(
    val message: String = "Invalid subnet format",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class SubnetValidator : ConstraintValidator<ValidSubnet, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }

        // Regular expression to match CIDR notation (e.g., 192.168.0.0/24)
        val subnetRegex =
            Regex("""^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])/(3[0-2]|[1-2]?[0-9])$""")
        return subnetRegex.matches(value)
    }
}