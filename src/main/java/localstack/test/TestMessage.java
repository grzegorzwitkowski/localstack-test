package localstack.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestMessage {

    private final String property1;

    @JsonCreator
    public TestMessage(@JsonProperty("property1") String property1) {
        this.property1 = property1;
    }

    public String getProperty1() {
        return property1;
    }

    @Override
    public String toString() {
        return "TestMessage{property1='" + getProperty1() + "'}";
    }
}
