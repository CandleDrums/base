/**
 * @Project base-module
 * @Package com.cds.base.module.progress.controller
 * @Class ProgressController.java
 * @Date Jun 8, 2020 5:54:19 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.module.progress.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cds.base.module.progress.listener.ProgressListener;
import com.cds.base.module.progress.model.Progress;

/**
 * @Description 进度公共请求
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 8, 2020 5:54:19 PM
 */
@CrossOrigin
@RestController(value = "/common")
public class ProgressController {
    @Autowired
    private ProgressListener progressListener;

    @GetMapping("/progress.htm")
    public Progress getProgress(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws IOException {
        return progressListener.getProgress();
    }
}
