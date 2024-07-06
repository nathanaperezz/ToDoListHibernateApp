package entity;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "AllToDo", query = "SELECT t FROM Todo t WHERE t.task = :task")
public class Todo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "task")
    private String task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != todo.id) return false;
        if (task != null ? !task.equals(todo.task) : todo.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }

    @Override
    public String  toString() {
        return "id = " + id + ", task = '" + task + '\'';
    }
}
