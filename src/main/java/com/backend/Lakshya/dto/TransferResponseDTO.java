package com.backend.Lakshya.dto;

public class TransferResponseDTO {
    private TransactionResponseDTO sourceTransaction;
    private TransactionResponseDTO destinationTransaction;

    // Getters & Setters
    public TransactionResponseDTO getSourceTransaction() { return sourceTransaction; }
    public void setSourceTransaction(TransactionResponseDTO sourceTransaction) { this.sourceTransaction = sourceTransaction; }

    public TransactionResponseDTO getDestinationTransaction() { return destinationTransaction; }
    public void setDestinationTransaction(TransactionResponseDTO destinationTransaction) { this.destinationTransaction = destinationTransaction; }
}
