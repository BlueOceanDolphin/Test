package test.org.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import test.org.test.model.noticeBoard.NoticeBoardData;
import test.org.test.model.noticeBoard.InfoService;
import test.org.test.model.noticeBoard.SaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class NoticeBoardController {
    private final SaveService saveService;
    private final InfoService infoService;

    @GetMapping("/write")
    public String write(@ModelAttribute NoticeBoardForm data) {

        return "board/write";
    }

    @PostMapping("/save")
    public String save(@Valid NoticeBoardForm ndata, Errors errors) {
        if (errors.hasErrors()) {
            return "board/write";
        }

        saveService.save(ndata);

        return "redirect:/board/view/" + ndata.getId();
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model){

        NoticeBoardData data = infoService.get(id);

        model.addAttribute("data", data);

        return "board/view";
    }

    @ExceptionHandler(RuntimeException.class)
    public String errorHandler(RuntimeException e, Model model) {
        String script = String.format("alert('%s');history.back();", e.getMessage());
        model.addAttribute("script", script);

        e.printStackTrace();

        return "common/_script";
    }
}
