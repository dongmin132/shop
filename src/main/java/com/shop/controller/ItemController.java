package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.serivce.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()) {        //상품 등록 시 필수 값이 없다면 다시 상품 등록 페이지로 전환함
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {  //상품 등록 시 첫번째 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환됨.
                                                //상품의 첫번째 이미지는 메인 페이지에서 보여줄 상품 이미지로 사용하기 위해서 필수 값으로 지정함.
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList); // 상품 저장 로직을 호출함. 매개 변수로 상품 정보와 상품 이미지 정보를 담고있는 itemImgFileList를 넘겨줌
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다");
            return "item/itemForm";
        }

        return "redirect:/";
    }
}
