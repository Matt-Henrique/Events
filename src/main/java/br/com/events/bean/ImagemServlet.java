package br.com.events.bean;

import java.io.File;


import java.io.IOException;
import java.nio.file.Files;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mateus Henrique Tofanello
 * @version 1.0
 * @since 1.0
 */

@WebServlet("/image")    
public class ImagemServlet extends HttpServlet {

 private static final long serialVersionUID = 1460571643688705941L;

    private String imagePath;

    public void init() throws ServletException {

       this.imagePath = System.getProperty("user.home") + File.separatorChar + "Events" + File.separatorChar;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestedImage = request.getParameter("imagem");

        File image = new File(imagePath, requestedImage);

        String contentType = getServletContext().getMimeType(image.getName());

        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));

        Files.copy(image.toPath(), response.getOutputStream());
    }
}