package com.paf.exercise.exercise.dto.ctrader;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Position {
    private Symbol symbol;
    private TradeType tradeType;
    private Double quantity;
    private String label;
    private String comment;
    private Double entryPrice;
    private Double takeProfit;
    private Double stopLoss;
    private Boolean running;


    public Position() {
    }

    public Position(Symbol symbol, TradeType tradeType, Double quantity, String label, String comment, Double entryPrice, Double stopLoss, Double takeProfit) {
        this.symbol = symbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.label = label;
        this.comment = comment;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(Double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public Double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(Double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public Double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(Double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Position{" +
                "symbol=" + symbol +
                ", tradeType=" + tradeType +
                ", quantity=" + quantity +
                ", label='" + label + '\'' +
                ", entryPrice=" + entryPrice +
                ", takeProfit=" + takeProfit +
                ", stopLoss=" + stopLoss +
                ", running=" + running +
                '}';
    }
}
