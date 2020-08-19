package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

abstract class Contact implements Serializable {
    protected String number = "";
    protected LocalDateTime timeCreated;
    protected LocalDateTime timeModified;

    public String getNumber() {
        if(number.equals(""))
            return "[no number]";
        return number;
    }

    public LocalDateTime getTimeCreated() { return this.timeCreated; }
    public LocalDateTime getTimeModified() { return timeModified; }

    public void setNumber(String number) {
        if(validateNumber(number))
            this.number = number;
        else {
            System.out.println("Wrong number format!");
            this.number = "[no number]";
        }
    }

    public void setTimeCreated() { this.timeCreated = LocalDateTime.now(); }
    public void setTimeModified() { this.timeModified = LocalDateTime.now(); }

    public boolean hasNumber(){
        return !number.equals("");
    }
    protected boolean validateNumber(String number) {
        return number.matches("\\+?\\(?[a-zA-Z0-9]+\\)?([\\-\\s]([a-zA-Z0-9]{2,}))+|" +
                "\\+?[a-zA-Z0-9]+[\\s\\-]\\(?[a-zA-Z0-9]{2,}\\)?([\\s\\-]([a-zA-Z0-9]{2,}))*|" +
                "\\+?\\(?[a-zA-Z0-9]+\\)?");
    }

    static public ArrayList<Contact> search(ArrayList<Contact> list, String query){
        ArrayList<Contact> results = new ArrayList<>();
        for(Contact i : list){
            if(i instanceof Person){
                if(((Person) i).getName().toLowerCase().contains(query.toLowerCase()) || ((Person) i).getSurname().toLowerCase().contains(query.toLowerCase())
                        || ((Person) i).getNumber().contains(query))
                    results.add(i);
            }
            else if(i instanceof Organization){
                if(((Organization) i).getOrganizationName().toLowerCase().contains(query.toLowerCase())
                        || ((Organization) i).getNumber().contains(query))
                    results.add(i);
            }
        }
        return results;
    }

    abstract public String toString();
    abstract public String toExtendedString();
}