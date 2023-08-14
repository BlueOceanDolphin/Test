package test.org.models.board;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import test.org.controllers.board.BoardDataForm;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final BoardSaveValidator validator;
    private final BoardDataDao boardDataDao;

    public void save(BoardDataForm data) {
        validator.check(data);

        boardDataDao.save(data);
    }
}
