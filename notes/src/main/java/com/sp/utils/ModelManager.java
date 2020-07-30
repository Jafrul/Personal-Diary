package com.sp.utils;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class ModelManager {

    public static void simpleModel(Model model, String module, String message, String error) {
        model.addAttribute("module", module);
        model.addAttribute("message", message);
        model.addAttribute("error", error);
    }

    public static void formModel(Model model, String module, Object formObject, String message, String error) {
        simpleModel(model, module, message, error);
        model.addAttribute("entity", formObject);
    }

    public static void multiChoiceFormModel(Model model, String module, Object formObject, List multiChoiceList, String message, String error) {
        formModel(model, module, formObject, message, error);
        model.addAttribute("mclist", multiChoiceList);
    }

    public static void pageModel(Model model, String module, Page page, List categories, String message, String error) {
        simpleModel(model, module, message, error);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageIndex", page.getNumber());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("categories", categories);
    }
    public static void formAndListModel(Model model, String module, Object formObject, List list, String message, String error) {
        formModel(model, module, formObject, message, error);
        model.addAttribute("list", list);
    }

}
