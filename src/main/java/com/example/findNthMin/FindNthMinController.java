package com.example.findNthMin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FindNthMinController {

    private final FindNthMinService findNthMinService;

    public FindNthMinController(FindNthMinService findNthMinService){
        this.findNthMinService = findNthMinService;
    }

    @Operation(summary = "Find Nth min number", description = "Find Nth minimum number from file")
    @ApiResponse(responseCode = "200", description = "Number found")
    @GetMapping("/find")
    public int findNthMin(
            @Parameter(description = "FilePath", required = true)
            @RequestParam String path,
            @Parameter(description = "Element number", required = true)
            @RequestParam int n
    ){
        return findNthMinService.findNthMin(path, n, false);
    }

    @Operation(summary = "Find Nth min unique number", description = "Find Nth minimum unique number from file")
    @ApiResponse(responseCode = "200", description = "Number found")
    @GetMapping("/findUnique")
    public int findNthMinUniique(
            @Parameter(description = "FilePath", required = true)
            @RequestParam String path,
            @Parameter(description = "Element number", required = true)
            @RequestParam int n
    ){
        return findNthMinService.findNthMin(path, n, true);
    }
}
