package cj.esanar.controller;

import cj.esanar.service.HistoryService;
import cj.esanar.service.dtos.out.HistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/esanar/api/v1/stories")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/list")
    public ResponseEntity<List<HistoryDto>> findAllStories(){

        List<HistoryDto> listStories= historyService.listHistory();
        return ResponseEntity.ok(listStories);

    }
    @GetMapping("/find/{id}")
    public ResponseEntity<HistoryDto> findHistoryById(@PathVariable Long id){
        HistoryDto historyDto=historyService.findHistoryById(id);
        return ResponseEntity.ok(historyDto);
    }

}
