package bistrot.common.error.message;

public interface MessageError {

    String COMPOSITION_ITEM_NOT_FOUND = "The composition item id: %s not found";
    String COMPOSITION_ITEM_DTO_ARGUMENT_IS_NULL = "The composition item dto argument is null";

    static String buildMessageError(Long id, String message) {
        return String.format(message, id);
    }
}
