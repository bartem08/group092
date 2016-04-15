package com.interview.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Serhii Levchynskyi on 10.04.2016.
 */
@Controller
@RequestMapping("/web")
public class CandidatePhotoUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(CandidatePhotoUploadController.class);

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(@RequestParam("candidateId") String candidateId,
                                          @RequestParam("file") MultipartFile file,
                                          HttpServletRequest request) {
        LOG.info("In the UPLOAD Controller");
        String message;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = request.getSession().getServletContext().getRealPath("/");
                System.out.println(rootPath);

                // Creating the directory to store file

                File dir = new File(rootPath + File.separator + "uploads");
                LOG.info(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + "candidatePhoto.jpg");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                String fileLocation = serverFile.getAbsolutePath();

                LOG.info("Server File Location=" + fileLocation);

                message = "You successfully uploaded file under name: " + "candidatePhoto.jpg" + "\n" +"path: " + fileLocation;
                ModelAndView mav = new ModelAndView("profilesAndTemplate/candidateProfile");
                mav.addObject("id", candidateId);
                mav.addObject("name", "candidatePhoto.jpg");
                mav.addObject("message", message);
                return mav;
            } catch (Exception e) {
                message = "You failed to upload " + "candidatePhoto.jpg";
                return new ModelAndView("profilesAndTemplate/candidateProfile", "message", message);
            }
        } else {
            message = "You failed to upload " + "candidatePhoto.jpg"
                    + " because the file was empty.";
            return new ModelAndView("profilesAndTemplate/candidateProfile", "message", message);
        }
    }
}
