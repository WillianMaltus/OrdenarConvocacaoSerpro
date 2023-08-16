package br.com.lertxt.services;

import br.com.lertxt.models.Candidato;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class LerTxtService {

    public void processarListas() throws IOException, ParseException {
        List<Candidato> listaAmplaConcorrencia = lerTxt("lista-convocados-serpro.txt");
        List<Candidato> listaCotas = lerTxt("lista-convocados-serpro-cotas.txt");
        List<Candidato> listaPcd = lerTxt("lista-convocados-serpro-pcd.txt");

        orderList(listaAmplaConcorrencia);
        orderList(listaCotas);
        orderList(listaPcd);

        saveCandidatosToFile(listaAmplaConcorrencia, "lista-convocados-serpro-ordenada.txt");
        saveCandidatosToFile(listaCotas, "lista-convocados-serpro-cotas-ordenada.txt");
        saveCandidatosToFile(listaPcd, "lista-convocados-serpro-pcd-ordenada.txt");
    }

    public List<Candidato> lerTxt(String nomeArquivo) throws IOException, ParseException {

        ClassPathResource resource = new ClassPathResource(nomeArquivo);
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<Candidato> candidatos = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");

            Candidato candidato = new Candidato();
            candidato.setInscricao(parts[0]);
            candidato.setNome(parts[1]);
            candidato.setNotaFinalPortugues(getDoubleValue(parts[2], candidato.getNome()));
            candidato.setNumeroAcertoPortugues(getIntegerValue(parts[3], candidato.getNome()));
            candidato.setNotaFinalIngles(getDoubleValue(parts[4], candidato.getNome()));
            candidato.setNumeroAcertoIngles(getIntegerValue(parts[5], candidato.getNome()));
            candidato.setNotaFinalEstatisticaProbablidade(getDoubleValue(parts[6], candidato.getNome()));
            candidato.setNumeroAcertoEstatisticaProbablidade(getIntegerValue(parts[7], candidato.getNome()));
            candidato.setNotaFinalRaciocinioLogico(getDoubleValue(parts[8], candidato.getNome()));
            candidato.setNumeroAcertoRaciocinioLogico(getIntegerValue(parts[9], candidato.getNome()));
            candidato.setNotaFinalLegislacao(getDoubleValue(parts[10], candidato.getNome()));
            candidato.setNumeroAcertoLegislacao(getIntegerValue(parts[11], candidato.getNome()));
            candidato.setNotaFinalConhecimentosBasicos(getDoubleValue(parts[12], candidato.getNome()));
            candidato.setNumeroAcertoConhecimentosBasicos(getIntegerValue(parts[13], candidato.getNome()));
            candidato.setNotaFinalConhecimentosEspecificos(getDoubleValue(parts[14], candidato.getNome()));
            candidato.setNumeroAcertoConhecimentosEspecificos(getIntegerValue(parts[15], candidato.getNome()));
            candidato.setNotaFinal(getIntegerValue(parts[16], candidato.getNome()));
            candidatos.add(candidato);
        }

        return candidatos;
    }

    public void orderList(List<Candidato> candidatos) {
        Collections.sort(candidatos);
    }

    public void saveCandidatosToFile(List<Candidato> candidatos, String fileName) throws IOException {
        String directoryPath = "C:\\temp";
        String filePath = directoryPath + "\\" + fileName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório (e subdiretórios, se necessário)
        }

        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < candidatos.size(); i++) {
                Candidato candidato = candidatos.get(i);
                String line = String.format("%d) %s - %s = %d",
                        i + 1,
                        candidato.getInscricao(),
                        candidato.getNome(),
                        candidato.getNotaFinal());
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public Double getDoubleValue(String valor, String nome) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);

        try {
            return decimalFormat.parse(valor.replace(" ", "")).doubleValue();
        } catch (ParseException e) {
            log.error("Erro ao converter {} do candidato: {}", valor, nome);
            return null;
        }
    }

    public Integer getIntegerValue(String valor, String nome) {
        try {
            return Integer.parseInt(valor.replace(" ", "").replace(".", ""));
        } catch (NumberFormatException e) {
            log.error("Erro ao converter {} do candidato: {}", valor, nome);
            return null;
        }
    }
}
