

package jp.gihyo.projava.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    record TaskItem(String id, String task, String category, String manager, String start, String deadline,
                    String description, String status) {
    }

    record UserName(String user_name_kanji) {
    }


    record CategoryItem(String category) {
    }

    record ManagerItem(String manager) {
    }

    record StatusItem(String status) {
    }

    private List<ManagerItem> managerItems = new ArrayList<>();
    private List<TaskItem> taskItems = new ArrayList<>();
    private List<CategoryItem> CategoryItems = new ArrayList<>();
    private List<StatusItem> statusItems = new ArrayList<>();
    private final TaskListDao dao;

    @Autowired
    HomeController(TaskListDao dao) {
        this.dao = dao;
    }

    @GetMapping("/list")
    String listItems(Model model,
                     //変数taskStatusを追加,defaultValueはtaskStatusに値が入っていないときに設定する値
                     @RequestParam(name = "taskStatus", defaultValue = "0") String taskStatus,
                     @RequestParam(name = "taskCategory", defaultValue = "0") String taskCategory,
                     @RequestParam(name = "taskManager", defaultValue = "0") String taskManager,
                     @AuthenticationPrincipal UserDetails user)  {
        List<TaskItem> taskItems = dao.findAll(taskStatus, taskCategory, taskManager);
        List<StatusItem> statusItems = dao.findStatus();
        model.addAttribute("taskList", taskItems);
        model.addAttribute("taskStatus", taskStatus);
        model.addAttribute("taskCategory", taskCategory);
        model.addAttribute("taskManager", taskManager);
        List<CategoryItem> CategoryItems = dao.findcategory();
        model.addAttribute("categoryList", CategoryItems);
        List<ManagerItem> managerItems = dao.find();
        model.addAttribute("managerList", managerItems);
        model.addAttribute("statusList", statusItems);
        String userName = dao.find(user.getUsername());
        model.addAttribute("currentUser", userName);
        return "home";
    }

    @PostMapping("/add")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("category") String category,
                   @RequestParam("manager") String manager,
                   @RequestParam("start") String start,
                   @RequestParam("deadline") String deadline,
                   //変数task_statusを追加
                   @RequestParam(name = "taskStatus") String taskStatus,
                   @RequestParam(name = "taskCategory") String taskCategory,
                   @RequestParam(name = "taskManager") String taskManager,
                   @RequestParam("description") String description) throws UnsupportedEncodingException {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, category, manager, start, deadline, description, "未完了");
        dao.add(item);
        String encodedTaskManager = URLEncoder.encode(taskManager, StandardCharsets.UTF_8);
        String encodedTaskCategory = URLEncoder.encode(taskCategory, StandardCharsets.UTF_8);
        String encodedTaskStatus = URLEncoder.encode(taskStatus, StandardCharsets.UTF_8);
        //絞り込み条件が設定された状態の/listに画面遷移
        return String.format("redirect:/list?taskStatus=%s&taskCategory=%s&taskManager=%s", encodedTaskStatus, encodedTaskCategory, encodedTaskManager);
    }

    @PostMapping("/delete")
    String deleteItem(@RequestParam("id") String id,
                      //変数task_statusを追加
                      @RequestParam(name = "taskStatus") String taskStatus,
                      @RequestParam(name = "taskCategory") String taskCategory,
                      @RequestParam(name = "taskManager") String taskManager) throws UnsupportedEncodingException {
        dao.delete(id);
        String encodedTaskManager = URLEncoder.encode(taskManager, StandardCharsets.UTF_8);
        String encodedTaskStatus = URLEncoder.encode(taskStatus, StandardCharsets.UTF_8);
        String encodedTaskCategory = URLEncoder.encode(taskCategory, StandardCharsets.UTF_8);
        //絞り込み条件が設定された状態の/listに画面遷移
        return String.format("redirect:/list?taskStatus=%s&taskCategory=%s&taskManager=%s", encodedTaskStatus, encodedTaskCategory, encodedTaskManager);
    }

    @PostMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("task") String task,
                      @RequestParam("category") String category,
                      @RequestParam("manager") String manager,
                      @RequestParam("start") String start,
                      @RequestParam("deadline") String deadline,
                      @RequestParam("description") String description,
                      @RequestParam("status") String status,
                      //変数taskStatusを追加
                      @RequestParam(name = "taskStatus") String taskStatus,
                      @RequestParam(name = "taskCategory") String taskCategory,
                      @RequestParam(name = "taskManager") String taskManager) throws UnsupportedEncodingException {
        TaskItem taskItem = new TaskItem(id, task, category, manager, start, deadline, description, status);
        dao.update(taskItem);
        String encodedTaskManager = URLEncoder.encode(taskManager, StandardCharsets.UTF_8);
        String encodedTaskStatus = URLEncoder.encode(taskStatus, StandardCharsets.UTF_8);
        String encodedTaskCategory = URLEncoder.encode(taskCategory, StandardCharsets.UTF_8);
        //絞り込み条件が設定された状態の/listに画面遷移
        return String.format("redirect:/list?taskStatus=%s&taskCategory=%s&taskManager=%s", encodedTaskStatus, encodedTaskCategory, encodedTaskManager);
    }

    @GetMapping("/setting")
    String setting(Model model,
                   @RequestParam(name = "taskStatus", defaultValue = "0") String taskStatus,
                   @RequestParam(name = "taskCategory", defaultValue = "0") String taskCategory,
                   @RequestParam(name = "taskManager", defaultValue = "0") String taskManager) {
        model.addAttribute("taskStatus", taskStatus);
        model.addAttribute("taskCategory", taskCategory);
        model.addAttribute("taskManager", taskManager);
        List<TaskItem> taskItems = dao.findAll("0", "0", "0");
        model.addAttribute("taskList",taskItems);
        List<ManagerItem> managerItems = dao.find();
        model.addAttribute("managerList", managerItems);
        List<CategoryItem> categoryItems = dao.findcategory();
        model.addAttribute("categoryList", categoryItems);
        List<StatusItem> statusItems = dao.findStatus();
        model.addAttribute("statusList", statusItems);
        return "setting";
    }

    @GetMapping("/setting_status_add")
    String setting_status_add(Model model,
                              @RequestParam("setting_status_add") String statusAdd) {
        dao.addStatus(statusAdd);
        return "redirect:/setting";
    }

    @GetMapping("/setting_status_update")
    String setting_status_update(Model model,
                                 @RequestParam("status_before") String statusBefore,
                                 @RequestParam("status_after") String statusAfter) {

        dao.updateStatus(statusBefore, statusAfter);
        return "redirect:/setting";
    }

    @GetMapping("/setting_status_delete")
    String setting_status_delete(Model model,
                                 @RequestParam("status_delete_before") String statusBefore,
                                 @RequestParam("status_delete_after") String statusAfter) {
        dao.deleteStatus(statusBefore, statusAfter);
        return "redirect:/setting";
    }

    @GetMapping("/addsettingmanager")
    String settingmanager(@RequestParam("setting_add_manager") String manager,
                          @RequestParam(name = "taskStatus") String taskStatus,
                          @RequestParam(name = "taskCategory") String taskCategory,
                          @RequestParam(name = "taskManager") String taskManager) {
        /*ManagerItem managerItem = new ManagerItem(manager);*/
        dao.add(manager);
        String encodedTaskManager = URLEncoder.encode(taskManager, StandardCharsets.UTF_8);
        String encodedTaskCategory = URLEncoder.encode(taskCategory, StandardCharsets.UTF_8);
        String encodedTaskStatus = URLEncoder.encode(taskStatus, StandardCharsets.UTF_8);
        return String.format("redirect:/setting?taskStatus=%s&taskCategory=%s&taskManager=%s", taskStatus, taskCategory, encodedTaskManager);
    }

    @GetMapping("/updatesettingmanager")
    String updatesettingmanager(@RequestParam("setting_manager_update") String manager,
                                @RequestParam("setting_manager_preupdate") String premanager,
                                @RequestParam(name = "taskStatus") String taskStatus,
                                @RequestParam(name = "taskCategory") String taskCategory,
                                @RequestParam(name = "taskManager") String taskManager) {
        dao.updatemanager(manager, premanager);
        String encodedTaskManager = URLEncoder.encode(taskManager, StandardCharsets.UTF_8);
        String encodedTaskCategory = URLEncoder.encode(taskCategory, StandardCharsets.UTF_8);
        String encodedTaskStatus = URLEncoder.encode(taskStatus, StandardCharsets.UTF_8);
        return String.format("redirect:/setting?taskStatus=%s&taskCategory=%s&taskManager=%s", taskStatus, taskCategory, encodedTaskManager);
    }

    @GetMapping("/deletesettingmanager")
    String deletesettingmanager(@RequestParam("setting_manager_delete") String manager,
                                @RequestParam(name = "taskStatus") String taskStatus,
                                @RequestParam(name = "taskCategory") String taskCategory,
                                @RequestParam(name = "taskManager") String taskManager) {
        dao.deletemanager(manager);
        String encodedTaskManager = URLEncoder.encode(taskManager, StandardCharsets.UTF_8);
        String encodedTaskCategory = URLEncoder.encode(taskCategory, StandardCharsets.UTF_8);
        String encodedTaskStatus = URLEncoder.encode(taskStatus, StandardCharsets.UTF_8);
        return String.format("redirect:/setting?taskStatus=%s&taskCategory=%s&taskManager=%s", taskStatus, taskCategory, encodedTaskManager);
    }

    @GetMapping("/setting_category_add")
    String setting_category_add(@RequestParam("category") String category) {
        dao.setting_category_add(category);
        return "redirect:/setting";
    }

    @GetMapping("/setting_category_delete")
    String deleteItem(@RequestParam("category") String category) {
        dao.setting_category_delete(category);
        return "redirect:/setting";
    }

    @GetMapping("/setting_category_update")
    String updateItem(@RequestParam("category_before") String category_before,
                      @RequestParam("category_after") String category_after) {

        dao.setting_category_update(category_before, category_after);
        //絞り込み条件が設定された状態の/listに画面遷移
        return "redirect:/setting";
    }
}

