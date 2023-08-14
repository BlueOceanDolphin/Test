package test.org.models.board;

import org.springframework.stereotype.Component;
import test.org.controllers.board.BoardDataForm;

import test.org.vaildators.Validator;

@Component
public class BoardSaveValidator implements Validator<BoardDataForm>, test.org.vaildators.RequiredVaildator {

    @Override
    public void check(BoardDataForm data) {
        checkRequired(data.getPoster(), new BoardValidationException("작성자를 입력해주세요."));
        checkRequired(data.getSubject(), new BoardValidationException("제목을 입력해주세요."));
        checkRequired(data.getContent(), new BoardValidationException("내용을 입력해주세요."));

    }
}
