package com.example.projetojavafx.Modelo;

public class Viagem {
    private String motorista;
    private String partida;
    private String destino;
    private String dataViagem;
    private String estado;

    public Viagem(String motorista, String partida, String destino, String dataViagem, String estado) {
        this.motorista = motorista;
        this.partida = partida;
        this.destino = destino;
        this.dataViagem = dataViagem;
        this.estado = estado;
    }

    public String getMotorista() { return motorista; }
    public String getPartida() { return partida; }
    public String getDestino() { return destino; }
    public String getDataViagem() { return dataViagem; }
    public String getEstado() { return estado; }
}
