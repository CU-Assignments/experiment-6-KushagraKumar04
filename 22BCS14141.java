import java.util.*;
import java.util.stream.*;

// Easy Level: Sorting Employee Objects
class Employee {
    String name;
    int age;
    double salary;

    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String toString() {
        return name + " - Age: " + age + ", Salary: " + salary;
    }
}

class EasyLevel {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 50000),
            new Employee("Bob", 25, 60000),
            new Employee("Charlie", 35, 55000)
        );
        employees.sort(Comparator.comparingDouble(e -> e.salary));
        employees.forEach(System.out::println);
    }
}

// Medium Level: Filtering and Sorting Students
class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

class MediumLevel {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("John", 82),
            new Student("Emma", 74),
            new Student("Sophia", 90),
            new Student("Liam", 78)
        );
        students.stream()
                .filter(s -> s.marks > 75)
                .sorted(Comparator.comparingDouble(s -> -s.marks))
                .map(s -> s.name)
                .forEach(System.out::println);
    }
}

// Hard Level: Processing a Large Dataset of Products
class Product {
    String name;
    String category;
    double price;

    Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
}

class HardLevel {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", "Electronics", 1200),
            new Product("Phone", "Electronics", 800),
            new Product("Shirt", "Clothing", 50),
            new Product("Jeans", "Clothing", 70),
            new Product("TV", "Electronics", 1500)
        );
        Map<String, List<Product>> groupedByCategory = products.stream()
                .collect(Collectors.groupingBy(p -> p.category));
        groupedByCategory.forEach((category, productList) -> {
            System.out.println(category + " -> " + productList.size() + " products");
        });
        Map<String, Optional<Product>> mostExpensiveByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.category,
                        Collectors.maxBy(Comparator.comparingDouble(p -> p.price))
                ));
        mostExpensiveByCategory.forEach((category, product) ->
                System.out.println("Most Expensive in " + category + ": " + product.get().name));
        double avgPrice = products.stream()
                .mapToDouble(p -> p.price)
                .average()
                .orElse(0);
        System.out.println("Average Price: " + avgPrice);
    }
}
