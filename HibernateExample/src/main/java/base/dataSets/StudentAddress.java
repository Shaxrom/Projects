package dataSets;

import javax.persistence.Embeddable;

@Embeddable
public class StudentAddress {

    private String country;

    private String city;

    private String address;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
    return "StudentAddress{" +
            "country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", address='" + address + '\'' +
            '}';
}
}
