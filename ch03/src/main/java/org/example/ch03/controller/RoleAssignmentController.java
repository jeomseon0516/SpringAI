package org.example.ch03.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.ch03.service.RoleAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@Log4j2
@RequiredArgsConstructor
public class RoleAssignmentController {

    private final RoleAssignmentService service;

    @GetMapping("/ai/role-assignment")
    public String roleAssignment() {
        return "role-assignment";
    }

    @PostMapping("/ai/role-assignment")
    @ResponseBody
    public String roleAssignment(@RequestParam("requirements") String requirements) {
        log.info("requirements:{}", requirements);

        return service.prompt(requirements);
    }
}
