package com.example.schollink.service;

import com.example.schollink.Dto.DisciplinaNotasDto;
import com.example.schollink.Dto.HistoricoAlunoDto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class BoletimPdfService {

    public ByteArrayInputStream gerarBoletimPdf(HistoricoAlunoDto historico) {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            BaseFont bf = BaseFont.createFont(
                BaseFont.HELVETICA,
                BaseFont.CP1252,
                BaseFont.EMBEDDED
            );

            try {
                ClassPathResource imgFile = new ClassPathResource("static/images/Logo.png");
                byte[] imageBytes = imgFile.getInputStream().readAllBytes();

                Image logo = Image.getInstance(imageBytes);

                logo.scaleToFit(90, 90);
                logo.setAbsolutePosition(
                document.getPageSize().getWidth() - 110,
                document.getPageSize().getHeight() - 110  
            );
                document.add(logo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Font tituloFont = new Font(bf, 22, Font.BOLD, new Color(0, 70, 140));
            Font etiquetaFont = new Font(bf, 15, Font.BOLD, Color.BLACK);
            Font subtituloFont = new Font(bf, 11, Font.BOLD, Color.BLACK);
            Font textoFont = new Font(bf, 10, Font.NORMAL, Color.BLACK);

            Paragraph titulo = new Paragraph("BOLETIM ESCOLAR - 2025", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(18);
            document.add(titulo);

            document.add(new Paragraph("Nome: " + historico.getNomeAluno(), etiquetaFont));
            document.add(new Paragraph("Matrícula: " + historico.getMatricula(), etiquetaFont));
            document.add(new Paragraph("Turma: " + historico.getTurma(), etiquetaFont));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(22);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = new float[]{
                    2.5f, 0.6f, 0.6f, 0.6f, 0.6f, 0.8f,
                    0.6f, 0.6f, 0.6f, 0.6f, 0.8f,
                    0.6f, 0.6f, 0.6f, 0.6f, 0.8f,
                    0.6f, 0.6f, 0.6f, 0.6f, 0.8f,
                    1.0f
            };
            table.setWidths(columnWidths);

            PdfPCell headerDisciplina = new PdfPCell(new Phrase("DISCIPLINA", subtituloFont));
            headerDisciplina.setBackgroundColor(new Color(190, 210, 255));
            headerDisciplina.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerDisciplina.setRowspan(2);
            table.addCell(headerDisciplina);

            adicionarBimestreCabecalho(table, "1° BIM", subtituloFont);
            adicionarBimestreCabecalho(table, "2° BIM", subtituloFont);
            adicionarBimestreCabecalho(table, "3° BIM", subtituloFont);
            adicionarBimestreCabecalho(table, "4° BIM", subtituloFont);

            PdfPCell headerFinal = new PdfPCell(new Phrase("MÉDIA FINAL", subtituloFont));
            headerFinal.setBackgroundColor(new Color(190, 210, 255));
            headerFinal.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerFinal.setRowspan(2);
            table.addCell(headerFinal);

            for (int i = 0; i < 4; i++) {
                table.addCell(cabecalhoCelula("P1", subtituloFont));
                table.addCell(cabecalhoCelula("P2", subtituloFont));
                table.addCell(cabecalhoCelula("AC", subtituloFont));
                table.addCell(cabecalhoCelula("AF", subtituloFont));
                table.addCell(cabecalhoCelula("MÉD.", subtituloFont));
            }

            for (DisciplinaNotasDto d : historico.getDisciplinas()) {

                table.addCell(celula(d.getNomeDisciplina(), textoFont, null));

                adicionarNotasBimestre(table, d.getPrimeiroBimestre(), d.getMediaPrimeiroBi(), textoFont);
                adicionarNotasBimestre(table, d.getSegundoBimestre(), d.getMediaSegundoBi(), textoFont);
                adicionarNotasBimestre(table, d.getTerceiroBimestre(), d.getMediaTerceiroBi(), textoFont);
                adicionarNotasBimestre(table, d.getQuartoBimestre(), d.getMediaQuartoBi(), textoFont);

                table.addCell(
                    celula(
                        formatarNota(d.getMediaFinal()),
                        textoFont,
                        corMedia(d.getMediaFinal())
                    )
                );
            }

            document.add(table);

            Paragraph rodape = new Paragraph(
                "Emitido automaticamente pelo sistema SchoolLink.",
                new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY)
            );
            rodape.setAlignment(Element.ALIGN_RIGHT);
            document.add(rodape);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private PdfPCell celula(String texto, Font font, Color bg) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        if (bg != null) cell.setBackgroundColor(bg);
        return cell;
    }

    private PdfPCell cabecalhoCelula(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(210, 225, 255));
        return cell;
    }

    private void adicionarBimestreCabecalho(PdfPTable table, String titulo, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(titulo, font));
        cell.setColspan(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new Color(170, 200, 255));
        table.addCell(cell);
    }

    private void adicionarNotasBimestre(
            PdfPTable table,
            Map<String, Double> notas,
            Double media,
            Font font
    ) {
        table.addCell(celula(formatarNota(pegar(notas, "P1")), font, null));
        table.addCell(celula(formatarNota(pegar(notas, "P2")), font, null));
        table.addCell(celula(formatarNota(pegar(notas, "AC")), font, null));
        table.addCell(celula(formatarNota(pegar(notas, "AF")), font, null));

        table.addCell(celula(formatarNota(media), font, corMedia(media)));
    }

    private Double pegar(Map<String, Double> map, String key) {
        return map == null ? null : map.get(key);
    }

    private String formatarNota(Double nota) {
        return nota == null ? "-" : String.format("%.1f", nota);
    }

    private Color corMedia(Double media) {
        if (media == null) return null;
        return media >= 6.0 ? new Color(0, 160, 0, 90) : new Color(200, 0, 0, 90);
    }
}