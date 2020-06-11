/**
 * @Project base-module
 * @Package com.cds.base.module.progress.controller
 * @Class ProgressController.java
 * @Date Jun 8, 2020 5:54:19 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.module.progress.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RestController
@RequestMapping(value = "/module/progress")
public class ProgressController {
    @Autowired
    private ProgressListener progressListener;

    @GetMapping("/detail.htm")
    public Progress getProgress(@RequestParam(name = "name", required = false) String name) throws IOException {
        return progressListener.getProgress(name);
    }
}
