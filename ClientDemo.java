package com.klef.jfsd.exam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            // Step 1: Create and save new projects
            session.beginTransaction();

            Project project1 = new Project("Project A", 12, 500000, "Alice");
            Project project2 = new Project("Project B", 6, 300000, "Bob");
            Project project3 = new Project("Project C", 18, 1000000, "Charlie");

            session.save(project1);
            session.save(project2);
            session.save(project3);

            session.getTransaction().commit();

            // Step 2: Aggregate queries using Criteria API

            session = factory.getCurrentSession();
            session.beginTransaction();

            // Create Criteria for Project entity
            Criteria criteria = session.createCriteria(Project.class);

            // Perform aggregate operations on the Budget column
            criteria.setProjection(Projections.rowCount());  // COUNT
            System.out.println("Total number of projects: " + criteria.uniqueResult());

            criteria.setProjection(Projections.max("budget"));  // MAX
            System.out.println("Maximum budget: " + criteria.uniqueResult());

            criteria.setProjection(Projections.min("budget"));  // MIN
            System.out.println("Minimum budget: " + criteria.uniqueResult());

            criteria.setProjection(Projections.sum("budget"));  // SUM
            System.out.println("Total budget: " + criteria.uniqueResult());

            criteria.setProjection(Projections.avg("budget"));  // AVG
            System.out.println("Average budget: " + criteria.uniqueResult());

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
