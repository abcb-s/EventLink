package org.example.eventlink.notice.dto;

public record DeleteResponse(
        String message
) {
    public static DeleteResponse of(String message){
        return new DeleteResponse(
                message
        );
    }
}
