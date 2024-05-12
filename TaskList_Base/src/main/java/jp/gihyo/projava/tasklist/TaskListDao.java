package jp.gihyo.projava.tasklist;


import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Objects;

import jp.gihyo.projava.tasklist.HomeController.TaskItem;
import jp.gihyo.projava.tasklist.HomeController.CategoryItem;
import jp.gihyo.projava.tasklist.HomeController.ManagerItem;
import jp.gihyo.projava.tasklist.HomeController.StatusItem;


@Service
public class TaskListDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(TaskItem taskItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasklist");
        insert.execute(param);
    }



    public <categoryStatus> List<TaskItem> findAll(String taskStatus, String taskCategory, String taskManager) {
        String queryBase = "select * from TASKLIST";
        String orderBy = "ORDER BY deadline = '', deadline, start = '', start ASC";

        List<String> conditions = new ArrayList<String>();
        List<Object> params = new ArrayList<Object>();
        List<Integer> paramTypes = new ArrayList<Integer>();
        if (!taskStatus.equals("0")) {
            conditions.add("status = ? ");
            params.add(taskStatus);
            paramTypes.add(java.sql.Types.VARCHAR);
        }
        if (!taskCategory.equals("0")) {
            conditions.add("category = ? ");
            params.add(taskCategory);
            paramTypes.add(java.sql.Types.VARCHAR);
        }
        if (!taskManager.equals("0")) {
            conditions.add("manager = ? ");
            params.add(taskManager);
            paramTypes.add(java.sql.Types.VARCHAR);
        }
        String condition = "";
        if (!conditions.isEmpty()) {
            condition = "WHERE " + String.join("AND ", conditions);
        }

        String query = String.join(" ", queryBase, condition, orderBy);
        int[] argTypes = paramTypes.stream().mapToInt(i -> i).toArray();
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query, params.toArray(), argTypes);
        List<TaskItem> taskItems = result.stream()
                .map((Map<String, Object> row) -> new TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("category").toString(),
                        row.get("manager").toString(),
                        row.get("start").toString(),
                        row.get("deadline").toString(),
                        row.get("description").toString(),
                        row.get("status").toString()))
                .toList();
        return taskItems;
    }

      public int add(String manager) {
        int number = jdbcTemplate.update("INSERT INTO managerlist (manager) SELECT ? WHERE NOT EXISTS (SELECT manager FROM managerlist WHERE manager = ?)", manager, manager);
        return number;
    }

    public int setting_category_add(String category) {
        int number = jdbcTemplate.update("INSERT INTO categorylist (category) SELECT ? WHERE NOT EXISTS (SELECT category FROM categorylist WHERE category = ?)", category, category);
        return number;
    }
  
    public List<ManagerItem> find() {
        String query = "SELECT*FROM managerlist";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<ManagerItem> managerItems = result.stream()
                .map((Map<String, Object> row) -> new ManagerItem(
                        row.get("manager").toString()))
                .toList();
        return managerItems;
    }

    public List<CategoryItem> findcategory() {
        String query = "SELECT*FROM categorylist";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<CategoryItem> categoryItems = result.stream()
                .map((Map<String, Object> row) -> new CategoryItem(
                        row.get("category").toString()))
                .toList();
        return categoryItems;
    }
  
    public String find(String userName) {
        String query = "SELECT user_name_kanji FROM account WHERE user_name = ?";
        String result = jdbcTemplate.queryForObject(query,String.class,userName);
        return result;
    }

    public int delete(String id) {
        int number = jdbcTemplate.update("DELETE FROM tasklist WHERE id = ?", id);
        return number;
    }

    public List deletemanager(String manager) {
        int number = jdbcTemplate.update("DELETE FROM managerlist WHERE manager = ?", manager);
        int Number = jdbcTemplate.update("UPDATE tasklist SET manager = '未選択' WHERE manager = ?", manager);
        List<Integer> deletemanagerlist = new ArrayList<>();
        deletemanagerlist.add(number);
        deletemanagerlist.add(Number);
        return deletemanagerlist;
    }

    public int update(TaskItem taskItem) {
        int number = jdbcTemplate.update(
                "UPDATE tasklist SET task = ?, category = ?, manager = ?, start = ? , deadline = ?, description = ? , status = ? WHERE id = ?",
                taskItem.task(),
                taskItem.category(),
                taskItem.manager(),
                taskItem.start(),
                taskItem.deadline(),
                taskItem.description(),
                taskItem.status(),
                taskItem.id());
        return number;
    }

    public List setting_category_delete(String category) {
        int number = jdbcTemplate.update("DELETE FROM categorylist WHERE category = ?", category);
        int Number = jdbcTemplate.update("UPDATE tasklist SET category = '未選択' WHERE category = ?", category);
        List<Integer> integerList = new ArrayList<>();
        integerList.add(number);
        integerList.add(Number);
        return integerList;
    }

    public List setting_category_update(String category_before, String category_after) {
        int number = jdbcTemplate.update(
               "UPDATE categorylist SET category = ? WHERE category = ? AND NOT EXISTS (SELECT * FROM categorylist WHERE category = ? )",
                category_after,category_before,category_after);
        int Number = jdbcTemplate.update(
                "UPDATE tasklist SET category = ? WHERE category = ?",
                category_after, category_before);
        List<Integer> integerList = new ArrayList<>();
        integerList.add(number);
        integerList.add(Number);

        return integerList;
    }

    public List updatemanager(String manager, String premanager) {
        int number = jdbcTemplate.update(
                "UPDATE managerlist SET manager = ? WHERE manager = ? AND NOT EXISTS (SELECT * FROM managerlist WHERE manager = ? )", manager, premanager, manager);
        int Number = jdbcTemplate.update("UPDATE tasklist SET manager = ? WHERE manager = ?", manager, premanager);
        List<Integer> updatemanagerlist = new ArrayList<>();
        updatemanagerlist.add(number);
        updatemanagerlist.add(Number);
        return updatemanagerlist;
    }

    public List<StatusItem> findStatus() {
        String query = "SELECT * FROM statuslist";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<StatusItem> statusItems = result.stream()
                .map((Map<String, Object> row) -> new StatusItem(
                        row.get("status").toString()))
                .toList();
        return statusItems;
    }

    public int addStatus(String statusAdd) {
        int number = jdbcTemplate.update("INSERT INTO statuslist SELECT ? " +
                "WHERE NOT EXISTS (SELECT status FROM statuslist WHERE status = ?)", statusAdd, statusAdd);
        return number;
    }

    public int updateStatus(String statusBefore, String statusAfter) {
        int number = jdbcTemplate.update("UPDATE statuslist SET status = ? WHERE status = ? AND NOT EXISTS (SELECT * FROM statuslist WHERE status = ? )", statusAfter, statusBefore, statusAfter)
                + jdbcTemplate.update("UPDATE tasklist SET status = ? WHERE status = ?", statusAfter, statusBefore);
        return number;
    }

    public int deleteStatus(String statusBefore, String statusAfter) {
        int number = 0;
        if (!Objects.equals(statusBefore, "未完了") && !Objects.equals(statusBefore, "対応中") && !Objects.equals(statusBefore, "完了")) {
            int number1 = jdbcTemplate.update("DELETE FROM statuslist WHERE status = ?", statusBefore);
            int number2 = jdbcTemplate.update("UPDATE tasklist SET status = ? WHERE status = ?", statusAfter, statusBefore);
            number = number1 + number2;
        }
        return number;
    }
}