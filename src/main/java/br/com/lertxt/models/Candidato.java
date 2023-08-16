package br.com.lertxt.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Candidato implements Comparable<Candidato>{

    private String inscricao;
    private String nome;
    private Double notaFinalPortugues;
    private Integer numeroAcertoPortugues;
    private Double notaFinalIngles;
    private Integer numeroAcertoIngles;
    private Double notaFinalEstatisticaProbablidade;
    private Integer numeroAcertoEstatisticaProbablidade;
    private Double notaFinalRaciocinioLogico;
    private Integer numeroAcertoRaciocinioLogico;
    private Double notaFinalLegislacao;
    private Integer numeroAcertoLegislacao;
    private Double notaFinalConhecimentosBasicos;
    private Integer numeroAcertoConhecimentosBasicos;
    private Double notaFinalConhecimentosEspecificos;
    private Integer numeroAcertoConhecimentosEspecificos;
    private Integer notaFinal;

    @Override
    public int compareTo(Candidato candidatoB) {
        return candidatoB.notaFinal - this.notaFinal;
    }
}
