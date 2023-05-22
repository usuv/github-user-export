/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maybank.yusuf.controllers;

import com.maybank.yusuf.models.ExportHistory;
import com.maybank.yusuf.models.User;
import com.maybank.yusuf.repositories.ExportHistoryRepository;
import com.maybank.yusuf.services.ExportService;
import com.maybank.yusuf.services.GithubService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 62878
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final GithubService githubService;
    private final ExportService exportService;
    private final ExportHistoryRepository exportHistoryRepository;

    @Autowired
    public UserController(GithubService githubService, ExportService exportService, ExportHistoryRepository exportHistoryRepository) {
        this.githubService = githubService;
        this.exportService = exportService;
        this.exportHistoryRepository = exportHistoryRepository;
    }

    @GetMapping
    public List<User> searchUsers(@RequestParam String query) {
        return githubService.searchGithubUsers(query);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportUsersToPdf(@RequestParam String query) throws IOException {
        List<User> users = githubService.searchGithubUsers(query);
        byte[] pdfBytes = exportService.generatePdf(users);
        String fileName = "users_export_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".pdf";

        // Save export history
        ExportHistory exportHistory = new ExportHistory();
        exportHistory.setFileName(fileName);
        exportHistory.setFilePath(fileName);
        exportHistory.setTimestamp(LocalDateTime.now());
        // Set other relevant fields
        exportHistoryRepository.save(exportHistory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/export-history")
    public List<ExportHistory> getExportHistory() {
        return exportHistoryRepository.findAll();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable Long id) throws IOException {
        Optional<ExportHistory> exportHistory = exportHistoryRepository.findById(id);
        if (exportHistory.isPresent()) {
            String filePath = exportHistory.get().getFilePath();

            // Read the PDF file from the file system or storage
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                // Create an InputStreamResource from the PDF file data
                InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile)) {
                    @Override
                    public String getFilename() {
                        return "export.pdf";
                    }
                };

                // Set the headers and return the ResponseEntity with the PDF resource
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.pdf");

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
