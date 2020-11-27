package com.paf.exercise.exercise.entity;

import com.paf.exercise.exercise.util.Symbol;
import com.paf.exercise.exercise.util.TradeType;

import javax.persistence.*;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Symbol symbol;
    @Column
    private TradeType tradeType;
    @Column
    private Double quantity;
    @Column
    private String label;
    @Column
    private Double entryPrice;
    @Column
    private Double takeProfit;
    @Column
    private Double stopLoss;
    @Column
    private Boolean running;


    public Position() {
    }

    public Position(Symbol symbol, TradeType tradeType, Double quantity, String label, Double entryPrice, Double takeProfit, Double stopLoss, Boolean running) {
        this.id = id;
        this.symbol = symbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.label = label;
        this.entryPrice = entryPrice;
        this.takeProfit = takeProfit;
        this.stopLoss = stopLoss;
        this.running = running;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
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

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
