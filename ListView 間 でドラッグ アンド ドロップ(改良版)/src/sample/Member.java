package sample;

import java.io.Serializable;

//Serializableを継承した自作クラス
public class Member implements Serializable {
    private String name = "名無し";
    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}