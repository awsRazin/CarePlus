package hospital.service;

import hospital.util.*;

public class AccountingService {
    private final TableService expenses=new TableService("data/expenses.csv","E","Expense","Date","Category","Description","Amount");
    private final TableService revenues=new TableService("data/revenues.csv","RV","Revenue","Date","Source","Amount");
    public void addExpense(){expenses.add();} public void addRevenue(){revenues.add();} public void viewExpenses(){expenses.view();} public void viewRevenues(){revenues.view();}
    public double totalExpenses(){return sum(expenses.rows(),4);} public double totalRevenue(){return sum(revenues.rows(),3);}
    private double sum(java.util.List<String[]> rows,int index){double total=0;for(String[]r:rows)if(r.length>index)try{total+=Double.parseDouble(r[index]);}catch(NumberFormatException ignored){}return total;}
    public void summary(){double revenue=totalRevenue(),expense=totalExpenses();System.out.printf("Total revenue : %.2f%nTotal expense : %.2f%nNet revenue   : %.2f%n",revenue,expense,revenue-expense);}
}
