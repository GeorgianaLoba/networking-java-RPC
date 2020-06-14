package ro.ubb.rpc.domain;


import java.util.HashSet;
import java.util.Set;

//made changes to baseEntity from Integer->Long
public class Client extends BaseEntity<Long> {
    private String name;
    private String address;
    private Integer age;

    public Client(){

    }

    public Client(String name, String address, Integer age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}'+" "+super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public static String stringFromClient(Client client){
        String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(client.getId().toString()+separator);
        sb.append(client.getName()+separator);
        sb.append(client.getAddress()+separator);
        sb.append(client.getAge().toString()+separator);

        return sb.toString();
    }
    public static String stringFromSetClients(Set<Client> clients){
        return clients.stream().map(Client::stringFromClient).reduce("",(f,s)->f+";"+s);
    }
    public static Set<Client> clientSetFromString(String str){
        String[] cl = str.split(";");
        Set<Client> client= new HashSet<>();
        for (String c: cl) if (c.length()> 2) client.add(Client.clientFromString(c));
        return client;
    }

    public static Client clientFromString(String str){
        Client client=new Client();
        String[] args = str.split(",");
        client.setId(Long.parseLong(args[0]));
        client.setName(args[1]);
        client.setAddress(args[2]);
        client.setAge(Integer.parseInt(args[3]));

        return client;
    }


}
