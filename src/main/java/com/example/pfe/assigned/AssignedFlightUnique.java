package com.example.pfe.assigned;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the idfs value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = AssignedFlightUnique.AssignedFlightUniqueValidator.class
)
public @interface AssignedFlightUnique {

    String message() default "{Exists.assigned.flight}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class AssignedFlightUniqueValidator implements ConstraintValidator<AssignedFlightUnique, Integer> {

        private final AssignedService assignedService;
        private final HttpServletRequest request;

        public AssignedFlightUniqueValidator(final AssignedService assignedService,
                final HttpServletRequest request) {
            this.assignedService = assignedService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Integer value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equals(assignedService.get(Integer.parseInt(currentId)).getFlight())) {
                // value hasn't changed
                return true;
            }
            return !assignedService.flightExists(value);
        }

    }

}
