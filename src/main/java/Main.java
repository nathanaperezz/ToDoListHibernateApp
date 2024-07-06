import entity.Todo;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            transaction.commit();


            int userOption = 0;
            while(userOption != -1) {

                System.out.println("\nEnter a number to perform a function to your to do list.");
                System.out.println("1  - Add new task");
                System.out.println("2  - Delete a task");
                System.out.println("3  - Print the list");
                System.out.println("-1 - Quit");

                userOption = scan.nextInt();

                if (userOption == 1) {
                    UserInputAddItem(entityManager, transaction);
                }
                else if (userOption == 2) {
                    UserInputDeleteItem(entityManager, transaction);
                }
                else if (userOption == 3) {
                    System.out.println("To do: ");
                    PrintToDoList(entityManager);
                }
                else if (userOption != -1) {
                    System.out.println("Error please type a valid option");
                }
            }




        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
    public static void UserInputAddItem (EntityManager entityManager, EntityTransaction transaction) {

        String newItem;

        System.out.print("New task: ");
        scan.nextLine();
        newItem = scan.nextLine();

        AddTask(newItem, entityManager, transaction);
        System.out.println("\n" + newItem + " has been added to your to do list!");
    }

    public static void AddTask(String task, EntityManager entityManager, EntityTransaction transaction) {

        transaction.begin();

        Todo todo = new Todo();
        todo.setTask(task);
        entityManager.merge(todo);

        transaction.commit();
    }


    public static void UserInputDeleteItem (EntityManager entityManager, EntityTransaction transaction) {

        int id = 0;

        System.out.println("Please enter the id of the task you would like to delete: ");
        id = scan.nextInt();

        DeleteItem(id, entityManager, transaction);
    }

    public static void DeleteItem (int id, EntityManager entityManager, EntityTransaction transaction) {

        transaction.begin();

        Query deleteQuery = entityManager.createNativeQuery("DELETE FROM todolist.todo WHERE id = ?");
        deleteQuery.setParameter(1, id);
        int result = deleteQuery.executeUpdate();

        if (result > 0) {
            System.out.println("Task with id " + id + " was deleted.");
        } else {
            System.out.println("No task found with id " + id + ".");
        }

        transaction.commit();
    }

    public static void PrintToDoList (EntityManager entityManager) {
        Query allToDoQuery = entityManager.createNativeQuery("SELECT * FROM todolist.todo", Todo.class);
        List<Todo> todos = allToDoQuery.getResultList();


        for (Todo todo : todos) {
            System.out.println(todo);
        }
    }
}

