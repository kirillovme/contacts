package contacts;

import java.io.Serializable;

class Organization extends Contact implements Serializable {
    private String organizationName;
    private String address;

    Organization(String organizationName, String address, String number) {
        this.organizationName = organizationName;
        this.address = address;
        if (super.validateNumber(number))
            super.number = number;
        else
            System.out.println("Wrong number format!");
        super.setTimeCreated();
        super.setTimeModified();
    }

    public String getOrganizationName() { return this.organizationName; }
    public String getAddress() { return this.address; }

    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() { return getOrganizationName(); }

    @Override
    public String toExtendedString(){
        return
                "Organization name: " + getOrganizationName() + "\n" +
                        "Address: " + getAddress() + "\n" +
                        "Number: " + getNumber() + "\n" +
                        "Time created: " + getTimeCreated() + "\n" +
                        "Time last edit: " + getTimeModified();

    }

}