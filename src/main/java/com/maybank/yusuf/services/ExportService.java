/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maybank.yusuf.services;

import com.maybank.yusuf.models.ExportHistory;
import com.maybank.yusuf.models.User;
import com.maybank.yusuf.repositories.ExportHistoryRepository;
import com.maybank.yusuf.repositories.UserRepository;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 62878
 */
@Service
public class ExportService {

    private final UserRepository userRepository;
    private final ExportHistoryRepository exportHistoryRepository;

    @Autowired
    public ExportService(UserRepository userRepository, ExportHistoryRepository exportHistoryRepository) {
        this.userRepository = userRepository;
        this.exportHistoryRepository = exportHistoryRepository;
    }

    public byte[] generatePdf(List<User> users) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;
        PDType1Font fontNormal = PDType1Font.HELVETICA;
        
        int fontSize = 12;

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        float bottomMargin = 70;

        float cellHeight = 20;
        float tableRowHeight = 15;
        int rowsPerPage = (int) ((yStart - bottomMargin) / tableRowHeight);
        int currentPage = 0;
        int start = 0;
        int end = Math.min(users.size(), start + rowsPerPage);

        while (start < users.size()) {
            contentStream.setFont(fontBold, fontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Username");
            contentStream.newLineAtOffset(tableWidth * 0.25f, 0);
            contentStream.showText("Name");
            contentStream.newLineAtOffset(tableWidth * 0.25f, 0);
            contentStream.showText("Email");
            contentStream.newLineAtOffset(tableWidth * 0.25f, 0);
            contentStream.showText("Role");
            contentStream.endText();

            yPosition -= cellHeight;

            contentStream.setFont(fontNormal, fontSize);
            for (int i = start; i < end; i++) {
                User user = users.get(i);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(user.getUsername() != null ? user.getUsername() : "");
                contentStream.newLineAtOffset(tableWidth * 0.25f, 0);
                contentStream.showText(user.getName() != null ? user.getName() : "");
                contentStream.newLineAtOffset(tableWidth * 0.25f, 0);
                contentStream.showText(user.getEmail() != null ? user.getEmail() : "");
                contentStream.newLineAtOffset(tableWidth * 0.25f, 0);
                contentStream.showText(user.getRole() != null ? user.getRole() : "");
                contentStream.endText();

                yPosition -= cellHeight;
            }

            contentStream.stroke();

            if (end < users.size()) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                currentPage++;
                yPosition = yStart;
                start = end;
                end = Math.min(users.size(), start + rowsPerPage);
            } else {
                break;
            }
        }

        contentStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        return outputStream.toByteArray();
    }

    public List<ExportHistory> getExportHistory() {
        return exportHistoryRepository.findAll();
    }

    public ExportHistory getExportById(Long exportId) {
        return exportHistoryRepository.findById(exportId).orElse(null);
    }

    public byte[] getExportFile(Long exportId) throws IOException {
        ExportHistory export = exportHistoryRepository.findById(exportId).orElse(null);
        if (export == null) {
            throw new FileNotFoundException("Export not found");
        }

        File exportFile = new File(export.getFilePath());
        if (!exportFile.exists()) {
            throw new FileNotFoundException("Export file not found");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileInputStream inputStream = new FileInputStream(exportFile);
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();

        return outputStream.toByteArray();
    }
}
