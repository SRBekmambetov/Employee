import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Read {

    private static List<Employee> listEmployee = new ArrayList<>();
    private static Map<String, List<Integer>> averSalaryMap = new HashMap<>();

    private static void reader() {
        BufferedReader readFromFile = null;
        String line;
        try {
            readFromFile = new BufferedReader(new FileReader("sample.txt"));
            while ((line = readFromFile.readLine()) != null) {
                Employee newEmployee = new Employee();
                String[] arr = line.split("\\|");
                newEmployee.setId(Integer.parseInt(arr[0]));
                newEmployee.setFirstName(arr[1]);
                newEmployee.setSecondName(arr[2]);
                newEmployee.setMiddleName(arr[3]);
                newEmployee.setDepartment(arr[4]);
                newEmployee.setSalary(Integer.parseInt(arr[5]));
                listEmployee.add(newEmployee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readFromFile != null) {
                try {
                    readFromFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void salaryDepartment() {
        for (Employee employee: listEmployee) {
            String department = employee.getDepartment();
            if (averSalaryMap.containsKey(department)) {
                List<Integer> countAndSummSalaryList = averSalaryMap.get(department);
                countAndSummSalaryList.set(0, countAndSummSalaryList.get(0) + 1);
                countAndSummSalaryList.set(1, countAndSummSalaryList.get(1) + employee.getSalary());
                averSalaryMap.put(department, countAndSummSalaryList);
            } else {
                List<Integer> countAndSummSalaryList = new ArrayList<>();
                countAndSummSalaryList.add(1);
                countAndSummSalaryList.add(employee.getSalary());
                averSalaryMap.put(department, countAndSummSalaryList);
            }
        }
        calculateAndPrintAverageSalary();
    }

    private static void calculateAndPrintAverageSalary() {
        for (Map.Entry entry : averSalaryMap.entrySet()) {
            System.out.print("Department: " + entry.getKey() + ", ");
            List<Integer> countAndSummSalaryList = (List<Integer>) entry.getValue();
            int average = countAndSummSalaryList.get(1) / countAndSummSalaryList.get(0);
            System.out.println("Average Salary: " + average);
        }
    }

    public static void main(String[] args) {
        reader();
        for (Employee employee: listEmployee) {
            System.out.println(employee);
        }
        System.out.println();
        salaryDepartment();
    }
}
