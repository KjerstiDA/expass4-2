package no.hvl.dat250.jpa.basicexample;

import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Todos {
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;
    //private ArrayList todos = new ArrayList();

    public String getAll(){
        ArrayList todos = new ArrayList();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("select t from Todo t");
        List<Todo> todoList = q.getResultList();
        em.close();
        for (Todo todo : todoList) {
            todos.add(todo);
        }
        Gson gson = new Gson();

        String jsonInString = gson.toJson(todos);

        return jsonInString;
    }


    public String getID(Long id){
        ArrayList todos = new ArrayList();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("select t from Todo t where t.id = " + id);
        List<Todo> todoList = q.getResultList();
        em.close();
        Gson gson = new Gson();
        for (Todo todo : todoList) {
            todos.add(todo);
        }
        String jsonInString = gson.toJson(todos);

        return jsonInString;
      }

    public String updateID(Long id, Todo todo){
        ArrayList todos = new ArrayList();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        String description = todo.getDescription();
        String summary = todo.getSummary();

        em.getTransaction().begin();
        Query q = em.createQuery("update Todo t set t.description ='"
                + description + "', t.summary ='" + summary + "' where t.id = " + id);
        q.executeUpdate();
        em.getTransaction().commit();

        Query q2 = em.createQuery("select t from Todo t where t.id = " + id);
        List<Todo> todoList = q2.getResultList();
        Gson gson = new Gson();
        em.close();

        todos.add(todoList.get(0));

        String jsonInString = gson.toJson(todos);
        return jsonInString;
    }

    public String create(Todo todo){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        String description = todo.getDescription();
        String summary = todo.getSummary();


        em.getTransaction().begin();
        Todo t = new Todo();
        t.setDescription(description);
        t.setSummary(summary);
        em.persist(t);
        em.getTransaction().commit();
        em.close();

        Gson gson = new Gson();
        String jsonInString = gson.toJson(t);
        return jsonInString;
    }
    String toJson () {

        Gson gson = new Gson();

        String jsonInString = gson.toJson(this);

        return jsonInString;
    }
}
