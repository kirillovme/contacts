package contacts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


public class Main implements Serializable {
    private static final long serialVersionUID = 1;
    static Scanner scanner;

    static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(obj);
        oos.close();
    }

    static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static <T> String printList(ArrayList<T> list) {
        String tmp = "";
        int i = 1;
        for (T t : list) {
            tmp += i + ". " + t.toString();
            i++;
        }
        return tmp;
    }

    static ArrayList<Contact> enterSearch(ArrayList<Contact> list) {
        System.out.println("Enter search query: ");
        String query = scanner.next();
        ArrayList<Contact> results = Contact.search(list, query);

        System.out.format("Found %d results:\n", results.size());
        for (int i = 0, index = 1; i < results.size(); i++, index++) {
            System.out.println(index + ". " + results.get(i));
        }
        return results;
    }

    static String searchMenu() {
        System.out.println("[search] Enter action ([number], back, again):");
        return scanner.next();
    }

    static String recordMenu() {
        System.out.println("[record] Enter action (edit, delete, menu):");
        return scanner.next();
    }

    static String menuMenu() {
        System.out.println("[menu] Enter action (add/search/count/list/exit):");
        return scanner.next();
    }

    static String listMenu() {
        System.out.println("[list] Enter action ([number], back):");
        return scanner.next();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Contact> list = new ArrayList<Contact>();
        String fileName;
        try {
            if (args.length != 0) {
                list = (ArrayList<Contact>) deserialize(args[0]);
                fileName = args[0];
                System.out.println("Phone book loaded from hard drive");
            } else {
                fileName = "default.txt";
                serialize(list, fileName);
                System.out.println("Phone book will be saved to the default.txt file");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured: " + e +
                    "\nPhone book will be saved to the default.txt file");
            fileName = "default.txt";
            serialize(list, fileName);
        }

        scanner = new Scanner(System.in);
        ArrayList<Contact> results = new ArrayList<>();
        int recordIndex = 0;
        String choice = menuMenu();

        while (true) {
            switch (choice) {
                case ("count"): {
                    System.out.println("The phone book has " + list.size() + " records");
                    choice = menuMenu();
                    break;
                }

                case ("search"): {
                    results = enterSearch(list);
                    System.out.println();
                    String action = searchMenu();
                    if (action.equals("back")) {
                        choice = menuMenu();
                        break;
                    } else if (action.equals("again")) {
                        choice = "search";
                        break;
                    } else if (Integer.parseInt(action) <= list.size() && Integer.parseInt(action) > 0) {
                        int index = Integer.parseInt(action);
                        System.out.println(list.get(index - 1).toExtendedString());
                        recordIndex = index - 1;
                        System.out.println();
                        choice = recordMenu();
                        break;
                    }
                    break;
                }

                case ("info"): {
                    System.out.println(printList(list));
                    System.out.println("Select a record: ");
                    int index = scanner.nextInt();
                    Contact c = list.get(index - 1);

                    if (c instanceof Person) {
                        Person person = (Person) c;
                        System.out.println(person.toExtendedString());
                    } else if (c instanceof Organization) {
                        Organization organization = (Organization) c;
                        System.out.println(organization.toExtendedString());
                    }
                    break;
                }
                case ("edit"): {
                    if (list.size() > 0) {
                        Contact c = list.get(recordIndex);
                        if (c instanceof Person) {
                            Person person = (Person) c;
                            System.out.println("Select a field (name, surname, number, birth, gender): ");
                            String field = scanner.next();
                            scanner.nextLine();

                            switch (field) {
                                case ("name"): {
                                    System.out.println("Enter name: ");
                                    person.setName(scanner.next());
                                    break;
                                }
                                case ("surname"): {
                                    System.out.println("Enter surname: ");
                                    person.setSurname(scanner.next());
                                    break;
                                }
                                case ("number"): {
                                    System.out.println("Enter number: ");
                                    person.setNumber(scanner.nextLine());
                                    break;
                                }
                                case ("birth"): {
                                    System.out.println("Enter date of birth: ");
                                    person.setBirthday(scanner.nextLine());
                                    break;
                                }
                                case ("gender"): {
                                    System.out.println("Enter gender: ");
                                    person.setGender(scanner.next());
                                    break;
                                }
                            }
                            System.out.println("The record updated!");
                            serialize(list, fileName);
                        } else if (c instanceof Organization) {
                            Organization organization = (Organization) c;
                            System.out.println("Select a field (name, address, number): ");
                            String field = scanner.next();
                            scanner.nextLine();

                            switch (field) {
                                case ("name"): {
                                    System.out.println("Enter organization name: ");
                                    organization.setOrganizationName(scanner.nextLine());
                                    break;
                                }
                                case ("address"): {
                                    System.out.println("Enter surname: ");
                                    organization.setAddress(scanner.nextLine());
                                    break;
                                }
                                case ("number"): {
                                    System.out.println("Enter number: ");
                                    organization.setNumber(scanner.nextLine());
                                    break;
                                }
                            }
                            System.out.println("The record updated!");
                        }
                    } else
                        System.out.println("No records to edit!");

                    choice = recordMenu();
                    break;
                }

                case ("delete"): {
                    if (list.size() > 0) {
                        list.remove(recordIndex);
                        System.out.println("Record successfully removed!");
                        serialize(list, fileName);
                        choice = menuMenu();
                    } else {
                        System.out.println("No records to remove!");
                        choice = menuMenu();
                    }
                    break;
                }

                case ("menu"): {
                    System.out.println();
                    choice = menuMenu();
                    break;
                }
                case ("add"): {
                    System.out.println("Enter the type (person, organization): ");
                    String type = scanner.next();
                    if (type.equals("person")) {
                        System.out.print("Enter the name: ");
                        String name = scanner.next();
                        System.out.print("Enter the surname: ");
                        String surname = scanner.next();
                        scanner.nextLine();
                        System.out.print("Enter the date of birth: ");
                        String birth = scanner.nextLine();
                        if (birth.equals("")) {
                            birth = null;
                            System.out.println("Bad birth date!");
                        }

                        System.out.print("Enter the gender (M, F): ");
                        String gender = scanner.nextLine();
                        if (!gender.equals("M") && !gender.equals("F")) {
                            System.out.println("Bad gender!");
                            gender = null;
                        }
                        System.out.print("Enter the number: ");
                        String number = scanner.nextLine();

                        Person person = new Person(name, surname, number, birth, gender);
                        list.add(person);
                        serialize(list, fileName);
                    } else if (type.equals("organization")) {
                        scanner.nextLine();
                        System.out.print("Enter the organization name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter the address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter the number: ");
                        String number = scanner.nextLine();

                        Organization organization = new Organization(name, address, number);
                        list.add(organization);
                    }
                    System.out.println("The record added.");
                    serialize(list, fileName);
                    System.out.println();
                    choice = menuMenu();
                    break;
                }
                case ("list"): {
                    for (int i = 0, index = 1; i < list.size(); i++, index++) {
                        System.out.println(index + ". " + list.get(i));
                    }
                    System.out.println();
                    String action = listMenu();
                    if (action.equals("back")) {
                        choice = menuMenu();
                        break;
                    } else if (Integer.parseInt(action) <= list.size() && Integer.parseInt(action) > 0) {
                        int index = Integer.parseInt(action);
                        System.out.println(list.get(index - 1).toExtendedString());
                        recordIndex = index - 1;
                        System.out.println();
                        choice = recordMenu();
                        break;
                    }
                    break;
                }
                case ("exit"): {
                    System.exit(0);
                }
            }

        }

    }
}