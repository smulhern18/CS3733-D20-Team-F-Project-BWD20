package edu.wpi.teamF.modelClasses;

public class ValidationException extends Exception {
        private static final long serialVersionUID = 1L;

        /** Exception Message */
        String message = null;

        public ValidationException(String message) {
            this.message = "Validation failed: invalid " + message;
        }
}
