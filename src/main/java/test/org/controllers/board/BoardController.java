package test.org.controllers.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import test.org.models.board.BoardData;
import test.org.models.board.InfoService;
import test.org.models.board.SaveService;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final SaveService saveService;
    private final InfoService infoService;

    @GetMapping("/write")
    public String write(@ModelAttribute BoardDataForm data) {

        return "board/write";
    }
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
}