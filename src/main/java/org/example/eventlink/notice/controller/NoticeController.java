package org.example.eventlink.notice.controller;

import lombok.RequiredArgsConstructor;
import org.example.eventlink.notice.dto.DeleteResponse;
import org.example.eventlink.notice.dto.NoticeRequest;
import org.example.eventlink.notice.dto.NoticeResponse;
import org.example.eventlink.notice.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public NoticeResponse createNotice(@RequestBody NoticeRequest request){
        return noticeService.createNotice(request);
    }

    @GetMapping
    public List<NoticeResponse> getNotices(){
        return noticeService.getNotices();
    }

    @PatchMapping("/{id}")
    public NoticeResponse updateNotice(@PathVariable Long id, @RequestBody NoticeRequest request){
        return noticeService.updateNotice(id,request);
    }

    @DeleteMapping("/{id}")
    public DeleteResponse deleteNotice(@PathVariable Long id){
        return noticeService.deleteNotice(id);
    }
}
