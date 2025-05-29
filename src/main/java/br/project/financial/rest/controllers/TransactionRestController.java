package br.project.financial.rest.controllers;

import br.project.financial.dtos.transaction.input.TransactionRevenueByDateInputDTO;
import br.project.financial.dtos.transaction.input.TransactionRevenueByPeriodInputDTO;
import br.project.financial.dtos.transaction.output.*;
import br.project.financial.enums.TransactionType;
import br.project.financial.rest.specs.TransactionControllerSpecs;
import br.project.financial.usecases.transaction.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionRestController implements TransactionControllerSpecs {

    private final CalculateRevenueByTypeAndDateUseCase byDateUseCase;
    private final CalculateRevenueByTypeAndPeriodUseCase byPeriodUseCase;
    private final CalculateBranchRevenueByTypeAndPeriodUseCase byBranchUseCase;
    private final CalculateTopBranchByTypeUseCase topBranchUseCase;
    private final CalculateTop2BranchesComparisonUseCase comparisonUseCase;
    private final ListDetailedTransactionsUseCase detailedTransactionsUseCase;

    public TransactionRestController(
            CalculateRevenueByTypeAndPeriodUseCase byPeriodUseCase,
            CalculateRevenueByTypeAndDateUseCase byDateUseCase,
            CalculateBranchRevenueByTypeAndPeriodUseCase byBranchUseCase,
            CalculateTopBranchByTypeUseCase topBranchUseCase,
            CalculateTop2BranchesComparisonUseCase comparisonUseCase,
            ListDetailedTransactionsUseCase detailedTransactionsUseCase
    ) {
        this.byPeriodUseCase = byPeriodUseCase;
        this.byDateUseCase = byDateUseCase;
        this.byBranchUseCase = byBranchUseCase;
        this.topBranchUseCase = topBranchUseCase;
        this.comparisonUseCase = comparisonUseCase;
        this.detailedTransactionsUseCase = detailedTransactionsUseCase;
    }

    @GetMapping
    public ResponseEntity<TransactionRevenueOutputDTO> getRevenueByTypeAndDate(
            @ModelAttribute TransactionRevenueByDateInputDTO inputDTO){
        return ResponseEntity.ok(byDateUseCase.execute(inputDTO.getTransactionType(), inputDTO.getDate()));
    }

            @GetMapping("/period")
    public ResponseEntity<TransactionRevenueOutputDTO> getRevenueByTypeAndPeriod(TransactionRevenueByPeriodInputDTO inputDTO) {
        return ResponseEntity.ok(byPeriodUseCase.execute(inputDTO.getTransactionType(), inputDTO.getStartDate(), inputDTO.getEndDate()));
    }

    @GetMapping("/branch")
    public ResponseEntity<BranchTransactionRevenueDTO> getRevenueByBranchTypeAndPeriod(
            @RequestParam("type") TransactionType transactionType,
            @RequestParam String branch,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(byBranchUseCase.execute(transactionType, branch, startDate, endDate));
    }

    @GetMapping("/branch/top")
    public ResponseEntity<BranchAmountDTO> getTopBranchByType(
            @RequestParam("type") TransactionType transactionType
    ) {
        return ResponseEntity.ok(topBranchUseCase.execute(transactionType));
    }

    @GetMapping("/comparison")
    public ResponseEntity<Top2BranchesComparisonDTO> compareTop2Branches(
            @RequestParam("type") TransactionType transactionType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(comparisonUseCase.execute(transactionType, startDate, endDate));
    }

    @GetMapping("/detailed")
    public ResponseEntity<List<TransactionDetailedDTO>> getDetailedTransactions(
            @RequestParam String branch,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(detailedTransactionsUseCase.execute(branch, startDate, endDate));
    }
}
