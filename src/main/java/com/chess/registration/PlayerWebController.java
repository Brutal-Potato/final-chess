package com.chess.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/player")
public class PlayerWebController {

    @Autowired
    private final PlayerRepository playerRepository;

    @Autowired
    private final PlayerService playerService;

    public PlayerWebController(PlayerRepository playerRepository, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }


    @GetMapping
    public String index() {
        return "/index.html";
    }

    @RequestMapping(value = "/data_for_table", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDataForTable(@RequestParam Map<String, Object> params) {
        int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
        int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
        int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
        int currentPage = start / length;

        String sortName = "id";
        String tableOrderColumnIdx = params.get("order[0][column]").toString();
        String tableOrderColumnName = "columns[" + tableOrderColumnIdx + "][data]";
        if (params.containsKey(tableOrderColumnName))
            sortName = params.get(tableOrderColumnName).toString();
        String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

        Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC), sortName);
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(currentPage,
                length,
                sort);

        String queryString = (String) (params.get("search[value]"));

        Page<Players> players = playerService.getPlayersForTable(queryString, pageRequest);

        long totalRecords = players.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        players.forEach(player -> {
            Map<String, Object> cellData = new HashMap<>();
            cellData.put("id", player.getId());
            cellData.put("firstName", player.getFirstName());
            cellData.put("lastName", player.getLastName());
            cellData.put("emailAddress", player.getEmailAddress());
            cellData.put("dateStarted", player.getDateStarted());
            cells.add(cellData);
        });

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("draw", draw);
        jsonMap.put("recordsTotal", totalRecords);
        jsonMap.put("recordsFiltered", totalRecords);
        jsonMap.put("data", cells);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Players playerInstance = playerRepository.findById(Long.valueOf(id)).get();

        model.addAttribute("playerInstance", playerInstance);

        return "/edit.html";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("playerInstance") Players playerInstance,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/edit.html";
        } else {
            if (playerRepository.save(playerInstance) != null)
                atts.addFlashAttribute("message", "player updated successfully");
            else
                atts.addFlashAttribute("message", "player update failed.");

            return "redirect:/";
        }
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("playerInstance", new Players());
        return "/create.html";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("playerInstance") Players playerInstance,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/create.html";
        } else {
            if (playerRepository.save(playerInstance) != null)
                atts.addFlashAttribute("message", "player created successfully");
            else
                atts.addFlashAttribute("message", "player creation failed.");

            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes atts) {
        Players playerInstance = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("player Not Found:" + id));

        playerRepository.delete(playerInstance);

        atts.addFlashAttribute("message", "player deleted.");

        return "redirect:/";
    }

}
