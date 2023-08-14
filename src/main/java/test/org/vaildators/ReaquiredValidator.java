package test.org.vaildators;

public interface ReaquiredValidator {
    default void checkRequired(String str, RuntimeException e) {
        if (str == null && str.isBlank()){
            throw e;
        }
    }
}
