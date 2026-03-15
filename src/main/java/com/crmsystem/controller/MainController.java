package com.crmsystem.controller;

import com.crmsystem.db.DbConnector;
import com.crmsystem.model.ApplicationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    /**
     * Главная страница со списком заявок
     * @param model
     * @return - редирект на главную
     */
    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("applications", DbConnector.getAllApplications());

        return "pages/index";
    }

    /**
     * Главная страница со списком заявок
     * @param model
     * @return - редирект на главную
     */
    @GetMapping("/new-applications")
    public String getNewApplicationsPage(Model model) {
        model.addAttribute("applications", DbConnector.getUnhandledApplications());

        return "pages/index";
    }

    /**
     * Главная страница со списком заявок
     * @param model
     * @return - редирект на главную
     */
    @GetMapping("/handled-applications")
    public String getHandledApplicationsPage(Model model) {
        model.addAttribute("applications", DbConnector.getHandledApplications());

        return "pages/index";
    }

    /**
     * Страница добавления заявки
     * @return - возвращаем страницу добавления
     */
    @GetMapping("/add-application")
    public String getAddApplicationPage() {
        return "pages/add-application";
    }

    /**
     * Сохранения новой заявки
     * @return - редирект на главную
     */
    @PostMapping("/add-application")
    public String addNewApplication(ApplicationRequest application) {
        DbConnector.addApplication(application);

        return "redirect:/";
    }

    @GetMapping("/applications/{id}")
    public String getApplicationDetailsPage(@PathVariable Long id, Model model) {
        ApplicationRequest applicationRequest = DbConnector.getApplicationById(id);

        System.out.println(applicationRequest.toString());

        model.addAttribute("application", applicationRequest);

        return "pages/application-details";
    }

    /**
     * Обновление заявки
     * @param application обновлённая заявка
     * @return редирект на страницу деталей
     */
    @PostMapping("/applications/update")
    public String updateApplication(ApplicationRequest application) {
        DbConnector.updateApplication(application);

        return "redirect:/";
    }

    /**
     * Удаление заявки
     * @param applicationRequest идентификатор заявки
     * @return редирект на главную
     */
    @PostMapping("/applications/delete")
    public String deleteApplication(ApplicationRequest applicationRequest) {

        System.out.println(applicationRequest.toString());
        DbConnector.deleteApplication(applicationRequest.getId());

        return "redirect:/";
    }
}
