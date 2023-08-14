package test.org.controllers.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import test.org.models.board.BoardData;
import test.org.models.board.BoardDataDao;
import test.org.models.board.InfoService;
import test.org.models.board.SaveService;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardDataDao boardDataDao;
    private final SaveService saveService;
    private final InfoService infoService;

    @Autowired
    WebApplicationContext servlet;
    @GetMapping("/write")
    public String write(@ModelAttribute BoardDataForm data) {

        return "board/write";
    }

    /*@GetMapping("/list")
    public String list(@ModelAttribute BoardDataForm data) {

        return "board/list";
    }*/

    @PostMapping("/save")
    public String save(@Valid BoardDataForm data, Errors errors) {

        if (errors.hasErrors()) {
            return "board/write";
        }

        saveService.save(data);

        return "redirect:/board/view/" + data.getId();
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {

        BoardData data = infoService.get(id);

        model.addAttribute("data", data);

        return "board/view";
    }

    @ExceptionHandler(RuntimeException.class)
    public String errorHandler(RuntimeException e, Model model) {

        String script = String.format("alert('%s');history.back();", e.getMessage());
        model.addAttribute("script", script);


        e.printStackTrace();

        return "commons/execute_script";
    }



    @GetMapping("/list")
    public String showBoardList(Model model) {
        List<BoardData> boardList = boardDataDao.getAll();
        model.addAttribute("list", boardList);
        return "board/list"; // Thymeleaf 템플릿의 경로
    }
}