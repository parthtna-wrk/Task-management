package com.argus.Project.Management.Backend.repositories;

import com.argus.Project.Management.Backend.DTO.CountType;
import com.argus.Project.Management.Backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.validation.Validator;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query(value = "select * from task order by due_date desc", nativeQuery = true)
    public List<Task> getAllTaskDueDateDesc();

    @Query(value = "select new com.argus.Project.Management.Backend.DTO.CountType((CAST(COUNT(*) AS FLOAT) / (select COUNT(*) from Task)) *100,type) from Task GROUP BY type")
    public List<CountType> getPercentageGroupByType();

}
