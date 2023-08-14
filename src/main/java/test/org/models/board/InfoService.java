package test.org.models.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final BoardDataDao boardDataDao;

    public BoardData get(long id) {

        BoardData data = boardDataDao.get(id);
        if (data == null) {
            throw new BoardDataNotFoundException();
        }

        return data;
    }
}
